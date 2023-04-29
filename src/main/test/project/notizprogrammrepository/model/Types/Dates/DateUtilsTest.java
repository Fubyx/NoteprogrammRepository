package project.notizprogrammrepository.model.Types.Dates;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateUtilsTest {
    @Test
    public void testGetDayOfWeek() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        Date d = sdf.parse("25.04.2023 11:25:30");
        assertEquals(2, DateUtils.getDayOfWeek(d));
    }
}
