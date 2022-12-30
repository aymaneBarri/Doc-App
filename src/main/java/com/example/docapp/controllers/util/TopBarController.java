package com.example.docapp.controllers.util;

import com.example.docapp.models.Utilisateur;
import com.example.docapp.models.ViewModel;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TopBarController implements Initializable {
    public Label userName;
    public Label currentUserNameLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentUserNameLabel.setText(Utilisateur.currentUser.getLastName());
        userName.setText(Utilisateur.currentUser.getLastName() + " " + Utilisateur.currentUser.getFirstName());
    }

}
