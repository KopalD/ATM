import com.upstox.enums.CardStatus;
import com.upstox.enums.TransactionMenu;
import com.upstox.model.*;
import com.upstox.service.AtmService;
import com.upstox.service.impl.AtmServiceImpl;
import com.upstox.state.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCreditState {
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
    @DisplayName("Test to check a valid Credit transfer to user's account through ATM. Currently user can only deposit cash in his own account.")
    public void doCredit() {
        assertTrue(this.atmServiceImpl.getState() instanceof ReadyState);
        this.atmServiceImpl.insertCard(card);
        assertTrue(this.atmServiceImpl.getState() instanceof EnterPINState);
        this.atmServiceImpl.authenticatePin("1234");
        assertTrue(this.atmServiceImpl.getState() instanceof TransactionMenuState);
        this.atmServiceImpl.selectOption(TransactionMenu.CREDIT);
        assertTrue(this.atmServiceImpl.getState() instanceof CreditState);

        List<Bill> billList = new ArrayList<>();
        billList.add(new Bill(100, "XXX-8745-873"));
        billList.add(new Bill(100, "XXX-8745-874"));
        billList.add(new Bill(100, "XXX-8745-875"));
        billList.add(new Bill(100, "XXX-8745-876"));
        billList.add(new Bill(100, "XXX-8745-877"));

        this.atmServiceImpl.credit(500, billList);
        assertTrue(this.atmServiceImpl.getState() instanceof DisplayBalanceState);
        this.atmServiceImpl.fetchCard();
        assertTrue(this.atmServiceImpl.getState() instanceof ExitState);
    }

    @Test
    @DisplayName("Test to invalidate damaged/invalid bills provided during credit.")
    public void doCreditWithInvalidBills() {
        assertTrue(this.atmServiceImpl.getState() instanceof ReadyState);
        this.atmServiceImpl.insertCard(card);
        assertTrue(this.atmServiceImpl.getState() instanceof EnterPINState);
        this.atmServiceImpl.authenticatePin("1234");
        assertTrue(this.atmServiceImpl.getState() instanceof TransactionMenuState);
        this.atmServiceImpl.selectOption(TransactionMenu.CREDIT);
        assertTrue(this.atmServiceImpl.getState() instanceof CreditState);

        List<Bill> bills = new ArrayList<>();
        bills.add(new Bill(100, "XXX-8745-873"));
        bills.add(new Bill(100, "XXX-8745-874"));
        bills.add(new Bill(100, "XXX-8745-875"));
        bills.add(new Bill(100, "KIP-8745-876"));
        bills.add(new Bill(100, "XXX-8745-877"));
        this.atmServiceImpl.credit(500, bills);
        assertTrue(this.atmServiceImpl.getState() instanceof ErrorState);
        this.atmServiceImpl.fetchCard();
        assertTrue(this.atmServiceImpl.getState() instanceof  ExitState);
    }

    @Test
    @DisplayName("test to check mismatch between amount entered and face value of bills entered by user.")
    public void doCreditWithInvalidAmount() {
        assertTrue(this.atmServiceImpl.getState() instanceof ReadyState);
        this.atmServiceImpl.insertCard(card);
        assertTrue(this.atmServiceImpl.getState() instanceof EnterPINState);
        this.atmServiceImpl.authenticatePin("1234");
        assertTrue(this.atmServiceImpl.getState() instanceof TransactionMenuState);
        this.atmServiceImpl.selectOption(TransactionMenu.CREDIT);
        assertTrue(this.atmServiceImpl.getState() instanceof CreditState);

        List<Bill> bills = new ArrayList<>();
        bills.add(new Bill(100, "XXX-8745-873"));
        bills.add(new Bill(100, "XXX-8745-874"));
        bills.add(new Bill(100, "XXX-8745-875"));
        bills.add(new Bill(100, "XXX-8745-876"));
        bills.add(new Bill(100, "XXX-8745-877"));
        this.atmServiceImpl.credit(600, bills);
        assertTrue(this.atmServiceImpl.getState() instanceof  ErrorState);
        this.atmServiceImpl.fetchCard();
        assertTrue(this.atmServiceImpl.getState() instanceof  ExitState);
    }

}
