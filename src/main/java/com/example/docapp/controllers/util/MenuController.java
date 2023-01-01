package com.example.docapp.controllers.util;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.models.Permission;
import com.example.docapp.models.Utilisateur;
import com.example.docapp.models.ViewModel;
import com.example.docapp.util.DBUtil;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    public JFXButton acceuilBtn;
    public JFXButton patientBtn;
    public JFXButton visiteBtn;
    public JFXButton userBtn;
    public JFXButton rdvBtn;
    public JFXButton logoutBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (Permission permission : Utilisateur.currentPermissions) {
            if (permission.getSubject().equals("patient")) {
                if (!permission.isCanView())
                    patientBtn.setDisable(true);
            }
            if (permission.getSubject().equals("visite")) {
                if (!permission.isCanView())
                    visiteBtn.setDisable(true);
            }
            if (permission.getSubject().equals("rendez_vous")) {
                if (!permission.isCanView())
                    rdvBtn.setDisable(true);
            }
            if (permission.getSubject().equals("utilisateur")) {
                if (!permission.isCanView())
                    userBtn.setDisable(true);
            }
        }

        patientBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
              ViewModel.getInstance().getViewFactory().showPatient();
            }
        });
        acceuilBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                ViewModel.getInstance().getViewFactory().showDashboard();
            }
        });

        userBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ViewModel.getInstance().getViewFactory().showUser();
            }
        });

        visiteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewModel.getInstance().getViewFactory().showVisite();
            }
        });

        rdvBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewModel.getInstance().getViewFactory().showRdv();
            }
        });

        logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Déconnexion");
                alert.setContentText("Voulez vous vraiment se déconnecter?");
                ButtonType okButton = new ButtonType("Oui", ButtonBar.ButtonData.YES);
                ButtonType cancelButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(okButton, cancelButton);
                alert.showAndWait().ifPresent(type -> {
                    if (type == okButton) {
                        Utilisateur.currentUser = null;
                        ViewModel.getInstance().getViewFactory().showLogin();
                    }
                });
            }
        });

    }


}
