package ai.promethean.CLI;

import ai.promethean.API.API;
import ai.promethean.DataModel.GoalState;
import ai.promethean.DataModel.Optimization;
import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.Task;
import ai.promethean.Logger.Logger;
import ai.promethean.Parser.JSONParser;
import ai.promethean.TestCaseGenerator.TestCaseGenerator;
import com.google.devtools.common.options.OptionsParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * CLI is an example of how to create a commmand line tool to interact
 * with the system via the API
 */
public class CLI {

    /**
     * The main running function of the CLI. Checks for command types.
     * @param args
     */
    public static void exec(String[] args) {
        if (args.length == 0) {
            writeShortManual();
            return;
        }

        switch (args[0].toLowerCase()) {
            case "plan":
                plan(args);
                break;
            case "testgen":
                testgen(args);
                break;
            default: API.throwCLIError("Invalid command: " + args[0]);
        }
    }

    /**
     * Outputs a manual of commands and their descriptions
     */
    private static void writeShortManual() {
        System.out.println("\nPromethean CLI v1.0.0");
        System.out.println("Commands:");
        System.out.println("\tplan - create (and optionally execute) a plan given input json");
        System.out.println("\ttestgen - generate a test input file given initial and goal states");
        System.out.println("Run <command> --help to see all options\n");
    }

    /**
     * Parses options for the Plan command
     * @param args
     * @return PlanOptions object of the given options
     */
    private static PlanOptions parsePlanOptions(String[] args) {
        OptionsParser parser = OptionsParser.newOptionsParser(PlanOptions.class);
        parser.parseAndExitUponError(args);
        return parser.getOptions(PlanOptions.class);
    }

    /**
     * Parses options for the Testgen command
     * @param args
     * @return TestgenOptions object of the given options
     */
    private static TestgenOptions parseTestgenOptions(String[] args) {
        OptionsParser parser = OptionsParser.newOptionsParser(TestgenOptions.class);
        parser.parseAndExitUponError(args);
        return parser.getOptions(TestgenOptions.class);
    }

    private static void setVerbosity(PlanOptions options) {
        Logger.directoryName = options.logs;
        if (options.printLogs) {
            Logger.logFlag = true;
            Logger.printFlag = true;
        } else if (options.verbose) {
            Logger.logFlag = true;
        }
    }

    private static void setVerbosity(TestgenOptions options) {
        Logger.directoryName = options.logs;
        if (options.printLogs) {
            Logger.logFlag = true;
            Logger.printFlag = true;
        } else if (options.verbose) {
            Logger.logFlag = true;
        }
    }

    /**
     * The main method of the Plan command
     * @param args - requires at least an input file or string
     */
    private static void plan(String[] args) {
        PlanOptions options = parsePlanOptions(args);
        CLI.setVerbosity(options);
        if (!options.inFile.isEmpty()) {
            if (options.execute) {
                API.executePlan( options.inFile,
                        true,
                        options.planOutput,
                        options.statesOutput,
                        options.stop,
                        options.clf);
            } else {
                API.generatePlanFromJSON( options.inFile,
                        true,
                        options.planOutput,
                        options.stop,
                        options.clf);
            }
        } else if (!options.inString.isEmpty()) {
            if (options.execute) {
                API.executePlan( options.inFile,
                        false,
                        options.planOutput,
                        options.statesOutput,
                        options.stop,
                        options.clf);
            } else {
                API.generatePlanFromJSON( options.inFile,
                        false,
                        options.planOutput,
                        options.stop,
                        options.clf);
            }
        } else {
            API.throwCLIError("No input JSON provided (-i)");
        }
    }

    /**
     * The main method of the Testgen command
     * @param args - requires at lease an input file or string
     */
    private static void testgen(String[] args) {
        JSONParser parser = new JSONParser();
        TestgenOptions options = parseTestgenOptions(args);
        CLI.setVerbosity(options);
        if (options.numTasks < 1) {
            API.throwCLIError("Invalid number of tasks provided (-n): " + options.numTasks);
        }

        Map<String, Object> objects;
        if (!options.inFile.isEmpty()) {
            objects = parser.parse(options.inFile, true);
        } else if (!options.inString.isEmpty()) {
            objects = parser.parse(options.inString, false);
        } else {
            API.throwCLIError("No input JSON provided (-i)");
            return;
        }

        if (objects.get("initialState") == null || objects.get("goalState") == null) {
            API.throwCLIError("Both an initial state and a goal state must be provided.");
        }

        TestCaseGenerator generator = new TestCaseGenerator(
                (SystemState) objects.get("initialState"),
                (GoalState) objects.get("goalState"),
                options.numTasks,
                options.optimalPlan,
                options.output
        );

        ArrayList<Task> tasks = generator.generateTestCase();
        ArrayList<Optimization> ops = generator.generateOptimizations();
        try{
            generator.testCaseToJSON(tasks, ops, options.perturbations);
        } catch (IOException e){
            API.throwCLIError("Failed to write testCase to JSON");
        }

    }

}
