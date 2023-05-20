package project.notizprogrammrepository.model.Types.segments;

import project.notizprogrammrepository.model.Types.entries.TodoEntry;

import java.util.*;

//28.04.2023 Fabian: add-, remove-, editEntry
/**
 * A container class used for storage of TodoEntries, representing the todo_-segment of the application.
 */
public class TodoSegment extends Segment {
    /**
     * An integer starting at the minimum value representing the id of the next TodoEntry that is created.
     */
    private int nextEmptyId = Integer.MIN_VALUE;
    /**
     * A List of all entries in the todoList.
     */
    private final ArrayList<TodoEntry> entries = new ArrayList<>();

    /**
     * Adds the given Entry to the todoList, setting its id to the value of nextEmptyId and increasing nextEmptyId by 1.
     * @param entry The TodoEntry to be added.
     */
    public void addEntry(TodoEntry entry) {
        entry.setId(nextEmptyId);
        ++nextEmptyId;
        entries.add(entry);
    }

    /**
     * Removes the given TodoEntry from the todoList.
     * @param entry The TodoEntry to be removed.
     */
    public void removeEntry(TodoEntry entry) {
        entries.remove(entry);
    }

    /**
     * Changes the properties of the TodoEntry in the calendar that has the same id as the given TodoEntry to the values of the given TodoEntry.
     * @param entry The TodoEntry containing the id of the TodoEntry that is supposed to be changed and the new values for that TodoEntry.
     */
    public void editEntry(TodoEntry entry) {
        TodoEntry oldEntry = getEntry(entry.getId());
        if(oldEntry == null)
            return;
        try {
            oldEntry.setTitle(entry.getTitle());
        }catch (NullPointerException ignored){}
        try {
            oldEntry.setText(entry.getText());
        }catch (NullPointerException ignored){}
        try {
            oldEntry.setDate(entry.getDate());
        }catch (NullPointerException ignored){}
        try {
            oldEntry.setPriority(entry.getPriority());
        }catch (NullPointerException ignored){}
    }

    /**
     * Returns the Entry with the given id or null if there is none.
     * @param id The id of the desired Entry.
     * @return The Entry with the given id or null if there is none.
     */
    private TodoEntry getEntry(long id){
        for (TodoEntry next : entries) {
            if (next.getId() == id) {
                return next;
            }
        }
         return null;
    }

    /**
     * Sorts the todoList by priority and returns it.
     * @return An ArrayList of TodoEntries containing all entries to the todoList.
     */
    public ArrayList<TodoEntry> getEntries() {
        entries.sort(Comparator.comparingInt(TodoEntry::getPriority));
        return entries;
    }
}
