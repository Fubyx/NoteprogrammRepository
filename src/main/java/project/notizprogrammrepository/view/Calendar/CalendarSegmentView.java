package project.notizprogrammrepository.view.Calendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
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
import project.notizprogrammrepository.model.Types.entries.CalendarEntry;
import project.notizprogrammrepository.model.Types.segments.CalendarSegment;
import project.notizprogrammrepository.view.SegmentView;
import project.notizprogrammrepository.view.ViewUtils.EntryButton;
import project.notizprogrammrepository.view.Calendar.Day.DayInMonthView;
import project.notizprogrammrepository.view.Calendar.Day.DayInWeekView;
import project.notizprogrammrepository.view.Calendar.Month.MonthView;
import project.notizprogrammrepository.view.Calendar.Week.WeekView;

public class CalendarSegmentView extends SegmentView {
    private final Group view;
    private final VBox vBox;
    private final Rectangle vBoxBackground = new Rectangle();
    private final Button switchViewButton = new Button();
    private final Label yearLabel;
    private final Rectangle distance_switch_to_month;
    private final Label monthLabel;
    private final Rectangle leftButton;
    private final Rectangle rightButton;
    private Month currentMonth;
    private Day[]currentWeek = null;
    private MonthView monthView;
    private WeekView weekView;
    private Controller controller;
    private final CalendarEntryView calendarEntryView;
    public CalendarSegmentView(double x, double y, double width, double height, Month month, Controller controller){
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
                    currentWeek = controller.switchToWeekView(Mode.CALENDAR);
                    weekView.changeContents(currentWeek);
                    monthView.getRoot().setVisible(false);
                    weekView.getRoot().setVisible(true);
                }else{
                    currentWeek = null;
                    currentMonth = controller.switchToMonthView(Mode.CALENDAR);
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
        monthLabel.setTextFill(Color.LIGHTGREEN);
        monthLabel.setAlignment(Pos.CENTER);
        vBox.getChildren().add(monthLabel);

        yearLabel = new Label(String.valueOf(month.getYear()));
        yearLabel.setTextFill(Color.LIGHTGREEN);
        yearLabel.setAlignment(Pos.CENTER);
        vBox.getChildren().add(yearLabel);

        EventHandler<MouseEvent> handleMonthSwitch = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getSource().equals(leftButton)){
                    ((CalendarSegment)controller.getApplication().getSegment(Mode.CALENDAR)).shiftView(false);
                } else if (mouseEvent.getSource().equals(rightButton)) {
                    ((CalendarSegment)controller.getApplication().getSegment(Mode.CALENDAR)).shiftView(true);
                }else{
                    return;
                }
                if(currentMonth == null){
                    currentWeek = controller.switchToWeekView(Mode.CALENDAR);
                    weekView.changeContents(currentWeek);
                    for(Day d : currentWeek){
                        if(d != null){
                            yearLabel.setText(String.valueOf(d.getDate().getYear() + 1900));
                            break;
                        }
                    }
                    monthLabel.setText(getMonthLabelText());
                }else {
                    currentMonth = controller.switchToMonthView(Mode.CALENDAR);
                    monthView.changeContents(currentMonth);
                    yearLabel.setText(String.valueOf(currentMonth.getYear()));
                    monthLabel.setText(getMonthLabelText());
                }
            }
        };
        leftButton = new Rectangle(width/5, height/2 - height/8, width/20, height/4);
        leftButton.setOnMouseClicked(handleMonthSwitch);
        view.getChildren().add(leftButton);

        rightButton = new Rectangle(width - width/20, height/2 - height/8, width/20, height/4);
        rightButton.setOnMouseClicked(handleMonthSwitch);
        view.getChildren().add(rightButton);

        EventHandler<ActionEvent> calendarEntryHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(actionEvent.getSource() instanceof EntryButton){
                    calendarEntryView.display((CalendarEntry) ((EntryButton) actionEvent.getSource()).getEntry());
                } else if(actionEvent.getSource() instanceof DayInMonthView){
                    calendarEntryView.display(((DayInMonthView) actionEvent.getSource()).getDay().getDate());
                }else if(actionEvent.getSource() instanceof DayInWeekView){
                    calendarEntryView.display(((DayInWeekView) actionEvent.getSource()).getDay().getDate());
                }
            }
        };

        this.monthView = new MonthView(width/5 + width/20, 0, width - width/5 -  width/10, height, month, calendarEntryHandler, controller, CalendarSegmentView.this);
        view.getChildren().add(monthView.getRoot());

        this.weekView = new WeekView(width/5 + width/20, 0, width - width/5 - width/10, height, month.getWeek(1), calendarEntryHandler, controller, CalendarSegmentView.this);
        weekView.getRoot().setVisible(false);
        view.getChildren().add(weekView.getRoot());

        calendarEntryView = new CalendarEntryView(width/2 - width/6, height/10, width/3, height/10 * 8, controller, CalendarSegmentView.this);
        view.getChildren().add(calendarEntryView.getRoot());



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

        leftButton.setX(width/5);
        leftButton.setY(height/2 - height/8);
        leftButton.setWidth(width/20);
        leftButton.setHeight(height/4);

        rightButton.setX(width/5 + width/20 + (width - width /5 - 2 * width /20));
        rightButton.setY(height/2 - height/8);
        rightButton.setWidth(width/20);
        rightButton.setHeight(height/4);

        monthView.resize(width /5 + width /20, 0, width - width /5 - 2 * width /20, height);

        weekView.resize(width /5 + width /20, 0, width - width /5 - 2 * width /20, height);

        calendarEntryView.resize(width /2 - width /6, height /10, width /3, height /10 * 8);
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
            currentMonth = controller.switchToMonthView(Mode.CALENDAR);
            monthView.changeContents(currentMonth);
        }else{
            currentWeek = controller.switchToWeekView(Mode.CALENDAR);
            weekView.changeContents(currentWeek);
        }
    }
}
