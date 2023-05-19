package project.notizprogrammrepository.model.Types;

import project.notizprogrammrepository.model.Types.Dates.DateComparator;
import project.notizprogrammrepository.model.Types.entries.Note;
import project.notizprogrammrepository.model.Types.entries.Subject;

import java.io.Serializable;
import java.util.*;

//17.04.2023 Fabian: Sorting by date + constructors
//18.04.2023 Fabian: Added changing to Title and Subject, remove, toString
//30.04.2023 Fabian: Added editText

/**
 * A NoteCollection is a collection of Note objects based on their title or their subject. The notes are sorted by date.
 */
public class NoteCollection implements Serializable {
    /**
     * A TreeMap containing all notes of the collection sorted by their date. TreeMap is used to ensure fast editing.
     */
    private final TreeMap<Date, Note> notes = new TreeMap<>(new DateComparator());
    /**
     * The title of the collection.
     */
    private String title;
    /**
     * The subject of the collection.
     */
    private Subject subject;

    /**
     * Creates a NoteCollection with the given title and sets its subject to NONE.
     * @param title The title of the collection.
     */
    public NoteCollection (String title){
        this.title = title;
        this.subject = Subject.NONE;
    }

    /**
     * Creates a NoteCollection with the given subject and sets its title to subject.toString().
     * @param subject The subject of the collection.
     */
    public NoteCollection (Subject subject){
        this.subject = subject;
        this.title = subject.toString();
    }

    /**
     * Returns the title of the collection.
     * @return The title of the collection.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the subject of the collection.
     * @return The subject of the collection.
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Adds the given Note to the collection.
     * @param note The Note to be added.
     */
    public void add(Note note){
        notes.put(note.getDate(), note);
    }

    /**
     * Returns the TreeMap containing all Notes of the collection.
     * @return The TreeMap containing all Notes of the collection.
     */
    public TreeMap<Date, Note> getNotes(){
        return notes;
    }

    /**
     * Removes the given Note from the collection.
     * @param note The note to be removed.
     */
    public void remove(Note note){
        notes.remove(note.getDate());
    }

    /**
     * Turns the collection into a string by putting the title followed by the text of every single note, separated by a newline a tabulator and a second newline.
     * @return The String containing all texts and the title.
     */
    @Override
    public String toString() {
        String s = title;
        s = s.concat("\n\t\n");
        ArrayList<Date> dates = new ArrayList<>(notes.keySet().stream().toList());
        for (Date d:dates) {
            s = s.concat(notes.get(d).getText() + "\n\t\n");
        }
        return s;
    }
    /**
     * Turns the collection into a string by putting text of every single note, separated by a newline a tabulator and a second newline.
     * @return The String containing all texts.
     */
    public String getText(){
        String s = "";
        ArrayList<Date> dates = new ArrayList<>(notes.keySet().stream().toList());
        for (Date d:dates) {
            s = s.concat(notes.get(d).getText() + "\n\t\n");
        }
        return s;
    }

    /**
     * Checks whether the collection has entries.
     * @return false only if no Note is in the collection.
     */
    public boolean isEmpty(){
        return notes.isEmpty();
    }

    /**
     * Edits the texts of the contained notes so that it corresponds to the given text.
     * @param text The new text.
     */
    public void editText(String text){
        String [] partsNew = text.split("\n\t\n");
        String [] partsOld = this.getText().split("\n\t\n");
        ArrayList<Date> set = new ArrayList<>(notes.keySet().stream().toList());
        for(int i = 0; i < partsOld.length; ++i){
            if(!partsOld[i].equals(partsNew[i])){
                notes.get(set.get(i)).setText(partsNew[i]);
            }
        }

    }
}
