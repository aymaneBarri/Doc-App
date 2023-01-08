package com.example.docapp.controllers.util;

import com.example.docapp.models.Utilisateur;
import com.example.docapp.models.ViewModel;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.kordamp.ikonli.javafx.FontIcon;

import javax.swing.text.View;
import java.net.URL;
import java.util.ResourceBundle;

public class TopBarController implements Initializable {
    public Label userName;
    public Label currentUserNameLabel;
    public JFXButton profileBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        profileBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewModel.getInstance().getViewFactory().showProfile();
            }
        });
        currentUserNameLabel.setText(Utilisateur.currentUser.getLastName());
        userName.setText(Utilisateur.currentUser.getLastName() + " " + Utilisateur.currentUser.getFirstName());
    }

}
