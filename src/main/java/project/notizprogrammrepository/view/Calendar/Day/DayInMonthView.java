package project.notizprogrammrepository.view.Calendar.Day;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
constructor: width, height, Day

Group:
- scrollPane -> List of Buttons -> corresponding to a Day
- Label with transparent Background
 */
//03.05.2023 Fabian: constructor, generateButton, resize, changeContents

/**
 * A view component for a single day in the MonthView of a calendar.
 */
public class DayInMonthView extends Group{
    /**
     * The ScrollPane used to display all EntryButtons.
     */
    private final ScrollPane entryScrollPane;
    /**
     * A VBox containing all EntryButtons.
     */
    private final VBox entryVBox;
    /**
     * A Label displaying the number of the day.
     */
    private final Label numberOfDayLabel;
    /**
     * A Day object representing the current Day.
     */
    private Day currentDay;
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
     * Creates a new DayInMonthView with the given size and values.
     * @param width The width of the component.
     * @param height The height of the component.
     * @param day The day which the component represents.
     * @param buttonClickHandler The handler for EntryButtonClicks.
     * @param controller The controller of the application.
     * @param segmentView The SegmentView the component is a part of.
     */
    public DayInMonthView(double width, double height, Day day, EventHandler<ActionEvent> buttonClickHandler, Controller controller, SegmentView segmentView){
        this.buttonClickHandler = buttonClickHandler;
        this.setOnMouseClicked(mouseEvent -> buttonClickHandler.handle(new ActionEvent(DayInMonthView.this, null)));
        this.controller = controller;
        this.segmentView = segmentView;

        entryScrollPane = new ScrollPane();
        entryVBox = new VBox();
        changeContents(day);
        entryScrollPane.setContent(entryVBox);
        entryScrollPane.setStyle("-fx-background-insets: 1, 1, 1");
        entryScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        entryScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        numberOfDayLabel = new Label(" " + day.getDay());
        numberOfDayLabel.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0),new Insets(0))));
        resize(width,height);
        this.getChildren().add(entryScrollPane);
        this.getChildren().add(numberOfDayLabel);
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
            buttons.add(button);
        }
        for(Button b: buttons){
            b.setOnAction(buttonClickHandler);
        }
        return buttons;
    }

    /**
     * Changes the size of all components respective to the given width and height.
     * @param newWidth The new width of the component.
     * @param newHeight The new height of the component.
     */
    public void resize(double newWidth, double newHeight){
        entryScrollPane.setPrefSize(newWidth, newHeight);
        entryScrollPane.setMaxSize(newWidth, newHeight);
        for(Node b : entryVBox.getChildren()){
            ((Button)b).setPrefSize(newWidth, newHeight/4);
            ((Button) b).setFont(new Font("Arial", (double) 15 /100 * newHeight));
        }
        numberOfDayLabel.setPrefWidth(newWidth/4);
        numberOfDayLabel.setPrefHeight(newHeight/4);
        numberOfDayLabel.setFont(new Font("Arial", (double) 15 /100 * newHeight));
    }

    /**
     * Changes currentDay to the given day and regenerates the EntryButtons.
     * @param day The new day.
     */
    public void changeContents(Day day){
        currentDay = day;
        entryVBox.getChildren().clear();
        entryVBox.getChildren().addAll(generateButtons());
    }

    /**
     * Returns the day currently represented by this component.
     * @return The day currently represented by this component.
     */
    public Day getDay() {
        return currentDay;
    }
}
