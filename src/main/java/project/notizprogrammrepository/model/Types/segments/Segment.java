package project.notizprogrammrepository.model.Types.segments;

import project.notizprogrammrepository.model.Types.entries.Entry;

import java.util.List;

public abstract class Segment {
   private List<Entry> entries;
   private double nextEmptyId;
   public abstract void addEntry(Entry entry);
   public abstract boolean removeEntry(Entry entry);
   public abstract void editEntry(Entry entry);
}
