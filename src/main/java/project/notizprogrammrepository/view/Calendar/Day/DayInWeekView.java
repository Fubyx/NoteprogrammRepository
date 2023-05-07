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
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.view.ViewUtils.EntryButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*
Plan:

 */
public class DayInWeekView extends Group {
    private final ScrollPane root;
    private final VBox entryVBox;
    private final Day currentDay;
    private final EventHandler<ActionEvent> buttonClickHandler;
    private double width;
    private double height;
    public DayInWeekView (double width, double height, Day day, EventHandler<ActionEvent> buttonClickHandler){
        this.currentDay = day;
        this.buttonClickHandler = buttonClickHandler;
        this.width = width;
        this.height = height;
        this.root = new ScrollPane();

        entryVBox = new VBox();
        entryVBox.getChildren().addAll(generateButtons());
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                buttonClickHandler.handle(new ActionEvent(DayInWeekView.this, null));
            }
        });
        root.setContent(entryVBox);
        root.setStyle("-fx-background-insets: 1, 1, 1");

        root.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        root.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        root.setContent(entryVBox);

        this.getChildren().add(root);
        resize(width, height);
    }

    private ArrayList<Button> generateButtons(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        ArrayList<Button> buttons = new ArrayList<>();
        for(Entry e: currentDay.getEntries()){
            EntryButton button = new EntryButton(e.getTitle().substring(0, Math.min(10, e.getTitle().length())) + "  " + sdf.format(e.getDate()));
            button.setEntry(e);
            button.setOnAction(buttonClickHandler);
            buttons.add(button);
        }
        return buttons;
    }
    public void resize(double width, double height){
        this.width = width;
        this.height = height;
        root.setPrefSize(width, height);
        for(Node b : entryVBox.getChildren()){
            ((Button)b).setPrefSize(width, height/10);
            ((Button) b).setFont(new Font("Arial", (double) 15 /500 * height));
        }
    }
    public Day getDay() {
        return currentDay;
    }
}
