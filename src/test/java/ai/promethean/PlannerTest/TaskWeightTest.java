package ai.promethean.PlannerTest;

import ai.promethean.DataModel.StaticOptimizations;
import ai.promethean.DataModel.Task;
import ai.promethean.Planner.TaskWeight;
import ai.promethean.TestConstants.OptimizationTestConstants;
import ai.promethean.TestConstants.TaskTestConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TaskWeightTest {

    @Test
    public void taskWeightTest() {
        Task testTask = TaskTestConstants.getFullTask1();
        TaskWeight.initialHeuristic = 10.0;

        double val = TaskWeight.calculateTaskWeight(testTask, new StaticOptimizations());
        assertEquals(28.57142857142857, val);

        StaticOptimizations optimizations = OptimizationTestConstants.getAllTestOptimizations();
        val = TaskWeight.calculateTaskWeight(testTask, optimizations);
        // Task Weight should change when optimizations are applied
        assertEquals(21.867743333333337, val);
    }
}
