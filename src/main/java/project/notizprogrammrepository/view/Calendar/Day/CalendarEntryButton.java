package project.notizprogrammrepository.view.Calendar.Day;

import javafx.scene.control.Button;
import project.notizprogrammrepository.model.Types.entries.Entry;

public class CalendarEntryButton extends Button {

    private Entry entry;

    public CalendarEntryButton(String s) {
        super(s);
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public Entry getEntry() {
        return entry;
    }
}
