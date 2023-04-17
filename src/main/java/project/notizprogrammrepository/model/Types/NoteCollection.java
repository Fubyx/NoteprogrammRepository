package project.notizprogrammrepository.model.Types;

import project.notizprogrammrepository.model.Types.entries.Note;
import project.notizprogrammrepository.model.Types.entries.Subject;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

//17.04.2023 Fabian: Sortierung nach Datum + Konstruktoren
public class NoteCollection {
    private TreeMap<Date, Note> notes = new TreeMap<>(new Comparator<Date>() {
        @Override
        public int compare(Date o1, Date o2) {
            return o1.compareTo(o2);
        }
    });;
    private String title;
    private Subject subject;
    public NoteCollection (String title){
        this.title = title;
        this.subject = Subject.NONE;
    }
    public NoteCollection (Subject subject){
        this.subject = subject;
        this.title = subject.toString();
    }
    public void add(Note note){
        notes.put(note.getDate(), note);
    }

}
