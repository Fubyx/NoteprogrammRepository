package project.notizprogrammrepository.model.Types.entries;

import java.util.Date;

//17.04.2023 Fabian: Override equals
//24.04.2023 Fabian: Fix to constructor
//25.04.2023 Fabian: removed id, changed equals to compare date

/**
 * A Note of information, bound to a specific date on a calendar.
 */
public class Note extends Entry {
    /**
     * The school-subject for which the note was written
     */
    private Subject subject;
    /**
     * A property defining whether the note will be added to the NoteCollection of the same title.
     */
    private boolean collectByTitle;

    /**
     * Creates A Note with title, text, date and the collection-property.
     * @param title The title of the Note.
     * @param text The text of the Note.
     * @param date The date on which the Note is saved.
     * @param collectByTitle The property defining whether the note will be added to the NoteCollection of the same title.
     */
    public Note(String title, String text, Date date, boolean collectByTitle) {
        super(title, text, date);
        this.subject = Subject.NONE;
        this.collectByTitle = collectByTitle;
    }
    /**
     * Creates A Note with title, text, date, subject and the collection-property.
     * @param title The title of the Note.
     * @param text The text of the Note.
     * @param date The date on which the Note is saved.
     * @param collectByTitle The property defining whether the note will be added to the NoteCollection of the same title.
     * @param subject The subject by which the Note will be collected.
     */
    public Note(String title, String text, Date date, boolean collectByTitle, Subject subject) {
        super(title, text, date);
        this.subject = subject;
        this.collectByTitle = collectByTitle;
    }

    /**
     * Returns the subject of the Note.
     * @return The subject of the Note.
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Changes the Subject of the Note to the given value.
     * @param subject The new value for subject.
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /**
     * Returns whether the collectByTitle-property is active.
     * @return true if the Note is collected.
     */
    public boolean isCollectByTitle() {
        return collectByTitle;
    }

    /**
     * Sets the collectByTitle-property to the given value.
     * @param collectByTitle The new value for the collectByTitle-property.
     */
    public void setCollectByTitle(boolean collectByTitle) {
        this.collectByTitle = collectByTitle;
    }
    /**
     * Compares the 2 Notes. Returns true only if the Date of obj matches the Date of the Note
     * @param obj The Note to compare to
     * @return Returns whether the Dates of the 2 Notes are equal.
     */
    @Override
    public boolean equals(Object obj){
        return ((obj instanceof Note) && (((Note) obj).getDate().equals(this.getDate())));
    }
}
