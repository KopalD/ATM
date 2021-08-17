import com.upstox.enums.CardStatus;
import com.upstox.service.impl.AtmServiceImpl;
import com.upstox.model.Card;
import com.upstox.state.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAtmPin {
    private AtmServiceImpl atmServiceImpl;
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
    @DisplayName("If user wants to exit from Transaction Menu State by pressing Exit button.")
    public void testExitAfterEnteringPin() {
        assertTrue(this.atmServiceImpl.getState() instanceof ReadyState);
        this.atmServiceImpl.insertCard(card);
        assertTrue(this.atmServiceImpl.getState() instanceof EnterPINState);
        this.atmServiceImpl.authenticatePin("1234");
        assertTrue(this.atmServiceImpl.getState() instanceof TransactionMenuState);
        this.atmServiceImpl.fetchCard(); // It will work as an exit method and return the card to user.
        assertTrue(this.atmServiceImpl.getState() instanceof ExitState);
    }



    @Test
    @DisplayName("Test to invalidate an incorrect pin entered by user.")
    public void testInvalidPin() {
        assertTrue(this.atmServiceImpl.getState() instanceof ReadyState);
        this.atmServiceImpl.insertCard(card);
        assertTrue(this.atmServiceImpl.getState() instanceof EnterPINState);
        this.atmServiceImpl.authenticatePin("1231");
        assertTrue(this.atmServiceImpl.getState() instanceof ErrorState);
        this.atmServiceImpl.fetchCard();
        assertTrue(this.atmServiceImpl.getState() instanceof  ExitState);
    }

}
