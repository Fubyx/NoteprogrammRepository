package project.notizprogrammrepository.model;

import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.Dates.Month;
import project.notizprogrammrepository.model.Types.Mode;
import project.notizprogrammrepository.model.Types.entries.CalendarEntry;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.model.Types.entries.Note;
import project.notizprogrammrepository.model.Types.entries.TodoEntry;
import project.notizprogrammrepository.model.Types.segments.CalendarSegment;
import project.notizprogrammrepository.model.Types.segments.NoteSegment;
import project.notizprogrammrepository.model.Types.segments.Segment;
import project.notizprogrammrepository.model.Types.segments.TodoSegment;

import java.util.ArrayList;
import java.util.Date;

/*
Plan:
constructor: creates the segments (defaultmode = calendar)
Access to Segments -> getWeek, getMonth, getTodoList
add, remove, edit methods for entries -> separation with instanceof
Switch calendar mode & switch between calendar, note and todomode -> enum
 */
//29.04.2023 Fabian: constructor, switchMode, getCurrentMode, getWeek, getMonth, getTodoList, getSegment
public class Application {
    private final NoteSegment noteSegment;
    private final CalendarSegment calendarSegment;
    private final TodoSegment todoSegment;
    private Mode currentMode;

    public Application(){
        this.currentMode = Mode.CALENDAR;
        Date today = new Date();
        noteSegment = new NoteSegment(today);
        calendarSegment = new CalendarSegment(today);
        todoSegment = new TodoSegment();
    }
    public void switchMode(Mode mode){
        currentMode = mode;
    }
    public Mode getCurrentMode(){
        return currentMode;
    }
    public Day[]getWeek(){
        if(currentMode == Mode.NOTE){
            return noteSegment.getCurrentWeek();
        }
        return calendarSegment.getCurrentWeek();
    }
    public Month getMonth(){
        if(currentMode == Mode.NOTE){
            return noteSegment.getMonth();
        }
        return calendarSegment.getMonth();
    }
    public ArrayList<TodoEntry> getTodoList(){
        return todoSegment.getEntries();
    }
    public Segment getSegment(Mode mode){
        switch (mode){
            case NOTE -> {
                return noteSegment;
            }
            case TODO -> {
                return todoSegment;
            }
            default -> {
                return calendarSegment;
            }
        }
    }

    public void addEntry(Entry entry){
        if(entry instanceof CalendarEntry){
            calendarSegment.addCalendarEntry((CalendarEntry) entry);
        }else if(entry instanceof TodoEntry){
            todoSegment.addEntry(entry);
        }else{
            noteSegment.addNote((Note)entry);
        }
    }

}
