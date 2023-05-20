package project.notizprogrammrepository.view.Note.Collections;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import project.notizprogrammrepository.controller.Controller;


/*
Plan:
surrounded by an offset of size/20
top row: Label with the title; cancel- and saveButton
rest: textArea for the CollectionText
 */

/**
 * A view component representing an editor for a NoteCollection.
 */
public class CollectionView {
    /**
     * The root Group of the component containing all other elements.
     */
    private final Group root;
    /**
     * A label for the title of the collection.
     */
    private final Label title;
    /**
     * A button used to discard all changes and close the editor.
     */
    private final Button cancelButton;
    /**
     * A button used to save all changes and close the editor.
     */
    private final Button saveButton;
    /**
     * A TextArea used for displaying and editing the collection's text.
     */
    private final TextArea textArea;
    /**
     * The Controller used for access to the data.
     */
    private final Controller controller;
    /**
     * Creates a new CollectionView of the given size at the given position.
     * @param x The x position of the component.
     * @param y The y position of the component.
     * @param width The width of the component.
     * @param height The height of the component.
     * @param controller The Controller used for access to the data.
     */
    public CollectionView(double x, double y, double width, double height, Controller controller, CollectionSegmentView collectionSegmentView){
        this.controller = controller;

        root = new Group();

        title = new Label("Title");
        title.getStyleClass().add("labels");
        root.getChildren().add(title);

        cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("cancelbutton");
        cancelButton.setOnAction(actionEvent -> {
            root.setVisible(false);
            collectionSegmentView.setVisible(true);
            collectionSegmentView.refresh();
        });
        root.getChildren().add(cancelButton);

        saveButton = new Button("Save");
        saveButton.getStyleClass().add("savebutton");
        saveButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (controller.getCollection(title.getText()) != null)//Just a precaution
                    controller.getCollection(title.getText()).editText(textArea.getText());
                root.setVisible(false);
                collectionSegmentView.setVisible(true);
                collectionSegmentView.refresh();
            }
        });
        root.getChildren().add(saveButton);

        textArea = new TextArea();
        root.getChildren().add(textArea);

        resize(x, y, width, height);
        root.setVisible(false);
    }
    /**
     * Changes the size and position of all components respective to the given values.
     * @param x The new x position of the component.
     * @param y The new y position of the component.
     * @param width The new width of the component.
     * @param height The new height of the component.
     */
    public void resize(double x, double y, double width, double height){
        root.setLayoutX(x);
        root.setLayoutY(y);

        title.setLayoutX(width/20);
        title.setLayoutY(height/20);
        title.setPrefSize(width/5, height/10);
        title.setFont(new Font("Arial", (double) 15 /500 * height));

        saveButton.setLayoutX(width - width/20 - width/5);
        saveButton.setLayoutY(height/20);
        saveButton.setPrefSize(width/5, height/10);

        cancelButton.setLayoutX(width - width/20 * 2 - width/5 * 2);
        cancelButton.setLayoutY(height/20);
        cancelButton.setPrefSize(width/5, height/10);

        textArea.setLayoutX(width/20);
        textArea.setLayoutY(height/5);
        textArea.setPrefSize(width - width/10, height - height/5 - height/20);
    }

    /**
     * Sets the values of the components to the values of the collection corresponding to the given title and displays the component.
     * @param collectionTitle The title of the collection to be displayed.
     */
    public void display(String collectionTitle){
        title.setText(collectionTitle);
        textArea.setText(controller.getCollection(collectionTitle).getText());
        root.setVisible(true);
    }
    /**
     * Returns the root Group of the component.
     * @return The root Group of the component.
     */
    public Group getRoot() {
        return root;
    }
}
