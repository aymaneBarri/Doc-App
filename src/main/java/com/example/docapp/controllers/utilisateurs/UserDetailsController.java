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
import javafx.scene.control.*;
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
    public TextField idField;
    public ComboBox<String> typeUser;
    public JFXButton deleteBtn;

    String errorMessage = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeUser.getItems().clear();
        rolesBtn.setDisable(true);

        typeUser.getItems().addAll(
                "admin",
                "utilisateur");
        typeUser.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            rolesBtn.setDisable(newValue.equals("admin"));
        });

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

                        ViewModel.getInstance().getViewFactory().showUser();

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Utilisateur modifié avec succès!");
                        alert.show();
                        Stage s = (Stage) deleteBtn.getScene().getWindow();
                        s.close();
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Current project is modified");
                alert.setContentText("Cet utilisateur va être supprimé, continuer?");
                ButtonType okButton = new ButtonType("Oui", ButtonBar.ButtonData.YES);
                ButtonType cancelButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(okButton, cancelButton);
                alert.showAndWait().ifPresent(type -> {
                    if (type == okButton) {
                        System.out.println(okButton.getText());
                        System.out.println("nononon");
                        System.out.println(UtilisateurDAO.deleteUtilisateur(Integer.parseInt(idField.getText())));
                        Stage s = (Stage) deleteBtn.getScene().getWindow();
                        s.close();

                        ViewModel.getInstance().getViewFactory().showUser();
                    }
                });
            }
        });

        rolesBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewModel.getInstance().getViewFactory().showUserRoles();
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

        return true;
    }

}
