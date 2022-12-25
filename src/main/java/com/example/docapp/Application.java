package com.example.docapp;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.dao.UtilisateurDAO;
import com.example.docapp.models.Role;
import com.example.docapp.models.RolesUtilisateur;
import com.example.docapp.models.Utilisateur;
import com.example.docapp.models.ViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.Vector;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        ViewModel.getInstance().getViewFactory().showLogin();
    }

    public static void main(String[] args) {

        launch();
    }
}