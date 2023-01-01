package com.example.docapp.controllers.utilisateurs;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.dao.RoleDAO;
import com.example.docapp.dao.UtilisateurDAO;
import com.example.docapp.models.*;
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
    public JFXButton deleteBtn;
    public ComboBox<Role> rolesComboBox;

    String errorMessage = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Permission permission : Utilisateur.currentPermissions) {
            if (permission.getSubject().equals("utilisateur")) {
                if (!permission.isCanModify())
                    saveBtn.setDisable(true);
                else if (!permission.isCanDelete())
                    deleteBtn.setDisable(true);
            }
        }

        rolesComboBox.getItems().clear();
        for (Role role : RoleDAO.getRoles()){
            rolesComboBox.getItems().add(role);
        }
        rolesComboBox.getSelectionModel().select(0);

//        typeUser.getItems().clear();
////        rolesBtn.setDisable(true);
//
//        typeUser.getItems().addAll(
//                "admin",
//                "utilisateur");
//        typeUser.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
//            rolesBtn.setDisable(newValue.equals("admin"));
//        });

        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!formIsValid()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(errorMessage);
                    alert.show();
                } else {
                    Utilisateur utilisateur = new Utilisateur(Integer.parseInt(idField.getText().trim()), prenomField.getText().trim(), nomField.getText().trim(), emailField.getText().trim(), passField.getText().trim(), cinField.getText().trim(), phoneField.getText().trim(), rolesComboBox.getSelectionModel().getSelectedItem().getId());
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

    public void setData(Utilisateur user){
        BorderPane root = null;
        try {
//            Utilisateur user = UtilisateurDAO.getUserByID(id);
            this.setIdField(String.valueOf(user.getId()));
            this.setNomField(user.getFirstName());
            this.setPrenomField(user.getLastName());
            this.setEmailField(user.getEmail());
            this.setCinField(user.getCin());
            this.setPhoneField(user.getPhoneNumber());
            this.setRolesComboBox(RoleDAO.getRoleById(user.getIdRole()));

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
    public void setRolesComboBox(Role role){
        this.rolesComboBox.getSelectionModel().select(role);;
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
