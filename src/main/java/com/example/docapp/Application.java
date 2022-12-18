package com.example.docapp;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.dataAccess.DBUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("view/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Log in");
        stage.getIcons().add(new Image(Objects.requireNonNull(Application.class.getResourceAsStream("images/icon.png"))));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        DBUtil ff= new DBUtil();
        System.out.println(ff.encryptPassword("Android123@@"));
        String hh= ff.encryptPassword("Android123@@");
        System.out.println(ff.decryptPassword(hh));
        launch();
    }
}