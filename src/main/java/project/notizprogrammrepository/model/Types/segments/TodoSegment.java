package project.notizprogrammrepository.model.Types.segments;

import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.model.Types.entries.Note;
import project.notizprogrammrepository.model.Types.entries.TodoEntry;

import java.util.List;

public class TodoSegment extends Segment{
    private List<TodoEntry> entries;

    public void addEntry(Entry entry) {

    }

    public boolean removeEntry(Entry entry) {
        return false;
    }

    public void editEntry(Entry entry) {

    }


}
