package project.notizprogrammrepository.view.Calendar.Day;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.entries.Entry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*
Plan:

 */
public class DayInWeekView {
    private ScrollPane root;
    private VBox entryVBox;
    private Day currentDay;
    private EventHandler<ActionEvent> buttonClickHandler;
    private double width;
    private double height;

    public DayInWeekView (double width, double height, Day day, EventHandler<ActionEvent> buttonClickHandler){
        currentDay = day;
        this.buttonClickHandler = buttonClickHandler;
        this.width = width;
        this.height = height;

        entryVBox = new VBox();
        entryVBox.getChildren().addAll(generateButtons());

        resize(width, height);
        root.setContent(entryVBox);
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
    public void resize(double width, double height){
        this.width = width;
        this.height = height;
        root.setPrefSize(width, height);
        root.setMaxSize(width, height);
        for(Node b : entryVBox.getChildren()){
            ((Button)b).setPrefSize(width, height/10);
        }
        root.setPrefWidth(width);
        root.setPrefHeight(height);
    }
    public void changeContents(Day day){
        entryVBox.getChildren().clear();
        entryVBox.getChildren().addAll(generateButtons());
        resize(width, height);
    }
    public ScrollPane getRoot() {
        return root;
    }
}
