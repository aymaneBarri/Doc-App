package com.example.docapp.controllers.patient;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.models.Patient;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class PatientSelectController implements Initializable {
    public ListView<BorderPane> listPatient;
    public Label idPatient;
    public TextField searchField;
    public JFXButton searchBtn;
    public JFXButton cancelBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PatientDAO patientDAO = new PatientDAO();
        Vector<Patient> patientList = patientDAO.getPatients();
        for (Patient patient : patientList) {
            BorderPane bp = createCard(patient.getFirstName(),patient.getLastName(), patient.getBirthDate(), patient.getCin(), patient.getId());
            listPatient.getItems().add(bp);
        }

        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage s = (Stage) cancelBtn.getScene().getWindow();
                s.close();
            }
        });

        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String search = searchField.getText();
                if(!search.isEmpty()){
                    listPatient.getItems().clear();
                    Vector<Patient> patientList = PatientDAO.searchPatients(search);
                    for (Patient patient : patientList) {
                        BorderPane bp = createCard(patient.getFirstName(),patient.getLastName(), patient.getBirthDate(), patient.getCin(), patient.getId());
                        listPatient.getItems().add(bp);
                    }
                }
            }
        });
    }


    public BorderPane createCard(String firstName, String lastName, String date, String cin, Integer id) {
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/docapp/view/patients/patientSelectItem.fxml"
                    )
            );

            root = loader.load();
            PatientSelectItemController pc = loader.getController();
            pc.nomPatient.setText(lastName + " " + firstName);
            pc.birthPatient.setText(date);
            pc.cinPatient.setText(cin);
            pc.patientID.setText(String.valueOf(id));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;

    }
}
