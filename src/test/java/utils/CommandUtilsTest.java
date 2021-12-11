package utils;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandUtilsTest {

    CommandUtils commandUtils = null;

    @BeforeEach
    void setUp() {
        commandUtils = new CommandUtils(createOptions());
    }

    @Test
    public void testValidArgs()
    {
        String[] arr = new String[4];
        arr[0] = "-f";
        arr[1] = "/ff.csv";
        arr[2] = "-d";
        arr[3] = "2018-01-01";
        CommandLine args = commandUtils.parseArguments(arr);
        assertNotNull(args);
    }

    private Options createOptions()
    {
        Options options = new Options();

        options.addOption("f", "filename", true, "file name to load data from");
        options.addOption("d", "date", true, "date with most active cookies");
        return options;
    }
}