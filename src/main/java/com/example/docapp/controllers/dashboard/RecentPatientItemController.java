package com.example.docapp.controllers.dashboard;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RecentPatientItemController implements Initializable {
    public Label nomPatient;
    public Label prenomPatient;
    public Label birthPatient;
    public Label phonePatient;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public void setNomPatient(String nomPatient) {
        this.nomPatient.setText(nomPatient);
    }

    public void setPrenomPatient(String prenomPatient) {
        this.prenomPatient.setText(prenomPatient);
    }

    public void setBirthPatient(String birthPatient) {
        this.birthPatient.setText(birthPatient);
    }

    public void setPhonePatient(String phonePatient) {
        this.phonePatient.setText(phonePatient);
    }
}
