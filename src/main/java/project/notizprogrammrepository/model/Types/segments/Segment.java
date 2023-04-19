package project.notizprogrammrepository.model.Types.segments;

import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.model.Types.entries.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Segment {
   private List<Entry> entries;
   private double nextEmptyId;
   public void setEntries(List<Entry>entries){
      this.entries = entries;
   }
   public abstract void addEntry(Entry entry);
   public abstract boolean removeEntry(Entry entry);
   public abstract void editEntry(Entry entry);
   public abstract Note getNote(double id);
   public List<Entry> getEntries(){
      return entries;
   }

}
