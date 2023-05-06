package project.notizprogrammrepository.view.Calendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.converter.LocalTimeStringConverter;
import project.notizprogrammrepository.controller.Controller;
import project.notizprogrammrepository.model.Types.entries.CalendarEntry;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.view.EntryView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/*
Plan:
Rectangle with rounded corners, displayed in the middle of the screen. Date set as the Day clicked. Time set as 00:00 initially.
 */
public class CalendarEntryView {
    private Group root;
    private Rectangle background;
    private TextField titleInput;
    private DatePicker datePicker;
    private Spinner<LocalTime> timePicker;
    private Label reminderLabel;
    private DatePicker reminderDatePicker;
    private Spinner<LocalTime> reminderTimePicker;
    private TextArea textArea;
    private Button saveButton;
    private Button cancelButton;
    private Controller controller;
    private CalendarEntry currentEntry = null;

    public CalendarEntryView(double x, double y, double width, double height, Controller controller, CalendarSegmentView calendarView){
        this.controller = new Controller();

        root = new Group();
        background = new Rectangle(width, height, Color.GRAY);
        root.getChildren().add(background);

        datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        root.getChildren().add(datePicker);

        timePicker = new Spinner<>();
        SpinnerValueFactory<LocalTime> valueFactory = new SpinnerValueFactory<LocalTime>() {
            {
                setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:mm:ss"), null));
                setValue(LocalTime.now());
            }
            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(steps));
            }
            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(steps));
            }
        };
        timePicker.setValueFactory(valueFactory);
        root.getChildren().add(timePicker);

        titleInput = new TextField("Title");
        root.getChildren().add(titleInput);

        reminderLabel = new Label("Reminder:");
        root.getChildren().add(reminderLabel);

        reminderDatePicker = new DatePicker();
        root.getChildren().add(reminderDatePicker);

        reminderTimePicker = new Spinner<>();
        valueFactory = new SpinnerValueFactory<LocalTime>() {
            {
                setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:mm:ss"), null));
                setValue(null);
            }
            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(steps));
            }
            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(steps));
            }
        };
        reminderTimePicker.setValueFactory(valueFactory);
        root.getChildren().add(reminderTimePicker);

        textArea = new TextArea("Text...");
        root.getChildren().add(textArea);

        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.setVisible(false);
                resetDisplayValues();
            }
        });
        root.getChildren().add(cancelButton);

        saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(titleInput.getText().equals("Title"))
                    return;
                CalendarEntry calendarEntry;
                LocalDateTime lDT = LocalDateTime.of(datePicker.getValue(), timePicker.getValue());
                if (reminderDatePicker.getValue() != null){
                    LocalDateTime lDTRem = LocalDateTime.of(reminderDatePicker.getValue(), reminderTimePicker.getValue());
                    calendarEntry = new CalendarEntry(titleInput.getText(), textArea.getText(), Date.from(lDT.atZone(ZoneId.systemDefault()).toInstant()), Date.from(lDTRem.atZone(ZoneId.systemDefault()).toInstant()));
                }else
                    calendarEntry = new CalendarEntry(titleInput.getText(), textArea.getText(), Date.from(lDT.atZone(ZoneId.systemDefault()).toInstant()));
                controller.changeEntry(currentEntry, calendarEntry, false);
                root.setVisible(false);
                resetDisplayValues();
                calendarView.refresh();
            }
        });
        root.getChildren().add(saveButton);

        resize(x, y, width, height);
        root.setVisible(false);
    }
    public void display(CalendarEntry entry) {
        if(entry == null)
            return;
        currentEntry = entry;
        if(entry.getDate() != null) {
            datePicker.setValue(entry.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            timePicker.getValueFactory().setValue(entry.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
        }
        if(entry.getTitle() != null){
            titleInput.setText(entry.getTitle());
        }
        if(entry.getText() != null){
            textArea.setText(entry.getText());
        }
        if(entry.getReminderDate() != null) {
            reminderDatePicker.setValue(entry.getReminderDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            reminderTimePicker.getValueFactory().setValue(entry.getReminderDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
        }else{
            reminderTimePicker.getValueFactory().setValue(LocalTime.of(0, 0));
        }

        root.setVisible(true);
    }
    public void display(Date date) {
        if(date != null) {
            datePicker.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            timePicker.getValueFactory().setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
        }
        reminderTimePicker.getValueFactory().setValue(LocalTime.of(0, 0));
        root.setVisible(true);
    }
    private void resetDisplayValues(){
        currentEntry = null;
        datePicker.setValue(null);
        timePicker.getValueFactory().setValue(LocalTime.of(0, 0));
        titleInput.setText("Title");
        reminderDatePicker.setValue(null);
        reminderTimePicker.getValueFactory().setValue(LocalTime.of(0, 0));
        textArea.setText("Text...");
    }
    public void resize(double x, double y, double width, double height){
        root.setLayoutX(x);
        root.setLayoutY(y);

        background.setHeight(height);
        background.setWidth(width);

        datePicker.setLayoutX(width/20);
        datePicker.setLayoutY(height/20);
        datePicker.setPrefSize(width/2 - width/40 * 3, height/10);

        timePicker.setLayoutX(width/40 + width/2);
        timePicker.setLayoutY(height/20);
        timePicker.setPrefSize(width/2 - width/40 * 3, height/10);

        titleInput.setLayoutX(width/20);
        titleInput.setLayoutY(height/20 * 4);
        titleInput.setPrefWidth(width - width/10);
        titleInput.setPrefHeight(height/10);

        reminderLabel.setLayoutX(width/20);
        reminderLabel.setLayoutY(height/20 * 7);
        reminderLabel.setPrefWidth((width - width/10)/3);
        reminderLabel.setPrefHeight(height/10);

        reminderDatePicker.setLayoutX(width/20 + (width - width/10)/3);
        reminderDatePicker.setLayoutY(height/20 * 7);
        reminderDatePicker.setPrefWidth((width - width/10)/3);
        reminderDatePicker.setPrefHeight(height/10);

        reminderTimePicker.setLayoutX(width/20 + (width - width/10)/3 * 2);
        reminderTimePicker.setLayoutY(height/20 * 7);
        reminderTimePicker.setPrefSize((width - width/10)/3, height/10);

        textArea.setLayoutX(width/20);
        textArea.setLayoutY(height/20 * 10);
        textArea.setPrefSize(width - width/10, height/10 * 3);

        cancelButton.setLayoutX(width/20);
        cancelButton.setLayoutY(height/20 * 17);
        cancelButton.setPrefSize(width/2 - width/10, height/10);

        saveButton.setLayoutX(width/2 + width/20);
        saveButton.setLayoutY(height/20 * 17);
        saveButton.setPrefSize(width/2 - width/10, height/10);
    }
    public Group getRoot() {
        return root;
    }
}
