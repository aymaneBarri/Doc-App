package com.example.docapp.controllers.visites;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NewVisiteController implements Initializable {
    public JFXButton addBtn;
    public JFXButton cancelBtn;
    public TextField cinField;
    public DatePicker datePicker;
    public TextField illnessField;
    public TextArea noteArea;
    public TextField montantField;
    public TextField assuranceField;
    public TextField hourField;
    public TextArea prescriptionField;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
