package com.example.docapp.controllers.visites;

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

        voirPlus.setOnAction(event -> {
            System.out.println(patientID.getText());
            System.out.println(visitID.getText());
            ViewModel.getInstance().getViewFactory().showVisiteDetail(patientID.getText(),visitID.getText());

        });
        
    }
}
