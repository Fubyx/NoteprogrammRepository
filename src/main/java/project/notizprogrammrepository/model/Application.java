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

import java.io.Serializable;
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
//30.04.2023 Fabian: removeEntry, editEntry

/**
 * The main container of the app, containing and managing all "storage"-segments.
 */
public class Application implements Serializable {
    /**
     * The segment containing and managing all notes and notecollections.
     */
    private final NoteSegment noteSegment;
    /**
     * The segment containing and managing all calendarEntries.
     */
    private final CalendarSegment calendarSegment;
    /**
     * The segment containing and managing all todoEntries.
     */
    private final TodoSegment todoSegment;
    /**
     * A enum representing which mode is currently active. default = CALENDAR
     */
    private Mode currentMode;

    /**
     * A new Application is created and all segments are created, the calendars being generated with the systemTime at the time of the constructor-call.
     */
    public Application(){
        this.currentMode = Mode.CALENDAR;
        Date today = new Date();
        noteSegment = new NoteSegment(today);
        calendarSegment = new CalendarSegment(today);
        todoSegment = new TodoSegment();
    }

    /**
     * CurrentMode is changed to the given mode.
     * @param mode The new mode.
     */
    public void switchMode(Mode mode){
        currentMode = mode;
    }

    /**
     * Returns the current mode.
     * @return The current mode.
     */
    public Mode getCurrentMode(){
        return currentMode;
    }

    /**
     * Returns a Day-array representing the current week of the calendar- or noteSegment depending on which mode is active.
     * @return A Day-array representing the current week.
     */
    public Day[]getWeek(){
        if(currentMode == Mode.NOTE){
            return noteSegment.getCurrentWeek();
        }
        return calendarSegment.getCurrentWeek();
    }

    /**
     * Returns the current month of the calendar- or noteSegment depending on which mode is active.
     * @return The current month.
     */
    public Month getMonth(){
        if(currentMode == Mode.NOTE){
            return noteSegment.getMonth();
        }
        return calendarSegment.getMonth();
    }

    /**
     * Returns the todoList.
     * @return The todoList.
     */
    public ArrayList<TodoEntry> getTodoList(){
        return todoSegment.getEntries();
    }

    /**
     * Returns the segment of the given mode.
     * @param mode The mode of the desired segment.
     * @return The segment of the given mode.
     */
    public Segment getSegment(Mode mode){
        switch (mode){
            case TODO -> {
                return todoSegment;
            }
            case CALENDAR -> {
                return calendarSegment;
            }
            default -> {//NOTE and COLLECTIONS both return the note-segment, as the collections are in the note-segment
                return noteSegment;
            }
        }
    }

    /**
     * Adds an Entry to its segment depending on its class.
     * @param entry The Entry to be added.
     */
    public void addEntry(Entry entry){
        if(entry instanceof CalendarEntry){
            calendarSegment.addCalendarEntry((CalendarEntry) entry);
        }else if(entry instanceof TodoEntry){
            todoSegment.addEntry((TodoEntry) entry);
        }else{
            noteSegment.addNote((Note)entry);
        }
    }

    /**
     * Removes the given Entry from its segment.
     * @param entry The Entry to be removed.
     * @param absolute A flag used for notes to decide if they are removed from collections as well. Irrelevant to other Entry-types.
     */
    public void removeEntry(Entry entry, boolean absolute){
        if(entry instanceof CalendarEntry){
            calendarSegment.removeCalendarEntry((CalendarEntry) entry);
        }else if(entry instanceof TodoEntry){
            todoSegment.removeEntry((TodoEntry) entry);
        }else{
            noteSegment.removeNote((Note)entry, absolute);
        }
    }

    /**
     * Changes the properties of the Entry, in its segment, that has the same date or id, as the given Entry, to the values of the given Entry.
     * @param entry The Entry containing the date or id of the Entry that is supposed to be changed and the new values for that Entry.
     */
    public void editEntry(Entry entry){
        if(entry instanceof CalendarEntry){
            calendarSegment.editCalendarEntry((CalendarEntry) entry);
        }else if(entry instanceof TodoEntry){
            todoSegment.editEntry((TodoEntry) entry);
        }else{
            noteSegment.editNote((Note)entry);
        }
    }

}
