package project.notizprogrammrepository.model.Types.Dates;


import project.notizprogrammrepository.model.Types.entries.Entry;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//25.04.2023 Fabian: constructor, monthNumber, year, hasEntries, addEntry, remove, getEntry, getDay, getWeek

/**
 * A structure used for the storage of data (in the form of Entries) representing a certain month.
 */
public class Month implements Serializable {
    /**
     * A 2-dimensional array of Day objects containing the Days of the month.
     */
    private Day[][] days = new Day[6][7];
    // 17.04.2023
    // 1.Januar.31 / 2.Februar.28 / 3.März.31 / 4.April.30 / 5.Mai.31 / 6.Juni.30 / 7.Juli.31 / 8.August.31 /
    // 9.September.30 / 10.Oktober.31 / 11.November.30 / 12.Dezember.31
    /**
     * An integer representing the number of the month from 1 to 12
     */
    private final int monthNumber;
    /**
     * An integer representing the year.
     */
    private final int year;

    /**
     * Returns the days array.
     * @return A 2-dimensional array of Day objects.
     */
    public Day[][] getDays() {
        return days;
    }

    /**
     * Returns the number of the month from 1 to 12.
     * @return A integer from 1 to 12.
     */
    public int getMonthNumber() {
        return monthNumber;
    }

    /**
     * Returns the year.
     * @return the year.
     */
    public int getYear() {
        return year;
    }

    //creates the days of the month corresponding to the given information

    /**
     * Creates the days of the month and fills the rest of the days array with null.
     * @param year The year of the month.
     * @param monthNumber The monthNumber from 1 to 12.
     */
    public Month (int year, int monthNumber){
        this.monthNumber = monthNumber;
        this.year = year;
        int amountOfDays;
        switch (monthNumber){
            case 1, 3, 5, 7, 8, 10, 12 -> {
                amountOfDays = 31;
            }
            case 2 -> {
                if(((year%4 == 0) && (year%100 != 0)) || (year%400 == 0)){
                    amountOfDays = 29;
                }else{
                    amountOfDays = 28;
                }
            }
            default -> {
                amountOfDays = 30;
            }
        }
        String s = ("01." + monthNumber + "." + year);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy");
        try {
            int dayOfWeekOfFirst = getDayOfWeek(sdf.parse(s)) - 1;
            int counter = 0;
            for (int i = 0; i < 6; ++i) {
                for (int j = 0; j < 7; ++j) {
                    if (counter == 0) {
                        if (j < dayOfWeekOfFirst) {
                            days[i][j] = null;
                        } else {
                            ++counter;
                            days[i][j] = new Day(sdf.parse(counter + "." + monthNumber + "." + year));
                        }
                    } else {
                        ++counter;
                        if (counter > amountOfDays) {
                            days[i][j] = null;
                            continue;
                        }
                        days[i][j] = new Day(sdf.parse(counter + "." + monthNumber + "." + year));
                    }
                }
            }
        }catch(ParseException p){
            days = null;
        }
    }

    /**
     * Checks whether there are any entries stored in the month.
     * @return true if any Day of the month has entries.
     */
    public boolean hasEntries(){
        for(int i = 0; i < days.length; ++i){
            for(int j = 0; j < days[i].length; ++j){
                if(days[i][j] != null && days[i][j].amountOfEntries() > 0){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adds the given Entry to the day corresponding to its date.
     * @param e The Entry to be added.
     */
    public void addEntry(Entry e){
        for(int i = 0; i < days.length; ++i){
            for(int j = 0; j < days[i].length; ++j){
                if(days[i][j] != null && days[i][j].isOnSameDay(e.getDate())){
                    days[i][j].addEntry(e);
                }
            }
        }
    }

    /**
     * Removes the given entry if it is within the current month.
     * @param e The Entry to be removed.
     */
    public void remove (Entry e){
        for(int i = 0; i < days.length; ++i){
            for(int j = 0; j < days[i].length; ++j){
                if(days[i][j] != null && days[i][j].isOnSameDay(e.getDate())){
                    days[i][j].remove(e);
                }
            }
        }
    }

    /**
     * Returns the Entry connected to the given date.
     * @param date The date of the Entry.
     * @return The Entry connected to the given date.
     */
    public Entry getEntry(Date date){
        for (Day[] day : days) {
            for (Day value : day) {
                if (value != null && value.isOnSameDay(date)) {
                    return value.getEntry(date);
                }
            }
        }
        return null;
    }

    /**
     * Returns the Day in which the given date lies.
     * @param date The date of the wanted day.
     * @return The Day connected to the given date.
     */
    public Day getDay(Date date){
        for (Day[] day : days) {
            for (Day value : day) {
                if (value != null && value.isOnSameDay(date)) {
                    return value;
                }
            }
        }
        return null;
    }
    /**
     * Returns the Day-array connected to the given week number
     * @param week The date of the day.
     * @return The Day-Array representing the week corresponding to the given number.
     */
    public Day[] getWeek(int week){
        try{
            if(days[week][0] != null || days[week][6] != null){
                return days[week];
            }else{
                return null;
            }
        }catch(IndexOutOfBoundsException i){
            return null;
        }
    }
    /**
     * Calculates what day of the week (monday, tuesday, ...) the given date is on.
     * @param d The date for which the day of the week is calculated.
     * @return A value from 1 to 7 representing the day of the week or -1 if the calculation does not work.
     */
    private int getDayOfWeek(Date d){
        SimpleDateFormat formatterDay = new SimpleDateFormat("dd");    //wandelt das datum in einen String um.
        SimpleDateFormat formatterMonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");

        int w_tag;
        int tag = Integer.parseInt(formatterDay.format(d));
        int monat = Integer.parseInt(formatterMonth.format(d));
        int jahr = Integer.parseInt(formatterYear.format(d));
        int h = monat, k = jahr ;
        if (monat < 3)
        {
            h = monat + 12 ;
            k = jahr-1 ;
        }
        w_tag = (tag+2*h + (3*h+3)/5 + k + k/4 - k/100 + k/400 + 1)%7;
        switch (w_tag)
        {
            case 0 ->{return 7;} //Sonntag
            case 1 ->{return 1;} //Montag
            case 2 ->{return 2;} //Dienstag
            case 3 ->{return 3;} //Mittwoch
            case 4 ->{return 4;} //Donnerstag
            case 5 ->{return 5;} //Freitag
            case 6 ->{return 6;} //Samstag
            default ->{return -1;} //gibts nicht
        }
    }
}