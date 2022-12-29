package com.example.docapp.controllers.patient;

import com.example.docapp.controllers.patient.PatientItemController;
import com.example.docapp.dao.PatientDAO;
import com.example.docapp.models.Patient;
import com.example.docapp.models.ViewModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import com.jfoenix.controls.JFXButton;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

public class PatientsController implements Initializable {

    public ListView<BorderPane> listPatient;
    public TextField searchField;
    public JFXButton newPatient;
    public JFXButton searchBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PatientDAO patientDAO = new PatientDAO();
        Vector<Patient> patientList = patientDAO.getPatients();
        for (Patient patient : patientList) {
            BorderPane bp = createCard(patient.getFirstName(),patient.getLastName(), patient.getBirthDate(), patient.getPhoneNumber(), patient.getId());
            listPatient.getItems().add(bp);
        }

        newPatient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewModel.getInstance().getViewFactory().showNewPatient();
            }
        });



        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String search = searchField.getText();
                listPatient.getItems().clear();
                Vector<Patient> patientList = PatientDAO.searchPatients(search);
                for (Patient patient : patientList) {
                    BorderPane bp = createCard(patient.getFirstName(),patient.getLastName(), patient.getBirthDate(), patient.getPhoneNumber(), patient.getId());
                    listPatient.getItems().add(bp);
                }
            }
        });

    }


    public BorderPane createCard(String firstName, String lastName, String date, String phone, Integer id) {
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/docapp/view/patients/patientItem.fxml"
                    )
            );

            root = loader.load();
            PatientItemController pc = loader.getController();
            pc.setNomPatient(lastName);
            pc.setBirthPatient(date);
            pc.setPrenomPatient(firstName);
            pc.setPhonePatient(phone);
            pc.setPatientID(String.valueOf(id));


        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;

    }



    public void refresh() {
        PatientDAO patientDAO = new PatientDAO();
        Vector<Patient> patientList = patientDAO.getPatients();
        for (Patient patient : patientList) {
            BorderPane bp = createCard(patient.getFirstName(),patient.getLastName(), patient.getBirthDate(), patient.getPhoneNumber(), patient.getId());
            listPatient.getItems().add(bp);
        }
    }

}
