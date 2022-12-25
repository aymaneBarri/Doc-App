package com.example.docapp.controllers.patient;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.models.Patient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientDetailsController implements Initializable {

    @FXML
    public Label idPatient;
    @FXML
    public Label nomLabel;
    @FXML
    private Label birthLabel;
    @FXML
    private Label cinLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label noteLabel;
    @FXML
    private ListView<BorderPane> listOrdonnances;
    @FXML
    private ListView<BorderPane> listVisites;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setData(String id){
        BorderPane root = null;
        PatientDAO dao = new PatientDAO();
        try {
            Patient pt = dao.getPatientByID(id);
            if (pt!= null) {
                this.setNomLabel(pt.getFirstName() +" "+ pt.getLastName());
                this.setBirthLabel(pt.getBirthDate().toString());
                this.setCinLabel(pt.getCin());
                this.setPhoneLabel(pt.getPhoneNumber());
                this.setNoteLabel(pt.getDescription());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBirthLabel(String text) {
        birthLabel.setText(text);
    }
    public void setCinLabel(String text) {
        cinLabel.setText(text);
    }

    public void setPhoneLabel(String text) {
        phoneLabel.setText(text);
    }

    public void setNoteLabel(String text) {
        noteLabel.setText(text);
    }

    public void setNomLabel( String text) {
        nomLabel.setText(text);
    }




}
