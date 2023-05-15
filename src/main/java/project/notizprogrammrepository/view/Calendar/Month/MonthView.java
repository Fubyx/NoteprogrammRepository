package project.notizprogrammrepository.view.Calendar.Month;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import project.notizprogrammrepository.MainApplication;
import project.notizprogrammrepository.controller.Controller;
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.model.Types.Dates.Month;
import project.notizprogrammrepository.model.Types.entries.Entry;
import project.notizprogrammrepository.view.Calendar.Day.DayInMonthView;
import project.notizprogrammrepository.view.SegmentView;

import java.util.ArrayList;

/*
Plan:
constructor -> width, height, month

VBox -> Split into 2 Parts:
- HBox (width, height/7): Mon, Tue, Wed, Thu, Fri, Sat, Sun --> labels each width/7 height/7
- "Table": VBox (width, height - height/7) with 6 HBoxes -> each (width, height/7)
    - each HBox -> 7 DayInMonthView -> each (width/7, height/7)

Contains:
- a CalendarEntryView that gets assigned the Entry before being displayed
 */
public class MonthView {
    private double width;
    private double height;
    private VBox root;
    private HBox weekDayNameDisplay;
    private VBox table;
    private ArrayList<DayInMonthView> dMVs = new ArrayList<>();
    private Month currentMonth;
    private EventHandler<ActionEvent> entryClickHandler;
    private Controller controller;
    private SegmentView segmentView;

    public MonthView (double x, double y, double width, double height, Month month, EventHandler<ActionEvent> entryClickHandler, Controller controller, SegmentView segmentView){
        this.currentMonth = month;
        this.entryClickHandler = entryClickHandler;
        this.width = width;
        this.height = height;
        this.controller = controller;
        this.segmentView = segmentView;
        root = new VBox();

        weekDayNameDisplay = new HBox();
        weekDayNameDisplay.getChildren().addAll(getWeekDayLabels());

        table = new VBox();
        table.getChildren().addAll(getTableRows());

        resize(x, y, width, height);
        root.getChildren().add(weekDayNameDisplay);
        root.getChildren().add(table);
    }
    public void resize(double x, double y, double width, double height){
        this.width = width;
        this.height = height;
        root.setLayoutX(x);
        root.setLayoutY(y);
        root.setPrefSize(width, height);
        resizeWeekDayLabels();
        resizeTableRows();
    }
    public void changeContents(Month month){
        this.currentMonth = month;
        root.getChildren().remove(table);
        table.getChildren().clear();
        table.getChildren().addAll(getTableRows());
        root.getChildren().add(table);
    }
    private ArrayList<Label> getWeekDayLabels(){
        ArrayList<Label> labels = new ArrayList<>();
        Label l = new Label("Mon");
        l.setTextFill(Color.RED);
        l.setAlignment(Pos.CENTER);
        labels.add(l);
        l = new Label("Tue");
        l.setTextFill(Color.RED);
        l.setAlignment(Pos.CENTER);
        labels.add(l);
        l = new Label("Wed");
        l.setTextFill(Color.RED);
        l.setAlignment(Pos.CENTER);
        labels.add(l);
        l = new Label("Thu");
        l.setTextFill(Color.RED);
        l.setAlignment(Pos.CENTER);
        labels.add(l);
        l = new Label("Fri");
        l.setTextFill(Color.RED);
        l.setAlignment(Pos.CENTER);
        labels.add(l);
        l = new Label("Sat");
        l.setTextFill(Color.RED);
        l.setAlignment(Pos.CENTER);
        labels.add(l);
        l = new Label("Sun");
        l.setTextFill(Color.RED);
        l.setAlignment(Pos.CENTER);
        labels.add(l);
        return labels;
    }
    private void resizeWeekDayLabels(){
        weekDayNameDisplay.setPrefSize(width, height/7);
        for(Node n : weekDayNameDisplay.getChildren()){
            ((Label)n).setPrefSize(width/7, height/7);
            ((Label) n).setFont(new Font("Arial", (double) 15 /500 * height));
        }
    }
    private ArrayList<HBox> getTableRows(){
        ArrayList<HBox>hBoxes = new ArrayList<>();
        for(int i = 0; i < 6; ++i){
            HBox box = new HBox();
            Day[]days = currentMonth.getWeek(i);
            if(days == null){
                box.getChildren().add(new Rectangle(width, height/7, Paint.valueOf(MainApplication.backgroundColor)));
            }else{
                for(Day d : days){
                    if(d == null){
                        box.getChildren().add(new Rectangle(width/7, height/7, Paint.valueOf(MainApplication.backgroundColor)));
                    }else{
                        DayInMonthView dMV = new DayInMonthView(width/7, height/7, d, entryClickHandler, controller, segmentView);
                        box.getChildren().add(dMV);
                        dMVs.add(dMV);
                    }
                }
            }
            box.setStyle(".hbox:focused {-fx-focus-color: transparent;}");
            hBoxes.add(box);
        }
        return hBoxes;
    }
    private void resizeTableRows(){
        for(Node node: table.getChildren()){
            HBox hBox = (HBox) node;
            for(Node node1 : hBox.getChildren()){
                if(node1 instanceof Rectangle){
                    ((Rectangle) node1).setHeight(height/7);
                    if(hBox.getChildren().size() == 1){
                        ((Rectangle) node1).setWidth(width);
                    }else{
                        ((Rectangle) node1).setWidth(width/7);
                    }
                }
            }
        }
        for(DayInMonthView dMV : dMVs){
            dMV.resize(width/7, height/7);
        }
    }

    public VBox getRoot() {
        return root;
    }
}
