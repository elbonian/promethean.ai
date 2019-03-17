package ai.promethean;

import java.io.IOException;
import java.util.*;

import ai.promethean.DataModel.*;
import ai.promethean.API.*;
import ai.promethean.TestCaseGenerator.*;


public class Main {

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

        // TestCaseGenerator test_case = new TestCaseGenerator(start, goal);
        // TestCaseGenerator test_case = new TestCaseGenerator(start, goal, 500, "500_tasks");
        // TestCaseGenerator test_case = new TestCaseGenerator(start, goal, 1000, "1000_tasks");
        // TestCaseGenerator test_case = new TestCaseGenerator(start, goal, 5000, "5000_tasks");

        // **** Test case generation... Comment out to stop generating the files

//        ArrayList<Task> generated_tasks = test_case.generateTestCase();
//        ArrayList<Optimization> optimizations = test_case.generateOptimizations(3);
//
//        try {
//            test_case.testCaseToJSON(generated_tasks, optimizations, 3);
//        } catch (IOException e) {
//            System.out.println("Oof");
//            System.out.println(e);
//        }


        API api = new API();
        api.generatePlan("JSON_input/TestCases/5000_tasks.json", true);
        // api.generatePlan("JSON_input/InputFiles/Test.json", true);
    }

}
