package com.example.docapp.controllers.visites;


import com.example.docapp.controllers.patient.PatientItemController;
import com.example.docapp.dao.PatientDAO;
import com.example.docapp.dao.VisiteDAO;
import com.example.docapp.models.Patient;
import com.example.docapp.models.Visite;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class VisitesController implements Initializable {
    public ListView<BorderPane> listVisite;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Vector<Visite> visiteList = VisiteDAO.getAllVisites();
        for (Visite visite : visiteList) {
            BorderPane bp = createCard(visite.getVisit_date(),visite.getId()+"",visite.getAmount()+"", visite.getIllness(), visite.getId_patient());
            listVisite.getItems().add(bp);
        }


    }


    public BorderPane createCard(String date,String visiteId, String amount, String maladie, Integer id) {
        BorderPane root = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/docapp/view/visites/visiteItem.fxml"
                    )
            );

            root = loader.load();
            VisiteItemController pc = loader.getController();

            pc.maladieLabel.setText(maladie);
            pc.dateLabel.setText(date);
            pc.montantLabel.setText(amount);
            pc.patientID.setText(id.toString());
            pc.visitID.setText(visiteId.toString());
            Patient patient =PatientDAO.getPatientByID(id.toString());
            pc.patientLabel.setText(patient.getFirstName()+" "+patient.getLastName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;

    }
}
