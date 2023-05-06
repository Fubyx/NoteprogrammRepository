package project.notizprogrammrepository.view.Calendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.converter.LocalTimeStringConverter;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.view.EntryView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/*
Plan:
Rectangle with rounded corners, displayed in the middle of the screen. Date set as the Day clicked. Time set as 00:00 initially.
 */
public class CalendarEntryView extends EntryView {
    private Group root;
    private Rectangle background;
    private TextField titleInput;
    private DatePicker datePicker;
    private Spinner<LocalTime> timePicker;
    private Button saveButton;

    public CalendarEntryView(double x, double y, double width, double height){
        root = new Group();
        root.setLayoutX(x);
        root.setLayoutY(y);
        background = new Rectangle(width, height, Color.GRAY);
        root.getChildren().add(background);

        datePicker = new DatePicker();
        datePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate date = datePicker.getValue();
                System.err.println(date);
            }
        });
        datePicker.setPrefSize(width/2 - width/10 - width/20, height/5);
        datePicker.setLayoutX(width/10);
        datePicker.setLayoutY(height/10);
        root.getChildren().add(datePicker);

        timePicker = new Spinner<>();
        SpinnerValueFactory<LocalTime> valueFactory = new SpinnerValueFactory<LocalTime>() {
            {
                setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:mm:ss "), null));
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
        timePicker.setPrefSize(width/2 - width/10 - width/20, height/2);
        datePicker.setLayoutX(width/20 + width/2);
        datePicker.setLayoutY(height/10);
        root.getChildren().add(timePicker);

        titleInput = new TextField("Title");
        titleInput.setPrefWidth(width/3);
        titleInput.setPrefHeight(height/5);
        titleInput.setLayoutX(width/10);
        titleInput.setLayoutY(height/10 * 2  + height/5);
        root.getChildren().add(titleInput);



        saveButton = new Button();

    }
    @Override
    public void display(Entry entry) {

    }

}
