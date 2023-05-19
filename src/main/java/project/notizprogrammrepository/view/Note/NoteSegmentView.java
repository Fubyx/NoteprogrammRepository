package project.notizprogrammrepository.view.Note;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

public class NoteSegmentView extends SegmentView {
    private final Group view;
    private final VBox vBox;
    private final Rectangle vBoxBackground = new Rectangle();
    private final Button switchViewButton = new Button();
    private final Label yearLabel;
    private final Rectangle distance_switch_to_month;
    private final Label monthLabel;
    private final Rectangle distance_month_to_collections;
    private final Button collectionViewButton;
    private final Rectangle leftButton;
    private final Rectangle rightButton;
    private Month currentMonth;
    private Day[]currentWeek = null;
    private MonthView monthView;
    private WeekView weekView;
    private Controller controller;
    private final NoteView noteView;
    public NoteSegmentView(double x, double y, double width, double height, Month month, Controller controller, CollectionSegmentView collectionSegmentView){
        this.currentMonth = month;
        this.controller = controller;
        view = new Group();

        vBox = new VBox();
        vBoxBackground.setFill(Paint.valueOf("#2E4053"));
        view.getChildren().add(vBoxBackground);
        view.getChildren().add(vBox);

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
        vBox.getChildren().add(switchViewButton);

        distance_switch_to_month = new Rectangle(width/5,height/10, Paint.valueOf("#2E4053"));
        vBox.getChildren().add(distance_switch_to_month);

        monthLabel = new Label(getMonthLabelText());
        monthLabel.getStyleClass().add("labels");
        vBox.getChildren().add(monthLabel);

        yearLabel = new Label(String.valueOf(month.getYear()));
        yearLabel.getStyleClass().add("labels");
        vBox.getChildren().add(yearLabel);

        distance_month_to_collections = new Rectangle(width/5,height/10, Paint.valueOf("#2E4053"));
        vBox.getChildren().add(distance_month_to_collections);


        collectionViewButton = new Button("CollectionView");
        collectionViewButton.getStyleClass().add("switchViewButton");
        collectionViewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                collectionSegmentView.refresh();
                view.setVisible(false);
                collectionSegmentView.getRoot().setVisible(true);
            }
        });
        vBox.getChildren().add(collectionViewButton);

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
        view.getChildren().add(monthView.getRoot());

        this.weekView = new WeekView(width/5 + width/20, 0, width - width/5 - width/10, height, month.getWeek(1), calendarEntryHandler, controller, NoteSegmentView.this);
        weekView.getRoot().setVisible(false);
        view.getChildren().add(weekView.getRoot());

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
        view.getChildren().add(leftButton);

        rightButton = new Rectangle(width - width/20, height/2 - height/8, width/20, height/4);
        rightButton.setOnMouseClicked(handleMonthSwitch);
        rightButton.getStyleClass().add("rectangle");
        view.getChildren().add(rightButton);

        noteView = new NoteView(0,0, width, height, controller, NoteSegmentView.this);
        view.getChildren().add(noteView.getRoot());


        resize(x, y, width, height);
    }
    public Group getView() {
        return view;
    }
    public void resize(double x, double y, double width, double height){
        view.setLayoutX(x);
        view.setLayoutY(y);
        view.prefWidth(width);
        view.prefHeight(height);

        vBox.prefWidth(width / 5);
        vBox.prefHeight(height);
        vBoxBackground.setWidth(width / 5);
        vBoxBackground.setHeight(height);

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
