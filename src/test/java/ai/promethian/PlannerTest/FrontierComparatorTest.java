package ai.promethian.PlannerTest;

import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.Task;
import ai.promethean.Planner.FrontierComparator;
import ai.promethean.Planner.StateTemplate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class FrontierComparatorTest {
    private PriorityQueue<StateTemplate> frontier = new PriorityQueue<StateTemplate>(1, new FrontierComparator());
    private SystemState systemState = new SystemState(1);
    private Task task = new Task(1, "TestTask");

    private StateTemplate stateTemplate1 = new StateTemplate(systemState, task, 1.0, 10.0 );
    private StateTemplate stateTemplate2 = new StateTemplate(systemState, task, 1.0, 13.0 );
    private StateTemplate stateTemplate3 = new StateTemplate(systemState, task, 1.0, 8.0 );
    private StateTemplate stateTemplate4 = new StateTemplate(systemState, task, 1.0, 24.0 );

    public FrontierComparatorTest() {
        frontier.add(stateTemplate1);
        frontier.add(stateTemplate2);
    }

    @Test
    void checkPeek() {
        assertSame(frontier.peek(), stateTemplate1);
        frontier.add(stateTemplate3);
        frontier.add(stateTemplate4);
        assertSame(frontier.peek(), stateTemplate3);
    }

    @Test
    void checkPoll() {
        frontier.add(stateTemplate3);
        assertSame(frontier.poll(), stateTemplate3);
        assertSame(frontier.poll(), stateTemplate1);
        frontier.add(stateTemplate4);
        assertSame(frontier.poll(), stateTemplate2);
    }
}
