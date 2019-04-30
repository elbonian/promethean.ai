package ai.promethean.PlannerTest;

import ai.promethean.DataModel.Condition;
import ai.promethean.DataModel.GoalState;
import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.Task;
import ai.promethean.Planner.Heuristic;
import ai.promethean.TestConstants.ConditionTestConstants;
import ai.promethean.TestConstants.StateTestConstants;
import ai.promethean.TestConstants.TaskTestConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HeuristicTest {

    @Test
    public void HeuristicTest() {
        GoalState testGoal = new GoalState();
        for ( Condition goalCondition :ConditionTestConstants.getAllTestConditions() ) {
            testGoal.addRequirement(goalCondition);
        }
        SystemState currentState = StateTestConstants.getFullState();
        double val = Heuristic.h_value(currentState, testGoal, new Task(10,"NullTask"));
        // Should be the norm or the difference vector
        assertEquals(119.98141000000001, val);

        testGoal.addRequirement("StringProperty1","Some String I Guess","==");
        val = Heuristic.h_value(currentState, testGoal, new Task(10,"NullTask"));
        // This new property requirement is already met by the state therefore should not affect the heuristic
        assertEquals(119.98141000000001, val);

        testGoal.addRequirement("BoolProperty2", true, "==");
        val = Heuristic.h_value(currentState, testGoal, new Task(10,"NullTask"));
        // This new property is not met by the state and should therefore change the heuristic
        assertEquals(119.98557723988372, val);

        Task testTask = TaskTestConstants.getFullTask2();
        val = Heuristic.h_value(currentState, testGoal, testTask);
        // Heuristic should apply a task's impacts in order to calculate the next state's heuristic value
        assertEquals(116.84409928461257, val);

    }
}
