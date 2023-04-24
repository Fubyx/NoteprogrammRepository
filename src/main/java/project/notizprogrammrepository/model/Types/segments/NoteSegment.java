package project.notizprogrammrepository.model.Types.segments;

import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.NoteCollection;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.model.Types.entries.Note;
import project.notizprogrammrepository.model.Types.entries.Subject;

import java.util.*;

//19.04.2023 Fabian: Empty constructor, removeAll (remove all notes of a certain day), addEntry
//24.04.2023 Fabian: getCollection; editEntry; removeEntry (remove from collections); addEntry (add to collections); collectByTitle, collectBySubject, removeAll (remove from collections), removeFromCollections
public class NoteSegment extends CalendarSegment{
    private TreeMap<String, NoteCollection> collections = new TreeMap<>();
    public NoteSegment(){
        this.setEntries(new ArrayList<>());
    }
    public void removeAll(Day day){
        Iterator<Entry> it = this.getEntries().listIterator();
        while (it.hasNext()){
            Note note = (Note)it.next();
            if(day.isOnSameDay(note.getDate())){
                it.remove();
                removeFromCollections(note);
            }
        }
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
    public NoteCollection getCollection(String title){
        return collections.get(title);
    }
    public NoteCollection getCollection(Subject subject){
        return collections.get(subject.toString());
    }

    @Override
    public void addEntry(Entry entry) {
        if(entry instanceof Note) {
            this.getEntries().add(entry);
            if(((Note) entry).isCollectByTitle()){
                collectByTitle((Note)entry);
            }
            if(((Note) entry).getSubject() != Subject.NONE){
                collectBySubject((Note)entry);
            }
        }
    }

    @Override
    public boolean removeEntry(Entry entry) {
        if(this.getEntries().contains(entry)){
            Note note = (Note)entry;
            removeFromCollections(note);
        }
        return this.getEntries().remove(entry);
    }

    @Override
    public void editEntry(Entry entry) {
        Note note = (Note)entry;
        int index = this.getEntries().indexOf(note);
        if(index != -1){
            Note oldNote = this.getNote(note.getId());
            if(oldNote == null)
                return;
            removeFromCollections(oldNote);

            oldNote.setTitle(note.getTitle());
            oldNote.setSubject(note.getSubject());
            oldNote.setCollectByTitle(note.isCollectByTitle());
            oldNote.setDate(note.getDate());
            oldNote.setText(note.getText());

            if(oldNote.isCollectByTitle()){
                collectByTitle(oldNote);
            }
            if(oldNote.getSubject() != Subject.NONE){
                collectBySubject(oldNote);
            }
        }

    }

    @Override
    public Note getNote(double id) {
        for(Entry n: this.getEntries()){
            if(n.getId() == id){
                return (Note)n;
            }
        }
        return null;
    }
}
