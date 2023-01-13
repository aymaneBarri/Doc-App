package com.example.docapp.controllers.utilisateurs;

import com.example.docapp.dao.UtilisateurDAO;
import com.example.docapp.models.Utilisateur;
import com.example.docapp.models.ViewModel;
import com.example.docapp.util.DBUtil;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    public VBox vbox;
    public TextField idField;
    public TextField nomField;
    public TextField prenomField;
    public TextField emailField;
    public PasswordField passField;
    public TextField phoneField;
    public TextField cinField;
    public JFXButton saveBtn;
    private String errorMessage ="";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            HBox root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/docapp/view/util/topBar.fxml")));
            vbox.getChildren().add(0,root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        idField.setText(String.valueOf(Utilisateur.currentUser.getId()));

        nomField.setText(Utilisateur.currentUser.getLastName());
        prenomField.setText(Utilisateur.currentUser.getFirstName());
        emailField.setText(Utilisateur.currentUser.getEmail());
        phoneField.setText(Utilisateur.currentUser.getPhoneNumber());
        cinField.setText(Utilisateur.currentUser.getCin());

        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!formIsValid()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(errorMessage);
                    alert.show();
                }else{
                    int status = 0;
                    Utilisateur user = new Utilisateur();
                    user.setId(Integer.parseInt(idField.getText()));
                    user.setEmail(emailField.getText());
                    user.setLastName(nomField.getText());
                    user.setFirstName(prenomField.getText());
                    user.setCin(cinField.getText());
                    user.setPhoneNumber(phoneField.getText());
                    user.setIdRole(Utilisateur.currentUser.getIdRole());
                    user.setRole(Utilisateur.currentUser.getRole());
                    if (passField.getText().isEmpty()){
                        status = UtilisateurDAO.editUtilisateur(user, false);
                    }else{
                        user.setPassword(passField.getText());
                        status =  UtilisateurDAO.editUtilisateur(user, true);
                    }
                    if(status == 201){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Profile edité avec succés");
                        alert.show();
                        Utilisateur.currentUser = user;
                        ViewModel.getInstance().getViewFactory().showProfile();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Erreur, veuillez réessayer");
                        alert.show();
                    }
                }
            }
        });
    }

    public boolean formIsValid() {
        if (nomField.getText().trim().isEmpty() || prenomField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty() || cinField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty()) {
            errorMessage = "Veuillez remplir tous les champs!";
            return false;
        }

        else if (!DBUtil.isValid(emailField.getText())) {
            errorMessage = "Veuillez entrer une adresse email valide!";
            return false;
        }

        try {
            Integer.parseInt(phoneField.getText().trim());
        } catch (NumberFormatException e) {
            errorMessage = "Veuillez entrer un numéro de téléphone valide!";
            return false;
        }

        return true;
    }
}
