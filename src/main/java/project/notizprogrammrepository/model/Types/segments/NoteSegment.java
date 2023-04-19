package project.notizprogrammrepository.model.Types.segments;

import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.NoteCollection;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.model.Types.entries.Note;

import java.util.*;

//19.04.2023 Fabian: Empty constructor, removeAll, addEntry
public class NoteSegment extends CalendarSegment{
    private TreeMap<String, NoteCollection> collections;
    public NoteSegment(){
        this.setEntries(new ArrayList<>());
    }
    //TODO: remove from collections
    public void removeAll(Day day){
        Iterator<Entry> it = this.getEntries().listIterator();
        while (it.hasNext()){
            Note n = (Note)it.next();
            if(day.isOnSameDay(n.getDate())){
                it.remove();
            }
        }
    }
    public void collectBySubject(Note note){}
    public void collectByTitle(Note note){

    }

    @Override
    public void addEntry(Entry entry) {
        if(entry instanceof Note)
            this.getEntries().add(entry);
    }

    //TODO: remove from collections
    @Override
    public boolean removeEntry(Entry entry) {
        if(this.getEntries().contains(entry)){
            for(NoteCollection n: collections.values()){
                n.remove((Note)entry);
            }
        }
        return this.getEntries().remove(entry);
    }

    @Override
    public void editEntry(Entry entry) {
        Note note = (Note)entry;
        int index = this.getEntries().indexOf(note);
        if(index != -1){
            Note oldNote = this.getNote(note.getId());
            if(!oldNote.getTitle().equals(note.getTitle())){

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
