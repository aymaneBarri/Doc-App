package com.example.docapp.controllers.utilisateurs;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.dao.UtilisateurDAO;
import com.example.docapp.models.Patient;
import com.example.docapp.models.Utilisateur;
import com.example.docapp.models.ViewModel;
import com.example.docapp.util.DBUtil;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UserDetailsController implements Initializable {
    public TextField nomField;
    public TextField prenomField;
    public TextField emailField;
    public TextField passField;
    public TextField phoneField;
    public TextField cinField;
    public JFXButton rolesBtn;
    public JFXButton saveBtn;
    public JFXButton cancelBtn;
    public TextField idField;

    String errorMessage = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!formIsValid()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(errorMessage);
                    alert.show();
                } else {
                    try {
                        Utilisateur utilisateur = new Utilisateur(Integer.parseInt(idField.getText().trim()), prenomField.getText().trim(), nomField.getText().trim(), emailField.getText().trim(), passField.getText().trim(), cinField.getText().trim(), phoneField.getText().trim());
                        if (passField.getText().isEmpty()){
                            UtilisateurDAO.editUtilisateur(utilisateur,false);
                        }
                        else {
                            UtilisateurDAO.editUtilisateur(utilisateur,true);
                        }



                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Utilisateur modifié avec succès!");
                        alert.show();
                        Stage s = (Stage) cancelBtn.getScene().getWindow();
                        s.close();
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage s = (Stage) cancelBtn.getScene().getWindow();
                s.hide();
            }
        });

        rolesBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewModel.getInstance().getViewFactory().showUserRoles(idField.getText());
            }
        });
    }

    public void setData(String id){
        BorderPane root = null;
        UtilisateurDAO dao = new UtilisateurDAO();
        try {
            Utilisateur user = dao.getUserByID(id);
            if (user!= null) {
                this.setIdField(String.valueOf(user.getId()));
                this.setNomField(user.getFirstName());
                this.setPrenomField(user.getLastName());
                this.setEmailField(user.getEmail());
                this.setCinField(user.getCin());
                this.setPhoneField(user.getPhoneNumber());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setIdField(String id) {
        this.idField.setText(id);
    }

    public void setNomField(String text){
        this.nomField.setText(text);
    }
    public void setPrenomField(String text){
        this.prenomField.setText(text);
    }
    public void setEmailField(String text){
        this.emailField.setText(text);
    }
    public void setPassField(String text){
        this.passField.setText(text);
    }
    public void setPhoneField(String text){
        this.phoneField.setText(text);
    }
    public void setCinField(String text){
        this.cinField.setText(text);
    }

    public boolean formIsValid() {
        if(nomField.getText().trim().isEmpty() || prenomField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty() ||  cinField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty()){
            errorMessage = "Veuillez remplir tous les champs!";
            return false;
        }

        if(!DBUtil.isValid(emailField.getText())){
            errorMessage = "Veuillez entrer une adresse email valide!";
            return false;
        }

        try {
            Integer.parseInt(phoneField.getText().trim());
        } catch (NumberFormatException e) {
            errorMessage = "Veuillez entrer un numéro de téléphone valide!";
            return false;
        }

//        if (nomField.getText().isEmpty()) {
//            errorMessage += " Nom est obligatoire";
//        }
//        if (prenomField.getText().isEmpty()) {
//            errorMessage += " Prenom est obligatoire";
//        }
//        if (cinField.getText().isEmpty()) {
//            errorMessage += " CIN est obligatoire";
//        }
//        if (phoneField.getText().isEmpty()) {
//            errorMessage += " Phone est obligatoire";
//        }
//        if (birthField.getValue() == null) {
//            errorMessage += " Date de naissance est obligatoire";
//        }
//        try{
//            if (birthField.getValue().isAfter(LocalDate.now())) {
//                errorMessage += " Date de naissance est invalide";
//            }
//        } catch (Exception e) {
//            errorMessage += " Date de naissance est invalide";
//        }
//        try {
//
//            Integer.parseInt(phoneField.getText().trim());
//        } catch (Exception e) {
//            errorMessage += " Numero de telephone est invalide";
//        }
//        return errorMessage;

        return true;
    }

}
