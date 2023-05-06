package project.notizprogrammrepository.view.Calendar.Day;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.entries.Entry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*
Plan:
constructor: width, height, Day

Group:
- scrollPane -> List of Buttons -> corresponding to a Day
- Label with transparent Background
 */
//03.05.2023 Fabian: constructor, generateButton, resize, changeContents
public class DayInMonthView {
    private Group view = new Group();
    private ScrollPane entryScrollPane;
    private VBox entryVBox;
    private double width;
    private double height;
    private Label numberOfDayLabel;
    private Day currentDay;
    private EventHandler<ActionEvent> buttonClickHandler;
    public DayInMonthView(double width, double height, Day day, EventHandler<ActionEvent> buttonClickHandler){
        this.width = width;
        this.height = height;
        this.buttonClickHandler = buttonClickHandler;

        entryScrollPane = new ScrollPane();
        entryVBox = new VBox();
        changeContents(day);
        entryScrollPane.setContent(entryVBox);
        entryScrollPane.setStyle("-fx-background-color:transparent;");
        entryScrollPane.setStyle("-fx-border-width: 0");
        entryScrollPane.setStyle("-fx-background-insets: 0, 0, 0");

        entryScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        entryScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        numberOfDayLabel = new Label(" " + day.getDay());
        numberOfDayLabel.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0),new Insets(0))));
        resize(width,height);
        this.view.getChildren().add(entryScrollPane);
        this.view.getChildren().add(numberOfDayLabel);
    }

    public Group getView() {
        return view;
    }

    private ArrayList<Button> generateButtons(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        ArrayList<Button> buttons = new ArrayList<>();
        for(Entry e: currentDay.getEntries()){
            CalendarEntryButton button = new CalendarEntryButton(e.getTitle().substring(0, 10) + "  " + sdf.format(e.getDate()));
            button.setEntry(e);
            buttons.add(button);
        }
        for(Button b: buttons){
            b.setOnAction(buttonClickHandler);
        }
        return buttons;
    }

    public void resize(double newWidth, double newHeight){
        entryScrollPane.setPrefSize(newWidth, newHeight);
        entryScrollPane.setMaxSize(newWidth, newHeight);
        for(Node b : entryVBox.getChildren()){
            ((Button)b).setPrefSize(newWidth, newHeight/4);
        }
        numberOfDayLabel.setPrefWidth(newWidth/4);
        numberOfDayLabel.setPrefHeight(newHeight/4);
    }
    public void changeContents(Day day){
        currentDay = day;
        entryVBox.getChildren().clear();
        entryVBox.getChildren().addAll(generateButtons());
    }
}
