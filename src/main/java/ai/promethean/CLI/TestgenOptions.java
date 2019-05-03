package ai.promethean.CLI;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

/**
 * Command-line options definition for Testgen command
 */
public class TestgenOptions extends OptionsBase {
    @Option(
            name = "inFile",
            abbrev = 'i',
            help = "JSON input file for the test generator\nMust contain an initial state and a goal state\n",
            category = "testgen",
            defaultValue = ""
    )
    public String inFile;

    @Option(
            name = "inString",
            help = "JSON string input for test generator\n",
            category = "testgen",
            defaultValue = ""
    )
    public String inString;

    @Option(
            name = "numTasks",
            abbrev = 'n',
            help = "Number of tasks to generate for test case\n",
            category = "testgen",
            defaultValue = "0"
    )
    public int numTasks;

    @Option(
            name = "perturbations",
            abbrev = 'p',
            help = "Number of perturbations to generate for test case\n",
            category = "testgen",
            defaultValue = "0"
    )
    public int perturbations;

    @Option(
            name = "optimal-plan",
            help = "If true, generated test will contain a specific, optimal plan\n",
            category = "testgen",
            defaultValue = "true"
    )
    public boolean optimalPlan;

    @Option(
            name = "verbose",
            abbrev = 'v',
            help = "Enables logging\n",
            category = "testgen",
            defaultValue = "false"
    )
    public boolean verbose;

    @Option(
            name = "logs",
            abbrev = 'l',
            help = "Write logs to specific directory\n",
            category = "testgen",
            defaultValue = "Logs"
    )
    public String logs;

    @Option(
            name = "print-logs",
            help = "print any logs to the command line (enables verbose by default)\n",
            category = "testgen",
            defaultValue = "false"
    )
    public boolean printLogs;

    @Option(
            name = "output",
            abbrev = 'o',
            help = "Simulate execution after planning (including perturbations)\n",
            category = "testgen",
            defaultValue = "generated_test.json"
    )
    public String output;


}
