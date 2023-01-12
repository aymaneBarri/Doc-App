package com.example.docapp.controllers.patient;

import com.example.docapp.dao.PatientDAO;
import com.example.docapp.dao.VisiteDAO;
import com.example.docapp.models.*;
import com.example.docapp.util.DateFormatter;
import com.example.docapp.util.Print;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.io.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import com.itextpdf.kernel.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

import java.time.LocalDate;

import java.util.ResourceBundle;
import java.util.Vector;

public class PatientDetailsController implements Initializable {
    @FXML
    public Label idP;
    public TextField nomField;
    public DatePicker birthField;
    public TextField cinField;
    public TextField phoneField;
    public TextArea noteArea;
    public JFXButton editBtn;
    public JFXButton deleteBtn;
    public TextField prenomField;
    public TextField joinField;
    @FXML
    private ListView<BorderPane> listOrdonnances;
    @FXML
    private ListView<BorderPane> listVisites;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Permission permission : Utilisateur.currentUser.getRole().getPermissions()) {
            if (permission.getSubject().equals("patient")) {
                if (!permission.isCanModify())
                    editBtn.setDisable(true);
                else if (!permission.isCanDelete())
                    deleteBtn.setDisable(true);
            }
        }


        editBtn.setOnAction(actionEvent -> {
       if(validateForm().isEmpty()){
              Patient patient = new Patient();
              patient.setBirthDate(birthField.getValue().format(DateFormatter.dateformatter));
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


    public BorderPane createPrescriptionCard(Visite visite) {
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/docapp/view/patients/prescriptionItem.fxml"
                    )
            );

            root = loader.load();
            PrescriptionItemController pc = loader.getController();
            pc.dateLabel.setText(visite.getVisit_date());
            pc.maladieLabel.setText(visite.getIllness());
            pc.patientID.setText(String.valueOf(visite.getId_patient()));
            pc.printBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Print.print(visite);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;

    }

    public BorderPane createVisiteCard(Visite visite) {
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/docapp/view/patients/visiteItem.fxml"
                    )
            );

            root = loader.load();
            VisiteItemController vc = loader.getController();
            vc.dateLabel.setText(visite.getVisit_date());
            vc.amountLabel.setText(String.valueOf(visite.getAmount()));



        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;

    }

    public void setPrescription(String id){
        Vector<Visite> visiteList = VisiteDAO.getVistes(id);
        for (Visite visite : visiteList) {
            if (!visite.getPrescription().isEmpty()){
                BorderPane bp = createPrescriptionCard(visite);
                listOrdonnances.getItems().add(bp);
            }
        }
    }

    public void setVisites(String id){
        Vector<Visite> visiteList = VisiteDAO.getRecentVistes(id);
        for (Visite visite : visiteList) {
            BorderPane bp = createVisiteCard(visite);
            listVisites.getItems().add(bp);
        }
    }

    public void setData(String id){

        BorderPane root = null;
        try {
            Patient patient = PatientDAO.getPatientByID(id);
            nomField.setText(patient.getLastName());
            prenomField.setText(patient.getFirstName());
            birthField.setValue(LocalDate.parse(patient.getBirthDate(), DateFormatter.dateformatter));
            cinField.setText(patient.getCin());
            phoneField.setText(patient.getPhoneNumber());
            noteArea.setText(patient.getDescription());
            idP.setText(String.valueOf(patient.getId()));
            joinField.setText(patient.getJoin_date());
            setPrescription(id);
            setVisites(id);

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
