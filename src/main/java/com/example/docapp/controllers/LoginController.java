package com.example.docapp.controllers;

import com.example.docapp.ViewFactory;
import com.example.docapp.models.ViewModel;
import com.example.docapp.util.DBUtil;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private JFXButton loginButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int statusCode = 0;
                if (emailTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Veuillez remplir tous les champs");
                    alert.show();
                } else if (!DBUtil.isValid(emailTextField.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Veuillez entrer une adresse email valide");
                    alert.show();
                } else {
                    statusCode = DBUtil.login(actionEvent, emailTextField.getText(), passwordTextField.getText());
                }

                if (statusCode == 200) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Bienvenue");
                    alert.show();
                    ViewModel.getInstance().getViewFactory().showDashboard();
                } else if (statusCode == 400) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Informations incorrectes!");
                    alert.show();
                }
            }
        });
    }
}