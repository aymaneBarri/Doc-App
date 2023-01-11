package com.example.docapp.controllers.dashboard;

import com.example.docapp.controllers.patient.PatientItemController;
import com.example.docapp.controllers.patient.VisiteItemController;
import com.example.docapp.dao.PatientDAO;
import com.example.docapp.dao.RendezVousDAO;
import com.example.docapp.dao.VisiteDAO;
import com.example.docapp.models.Patient;
import com.example.docapp.models.RendezVous;
import com.example.docapp.models.Visite;
import com.example.docapp.util.DateFormatter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Vector;

public class DashboardContentController implements Initializable {

    public Label nbPatientLabel;
    public Label nbVisiteLabel;
    public Label revenueLabel;
    public ListView<BorderPane> listRdv;
    public ListView<BorderPane> listPatient;
    public VBox vbox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            HBox root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/docapp/view/util/topBar.fxml")));
            vbox.getChildren().add(0,root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ;
        nbPatientLabel.setText(PatientDAO.getPatients().size()+"");
        System.out.println(LocalDateTime.now().format(DateFormatter.formatter));
        nbVisiteLabel.setText(RendezVousDAO.getAllRendezVous(LocalDateTime.now().format(DateFormatter.formatter).split(" ")[0]).size()+"");
        double total = 0.0;
        for (int i = 0; i <VisiteDAO.getAllVisites("").size() ; i++) {
            total += VisiteDAO.getAllVisites(LocalDateTime.now().format(DateFormatter.formatter).split(" ")[0]).get(i).getAmount();

        }
        revenueLabel.setText(total+"");

        Vector<Patient> patientList = PatientDAO.getRecentPatients();
        if (patientList.isEmpty()){
            BorderPane b = new BorderPane();
            Label l = new Label("Pas de nouveaux patients");
            b.setCenter(l);
            listPatient.getItems().add(b);
        }else{
            for (Patient p : patientList) {
                BorderPane bp = createPCard(p);
                listPatient.getItems().add(bp);
            }
        }




        refreshList();


    }


    public void refreshList(){
        listRdv.getItems().clear();
        Vector<RendezVous> rdvList = RendezVousDAO.getDoneRendezVous(LocalDateTime.now().format(DateFormatter.formatter), 0);
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
    public BorderPane createPCard(Patient patient) {
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/docapp/view/dashboard/patientItem.fxml"
                    )
            );

            root = loader.load();
            RecentPatientItemController pc = loader.getController();
            pc.nomPatient.setText(patient.getLastName());
            pc.prenomPatient.setText(patient.getFirstName());
            pc.joinLabel.setText(patient.getJoin_date());

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
                    rdv.setDone(true);
                    RendezVousDAO.editRendezVous(rdv);
                    refreshList();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;

    }



}
