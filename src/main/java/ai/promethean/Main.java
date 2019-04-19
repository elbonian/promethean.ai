package ai.promethean;

import ai.promethean.API.API;
import ai.promethean.CLI.CLI;
import ai.promethean.DataModel.*;
import ai.promethean.TestCaseGenerator.TestCaseGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        // CLI.exec(args);
        API api = new API();
        api.executePlan("JSON_input/TestCases/generated_test.json", true, 100, false);
//        Map<String, Object> ob = api.parseInput("JSON_input/TestCases/many_paths.json", true);
//        TestCaseGenerator testCaseGenerator = new TestCaseGenerator((SystemState)  ob.get("initialState"),(GoalState) ob.get("goalState"), 11, true);
//        ArrayList<Task> t = testCaseGenerator.generateTestCase();
//        ArrayList<Optimization> o = testCaseGenerator.generateOptimizations();
//        testCaseGenerator.testCaseToJSON(t, o, 0);
    }
}
