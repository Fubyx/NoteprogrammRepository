package project.notizprogrammrepository.view.Calendar.Day;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.notizprogrammrepository.controller.Controller;
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.view.SegmentView;
import project.notizprogrammrepository.view.ViewUtils.EntryButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*
Plan:

 */

/**
 * A view component for a single day in a WeekView of a calendar.
 */
public class DayInWeekView extends Group {
    /**
     * The ScrollPane used to display all EntryButtons.
     */
    private final ScrollPane entryScrollPane;
    /**
     * A VBox containing all EntryButtons.
     */
    private final VBox entryVBox;
    /**
     * A Day object representing the current Day.
     */
    private final Day currentDay;
    /**
     * The EventHandler used for the handling of interactions with the EntryButtons.
     */
    private final EventHandler<ActionEvent> buttonClickHandler;
    /**
     * The Controller used for access to the data.
     */
    private final Controller controller;
    /**
     * The SegmentView the component is a part of.
     */
    private final SegmentView segmentView;
    /**
     * Creates a new DayInWeekView with the given size and values.
     * @param width The width of the component.
     * @param height The height of the component.
     * @param day The day which the component represents.
     * @param buttonClickHandler The handler for EntryButtonClicks.
     * @param controller The controller of the application.
     * @param segmentView The SegmentView the component is a part of.
     */
    public DayInWeekView (double width, double height, Day day, EventHandler<ActionEvent> buttonClickHandler, Controller controller, SegmentView segmentView){
        this.currentDay = day;
        this.buttonClickHandler = buttonClickHandler;
        this.entryScrollPane = new ScrollPane();
        this.controller = controller;
        this.segmentView = segmentView;


        entryVBox = new VBox();
        entryVBox.getChildren().addAll(generateButtons());
        entryScrollPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                buttonClickHandler.handle(new ActionEvent(DayInWeekView.this, null));
            }
        });
        entryScrollPane.setContent(entryVBox);
        entryScrollPane.setStyle("-fx-background-insets: 1, 1, 1");

        entryScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        entryScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        entryScrollPane.setContent(entryVBox);

        this.getChildren().add(entryScrollPane);
        resize(width, height);
    }
    /**
     * Generates the EntryButtons for all entries of currentDay. Applies the buttonClickHandler to the buttons and then returns them.
     * @return An ArrayList of EntryButtons each representing one Entry of currentDay.
     */
    private ArrayList<Button> generateButtons(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        ArrayList<Button> buttons = new ArrayList<>();
        for(Entry e: currentDay.getEntries()){
            EntryButton button = new EntryButton(e.getTitle().substring(0, Math.min(10, e.getTitle().length())) + "  " + sdf.format(e.getDate()), controller, segmentView);
            button.setEntry(e);
            button.setOnAction(buttonClickHandler);
            buttons.add(button);
        }
        return buttons;
    }
    /**
     * Changes the size of all components respective to the given width and height.
     * @param width The new width of the component.
     * @param height The new height of the component.
     */
    public void resize(double width, double height){
        entryScrollPane.setPrefSize(width, height);
        for(Node b : entryVBox.getChildren()){
            ((Button)b).setPrefSize(width, height/10);
            ((Button) b).setFont(new Font("Arial", (double) 15 /500 * height));
        }
    }
    /**
     * Returns the day currently represented by this component.
     * @return The day currently represented by this component.
     */
    public Day getDay() {
        return currentDay;
    }
}
