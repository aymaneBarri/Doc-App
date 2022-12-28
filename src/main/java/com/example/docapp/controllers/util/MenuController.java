package com.example.docapp.controllers.util;

import com.example.docapp.models.ViewModel;
import com.example.docapp.util.DBUtil;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;


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

            }
        });


    }


}
