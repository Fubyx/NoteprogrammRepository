package project.notizprogrammrepository.view.ViewUtils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import project.notizprogrammrepository.controller.Controller;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.view.Note.NoteSegmentView;
import project.notizprogrammrepository.view.SegmentView;

public class EntryButton extends Button {

    private Entry entry;

    private CustomContextMenu customContextMenu;
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

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public Entry getEntry() {
        return entry;
    }
}
