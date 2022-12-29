package com.example.docapp.controllers.visites;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.dao.VisiteDAO;
import com.example.docapp.models.Patient;
import com.example.docapp.models.Visite;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class NewVisiteController implements Initializable {
    public JFXButton addBtn;
    public JFXButton cancelBtn;
    public TextField idPatient;
    public DatePicker datePicker;
    public TextField illnessField;
    public TextArea noteArea;
    public TextField montantField;
    public TextField assuranceField;
    public TextField hourField;
    public TextArea prescriptionField;
    public TextField fullName;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addBtn.setOnAction(event -> {
        if(!Objects.equals(validateInformation(), "")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(validateInformation());
            alert.showAndWait();
        }else{
            Visite visite = new Visite();
            visite.setId_patient(Integer.parseInt(idPatient.getText()));
            visite.setIllness(illnessField.getText());
            visite.setAmount(Float.parseFloat(montantField.getText()));
            visite.setVisit_date(datePicker.getValue()+" "+hourField.getText());
            visite.setDescription(noteArea.getText());
            visite.setPrescription(prescriptionField.getText());
            visite.setAssurance(assuranceField.getText());


         if(VisiteDAO.addVisite(visite)==201){
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
             alert.setTitle("Confirmation");
             alert.setHeaderText("Confirmation");
             alert.setContentText("Visite ajoutée avec succès!");
             alert.showAndWait();

             Stage stage = (Stage) addBtn.getScene().getWindow();
                stage.close();

         }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Une erreur s'est produite lors de l'ajout de la visite!");
                alert.showAndWait();
         }


        }

        });
        cancelBtn.setOnAction(event -> {
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        });

    }
    public void setData(String id){

        idPatient.setText(id+"");
        Patient patient = new Patient();
        patient=PatientDAO.getPatientByID(id);
        fullName.setText(patient.getFirstName()+ " " + patient.getLastName());
        datePicker.setValue(LocalDate.now());
        hourField.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm")));
    }
    public String validateInformation(){
        String error = "";
        if(idPatient.getText().isEmpty()) {
            error += " ID Patient is empty";
        }
        if(datePicker.getValue().isAfter(LocalDate.now())){
            error += " Date is not valid";
        }

        if(hourField.getText().isEmpty()){
            error += " Hour is empty";
        }

        if(montantField.getText().isEmpty()){
            error += " Montant is empty";
        }





        return error;
    }
}
