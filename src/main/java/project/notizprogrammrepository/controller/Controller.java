package project.notizprogrammrepository.controller;

import project.notizprogrammrepository.model.Application;
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.Dates.Month;
import project.notizprogrammrepository.model.Types.Mode;
import project.notizprogrammrepository.model.Types.NoteCollection;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.model.Types.entries.TodoEntry;
import project.notizprogrammrepository.model.Types.segments.CalendarSegment;
import project.notizprogrammrepository.model.Types.segments.NoteSegment;

import java.io.*;
import java.util.ArrayList;

/*
Plan:
Create all Interfaces through which the view will access the model/data.
Saving and loading (saving application to a .ser file)

Attributes:
application
name of saveFile

Interfaces:
switchToCalendar --> switches to calendar mode and returns the current month of the calendar
switchToNotes --> switches to note mode and returns the current month
switchToTodo --> switches to todoMode and returns the TodoList
switchToCollectionMode --> switches To CollectionMode and returns the list of collectionTitles
switchToWeekView --> switches to weekView and returns the current week
switchToMonthView --> switches to monthView and returns the current month
changeEntry(oldEntry, newEntry)
- case: oldEntry == null -> newEntry gets added
- case: newEntry == null -> old Entry gets removed
- case: oldEntry.date != newEntry.date --> oldEntry gets removed and newEntry gets added
- case: oldEntry,.date == newEntry.date --> oldEntry gets edited to the values of newEntry
getCollection(title) -> returns the collection corresponding to the title

closeApplication() --> saves the application into the specified file and returns whether the operation has been successful

 */
//30.04.2023 Fabian: switchToCalendar, switchToNotes, switchToTodo, switchToCollectionMode, switchToWeekView, switchToMonthView, changeEntry, getCollection
//03.05.2023 Fabian: editCollection
public class Controller {
    private Application application;
    private final String saveFileName = "C:\\Users\\Public\\NoteApplication\\noteApplicationSave.ser";
    public Controller(){
        File f = new File(saveFileName);
        if(f.exists()){
            try {
                FileInputStream fileIn = new FileInputStream(saveFileName);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                application = (Application) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException | ClassNotFoundException e) {
                application = new Application();
            }
        }else {
            application = new Application();
        }
    }
    public boolean closeApplication(){
        try{
            FileOutputStream fileOut = new FileOutputStream(saveFileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(application);

            out.close();
            fileOut.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.fillInStackTrace());
            System.err.println("Save failed");
            return false;
        }
    }
    public Month switchToCalendar(){
        application.switchMode(Mode.CALENDAR);
        return application.getMonth();
    }
    public Month switchToNote(){
        application.switchMode(Mode.NOTE);
        return application.getMonth();
    }
    public ArrayList<TodoEntry> switchToTodo(){
        application.switchMode(Mode.TODO);
        return application.getTodoList();
    }
    public ArrayList<String> switchToCollectionMode(){
        application.switchMode(Mode.COLLECTIONS);
        return ((NoteSegment)application.getSegment(Mode.NOTE)).getCollectionTitles();
    }
    public Day[]switchToWeekView(Mode mode){
        CalendarSegment calendarSegment = (CalendarSegment) application.getSegment(mode);
        if(!calendarSegment.isWeekViewActive())
            calendarSegment.switchView();
        return calendarSegment.getCurrentWeek();
    }
    public Month switchToMonthView(Mode mode){
        CalendarSegment calendarSegment = (CalendarSegment) application.getSegment(mode);
        if(calendarSegment.isWeekViewActive())
            calendarSegment.switchView();
        return calendarSegment.getMonth();
    }
    public void changeEntry(Entry oldEntry, Entry newEntry, boolean absolute/*Used for removal of Notes*/){
        if(oldEntry == null){
            application.addEntry(newEntry);
        }
        else if(newEntry == null){
            application.removeEntry(oldEntry, absolute);
        }else{
            if(oldEntry.getDate().equals(newEntry.getDate()) || oldEntry instanceof TodoEntry){
                application.editEntry(newEntry);
            }else{
                application.removeEntry(oldEntry, absolute);
                application.addEntry(newEntry);
            }
        }
    }
    public NoteCollection getCollection(String title){
        NoteSegment noteSegment = (NoteSegment) application.getSegment(Mode.NOTE);
        return noteSegment.getCollection(title);
    }
    public Application getApplication() {
        return application;
    }
}
