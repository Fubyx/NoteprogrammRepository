package project.notizprogrammrepository.model.Types.segments;

import project.notizprogrammrepository.model.Types.Dates.Calendar;
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.Dates.Month;
import project.notizprogrammrepository.model.Types.entries.CalendarEntry;

import java.util.Date;

//25.04.2023 Fabian: calendar, weekViewActive, currentWeek, constructor, getCalendar, shiftView, switchView, getCurrentWeek, getMonth
//29.04.2023 Fabian: set default value of currentWeek fixing an error with getCurrentWeek
//26.04.2023 Miguel: removeCalendarEntry ( remove the Entry from Calendar), addCalendarEntry ( add an Entry to Calendar ) , editCalendarEntry ( edit an Entry in Calendar )
//30.04.2023 Fabian: added reminderDate to editCalendarEntry
public class CalendarSegment extends Segment{
    private Calendar calendar;
    private boolean weekViewActive = false;
    private int currentWeek;
    public CalendarSegment (Date date){
        calendar = new Calendar(date);
        currentWeek = 1;
    }
    public Calendar getCalendar() {
        return calendar;
    }

    //switches 1 month to the left or right
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
    public void switchView(){
        weekViewActive = !weekViewActive;
        currentWeek = 1;
    }
    public Day[]getCurrentWeek(){
        return calendar.getWeek(currentWeek);
    }
    public Month getMonth(){
        return calendar.getMonth();
    }

    public void addCalendarEntry(CalendarEntry calendarEntry){
        this.getCalendar().addEntry(calendarEntry);
    }
    public void removeCalendarEntry(CalendarEntry calendarEntry){
        this.getCalendar().remove(calendarEntry);
    }
    public void editCalendarEntry(CalendarEntry calendarEntry){
        CalendarEntry oldcalendarEntry = this.getCalendarEntry(calendarEntry.getDate());
        if(oldcalendarEntry == null)
            return;
        oldcalendarEntry.setTitle(calendarEntry.getTitle());
        oldcalendarEntry.setText(calendarEntry.getText());
        oldcalendarEntry.setReminderDate(calendarEntry.getReminderDate());
    }
    public CalendarEntry getCalendarEntry(Date date){
        return (CalendarEntry)getCalendar().getEntry(date);
    }

}
