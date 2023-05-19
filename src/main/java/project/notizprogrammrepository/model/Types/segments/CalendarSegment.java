package project.notizprogrammrepository.model.Types.segments;

import project.notizprogrammrepository.model.Types.Dates.Calendar;
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.Dates.Month;
import project.notizprogrammrepository.model.Types.entries.CalendarEntry;

import java.awt.*;
import java.awt.TrayIcon.MessageType;

import java.util.Date;

//25.04.2023 Fabian: calendar, weekViewActive, currentWeek, constructor, getCalendar, shiftView, switchView, getCurrentWeek, getMonth
//29.04.2023 Fabian: set default value of currentWeek fixing an error with getCurrentWeek
//26.04.2023 Miguel: removeCalendarEntry ( remove the Entry from Calendar), addCalendarEntry ( add an Entry to Calendar ) , editCalendarEntry ( edit an Entry in Calendar )
//30.04.2023 Fabian: added reminderDate to editCalendarEntry

/**
 * A container class used for storage of CalendarEntries, representing the calendar-segment of the application.
 */
public class CalendarSegment extends Segment {
    /**
     * The calendar corresponding to the segment.
     */
    private final Calendar calendar;
    /**
     * A property stating whether the whole month or just a week is currently displayed.
     */
    private boolean weekViewActive = false;
    /**
     * The week of the current month that is currently being displayed. default = 1.
     */
    private int currentWeek = 1;

    /**
     * Creates a new CalendarSegment with a Calendar corresponding to the given date.
     * @param date The date of the creation of the Calendar.
     */
    public CalendarSegment (Date date){
        calendar = new Calendar(date);
    }

    /**
     * Returns the calendar.
     * @return The calendar.
     */
    public Calendar getCalendar() {
        return calendar;
    }

    //switches 1 month to the left or right

    /**
     * Switches one week or month forward or backwards in time. Week or month depends on the weekViewActive-property.
     * Forward is represented by the rightShift parameter being true.
     * Backwards is represented by the rightShift parameter being false
     * @param rightShift Represents the direction of the shift. Forward in time = true; backwards in time = false
     */
    public void shiftView(boolean rightShift){
        if(weekViewActive){
            if(rightShift){
                ++currentWeek;
            }else{
                --currentWeek;
            }
            Day[] daysOfWeek = calendar.getWeek(currentWeek);
            if(daysOfWeek == null){
                calendar.shiftCalendar(rightShift);
                if(rightShift){
                    currentWeek = 1;
                }else{
                    currentWeek = 6;
                    while(calendar.getWeek(currentWeek) == null){
                        --currentWeek;
                    }
                }
            }
        }else{
            calendar.shiftCalendar(rightShift);
        }
    }
    //switches between weekView and monthView

    /**
     * Switches between weekView and monthView and resets currentWeek to 1.
     */
    public void switchView(){
        weekViewActive = !weekViewActive;
        currentWeek = 1;
    }

    /**
     * Returns a Day-array representing the currently active week.
     * @return The current week as an array of Day objects.
     */
    public Day[]getCurrentWeek(){
        return calendar.getWeek(currentWeek);
    }

    /**
     * Returns a Month object representing the currently active month.
     * @return The current month.
     */
    public Month getMonth(){
        return calendar.getMonth();
    }

    /**
     * Adds the given Entry to the calendar.
     * @param calendarEntry The Entry to be added.
     */
    public void addCalendarEntry(CalendarEntry calendarEntry){
        this.getCalendar().addEntry(calendarEntry);
    }

    /**
     * Removes the given Entry from the calendar.
     * @param calendarEntry The Entry to be removed.
            */
    public void removeCalendarEntry(CalendarEntry calendarEntry){
        this.getCalendar().remove(calendarEntry);
    }

    /**
     * Changes the properties of the Entry in the calendar that has the same date as the given Entry to the values of the given Entry.
     * @param calendarEntry The Entry containing the date of the Entry that is supposed to be changed and the new values for that Entry.
     */
    public void editCalendarEntry(CalendarEntry calendarEntry){
        CalendarEntry oldcalendarEntry = this.getCalendarEntry(calendarEntry.getDate());
        if(oldcalendarEntry == null)
            return;
        oldcalendarEntry.setTitle(calendarEntry.getTitle());
        oldcalendarEntry.setText(calendarEntry.getText());
        oldcalendarEntry.setReminderDate(calendarEntry.getReminderDate());
    }

    /**
     * Returns th CalendarEntry corresponding to the given date.
     * @param date The date of the Entry.
     * @return The Entry corresponding to the given date.
     */
    public CalendarEntry getCalendarEntry(Date date){
        return (CalendarEntry)getCalendar().getEntry(date);
    }

    // 03.05.2023 Miguel: throwInfo -> displays a Windows-Notification --> unfinished
    /*
    public void throwInfo(){
        try{
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().createImage("some-icon.png");

            TrayIcon trayIcon = new TrayIcon(image, "Java AWT Tray Demo");
            trayIcon.setImageAutoSize(true);        // let`s the system resize the image if needed
            trayIcon.setToolTip("System tray icon demo");
            tray.add(trayIcon);

            // Display info notification:
            trayIcon.displayMessage("Hello, World", "Java Notification Demo", MessageType.INFO);
            // Error:
            // trayIcon.displayMessage("Hello, World", "Java Notification Demo", MessageType.ERROR);
            // Warning:
            // trayIcon.displayMessage("Hello, World", "Java Notification Demo", MessageType.WARNING);
        }catch(Exception ex){
            System.err.print(ex);
        }
    }//*/

    /**
     * Returns whether the weekView is currently active.
     * @return Whether the weekView is currently active.
     */
    public boolean isWeekViewActive() {
        return weekViewActive;
    }
}
