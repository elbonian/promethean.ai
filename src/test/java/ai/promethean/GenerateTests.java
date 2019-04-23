package ai.promethean;

import java.io.IOException;
import java.util.*;

import ai.promethean.API.API;
import ai.promethean.DataModel.GoalState;
import ai.promethean.DataModel.Optimization;
import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.Task;
import ai.promethean.TestCaseGenerator.*;

public class GenerateTests {

    public static void main(String[] args) {

        SystemState start = new SystemState();
        start.addProperty("Battery", 100.0);
        start.addProperty("doorsOpen", false);
        start.addProperty("scienceCollected", 0.0);
        start.addProperty("isHappy", false);
        start.addProperty("wheelSpeed", 20.0);
        start.addProperty("solarPanelsOpen", false);
        start.addProperty("Altitude", 50.0);
        start.addProperty("CPU_temperature", 35.0);
        start.addProperty("Gremlins", false);
        GoalState goal = new GoalState();
        goal.addRequirement("Battery", 80.0, ">=");
        goal.addRequirement("doorsOpen", true, "==");
        goal.addRequirement("scienceCollected", 50.0, ">=");
        goal.addRequirement("isHappy", true, "==");
        goal.addRequirement("solarPanelsOpen", true, "==");
        goal.addRequirement("Gremlins", false, "==");
        goal.addRequirement("Altitude", 75.0, ">=");
        goal.addRequirement("CPU_temperature", 45.0, "<=");

        // TestCaseGenerator test_case = new TestCaseGenerator(start, goal, true);
        // TestCaseGenerator test_case = new TestCaseGenerator(start, goal, false);
        // TestCaseGenerator test_case = new TestCaseGenerator(start, goal, 100, true);
        TestCaseGenerator test_case = new TestCaseGenerator(start, goal, 100, false, "100_tasks_nocrit");
        // TestCaseGenerator test_case = new TestCaseGenerator(start, goal, 500, true, "500_tasks");
        // TestCaseGenerator test_case = new TestCaseGenerator(start, goal, 500, false, "500_tasks_nocrit");
        // TestCaseGenerator test_case = new TestCaseGenerator(start, goal, 1000, true, "1000_tasks");
        // TestCaseGenerator test_case = new TestCaseGenerator(start, goal, 1000, false, "1000_tasks_nocrit");
        // TestCaseGenerator test_case = new TestCaseGenerator(start, goal, 5000, true, "5000_tasks");
        // TestCaseGenerator test_case = new TestCaseGenerator(start, goal, 5000, false, "5000_tasks_nocrit");

        // **** Test case generation... Comment out to stop generating the files

        ArrayList<Task> generated_tasks = test_case.generateTestCase();
        ArrayList<Optimization> optimizations = test_case.generateOptimizations();

        try {
            test_case.testCaseToJSON(generated_tasks, optimizations, 3);
        } catch (IOException e) {
            System.out.println("Oof");
            System.out.println(e);
        }
    }
}
