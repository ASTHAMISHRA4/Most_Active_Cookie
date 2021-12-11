import com.opencsv.CSVReader;
import org.apache.commons.cli.*;
import utils.CommandUtils;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Takes filename and date as Input and searches the given file for cookies with given date.
 */
public class ActiveCookie {

    Options options;
    String optionFile = "filename";
    String optionDate = "date";
    String datePattern = "yyyy-MM-dd'T'HH:mm:ss'+'SS':'SS";

    /**
     * Converts String to LocalDate
     *
     * @param dateStr String to be converted.
     * @return Instance of LocalDate.
     */
    private LocalDate formatDate(String dateStr)
    {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(datePattern, Locale.ENGLISH);
        return LocalDate.parse(dateStr, inputFormatter);
    }

    /**
     * Filters log data and returns logs specific to given date.
     *
     * @param logs - Log String
     * @param date - Date
     * @return - Logs with given date.
     */
    private List<String[]> getLogsForDate(List<String[]> logs, LocalDate date)
    {
        if(logs == null || logs.isEmpty() || date == null) return null;
        List<String[]> currentCookies = new ArrayList<>();
        System.out.println("Date : "+date);

        /*
         * Binary search will be helpful if the file is large. It will then filter down the range
         * and optimize the run time.
         */
        int n = logs.size();
        int l = 1, r = n-1;
        while(l < r)
        {
            int mid = l + (r - l)/2;
            LocalDate currentDate = formatDate(logs.get(mid)[1]);
            if(currentDate.compareTo(date) > 0)
                l = mid + 1;
            else if(currentDate.compareTo(date) < 0)
                r = mid;
            else
                break;
        }

        //Filtering the logs with current date in range
        for(String[] arr : logs.subList(l,r+1))
        {
            if(formatDate(arr[1]).compareTo(date) == 0)
                currentCookies.add(arr);
        }

        return currentCookies;
    }

    /**
     * Returns the Cookies with max value (max repetitions)
     *
     * @param map - HashMap to be searched.
     * @return - List of Keys with max value.
     */
    private List<String> getMaxCokkies(Map<String, Integer> map)
    {
        if(map == null || map.isEmpty()) return null;
        List<String> activeKeys = new ArrayList<>();
        int maxValueInMap = (Collections.max(map.values()));
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                activeKeys.add(entry.getKey());
            }
        }
        return activeKeys;
    }

    /**
     * Search the logs to find and the most occurred cookie for given date.
     *
     * @param filename - Name of file which contains logs.
     * @param date - Date for which the logs are searched.
     */
    public List<String> searchLogs(String filename, LocalDate date)
    {
        Map<String, Integer> map = new HashMap<>();
        List<String[]> logs = null;
        try {
            logs = getLogsForDate(readFile(filename), date);
            if(logs == null) return null;
            for (String[] data : logs) {
                if (map.containsKey(data[0]))
                    map.put(data[0], map.get(data[0]) + 1);
                else
                    map.put(data[0], 1);
            }
           return getMaxCokkies(map);
        } catch (IOException e) {
            System.out.println("Invalid Filename. Please try again!");
            e.printStackTrace();
            return null;
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Invalid Date. Please try again!");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Reads a file and returns its line, if file is valid.
     * Otherwise throws Exception.
     *
     * @param filename File name
     * @return List<String[]> : List of lines of file.
     * @throws IOException if File is invalid.
     */
    public List<String[]> readFile(String filename) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream(filename)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
                try (CSVReader csvreader = new CSVReader(reader)) {
                    return csvreader.readAll();
                }
            }
        }
    }

    /**
     * Generates application command line options
     *
     * @return application <code>Options</code>
     */
    public Options getOptions() {

        options = new Options();

        options.addOption("f", optionFile, true, "file name to load data from");
        options.addOption("d", optionDate, true, "date with most active cookies");
        return options;
    }

    public static void main(String[] args) {
        ActiveCookie activeCookie = new ActiveCookie();
        CommandUtils commandUtils = new CommandUtils(activeCookie.getOptions());
        CommandLine line = commandUtils.parseArguments(args);

        String fileName = line.getOptionValue(activeCookie.optionFile);
        LocalDate date = LocalDate.parse(line.getOptionValue(activeCookie.optionDate));

        List<String> cookies = activeCookie.searchLogs(fileName, date);
        if(cookies == null)
            System.out.println("An error occured! Please check the error message and try again.");
        else
        {
            System.out.println("Most Active Cookies are :: ");
            for(String cookie : cookies)
                System.out.println(cookie);
        }

    }
}
