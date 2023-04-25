package project.notizprogrammrepository.model.Types.Dates;

import org.junit.jupiter.api.Test;
import project.notizprogrammrepository.model.Types.entries.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CalendarTest {
    @Test
    public void constructorTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Calendar c = new Calendar(sdf.parse("04.05.2022"));
        assertEquals(4, c.getMonths().get(0).getMonthNumber());
        assertEquals(5, c.getMonths().get(1).getMonthNumber());
        assertEquals(6, c.getMonths().get(2).getMonthNumber());
        assertEquals(7, c.getMonths().get(3).getMonthNumber());
        assertEquals(4, c.getMonths().size());
        c = new Calendar(sdf.parse("04.01.2022"));
        assertEquals(12, c.getMonths().get(0).getMonthNumber());
        assertEquals(2021, c.getMonths().get(0).getYear());
        c = new Calendar(sdf.parse("04.12.2022"));
        assertEquals(2, c.getMonths().get(3).getMonthNumber());
        assertEquals(2023, c.getMonths().get(3).getYear());
        assertEquals(1, c.getCurrentMonthIndex());
    }

    @Test
    public void shiftCalendarTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy");
        Calendar c = new Calendar(sdf.parse("04.05.2022"));
        c.shiftCalendar(false);
        assertEquals(1, c.getCurrentMonthIndex());
        assertEquals(4, c.getMonths().get(c.getCurrentMonthIndex()).getMonthNumber());
        assertEquals(4, c.getMonths().size());
        Note n = new Note("uagd", "tdfaawdf", sdf.parse("25.04.2022"),true);
        c.addEntry(n);
        c.shiftCalendar(true);
        c.shiftCalendar(true);
        assertEquals(2, c.getCurrentMonthIndex());
        assertEquals(6, c.getMonths().get(c.getCurrentMonthIndex()).getMonthNumber());
        assertEquals(5, c.getMonths().size());
    }

    @Test
    public void testGetEntry() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy");
        Calendar c = new Calendar(sdf.parse("04.05.2022"));
        c.shiftCalendar(false);
        Note n = new Note("uagd", "tdfaawdf", sdf.parse("25.04.2022"),true);
        c.addEntry(n);
        assertEquals(n, c.getEntry(n.getDate()));
        c.shiftCalendar(true);
        assertNull(c.getEntry(n.getDate()));
    }


}
