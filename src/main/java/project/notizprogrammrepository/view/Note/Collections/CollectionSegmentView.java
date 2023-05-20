package project.notizprogrammrepository.view.Note.Collections;

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

/**
 * A view component representing the CollectionSegment of the application.
 */
public class CollectionSegmentView {
    /**
     * The root Group of the component containing all other elements.
     */
    private final Group root;
    /**
     * A divisive line between the 2 lists.
     */
    private final Rectangle divisionLine;
    /**
     * A label with the text "Collect by title"
     */
    private final Label titles;
    /**
     * The ScrollPane displaying the list of Buttons representing the different collections by title.
     */
    private final ScrollPane titlesScrollPane;
    /**
     * The VBox containing the list of Buttons representing the different collections bs title.
     */
    private final VBox titlesVBox;
    /**
     * A label with the text "Collect by subject"
     */
    private final Label subjects;
    /**
     * The ScrollPane displaying the list of Buttons representing the different collections by subject.
     */
    private final ScrollPane subjectsScrollPane;
    /**
     * The VBox containing the list of Buttons representing the different collections by subject.
     */
    private final VBox subjectsVBox;
    /**
     * The Editor for NoteCollections.
     */
    private final CollectionView collectionView;
    /**
     * The Controller used for access to the data.
     */
    private final Controller controller;
    /**
     * The width of the component
     */
    private double width;
    /**
     * Creates a new CollectionSegmentView of the given size at the given position.
     * @param x The x position of the component.
     * @param y The y position of the component.
     * @param width The width of the component.
     * @param height The height of the component.
     * @param controller The Controller used for access to the data.
     */
    public CollectionSegmentView(double x, double y, double width, double height, Controller controller){
        this.controller = controller;
        root = new Group();

        divisionLine = new Rectangle();
        divisionLine.setFill(Color.BLACK);
        root.getChildren().add(divisionLine);

        titles = new Label("Collected by Title");
        titles.getStyleClass().add("labels");
        root.getChildren().add(titles);

        subjects = new Label("Collected by Subject");
        subjects.getStyleClass().add("labels");
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
        this.width = width;
    }

    /**
     * Creates the buttons of all collections and adds them to their respective VBox.
     */
    private void setButtons(){
        ArrayList<String> strings = controller.switchToCollectionMode();
        for(String s : strings){
            Button b = new Button(s);
            b.setMinWidth(width / 2.53);
            b.getStyleClass().add("buttons");
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

    /**
     * Enables or disables the component.
     * @param visible true if the component is supposed to be visible.
     */
    public void setVisible(boolean visible){
        divisionLine.setVisible(visible);
        titles.setVisible(visible);
        subjects.setVisible(visible);
        subjectsScrollPane.setVisible(visible);
        titlesScrollPane.setVisible(visible);
    }

    /**
     * Checks whether the given String corresponds to the result of the toString() method of any of the possible values of subject.
     * @param s The String to be checked.
     * @return true if the given String is a subject.
     */
    private boolean isSubject(String s){
        ArrayList<Subject> subjects = new ArrayList<>(Arrays.stream(Subject.values()).toList());
        ArrayList<String>subjectTitles = new ArrayList<>();
        for(Subject subject: subjects){
            subjectTitles.add(subject.toString());
        }
        subjects.clear();
        return subjectTitles.contains(s);
    }
    /**
     * Changes the size and position of all components respective to the given values.
     * @param x The new x position of the component.
     * @param y The new y position of the component.
     * @param width The new width of the component.
     * @param height The new height of the component.
     */
    public void resize(double x, double y, double width, double height){
        this.width = width;
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
    /**
     * Refreshes the collectionLists.
     */
    public void refresh(){
        titlesVBox.getChildren().clear();
        subjectsVBox.getChildren().clear();
        setButtons();
    }
    /**
     * Returns the root Group of the component.
     * @return The root Group of the component.
     */
    public Group getRoot() {
        return root;
    }
}
