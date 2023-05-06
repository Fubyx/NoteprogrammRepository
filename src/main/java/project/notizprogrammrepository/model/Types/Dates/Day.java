package project.notizprogrammrepository.model.Types.Dates;
import project.notizprogrammrepository.model.Types.entries.Entry;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/*

 */
//19.04.2023 Fabian: Constructor, isOnSameDay
//25.04.2023 Fabian: addEntry, amountOfEntries
public class Day implements Serializable {
    private Date date;
    private ArrayList<Entry> entries = new ArrayList<>();

    public Day(Date date) {
        this.date = date;
    }

    public boolean isOnSameDay(Date date){
        return date.getYear() == this.date.getYear() && date.getMonth() == this.date.getMonth() && date.getDate() == this.date.getDate();
    }

    public int amountOfEntries(){
        return entries.size();
    }
    public void addEntry(Entry e){
        entries.add(e);
    }
    public void remove(Entry e){
        entries.remove(e);
    }
    public void removeAll(){
        entries.clear();
    }
    public Entry getEntry(Date date){
        for(Entry e : entries){
            if(date.equals(e.getDate())){
                return e;
            }
        }
        return null;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }
    public String getDay(){
        return "" + date.getDate();
    }

    public Date getDate() {
        return date;
    }
}
