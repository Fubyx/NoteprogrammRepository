package project.notizprogrammrepository.view.ViewUtils;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SwitchButton extends StackPane {
    private final Rectangle back;
    private final Button button = new Button();
    private String buttonStyleOff = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: WHITE;";
    private String buttonStyleOn = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: #00893d;";
    private boolean state;

    private void init(double height) {
        //getChildren().addAll(back, button);
        back.setFill(Color.valueOf("#ced5da"));
        Double r = height/7.5;
        button.setShape(new Circle(r));
        setAlignment(button, Pos.CENTER_LEFT);
        button.setStyle(buttonStyleOff);

        getChildren().add(back);
        getChildren().add(button);
    }

    public SwitchButton(double x, double y, double width, double height) {
        setLayoutX(x);
        setLayoutY(y);
        back = new Rectangle(width, height, Color.RED);
        init(height);
        EventHandler<Event> click = new EventHandler<Event>() {
            @Override
            public void handle(Event e) {
                if (state) {
                    button.setStyle(buttonStyleOff);
                    back.setFill(Color.valueOf("#ced5da"));
                    setAlignment(button, Pos.CENTER_LEFT);
                    state = false;
                } else {
                    button.setStyle(buttonStyleOn);
                    back.setFill(Color.valueOf("#80C49E"));
                    setAlignment(button, Pos.CENTER_RIGHT);
                    state = true;
                }
            }
        };

        button.setFocusTraversable(false);
        setOnMouseClicked(click);
        button.setOnMouseClicked(click);

        resize(x, y, width, height);
    }

    public boolean isState() {
        return state;
    }

    public void simClick(){
        button.getOnMouseClicked().handle(null);
    }
    public void resize(double x, double y, double width, double height){
        setLayoutX(x);
        setLayoutY(y);
        setPrefSize(width, height);

        back.setWidth(width);
        back.setHeight(height);
        back.setArcWidth(width);
        back.setArcHeight(height);

        ((Circle)button.getShape()).setRadius(height/7.5);
        button.setPrefSize(height, height);
    }
}
