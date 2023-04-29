package project.notizprogrammrepository.view;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import project.notizprogrammrepository.MainApplication;

public class KalenderView {
    private final Group view = new Group();
    private final HBox hBox = new HBox();
    private final Rectangle hboxBackground = new Rectangle();
    private final Button switchViewButton = new Button();
    private final float width = 800;
    private final float height = 500;

    public Group getView() {
        return view;
    }
    public void sethBox(){
        hBox.prefHeight(height);
        hBox.prefWidth(width / 10);
        hBox.setLayoutX(width / 10);
        hBox.setLayoutY(0);

        hboxBackground.setFill(Paint.valueOf("#2E4053"));
        hboxBackground.setWidth(width / 6);
        hboxBackground.setHeight(height);
        hBox.getChildren().add(hboxBackground);
    }

    public void setSwitchViewButton(){
        switchViewButton.setText("Switch View");
        switchViewButton.setPrefHeight(40);
        switchViewButton.setPrefWidth(100);
        hBox.getChildren().add(switchViewButton);
    }
    public void addElements(){
        view.getChildren().add(hBox);
    }
}
