package com.example.docapp.controllers.patient;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewPatientController implements Initializable {
    public TextField nomField;
    public TextField prenomField;
    public TextField cinField;
    public DatePicker birthPicker;
    public TextField phoneField;
    public TextArea noteArea;
    public JFXButton addBtn;
    public JFXButton cancelBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage s = (Stage) cancelBtn.getScene().getWindow();
                s.hide();
            }
        });
    }
}
