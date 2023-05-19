package project.notizprogrammrepository.model.Types.segments;

import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.NoteCollection;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.model.Types.entries.Note;
import project.notizprogrammrepository.model.Types.entries.Subject;

import java.util.*;

//19.04.2023 Fabian: Empty constructor, removeAll (remove all notes of a certain day), addEntry
//24.04.2023 Fabian: getCollection; editEntry; removeEntry (remove from collections); addEntry (add to collections); collectByTitle, collectBySubject, removeAll (remove from collections), removeFromCollections
//25.04.2023 Fabian: Updated all methods to use the calendar instead of entries and not to use id
/**
 * A container class used for storage of Notes, representing the note-segment of the application.
 */
public class NoteSegment extends CalendarSegment {
    /**
     * A TreeMap containing all NoteCollections sorted by title for faster access
     */
    private final TreeMap<String, NoteCollection> collections = new TreeMap<>();

    /**
     * Creates a new NoteSegment, creating a calendar with the given date.
     * @param date The date of creation of the calendar.
     */
    public NoteSegment(Date date){
        super(date);
    }

    /**
     * Removes all entries from the Day corresponding to the given date.
     * @param date The date on which day all entries are removed.
     * @param absolute A flag stating whether the entries will be removed from their collections.
     */
    public void removeAll(Date date, boolean absolute){
        Day day = getCalendar().getDay(date);
        if(absolute)
            for(Entry e:day.getEntries()){
                removeFromCollections((Note)e);
            }
        day.removeAll();
    }

    /**
     * Removes the given Note from the collection(s) it is in.
     * @param note The note to be removed.
     */
    private void removeFromCollections(Note note){
        NoteCollection c;
        if(note.isCollectByTitle()){
            c = getCollection(note.getTitle());
            c.remove(note);
            if(c.isEmpty()){
                collections.remove(c.getTitle());
            }
        }
        if(note.getSubject() != Subject.NONE){
            c = getCollection(note.getSubject());
            c.remove(note);
            if(c.isEmpty()){
                collections.remove(c.getTitle());
            }
        }
    }

    /**
     * Adds the given note to the collection corresponding to its subject. If there is no such collection, one is created.
     * @param note The note to be added to a collection by subject.
     */
    public void collectBySubject(Note note){
        if(collections.containsKey(note.getSubject().toString())){
            collections.get(note.getSubject().toString()).add(note);
        }else{
            NoteCollection nC = new NoteCollection(note.getSubject());
            nC.add(note);
            collections.put(nC.getTitle(), nC);
        }
    }
    /**
     * Adds the given note to the collection corresponding to its title. If there is no such collection, one is created.
     * @param note The note to be added to a collection by title.
     */
    public void collectByTitle(Note note){
        if(collections.containsKey(note.getTitle())){
            collections.get(note.getTitle()).add(note);
        }else{
            NoteCollection nC = new NoteCollection(note.getTitle());
            nC.add(note);
            collections.put(nC.getTitle(), nC);
        }
    }

    /**
     * Deletes a collection, disabling collectByTitle ond setting its subject to NONE.
     * @param title The title of the collection to be removed.
     */
    public void deleteCollection(String title){
        NoteCollection noteCollection = collections.get(title);
        for(Note n: noteCollection.getNotes().values()){
            removeFromCollections(n);
            n.setCollectByTitle(false);
            n.setSubject(Subject.NONE);
        }
        collections.remove(title);
    }

    /**
     * Returns the collection corresponding to the given title.
     * @param title The title of the collection.
     * @return The collection corresponding to the given title.
     */
    public NoteCollection getCollection(String title){
        return collections.get(title);
    }
    /**
     * Returns the collection corresponding to the given subject.
     * @param subject The subject of the collection.
     * @return The collection corresponding to the given subject.
     */
    public NoteCollection getCollection(Subject subject){
        return collections.get(subject.toString());
    }

    /**
     * Returns the titles of all collections.
     * @return An ArrayList of Strings containing the titles of all collections.
     */
    public ArrayList<String> getCollectionTitles(){
        return new ArrayList<>(collections.keySet());
    }

    /**
     * Adds the note to the calendar and any collections based on its values.
     * @param note The note to be added.
     */
    public void addNote(Note note) {
            this.getCalendar().addEntry(note);
            if(note.isCollectByTitle()){
                collectByTitle(note);
            }
            if(note.getSubject() != Subject.NONE){
                collectBySubject(note);
            }
    }

    /**
     * Removes the note from the calendar. Removes the note from its collections if the absolute flag is set to true.
     * @param note The note to be removed.
     * @param absolute The flag determining whether the note will be removed from collections. (ture = removed from collections)
     */
    public void removeNote(Note note, boolean absolute) {
        if(absolute)
            removeFromCollections(note);
        this.getCalendar().remove(note);
    }
    /**
     * Changes the properties of the Note in the calendar that has the same date as the given Note to the values of the given Note.
     * @param note The Note containing the date of the Note that is supposed to be changed and the new values for that Note.
     */
    public void editNote(Note note) {
        Note oldNote = this.getNote(note.getDate());
        if(oldNote == null)
            return;
        removeFromCollections(oldNote);
        oldNote.setTitle(note.getTitle());
        oldNote.setSubject(note.getSubject());
        oldNote.setCollectByTitle(note.isCollectByTitle());
        oldNote.setText(note.getText());
        if(oldNote.isCollectByTitle()){
            collectByTitle(oldNote);
        }
        if(oldNote.getSubject() != Subject.NONE){
            collectBySubject(oldNote);
        }
    }

    /**
     * Returns the Note represented by the given date.
     * @param date The date of the desired Note.
     * @return The Note represented by the given date.
     */
    public Note getNote(Date date) {
        return (Note)getCalendar().getEntry(date);
    }
}
