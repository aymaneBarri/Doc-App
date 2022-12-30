package com.example.docapp.controllers.rendezvous;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.dao.RendezVousDAO;
import com.example.docapp.models.Patient;
import com.example.docapp.models.RendezVous;
import com.example.docapp.models.ViewModel;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RdvDetailsController implements Initializable {
    public TextField idField;
    public DatePicker datePicker;
    public TextField heureField;
    public TextArea noteArea;
    public TextField cinField;
    public JFXButton saveBtn;
    public JFXButton delBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        delBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Supprimer");
                alert.setContentText("Voulez-vous vraiment supprimer le rendez-vous?");
                ButtonType okButton = new ButtonType("Oui", ButtonBar.ButtonData.YES);
                ButtonType cancelButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(okButton, cancelButton);
                alert.showAndWait().ifPresent(type -> {
                    if (type == okButton) {

                        RendezVousDAO.deleteRendezVous(Integer.parseInt(idField.getText()));
                        Stage s = (Stage) saveBtn.getScene().getWindow();
                        s.close();
                        ViewModel.getInstance().getViewFactory().showRdv();
                    }
                });
            }
        });

        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(validateForm().isEmpty()){
                    RendezVous rdv = new RendezVous();
                    rdv.setId(Integer.parseInt(idField.getText()));
                    rdv.setDescription(noteArea.getText());
                    rdv.setRendezVousDate(datePicker.getValue().toString() + " " + heureField.getText());
                    rdv.setId_patient(Integer.parseInt(idField.getText()));
                    int status = RendezVousDAO.editRendezVous(rdv);
                    System.out.println(status);
                    if(status == 201){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Rendez-vous modifié avec succés");
                        alert.show();
                        Stage s = (Stage) saveBtn.getScene().getWindow();
                        s.close();

                        ViewModel.getInstance().getViewFactory().showRdv();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Erreur, veuillez réessayer");
                        alert.show();
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(validateForm());
                    alert.show();
                }
            }
        });
    }

    public String validateForm(){
        String errorText="";
        if(datePicker.getValue() == null|| heureField.getText().isEmpty() || cinField.getText().isEmpty()){
            errorText = "Veuillez remplir tous les champs !";

        }
        return errorText;

    }

    public void setData(String idRdv, String idPatient) {
        try {
            RendezVous rdv = RendezVousDAO.getRendezVousById(Integer.valueOf(idRdv));
            Patient patient = PatientDAO.getPatientByID(String.valueOf(idPatient));
            if (rdv!= null) {
                idField.setText(rdv.getId()+"");
                cinField.setText(patient.getCin());
                datePicker.setValue( LocalDate.parse(rdv.getRendezVousDate().split(" ")[0]));
                heureField.setText(rdv.getRendezVousDate().split(" ")[1]);
                noteArea.setText(rdv.getDescription());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}