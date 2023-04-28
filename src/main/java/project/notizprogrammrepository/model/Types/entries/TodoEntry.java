package project.notizprogrammrepository.model.Types.entries;

import java.util.Date;

/*
Plan:
Add id
 */
public class TodoEntry extends Entry{
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
    public TodoEntry(String title, Date date) {
        super(title, date);
        this.priority = 10;
    }
    public TodoEntry(String title, Date date, int priority) {
        super(title, date);
        this.priority = priority;
    }

    public TodoEntry(String title, String text, Date date) {
        super(title, text, date);
        this.priority = 10;
    }
    public TodoEntry(String title, String text, Date date, int priority) {
        super(title, text, date);
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        if(priority < 0)
            this.priority = 0;
        else if(priority > 10)
            this.priority = 10;
        else
            this.priority = priority;
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
