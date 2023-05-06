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
import javafx.util.converter.LocalTimeStringConverter;
import project.notizprogrammrepository.controller.Controller;
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.Dates.Month;
import project.notizprogrammrepository.model.Types.Mode;
import project.notizprogrammrepository.model.Types.segments.CalendarSegment;
import project.notizprogrammrepository.view.Calendar.Month.MonthView;
import project.notizprogrammrepository.view.Calendar.Week.WeekView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CalendarSegmentView {
    private final Group view;
    private final VBox vBox;
    private final Rectangle vBoxBackground = new Rectangle();
    private final Button switchViewButton = new Button();
    private final Label yearLabel;
    private final Rectangle distance_switch_to_month;
    private final Label monthLabel;
    private final Rectangle leftButton;
    private final Rectangle rightButton;
    private double width;
    private double height;
    private Month currentMonth;
    private Day[]currentWeek = null;
    private MonthView monthView;
    private WeekView weekView;
    private Controller controller;
    public CalendarSegmentView(double x, double y, double width, double height, Month month, Controller controller){
        this.width = width;
        this.height = height;
        this.currentMonth = month;
        this.controller = controller;
        view = new Group();

        vBox = new VBox();
        vBoxBackground.setFill(Paint.valueOf("#2E4053"));
        view.getChildren().add(vBoxBackground);
        view.getChildren().add(vBox);

        switchViewButton.setText("Switch View");
        switchViewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        vBox.getChildren().add(switchViewButton);

        distance_switch_to_month = new Rectangle(width/5,height/10, Paint.valueOf("#2E4053"));
        vBox.getChildren().add(distance_switch_to_month);

        monthLabel = new Label(getMonthLabelText());
        monthLabel.setTextFill(Color.LIGHTGREEN);
        monthLabel.setAlignment(Pos.CENTER);
        monthLabel.setFont(new Font("Arial", (double) 15 /500 * height));
        vBox.getChildren().add(monthLabel);

        yearLabel = new Label(String.valueOf(month.getYear()));
        yearLabel.setTextFill(Color.LIGHTGREEN);
        yearLabel.setAlignment(Pos.CENTER);
        yearLabel.setFont(new Font("Arial", (double) 15 /500 * height));
        vBox.getChildren().add(yearLabel);

        leftButton = new Rectangle(width/5, height/2 - height/8, width/20, height/4);
        leftButton.setOnMouseClicked(handleMonthSwitch);
        view.getChildren().add(leftButton);

        rightButton = new Rectangle(width - width/20, height/2 - height/8, width/20, height/4);
        rightButton.setOnMouseClicked(handleMonthSwitch);
        view.getChildren().add(rightButton);

        this.monthView = new MonthView(width/5 + width/20, 0, width - width/5 - 2 * width/20, height, month, actionEvent -> System.out.println("Clicked"));//Handler is temporary
        view.getChildren().add(monthView.getRoot());

        resize(x, y, width, height);
    }
    public Group getView() {
        return view;
    }
    public void resize(double x, double y, double newWidth, double newHeight){
        this.width = newWidth;
        this.height = newHeight;

        view.setLayoutX(x);
        view.setLayoutY(y);

        view.prefWidth(width);
        view.prefHeight(height);

        vBox.prefWidth(width / 5);
        vBox.prefHeight(height);

        vBoxBackground.setWidth(width / 5);
        vBoxBackground.setHeight(height);

        switchViewButton.setPrefWidth(width/5);
        switchViewButton.setPrefHeight(height/10);

        yearLabel.setPrefWidth(width/5);
        yearLabel.setPrefHeight(height/10);

        distance_switch_to_month.setWidth(width/5);
        distance_switch_to_month.setHeight(height/10);

        monthLabel.setPrefWidth(width/5);
        monthLabel.setPrefHeight(height/10);

        monthView.resize(width/5 + width/20, 0, width - width/5 - 2*width/20, height);
    }
    private String getMonthLabelText(){
        switch(this.currentMonth.getMonthNumber()){
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
    private EventHandler<MouseEvent> handleMonthSwitch = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getSource().equals(leftButton)){
                ((CalendarSegment)controller.getApplication().getSegment(Mode.CALENDAR)).shiftView(false);
            } else if (mouseEvent.getSource().equals(rightButton)) {
                ((CalendarSegment)controller.getApplication().getSegment(Mode.CALENDAR)).shiftView(true);
            }else{
                return;
            }
            currentMonth = controller.switchToCalendar();
            monthView.changeContents(currentMonth);
            yearLabel.setText(String.valueOf(currentMonth.getYear()));
            monthLabel.setText(getMonthLabelText());
        }
    };
}
