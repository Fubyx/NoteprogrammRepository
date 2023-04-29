package project.notizprogrammrepository.model.Types;

import project.notizprogrammrepository.model.Types.entries.Note;
import project.notizprogrammrepository.model.Types.entries.Subject;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

//17.04.2023 Fabian: Sorting by date + constructors
//18.04.2023 Fabian: Added changing to Title and Subject, remove, toString
public class NoteCollection {
    private TreeMap<Date, Note> notes = new TreeMap<>(Date::compareTo);;
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

    public String getTitle() {
        return title;
    }

    public Subject getSubject() {
        return subject;
    }
    public void changeSubject(Subject newSubject){
        this.subject = newSubject;
        this.title = newSubject.toString();
    }

    public void changeTitle(String title){
        this.title = title;
        this.subject = Subject.NONE;
    }

    public void add(Note note){
        notes.put(note.getDate(), note);
    }
    public TreeMap<Date, Note> getNotes(){
        return notes;
    }
    public boolean remove(Note note){
        return notes.remove(note.getDate()) != null;
    }

    @Override
    public String toString() {
        String s = title;
        s = s.concat("\n");
        for (Date d:notes.keySet()) {
            s = s.concat(notes.get(d).getText() + "\n\n");
        }
        return s;
    }
    public boolean isEmpty(){
        return notes.isEmpty();
    }
}
