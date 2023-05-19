package project.notizprogrammrepository.model.Types;

import org.junit.jupiter.api.Test;
import project.notizprogrammrepository.model.Types.entries.Note;
import project.notizprogrammrepository.model.Types.entries.Subject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//19.04.2023 Fabian: Test for constructor, changing of title and subject, adding and getting notes
import static org.junit.jupiter.api.Assertions.*;
class NoteCollectionTest {
    @Test
    public void testNoteCollection(){
        //Testing the constructors
        NoteCollection n = new NoteCollection(Subject.ENGLISH);
        assertEquals(Subject.ENGLISH, n.getSubject());
        assertEquals("ENGLISH", n.getTitle());

        n = new NoteCollection("Test");
        assertEquals(Subject.NONE, n.getSubject());
        assertEquals("Test", n.getTitle());

        //Test adding a note and getting all Notes
        Note n1 = new Note("Test", "Hallo", new Date(),  true);
        n.add(n1);
        assertTrue(n.getNotes().containsValue(n1));
    }

    @Test
    public void testEditText() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/HH/mm/ss");
        Note n = new Note("Title", "Auto", sdf.parse("12/04/2023/10/00/00"), true);
        Note n1 = new Note("Title", "Tttest", sdf.parse("12/04/2023/11/00/00"), true);
        Note n2 = new Note("Title", "Alex", sdf.parse("12/04/2023/12/00/00"), true);
        assertEquals(-1, n.getDate().compareTo(n1.getDate()));
        assertEquals(-1, n1.getDate().compareTo(n2.getDate()));
        NoteCollection noteCollection = new NoteCollection("Title");
        noteCollection.add(n);
        assertEquals("Auto\n\t\n", noteCollection.getText());
        noteCollection.add(n1);
        assertEquals("Auto\n\t\nTttest\n\t\n", noteCollection.getText());
        noteCollection.add(n2);
        assertEquals("Auto\n\t\nTttest\n\t\nAlex\n\t\n", noteCollection.getText());
        noteCollection.editText("Hallo\n\t\nTttest\n\t\nAxel\n\t\n");
        assertEquals("Hallo\n\t\nTttest\n\t\nAxel\n\t\n", noteCollection.getText());
        assertEquals("Hallo", n.getText());
        assertEquals("Tttest", n1.getText());
        assertEquals("Axel", n2.getText());

    }
  
}