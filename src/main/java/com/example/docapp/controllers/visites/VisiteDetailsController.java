package com.example.docapp.controllers.visites;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.dao.VisiteDAO;
import com.example.docapp.models.Patient;
import com.example.docapp.models.ViewModel;
import com.example.docapp.models.Visite;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class VisiteDetailsController implements Initializable {
    public JFXButton saveBtn;
    public JFXButton cancelBtn;
    public DatePicker datePicker;
    public TextField illnessField;
    public TextArea noteArea;
    public TextField montantField;
    public TextField montantField1;
    public TextField montantField11;
    public TextField fullname;
    public TextField assurance;
    public TextField heureField;
    public TextArea prescriptionField;
    public TextField visiteId;
    public TextField patientId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        saveBtn.setOnAction(event -> {

            if(!validateInformation().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText(validateInformation());
                alert.showAndWait();
            }else{
                Visite visite = new Visite();
                visite.setId_patient(Integer.parseInt(patientId.getText()));
                visite.setIllness(illnessField.getText());
                visite.setAmount(Float.parseFloat(montantField.getText()));
                visite.setVisit_date(datePicker.getValue()+" "+heureField.getText());
                visite.setDescription(noteArea.getText());
                visite.setPrescription(prescriptionField.getText());
                visite.setAssurance(assurance.getText());
                visite.setId(Integer.parseInt(visiteId.getText()));


               int statusCode= VisiteDAO.editVisite(visite);
               if(statusCode ==  201){
                   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                   alert.setTitle("Confirmation");
                   alert.setHeaderText("Confirmation");
                   alert.setContentText("Visite updated successfully");
                   alert.showAndWait();
                   ViewModel.getInstance().getViewFactory().showVisite();
                   Stage stage = (Stage) saveBtn.getScene().getWindow();
                   stage.close();
               }else{
                     Alert alert = new Alert(Alert.AlertType.ERROR);
                     alert.setTitle("Error");
                     alert.setHeaderText("Error");
                     alert.setContentText("Error while updating visite");
                     alert.showAndWait();
               }

            }
        });
        cancelBtn.setOnAction(event -> {


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Current project is modified");
            alert.setContentText("Save?");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    VisiteDAO.deleteVisite(visiteId.getText());
                    Stage s = (Stage) saveBtn.getScene().getWindow();
                    s.close();

                    ViewModel.getInstance().getViewFactory().showVisite();
                }
            });
        });
    }

    public void setData(String patientID, String VisitID){
       Patient patient= PatientDAO.getPatientByID(patientID);
        Visite visite= VisiteDAO.getVisiteById(VisitID);
        illnessField.setText(visite.getIllness());
        visiteId.setText(visite.getId()+"");
        patientId.setText(patient.getId()+"");
        noteArea.setText(visite.getDescription());
        montantField.setText(visite.getAmount()+"");
        String date_visite= visite.getVisit_date().split(" ")[0];
        String heure_visite= visite.getVisit_date().split(" ")[1];
        datePicker.setValue(LocalDate.parse(date_visite));
        heureField.setText(heure_visite);
        fullname.setText(patient.getFirstName()+" "+patient.getLastName());
        assurance.setText(visite.getAssurance());
        prescriptionField.setText(visite.getPrescription());




    }
    public String validateInformation(){
        String error = "";

        if(datePicker.getValue().isAfter(LocalDate.now())){
            error += " Date is not valid";
        }

        if(heureField.getText().isEmpty()){
            error += " Hour is empty";
        }

        if(montantField.getText().isEmpty()){
            error += " Montant is empty";
        }





        return error;
    }


    }

