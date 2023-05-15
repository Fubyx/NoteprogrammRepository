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
public class Calendar implements Serializable {
    private LinkedList<Month> months = new LinkedList<>();
    private int currentMonthIndex = 1; //the index of the month that is currently displayed
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
    public LinkedList<Month> getMonths() {
        return months;
    }

    public int getCurrentMonthIndex() {
        return currentMonthIndex;
    }
    //shifts the current month to the right or left
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
    public void addEntry(Entry e){
        months.get(currentMonthIndex).addEntry(e);
    }
    public void remove(Entry e){
        months.get(currentMonthIndex).remove(e);
    }
    //returns the entry for the specified date or null if there is no entry with that date in the current month
    public Entry getEntry(Date date){
        return months.get(currentMonthIndex).getEntry(date);
    }
    public Day getDay(Date date){
        return months.get(currentMonthIndex).getDay(date);
    }
    public Day[]getWeek(int week){
        return months.get(currentMonthIndex).getWeek(week - 1);
    }
    public Month getMonth(){
        return months.get(currentMonthIndex);
    }
}
