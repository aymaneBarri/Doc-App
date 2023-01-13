package com.example.docapp.controllers.utilisateurs;

import com.example.docapp.controllers.patient.PatientSelectItemController;
import com.example.docapp.dao.PermissionDAO;
import com.example.docapp.dao.RoleDAO;
import com.example.docapp.models.*;
import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;

public class UserPermissionController implements Initializable {
    public JFXButton saveBtn;
    public JFXButton cancelBtn;
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
    public CheckBox viewRole;
    public CheckBox addRole;
    public CheckBox editRole;
    public CheckBox deleteRole;
    public JFXButton addBtn;
    public GridPane permissionCheckBoxes;
    public boolean canModify = true;
    public boolean canDelete = true;
    boolean[] rights;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Permission permission : Utilisateur.currentUser.getRole().getPermissions()) {
            if (permission.getSubject().equals("role")) {
                rights = new boolean[]{permission.isCanView(), permission.isCanAdd(), permission.isCanModify(), permission.isCanDelete()};
                if (!permission.isCanAdd())
                    addBtn.setDisable(true);
                if (!permission.isCanModify()){
                    saveBtn.setDisable(true);
                    canModify = false;
                }
                if (!permission.isCanDelete()){
                    deleteBtn.setDisable(true);
                    canDelete = false;
                }
            }
        }

        refreshRolesList();

        listRole.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Role>() {
            @Override
            public void changed(ObservableValue<? extends Role> observableValue, Role role, Role t1) {
                permissionCheckBoxes.setVisible(true);
                if(t1 != null){
                    currentRole = t1;
                    setData(t1.getId());
                }
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

        viewRole.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!viewRole.isSelected()) {
                    addRole.setSelected(false);
                    editRole.setSelected(false);
                    deleteRole.setSelected(false);
                }
            }
        });

        addRole.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(addRole.isSelected()) {
                    viewRole.setSelected(true);
                }
            }
        });

        editRole.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(editRole.isSelected()) {
                    viewRole.setSelected(true);
                }
            }
        });

        deleteRole.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(deleteRole.isSelected()) {
                    viewRole.setSelected(true);
                }
            }
        });

        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TextInputDialog tid = new TextInputDialog();
                tid.setHeaderText("Entrez le nom du rôle");
                tid.setTitle("Nouveau rôle");

                Button okButton = (Button) tid.getDialogPane().lookupButton(ButtonType.OK);
                TextField inputField = tid.getEditor();
                BooleanBinding isInvalid = Bindings.createBooleanBinding(() -> inputField.getText().trim().isEmpty(), inputField.textProperty());
                okButton.disableProperty().bind(isInvalid);

                Optional<String> result = tid.showAndWait();

                Role role;
                int statusCode;
                if (result.isPresent()) {
                    role = new Role(result.get().trim());
                    statusCode = RoleDAO.addRole(role, null);
                } else {
                    return;
                }


                if (statusCode == 201) {
                    refreshRolesList();
                    listRole.getSelectionModel().select(listRole.getItems().size() - 1);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Rôle ajouté avec succès!");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erreur lors de l'ajout du rôle!");
                    alert.show();
                }
            }
        });

        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Vous risquez la perte des données");
                warning.setContentText("Ce rôle va être supprimé, continuer?");
                ButtonType okButton = new ButtonType("Oui", ButtonBar.ButtonData.YES);
                ButtonType cancelButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
                warning.getButtonTypes().setAll(okButton, cancelButton);
                warning.showAndWait().ifPresent(type -> {
                    if (type == okButton) {
                        int statusCode = RoleDAO.deleteRole(currentRole);
                        if (statusCode == 201) {
                            refreshRolesList();
                            permissionCheckBoxes.setVisible(false);
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setContentText("Rôle supprimé avec succès!");
                            alert.show();
                            try {
                                FXMLLoader loader = new FXMLLoader(
                                        getClass().getResource(
                                                "/com/example/docapp/view/utilisateurs/userDetails.fxml"
                                        )
                                );
                                BorderPane root;
                                root = loader.load();
                                UserDetailsController ud = loader.getController();
                                ud.rolesComboBox.getItems().clear();
                                ud.populateRolesComboBox();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Erreur lors de la suppression du rôle!");
                            alert.show();
                        }
                    }
                });
            }
        });

        saveBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Vector<Permission> permissions = new Vector<Permission>();
                Permission permissionPatient = new Permission(currentRole.getId(), "patient", viewPatient.isSelected(), addPatient.isSelected(), editPatient.isSelected(), deletePatient.isSelected());
                Permission permissionVisite = new Permission(currentRole.getId(), "visite", viewVisite.isSelected(), addVisite.isSelected(), editVisite.isSelected(), deleteVisite.isSelected());
                Permission permissionUtilisateur = new Permission(currentRole.getId(), "utilisateur", viewUser.isSelected(), addUser.isSelected(), editUser.isSelected(), deleteUser.isSelected());
                Permission permissionRdv = new Permission(currentRole.getId(), "rendez_vous", viewRdv.isSelected(), addRdv.isSelected(), editRdv.isSelected(), deleteRdv.isSelected());
                Permission permissionRole = new Permission(currentRole.getId(), "role", viewRole.isSelected(), addRole.isSelected(), editRole.isSelected(), deleteRole.isSelected());
                permissions.add(permissionPatient);
                permissions.add(permissionVisite);
                permissions.add(permissionUtilisateur);
                permissions.add(permissionRdv);
                permissions.add(permissionRole);

                int statusCode = RoleDAO.editRolePermissions(currentRole, permissions);

                if (statusCode == 201) {
                    refreshRolesList();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Rôle modifié avec succès!");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erreur lors de la modification du rôle!");
                    alert.show();
                }

                refreshRolesList();
            }
        });
    }

    public void refreshRolesList(){
        listRole.getItems().clear();
        for (Role role : RoleDAO.getRoles()) {
            listRole.getItems().add(role);
        }
    }

    public void setData(int idRole) {
        if(canModify)
            saveBtn.setDisable(rights[2] && (idRole == 1 || idRole == 2));
        if(canDelete)
            deleteBtn.setDisable(rights[3] && (idRole == 1 || idRole == 2));

        viewPatient.setSelected(false);
        viewUser.setSelected(false);
        viewRdv.setSelected(false);
        viewVisite.setSelected(false);
        viewRole.setSelected(false);

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
                case "role" -> {
                    viewRole.setSelected(permission.isCanView());
                    addRole.setSelected(permission.isCanAdd());
                    editRole.setSelected(permission.isCanModify());
                    deleteRole.setSelected(permission.isCanDelete());
                }
            }
        }
    }
}
