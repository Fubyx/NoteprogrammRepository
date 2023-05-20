package project.notizprogrammrepository.view.ViewUtils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import project.notizprogrammrepository.controller.Controller;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.view.Note.NoteSegmentView;
import project.notizprogrammrepository.view.SegmentView;

/**
 * A custom Button for Entries.
 */
public class EntryButton extends Button {
    /**
     * The Entry connected to the Button.
     */
    private Entry entry;
    /**
     * The ContextMenu of the Button.
     */
    private CustomContextMenu customContextMenu;

    /**
     * Creates a new EntryButton with the given text.
     * @param s The text of the Button.
     * @param controller The controller used for access to data.
     * @param segmentView The SegmentView the button is a part of.
     */
    public EntryButton(String s, Controller controller, SegmentView segmentView) {
        super(s);
        customContextMenu = new CustomContextMenu(EntryButton.this);
        MenuItem delete = new MenuItem("delete");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.changeEntry(((CustomContextMenu)((MenuItem)actionEvent.getSource()).getParentPopup()).getButton().getEntry(), null, false);
                segmentView.refresh();
            }
        });
        customContextMenu.getItems().add(delete);
        if(segmentView instanceof NoteSegmentView){
            MenuItem deleteAbsolute = new MenuItem("delete absolute");
            deleteAbsolute.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    controller.changeEntry(((CustomContextMenu)((MenuItem)actionEvent.getSource()).getParentPopup()).getButton().getEntry(), null, true);
                    segmentView.refresh();
                }
            });
            customContextMenu.getItems().add(deleteAbsolute);
        }
        this.setContextMenu(customContextMenu);
    }

    /**
     * Sets the entry of the Button.
     * @param entry The entry of the Button.
     */
    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    /**
     * Returns the entry of the Button.
     * @return The entry of the Button.
     */
    public Entry getEntry() {
        return entry;
    }
}
