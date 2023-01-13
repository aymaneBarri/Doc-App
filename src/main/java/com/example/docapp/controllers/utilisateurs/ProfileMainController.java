package com.example.docapp.controllers.utilisateurs;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProfileMainController implements Initializable {
    public BorderPane borderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            VBox root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/docapp/view/utilisateurs/profileContent.fxml")));
            borderPane.setCenter(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
