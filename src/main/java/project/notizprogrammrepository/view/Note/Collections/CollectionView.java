package project.notizprogrammrepository.view.Note.Collections;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import project.notizprogrammrepository.controller.Controller;


/*
Plan:
surrounded by an offset of size/20
top row: Label with the title; cancel- and saveButton
rest: textArea for the CollectionText
 */
public class CollectionView {
    private final Group root;
    private final Label title;
    private final Button cancelButton;
    private final Button saveButton;
    private final TextArea textArea;
    private final Controller controller;
    public CollectionView(double x, double y, double width, double height, Controller controller, CollectionSegmentView collectionSegmentView){
        this.controller = controller;

        root = new Group();

        title = new Label("Title");
        title.setTextFill(Color.GREEN);
        root.getChildren().add(title);

        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(actionEvent -> {
            root.setVisible(false);
            collectionSegmentView.setVisible(true);
            collectionSegmentView.refresh();
        });
        root.getChildren().add(cancelButton);

        saveButton = new Button("Save");
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
    public void display(String collectionTitle){
        title.setText(collectionTitle);
        textArea.setText(controller.getCollection(collectionTitle).getText());
        root.setVisible(true);
    }
    public Group getRoot() {
        return root;
    }
}
