import com.upstox.enums.CardStatus;
import com.upstox.service.AtmService;
import com.upstox.service.impl.AtmServiceImpl;
import com.upstox.model.Card;
import com.upstox.state.impl.ErrorState;
import com.upstox.state.impl.ExitState;
import com.upstox.state.impl.ReadyState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUserCard {
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
    @DisplayName("Negative test to check a invalid card.")
    public void testInvalidCard() {
        card.setCardNumber(1l * 48573);
        assertTrue(this.atmServiceImpl.getState() instanceof ReadyState);
        this.atmServiceImpl.insertCard(card);
        assertTrue(this.atmServiceImpl.getState() instanceof ErrorState);
        this.atmServiceImpl.fetchCard();
        assertTrue(this.atmServiceImpl.getState() instanceof ExitState);
    }

    @Test
    @DisplayName("Negative test to check a invalid card.")
    public void testExpiredCard() {
        card.setCardNumber(4263478634786327L); // Expired Card
        assertTrue(this.atmServiceImpl.getState() instanceof ReadyState);
        this.atmServiceImpl.insertCard(card);
        assertTrue(this.atmServiceImpl.getState() instanceof  ErrorState);
        this.atmServiceImpl.fetchCard();
        assertTrue(this.atmServiceImpl.getState() instanceof  ExitState);
    }
}
