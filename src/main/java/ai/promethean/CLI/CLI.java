package ai.promethean.CLI;

import ai.promethean.API.API;
import ai.promethean.API.CLIError;
import ai.promethean.Planner.Plan;
import com.google.devtools.common.options.OptionsParser;

public class CLI {
    private static API api = new API();

    public static void exec(String[] args) {
        if (args.length == 0) {
            writeShortManual();
            return;
        }

        switch (args[0]) {
            case "plan":
                plan(args);
                break;
            case "testgen":
                testgen(args);
                break;
            default: throw new CLIError("Invalid command: " + args[0]);
        }
    }

    private static void writeShortManual() {
        System.out.println("systemcli manual");
    }

    private static PlanOptions parsePlanOptions(String[] args) {
        OptionsParser parser = OptionsParser.newOptionsParser(PlanOptions.class);
        parser.parseAndExitUponError(args);
        return parser.getOptions(PlanOptions.class);
    }
    private static TestgenOptions parseTestgenOptions(String[] args) {
        OptionsParser parser = OptionsParser.newOptionsParser(TestgenOptions.class);
        parser.parseAndExitUponError(args);
        return parser.getOptions(TestgenOptions.class);
    }
    private static void plan(String[] args) {
        PlanOptions options = parsePlanOptions(args);
        if (!options.inFile.isEmpty()) {
            if (options.execute) {
                api.executePlan(options.inFile, true);
            } else {
                Plan plan = api.generatePlanFromJSON(options.inFile, true);
            }
        } else if (!options.inString.isEmpty()) {
            if (options.execute) {
                api.executePlan(options.inFile, false);
            } else {
                Plan plan = api.generatePlanFromJSON(options.inFile, false);
            }
        } else {
            throw new CLIError("No input JSON provided");
        }
    }
    private static void testgen(String[] args) {
        System.out.println("testgen");
    }

}
