package project.notizprogrammrepository.model.Types.segments;

import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.NoteCollection;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.model.Types.entries.Note;
import project.notizprogrammrepository.model.Types.entries.Subject;

import java.io.Serializable;
import java.util.*;

//19.04.2023 Fabian: Empty constructor, removeAll (remove all notes of a certain day), addEntry
//24.04.2023 Fabian: getCollection; editEntry; removeEntry (remove from collections); addEntry (add to collections); collectByTitle, collectBySubject, removeAll (remove from collections), removeFromCollections
//25.04.2023 Fabian: Updated all methods to use the calendar instead of entries and not to use id
public class NoteSegment extends CalendarSegment implements Serializable {
    private final TreeMap<String, NoteCollection> collections = new TreeMap<>();
    public NoteSegment(Date date){
        super(date);
    }
    public void removeAll(Date date, boolean absolute){
        Day day = getCalendar().getDay(date);
        if(absolute)
            for(Entry e:day.getEntries()){
                removeFromCollections((Note)e);
            }
        day.removeAll();
    }
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
    public void collectBySubject(Note note){
        if(collections.containsKey(note.getSubject().toString())){
            collections.get(note.getSubject().toString()).add(note);
        }else{
            NoteCollection nC = new NoteCollection(note.getSubject());
            nC.add(note);
            collections.put(nC.getTitle(), nC);
        }
    }
    public void collectByTitle(Note note){
        if(collections.containsKey(note.getTitle())){
            collections.get(note.getTitle()).add(note);
        }else{
            NoteCollection nC = new NoteCollection(note.getTitle());
            nC.add(note);
            collections.put(nC.getTitle(), nC);
        }
    }
    public void deleteCollection(String title){
        NoteCollection noteCollection = collections.get(title);
        for(Note n: noteCollection.getNotes().values()){
            removeFromCollections(n);
            n.setCollectByTitle(false);
            n.setSubject(Subject.NONE);
        }
        collections.remove(title);
    }
    public NoteCollection getCollection(String title){
        return collections.get(title);
    }
    public NoteCollection getCollection(Subject subject){
        return collections.get(subject.toString());
    }
    public ArrayList<String> getCollectionTitles(){
        return new ArrayList<>(collections.keySet());
    }
    public void addNote(Note note) {
            this.getCalendar().addEntry(note);
            if(note.isCollectByTitle()){
                collectByTitle(note);
            }
            if(note.getSubject() != Subject.NONE){
                collectBySubject(note);
            }
    }
    public void removeNote(Note note, boolean absolute) {
        if(absolute)
            removeFromCollections(note);
        this.getCalendar().remove(note);
    }
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
    public Note getNote(Date date) {
        return (Note)getCalendar().getEntry(date);
    }
}
