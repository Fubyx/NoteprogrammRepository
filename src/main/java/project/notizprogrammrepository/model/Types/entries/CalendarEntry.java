package project.notizprogrammrepository.model.Types.entries;

import java.util.Date;

//30.04.2023 Fabian: Add getter + setter + constructors for reminderDate
/**
 * An Entry to the calendar.
 */
public class CalendarEntry extends Entry {
    /**
     * The date at which the user wishes to receive a reminder about his Entry.
     */
    private Date reminderDate;
    /**
     * Creates a CalendarEntry with a title and a date.
     * @param title The title of the Entry.
     * @param date The date of the Entry.
     */
    public CalendarEntry(String title, Date date) {
        super(title, date);
        this.reminderDate = null;
    }
    /**
     * Creates an Entry with a title, a text and a date.
     * @param title The title of the Entry.
     * @param text The text of the Entry.
     * @param date The date of the Entry.
     */
    public CalendarEntry(String title, String text, Date date) {
        super(title, text, date);
        this.reminderDate = null;
    }

    /**
     * Creates an Entry with a title, a date, a text and a reminder.
     * @param title The title of the Entry.
     * @param text The text of the Entry.
     * @param date The date of the Entry.
     * @param reminderDate The date on which there will be a reminder for this Entry.
     */
    public CalendarEntry(String title, String text, Date date, Date reminderDate) {
        super(title, text, date);
        setReminderDate(reminderDate);
    }

    /**
     * Returns the date of the reminder.
     * @return The date of the reminder.
     */
    public Date getReminderDate() {
        return reminderDate;
    }

    /**
     * Changes the reminderDate attribute to the given date, if that date is after the current date.
     * @param reminderDate The new Value for reminderDate.
     */
    public void setReminderDate(Date reminderDate) {
        Date date = new Date();
        if(reminderDate != null && reminderDate.after(date))
            this.reminderDate = reminderDate;
        else
            this.reminderDate = null;
    }
}