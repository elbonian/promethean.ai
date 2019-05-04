package ai.promethean.CLI;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

/**
 * Command-line options definition for Plan command
 */
public class PlanOptions extends OptionsBase {
    @Option(
            name = "in-file",
            abbrev = 'i',
            help = "JSON input file for planning system\nMust contain at least an initial state, a goal state, and list of tasks\n",
            category = "plan",
            defaultValue = ""
    )
    public String inFile;

    @Option(
            name = "in-string",
            help = "JSON string input for planning system\n",
            category = "plan",
            defaultValue = ""
    )
    public String inString;

    @Option(
            name = "verbose",
            abbrev = 'v',
            help = "Enables logging\n",
            category = "plan",
            defaultValue = "false"
    )
    public boolean verbose;

    @Option(
            name = "logs",
            abbrev = 'l',
            help = "Write logs to specific directory\n",
            category = "plan",
            defaultValue = "Logs"
    )
    public String logs;

    @Option(
            name = "print-logs",
            help = "Print any logs to the command line (enables verbose by default)\n",
            category = "plan",
            defaultValue = "false"
    )
    public boolean printLogs;

    @Option(
            name = "execute",
            abbrev = 'x',
            help = "Simulate execution after planning (including perturbations)\n",
            category = "plan",
            defaultValue = "false"
    )
    public boolean execute;

    @Option(
            name = "plan-output",
            help = "Directory to write generated plans (planner) \n",
            category = "plan",
            defaultValue = "JSON_output/Plans"
    )
    public String planOutput;

    @Option(
            name = "states-output",
            help = "Directory to write simulated states (exec agent)\n",
            category = "plan",
            defaultValue = "JSON_output/SystemStates"
    )
    public String statesOutput;

    @Option(
            name = "stop",
            abbrev = 's',
            help = "Maximum runtime in seconds\n",
            category = "plan",
            defaultValue = "100"
    )
    public double stop;

    @Option(
            name = "clf",
            help = "If clf is enabled, the planner will not consider the stoptime until backtracking occurs\n",
            category = "plan",
            defaultValue = "true"
    )
    public boolean clf;

}


