module com.example.docapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.docapp to javafx.fxml;
    exports com.example.docapp;
    exports com.example.docapp.controllers;
    opens com.example.docapp.controllers to javafx.fxml;
}