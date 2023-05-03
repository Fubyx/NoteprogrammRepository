module project.notizprogrammrepository {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;


    opens project.notizprogrammrepository to javafx.fxml;
    exports project.notizprogrammrepository;
}