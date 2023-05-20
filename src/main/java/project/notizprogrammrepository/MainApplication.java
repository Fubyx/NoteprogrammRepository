package project.notizprogrammrepository;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.application.Application;
import project.notizprogrammrepository.controller.Controller;
import project.notizprogrammrepository.view.Calendar.CalendarSegmentView;

import project.notizprogrammrepository.view.Note.Collections.CollectionSegmentView;
import project.notizprogrammrepository.view.Note.NoteSegmentView;
import project.notizprogrammrepository.view.Todo.TodoSegmentView;

import java.util.Objects;

/*
Plan:

Tray on the left size around 1/10 of the screenWidth -> Icons for Calendar, Note- and TodoMode in that Order from top down.

Rest of Screen:
groups/panes that will be cycled around depending on the need. -> switch of view triggers reload
1. CalendarSegmentView: Split into a Tray on the left and the display area of the calendar on the right.
    tray: Button for switching between WeekView and MonthView, Label containing the Month and Year currently on display
    display area: cycled around between weekView and monthView
    1. WeekView: Table Type of layout with date (number of the day) and weekday(mon, tue, wed, ...) on the top and a scrollview of the entries going down from underneath the text.
    2. MonthView: on the top a tableHead type layout with the weekdays (mon, tue, ...). Beneath a roster of rectangles containing the dayNumber and a ScrollView of the entries
    display of the entries: a Button with the first x Words of the title and the hour and minute of the date
2. NoteSegmentView: same layout as the CalendarView except in the tray there is a additional Button for switching to the CollectionsView //Button could also be added to the tray of the outermost layer --> not yet decided
3. TodoSegmentView: Split into 2: a list on the left and a display of a TodoEntry on the right
    list: A list of the names of the todoEntries.
    display: A Editor with a TextField for the Title, a selector for the dueDate, a dropDown for the priority and a second- multi-line-textField for the Text. a save and a cancel button on the bottom
        When a TodoEntry gets selected, its current data gets inserted into the fields, if none is selected the fields will be empty. once the user has inserted data, he can Click save for a new Entry to be created or cancel for the fields to be emptied.
        Cancel will also cancel selection of a entry
4. CollectionsView: split into 2 Lists of Names. 1 for regular titles, 1 for Subjects.
5. CollectionView: Display of a single Collection: A label for the Title/Subject and a Label (Later a textField -> function not yet implemented) for the texts of all the contained notes.
later: 2 Buttons at the bottom or top to save or discard changes. These will not close the view but either trigger a refreshing of the textfield to the previous text (discard) or a change top the corresponding note(s) (save)
6. CalendarEntryView: A popUp in the middle of the CalendarSegmentView displaying the contents of the Entry
set To Visible or notVisible and adjusted to the Entry on click.
PopUp:
    A TextField for the Title.
    A TextField for the Text.
    A DateSelection (lookup on the internet) for the Date.
    A second DateSelection for the reminder.
    A Button save which saves the changes and closes (invisible) the popUp
    A Button discard/cancel which closes the PopUp
7. NoteView: A sort of TextEditor set to Visible by clicking on a note, displaying the contents of said note.
    A TextField for the Title.
    A dropDown selection for the Subject
    A Switch for the collectionByTitle.
    A DateSelection for the Date.
    A multi-line-TextField covering the rest of the Screen for the Text.
For each Button of an Entry a right click opens a dropdown with for now 1 option to delete
 */

//03.05.2023 Fabian: changed HBox to VBox, added Controller

/**
 * The main class containing all components of the application.
 */
public class MainApplication extends Application {
    /**
     * The color of the background of the application.
     */
    public static final String backgroundColor = "#283747";
    /**
     * The controller used for access to data.
     */
    private final Controller controller = new Controller();
    /**
     * The view component for the CalendarSegment.
     */
    private CalendarSegmentView calendarSegmentView;
    /**
     * The view component for the TodoSegment.
     */
    private TodoSegmentView todoSegmentView;
    /**
     * The view component for the NoteSegment.
     */
    private NoteSegmentView noteSegmentView;
    /**
     * The view component for the CollectionSegment.
     */
    private CollectionSegmentView collectionSegmentView;
    /**
     * The root component of the application, serving as parent of the scene.
     */
    private final Group root = new Group();
    /**
     * The VBox of the tray on the left of the view.
     */
    private final VBox leftTrayVbox = new VBox();
    /**
     * The width of the view.
     */
    private double width = 800;
    /**
     * The height of the view.
     */
    private double height = 500;
    /**
     * The background of the view.
     */
    private final Rectangle background = new Rectangle();
    /**
     * The background of the tray on the left of the view.
     */
    private final Rectangle leftTrayBackground = new Rectangle();
    /**
     * The button of the left tray used for switching to the calendarSegment.
     */
    private Button calendarSegmentButton;
    /**
     * The button of the left tray used for switching to the noteSegment.
     */
    private Button noteSegmentButton;
    /**
     * The button of the left tray used for switching to the todoSegment.
     */
    private Button todoSegmentButton;
    /**
     * Changes the size of all components respective to the given values.
     * @param width The new width of the component.
     * @param height The new height of the component.
     */
    private void resize(double width, double height){
        this.width = width;
        this.height = height;
        background.setWidth(width);
        background.setHeight(height);

        calendarSegmentView.resize(width/10, 0, width - width/10, height);
        noteSegmentView.resize(width/10, 0, width - width/10, height);
        collectionSegmentView.resize(width/10, 0, width - width/10, height);
        todoSegmentView.resize(width/10, 0, width - width/10, height);

        leftTrayVbox.setPrefSize(width/10, height);
        leftTrayBackground.setWidth(width/10);
        leftTrayBackground.setHeight(height);


        calendarSegmentButton.setPrefSize(width/10, height/10);

        noteSegmentButton.setPrefSize(width/10, height/10);

        todoSegmentButton.setPrefSize(width/10, height/10);
    }

    /**
     * Initializes all components with their designated values.
     */
    private void initialize(){
        background.setFill(Paint.valueOf(backgroundColor));
        leftTrayBackground.setFill(Paint.valueOf("#222843"));

        calendarSegmentView = new CalendarSegmentView(width/10, 0, width - width/10, height, controller.switchToCalendar(), controller);

        collectionSegmentView = new CollectionSegmentView(width/10, 0, width - width/10, height, controller);

        noteSegmentView = new NoteSegmentView(width/10, 0, width - width/10, height, controller.switchToNote(), controller, collectionSegmentView);
        noteSegmentView.getRoot().setVisible(false);

        todoSegmentView  = new TodoSegmentView(width/10, 0, width - width/10, height, controller);
        todoSegmentView.getTodoView().setVisible(false);

        calendarSegmentButton = new Button("Calendar");
        calendarSegmentButton.getStyleClass().add("mainButtons");
        calendarSegmentButton.setOnAction(actionEvent -> {
            todoSegmentView.getTodoView().setVisible(false);
            noteSegmentView.getRoot().setVisible(false);
            calendarSegmentView.getRoot().setVisible(true);
            collectionSegmentView.getRoot().setVisible(false);
            calendarSegmentView.refresh();
        });

        noteSegmentButton = new Button("Notes");
        noteSegmentButton.getStyleClass().add("mainButtons");
        noteSegmentButton.setOnAction(actionEvent -> {
            todoSegmentView.getTodoView().setVisible(false);
            noteSegmentView.getRoot().setVisible(true);
            calendarSegmentView.getRoot().setVisible(false);
            collectionSegmentView.getRoot().setVisible(false);
            noteSegmentView.refresh();
        });

        todoSegmentButton = new Button("Todo");
        todoSegmentButton.getStyleClass().add("mainButtons");
        todoSegmentButton.setOnAction(actionEvent -> {
            todoSegmentView.getTodoView().setVisible(true);
            noteSegmentView.getRoot().setVisible(false);
            calendarSegmentView.getRoot().setVisible(false);
            collectionSegmentView.getRoot().setVisible(false);
            todoSegmentView.refresh();
        });

        leftTrayVbox.getChildren().addAll(noteSegmentButton, todoSegmentButton, calendarSegmentButton);


        root.getChildren().addAll(
                background, leftTrayBackground, leftTrayVbox,
                calendarSegmentView.getRoot(),
                todoSegmentView.getTodoView(),
                noteSegmentView.getRoot(),
                collectionSegmentView.getRoot()
        );

        resize(width, height);
    }

    /**
     * Starts the application.
     * @param stage The stage on which the application is displayed.
     */
    @Override
    public void start(Stage stage){
        initialize();

        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());


        ChangeListener<Number> resizeListener = (observableValue, number, t1) -> resize(scene.getWidth(), scene.getHeight());
        scene.widthProperty().addListener(resizeListener);
        scene.heightProperty().addListener(resizeListener);
        stage.setTitle("A");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(windowEvent -> {
            controller.closeApplication();
            Platform.exit();
        });
    }
    public static void main(String[] args) {
        launch();
    }
}
