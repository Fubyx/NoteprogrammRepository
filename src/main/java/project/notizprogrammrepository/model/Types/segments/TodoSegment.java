package project.notizprogrammrepository.model.Types.segments;

import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.model.Types.entries.Note;
import project.notizprogrammrepository.model.Types.entries.TodoEntry;

import java.util.*;

//28.04.2023 Fabian: add-, remove-, editEntry
public class TodoSegment extends Segment{
    private int nextEmptyId = Integer.MIN_VALUE;
    private ArrayList<TodoEntry> entries = new ArrayList<>();
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
        try {
            oldEntry.setTitle(entry.getTitle());
        }catch (NullPointerException n){}
        try {
            oldEntry.setText(entry.getText());
        }catch (NullPointerException n){}
        try {
            oldEntry.setDate(entry.getDate());
        }catch (NullPointerException n){}
        try {
            oldEntry.setPriority(((TodoEntry)entry).getPriority());
        }catch (NullPointerException n){}
    }
    private TodoEntry getEntry(long id){
         Iterator<TodoEntry> it = entries.iterator();
         while (it.hasNext()){
             TodoEntry next = it.next();
             if(next.getId() == id){
                 return next;
             }
         }
         return null;
    }
    public ArrayList<TodoEntry> getEntries() {
        entries.sort((todoEntry1, todoEntry2) -> Integer.compare(todoEntry1.getPriority(), todoEntry2.getPriority()));
        return entries;
    }
}
