package project.notizprogrammrepository.model;

import javafx.stage.Stage;
import project.notizprogrammrepository.model.Types.segments.CalendarSegment;
import project.notizprogrammrepository.model.Types.segments.NoteSegment;
import project.notizprogrammrepository.model.Types.segments.TodoSegment;

public abstract class Application {
    private NoteSegment noteSegment;
    private CalendarSegment calendarSegment;
    private TodoSegment todoSegment;
    public void switchMode(String mode){}

    public abstract void start(Stage stage);
}
