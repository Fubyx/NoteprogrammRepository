package project.notizprogrammrepository.view.ViewUtils;

import javafx.scene.control.ContextMenu;

/**
 * A custom ContextMenu for EntryButtons saving the Button it was assigned to.
 */
public class CustomContextMenu extends ContextMenu {
    /**
     * The Button the menu is assigned to.
     */
    private EntryButton button;

    /**
     * Creates a ContextMenu with the given Button.
     * @param button The Button the menu is assigned to.
     */
    public CustomContextMenu (EntryButton button){
        super();
        this.button = button;
    }

    /**
     * Returns the EntryButton the menu was assigned to.
     * @return The EntryButton the menu was assigned to.
     */
    public EntryButton getButton() {
        return button;
    }
}
