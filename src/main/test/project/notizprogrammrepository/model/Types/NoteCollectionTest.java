package project.notizprogrammrepository.model.Types;

import org.junit.jupiter.api.Test;
import project.notizprogrammrepository.model.Types.entries.Note;
import project.notizprogrammrepository.model.Types.entries.Subject;

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

        //testing the change methods
        n.changeSubject(Subject.GERMAN);
        assertEquals(Subject.GERMAN, n.getSubject());
        assertEquals("GERMAN", n.getTitle());

        n.changeTitle("New");
        assertEquals(Subject.NONE, n.getSubject());
        assertEquals("New", n.getTitle());

        //Test adding a note and getting all Notes
        Note n1 = new Note("Test", "Hallo", new Date(),  true);
        n.add(n1);
        assertTrue(n.getNotes().containsValue(n1));
    }
  
}