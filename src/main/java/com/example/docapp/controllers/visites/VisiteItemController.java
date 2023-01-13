package com.example.docapp.controllers.visites;

import com.example.docapp.models.Permission;
import com.example.docapp.models.Utilisateur;
import com.example.docapp.models.ViewModel;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class VisiteItemController implements Initializable {
    public Label dateLabel;
    public Label maladieLabel;
    public Label patientLabel;
    public Label montantLabel;
    public Label patientID;
    public JFXButton voirPlus;
    public Label visitID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Permission permission : Utilisateur.currentUser.getRole().getPermissions()) {
            if (permission.getSubject().equals("visite")) {
                if (!permission.isCanModify() && !permission.isCanDelete())
                    voirPlus.setDisable(true);
            }
        }

        voirPlus.setOnAction(event -> {
            ViewModel.getInstance().getViewFactory().showVisiteDetail(patientID.getText(),visitID.getText());

        });
    }
}
