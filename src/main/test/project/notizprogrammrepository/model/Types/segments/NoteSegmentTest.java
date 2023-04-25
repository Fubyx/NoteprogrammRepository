package project.notizprogrammrepository.model.Types.segments;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.model.Types.entries.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

//19.04.2023 Fabian: Testing removeAll
//24.04.2023 Fabian: Testing collections, editEntry
class NoteSegmentTest {

    @Test
    public void removeAllTest() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        NoteSegment noteSegment = new NoteSegment(sdf.parse("04.04.2023 20:45:23"));
        Note n = new Note("tdtwd", "zgaezvaez", sdf.parse("20.04.2023 20:45:23"),  true);
        noteSegment.addNote(new Note("tdtwd", "zgaezvaez", sdf.parse("19.04.2023 20:45:23"),  true));
        noteSegment.addNote(new Note("tdtwd", "zgaezvaez", sdf.parse("19.04.2023 23:25:23"),  true));
        noteSegment.addNote(n);
        noteSegment.addNote(new Note("tdtwd", "zgaezvaez", sdf.parse("19.04.2023 00:00:00"),  true));

        noteSegment.removeAll(sdf.parse("19.04.2023 13:45:23"), true);

        assertEquals(n, noteSegment.getNote(n.getDate()));
        assertEquals("tdtwd\nzgaezvaez\n\n", noteSegment.getCollection(n.getTitle()).toString());
        noteSegment.deleteCollection(n.getTitle());
        assertNull(noteSegment.getCollection(n.getTitle()));

    }

    @Test
    public void generalTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

        NoteSegment noteSegment = new NoteSegment(sdf.parse("20.04.2023 20:45:23"));
        Note n = new Note("tdtwd", "zgaezvaez", sdf.parse("20.04.2023 20:45:23"),  true);
        noteSegment.addNote(new Note("tdtwd", "zgaezvez", sdf.parse("19.04.2023 20:45:23"),  true));
        noteSegment.addNote(new Note("tdtwd", "zgaezaez", sdf.parse("19.04.2023 23:25:23"),  true));
        noteSegment.addNote(n);
        noteSegment.addNote(new Note("tdtwd", "zgezvaez", sdf.parse("19.04.2023 00:00:00"),  true));

        assertEquals("tdtwd\nzgezvaez\n\nzgaezvez\n\nzgaezaez\n\nzgaezvaez\n\n", noteSegment.getCollection(n.getTitle()).toString());
        noteSegment.editNote(new Note("tgwfdtwfd", n.getText(), n.getDate(), n.isCollectByTitle()));
        assertEquals("tdtwd\nzgezvaez\n\nzgaezvez\n\nzgaezaez\n\n", noteSegment.getCollection("tdtwd").toString());
        assertEquals("tgwfdtwfd\nzgaezvaez\n\n", noteSegment.getCollection(n.getTitle()).toString());
        noteSegment.removeNote(n, true);
        assertNull(noteSegment.getCollection(n.getTitle()));

        Set<String> temp = new TreeSet<>();
        temp.add("tdtwd");
        assertEquals(temp, noteSegment.getCollectionTitles());

    }
    @Test
    public void shift_and_switchViewTest() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

        NoteSegment noteSegment = new NoteSegment(sdf.parse("20.04.2023 20:45:23"));

        Day []week = noteSegment.getCalendar().getWeek(2);

        noteSegment.switchView();
        noteSegment.shiftView(true);
        assertEquals(week, noteSegment.getCurrentWeek());
        noteSegment.shiftView(true);
        noteSegment.switchView();
        noteSegment.switchView();
        assertNotEquals(week, noteSegment.getCurrentWeek());
        noteSegment.shiftView(true);
        assertEquals(week, noteSegment.getCurrentWeek());
        assertEquals(4, noteSegment.getMonth().getMonthNumber());
        noteSegment.shiftView(false);
        noteSegment.shiftView(false);
        assertEquals(3, noteSegment.getMonth().getMonthNumber());
        noteSegment.shiftView(true);
        noteSegment.shiftView(true);
        assertEquals(4, noteSegment.getMonth().getMonthNumber());

    }

}