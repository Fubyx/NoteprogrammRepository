package project.notizprogrammrepository.model;

import org.junit.jupiter.api.Test;
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.Mode;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.model.Types.entries.Note;
import project.notizprogrammrepository.model.Types.entries.TodoEntry;
import project.notizprogrammrepository.model.Types.segments.CalendarSegment;
import project.notizprogrammrepository.model.Types.segments.NoteSegment;
import project.notizprogrammrepository.model.Types.segments.TodoSegment;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    @Test
    public void generalTest(){
        Application app = new Application();
        assertEquals(Mode.CALENDAR, app.getCurrentMode());
        app.switchMode(Mode.NOTE);
        assertEquals(Mode.NOTE, app.getCurrentMode());
        assertEquals(0, app.getTodoList().size());
        assertTrue(app.getSegment(app.getCurrentMode()) instanceof NoteSegment);
        assertTrue(app.getSegment(Mode.TODO) instanceof TodoSegment);
        assertNotNull(app.getWeek());
        assertNotNull(app.getMonth());
        assertNotEquals(((NoteSegment)app.getSegment(Mode.NOTE)).getCalendar(), ((CalendarSegment)app.getSegment(Mode.CALENDAR)).getCalendar());
    }

    @Test
    public void addEntryTest()  {
        Application app = new Application();
        app.addEntry(new TodoEntry("srtdfd", new Date()));
        assertEquals(1, app.getTodoList().size());
        Note n = new Note("stdfatwd", "§twfdtwdf", new Date(), true);
        app.addEntry(n);
        app.switchMode(Mode.NOTE);
        NoteSegment nS = (NoteSegment) app.getSegment(Mode.NOTE);
        assertEquals(n, nS.getNote(n.getDate()));
        assertEquals(n, nS.getCalendar().getEntry(n.getDate()));
        assertEquals(n, app.getMonth().getEntry(n.getDate()));
        Day [] days = app.getWeek();
        assertEquals(7, days.length);
        boolean contained = false;
        for (Day day : days) {
            if (day != null) {
                Entry e = day.getEntry(n.getDate());
                if (e == null || !e.equals(n))
                    continue;
                contained = true;
                break;
            }
        }
        assertFalse(contained);
    }
}
