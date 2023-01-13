package com.example.docapp.controllers.utilisateurs;

import com.example.docapp.dao.RoleDAO;
import com.example.docapp.dao.UtilisateurDAO;
import com.example.docapp.dao.*;
import com.example.docapp.models.*;
import com.example.docapp.util.DBUtil;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

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
    public ListView<BorderPane> listAction;
    public Label idLabel;

    String errorMessage = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Permission permission : Utilisateur.currentUser.getRole().getPermissions()) {
            if (permission.getSubject().equals("utilisateur")) {
                if (!permission.isCanModify())
                    saveBtn.setDisable(true);
                else if (!permission.isCanDelete())
                    deleteBtn.setDisable(true);
            } else if (permission.getSubject().equals("role")) {
                if (!permission.isCanView())
                    rolesBtn.setDisable(true);
            }
        }

        populateRolesComboBox();

        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!formIsValid()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(errorMessage);
                    alert.show();
                } else {
                    Utilisateur utilisateur = new Utilisateur(Integer.parseInt(idField.getText().trim()), prenomField.getText().trim(), nomField.getText().trim(), emailField.getText().trim(), passField.getText().trim(), cinField.getText().trim(), phoneField.getText().trim(), rolesComboBox.getSelectionModel().getSelectedItem().getId());
                    UtilisateurDAO.editUtilisateur(utilisateur, !passField.getText().isEmpty());

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
                alert.setTitle("Suppression d'utilisateur");
                alert.setContentText("Cet utilisateur va être supprimé, continuer?");
                ButtonType okButton = new ButtonType("Oui", ButtonBar.ButtonData.YES);
                ButtonType cancelButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(okButton, cancelButton);
                alert.showAndWait().ifPresent(type -> {
                    if (type == okButton) {
                        UtilisateurDAO.deleteUtilisateur(Integer.parseInt(idField.getText()));
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

        rolesComboBox.addEventHandler(ComboBox.ON_SHOWN, event -> populateRolesComboBox());
    }

    public void populateRolesComboBox() {
        rolesComboBox.getItems().clear();
        for (Role role : RoleDAO.getRoles()){
            rolesComboBox.getItems().add(role);
        }
        rolesComboBox.getSelectionModel().select(0);
    }

    public BorderPane createActionCard(Action action) {
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/docapp/view/utilisateurs/actionItem.fxml"
                    )
            );

            root = loader.load();
            ActionItemController it = loader.getController();
            it.actionLabel.setText(action.getAction());
            it.dateLabel.setText(action.getActionTime());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;

    }

    public void setActions(){
        Vector<Action> actionList = ActionDAO.getActions(Integer.parseInt(idLabel.getText()));
        for (Action action : actionList) {
            BorderPane bp = createActionCard(action);
            listAction.getItems().add(bp);
        }
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
