package com.example.docapp;

import com.example.docapp.controllers.patient.PatientDetailsController;
import com.example.docapp.controllers.utilisateurs.UserDetailsController;
import com.example.docapp.controllers.utilisateurs.UserRoleController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

public class ViewFactory {
    public Stage stage = new Stage();

    Scene scene = null;
    public ViewFactory() {}
    Image appIcon = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("images/icon.png")));
    public void showLogin(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docapp/view/login.fxml"));

        try{
            Scene oldScene = stage.getScene();
            if(oldScene!=null){
                scene = new Scene(loader.load(), oldScene.getWidth(), oldScene.getHeight());
            }else{
                scene = new Scene(loader.load());
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        stage.getIcons().add(appIcon);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.centerOnScreen();

        stage.show();
        stage.setMaximized(true);

    }

    public void showDashboard(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docapp/view/dashboard/dashboard.fxml"));
        try{
            Scene oldScene = stage.getScene();
            scene = new Scene(loader.load(), oldScene.getWidth(), oldScene.getHeight());
        }catch (Exception e){
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.setTitle("Acceuil - DocAssistant");
    }

    public void showPatient(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docapp/view/patients/patients.fxml"));
        try{
            Scene oldScene = stage.getScene();
            scene = new Scene(loader.load(), oldScene.getWidth(), oldScene.getHeight());
        }catch (Exception e){
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.setTitle("Patients - DocAssistant");


    }


    public void showUser(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docapp/view/utilisateurs/users.fxml"));
        try{
            Scene oldScene = stage.getScene();
            scene = new Scene(loader.load(), oldScene.getWidth(), oldScene.getHeight());
        }catch (Exception e){
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.setTitle("Utilisateurs - DocAssistant");

    }

    public void showNewPatient(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docapp/view/patients/newPatient.fxml"));
        try{
            BorderPane root = loader.load();
            Scene sc = new Scene(root);
            Stage s = new Stage();
            s.setScene(sc);
            s.setTitle("Nouveau patient");
            s.getIcons().add(appIcon);
            s.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showPatientDetails(String id){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docapp/view/patients/patientDetails.fxml"));
        try{
            BorderPane root = loader.load();
            PatientDetailsController pd = loader.getController();
            Scene sc = new Scene(root);
            Stage s = new Stage();
            s.setScene(sc);
            System.out.println("current patientID: " + id );
            pd.setData(id);
            s.setTitle("Détails du patient");
            s.getIcons().add(appIcon);
            s.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void showUserDetails(String id) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docapp/view/utilisateurs/userDetails.fxml"));
        try{
            BorderPane root = loader.load();
            UserDetailsController ud = loader.getController();
            Scene sc = new Scene(root);
            Stage s = new Stage();
            s.setScene(sc);
            ud.setData(id);
            s.setTitle("Modifier l'utilisateur");
            s.getIcons().add(appIcon);
            s.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showUserRoles(String text) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docapp/view/utilisateurs/userRoles.fxml"));
        try{
            BorderPane root = loader.load();
            UserRoleController ur = loader.getController();
            Scene sc = new Scene(root);
            Stage s = new Stage();
            s.setScene(sc);
           /* ur.setData(id);*/
            s.setTitle("Modifier l'utilisateur");
            s.getIcons().add(appIcon);
            s.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
