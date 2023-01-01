package com.example.docapp;

import com.example.docapp.controllers.patient.PatientDetailsController;
import com.example.docapp.controllers.rendezvous.NewRdvController;
import com.example.docapp.controllers.rendezvous.RdvDetailsController;
import com.example.docapp.controllers.utilisateurs.UserDetailsController;
import com.example.docapp.controllers.utilisateurs.UserPermissionController;
import com.example.docapp.controllers.visites.NewVisiteController;
import com.example.docapp.controllers.visites.VisiteDetailsController;
import com.example.docapp.models.Permission;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Objects;
import java.util.Vector;

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
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
            }
        });

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

    public void showUserRoles(int id) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docapp/view/utilisateurs/userPermission.fxml"));
        try{
            BorderPane root = loader.load();
            UserPermissionController ur = loader.getController();
            Scene sc = new Scene(root);
            Stage s = new Stage();
            s.setScene(sc);
            ur.setData(id, null);
            s.setTitle("Modifier l'utilisateur");
            s.getIcons().add(appIcon);
            s.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showNewUserRoles(Vector<Permission> permissions) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docapp/view/utilisateurs/userPermission.fxml"));
        try{
            BorderPane root = loader.load();
            UserPermissionController ur = loader.getController();
            Scene sc = new Scene(root);
            Stage s = new Stage();
            s.setScene(sc);
            ur.setData(0, permissions);
            s.setTitle("Nouveau utilisateur");
            s.getIcons().add(appIcon);
            s.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showVisite() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docapp/view/visites/visites.fxml"));
        try{
            Scene oldScene = stage.getScene();
            scene = new Scene(loader.load(), oldScene.getWidth(), oldScene.getHeight());
        }catch (Exception e){
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.setTitle("Visites - DocAssistant");
    }


    public void showRdv() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docapp/view/rendezvous/rendezvous.fxml"));
        try{
            Scene oldScene = stage.getScene();
            scene = new Scene(loader.load(), oldScene.getWidth(), oldScene.getHeight());
        }catch (Exception e){
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.setTitle("Rendez-Vous - DocAssistant");
    }


    public void showNewUser(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docapp/view/utilisateurs/newUser.fxml"));
        try{
            BorderPane root = loader.load();
            Scene sc = new Scene(root);
            Stage s = new Stage();
            s.setScene(sc);
            s.setTitle("Nouveau utilisateur");
            s.getIcons().add(appIcon);
            s.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void showNewVisite(String id){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docapp/view/visites/newVisite.fxml"));
        try{
            BorderPane root = loader.load();
            Scene sc = new Scene(root);
            Stage s = new Stage();
            NewVisiteController nv = loader.getController();
            nv.setData(id);
            s.setScene(sc);
            s.setTitle("Nouvelle visite");
            s.getIcons().add(appIcon);
            s.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void showVisiteDetail(String patientId, String visiteId){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docapp/view/visites/visiteDetails.fxml"));
        try{
            BorderPane root = loader.load();
            Scene sc = new Scene(root);
            Stage s = new Stage();
            VisiteDetailsController nv = loader.getController();
            nv.setData(patientId, visiteId);
            s.setScene(sc);
            s.setTitle("Détails de la visite");
            s.getIcons().add(appIcon);
            s.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    Stage sp = new Stage();
    public void showNewRdv(String id, String cin) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docapp/view/rendezvous/newRdv.fxml"));
        try{
            BorderPane root = loader.load();
            Scene sc = new Scene(root);
            NewRdvController nv = loader.getController();
            nv.idField.setText(id);
            nv.cinField.setText(cin);
            sp.setScene(sc);
            sp.setTitle("Nouveau rendez-vous");
            sp.getIcons().add(appIcon);
            sp.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showPatientSelect() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docapp/view/patients/patientSelect.fxml"));
        try{
            BorderPane root = loader.load();
            Scene sc = new Scene(root);
            sp.setScene(sc);
            sp.setTitle("Sélectionner un patient");
            sp.getIcons().add(appIcon);
            sp.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showRdvDetails(String idRdv, String idPatient){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/docapp/view/rendezvous/rdvDetails.fxml"));
        try{
            BorderPane root = loader.load();
            Scene sc = new Scene(root);
            Stage s = new Stage();
            RdvDetailsController rd = loader.getController();
            rd.setData(idRdv, idPatient);
            s.setScene(sc);
            s.setTitle("Détails de la visite");
            s.getIcons().add(appIcon);
            s.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
