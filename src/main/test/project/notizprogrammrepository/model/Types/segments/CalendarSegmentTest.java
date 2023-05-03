package project.notizprogrammrepository.model.Types.segments;

import org.junit.jupiter.api.Test;
import project.notizprogrammrepository.model.Types.entries.CalendarEntry;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

//28.04.2023 Miguel: Testing addCalenderEntry, Testing removeCalendarEntry, Testing editCalendarEntry
public class CalendarSegmentTest {

    @Test
    public void addCalendarEntryTest(){
        String title = "Title";
        Date date = new Date();
        CalendarEntry calendarEntry = new CalendarEntry(title, date);
        CalendarSegment calendarSegment = new CalendarSegment(date);
        calendarSegment.addCalendarEntry(calendarEntry);

        assertEquals(calendarEntry, calendarSegment.getCalendarEntry(date));
    }

    @Test
    public void removeCalendarEntryTest(){
        String title = "TestName";
        Date date = new Date();
        CalendarEntry calendarEntry = new CalendarEntry(title, date);
        CalendarSegment calendarSegment = new CalendarSegment(date);
        calendarSegment.addCalendarEntry(calendarEntry);

        calendarSegment.removeCalendarEntry(calendarEntry);
        assertNull(calendarSegment.getCalendarEntry(date));
    }

    @Test
    public void editCalenderEntryTest(){
        String title = "titel1";
        Date date = new Date();
        CalendarEntry oldcalendarEntry = new CalendarEntry(title, date);

        CalendarSegment calendarSegment = new CalendarSegment(date);
        calendarSegment.addCalendarEntry(oldcalendarEntry);

        String newtitle = "title2";
        CalendarEntry newcalendarEntry = new CalendarEntry(newtitle, date);

        calendarSegment.editCalendarEntry(newcalendarEntry);

        assertEquals(newcalendarEntry.getTitle(), calendarSegment.getCalendarEntry(date).getTitle());
        assertEquals(newcalendarEntry.getDate(), calendarSegment.getCalendarEntry(date).getDate());

    }
    //03.05.2023 Miguel: throwInfoTest
    @Test
    public void throwInfoTest(){
        Date date = new Date();
        CalendarSegment cs = new CalendarSegment(date);
        cs.throwInfo();
    }
}

