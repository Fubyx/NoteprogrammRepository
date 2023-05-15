package project.notizprogrammrepository;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.stage.WindowEvent;
import project.notizprogrammrepository.controller.Controller;
import project.notizprogrammrepository.view.Calendar.CalendarSegmentView;

import project.notizprogrammrepository.view.Note.Collections.CollectionSegmentView;
import project.notizprogrammrepository.view.Note.NoteSegmentView;
import project.notizprogrammrepository.view.Todo.TodoSegmentView;


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
For each Button of a Entry a right click opens a dropdown with for now 1 option to delete
 */

//03.05.2023 Fabian: changed HBox to VBox, added Controller
public class MainApplication extends Application {
    public static final String backgroundColor = "#283747";
    private final Controller controller = new Controller();
    private CalendarSegmentView calendarSegmentView;
    private TodoSegmentView todoSegmentView;
    private NoteSegmentView noteSegmentView;
    private CollectionSegmentView collectionSegmentView;
    private final Group root = new Group();
    private final VBox leftTrayVbox = new VBox();
    private double width = 800;
    private double height = 500;
    private final Rectangle background = new Rectangle();
    private final Rectangle leftTrayBackground = new Rectangle();
    private Button calendarSegmentButton;
    private Button noteSegmentButton;
    private Button todoSegmentButton;
    public void setBackground(){
        background.setFill(Paint.valueOf(backgroundColor));
        background.setHeight(height);
        background.setWidth(width);
    }
    public void setLeftTrayVBox(){
        leftTrayVbox.prefHeight(height);
        leftTrayVbox.prefWidth(width / 10);

        leftTrayBackground.setFill(Paint.valueOf("#222843"));
        leftTrayBackground.setWidth(width / 10);
        leftTrayBackground.setHeight(height);
    }
    public void addElements(){
        root.getChildren().addAll(
                background, leftTrayBackground, leftTrayVbox,
                calendarSegmentView.getView(),
                todoSegmentView.getTodoView(),
                noteSegmentView.getView(),
                collectionSegmentView.getRoot()
        );
        todoSegmentView.getTodoView().setVisible(false);
        noteSegmentView.getView().setVisible(false);

        leftTrayVbox.getChildren().addAll(noteSegmentButton, todoSegmentButton, calendarSegmentButton);
    }
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
    @Override
    public void start(Stage stage){
        setBackground();
        setLeftTrayVBox();

        calendarSegmentView = new CalendarSegmentView(width/10, 0, width - width/10, height, controller.switchToCalendar(), controller);

        collectionSegmentView = new CollectionSegmentView(width/10, 0, width - width/10, height, controller);

        noteSegmentView = new NoteSegmentView(width/10, 0, width - width/10, height, controller.switchToNote(), controller, collectionSegmentView);

        todoSegmentView  = new TodoSegmentView(width/10, 0, width - width/10, height, controller);

        calendarSegmentButton = new Button("Calendar");
        calendarSegmentButton.setOnAction(actionEvent -> {
            todoSegmentView.getTodoView().setVisible(false);
            noteSegmentView.getView().setVisible(false);
            calendarSegmentView.getView().setVisible(true);
            collectionSegmentView.getRoot().setVisible(false);
            calendarSegmentView.refresh();
        });

        noteSegmentButton = new Button("Notes");
        noteSegmentButton.setOnAction(actionEvent -> {
            todoSegmentView.getTodoView().setVisible(false);
            noteSegmentView.getView().setVisible(true);
            calendarSegmentView.getView().setVisible(false);
            collectionSegmentView.getRoot().setVisible(false);
            noteSegmentView.refresh();
        });

        todoSegmentButton = new Button("Todo");
        todoSegmentButton.setOnAction(actionEvent -> {
            todoSegmentView.getTodoView().setVisible(true);
            noteSegmentView.getView().setVisible(false);
            calendarSegmentView.getView().setVisible(false);
            collectionSegmentView.getRoot().setVisible(false);
            todoSegmentView.refresh();
        });


        addElements();

        resize(width, height);
        Scene scene = new Scene(root, width, height);

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

        //03.05.2023 Miguel: testing the Windows-Notifications
        /*
        Date date = new Date();
        CalendarSegment cs = new CalendarSegment(date);
        cs.throwInfo();
        //*/
    }
}
