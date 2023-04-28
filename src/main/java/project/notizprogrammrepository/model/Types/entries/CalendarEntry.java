package project.notizprogrammrepository.model.Types.entries;

import java.util.Date;

public class CalendarEntry extends Entry{
    private Date reminderDate;

    public CalendarEntry(String title, Date date) {
        super(title, date);
    }

    public CalendarEntry(String title, String text, Date date) {
        super(title, text, date);
    }
}