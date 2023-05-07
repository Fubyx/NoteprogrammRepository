package project.notizprogrammrepository.model.Types;

import project.notizprogrammrepository.model.Types.entries.Note;
import project.notizprogrammrepository.model.Types.entries.Subject;

import java.io.Serializable;
import java.util.*;

//17.04.2023 Fabian: Sorting by date + constructors
//18.04.2023 Fabian: Added changing to Title and Subject, remove, toString
//30.04.2023 Fabian: Added editText
public class NoteCollection implements Serializable {
    private TreeMap<Date, Note> notes = new TreeMap<>(new Comparator<Date>() {
        @Override
        public int compare(Date o1, Date o2) {
            return o1.compareTo(o2);
        }
    });
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
        s = s.concat("\n\n");
        ArrayList<Date> dates = new ArrayList<>(notes.keySet().stream().toList());
        for (Date d:dates) {
            s = s.concat(notes.get(d).getText() + "\n\n");
        }
        return s;
    }
    public String getText(){
        String s = "";
        ArrayList<Date> dates = new ArrayList<>(notes.keySet().stream().toList());
        for (Date d:dates) {
            s = s.concat(notes.get(d).getText() + "\n\n");
        }
        return s;
    }
    public boolean isEmpty(){
        return notes.isEmpty();
    }

    public void editText(String text){
        String [] partsNew = text.split("\n\n");
        String [] partsOld = this.getText().split("\n\n");
        ArrayList<Date> set = new ArrayList<>(notes.keySet().stream().toList());
        for(int i = 0; i < partsOld.length; ++i){
            if(!partsOld[i].equals(partsNew[i])){
                notes.get(set.get(i)).setText(partsNew[i]);
            }
        }

    }
}
