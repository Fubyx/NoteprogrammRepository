package project.notizprogrammrepository.model.Types.Dates;


import project.notizprogrammrepository.model.Types.entries.Entry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//25.04.2023 Fabian: constructor, monthNumber, year, hasEntries, addEntry, remove, getEntry, getDay, getWeek
public class Month {
    private Day[][] days = new Day[6][7];
    // 17.04.2023
    // 1.Januar.31 / 2.Februar.28 / 3.März.31 / 4.April.30 / 5.Mai.31 / 6.Juni.30 / 7.Juli.31 / 8.August.31 /
    // 9.September.30 / 10.Oktober.31 / 11.November.30 / 12.Dezember.31
    private final int monthNumber;
    private final int year;

    public Day[][] getDays() {
        return days;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public int getYear() {
        return year;
    }

    //creates the days of the month corresponding to the given information
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
            int dayOfWeekOfFirst = DateUtils.getDayOfWeek(sdf.parse(s)) - 1;
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

    public void addEntry(Entry e){
        for(int i = 0; i < days.length; ++i){
            for(int j = 0; j < days[i].length; ++j){
                if(days[i][j] != null && days[i][j].isOnSameDay(e.getDate())){
                    days[i][j].addEntry(e);
                }
            }
        }
    }
    public void remove (Entry e){
        for(int i = 0; i < days.length; ++i){
            for(int j = 0; j < days[i].length; ++j){
                if(days[i][j] != null && days[i][j].isOnSameDay(e.getDate())){
                    days[i][j].remove(e);
                }
            }
        }
    }
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
}
