package com.example.docapp.controllers.utilisateurs;

import com.example.docapp.dao.RoleDAO;
import com.example.docapp.dao.UtilisateurDAO;
import com.example.docapp.models.Permission;
import com.example.docapp.models.Role;
import com.example.docapp.models.Utilisateur;
import com.example.docapp.models.ViewModel;
import com.example.docapp.util.DBUtil;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class NewUserController implements Initializable {
    public TextField nomField;
    public TextField prenomField;
    public TextField emailField;
    public TextField passField;
    public TextField phoneField;
    public TextField cinField;
    public JFXButton rolesBtn;
    public JFXButton saveBtn;
    public JFXButton cancelBtn;
    public ComboBox<String> rolesComboBox;
    String errorMessage = "";
    int statusCode = 0;
    static Vector<Permission> permissions = new Vector<Permission>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Permission permission : Utilisateur.currentUser.getRole().getPermissions()) {
            if (permission.getSubject().equals("role")) {
                if (!permission.isCanView())
                    rolesBtn.setDisable(true);
            }
        }

        permissions.clear();
        permissions.add(new Permission("patient",false,false,false,false));
        permissions.add(new Permission("utilisateur",false,false,false,false));
        permissions.add(new Permission("visite",false,false,false,false));
        permissions.add(new Permission("rendez_vous",false,false,false,false));

        populateRolesComboBox();

        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!formIsValid()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(errorMessage);
                    alert.show();
                } else {
                    int idRole = RoleDAO.getRoleIdByName(rolesComboBox.getSelectionModel().getSelectedItem());
                    Utilisateur utilisateur = new Utilisateur(prenomField.getText().trim(), nomField.getText().trim(), emailField.getText().trim(), passField.getText().trim(), cinField.getText().trim(), phoneField.getText().trim(), idRole);
                    Role role = new Role(idRole, rolesComboBox.getSelectionModel().getSelectedItem());
                    statusCode = UtilisateurDAO.addUtilisateur(utilisateur);

                    if (statusCode == 201) {
                        ViewModel.getInstance().getViewFactory().showUser();

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Utilisateur ajouté avec succès!");
                        alert.show();
                        Stage s = (Stage) cancelBtn.getScene().getWindow();
                        s.close();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Erreur lors de l'ajout d'utilisateur!");
                        alert.show();
                    }
                }
            }
        });

        rolesComboBox.addEventHandler(ComboBox.ON_SHOWN, event -> populateRolesComboBox());

        rolesBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewModel.getInstance().getViewFactory().showNewUserRoles(permissions);
            }
        });

        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage s = (Stage) cancelBtn.getScene().getWindow();
                s.hide();
            }
        });
    }

    public void populateRolesComboBox() {
        rolesComboBox.getItems().clear();
        rolesComboBox.getSelectionModel().select(0);
        for (Role role : RoleDAO.getRoles()){
            rolesComboBox.getItems().add(role.getName());
        }
        rolesComboBox.getSelectionModel().select(0);
    }

    public boolean formIsValid() {
        if (nomField.getText().trim().isEmpty() || prenomField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty() || passField.getText().trim().isEmpty() || cinField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty()) {
            errorMessage = "Veuillez remplir tous les champs!";
            return false;
        }

        if (!DBUtil.isValid(emailField.getText())) {
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
