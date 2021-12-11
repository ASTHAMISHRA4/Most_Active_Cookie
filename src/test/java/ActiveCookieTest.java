import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

class ActiveCookieTest {

    @Test
    public void testEmptyFile()
    {
        ActiveCookie activeCookie = new ActiveCookie();
        LocalDate date = LocalDate.parse("2018-03-04");
        List<String> list = activeCookie.searchLogs("empty.csv", date);
        Assertions.assertNull(list);
    }

    @Test
    public void testValidFile()
    {
        String filename = "log.csv";
        ActiveCookie activeCookie = new ActiveCookie();
        LocalDate date = LocalDate.parse("2018-12-09");
        List<String> list = activeCookie.searchLogs(filename, date);
        Assertions.assertEquals(1, list.size());
       Assertions.assertEquals("AtY0laUfhglK3lC7", activeCookie.searchLogs(filename, date).get(0));
    }

    @Test
    public void testRepeatedVal()
    {
        String filename = "logrep.csv";
        ActiveCookie activeCookie = new ActiveCookie();
        LocalDate date = LocalDate.parse("2018-12-09");
        List<String> list = activeCookie.searchLogs(filename, date);
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals("AtY0laUfhglK3lC7", activeCookie.searchLogs(filename, date).get(0));
        Assertions.assertEquals("5UAVanZf6UtGyKVS", activeCookie.searchLogs(filename, date).get(1));
    }

    @Test
    public void testInvalidDate()
    {
        String filename = "logrep.csv";
        ActiveCookie activeCookie = new ActiveCookie();
        LocalDate date = LocalDate.parse("2018-12-01");
        List<String> list = activeCookie.searchLogs(filename, date);
        Assertions.assertNull(list);
    }

}