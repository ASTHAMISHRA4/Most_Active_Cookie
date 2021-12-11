package utils;

import org.apache.commons.cli.*;

/**
 * Contains Utility method to parse and validate command line arguments.
 */
public class CommandUtils {

    Options options;

    public CommandUtils(Options options) {
        this.options = options;
    }

    /**
     * Parses application arguments
     *
     * @param args application arguments
     * @return <code>CommandLine</code> which represents a list of application
     * arguments.
     */
    public CommandLine parseArguments(String[] args) {

        CommandLine line = null;
        CommandLineParser parser = new DefaultParser();
        try {
            line = parser.parse(options, args);
        } catch (Exception ex) {
            System.err.println("Failed to parse command line arguments");
            printAppHelp();
            System.exit(1);
        }
        validateArgs(line);
        return line;
    }

    /**
     * Prints application help
     */
    private void printAppHelp() {
        System.out.println("Please Enter Valid Arguments :");
        var formatter = new HelpFormatter();
        formatter.printHelp("JavaStatsEx", options, true);
    }

    /**
     * Validates and checks if all required command line options are present in input.
     * Stops program execution if a required argument is absent.
     *
     * @param commandLine - CommandLine instance to be validated.
     */
    private void validateArgs(CommandLine commandLine)
    {
        for(Option option : options.getOptions())
        {
            if(!commandLine.hasOption(option.getOpt()))
            {
                printAppHelp();
                System.exit(1);
            }
        }
    }
}
