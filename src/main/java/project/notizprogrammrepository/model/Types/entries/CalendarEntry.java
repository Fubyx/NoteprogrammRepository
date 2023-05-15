package project.notizprogrammrepository.model.Types.entries;

import java.io.Serializable;
import java.util.Date;

//30.04.2023 Fabian: Add getter + setter + constructors for reminderDate

public class CalendarEntry extends Entry implements Serializable {
    private Date reminderDate;

    public CalendarEntry(String title, Date date) {
        super(title, date);
        this.reminderDate = null;
    }

    public CalendarEntry(String title, String text, Date date) {
        super(title, text, date);
        this.reminderDate = null;
    }
    public CalendarEntry(String title, Date date, Date reminderDate) {
        super(title, date);
        setReminderDate(reminderDate);
    }

    public CalendarEntry(String title, String text, Date date, Date reminderDate) {
        super(title, text, date);
        setReminderDate(reminderDate);
    }

    public Date getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(Date reminderDate) {
        Date date = new Date();
        if(reminderDate != null && reminderDate.after(date))
            this.reminderDate = reminderDate;
        else
            this.reminderDate = null;
    }
}