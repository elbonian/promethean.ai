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
            help = "JSON input file for planning system",
            category = "inputs",
            defaultValue = ""
    )
    public String inFile;

    @Option(
            name = "inString",
            help = "JSON string input for planning system",
            category = "inputs",
            defaultValue = ""
    )
    public String inString;

    @Option(
            name = "numTasks",
            help = "Number of tasks to generate for test case",
            category = "inputs",
            defaultValue = "-1"
    )
    public int numTasks;

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
            defaultValue = ""
    )
    public String logs;

    @Option(
            name = "output",
            abbrev = 'o',
            help = "directory to write outputs",
            category = "directs",
            defaultValue = ""
    )
    public String output;

}
