package project.notizprogrammrepository.model.Types.entries;

import java.util.Date;

/*
Plan:
Add id
 */
public class TodoEntry extends Entry {
    private int priority;//0 - 10
    private long id = 0;

    public TodoEntry(String title) {
        super(title);
        this.priority = 10;
    }
    public TodoEntry(String title, int priority) {
        super(title);
        this.priority = priority;
    }
    public TodoEntry(String title, Date dueDate) {
        super(title, dueDate);
        this.priority = 10;
    }
    public TodoEntry(String title, String text, Date dueDate, int priority) {
        super(title, text, dueDate);
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        if(priority < 0)
            this.priority = 0;
        else this.priority = Math.min(priority, 10);
    }

    public void setId(long id){
        if(this.id == 0){
            this.id = id;
        }
    }

    public long getId() {
        return id;
    }
}
