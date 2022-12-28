package com.example.docapp.controllers.utilisateurs;

import com.example.docapp.models.ViewModel;
import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewUserController implements Initializable {
    public TextField nomField;
    public TextField prenomField;
    public TextField emailField;
    public TextField passField;
    public TextField phoneField;
    public TextField cinField;
    public JFXButton rolesBtn;
    public ComboBox typeUser;
    public JFXButton saveBtn;
    public JFXButton cancelBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        typeUser.getItems().clear();
        rolesBtn.setDisable(true);

        typeUser.getItems().addAll(
                    "admin",
                    "utilisateur");
        typeUser.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
               rolesBtn.setDisable(newValue.equals("admin")?true:false);
        });

        rolesBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewModel.getInstance().getViewFactory().showUserRoles();
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

}
