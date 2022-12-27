package com.example.docapp.controllers.patient;

import com.example.docapp.controllers.patient.PatientItemController;
import com.example.docapp.dao.PatientDAO;
import com.example.docapp.models.Patient;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import com.jfoenix.controls.JFXButton;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class PatientsController implements Initializable {

    public ListView<BorderPane> listPatient;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PatientDAO patientDAO = new PatientDAO();
        Vector<Patient> patientList = patientDAO.getPatients();
        for (Patient patient : patientList) {
            BorderPane bp = createCard(patient.getFirstName(),patient.getLastName(), patient.getBirthDate().toString(), patient.getPhoneNumber(), patient.getId());
            listPatient.getItems().add(bp);
        }

    }

    public BorderPane createCard(String firstName, String lastName, String date, String phone, Integer id) {
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/docapp/view/patients/patientItem.fxml"
                    )
            );
            root = (BorderPane) loader.load();
            PatientItemController pc = (PatientItemController) loader.getController();
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

}
