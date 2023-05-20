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

/**
 * The Controller creates and provides the data stored in the Application.
 * Furthermore, it manages saving and loading of data to the users device.
 * @author Fabian Reifer
 */
public class Controller {
    /**
     * The main component of the application containing all data.
     */
    private Application application;
    /**
     * The name of the save file.
     */
    private final String saveFileName = "noteApplicationSave.ser";
    /**
     * The path to the directory containing the save file.
     */
    private final String saveDirectoryPath = "C:\\Users\\Public\\NoteApplication";
    /**
     * Loads the saved data or creates a new Application if no save file is found.
     */
    public Controller(){
        File f = new File(saveDirectoryPath + "\\" + saveFileName);
        if(f.exists()){
            try {
                FileInputStream fileIn = new FileInputStream(saveDirectoryPath + "\\" + saveFileName);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                application = (Application) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                application = new Application();
            }
        }else {
            application = new Application();
        }
    }
    /**
     * Saves the data of the Application into the specified file.
     */
    public void closeApplication(){
        try{
            // create a File object with the directory path
            File directory = new File(saveDirectoryPath);

            // check if the directory exists
            if (!directory.exists()) {
                // create the directory if it doesn't exist
                directory.mkdirs();
            }
            FileOutputStream fileOut = new FileOutputStream(saveDirectoryPath + "\\" + saveFileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(application);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.fillInStackTrace();
            System.err.println("Save failed");
        }
    }

    /**
     * Changes the mode to calendarMode and returns the current month.
     * @return The month active when last opening the calendar or the current month.
     */
    public Month switchToCalendar(){
        application.switchMode(Mode.CALENDAR);
        return application.getMonth();
    }
    /**
     * Changes the mode to noteMode and returns the current month.
     * @return The month active when last opening the calendar or the current month.
     */
    public Month switchToNote(){
        application.switchMode(Mode.NOTE);
        return application.getMonth();
    }

    /**
     * Changes mode to todoMode and returns the todolist.
     * @return An ArrayList of TodoEntries containing the todolist.
     */
    public ArrayList<TodoEntry> switchToTodo(){
        application.switchMode(Mode.TODO);
        return application.getTodoList();
    }

    /**
     * Changes mode to collectionMode and returns a list of the titles of currently active collections.
     * @return An ArrayList of strings containing the titles of all collections which can then be used to get the collection using "getCollection".
     */
    public ArrayList<String> switchToCollectionMode(){
        application.switchMode(Mode.COLLECTIONS);
        return ((NoteSegment)application.getSegment(Mode.NOTE)).getCollectionTitles();
    }

    /**
     * Switches to weekView if necessary and returns the current week.
     * @param mode The mode from which the week is requested.
     * @return The current week of the specified mode.
     * @throws ClassCastException if mode == Subject.TODO
     */
    public Day[]switchToWeekView(Mode mode){
        CalendarSegment calendarSegment = (CalendarSegment) application.getSegment(mode);
        if(!calendarSegment.isWeekViewActive())
            calendarSegment.switchView();
        return calendarSegment.getCurrentWeek();
    }
    /**
     * Switches to monthView if necessary and returns the current month.
     * @param mode The mode from which the month is requested.
     * @return The current month of the specified mode.
     * @throws ClassCastException if mode == Subject.TODO
     */
    public Month switchToMonthView(Mode mode){
        CalendarSegment calendarSegment = (CalendarSegment) application.getSegment(mode);
        if(calendarSegment.isWeekViewActive())
            calendarSegment.switchView();
        return calendarSegment.getMonth();
    }

    /**
     * Changes the contents of the oldEntry to the contents of the newEntry.
     * If oldEntry is null newEntry is simply added.
     * If newEntry is null the oldEntry is removed.
     * @param oldEntry The Entry to be changed or null for adding newEntry.
     * @param newEntry A new Entry Object containing the data to be put into oldEntry or null for removing oldEntry.
     * @param absolute A flag used for Notes only, defining whether they will be removed from collections on deletion.
     */
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

    /**
     * Gets the Collection of the specified title.
     * @param title The title of the collection.
     * @return The desired Collection or null if there is no Collection with the specified title.
     */
    public NoteCollection getCollection(String title){
        NoteSegment noteSegment = (NoteSegment) application.getSegment(Mode.NOTE);
        return noteSegment.getCollection(title);
    }

    /**
     * Returns the currently running Application.
     * @return The Application-object currently in use.
     */
    public Application getApplication() {
        return application;
    }
}
