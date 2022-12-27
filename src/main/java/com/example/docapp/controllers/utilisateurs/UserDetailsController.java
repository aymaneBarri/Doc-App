package com.example.docapp.controllers.utilisateurs;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.dao.UtilisateurDAO;
import com.example.docapp.models.Patient;
import com.example.docapp.models.Utilisateur;
import com.example.docapp.models.ViewModel;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
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
    public JFXButton cancelBtn;
    public TextField idField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage s = (Stage) cancelBtn.getScene().getWindow();
                s.hide();
            }
        });

        rolesBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewModel.getInstance().getViewFactory().showUserRoles(idField.getText());
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

}
