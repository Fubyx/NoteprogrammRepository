package project.notizprogrammrepository.model.Types.entries;

import java.util.Date;

public class TodoEntry extends Entry{
    private int priority;//0 - 10

    public TodoEntry(String title, double id) {
        super(title, id);
    }

    public TodoEntry(String title, Date date, double id) {
        super(title, date, id);
    }

    public TodoEntry(String title, String text, Date date, double id) {
        super(title, text, date, id);
    }
}
