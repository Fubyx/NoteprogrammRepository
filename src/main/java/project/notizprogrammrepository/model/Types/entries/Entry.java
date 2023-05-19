package project.notizprogrammrepository.model.Types.entries;

import java.io.Serializable;
import java.util.Date;

/**
 * Serves as a superclass for all types of entries.
 */
public abstract class Entry implements Serializable {
    /**
     * The title of the Entry.
     */
    private String title;
    /**
     * The text of the entry.
     */
    private String text;
    /**
     * The date where the Entry is stored for CalendarEntry and Note.
     * The dueDate for TodoEntry.
     */
    private Date date;

    /**
     * Creates an Entry with just a title.
     * @param title The title of the Entry.
     */
    public Entry(String title){
        this.title = title;
    }

    /**
     * Creates an Entry with a title and a date.
     * @param title The title of the Entry.
     * @param date The date of the Entry.
     */
    public Entry(String title, Date date) {
        this.title = title;
        this.date = date;
    }

    /**
     * Creates an Entry with a title, a text and a date.
     * @param title The title of the Entry.
     * @param text The text of the Entry.
     * @param date The date of the Entry.
     */
    public Entry(String title, String text, Date date) {
        this.title = title;
        this.text = text;
        this.date = date;
    }

    /**
     * Returns the title.
     * @return The title of the Entry.
     */
    public String getTitle() {
        return title;
    }
    /**
     * Sets the title.
     * @param title The new title of the Entry.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Returns the text.
     * @return The text of the Entry.
     */
    public String getText() {
        return text;
    }
    /**
     * Sets the text.
     * @param text The new text of the Entry.
     */
    public void setText(String text) {
        this.text = text;
    }
    /**
     * Returns the date.
     * @return The date of the Entry.
     */
    public Date getDate() {
        return date;
    }
    /**
     * Sets the date.
     * @param date The new date of the Entry.
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
