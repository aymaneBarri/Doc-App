package com.example.docapp.controllers.utilisateurs;

import com.example.docapp.controllers.visites.VisiteItemController;
import com.example.docapp.dao.PatientDAO;
import com.example.docapp.dao.PermissionDAO;
import com.example.docapp.dao.RoleDAO;
import com.example.docapp.dao.UtilisateurDAO;
import com.example.docapp.models.Patient;
import com.example.docapp.models.Permission;
import com.example.docapp.models.Role;
import com.example.docapp.models.Utilisateur;
import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class UserPermissionController implements Initializable {
    public JFXButton saveBtn;
    public JFXButton cancelBtn;
    public ListView<BorderPane> listRoles;
    public CheckBox viewPatient;
    public CheckBox addPatient;
    public CheckBox editPatient;
    public CheckBox deletePatient;
    public CheckBox viewUser;
    public CheckBox addUser;
    public CheckBox editUser;
    public CheckBox deleteUser;
    public CheckBox viewVisite;
    public CheckBox addVisite;
    public CheckBox editVisite;
    public CheckBox deleteVisite;
    public CheckBox viewRdv;
    public CheckBox addRdv;
    public CheckBox editRdv;
    public CheckBox deleteRdv;
    public JFXButton deleteBtn;
    public static Role currentRole;
    public ListView<Role> listRole;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Role role : RoleDAO.getRoles()) {
            listRole.getItems().add(role);
        }

        listRole.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Role>() {
            @Override
            public void changed(ObservableValue<? extends Role> observableValue, Role role, Role t1) {
                currentRole = t1;
                setData(t1.getId());
            }
        });

        viewPatient.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!viewPatient.isSelected()) {
                    addPatient.setSelected(false);
                    editPatient.setSelected(false);
                    deletePatient.setSelected(false);
                }
            }
        });

        addPatient.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(addPatient.isSelected()) {
                    viewPatient.setSelected(true);
                }
            }
        });

        editPatient.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(editPatient.isSelected()) {
                    viewPatient.setSelected(true);
                }
            }
        });

        deletePatient.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(deletePatient.isSelected()) {
                    viewPatient.setSelected(true);
                }
            }
        });

        viewUser.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!viewUser.isSelected()) {
                    addUser.setSelected(false);
                    editUser.setSelected(false);
                    deleteUser.setSelected(false);
                }
            }
        });

        addUser.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(addUser.isSelected()) {
                    viewUser.setSelected(true);
                }
            }
        });

        editUser.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(editUser.isSelected()) {
                    viewUser.setSelected(true);
                }
            }
        });

        deleteUser.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(deleteUser.isSelected()) {
                    viewUser.setSelected(true);
                }
            }
        });

        viewVisite.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!viewVisite.isSelected()) {
                    addVisite.setSelected(false);
                    editVisite.setSelected(false);
                    deleteVisite.setSelected(false);
                }
            }
        });

        addVisite.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(addVisite.isSelected()) {
                    viewVisite.setSelected(true);
                }
            }
        });

        editVisite.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(editVisite.isSelected()) {
                    viewVisite.setSelected(true);
                }
            }
        });

        deleteVisite.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(deleteVisite.isSelected()) {
                    viewVisite.setSelected(true);
                }
            }
        });

        viewRdv.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!viewRdv.isSelected()) {
                    addRdv.setSelected(false);
                    editRdv.setSelected(false);
                    deleteRdv.setSelected(false);
                }
            }
        });

        addRdv.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(addRdv.isSelected()) {
                    viewRdv.setSelected(true);
                }
            }
        });

        editRdv.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(editRdv.isSelected()) {
                    viewRdv.setSelected(true);
                }
            }
        });

        deleteRdv.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(deleteRdv.isSelected()) {
                    viewRdv.setSelected(true);
                }
            }
        });

        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage s = (Stage) deleteBtn.getScene().getWindow();
                s.hide();
            }
        });

        saveBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Vector<Permission> permissions = new Vector<Permission>();
                Permission permissionPatient = new Permission("patient", viewPatient.isSelected(), addPatient.isSelected(), editPatient.isSelected(), deletePatient.isSelected());
                Permission permissionVisite = new Permission("visite", viewVisite.isSelected(), addVisite.isSelected(), editVisite.isSelected(), deleteVisite.isSelected());
                Permission permissionUtilisateur = new Permission("utilisateur", viewUser.isSelected(), addUser.isSelected(), editUser.isSelected(), deleteUser.isSelected());
                Permission permissionRdv = new Permission("rendez_vous", viewRdv.isSelected(), addRdv.isSelected(), editRdv.isSelected(), deleteRdv.isSelected());
                permissions.add(permissionPatient);
                permissions.add(permissionVisite);
                permissions.add(permissionUtilisateur);
                permissions.add(permissionRdv);

                RoleDAO.editRolePermissions(currentRole, permissions);

                Stage s = (Stage) saveBtn.getScene().getWindow();
                s.hide();
            }
        });
    }

    public void setData(int idRole) {
        viewPatient.setSelected(false);
        viewUser.setSelected(false);
        viewRdv.setSelected(false);
        viewVisite.setSelected(false);

        Vector<Permission> permissions = PermissionDAO.getPermissionsByRole(idRole);

        for (Permission permission : permissions) {
            switch (permission.getSubject()) {
                case "patient" -> {
                    viewPatient.setSelected(permission.isCanView());
                    addPatient.setSelected(permission.isCanAdd());
                    editPatient.setSelected(permission.isCanModify());
                    deletePatient.setSelected(permission.isCanDelete());
                }
                case "utilisateur" -> {
                    viewUser.setSelected(permission.isCanView());
                    addUser.setSelected(permission.isCanAdd());
                    editUser.setSelected(permission.isCanModify());
                    deleteUser.setSelected(permission.isCanDelete());
                }
                case "visite" -> {
                    viewVisite.setSelected(permission.isCanView());
                    addVisite.setSelected(permission.isCanAdd());
                    editVisite.setSelected(permission.isCanModify());
                    deleteVisite.setSelected(permission.isCanDelete());
                }
                case "rendez_vous" -> {
                    viewRdv.setSelected(permission.isCanView());
                    addRdv.setSelected(permission.isCanAdd());
                    editRdv.setSelected(permission.isCanModify());
                    deleteRdv.setSelected(permission.isCanDelete());
                }
            }
        }
    }
}
