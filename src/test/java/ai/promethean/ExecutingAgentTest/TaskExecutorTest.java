package ai.promethean.ExecutingAgentTest;

import ai.promethean.DataModel.NumericalProperty;
import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.Task;
import ai.promethean.ExecutingAgent.ClockObserver;
import ai.promethean.ExecutingAgent.TaskExecutor;
import ai.promethean.Planner.Plan;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskExecutorTest {

    @Test
    void testInit() {
        SystemState start = new SystemState(0);
        SystemState goal = new SystemState(2);
        Task t = new Task(2, "task");

        goal.setPreviousState(start);
        goal.setPreviousTask(t);

        Plan plan = new Plan(goal,true);

        TaskExecutor te = new TaskExecutor(plan);
        assertFalse(te.isPlanCompleted());
    }

    @Test
    void testUpdate() {
        int endTime = 2;
        SystemState start = new SystemState(0);
        SystemState goal = new SystemState(endTime);
        start.addProperty("number", 0.0);
        goal.addProperty("number", 0.0);
        Task t = new Task(endTime, "task");

        NumericalProperty n = new NumericalProperty("number", 1.0);
        n.setType("delta");

        t.addProperty(n);

        goal.setPreviousState(start);
        goal.setPreviousTask(t);

        Plan plan = new Plan(goal,true);

        TaskExecutor te = new TaskExecutor(plan);
        te.addState(start);

        te.update(1);
        assertFalse(te.isPlanCompleted());
        assertEquals(0.0, (double)(ClockObserver.peekLastState().getProperty("number").getValue()));

        ClockObserver.peekLastState().getProperty("task");

        te.update(endTime);
        assertFalse(te.isPlanCompleted());
        assertEquals(1.0, (double)(ClockObserver.peekLastState().getProperty("number").getValue()));

        te.update(3);
        assertTrue(te.isPlanCompleted());
    }
}
