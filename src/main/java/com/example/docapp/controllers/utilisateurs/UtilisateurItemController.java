package com.example.docapp.controllers.utilisateurs;

import com.example.docapp.dao.UtilisateurDAO;
import com.example.docapp.models.Permission;
import com.example.docapp.models.Utilisateur;
import com.example.docapp.models.ViewModel;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class UtilisateurItemController implements Initializable {
    public Label nom;
    public Label prenom;
    public Label email;
    public Label phone;
    public Label id;
    public JFXButton editBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Permission permission : Utilisateur.currentUser.getRole().getPermissions()) {
            if (permission.getSubject().equals("utilisateur")) {
                if (!permission.isCanModify() && !permission.isCanDelete())
                    editBtn.setDisable(true);
            }
        }

        editBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewModel.getInstance().getViewFactory().showUserDetails(UtilisateurDAO.getUserByID(Integer.parseInt(id.getText())));
            }
        });
    }

    public void setNom(String nom) {
        this.nom.setText(nom);
    }

    public void setPrenom(String prenom) {
        this.prenom.setText(prenom);
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }

    public void setPhone(String phone) {
        this.phone.setText(phone);
    }

    public void setId(String id) {
        this.id.setText(id);
    }
}
