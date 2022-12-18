package com.example.docapp.controllers;

import com.example.docapp.dataAccess.DBUtil;
import com.example.docapp.models.Utilisateur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button loginButton;

    @FXML
    protected void onLoginButtonClick() {
//        DBUtil db = new DBUtil();
//        Connection connection = db.getConnection();
//
////        String query = "Select * from utilisateur where email = '" + emailTextField.getText() + "' and password = '" + passwordTextField.getText() + "'";
//
//        try {
//            PreparedStatement psLogin = connection.prepareStatement("SELECT * FROM utilisateur WHERE email = ? AND password = ?");
//            psLogin.setString(1, emailTextField.getText());
//            psLogin.setString(2, passwordTextField.getText());
//            ResultSet queryOutput = psLogin.executeQuery();
//
//            if (queryOutput.isBeforeFirst()) {
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setContentText("Connecté!");
//                alert.show();
//            } else {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setContentText("Utilisateur non existant!");
//                alert.show();
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

//        String query = "Select * from utilisateur where email = '" + emailTextField.getText() + "' and password = '" + passwordTextField.getText() + "'";

                try {
                    Connection connection = DBUtil.getConnection();

                    PreparedStatement psLogin = connection.prepareStatement("SELECT * FROM utilisateur WHERE email = ? AND password = ?");
                    psLogin.setString(1, emailTextField.getText());
                    psLogin.setString(2, passwordTextField.getText());
                    ResultSet queryOutput = psLogin.executeQuery();

                    if (queryOutput.isBeforeFirst()) {
                        Utilisateur utilisateur = new Utilisateur();
                        while(queryOutput.next()) {
                            utilisateur = new Utilisateur(queryOutput.getInt(1), queryOutput.getString(2), queryOutput.getString(3), queryOutput.getString(4), queryOutput.getString(5), queryOutput.getString(6), queryOutput.getString(7));
                        }

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Bienvenu " + utilisateur.getFirstName() + " " + utilisateur.getLastName());
                        alert.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Utilisateur non existant!");
                        alert.show();
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    DBUtil.stopConnection();
                }
            }
        });
    }
}