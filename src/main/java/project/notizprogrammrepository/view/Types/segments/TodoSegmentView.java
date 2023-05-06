package project.notizprogrammrepository.view.Types.segments;

import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import project.notizprogrammrepository.MainApplication;

public class TodoSegmentView extends SegmentView{
    private float width = 800;
    private float height = 500;
    private final Group background = new Group();
    private final Rectangle backgroundScrollpane = new Rectangle(width / 2.5,height);
    private final ScrollPane scrollPane = new ScrollPane();
    private final Group display = new Group();
    private final TextField titleTextField = new TextField();
    private final DatePicker dueDate = new DatePicker();
    private final ComboBox priorityBox = new ComboBox();
    private final TextArea textField = new TextArea();
    private final Button cancelButton = new Button();
    private final Button saveButton = new Button();


    public Group getTodoView(){
        return background;
    }
    public void list(){
        scrollPane.setPrefHeight(height);
        scrollPane.setPrefWidth(width / 2.5);
        scrollPane.setLayoutX(width / 10);
        scrollPane.setLayoutY(0);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(backgroundScrollpane);

        backgroundScrollpane.setFill(Paint.valueOf("#2E4053"));
    }
    private float temp = (float) (width / 10 + width / 2.5);
    private float displayWidth = width - temp;
    public void display(){
        display.prefHeight(height);
        display.prefWidth(displayWidth);
        display.setLayoutX(displayWidth);
        display.setLayoutY(0);

        setDueDate();
        setTitleTextField();
        setPriorityBox();
        setTextField();
        setButtons();
    }

    public void setTitleTextField(){
        titleTextField.setText("Title...");
        titleTextField.setLayoutX((displayWidth / 2) - ((width / 7) / 2));
        titleTextField.setLayoutY(height / 15);
        titleTextField.setPrefWidth(width / 7);
    }
    public void setDueDate(){
        dueDate.setPrefWidth(width / 8);
        dueDate.setLayoutX((displayWidth / 2) - ((width / 8) * 1.7));
        dueDate.setLayoutY(height / 15);
    }
    public void setPriorityBox(){
        priorityBox.setPromptText("Priority");
        priorityBox.setPrefWidth(width / 8);
        priorityBox.setLayoutX((displayWidth / 2) + ((width / 8) / 1.4));
        priorityBox.setLayoutY(height / 15);
        for (int i = 0; i <= 10; i++){
            priorityBox.getItems().add(i);
        }
    }
    public void setTextField(){
        textField.setText("Text...");
        textField.setPrefHeight(height / 1.5);
        textField.setPrefWidth(displayWidth - 50);
        textField.setLayoutX((displayWidth / 2) - (width / 8) * 1.7);
        textField.setLayoutY(height / 5);
    }
    public void setButtons(){
        cancelButton.setText("CANCEL");
        cancelButton.setPrefWidth(width / 5);
        cancelButton.setPrefHeight(height / 13);
        cancelButton.setLayoutX((displayWidth / 2) - (width / 8) * 1.7);
        cancelButton.setLayoutY(height - (height / 13) * 1.3);
        cancelButton.setStyle("-fx-background-color: RED");
        cancelButton.setTextFill(Paint.valueOf("WHITE"));
        cancelButton.setFont(Font.font("Arial", 20));

        saveButton.setText("SAVE");
        saveButton.setPrefWidth(width / 5);
        saveButton.setPrefHeight(height / 13);
        saveButton.setLayoutX((displayWidth / 2) + (width / 8) / 5);
        saveButton.setLayoutY(height - (height / 13) * 1.3);
        saveButton.setStyle("-fx-background-color: GREEN");
        saveButton.setTextFill(Paint.valueOf("WHITE"));
        saveButton.setFont(Font.font("Arial", 20));

    }

    public void addElements(){
        display.getChildren().addAll(titleTextField, dueDate, priorityBox, textField, cancelButton, saveButton);
        background.getChildren().addAll(scrollPane, display);
    }
}
