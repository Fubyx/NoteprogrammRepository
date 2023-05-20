package project.notizprogrammrepository.view.Calendar.Month;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import project.notizprogrammrepository.MainApplication;
import project.notizprogrammrepository.controller.Controller;
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.Dates.Month;
import project.notizprogrammrepository.view.Calendar.Day.DayInMonthView;
import project.notizprogrammrepository.view.SegmentView;

import java.util.ArrayList;

/*
Plan:
constructor -> width, height, month

VBox -> Split into 2 Parts:
- HBox (width, height/7): Mon, Tue, Wed, Thu, Fri, Sat, Sun --> labels each width/7 height/7
- "Table": VBox (width, height - height/7) with 6 HBoxes -> each (width, height/7)
    - each HBox -> 7 DayInMonthView -> each (width/7, height/7)

Contains:
- a CalendarEntryView that gets assigned the Entry before being displayed
 */

/**
 * A view-component for a month.
 */
public class MonthView {
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
     * A HBox containing labels for all weekdays. The weekdays are represented in their shortened form (ex. wed). Each label takes up 1/7 of the width and 1/7 of the height.
     */
    private final HBox weekDayNameDisplay;
    /**
     * A VBox containing 6 HBoxes representing the table like representation of the days of a month, each row containing 7 days. There are six rows because that is the maximum amount of weeks a month can have days in.
     */
    private final VBox table;
    /**
     * An ArrayList of DayInMonthViews containing all DayInMonthViews.
     */
    private final ArrayList<DayInMonthView> dMVs = new ArrayList<>();
    /**
     * The month currently represented by this component.
     */
    private Month currentMonth;
    /**
     * The handler for clicks on EntryButtons in DayInMonthViews.
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
     * Creates a MonthView of the given size at the given position with the given values.
     * @param x The x position of the component.
     * @param y The y position of the component.
     * @param width The width of the component.
     * @param height The height of the component.
     * @param month The month the component represents.
     * @param entryClickHandler The handler for clicks on EntryButtons in DayInMonthViews.
     * @param controller The Controller used for access to the data.
     * @param segmentView The SegmentView the component is a part of.
     */
    public MonthView (double x, double y, double width, double height, Month month, EventHandler<ActionEvent> entryClickHandler, Controller controller, SegmentView segmentView){
        this.currentMonth = month;
        this.entryClickHandler = entryClickHandler;
        this.width = width;
        this.height = height;
        this.controller = controller;
        this.segmentView = segmentView;
        root = new VBox();

        weekDayNameDisplay = new HBox();
        weekDayNameDisplay.getChildren().addAll(getWeekDayLabels());

        table = new VBox();
        table.getChildren().addAll(getTableRows());

        resize(x, y, width, height);
        root.getChildren().add(weekDayNameDisplay);
        root.getChildren().add(table);
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
        resizeTableRows();
    }

    /**
     * Changes the tables contents to the values of the given month.
     * @param month The new month to be represented by the component.
     */
    public void changeContents(Month month){
        this.currentMonth = month;
        root.getChildren().remove(table);
        table.getChildren().clear();
        table.getChildren().addAll(getTableRows());
        root.getChildren().add(table);
    }

    /**
     * Returns the labels of the weekdays.
     * @return The labels of the weekdays.
     */
    private ArrayList<Label> getWeekDayLabels(){
        ArrayList<Label> labels = new ArrayList<>();
        Label l = new Label("Mon");
        l.getStyleClass().add("labels");
        labels.add(l);
        l = new Label("Tue");
        l.getStyleClass().add("labels");
        labels.add(l);
        l = new Label("Wed");
        l.getStyleClass().add("labels");
        labels.add(l);
        l = new Label("Thu");
        l.getStyleClass().add("labels");
        labels.add(l);
        l = new Label("Fri");
        l.getStyleClass().add("labels");
        labels.add(l);
        l = new Label("Sat");
        l.getStyleClass().add("labels");
        labels.add(l);
        l = new Label("Sun");
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
     * Returns an ArrayList of HBoxes representing the rows of the views table. Each element is either a DayInMonthView or a rectangle with 1/7 of the width and 1/7 of the height in size.
     * @return An ArrayList of HBoxes representing the rows of the views table.
     */
    private ArrayList<HBox> getTableRows(){
        ArrayList<HBox>hBoxes = new ArrayList<>();
        for(int i = 0; i < 6; ++i){
            HBox box = new HBox();
            Day[]days = currentMonth.getWeek(i);
            if(days == null){
                box.getChildren().add(new Rectangle(width, height/7, Paint.valueOf(MainApplication.backgroundColor)));
            }else{
                for(Day d : days){
                    if(d == null){
                        box.getChildren().add(new Rectangle(width/7, height/7, Paint.valueOf(MainApplication.backgroundColor)));
                    }else{
                        DayInMonthView dMV = new DayInMonthView(width/7, height/7, d, entryClickHandler, controller, segmentView);
                        box.getChildren().add(dMV);
                        dMVs.add(dMV);
                    }
                }
            }
            box.setStyle(".hbox:focused {-fx-focus-color: transparent;}");
            hBoxes.add(box);
        }
        return hBoxes;
    }

    /**
     * Sets the size of the tableRows respective to the components width and height.
     */
    private void resizeTableRows(){
        for(Node node: table.getChildren()){
            HBox hBox = (HBox) node;
            for(Node node1 : hBox.getChildren()){
                if(node1 instanceof Rectangle){
                    ((Rectangle) node1).setHeight(height/7);
                    if(hBox.getChildren().size() == 1){
                        ((Rectangle) node1).setWidth(width);
                    }else{
                        ((Rectangle) node1).setWidth(width/7);
                    }
                }
            }
        }
        for(DayInMonthView dMV : dMVs){
            dMV.resize(width/7, height/7);
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
