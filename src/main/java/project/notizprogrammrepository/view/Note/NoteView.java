package project.notizprogrammrepository.view.Note;

import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.converter.LocalTimeStringConverter;
import project.notizprogrammrepository.controller.Controller;
import project.notizprogrammrepository.model.Types.entries.Note;
import project.notizprogrammrepository.model.Types.entries.Subject;
import project.notizprogrammrepository.view.ViewUtils.SwitchButton;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * A view component representing an editor for a Note.
 */
public class NoteView {
    /**
     * The root Group of the component containing all other elements.
     */
    private final Group root;
    /**
     * The background of the editor.
     */
    private final Rectangle background;
    /**
     * A TextField for the input of the title.
     */
    private final TextField titleInput;
    /**
     * A label with the text "Collect:"
     */
    private final Label collectLabel;
    /**
     * A switch for enabling or disabling collection by title.
     */
    private final SwitchButton collectSwitch;
    /**
     * A ComboBox used for the selection of the subject.
     */
    private final ComboBox<Subject> subjectSelector;
    /**
     * A selector for the date of the Note.
     */
    private final DatePicker datePicker;
    /**
     * A Spinner used for the selection of the time.
     */
    private final Spinner<LocalTime> timePicker;
    /**
     * A TextArea used for displaying and editing the text of the Entry.
     */
    private final TextArea textArea;
    /**
     * A button used to save all changes and close the editor.
     */
    private final Button saveButton;
    /**
     * A button used to discard all changes and close the editor.
     */
    private final Button cancelButton;
    /**
     * The currently displayed Note.
     */
    private Note currentNote = null;
    /**
     * Creates a NoteView of the given size at the given position with the given values.
     * @param x The x position of the component.
     * @param y The y position of the component.
     * @param width The width of the component.
     * @param height The height of the component.
     * @param controller The controller used for access to the data.
     * @param noteSegmentView The NoteSegmentView the component is a part of.
     */
    public NoteView(double x, double y, double width, double height, Controller controller, NoteSegmentView noteSegmentView){
        root = new Group();

        background = new Rectangle(width, height, Color.GRAY);
        root.getChildren().add(background);

        titleInput = new TextField("Title");
        root.getChildren().add(titleInput);

        collectLabel = new Label("Collect: ");
        collectLabel.getStyleClass().add("labels");
        root.getChildren().add(collectLabel);

        collectSwitch = new SwitchButton(width/25 * 9, height/20, width/25 * 3, height/10);
        root.getChildren().add(collectSwitch);

        subjectSelector = new ComboBox<>();
        for(Subject s : Subject.values()){
            subjectSelector.getItems().add(s);
        }
        subjectSelector.setPromptText("Subject");
        subjectSelector.setValue(Subject.NONE);
        root.getChildren().add(subjectSelector);

        datePicker = new DatePicker();
        root.getChildren().add(datePicker);

        timePicker = new Spinner<>();
        timePicker.setValueFactory(new SpinnerValueFactory<>() {
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
        });
        root.getChildren().add(timePicker);

        textArea = new TextArea("Text...");
        root.getChildren().add(textArea);

        cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("cancelbutton");
        cancelButton.setOnAction(actionEvent -> {
            root.setVisible(false);
            resetValues();
        });
        root.getChildren().add(cancelButton);

        saveButton = new Button("Save");
        saveButton.getStyleClass().add("savebutton");
        saveButton.setOnAction(actionEvent -> {
            if(titleInput.getText().equals("Title") || textArea.getText().equals("Text..."))
                return;
            LocalDateTime lDT = LocalDateTime.of(datePicker.getValue(), timePicker.getValue());
            Note note = new Note(titleInput.getText(), textArea.getText(), Date.from(lDT.atZone(ZoneId.systemDefault()).toInstant()), collectSwitch.isState(), subjectSelector.getValue());
            controller.changeEntry(currentNote, note, true);
            root.setVisible(false);
            resetValues();
            noteSegmentView.refresh();
        });
        root.getChildren().add(saveButton);

        resize(x, y, width, height);
        root.setVisible(false);
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

        background.setWidth(width);
        background.setHeight(height);

        titleInput.setLayoutX(width/25);
        titleInput.setLayoutY(height/20);
        titleInput.setPrefSize(width/25 * 3, height/10);

        collectLabel.setLayoutX(width/25 * 5);
        collectLabel.setLayoutY(height/20);
        collectLabel.setPrefSize(width/25 * 3, height/10);

        collectSwitch.resize(width/25 * 9, height/20, width/25 * 3, height/10);

        subjectSelector.setLayoutX(width/25 * 13);
        subjectSelector.setLayoutY(height/20);
        subjectSelector.setPrefSize(width/25 * 3, height/10);

        datePicker.setLayoutX(width/25 * 17);
        datePicker.setLayoutY(height/20);
        datePicker.setPrefSize(width/25 * 3, height/10);

        timePicker.setLayoutX(width/25 * 21);
        timePicker.setLayoutY(height/20);
        timePicker.setPrefSize(width/25 * 3, height/10);

        textArea.setLayoutX(width/25);
        textArea.setLayoutY(height/5);
        textArea.setPrefSize(width - width/25 * 2, height - height/5 * 2);

        saveButton.setLayoutX(width - width/25 * 4);
        saveButton.setLayoutY(height - height/20 * 3);
        saveButton.setPrefSize(width/25 * 3, height/10);

        cancelButton.setLayoutX(width - width/25 * 9);
        cancelButton.setLayoutY(height - height/20 * 3);
        cancelButton.setPrefSize(width/25 * 3, height/10);
    }
    /**
     * Resets all values to their default.
     */
    private void resetValues(){
        currentNote = null;
        titleInput.setText("Title");
        if(collectSwitch.isState())
            collectSwitch.simClick();
        subjectSelector.setValue(Subject.NONE);
        textArea.setText("Text...");
    }
    /**
     * Sets the values of all components to the values of the given Note and displays the component. If note == null nothing happens.
     * @param note The Note to be shown.
     */
    public void display(Note note){
        if(note == null){
            return;
        }
        this.currentNote = note;
        titleInput.setText(note.getTitle());
        if(note.isCollectByTitle())
            collectSwitch.simClick();
        subjectSelector.setValue(note.getSubject());
        datePicker.setValue(note.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        timePicker.getValueFactory().setValue(note.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
        textArea.setText(note.getText());
        root.setVisible(true);
    }
    /**
     * Sets the values of the datePicker and timePicker to the values of the given date and displays the component. If date == null no date is shown.
     * @param date The date for the Note to be created.
     */
    public void display(Date date){
        if(date != null) {
            datePicker.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            timePicker.getValueFactory().setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
        }else {
            datePicker.setValue(null);
            timePicker.getValueFactory().setValue(LocalTime.of(0, 0));
        }
        root.setVisible(true);
    }
}
