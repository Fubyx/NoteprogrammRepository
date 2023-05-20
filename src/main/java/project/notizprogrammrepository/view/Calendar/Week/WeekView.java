package project.notizprogrammrepository.view.Calendar.Week;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import project.notizprogrammrepository.MainApplication;
import project.notizprogrammrepository.controller.Controller;
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.view.Calendar.Day.DayInWeekView;
import project.notizprogrammrepository.view.SegmentView;

import java.util.ArrayList;
/**
 * A view-component for a week.
 */
public class WeekView {
    /**
     * The width of the component.
     */
    private double width;

    /**
     * The height of the component.
     */
    private double height;
    /**
     * The root VBox containing all other components.
     */
    private final VBox root;
    /**
     * A HBox containing labels for all weekdays. The weekdays are represented in their shortened form + the day-number respective to the month(ex. wed 3). Each label takes up 1/7 of the width and 1/7 of the height.
     */
    private final HBox weekDayNameDisplay;
    /**
     * A HBox containing all dayViews of the represented week.
     */
    private final HBox days;
    /**
     * The current week.
     */
    private Day[] currentWeek;
    /**
     * The handler for clicks on EntryButtons in DayInWeekViews.
     */
    private final EventHandler<ActionEvent> entryClickHandler;
    /**
     * The Controller used for access to the data.
     */
    private final Controller controller;
    /**
     * The SegmentView the component is a part of.
     */
    private final SegmentView segmentView;
    /**
     * Creates a WeekView of the given size at the given position with the given values.
     * @param x The x position of the component.
     * @param y The y position of the component.
     * @param width The width of the component.
     * @param height The height of the component.
     * @param week The week the component represents.
     * @param entryClickHandler The handler for clicks on EntryButtons in DayInWeekViews.
     * @param controller The Controller used for access to the data.
     * @param segmentView The SegmentView the component is a part of.
     */
    public WeekView (double x, double y, double width, double height, Day[] week, EventHandler<ActionEvent> entryClickHandler, Controller controller, SegmentView segmentView){
        this.currentWeek = week;
        this.entryClickHandler = entryClickHandler;
        this.width = width;
        this.height = height;
        this.segmentView = segmentView;
        this.controller = controller;
        root = new VBox();

        weekDayNameDisplay = new HBox();
        weekDayNameDisplay.getChildren().addAll(getWeekDayLabels());

        days = new HBox();
        days.getChildren().addAll(getDays());

        resize(x, y, width, height);
        root.getChildren().add(weekDayNameDisplay);
        root.getChildren().add(days);
    }
    /**
     * Changes the size and position of all components respective to the given values.
     * @param x The new x position of the component.
     * @param y The new y position of the component.
     * @param width The new width of the component.
     * @param height The new height of the component.
     */
    public void resize(double x, double y, double width, double height){
        this.width = width;
        this.height = height;
        root.setLayoutX(x);
        root.setLayoutY(y);
        root.setPrefSize(width, height);

        resizeWeekDayLabels();

        resizeDays();
    }
    /**
     * Changes the days-HBoxes contents to the values of the given week.
     * @param week The new week to be represented by the component.
     */
    public void changeContents(Day[]week){
        this.currentWeek = week;
        root.getChildren().remove(days);
        root.getChildren().remove(weekDayNameDisplay);

        days.getChildren().clear();
        days.getChildren().addAll(getDays());

        weekDayNameDisplay.getChildren().clear();
        weekDayNameDisplay.getChildren().addAll(getWeekDayLabels());

        root.getChildren().add(weekDayNameDisplay);
        resizeWeekDayLabels();
        root.getChildren().add(days);
    }

    /**
     * Returns the labels of the weekdays and number of the day in the month.
     * @return The labels of the weekdays.
     */
    private ArrayList<Label> getWeekDayLabels(){
        ArrayList<Label> labels = new ArrayList<>();
        Label l = new Label("Mon");
        if(currentWeek[0] != null){
            l.setText("Mon " + currentWeek[0].getDay());
        }
        l.getStyleClass().add("labels");
        labels.add(l);

        l = new Label("Tue");
        if(currentWeek[1] != null){
            l.setText("Tue " + currentWeek[1].getDay());
        }
        l.getStyleClass().add("labels");
        labels.add(l);

        l = new Label("Wed");
        if(currentWeek[2] != null){
            l.setText("Wed " + currentWeek[2].getDay());
        }
        l.getStyleClass().add("labels");
        labels.add(l);

        l = new Label("Thu");
        if(currentWeek[3] != null){
            l.setText("Thu " + currentWeek[3].getDay());
        }
        l.getStyleClass().add("labels");
        labels.add(l);

        l = new Label("Fri");
        if(currentWeek[4] != null){
            l.setText("Fri " + currentWeek[4].getDay());
        }
        l.getStyleClass().add("labels");
        labels.add(l);

        l = new Label("Sat");
        if(currentWeek[5] != null){
            l.setText("Sat " + currentWeek[5].getDay());
        }
        l.getStyleClass().add("labels");
        labels.add(l);

        l = new Label("Sun");
        if(currentWeek[6] != null){
            l.setText("Sun " + currentWeek[6].getDay());
        }
        l.getStyleClass().add("labels");
        labels.add(l);
        return labels;
    }
    /**
     * Sets the size of the weekday-labels respective to the components width and height.
     */
    private void resizeWeekDayLabels(){
        weekDayNameDisplay.setPrefSize(width, height/7);
        for(Node n : weekDayNameDisplay.getChildren()){
            ((Label)n).setPrefSize(width/7, height/7);
            ((Label) n).setFont(new Font("Arial", (double) 15 /500 * height));
        }
    }

    /**
     * Returns an ArrayList of nodes containing a representation for the days of currentWeek. Each of the 7 elements is either a DayInWeekView ora rectangle with 1/7 of the width and 5/7 of the height in size.
     * @return An ArrayList of nodes containing a representation for the days of currentWeek.
     */
    private ArrayList<Node> getDays(){
        ArrayList<Node> days = new ArrayList<>();
        for (Day day : currentWeek) {
            if (day == null) {
                days.add(new Rectangle(width / 7, height - height / 7 * 2, Color.valueOf(MainApplication.backgroundColor)));
            } else {
                days.add(new DayInWeekView(width / 7, height - height / 7 * 2, day, entryClickHandler, controller, segmentView));
            }
        }
        return days;
    }

    /**
     * Sets the size of the days respective to the components width and height.
     */
    private void resizeDays(){
        days.setPrefWidth(width);
        days.setPrefHeight((height/7) * 6);
        for(Node node : days.getChildren()){
            if(node instanceof  Rectangle){
                ((Rectangle) node).setWidth(width/7);
                ((Rectangle) node).setHeight(height - height/7 * 2);
            }else{
                node.resize(width/7, height - height/7 * 2);
            }
        }
    }
    /**
     * Returns the root VBox of the component.
     * @return The root VBox of the component.
     */
    public VBox getRoot() {
        return root;
    }
}
