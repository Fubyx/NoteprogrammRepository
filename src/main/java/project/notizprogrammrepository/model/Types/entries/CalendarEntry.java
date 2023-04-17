package project.notizprogrammrepository.model.Types.entries;

import java.util.Date;

public class CalendarEntry extends Entry{
    private Date reminderDate;

    public CalendarEntry(String title, Date date, double id) {
        super(title, date, id);
    }

    public CalendarEntry(String title, String text, Date date, double id) {
        super(title, text, date, id);
    }
}
