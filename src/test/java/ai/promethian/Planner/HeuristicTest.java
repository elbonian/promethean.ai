package ai.promethian.Planner;
import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.Task;
import ai.promethean.GraphManagement.Heuristic;
import ai.promethian.TestConstants.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HeuristicTest {
    private SystemState fullState = StateTestConstants.getFullState();
    private SystemState boolState = StateTestConstants.getBooleanState();
    private Task fullTask1 = TaskTestConstants.getFullTask1();
    private Task fullTask2 = TaskTestConstants.getFullTask2();

    @Test
    void checkH_value () {

    }
}
