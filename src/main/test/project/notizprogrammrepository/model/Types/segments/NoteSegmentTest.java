package project.notizprogrammrepository.model.Types.segments;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.model.Types.entries.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

//19.04.2023 Fabian: Testing removeAll
//24.04.2023 Fabian: Testing collections, editEntry
class NoteSegmentTest {

    @Test
    public void removeAllTest() throws ParseException {
        NoteSegment noteSegment = new NoteSegment();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        Note n = new Note("tdtwd", "zgaezvaez", sdf.parse("20.04.2023 20:45:23"), 7, true);
        noteSegment.addEntry(new Note("tdtwd", "zgaezvaez", sdf.parse("19.04.2023 20:45:23"), 5, true));
        noteSegment.addEntry(new Note("tdtwd", "zgaezvaez", sdf.parse("19.04.2023 23:25:23"), 6, true));
        noteSegment.addEntry(n);
        noteSegment.addEntry(new Note("tdtwd", "zgaezvaez", sdf.parse("19.04.2023 00:00:00"), 8, true));

        assertEquals(4, noteSegment.getEntries().size());
        noteSegment.removeAll(new Day(sdf.parse("19.04.2023 13:45:23")));
        assertEquals(1, noteSegment.getEntries().size());
        assertEquals(n, noteSegment.getEntries().get(0));
        assertEquals("tdtwd\nzgaezvaez\n\n", noteSegment.getCollection(n.getTitle()).toString());

    }

    @Test
    public void generalTest() throws ParseException {
        NoteSegment noteSegment = new NoteSegment();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        Note n = new Note("tdtwd", "zgaezvaez", sdf.parse("20.04.2023 20:45:23"), 7, true);
        noteSegment.addEntry(new Note("tdtwd", "zgaezvez", sdf.parse("19.04.2023 20:45:23"), 5, true));
        noteSegment.addEntry(new Note("tdtwd", "zgaezaez", sdf.parse("19.04.2023 23:25:23"), 6, true));
        noteSegment.addEntry(n);
        noteSegment.addEntry(new Note("tdtwd", "zgezvaez", sdf.parse("19.04.2023 00:00:00"), 8, true));

        assertEquals("tdtwd\nzgezvaez\n\nzgaezvez\n\nzgaezaez\n\nzgaezvaez\n\n", noteSegment.getCollection(n.getTitle()).toString());
        noteSegment.editEntry(new Note("tgwfdtwfd", n.getText(), n.getDate(), n.getId(), n.isCollectByTitle()));
        assertEquals("tdtwd\nzgezvaez\n\nzgaezvez\n\nzgaezaez\n\n", noteSegment.getCollection("tdtwd").toString());
        assertEquals("tgwfdtwfd\nzgaezvaez\n\n", noteSegment.getCollection(n.getTitle()).toString());
        noteSegment.removeEntry(n);
        assertNull(noteSegment.getCollection(n.getTitle()));

    }

}