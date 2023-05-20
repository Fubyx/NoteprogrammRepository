package project.notizprogrammrepository.view.Note;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import project.notizprogrammrepository.controller.Controller;
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.Dates.Month;
import project.notizprogrammrepository.model.Types.Mode;
import project.notizprogrammrepository.model.Types.entries.Note;
import project.notizprogrammrepository.model.Types.segments.CalendarSegment;
import project.notizprogrammrepository.view.SegmentView;
import project.notizprogrammrepository.view.ViewUtils.EntryButton;
import project.notizprogrammrepository.view.Calendar.Day.DayInMonthView;
import project.notizprogrammrepository.view.Calendar.Day.DayInWeekView;
import project.notizprogrammrepository.view.Calendar.Month.MonthView;
import project.notizprogrammrepository.view.Calendar.Week.WeekView;
import project.notizprogrammrepository.view.Note.Collections.CollectionSegmentView;
/**
 * A view component representing the NoteSegment of the application.
 */
public class NoteSegmentView extends SegmentView {
    /**
     * The root Group of the component containing all other elements.
     */
    private final Group root;
    /**
     * The VBox containing all elements of the tray.
     */
    private final VBox trayVBox;
    /**
     * The background of the tray.
     */
    private final Rectangle trayBackground = new Rectangle();
    /**
     * A button of the tray used for switching between week- and monthView.
     */
    private final Button switchViewButton = new Button();
    /**
     * A label of the tray for the year of the currently represented week or month.
     */
    private final Label yearLabel;
    /**
     * The distance between switchViewButton and monthLabel.
     */
    private final Rectangle distance_switch_to_month;
    /**
     * A label of the tray for the name of the currently represented month.
     */
    private final Label monthLabel;
    /**
     * The distance between yearLabel and collectionViewButton.
     */
    private final Rectangle distance_month_to_collections;
    /**
     * A button of the tray used for switching to the collectionView.
     */
    private final Button collectionViewButton;
    /**
     * The button used for shifts of the calendar backwards in time.
     */
    private final Rectangle leftButton;
    /**
     * The button used for shifts of the calendar forward in time.
     */
    private final Rectangle rightButton;
    /**
     * The currently displayed month or null if weekView is active.
     */
    private Month currentMonth;
    /**
     * The currently displayed week or null if monthView is active.
     */
    private Day[]currentWeek = null;
    /**
     * The monthView of the component.
     */
    private MonthView monthView;
    /**
     * The weekView of the component.
     */
    private WeekView weekView;
    /**
     * The Controller used for access to the data.
     */
    private Controller controller;
    /**
     * The editor for Notes.
     */
    private final NoteView noteView;
    /**
     * Creates a new NoteSegmentView of the given size at the given position with the given month.
     * @param x The x position of the component.
     * @param y The y position of the component.
     * @param width The width of the component.
     * @param height The height of the component.
     * @param month The month the component will show first.
     * @param controller The Controller used for access to the data.
     */
    public NoteSegmentView(double x, double y, double width, double height, Month month, Controller controller, CollectionSegmentView collectionSegmentView){
        this.currentMonth = month;
        this.controller = controller;
        root = new Group();

        trayVBox = new VBox();
        trayBackground.setFill(Paint.valueOf("#2E4053"));
        root.getChildren().add(trayBackground);
        root.getChildren().add(trayVBox);

        switchViewButton.setText("Switch View");
        switchViewButton.getStyleClass().add("switchViewButton");
        switchViewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(currentMonth != null){
                    currentMonth = null;
                    currentWeek = controller.switchToWeekView(Mode.NOTE);
                    weekView.changeContents(currentWeek);
                    monthView.getRoot().setVisible(false);
                    weekView.getRoot().setVisible(true);
                }else{
                    currentWeek = null;
                    currentMonth = controller.switchToMonthView(Mode.NOTE);
                    monthView.changeContents(currentMonth);
                    weekView.getRoot().setVisible(false);
                    monthView.getRoot().setVisible(true);
                }
            }
        });
        trayVBox.getChildren().add(switchViewButton);

        distance_switch_to_month = new Rectangle(width/5,height/10, Paint.valueOf("#2E4053"));
        trayVBox.getChildren().add(distance_switch_to_month);

        monthLabel = new Label(getMonthLabelText());
        monthLabel.getStyleClass().add("labels");
        trayVBox.getChildren().add(monthLabel);

        yearLabel = new Label(String.valueOf(month.getYear()));
        yearLabel.getStyleClass().add("labels");
        trayVBox.getChildren().add(yearLabel);

        distance_month_to_collections = new Rectangle(width/5,height/10, Paint.valueOf("#2E4053"));
        trayVBox.getChildren().add(distance_month_to_collections);


        collectionViewButton = new Button("CollectionView");
        collectionViewButton.getStyleClass().add("switchViewButton");
        collectionViewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                collectionSegmentView.refresh();
                root.setVisible(false);
                collectionSegmentView.getRoot().setVisible(true);
            }
        });
        trayVBox.getChildren().add(collectionViewButton);

        EventHandler<ActionEvent> calendarEntryHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(actionEvent.getSource() instanceof EntryButton){
                    noteView.display((Note) ((EntryButton) actionEvent.getSource()).getEntry());
                } else if(actionEvent.getSource() instanceof DayInMonthView){
                    noteView.display(((DayInMonthView) actionEvent.getSource()).getDay().getDate());
                }else if(actionEvent.getSource() instanceof DayInWeekView){
                    noteView.display(((DayInWeekView) actionEvent.getSource()).getDay().getDate());
                }
            }
        };

        this.monthView = new MonthView(width/5 + width/20, 0, width - width/5 -  width/10, height, month, calendarEntryHandler, controller, NoteSegmentView.this);
        root.getChildren().add(monthView.getRoot());

        this.weekView = new WeekView(width/5 + width/20, 0, width - width/5 - width/10, height, month.getWeek(1), calendarEntryHandler, controller, NoteSegmentView.this);
        weekView.getRoot().setVisible(false);
        root.getChildren().add(weekView.getRoot());

        EventHandler<MouseEvent> handleMonthSwitch = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getSource().equals(leftButton)){
                    ((CalendarSegment)controller.getApplication().getSegment(Mode.NOTE)).shiftView(false);
                } else if (mouseEvent.getSource().equals(rightButton)) {
                    ((CalendarSegment)controller.getApplication().getSegment(Mode.NOTE)).shiftView(true);
                }else{
                    return;
                }
                if(currentMonth == null){
                    currentWeek = controller.switchToWeekView(Mode.NOTE);
                    weekView.changeContents(currentWeek);
                    for(Day d : currentWeek){
                        if(d != null){
                            yearLabel.setText(String.valueOf(d.getDate().getYear() + 1900));
                            break;
                        }
                    }
                    monthLabel.setText(getMonthLabelText());
                }else {
                    currentMonth = controller.switchToMonthView(Mode.NOTE);
                    monthView.changeContents(currentMonth);
                    yearLabel.setText(String.valueOf(currentMonth.getYear()));
                    monthLabel.setText(getMonthLabelText());
                }
            }
        };
        leftButton = new Rectangle(width/5, height/2 - height/8, width/20, height/4);
        leftButton.setOnMouseClicked(handleMonthSwitch);
        leftButton.getStyleClass().add("rectangle");
        root.getChildren().add(leftButton);

        rightButton = new Rectangle(width - width/20, height/2 - height/8, width/20, height/4);
        rightButton.setOnMouseClicked(handleMonthSwitch);
        rightButton.getStyleClass().add("rectangle");
        root.getChildren().add(rightButton);

        noteView = new NoteView(0,0, width, height, controller, NoteSegmentView.this);
        root.getChildren().add(noteView.getRoot());


        resize(x, y, width, height);
    }
    /**
     * Returns the root Group of the component.
     * @return The root Group of the component.
     */
    public Group getRoot() {
        return root;
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
        root.prefWidth(width);
        root.prefHeight(height);

        trayVBox.prefWidth(width / 5);
        trayVBox.prefHeight(height);
        trayBackground.setWidth(width / 5);
        trayBackground.setHeight(height);

        switchViewButton.setPrefWidth(width /5);
        switchViewButton.setPrefHeight(height /10);
        switchViewButton.setFont(new Font("Arial", (double) 15 /500 * height));

        distance_switch_to_month.setWidth(width /5);
        distance_switch_to_month.setHeight(height /10);

        monthLabel.setPrefWidth(width /5);
        monthLabel.setPrefHeight(height /10);
        monthLabel.setFont(new Font("Arial", (double) 15 /500 * height));

        yearLabel.setPrefWidth(width /5);
        yearLabel.setPrefHeight(height /10);
        yearLabel.setFont(new Font("Arial", (double) 15 /500 * height));

        distance_month_to_collections.setWidth(width/5);
        distance_month_to_collections.setHeight(height/10);

        collectionViewButton.setPrefWidth(width/5);
        collectionViewButton.setPrefHeight(height/10);
        collectionViewButton.setFont(new Font("Arial", (double) 15 /500 * height));

        leftButton.setX(width/5);
        leftButton.setY(height/2 - height/8);
        leftButton.setWidth(width/20);
        leftButton.setHeight(height/4);

        rightButton.setX(width - width/20);
        rightButton.setY(height/2 - height/8);
        rightButton.setWidth(width/20);
        rightButton.setHeight(height/4);

        monthView.resize(width /5 + width /20, 0, width - width /5 - 2 * width /20, height);

        weekView.resize(width /5 + width /20, 0, width - width /5 - 2 * width /20, height);

        noteView.resize(0, 0, width, height);
    }
    /**
     * Returns a String representing the current month. (ex: January)
     * @return A String representing the current month.
     */
    private String getMonthLabelText(){
        int number = 0;
        if(currentMonth != null)
            number = this.currentMonth.getMonthNumber();
        else
            for (Day day: currentWeek)
                if(day != null) {
                    number = day.getDate().getMonth() + 1;
                    break;
                }
        switch(number){
            case 1 -> {
                return "January";
            }
            case 2 -> {
                return "February";
            }
            case 3 -> {
                return "March";
            }
            case 4 -> {
                return "April";
            }
            case 5 -> {
                return "May";
            }
            case 6 -> {
                return "June";
            }
            case 7 -> {
                return "July";
            }
            case 8 -> {
                return "August";
            }
            case 9 -> {
                return "September";
            }
            case 10 -> {
                return "October";
            }
            case 11 -> {
                return "November";
            }
            case 12 -> {
                return "December";
            }
            default -> {
                return "Error";
            }
        }
    }
    /**
     * Refreshes the monthView or weekView.
     */
    public void refresh(){
        if(currentMonth != null){
            currentMonth = controller.switchToMonthView(Mode.NOTE);
            monthView.changeContents(currentMonth);
        }else{
            currentWeek = controller.switchToWeekView(Mode.NOTE);
            weekView.changeContents(currentWeek);
        }
    }
}
