package com.example.docapp.controllers.dashboard;

import com.example.docapp.controllers.patient.PatientItemController;
import com.example.docapp.controllers.patient.VisiteItemController;
import com.example.docapp.dao.PatientDAO;
import com.example.docapp.dao.RendezVousDAO;
import com.example.docapp.dao.VisiteDAO;
import com.example.docapp.models.Patient;
import com.example.docapp.models.RendezVous;
import com.example.docapp.models.Visite;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

public class DashboardContentController implements Initializable {

    public Label nbPatientLabel;
    public Label nbVisiteLabel;
    public Label revenueLabel;
    public ListView<BorderPane> listRdv;
    public ListView<BorderPane> listPatient;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ;
        nbPatientLabel.setText(PatientDAO.getPatients().size()+"");
        nbVisiteLabel.setText(VisiteDAO.getAllVisites("").size()+"");
        double total = 0.0;
        for (int i = 0; i <VisiteDAO.getAllVisites("").size() ; i++) {
            total += VisiteDAO.getAllVisites("").get(i).getAmount();

        }
        revenueLabel.setText(total+"");

        Vector<Patient> patientList = PatientDAO.getPatients();
        for (Patient patient : patientList) {
            BorderPane bp = createPCard(patient.getFirstName(),patient.getLastName(), patient.getBirthDate(), patient.getPhoneNumber(), patient.getId());
            listPatient.getItems().add(bp);
        }


        Vector<RendezVous> rdvList = RendezVousDAO.getAllRendezVous(String.valueOf(LocalDate.now()));
        System.out.println(rdvList);
        if(!rdvList.isEmpty()){
            for (RendezVous rdv : rdvList) {
                BorderPane bp = createRCard(rdv);
                listRdv.getItems().add(bp);
            }
        }else{
            BorderPane b = new BorderPane();
            Label l = new Label("Pas de rendez-vous");
            b.setCenter(l);
            listRdv.getItems().add(b);
        }



    }
    public BorderPane createPCard(String firstName, String lastName, String date, String phone, Integer id) {
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/docapp/view/dashboard/patientItem.fxml"
                    )
            );

            root = loader.load();
            RecentPatientItemController pc = loader.getController();
            pc.setNomPatient(lastName);
            pc.setBirthPatient(date);
            pc.setPrenomPatient(firstName);
            pc.setPhonePatient(phone);



        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;

    }

    public BorderPane createRCard(RendezVous rdv) {
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/docapp/view/dashboard/rdvItem.fxml"
                    )
            );
            Patient p = PatientDAO.getPatientByID(String.valueOf(rdv.getId_patient()));
            root = loader.load();
            RedvItemController vc = loader.getController();
            vc.dateLabel.setText(rdv.getRendezVousDate());
            vc.patientID.setText(String.valueOf(rdv.getId_patient()));
            vc.rdvID.setText(String.valueOf(rdv.getId()));
            vc.nomLabel.setText(p.getLastName());
            vc.prenomLabel.setText(p.getFirstName());
            vc.doneBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;

    }



}
