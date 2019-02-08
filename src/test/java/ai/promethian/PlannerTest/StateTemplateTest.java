package ai.promethian.PlannerTest;

import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.Task;
import ai.promethean.Planner.StateTemplate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertSame;

public class StateTemplateTest {
    private SystemState systemState = new SystemState(1);
    private Task task = new Task(1, 2);
    private StateTemplate stateTemplate = new StateTemplate(systemState, task, 1.0, 1.0);

    @Test
    void checkStateTemplateReference() {
        assertSame(stateTemplate.getPreviousState(), systemState);
        assertSame(stateTemplate.getTask(), task);
    }

}
