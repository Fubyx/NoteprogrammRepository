package project.notizprogrammrepository.model.Types.Dates;

import project.notizprogrammrepository.model.Types.entries.Entry;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

/*
Plan:
Generate current month + 1 month back + 2 months forward.
If Month-view is switched, new month gets generated, old month gets deleted, if there is no entries.
 */
//25.04.2023 Fabian: constructor, shiftCalendar, addEntry, remove, getEntry
/**
 * The Calendar is a structure used for storage of data (in the form of Entries), sorted by date.
 */
public class Calendar implements Serializable {
    /**
     * A list of months.
     * The first element of this list is either one month earlier than the currently active month or the first month, where an Entry was made.
     * The last element of this list is either two months into the future from the currently active month or the month that is the furthest in the future, where an Entry was made.
     */
    private final LinkedList<Month> months = new LinkedList<>();
    /**
     * The index of the currently displayed month pointing to where in the LinkedList "months" it is saved.
     */
    private int currentMonthIndex = 1; //the index of the month that is currently displayed
    /**
     * Creates 1 month back, the current month and 2 month into the future, starting from the given date.
     * @param date The date to be used for generation of months.
     */
    public Calendar (Date date){
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        for (int i = -1; i < 3; ++i){
            if(month + i < 1){
                months.add(new Month(year - 1, 12));
            }else if(month + i > 12){
                months.add(new Month(year + 1, i));
            }else{
                months.add(new Month(year, month + i));
            }
        }
    }
    /**
     * Returns the currently saved months.
     * @return A LinkedList containing the months, starting from the earliest in time.
     */
    public LinkedList<Month> getMonths() {
        return months;
    }
    /**
     * Returns the index of the current month.
     * @return The index of the current month.
     */
    public int getCurrentMonthIndex() {
        return currentMonthIndex;
    }
    /**
     * Shifts the calendar to the left or right according to the given value. If a Month that now ranges out of the 4-month period covered by the Calendar does not have any entries, it is removed.
     * @param rightShift true: shift to the right; false: shift to the left
     */
    public void shiftCalendar(boolean rightShift){
        if(rightShift){
            try{
                months.get(currentMonthIndex + 3);
            }catch(IndexOutOfBoundsException i){
                int month = months.get(currentMonthIndex + 2).getMonthNumber() + 1;
                int year = months.get(currentMonthIndex + 2).getYear();
                if(month == 13){
                    month = 1;
                    ++year;
                }
                months.add(currentMonthIndex + 3, new Month(year, month));
            }
            ++currentMonthIndex;
            try{
                months.get(currentMonthIndex - 3);
            }catch(IndexOutOfBoundsException i){
                if(!months.get(currentMonthIndex - 2).hasEntries()){
                    months.remove(currentMonthIndex - 2);
                    --currentMonthIndex;
                }
            }
        }else{
            try{
                months.get(currentMonthIndex - 2);
                --currentMonthIndex;
            }catch(IndexOutOfBoundsException i){
                int month = months.get(currentMonthIndex - 1).getMonthNumber() - 1;
                int year = months.get(currentMonthIndex - 1).getYear();
                if(month == 0){
                    month = 12;
                    --year;
                }
                months.add(0, new Month(year, month));
            }
            try{
                months.get(currentMonthIndex + 4);
            }catch(IndexOutOfBoundsException i){
                if(!months.get(currentMonthIndex + 3).hasEntries()){
                    months.remove(currentMonthIndex + 3);
                }
            }
        }
    }
    /**
     * Adds an Entry to the current month.
     * @param e The Entry to be added.
     */
    public void addEntry(Entry e){
        months.get(currentMonthIndex).addEntry(e);
    }
    /**
     * Removes an Entry from the current month.
     * @param e The Entry to be removed.
     */
    public void remove(Entry e){
        months.get(currentMonthIndex).remove(e);
    }
    /**
     * Returns the Entry connected to the specified date or null if there is no such Entry in the current month.
     * @param date The date of the Entry.
     * @return The Entry connected to the date or null.
     */
    public Entry getEntry(Date date){
        return months.get(currentMonthIndex).getEntry(date);
    }
    /**
     * Returns the Day connected to the given date or null if the date is outside the current month.
     * @param date The date of the day.
     * @return The Day corresponding to the given date.
     */
    public Day getDay(Date date){
        return months.get(currentMonthIndex).getDay(date);
    }
    /**
     * Returns the Day-array connected to the given week number
     * @param week The date of the day.
     * @return The Day-Array representing the week corresponding to the given number.
     */
    public Day[]getWeek(int week){
        return months.get(currentMonthIndex).getWeek(week - 1);
    }
    /**
     * Returns the current month.
     * @return The current month.
     */
    public Month getMonth(){
        return months.get(currentMonthIndex);
    }
}
