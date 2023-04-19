package project.notizprogrammrepository.model.Types.segments;

import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.model.Types.entries.Note;

import java.util.List;

public class CalendarSegment extends Segment{
    private boolean weekViewActive;
    @Override
    public void addEntry(Entry entry) {

    }

    @Override
    public boolean removeEntry(Entry entry) {
        return false;
    }

    @Override
    public void editEntry(Entry entry) {

    }

    @Override
    public Note getNote(double id) {
        return null;
    }

    public void switchView(){}
}
