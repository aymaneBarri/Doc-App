package com.example.docapp.controllers.patient;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.models.Patient;
import com.example.docapp.models.Permission;
import com.example.docapp.models.Utilisateur;
import com.example.docapp.models.ViewModel;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class PatientDetailsController implements Initializable {

    @FXML
    public Label idPatient;
    public TextField idP;
    public TextField nomField;
    public DatePicker birthField;
    public TextField cinField;
    public TextField phoneField;
    public TextArea noteArea;
    public JFXButton editBtn;
    public JFXButton deleteBtn;
    public TextField prenomField;
    @FXML
    private ListView<BorderPane> listOrdonnances;
    @FXML
    private ListView<BorderPane> listVisites;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Permission permission : Utilisateur.currentPermissions) {
            if (permission.getSubject().equals("patient")) {
                if (!permission.isCanModify())
                    editBtn.setDisable(true);
                else if (!permission.isCanDelete())
                    deleteBtn.setDisable(true);
            }
        }

//            idPatient.setText("1");
        idP.setVisible(false);
        editBtn.setOnAction(actionEvent -> {
       if(validateForm().isEmpty()){
              Patient patient = new Patient();
              patient.setBirthDate(birthField.getValue().toString());
              patient.setCin(cinField.getText());
              patient.setFirstName(prenomField.getText());
              patient.setLastName(nomField.getText());
              patient.setPhoneNumber(phoneField.getText());
              patient.setDescription(noteArea.getText());
                patient.setId(Integer.parseInt(idP.getText()));
              int status =  PatientDAO.editPatient(patient);
              System.out.println(status);
              if(status == 201){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Patient edité avec succés");
                alert.show();

                  ViewModel.getInstance().getViewFactory().showPatient();

                  Stage s = (Stage) editBtn.getScene().getWindow();
                  s.close();
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
        });
        deleteBtn.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Supprimer");
            alert.setContentText("Voulez-vous vraiment supprimer le patient?");
            ButtonType okButton = new ButtonType("Oui", ButtonBar.ButtonData.YES);
            ButtonType cancelButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {

                   PatientDAO.deletePatient( Integer.parseInt(idP.getText()));
                    Stage s = (Stage) editBtn.getScene().getWindow();
                    s.close();

                    ViewModel.getInstance().getViewFactory().showPatient();
                    }
            });
        });
    }

    public void setData(String id){

        BorderPane root = null;
        PatientDAO dao = new PatientDAO();
        try {
            Patient patient = dao.getPatientByID(id);
            if (patient!= null) {
                idP.setText(patient.getId()+"");
                nomField.setText(patient.getLastName());
                prenomField.setText(patient.getFirstName());
                birthField.setValue(LocalDate.parse(patient.getBirthDate()));
                cinField.setText(patient.getCin());
                phoneField.setText(patient.getPhoneNumber());
/*                noteArea.setText(patient.getDescription());*/

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



public String validateForm(){
        String errorMessage = "";
        if (nomField.getText().isEmpty()) {
            errorMessage += " Nom est obligatoire";
        }
        if (prenomField.getText().isEmpty()) {
            errorMessage += " Prenom est obligatoire";
        }
        if (cinField.getText().isEmpty()) {
            errorMessage += " CIN est obligatoire";
        }
        if (phoneField.getText().isEmpty()) {
            errorMessage += " Phone est obligatoire";
        }
        if (birthField.getValue() == null) {
            errorMessage += " Date de naissance est obligatoire";
        }
        try{
            if (birthField.getValue().isAfter(LocalDate.now())) {
                errorMessage += " Date de naissance est invalide";
            }
        } catch (Exception e) {
            errorMessage += " Date de naissance est invalide";
        }
        try {

            Integer.parseInt(phoneField.getText().trim());
        } catch (Exception e) {
            errorMessage += " Numero de telephone est invalide";
        }
        return errorMessage;
}

}
