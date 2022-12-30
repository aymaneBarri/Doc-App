module com.example.docapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.feather;
    requires com.jfoenix;

    opens com.example.docapp to javafx.fxml;
    exports com.example.docapp;
    exports com.example.docapp.controllers;
    opens com.example.docapp.controllers to javafx.fxml;
    exports com.example.docapp.controllers.patient;
    opens com.example.docapp.controllers.patient to javafx.fxml;
    exports com.example.docapp.controllers.util;
    opens com.example.docapp.controllers.util to javafx.fxml;
    exports com.example.docapp.controllers.utilisateurs;
    opens com.example.docapp.controllers.utilisateurs to javafx.fxml;
    opens com.example.docapp.controllers.visites to javafx.fxml;
    opens com.example.docapp.controllers.rendezvous to javafx.fxml;
}