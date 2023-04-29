package project.notizprogrammrepository.model.Types.segments;

import org.junit.jupiter.api.Test;
import project.notizprogrammrepository.model.Types.entries.TodoEntry;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TodoSegmentTest {

    @Test
    public void addRemoveTest(){
        TodoSegment todoSegment = new TodoSegment();
        TodoEntry t = new TodoEntry("TestTitle");
        assertEquals(0, todoSegment.getEntries().size());
        todoSegment.addEntry(t);
        assertEquals(t, todoSegment.getEntries().get(0));
        assertEquals("TestTitle", todoSegment.getEntries().get(0).getTitle());
        TodoEntry newT = new TodoEntry("TTT");
        newT.setId(t.getId());
        todoSegment.editEntry(newT);
        assertEquals("TTT", todoSegment.getEntries().get(0).getTitle());
        todoSegment.removeEntry(t);
        assertEquals(0, todoSegment.getEntries().size());

        TodoEntry t1 = new TodoEntry("Hallo", 1);
        TodoEntry t2 = new TodoEntry("Hallllllooo", 3);
        TodoEntry t3 = new TodoEntry("tzzwfd", 0);

        todoSegment.addEntry(t1);
        todoSegment.addEntry(t2);
        assertEquals(t1, todoSegment.getEntries().get(0));

        todoSegment.addEntry(t3);
        assertEquals(t3, todoSegment.getEntries().get(0));
        Iterator<TodoEntry> it = todoSegment.getEntries().iterator();
        assertEquals(t3, it.next());
        assertEquals(t1, it.next());
        assertEquals(t2, it.next());


    }
}
