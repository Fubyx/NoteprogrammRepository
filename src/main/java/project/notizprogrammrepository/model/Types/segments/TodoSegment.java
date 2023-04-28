package project.notizprogrammrepository.model.Types.segments;

import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.model.Types.entries.Note;
import project.notizprogrammrepository.model.Types.entries.TodoEntry;

import java.util.*;

//28.04.2023 Fabian: editEntry
public class TodoSegment extends Segment{
    private int nextEmptyId = Integer.MIN_VALUE;
    private PriorityQueue<TodoEntry> entries =
            new PriorityQueue<>((todoEntry1, todoEntry2) -> Integer.compare(todoEntry1.getPriority(), todoEntry2.getPriority()));

    public void addEntry(Entry entry) {
        TodoEntry t = (TodoEntry)entry;
        t.setId(nextEmptyId);
        ++nextEmptyId;
        entries.add(t);
    }

    public void removeEntry(Entry entry) {
        entries.remove((TodoEntry) entry);
    }

    public void editEntry(Entry entry) {
        TodoEntry oldEntry = getEntry(((TodoEntry)entry).getId());
        if(oldEntry == null)
            return;
        oldEntry.setTitle(entry.getTitle());
        oldEntry.setText(entry.getText());
        oldEntry.setDate(entry.getDate());
        oldEntry.setPriority(((TodoEntry)entry).getPriority());
    }
    private TodoEntry getEntry(long id){
         Iterator<TodoEntry> it = entries.iterator();
         while (it.hasNext()){
             TodoEntry next = it.next();
             if(next.getDate().equals(id)){
                 return next;
             }
         }
         return null;
    }

    public PriorityQueue<TodoEntry> getEntries() {
        return entries;
    }
}
