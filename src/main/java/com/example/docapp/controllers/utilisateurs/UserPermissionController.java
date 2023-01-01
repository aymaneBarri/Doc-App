package com.example.docapp.controllers.utilisateurs;

import com.example.docapp.dao.UtilisateurDAO;
import com.example.docapp.models.Permission;
import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage s = (Stage) cancelBtn.getScene().getWindow();
                s.hide();
            }
        });

        saveBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Permission permissionPatient = new Permission("patient", viewPatient.isSelected(), addPatient.isSelected(), editPatient.isSelected(), deletePatient.isSelected());
                Permission permissionUtilisateur = new Permission("utilisateur", viewUser.isSelected(), addUser.isSelected(), editUser.isSelected(), deleteUser.isSelected());
                Permission permissionVisite = new Permission("visite", viewVisite.isSelected(), addVisite.isSelected(), editVisite.isSelected(), deleteVisite.isSelected());
                Permission permissionRdv = new Permission("rendez_vous", viewRdv.isSelected(), addRdv.isSelected(), editRdv.isSelected(), deleteRdv.isSelected());

                NewUserController.permissions.clear();
                NewUserController.permissions.add(permissionPatient);
                NewUserController.permissions.add(permissionUtilisateur);
                NewUserController.permissions.add(permissionVisite);
                NewUserController.permissions.add(permissionRdv);

                Stage s = (Stage) cancelBtn.getScene().getWindow();
                s.hide();
            }
        });
    }

    public void setData(int id, Vector<Permission> permissions) {
        if(id != 0)
            permissions = UtilisateurDAO.getUserPermissions(id);

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
