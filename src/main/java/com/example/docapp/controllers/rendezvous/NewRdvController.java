package com.example.docapp.controllers.rendezvous;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.dao.RendezVousDAO;
import com.example.docapp.models.Patient;
import com.example.docapp.models.RendezVous;
import com.example.docapp.models.ViewModel;
import com.example.docapp.util.DateFormatter;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewRdvController implements Initializable {
    public TextField idField;
    public DatePicker datePicker;
    public TextField heureField;
    public TextArea noteArea;
    public TextField cinField;
    public JFXButton selectPatient;
    public JFXButton addBtn;
    public JFXButton cancelBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(validateForm().isEmpty()){
                    RendezVous rdv = new RendezVous();
                    rdv.setDescription(noteArea.getText());
                    rdv.setRendezVousDate(datePicker.getValue().format(DateFormatter.dateformatter) + " " + heureField.getText());
                    rdv.setId_patient(Integer.parseInt(idField.getText()));
                    int status = RendezVousDAO.addRendezVous(rdv);
                    System.out.println(status);
                    if(status == 201){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Rendez-vous ajouté avec succés");
                        alert.show();
                        Stage s = (Stage) cancelBtn.getScene().getWindow();
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

        selectPatient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewModel.getInstance().getViewFactory().showPatientSelect();
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
}
