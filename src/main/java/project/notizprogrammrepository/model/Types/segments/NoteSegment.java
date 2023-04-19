package project.notizprogrammrepository.model.Types.segments;

import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.NoteCollection;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.model.Types.entries.Note;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//19.04.2023 Fabian: Empty constructor, removeAll, addEntry
public class NoteSegment extends CalendarSegment{
    private List<NoteCollection> collections;
    public NoteSegment(){
        this.setEntries(new ArrayList<>());
    }
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

    @Override
    public boolean removeEntry(Entry entry) {
        if(this.getEntries().contains(entry)){
            for(NoteCollection n: collections){
                n.remove((Note)entry);
            }
        }
        return this.getEntries().remove(entry);
    }

    @Override
    public void editEntry(Entry entry) {
        /*
        Note note = (Note)entry;
        int index = this.getEntries().indexOf(note);
        if(index != -1){

        }
        */
    }

    @Override
    public Note getNote(int id) {
        return null;
    }
}
