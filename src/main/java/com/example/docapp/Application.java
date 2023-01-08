package com.example.docapp;

import com.example.docapp.models.ViewModel;
import javafx.stage.Stage;
import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        ViewModel.getInstance().getViewFactory().showLogin();
    }

    public static void main(String[] args) {
        launch();
    }
}