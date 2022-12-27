package com.example.docapp.controllers.util;

import com.example.docapp.models.ViewModel;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TopBarController implements Initializable {
    public JFXButton newVisite;
    public JFXButton newPatient;
    public Label userName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newPatient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewModel.getInstance().getViewFactory().showNewPatient();
            }
        });
    }

}
