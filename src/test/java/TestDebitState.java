import com.upstox.enums.CardStatus;
import com.upstox.enums.TransactionMenu;
import com.upstox.model.*;
import com.upstox.service.AtmService;
import com.upstox.service.impl.AtmServiceImpl;
import com.upstox.state.impl.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestDebitState {
    private AtmService atmServiceImpl;
    private Card card;

    @BeforeEach
    public void setUp() {
        this.atmServiceImpl = new AtmServiceImpl();
        this.atmServiceImpl.init();
        card = new Card();
        card.setCardNumber(2024465520292333L);
        card.setAccountId("1023465510292764");
        card.setExpirationDate(LocalDate.of(2021, 11, 23));
        card.setStatus(CardStatus.ACTIVE);
    }

    @Test
    @DisplayName("Test for checking a valid debit card transaction through ATM.")
    public void doDebit() {
        assertTrue(this.atmServiceImpl.getState() instanceof ReadyState);
        this.atmServiceImpl.insertCard(card);
        assertTrue(this.atmServiceImpl.getState() instanceof EnterPINState);
        this.atmServiceImpl.authenticatePin("1234");
        assertTrue(this.atmServiceImpl.getState() instanceof TransactionMenuState);
        this.atmServiceImpl.selectOption(TransactionMenu.DEBIT);
        assertTrue(this.atmServiceImpl.getState() instanceof DebitState);
        this.atmServiceImpl.withdraw(500);
        assertTrue(this.atmServiceImpl.getState() instanceof DisplayBalanceState);
        this.atmServiceImpl.fetchCard();
        assertTrue(this.atmServiceImpl.getState() instanceof ExitState);
    }

    @Test
    @DisplayName("Test to print balance.")
    public void displayBalance() {
        assertTrue(this.atmServiceImpl.getState() instanceof ReadyState);
        this.atmServiceImpl.insertCard(card);
        assertTrue(this.atmServiceImpl.getState() instanceof EnterPINState);
        this.atmServiceImpl.authenticatePin("1234");
        assertTrue(this.atmServiceImpl.getState() instanceof TransactionMenuState);
        this.atmServiceImpl.selectOption(TransactionMenu.DISPLAY_BALANCE);
        assertTrue(this.atmServiceImpl.getState() instanceof DisplayBalanceState);
        this.atmServiceImpl.fetchCard();
        assertTrue(this.atmServiceImpl.getState() instanceof  ExitState);
    }



    @Test
    @DisplayName("Test to invalidate a debit transaction when user has insufficient funds in his account.")
    public void insufficientBalanceInUserAccount() {
        card.setCardNumber(4263478634786322L);
        assertTrue(this.atmServiceImpl.getState() instanceof ReadyState);
        this.atmServiceImpl.insertCard(card);
        assertTrue(this.atmServiceImpl.getState() instanceof EnterPINState);
        this.atmServiceImpl.authenticatePin("9013");
        assertTrue(this.atmServiceImpl.getState() instanceof TransactionMenuState);
        this.atmServiceImpl.selectOption(TransactionMenu.DEBIT);
        assertTrue(this.atmServiceImpl.getState() instanceof DebitState);
        this.atmServiceImpl.withdraw(600);
        assertTrue(this.atmServiceImpl.getState() instanceof ErrorState);
        this.atmServiceImpl.fetchCard();
        assertTrue(this.atmServiceImpl.getState() instanceof  ExitState);
    }

    @Test
    @DisplayName("Test to print a valid debit card transaction statement")
    public void printStatement() {
        assertTrue(this.atmServiceImpl.getState() instanceof ReadyState);
        this.atmServiceImpl.insertCard(card);
        assertTrue(this.atmServiceImpl.getState() instanceof EnterPINState);
        this.atmServiceImpl.authenticatePin("1234");
        assertTrue(this.atmServiceImpl.getState() instanceof TransactionMenuState);
        this.atmServiceImpl.selectOption(TransactionMenu.DEBIT);
        assertTrue(this.atmServiceImpl.getState() instanceof DebitState);
        this.atmServiceImpl.withdraw(100);
        assertTrue(this.atmServiceImpl.getState() instanceof DisplayBalanceState);
        this.atmServiceImpl.printStatement();
        assertTrue(this.atmServiceImpl.getState() instanceof DisplayBalanceState);
    }

    @Test
    @DisplayName("Test to not allow a transaction if ATM doesn't have sufficient funds.")
    public void insufficientBalanceInATM() {
        this.atmServiceImpl.setAvailableCash(100);
        assertTrue(this.atmServiceImpl.getState() instanceof ReadyState);
        this.atmServiceImpl.insertCard(card);
        assertTrue(this.atmServiceImpl.getState() instanceof EnterPINState);
        this.atmServiceImpl.authenticatePin("1234");
        assertTrue(this.atmServiceImpl.getState() instanceof TransactionMenuState);
        this.atmServiceImpl.selectOption(TransactionMenu.DEBIT);
        assertTrue(this.atmServiceImpl.getState() instanceof DebitState);
        this.atmServiceImpl.withdraw(600);
        assertTrue(this.atmServiceImpl.getState() instanceof  ErrorState);
        this.atmServiceImpl.fetchCard();
        assertTrue(this.atmServiceImpl.getState() instanceof  ExitState);
    }


}
