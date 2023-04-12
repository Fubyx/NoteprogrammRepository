module project.notizprogrammrepository {
    requires javafx.controls;
    requires javafx.fxml;


    opens project.notizprogrammrepository to javafx.fxml;
    exports project.notizprogrammrepository;
}