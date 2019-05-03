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
            help = "JSON input file for planning system",
            category = "inputs",
            defaultValue = ""
    )
    public String inFile;

    @Option(
            name = "in-string",
            help = "JSON string input for planning system",
            category = "inputs",
            defaultValue = ""
    )
    public String inString;

    @Option(
            name = "verbose",
            abbrev = 'v',
            help = "enable logging",
            category = "modifiers",
            defaultValue = "false"
    )
    public boolean verbose;

    @Option(
            name = "logs",
            abbrev = 'l',
            help = "write logs to specific file",
            category = "directs",
            defaultValue = "Logs"
    )
    public String logs;

    @Option(
            name = "print-logs",
            help = "print any logs to the command line (enables verbose by default)",
            category = "modifiers",
            defaultValue = "false"
    )
    public boolean printLogs;

    @Option(
            name = "execute",
            abbrev = 'x',
            help = "simulate execution after planning",
            category = "modifiers",
            defaultValue = "false"
    )
    public boolean execute;

    @Option(
            name = "plan-output",
            help = "directory to write generated plans (planner)",
            category = "directs",
            defaultValue = "JSON_output/Plans"
    )
    public String planOutput;

    @Option(
            name = "states-output",
            help = "directory to write simulated states (exec agent)",
            category = "directs",
            defaultValue = "JSON_output/SystemStates"
    )
    public String statesOutput;

    @Option(
            name = "stop",
            abbrev = 's',
            help = "maximum runtime in seconds (defaults to inf)",
            category = "modifiers",
            defaultValue = "100"
    )
    public double stop;

    @Option(
            name = "clf",
            help = "if clf is enabled, the planner will not consider the stoptime until backtracking occurs",
            category = "modifiers",
            defaultValue = "true"
    )
    public boolean clf;

}


