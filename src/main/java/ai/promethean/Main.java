package ai.promethean;

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
        GoalState goal = new GoalState();
        goal.addRequirement("Battery", 80.0, ">=");
        goal.addRequirement("doorsOpen", true, "==");
        goal.addRequirement("scienceCollected", 50.0, ">=");
        TestCaseGenerator test_case = new TestCaseGenerator(start, goal);
        ArrayList<PropertyDelta> deltas = test_case.getPropertyDeltas();

        ArrayList<Task> generated_tasks = test_case.getCriticalPathTasks();
        for(Task task: generated_tasks) {
            System.out.println(task + "\n****************\n");
        }
    }
}
