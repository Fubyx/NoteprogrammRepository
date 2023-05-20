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

/**
 * A view component representing an editor for a CalendarEntry.
 */
public class CalendarEntryView {
    /**
     * The root Group of the component containing all other elements.
     */
    private Group root;
    /**
     * The background of the editor.
     */
    private Rectangle background;
    /**
     * A TextField for the input of the title.
     */
    private TextField titleInput;
    /**
     * A selector for the date of the Entry.
     */
    private DatePicker datePicker;
    /**
     * A Spinner used for the selection of the time.
     */
    private Spinner<LocalTime> timePicker;
    /**
     * A Label containing the text "Reminder:"
     */
    private Label reminderLabel;
    /**
     * A selector for the  reminderDate of the Entry.
     */
    private DatePicker reminderDatePicker;
    /**
     * A Spinner used for the selection of the time of the reminder.
     */
    private Spinner<LocalTime> reminderTimePicker;
    /**
     * A TextArea used for displaying and editing the text of the Entry.
     */
    private TextArea textArea;
    /**
     * A button used to save all changes and close the editor.
     */
    private Button saveButton;
    /**
     * A button used to discard all changes and close the editor.
     */
    private Button cancelButton;
    /**
     * The currently displayed Entry.
     */
    private CalendarEntry currentEntry = null;

    /**
     * Creates a CalendarEntryView of the given size at the given position with the given values.
     * @param x The x position of the component.
     * @param y The y position of the component.
     * @param width The width of the component.
     * @param height The height of the component.
     * @param controller The controller used for access to the data.
     * @param calendarView The CalendarSegmentView the component is a part of.
     */
    public CalendarEntryView(double x, double y, double width, double height, Controller controller, CalendarSegmentView calendarView){
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
        cancelButton.getStyleClass().add("cancelbutton");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.setVisible(false);
                resetDisplayValues();
            }
        });
        root.getChildren().add(cancelButton);

        saveButton = new Button("Save");
        saveButton.getStyleClass().add("savebutton");
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

    /**
     * Sets the values of all components to the values of the given Entry and displays the component. If entry == null nothing happens.
     * @param entry The Entry to be shown.
     */
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
    /**
     * Sets the values of the datePicker and timePicker to the values of the given date and displays the component. If date == null no date is shown.
     * @param date The date for the Entry to be created.
     */
    public void display(Date date) {
        if(date != null) {
            datePicker.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            timePicker.getValueFactory().setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
        }
        reminderTimePicker.getValueFactory().setValue(LocalTime.of(0, 0));
        root.setVisible(true);
    }

    /**
     * Resets all values to their default.
     */
    private void resetDisplayValues(){
        currentEntry = null;
        datePicker.setValue(null);
        timePicker.getValueFactory().setValue(LocalTime.of(0, 0));
        titleInput.setText("Title");
        reminderDatePicker.setValue(null);
        reminderTimePicker.getValueFactory().setValue(LocalTime.of(0, 0));
        textArea.setText("Text...");
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
    /**
     * Returns the root Group of the component.
     * @return The root Group of the component.
     */
    public Group getRoot() {
        return root;
    }
}
