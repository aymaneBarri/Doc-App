package com.example.docapp.controllers.utilisateurs;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserPermissionController implements Initializable {
    public JFXButton saveBtn;
    public JFXButton cancelBtn;
    public ListView<BorderPane> listRoles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


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

            }
        });

    }
}
