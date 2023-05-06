package project.notizprogrammrepository;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.application.Application;
import project.notizprogrammrepository.model.Types.segments.CalendarSegment;
import project.notizprogrammrepository.controller.Controller;
import project.notizprogrammrepository.view.Calendar.CalendarEntryView;
import project.notizprogrammrepository.view.Calendar.CalendarSegmentView;

import java.util.Date;
import project.notizprogrammrepository.view.KalenderView;

import static javafx.application.Application.launch;

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
    private final KalenderView kalenderView = new KalenderView();
    private final Group view = new Group();
    private final HBox leftHbox = new HBox();
    private final float width = 800;
    private final float height = 500;

    private final Controller controller = new Controller();
    private CalendarSegmentView calendarSegmentView;
    private CalendarEntryView calendarEntryView; //View for the Editor of a CalendarEntry
    private final Group root = new Group();
    private final VBox leftTrayVbox = new VBox();
    private double width = 800;
    private double height = 500;


    private Rectangle background = new Rectangle();
    private Rectangle leftTrayBackground = new Rectangle();

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
        leftTrayVbox.getChildren().add(leftTrayBackground);
    }
    public void addElements(){
        root.getChildren().addAll(background, leftTrayVbox, calendarSegmentView.getView());
    }

    @Override
    public void start(Stage stage){
        calendarSegmentView = new CalendarSegmentView(width/10, 0, width - width/10, height, controller.switchToCalendar(), controller);
        setBackground();
        setLeftTrayVBox();

        addElements();

        Scene scene = new Scene(root, width, height);
        stage.setTitle("A");
        stage.setScene(scene);
        stage.show();
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
