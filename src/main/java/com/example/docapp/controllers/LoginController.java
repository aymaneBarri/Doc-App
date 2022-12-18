package com.example.docapp.controllers;

import com.example.docapp.dataAccess.DBUtil;
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
    private Button loginButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (emailTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Veuillez remplir tous les champs");
                    alert.show();
                }
                else if (!DBUtil.isValid(emailTextField.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Veuillez entrer une adresse email valide");
                    alert.show();
                }

                else {

                        DBUtil.login(actionEvent, emailTextField.getText(), passwordTextField.getText());

                }
            }
        });
    }
}