package com.example.docapp;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.dao.RendezVousDAO;
import com.example.docapp.dao.UtilisateurDAO;
import com.example.docapp.dao.VisiteDAO;
import com.example.docapp.models.*;
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
        Visite vs= new Visite();
        vs.setAmount(200);
        vs.setAssurance("assurance2");
        vs.setDescription("description3");
        vs.setIllness("illness2");
        vs.setPrescription("prescription2");
        vs.setVisit_date("2020-11-12");
        vs.setId_patient(1);
       vs.setId(2);
//        System.out.println(VisiteDAO.getVisiteById(vs));
        launch();



    }
}