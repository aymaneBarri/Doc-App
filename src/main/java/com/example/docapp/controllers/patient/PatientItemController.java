package com.example.docapp.controllers.patient;

import com.example.docapp.models.Permission;
import com.example.docapp.models.Utilisateur;
import com.example.docapp.models.ViewModel;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PatientItemController implements Initializable {
    public JFXButton newVisite;
    @FXML
    private Label patientID;
    @FXML
    private Label nomPatient;
    @FXML
    private Label prenomPatient;
    @FXML
    private Label birthPatient;
    @FXML
    private Label phonePatient;
    @FXML
    private JFXButton voirPlus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Permission permission : Utilisateur.currentPermissions) {
            if (permission.getSubject().equals("visite")) {
                if (!permission.isCanAdd())
                    newVisite.setDisable(true);
            }
            if (permission.getSubject().equals("patient")) {
                if (!permission.isCanModify() && !permission.isCanDelete())
                    voirPlus.setDisable(true);
            }
        }

        voirPlus.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                System.out.println(patientID.getText());
                ViewModel.getInstance().getViewFactory().showPatientDetails(patientID.getText());
            }
        });
        newVisite.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewModel.getInstance().getViewFactory().showNewVisite(patientID.getText());
            }
        });
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
    public void setPatientID(String patientID) {
        this.patientID.setText(patientID);
    }

}
