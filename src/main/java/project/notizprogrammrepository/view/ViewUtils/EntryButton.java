package project.notizprogrammrepository.view.ViewUtils;

import javafx.scene.control.Button;
import project.notizprogrammrepository.model.Types.entries.Entry;

public class EntryButton extends Button {

    private Entry entry;

    public EntryButton(String s) {
        super(s);
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public Entry getEntry() {
        return entry;
    }
}
