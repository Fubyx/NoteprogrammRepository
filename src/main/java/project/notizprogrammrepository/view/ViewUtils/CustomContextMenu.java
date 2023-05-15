package project.notizprogrammrepository.view.ViewUtils;

import javafx.scene.control.ContextMenu;

public class CustomContextMenu extends ContextMenu {
    private EntryButton button;
    public CustomContextMenu (EntryButton button){
        super();
        this.button = button;
    }

    public EntryButton getButton() {
        return button;
    }
}
