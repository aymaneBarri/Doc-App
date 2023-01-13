package com.example.docapp.controllers.visites;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.dao.VisiteDAO;
import com.example.docapp.models.Patient;
import com.example.docapp.models.Visite;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Vector;

public class VisitesController implements Initializable {
    public ListView<BorderPane> listVisite;
    public TextField searchField;
    public JFXButton searchBtn;
    public VBox vbox;
    public Label totalLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            HBox root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/docapp/view/util/topBar.fxml")));
            vbox.getChildren().add(0,root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Vector<Visite> visiteList = VisiteDAO.getAllVisites("");
        totalLabel.setText(String.valueOf(visiteList.size()));
        for (Visite visite : visiteList) {
            BorderPane bp = createCard(visite.getVisit_date(),visite.getId()+"",visite.getAmount()+"", visite.getIllness(), visite.getId_patient());
            listVisite.getItems().add(bp);
        }

        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                listVisite.getItems().clear();
                Vector<Visite> visiteList = VisiteDAO.getAllVisites(searchField.getText());
                for (Visite visite : visiteList) {
                    BorderPane bp = createCard(visite.getVisit_date(),visite.getId()+"",visite.getAmount()+"", visite.getIllness(), visite.getId_patient());
                    listVisite.getItems().add(bp);
                }
            }
        });
    }

    public BorderPane createCard(String date,String visiteId, String amount, String maladie, Integer id) {
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/docapp/view/visites/visiteItem.fxml"
                    )
            );

            root = loader.load();
            VisiteItemController vc = loader.getController();

            vc.maladieLabel.setText(maladie);
            vc.dateLabel.setText(date);
            vc.montantLabel.setText(amount);
            vc.patientID.setText(id.toString());
            vc.visitID.setText(visiteId.toString());
            Patient patient =PatientDAO.getPatientByID(id.toString());
            vc.patientLabel.setText(patient.getFirstName()+" "+patient.getLastName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }
}
