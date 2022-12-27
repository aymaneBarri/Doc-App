package com.example.docapp.controllers.patient;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.models.Patient;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class PatientDetailsController implements Initializable {

    @FXML
    public Label idPatient;
    public TextField nomField;
    public DatePicker birthField;
    public TextField cinField;
    public TextField phoneField;
    public TextArea noteArea;
    public JFXButton editBtn;
    public JFXButton cancelBtn;
    public TextField prenomField;
    @FXML
    private ListView<BorderPane> listOrdonnances;
    @FXML
    private ListView<BorderPane> listVisites;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        editBtn.setOnAction(actionEvent -> {
            Patient patient = new Patient();
            patient.setLastName(nomField.getText());
            patient.setFirstName(prenomField.getText());
            patient.setBirthDate(birthField.getValue().toString());
            patient.setPhoneNumber(phoneField.getText());
            patient.setDescription(noteArea.getText());
            patient.setCin(cinField.getText());
        });

    }

    public void setData(String id){

        BorderPane root = null;
        PatientDAO dao = new PatientDAO();
        try {
            Patient patient = dao.getPatientByID(id);
            if (patient!= null) {
                nomField.setText(patient.getLastName());
                prenomField.setText(patient.getFirstName());
                birthField.setValue(LocalDate.parse(patient.getBirthDate()));
                cinField.setText(patient.getCin());
                phoneField.setText(patient.getPhoneNumber());
                noteArea.setText(patient.getDescription());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
