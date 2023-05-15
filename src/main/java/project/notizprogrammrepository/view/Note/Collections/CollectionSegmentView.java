package project.notizprogrammrepository.view.Note.Collections;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import project.notizprogrammrepository.controller.Controller;
import project.notizprogrammrepository.model.Types.entries.Subject;

import java.util.ArrayList;
import java.util.Arrays;

/*
Plan:
Border of size/20 around it
Black Line of width size/20 in the middle
Each side:
    distance size/20 from all Borders
    Label Titles/Subjects
    ScrollPane with Buttons --> Title/Subject of the Collection (+ later date of the latest note)
 */
public class CollectionSegmentView {
    private final Group root;
    private final Rectangle divisionLine;
    private final Label titles;
    private final ScrollPane titlesScrollPane;
    private final VBox titlesVBox;
    private final Label subjects;
    private final ScrollPane subjectsScrollPane;
    private final VBox subjectsVBox;
    private final CollectionView collectionView;
    private final Controller controller;

    public CollectionSegmentView(double x, double y, double width, double height, Controller controller){
        this.controller = controller;
        root = new Group();

        divisionLine = new Rectangle();
        divisionLine.setFill(Color.BLACK);
        root.getChildren().add(divisionLine);

        titles = new Label("Collected by Title");
        titles.setTextFill(Color.GREEN);
        root.getChildren().add(titles);

        subjects = new Label("Collected by Subject");
        subjects.setTextFill(Color.GREEN);
        root.getChildren().add(subjects);

        titlesVBox = new VBox();
        subjectsVBox = new VBox();

        setButtons();

        titlesScrollPane = new ScrollPane();
        titlesScrollPane.setContent(titlesVBox);
        titlesScrollPane.setStyle("-fx-background-insets: 1, 1, 1");
        titlesScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        titlesScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        root.getChildren().add(titlesScrollPane);

        subjectsScrollPane = new ScrollPane();
        subjectsScrollPane.setContent(subjectsVBox);
        subjectsScrollPane.setStyle("-fx-background-insets: 1, 1, 1");
        subjectsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        subjectsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        root.getChildren().add(subjectsScrollPane);

        collectionView = new CollectionView(0, 0, width, height, controller, CollectionSegmentView.this);
        root.getChildren().add(collectionView.getRoot());

        resize(x, y, width, height);
        root.setVisible(false);
    }
    private void setButtons(){
        ArrayList<String> strings = controller.switchToCollectionMode();
        for(String s : strings){
            Button b = new Button(s);
            b.setOnAction(actionEvent -> {
                setVisible(false);
                collectionView.display(b.getText());
            });
            if(isSubject(s)){
                subjectsVBox.getChildren().add(b);
            }else{
                titlesVBox.getChildren().add(b);
            }
        }
    }
    public void setVisible(boolean visible){
        divisionLine.setVisible(visible);
        titles.setVisible(visible);
        subjects.setVisible(visible);
        subjectsScrollPane.setVisible(visible);
        titlesScrollPane.setVisible(visible);
    }
    private boolean isSubject(String s){
        ArrayList<Subject> subjects = new ArrayList<>(Arrays.stream(Subject.values()).toList());
        ArrayList<String>subjectTitles = new ArrayList<>();
        for(Subject subject: subjects){
            subjectTitles.add(subject.toString());
        }
        subjects.clear();
        return subjectTitles.contains(s);
    }
    public void resize(double x, double y, double width, double height){
        root.setLayoutX(x);
        root.setLayoutY(y);

        divisionLine.setLayoutX(width/2 - width/40);
        divisionLine.setLayoutY(height/20);
        divisionLine.setWidth(width/20);
        divisionLine.setHeight(height - height/10);

        titles.setLayoutX(width/20);
        titles.setLayoutY(height/20);
        titles.setPrefSize(width/2 - width/10, height/10);
        titles.setFont(new Font("Arial", (double) 15 /500 * height));

        titlesScrollPane.setLayoutX(width/20);
        titlesScrollPane.setLayoutY(height/5);
        titlesScrollPane.setPrefSize(width/2 - width/10, height - height/5 - height/20);
        for(Node n : titlesVBox.getChildren()){
            Button b = (Button) n;
            b.setPrefSize(width/2 - width/10, (height - height/5 - height/20)/10);
        }

        subjects.setLayoutX(width/2 + width/20);
        subjects.setLayoutY(height/20);
        subjects.setPrefSize(width/2 - width/10, height/10);
        subjects.setFont(new Font("Arial", (double) 15 /500 * height));

        subjectsScrollPane.setLayoutX(width/2 + width/20);
        subjectsScrollPane.setLayoutY(height/5);
        subjectsScrollPane.setPrefSize(width/2 - width/10, height - height/5 - height/20);
        for(Node n : subjectsVBox.getChildren()){
            Button b = (Button) n;
            b.setPrefSize(width/2 - width/10, (height - height/5 - height/20)/10);
        }

        collectionView.resize(0, 0, width, height);
    }

    public void refresh(){
        titlesVBox.getChildren().clear();
        subjectsVBox.getChildren().clear();
        setButtons();
    }
    public Group getRoot() {
        return root;
    }
}
