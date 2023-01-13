package com.example.docapp.controllers.patient;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.models.Patient;
import com.example.docapp.models.ViewModel;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientSelectItemController implements Initializable {
    public Label nomPatient;
    public Label birthPatient;
    public Label cinPatient;
    public Label patientID;
    public JFXButton selectButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    Patient p =PatientDAO.getPatientByID(patientID.getText());
                    ViewModel.getInstance().getViewFactory().showNewRdv(patientID.getText(), p.getCin());
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
