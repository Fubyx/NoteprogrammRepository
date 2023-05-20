package project.notizprogrammrepository.view.ViewUtils;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * A button switching between 2 states.
 * Source: <a href="https://gist.github.com/meiswjn/dd64f0706085cab336e30ac7e2ef59b1">...</a>
 * The source code was modified to fit the requirements of the application.
 */
public class SwitchButton extends StackPane {
    /**
     * The background of the button.
     */
    private final Rectangle back;
    /**
     * The button that registers and handles clicks.
     */
    private final Button button = new Button();
    /**
     * The style of the switch when off.
     */
    private final String buttonStyleOff = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: WHITE;";
    /**
     * The style of the switch when on.
     */
    private final String buttonStyleOn = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: #00893d;";
    /**
     * The state of the switch.
     */
    private boolean state;
    /**
     * Creates a new SwitchButton of the given size at the given position.
     * @param x The x position of the component.
     * @param y The y position of the component.
     * @param width The width of the component.
     * @param height The height of the component.
     */
    public SwitchButton(double x, double y, double width, double height) {
        setLayoutX(x);
        setLayoutY(y);
        back = new Rectangle(width, height, Color.RED);
        EventHandler<Event> click = e -> {
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
        };

        back.setFill(Color.valueOf("#ced5da"));

        button.setFocusTraversable(false);
        setOnMouseClicked(click);
        button.setOnMouseClicked(click);
        double r = height/7.5;
        button.setShape(new Circle(r));
        setAlignment(button, Pos.CENTER_LEFT);
        button.setStyle(buttonStyleOff);

        getChildren().add(back);
        getChildren().add(button);
        resize(x, y, width, height);
    }

    /**
     * Returns the state of the button.
     * @return The state of the button.
     */
    public boolean isState() {
        return state;
    }

    /**
     * Simulates a click on the SwitchButton.
     */
    public void simClick(){
        button.getOnMouseClicked().handle(null);
    }
    /**
     * Changes the size and position of all components respective to the given values.
     * @param x The new x position of the component.
     * @param y The new y position of the component.
     * @param width The new width of the component.
     * @param height The new height of the component.
     */
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
