package project.notizprogrammrepository.model.Types.segments;

import org.junit.jupiter.api.Test;
import project.notizprogrammrepository.model.Types.entries.TodoEntry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TodoSegmentTest {

    @Test
    public void addRemoveTest(){
        TodoSegment todoSegment = new TodoSegment();
        TodoEntry t = new TodoEntry("TestTitle");
        assertNull(todoSegment.getEntries().peek());
        todoSegment.addEntry(t);
        assertEquals(t, todoSegment.getEntries().peek());
        assertEquals("TestTitle", todoSegment.getEntries().peek().getTitle());
        t.setTitle("TTT");
    }
}
