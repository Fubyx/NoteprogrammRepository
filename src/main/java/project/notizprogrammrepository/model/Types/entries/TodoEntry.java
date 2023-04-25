package project.notizprogrammrepository.model.Types.entries;

import java.util.Date;

public class TodoEntry extends Entry{
    private int priority;//0 - 10

    public TodoEntry(String title, Date date) {
        super(title, date);
    }

    public TodoEntry(String title, String text, Date date) {
        super(title, text, date);
    }
}
