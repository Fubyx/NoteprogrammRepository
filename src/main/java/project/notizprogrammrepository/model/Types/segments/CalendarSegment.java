package project.notizprogrammrepository.model.Types.segments;

import project.notizprogrammrepository.model.Types.Dates.Calendar;
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.Dates.Month;
import project.notizprogrammrepository.model.Types.entries.CalendarEntry;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.model.Types.entries.Note;

import java.util.Date;
import java.util.List;

//25.04.2023 Fabian: calendar, weekViewActive, currentWeek, constructor, getCalendar, shiftView, switchView, getCurrentWeek, getMonth
public class CalendarSegment extends Segment{
    private Calendar calendar;
    private boolean weekViewActive = false;
    private int currentWeek;
    public CalendarSegment (Date date){
        calendar = new Calendar(date);
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
    //wechselt von Tagesansicht in Wochenansicht
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

}
