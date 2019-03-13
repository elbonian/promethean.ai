package ai.promethean;

import java.io.IOException;
import java.util.*;

import ai.promethean.DataModel.*;
import ai.promethean.API.*;
import ai.promethean.TestCaseGenerator.*;


public class Main {

    public static void main(String[] args) {
        API api = new API();
        // api.generatePlan("JSON_input/InputFiles/test.json", true);

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
        TestCaseGenerator test_case = new TestCaseGenerator(start, goal);
        ArrayList<PropertyDelta> deltas = test_case.getPropertyDeltas();

        ArrayList<Task> generated_tasks = test_case.generateTestCase();

        try {
            test_case.testCaseToJSON(generated_tasks);
        } catch (IOException e) {
            System.out.println("Oof");
            System.out.println(e);
        }
    }
}
