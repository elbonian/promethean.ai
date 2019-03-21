package ai.promethian.ExecutingAgentTest;

import ai.promethean.ExecutingAgent.Clock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClockTest {
    @Test
    void checkInit() {
        Clock clock1 = new Clock();
        assertEquals(0, clock1.getCurrentTime());

        int startTime = 123;
        Clock clock2 = new Clock(startTime);
        assertEquals(startTime, clock2.getCurrentTime());

        Clock clock3 = new Clock(startTime, 2);
        assertEquals(startTime, clock3.getCurrentTime());

        assertEquals(1, clock1.getStepSize());
        assertEquals(1, clock2.getStepSize());
        assertEquals(2, clock3.getStepSize());
    }

    @Test
    void checkLag() {
        assertEquals(0, Clock.getLag());

        int lagAmount = 10;
        Clock.addLag(lagAmount);

        assertEquals(lagAmount, Clock.getLag());

        Clock.resetLag();
        assertEquals(0, Clock.getLag());
    }
}
