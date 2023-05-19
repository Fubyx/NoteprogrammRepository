package project.notizprogrammrepository.model.Types.entries;

import java.util.Date;

/*
Plan:
Add id
 */

/**
 * An Entry to a todolist.
 */
public class TodoEntry extends Entry {
    /**
     * The priority of the Entry ranging from 0 to ten where 0 is the top priority.
     */
    private int priority;//0 - 10
    /**
     * The id of the Entry used for comparison between 2 entries.
     */
    private long id = 0;

    /**
     * Creates a TodoEntry with the given title and sets its priority to 10.
     * @param title The title of the Entry.
     */
    public TodoEntry(String title) {
        super(title);
        this.priority = 10;
    }

    /**
     * Creates a TodoEntry with the given title and priority.
     * @param title The title of the Entry.
     * @param priority The priority of the Entry. (0 = highest; 10 = lowest)
     */
    public TodoEntry(String title, int priority) {
        super(title);
        setPriority(priority);
    }

    /**
     * Creates a TodoEntry with a title, and a dueDate and sets its priority to 10.
     * @param title The title of the Entry.
     * @param dueDate The date when the content of the Entry is due.
     */
    public TodoEntry(String title, Date dueDate) {
        super(title, dueDate);
        this.priority = 10;
    }

    /**
     * Creates a TodoEntry with a title, text, dueDate and priority.
     * @param title The title of the Entry.
     * @param text The text of the Entry.
     * @param dueDate The date when the content of the Entry is due.
     * @param priority The priority of the Entry. (0 = highest; 10 = lowest)
     */
    public TodoEntry(String title, String text, Date dueDate, int priority) {
        super(title, text, dueDate);
        setPriority(priority);
    }

    /**
     * Returns the priority of the Entry.
     * @return The priority of the Entry.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the priority to the given value or 0 if the given value is smaller than 0 or 10 if the given value is bigger than 10.
     * @param priority The new value for priority.
     */
    public void setPriority(int priority) {
        if(priority < 0)
            this.priority = 0;
        else this.priority = Math.min(priority, 10);
    }

    /**
     * Sets the id of the Entry to the given value.
     * @param id The new value for id.
     */
    public void setId(long id){
        if(this.id == 0){
            this.id = id;
        }
    }

    /**
     * Returns the id of the Entry.
     * @return The id of the Entry.
     */
    public long getId() {
        return id;
    }
}
