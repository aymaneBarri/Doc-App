package com.example.docapp.controllers.rendezvous;

import com.example.docapp.models.ViewModel;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RdvItemController implements Initializable {
    public Label dateRdv;
    public Label nomPatient;
    public Label rdvID;
    public JFXButton voirPlus;
    public Label patientID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            voirPlus.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ViewModel.getInstance().getViewFactory().showRdvDetails(rdvID.getText(), patientID.getText());
                }
            });

    }
}
