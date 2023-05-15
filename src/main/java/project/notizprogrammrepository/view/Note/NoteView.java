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

public class NoteView {
    private final Group root;
    private final Rectangle background;
    private final TextField titleInput;
    private final Label collectLabel;
    private final SwitchButton collectSwitch;
    private final ComboBox<Subject> subjectSelector;
    private final DatePicker datePicker;
    private final Spinner<LocalTime> timePicker;
    private final TextArea textArea;
    private final Button saveButton;
    private final Button cancelButton;
    private Note currentNote = null;

    public NoteView(double x, double y, double width, double height, Controller controller, NoteSegmentView noteSegmentView){
        root = new Group();

        background = new Rectangle(width, height, Color.GRAY);
        root.getChildren().add(background);

        titleInput = new TextField("Title");
        root.getChildren().add(titleInput);

        collectLabel = new Label("Collect: ");
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
        cancelButton.setOnAction(actionEvent -> {
            root.setVisible(false);
            resetValues();
        });
        root.getChildren().add(cancelButton);

        saveButton = new Button("Save");
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
    public Group getRoot() {
        return root;
    }
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
    private void resetValues(){
        currentNote = null;
        titleInput.setText("Title");
        if(collectSwitch.isState())
            collectSwitch.simClick();
        subjectSelector.setValue(Subject.NONE);
        textArea.setText("Text...");
    }
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
