package project.notizprogrammrepository;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.application.Application;
import project.notizprogrammrepository.view.KalenderView;

import static javafx.application.Application.launch;

public class MainApplication extends Application {

    private final KalenderView kalenderView = new KalenderView();
    private final Group view = new Group();
    private final HBox leftHbox = new HBox();
    private final float width = 800;
    private final float height = 500;


    Rectangle background = new Rectangle();
    Rectangle hBoxBackground = new Rectangle();

    public void setBackground(){
        background.setFill(Paint.valueOf("#283747"));
        background.setHeight(height);
        background.setWidth(width);
    }
    public void setLeftHbox(){
        leftHbox.prefHeight(height);
        leftHbox.prefWidth(width / 10);

        hBoxBackground.setFill(Paint.valueOf("#222843"));
        hBoxBackground.setWidth(width / 10);
        hBoxBackground.setHeight(height);
        leftHbox.getChildren().add(hBoxBackground);
    }
    public void addElements(){
        view.getChildren().addAll(background, leftHbox, kalenderView.getView());
    }

    @Override
    public void start(Stage stage){
        setBackground();
        setLeftHbox();
        kalenderView.sethBox();
        kalenderView.setSwitchViewButton();

        kalenderView.addElements();
        addElements();

        Scene scene = new Scene(view, width, height);
        stage.setTitle("A");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}
