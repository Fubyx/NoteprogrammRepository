package project.notizprogrammrepository.view.Calendar.Week;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import project.notizprogrammrepository.MainApplication;
import project.notizprogrammrepository.controller.Controller;
import project.notizprogrammrepository.model.Types.Dates.Day;
import project.notizprogrammrepository.view.Calendar.Day.DayInWeekView;
import project.notizprogrammrepository.view.SegmentView;

import java.util.ArrayList;

public class WeekView {
    private double width;
    private double height;
    private VBox root;
    private HBox weekDayNameDisplay;
    private HBox days;
    private Day[] currentWeek;
    private EventHandler<ActionEvent> entryClickHandler;
    private Controller controller;
    private SegmentView segmentView;

    public WeekView (double x, double y, double width, double height, Day[] week, EventHandler<ActionEvent> entryClickHandler, Controller controller, SegmentView segmentView){
        this.currentWeek = week;
        this.entryClickHandler = entryClickHandler;
        this.width = width;
        this.height = height;
        this.segmentView = segmentView;
        this.controller = controller;
        root = new VBox();

        weekDayNameDisplay = new HBox();
        weekDayNameDisplay.getChildren().addAll(getWeekDayLabels());

        days = new HBox();
        days.getChildren().addAll(getDays());

        resize(x, y, width, height);
        root.getChildren().add(weekDayNameDisplay);
        root.getChildren().add(days);
    }
    public void resize(double x, double y, double width, double height){
        this.width = width;
        this.height = height;
        root.setLayoutX(x);
        root.setLayoutY(y);
        root.setPrefSize(width, height);

        resizeWeekDayLabels();

        resizeDays();
    }
    public void changeContents(Day[]week){
        this.currentWeek = week;
        root.getChildren().remove(days);
        root.getChildren().remove(weekDayNameDisplay);

        days.getChildren().clear();
        days.getChildren().addAll(getDays());

        weekDayNameDisplay.getChildren().clear();
        weekDayNameDisplay.getChildren().addAll(getWeekDayLabels());

        root.getChildren().add(weekDayNameDisplay);
        resizeWeekDayLabels();
        root.getChildren().add(days);
    }
    private ArrayList<Label> getWeekDayLabels(){
        ArrayList<Label> labels = new ArrayList<>();
        Label l = new Label("Mon");
        if(currentWeek[0] != null){
            l.setText("Mon " + currentWeek[0].getDay());
        }
        l.setTextFill(Color.RED);
        l.setAlignment(Pos.CENTER);
        labels.add(l);

        l = new Label("Tue");
        if(currentWeek[1] != null){
            l.setText("Tue " + currentWeek[1].getDay());
        }
        l.setTextFill(Color.RED);
        l.setAlignment(Pos.CENTER);
        labels.add(l);

        l = new Label("Wed");
        if(currentWeek[2] != null){
            l.setText("Wed " + currentWeek[2].getDay());
        }
        l.setTextFill(Color.RED);
        l.setAlignment(Pos.CENTER);
        labels.add(l);

        l = new Label("Thu");
        if(currentWeek[3] != null){
            l.setText("Thu " + currentWeek[3].getDay());
        }
        l.setTextFill(Color.RED);
        l.setAlignment(Pos.CENTER);
        labels.add(l);

        l = new Label("Fri");
        if(currentWeek[4] != null){
            l.setText("Fri " + currentWeek[4].getDay());
        }
        l.setTextFill(Color.RED);
        l.setAlignment(Pos.CENTER);
        labels.add(l);

        l = new Label("Sat");
        if(currentWeek[5] != null){
            l.setText("Sat " + currentWeek[5].getDay());
        }
        l.setTextFill(Color.RED);
        l.setAlignment(Pos.CENTER);
        labels.add(l);

        l = new Label("Sun");
        if(currentWeek[6] != null){
            l.setText("Sun " + currentWeek[6].getDay());
        }
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
    private ArrayList<Node> getDays(){
        ArrayList<Node> days = new ArrayList<>();
        for(int i = 0; i < currentWeek.length; ++i){
            if(currentWeek[i] == null){
                days.add(new Rectangle(width/7, height - height/7 * 2, Color.valueOf(MainApplication.backgroundColor)));
            }else{
                days.add(new DayInWeekView(width/7, height - height/7 * 2, currentWeek[i], entryClickHandler, controller, segmentView));
            }
        }
        return days;
    }
    private void resizeDays(){
        days.setPrefWidth(width);
        days.setPrefHeight((height/7) * 6);
        for(Node node : days.getChildren()){
            if(node instanceof  Rectangle){
                ((Rectangle) node).setWidth(width/7);
                ((Rectangle) node).setHeight(height - height/7 * 2);
            }else{
                node.resize(width/7, height - height/7 * 2);
            }
        }
    }

    public VBox getRoot() {
        return root;
    }
}
