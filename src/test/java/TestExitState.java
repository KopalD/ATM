import com.upstox.service.impl.AtmServiceImpl;
import com.upstox.state.ATMState;
import com.upstox.state.impl.ExitState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestExitState {

    @Test
    public void ExitState() {
        AtmServiceImpl atmServiceImpl = new AtmServiceImpl();
        ATMState state = new ExitState(atmServiceImpl);
        assertNotNull(state);
    }

}
