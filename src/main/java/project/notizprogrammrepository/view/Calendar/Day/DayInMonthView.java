package project.notizprogrammrepository.view.Calendar.Day;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import project.notizprogrammrepository.controller.Controller;
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.view.SegmentView;
import project.notizprogrammrepository.view.ViewUtils.CustomContextMenu;
import project.notizprogrammrepository.view.ViewUtils.EntryButton;

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
public class DayInMonthView extends Group{
    private ScrollPane entryScrollPane;
    private VBox entryVBox;
    private double width;
    private double height;
    private Label numberOfDayLabel;
    private Day currentDay;
    private EventHandler<ActionEvent> buttonClickHandler;
    private Controller controller;
    private SegmentView segmentView;
    public DayInMonthView(double width, double height, Day day, EventHandler<ActionEvent> buttonClickHandler, Controller controller, SegmentView segmentView){
        this.width = width;
        this.height = height;
        this.buttonClickHandler = buttonClickHandler;
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                buttonClickHandler.handle(new ActionEvent(DayInMonthView.this, null));
            }
        });
        this.controller = controller;
        this.segmentView = segmentView;



        entryScrollPane = new ScrollPane();
        entryVBox = new VBox();
        changeContents(day);
        entryScrollPane.setContent(entryVBox);
        entryScrollPane.setStyle("-fx-background-insets: 1, 1, 1");
        entryScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        entryScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        numberOfDayLabel = new Label(" " + day.getDay());
        numberOfDayLabel.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0),new Insets(0))));
        resize(width,height);
        this.getChildren().add(entryScrollPane);
        this.getChildren().add(numberOfDayLabel);
    }

    private ArrayList<Button> generateButtons(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        ArrayList<Button> buttons = new ArrayList<>();
        for(Entry e: currentDay.getEntries()){
            EntryButton button = new EntryButton(e.getTitle().substring(0, Math.min(10, e.getTitle().length())) + "  " + sdf.format(e.getDate()), controller, segmentView);
            button.setEntry(e);
            buttons.add(button);
        }
        for(Button b: buttons){
            b.setOnAction(buttonClickHandler);
        }
        return buttons;
    }

    public void resize(double newWidth, double newHeight){
        this.width = newWidth;
        this.height = newHeight;

        entryScrollPane.setPrefSize(newWidth, newHeight);
        entryScrollPane.setMaxSize(newWidth, newHeight);
        for(Node b : entryVBox.getChildren()){
            ((Button)b).setPrefSize(newWidth, newHeight/4);
            ((Button) b).setFont(new Font("Arial", (double) 15 /100 * height));
        }
        numberOfDayLabel.setPrefWidth(newWidth/4);
        numberOfDayLabel.setPrefHeight(newHeight/4);
        numberOfDayLabel.setFont(new Font("Arial", (double) 15 /100 * height));
    }
    public void changeContents(Day day){
        currentDay = day;
        entryVBox.getChildren().clear();
        entryVBox.getChildren().addAll(generateButtons());
    }
    public Day getDay() {
        return currentDay;
    }
}
