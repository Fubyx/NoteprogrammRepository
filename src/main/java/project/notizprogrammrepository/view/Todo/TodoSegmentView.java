package project.notizprogrammrepository.view.Todo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalTimeStringConverter;
import project.notizprogrammrepository.controller.Controller;
import project.notizprogrammrepository.model.Types.entries.TodoEntry;
import project.notizprogrammrepository.view.SegmentView;
import project.notizprogrammrepository.view.ViewUtils.EntryButton;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
/*
Plan:
border size/20 around everything
width/2 --> ScrollPane with list
rest:
left textField with Title + ComboBox with priorities

label due + below datePicker + timePicker

bottom left: cancel
bottom right load

rest textArea

/20
/10 title
/20             7 * /20
/10 date
/20

10 * /20 = /2

/20
/20 buttons    3 * /20
/20
 */

/**
 * A view component representing the TodoSegmentView of the application.
 */
public class TodoSegmentView extends SegmentView {
    /**
     * The root Group of the component containing all other elements.
     */
    private final Group root;
    /**
     * A label with the text "TODO_:"
     */
    private final Label todoLabel;
    /**
     * The ScrollPane used to display all EntryButtons representing the todoList.
     */
    private final ScrollPane scrollPane = new ScrollPane();
    /**
     * The VBox containing all EntryButtons representing the todoList.
     */
    private final VBox todoEntries;
    /**
     * A TextField for the input of the title.
     */
    private final TextField titleTextField;
    /**
     * A label with the text "DueDate:"
     */
    private final Label dueDateLabel;
    /**
     * A DatePicker for the dueDate.
     */
    private final DatePicker dueDate;
    /**
     * A TimePicker for the dueDate.
     */
    private final Spinner<LocalTime> timePicker;
    /**
     * A ComboBox for the priority.
     */
    private final ComboBox<Integer> priorityBox;
    /**
     * A TextArea used for displaying and editing the text of the Entry.
     */
    private final TextArea textArea;
    /**
     * A button used to discard all changes and reset the values of the editor to their default.
     */
    private final Button cancelButton;
    /**
     * A button used to save all changes and reset the values of the editor to their default.
     */
    private final Button saveButton;
    /**
     * The Controller used for access to the data.
     */
    private final Controller controller;
    /**
     * The currently displayed TodoEntry.
     */
    private TodoEntry currentEntry = null;
    /**
     * The width of the component,
     */
    private double width;
    /**
     * The height of the component.
     */
    private double height;
    /**
     * Creates a new CollectionSegmentView of the given size at the given position.
     * @param x The x position of the component.
     * @param y The y position of the component.
     * @param width The width of the component.
     * @param height The height of the component.
     * @param controller The Controller used for access to the data.
     */
    public TodoSegmentView(double x, double y, double width, double height, Controller controller){
        this.controller = controller;
        root = new Group();

        todoEntries = new VBox();
        setList();

        todoLabel = new Label("TODO:");
        todoLabel.getStyleClass().add("labels");
        root.getChildren().add(todoLabel);

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(todoEntries);
        root.getChildren().add(scrollPane);

        titleTextField = new TextField("Title");
        root.getChildren().add(titleTextField);

        priorityBox = new ComboBox<>();
        for(int i = 0; i <= 10; ++i){
            priorityBox.getItems().add(i);
        }
        priorityBox.setPromptText("Priority");
        root.getChildren().add(priorityBox);

        dueDateLabel = new Label("DueDate:");
        dueDateLabel.getStyleClass().add("labels2");
        root.getChildren().add(dueDateLabel);

        dueDate = new DatePicker();
        root.getChildren().add(dueDate);

        timePicker = new Spinner<>();
        timePicker.setValueFactory(new SpinnerValueFactory<LocalTime>() {
            {
                setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:mm"), null));
                setValue(LocalTime.of(0, 0));
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

        textArea  = new TextArea("Text...");
        root.getChildren().add(textArea);

        cancelButton  = new Button("Cancel");
        cancelButton.getStyleClass().add("cancelbutton");
        //cancelButton.setId("buttonLeftTray");
        //cancelButton.getStyleClass().add("button-64");

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resetValues();
            }
        });
        root.getChildren().add(cancelButton);

        saveButton = new Button("Save");
        saveButton.getStyleClass().add("savebutton");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDateTime lDT;
                TodoEntry todoEntry;
                int priority = priorityBox.getValue() == null ? 10:priorityBox.getValue();
                if(dueDate.getValue() != null) {
                    lDT = LocalDateTime.of(dueDate.getValue(), timePicker.getValue());
                    todoEntry = new TodoEntry(titleTextField.getText(), textArea.getText(), Date.from(lDT.atZone(ZoneId.systemDefault()).toInstant()), priority);
                }else{
                    todoEntry = new TodoEntry(titleTextField.getText(), textArea.getText(), null, priority);
                }
                controller.changeEntry(currentEntry, todoEntry, false);
                resetValues();
                refresh();
            }
        });
        root.getChildren().add(saveButton);

        resize(x, y, width, height);
        root.setVisible(false);
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

        todoLabel.setLayoutX(width/20);
        todoLabel.setLayoutY(height/20);
        todoLabel.setPrefSize(width/2 - width/20, height/10);

        scrollPane.setLayoutX(width/20);
        scrollPane.setLayoutY(height/10 * 2);
        scrollPane.setPrefSize(width/2 - width/20, height - height/10 * 3);

        titleTextField.setLayoutX(width/2 + width/20);
        titleTextField.setLayoutY(height/20);
        double v = width / 4 - width / 20 - width / 40;
        titleTextField.setPrefSize(v,height/10);

        priorityBox.setLayoutX(width - width/20 - (v));
        priorityBox.setLayoutY(height/20);
        priorityBox.setPrefSize(v,height/10);

        dueDateLabel.setLayoutX(width/2 + width/20);
        dueDateLabel.setLayoutY(height/10 * 2);
        double v1 = width / 6 - width / 20 - width / 40;
        dueDateLabel.setPrefSize(v1, height/10);

        dueDate.setLayoutX(width/2 + width/20 * 2 + v1);
        dueDate.setLayoutY(height/10 * 2);
        dueDate.setPrefSize(v1, height/10);

        timePicker.setLayoutX(width/2 + width/20 * 3 + (v1) * 2);
        timePicker.setLayoutY(height/10 * 2);
        timePicker.setPrefSize(v1, height/10);

        textArea.setLayoutX(width/2 + width/20);
        textArea.setLayoutY(7 * height/20);
        textArea.setPrefSize(width/2 - width/10, height/2);

        cancelButton.setLayoutX(width/2 + width/20);
        cancelButton.setLayoutY(height - height/20 * 3);
        cancelButton.setPrefSize(v,height/10);

        saveButton.setLayoutX(width - width/20 - (v));
        saveButton.setLayoutY(height - height/20 * 3);
        saveButton.setPrefSize(v,height/10);

        resizeButtons();
    }
    /**
     * Changes the size of all buttons respective to the size of the component..
     */
    private void resizeButtons(){
        for(Node n : todoEntries.getChildren()){
            ((Button)n).setPrefSize(width/2 - width/20, height/10);
        }
    }
    /**
     * Resets all values of the editor to their default.
     */
    private void resetValues(){
        currentEntry = null;
        titleTextField.setText("Title");
        dueDate.setValue(null);
        timePicker.getValueFactory().setValue(LocalTime.of(0,0));
        priorityBox.setValue(null);
        priorityBox.setPromptText("Priority");
        textArea.setText("Text...");
    }
    /**
     * Sets all values of the editor to the values of the given TodoEntry.
     * @param todoEntry The Entry to be displayed.
     */
    private void setValues(TodoEntry todoEntry){
        if(todoEntry == null)
            return;
        titleTextField.setText(todoEntry.getTitle());
        if(todoEntry.getDate() != null){
            dueDate.setValue(todoEntry.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            timePicker.getValueFactory().setValue(todoEntry.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
        }
        priorityBox.setValue(todoEntry.getPriority());
        textArea.setText(todoEntry.getText());
    }
    /**
     * Refreshes the todoList.
     */
    public void refresh(){
        todoEntries.getChildren().clear();
        setList();
        resizeButtons();
    }
    /**
     * Returns the root Group of the component.
     * @return The root Group of the component.
     */
    public Group getTodoView(){
        return root;
    }

    /**
     * Generates the EntryButtons for all TodoEntries and adds them to todoEntries.
     */
    public void setList(){
        ArrayList<TodoEntry> entries = controller.switchToTodo();
        for(TodoEntry entry : entries){
            EntryButton b = new EntryButton(entry.getTitle(), controller, TodoSegmentView.this);
            b.getStyleClass().add("switchViewButton");
            b.setEntry(entry);
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    setValues((TodoEntry) ((EntryButton)actionEvent.getSource()).getEntry());
                }
            });
            todoEntries.getChildren().add(b);
        }
    }
}
