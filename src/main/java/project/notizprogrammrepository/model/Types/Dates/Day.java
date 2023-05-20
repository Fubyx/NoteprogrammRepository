package project.notizprogrammrepository.model.Types.Dates;
import project.notizprogrammrepository.model.Types.entries.Entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

//19.04.2023 Fabian: Constructor, isOnSameDay
//25.04.2023 Fabian: addEntry, amountOfEntries
//07.05.2023 Fabian: sort entries by Date before returning them

/**
 * A structure used for the storage of data (in the form of Entries) representing a certain day.
 */
public class Day implements Serializable {
    /**
     * The date with which the Object is created. The date contains the day the object represents.
     */
    private final Date date;
    /**
     * An ArrayList containing all entries made on this day.
     */
    private final ArrayList<Entry> entries = new ArrayList<>();

    /**
     * Applies the given date to the date-parameter.
     * @param date The date containing the day which the Object in creation represents.
     */
    public Day(Date date) {
        this.date = date;
    }

    /**
     * Checks whether the given date is on the day.
     * @param date The date to be compared.
     * @return True if the dates year, month and day numbers are identical to the ones of the day.
     */
    public boolean isOnSameDay(Date date){
        return date.getYear() == this.date.getYear() && date.getMonth() == this.date.getMonth() && date.getDate() == this.date.getDate();
    }
    /**
     * Counts how many entries are stored in this day.
     * @return The number of entries stored in this day.
     */
    public int amountOfEntries(){
        return entries.size();
    }
    /**
     * Adds the given Entry to the day.
     * @param e The Entry to be added.
     */
    public void addEntry(Entry e){
        entries.add(e);
    }
    /**
     * Removes the given Entry from the day.
     * @param e The Entry to be removed.
     */
    public void remove(Entry e){
        entries.remove(e);
    }
    /**
     * Removes all entries made on this day.
     */
    public void removeAll(){
        entries.clear();
    }

    /**
     * Returns the Entry tied to the given date.
     * @param date The date of the desired Entry.
     * @return The Entry corresponding to the date or null if there is none.
     */
    public Entry getEntry(Date date){
        for(Entry e : entries){
            if(date.equals(e.getDate())){
                return e;
            }
        }
        return null;
    }

    /**
     * Sorts the entries stored in the Day by date.
     * @return An ArrayList containing the sorted entries.
     */
    public ArrayList<Entry> getEntries() {
        entries.sort(Comparator.comparing(Entry::getDate));
        return entries;
    }

    /**
     * Returns the number of the day in the month as a String.
     * @return A String representing the number of the day in the month.
     */
    public String getDay(){
        return String.valueOf(date.getDate());
    }

    /**
     * Returns the date.
     * @return The date.
     */
    public Date getDate() {
        return date;
    }
}
