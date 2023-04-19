package project.notizprogrammrepository.model.Types.segments;

import org.junit.jupiter.api.Test;
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.model.Types.entries.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class NoteSegmentTest {

    @Test
    public void removeAllTest() throws ParseException {
        NoteSegment noteSegment = new NoteSegment();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        Note n = new Note("tdtwd", "zgaezvaez", sdf.parse("20.04.2023 20:45:23"), 7);
        noteSegment.addEntry(new Note("tdtwd", "zgaezvaez", sdf.parse("19.04.2023 20:45:23"), 5));
        noteSegment.addEntry(new Note("tdtwd", "zgaezvaez", sdf.parse("19.04.2023 23:25:23"), 6));
        noteSegment.addEntry(n);
        noteSegment.addEntry(new Note("tdtwd", "zgaezvaez", sdf.parse("19.04.2023 00:00:00"), 8));

        assertEquals(4, noteSegment.getEntries().size());
        noteSegment.removeAll(new Day(sdf.parse("19.04.2023 13:45:23")));
        assertEquals(1, noteSegment.getEntries().size());
        assertEquals(n, noteSegment.getEntries().get(0));
    }

}