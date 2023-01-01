package com.example.docapp.controllers.patient;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.models.Patient;
import com.example.docapp.models.ViewModel;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

public class NewPatientController implements Initializable {
    public TextField nomField;
    public TextField prenomField;
    public TextField cinField;
    public DatePicker birthPicker;
    public TextField phoneField;
    public TextArea noteArea;
    public JFXButton addBtn;
    public JFXButton cancelBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        birthPicker.setValue(LocalDate.now());
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage s = (Stage) cancelBtn.getScene().getWindow();
                s.close();
            }
        });

        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if(validateForm().isEmpty()){
                    Patient patient = new Patient();
                    patient.setBirthDate(birthPicker.getValue().toString());
                    patient.setCin(cinField.getText());
                    patient.setFirstName(prenomField.getText());
                    patient.setLastName(nomField.getText());
                    patient.setPhoneNumber(phoneField.getText());
                    patient.setDescription(noteArea.getText());
                   int status =  PatientDAO.addPatient(patient);
                    System.out.println(status);
                    if(status == 201){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("good");
                        alert.show();
                        Stage s = (Stage) cancelBtn.getScene().getWindow();
                        s.close();

                        ViewModel.getInstance().getViewFactory().showPatient();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("not good");
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
        if(nomField.getText().isEmpty()||prenomField.getText().isEmpty()||cinField.getText().isEmpty()||birthPicker.getValue()==null||phoneField.getText().isEmpty()){
            errorText = "Veuillez remplir tous les champs !";

        }
        else {

            try{
                Integer.parseInt(phoneField.getText().trim());

            }catch (Exception e){

                errorText = "Veuillez entrer un nombre de telephone valide !";
            }
        }
        return errorText;

    }
}
