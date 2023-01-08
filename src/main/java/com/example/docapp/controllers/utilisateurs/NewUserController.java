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
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
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

        rolesComboBox.getItems().clear();
        for (Role role : RoleDAO.getRoles()){
            rolesComboBox.getItems().add(role.getName());
        }
        rolesComboBox.getSelectionModel().select(0);

//        typeUser.getItems().clear();
//        rolesBtn.setDisable(true);
//
//        typeUser.getItems().addAll(
//                "Admin",
//                "Utilisateur");
//        typeUser.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
//            if (newValue.equals("Admin")) {
//                permissions.clear();
////                permissions.add(new Permission("action",true,false,false,false));
//                permissions.add(new Permission("patient",true,true,true,true));
////                permissions.add(new Permission("permission",true,true,true,true));
//                permissions.add(new Permission("rendez_vous",true,true,true,true));
//                permissions.add(new Permission("utilisateur",true,true,true,true));
//                permissions.add(new Permission("visite",true,true,true,true));
//            }
//            else if (newValue.equals("Utilisateur")) {
//                permissions.clear();
////                permissions.add(new Permission("action",false,false,false,false));
//                permissions.add(new Permission("patient",true,false,false,false));
////                permissions.add(new Permission("permission",true,false,false,false));
//                permissions.add(new Permission("rendez_vous",true,false,false,false));
//                permissions.add(new Permission("utilisateur",true,false,false,false));
//                permissions.add(new Permission("visite",true,false,false,false));
//            }
//
//            rolesBtn.setDisable(newValue.equals("Admin"));
//        });

        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!formIsValid()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(errorMessage);
                    alert.show();

//                        Patient patient = new Patient();
//                        patient.setBirthDate(birthPicker.getValue().toString());
//                        patient.setCin(cinField.getText());
//                        patient.setFirstName(prenomField.getText());
//                        patient.setLastName(nomField.getText());
//                        patient.setPhoneNumber(phoneField.getText());
//                        patient.setDescription(noteArea.getText());
//                        int status =  PatientDAO.addPatient(actionEvent, patient);
//                        System.out.println(status);
//                        if(status == 201){
//                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                            alert.setContentText("good");
//                            alert.show();
//                            Stage s = (Stage) cancelBtn.getScene().getWindow();
//                            s.close();
//
//                            ViewModel.getInstance().getViewFactory().showPatient();
//                        }else{
//                            Alert alert = new Alert(Alert.AlertType.ERROR);
//                            alert.setContentText("not good");
//                            alert.show();
//                        }
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
