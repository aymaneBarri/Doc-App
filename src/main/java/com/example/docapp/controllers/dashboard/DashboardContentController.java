package com.example.docapp.controllers.dashboard;

import com.example.docapp.controllers.visites.VisiteItemController;
import com.example.docapp.dao.PatientDAO;
import com.example.docapp.dao.VisiteDAO;
import com.example.docapp.models.Patient;
import com.example.docapp.models.Visite;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class DashboardContentController implements Initializable {

    public Label nbPatientLabel;
    public Label nbVisiteLabel;
    public Label revenueLabel;
    public ListView listVisite;

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
        Vector<Visite> visiteList = VisiteDAO.getAllVisites("");
//         visiteList.subList(0,visiteList.size() > 5 ? 5 : visiteList.size());
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
            VisiteItemController vc = loader.getController();

            vc.maladieLabel.setText(maladie);
            vc.dateLabel.setText(date);
            vc.montantLabel.setText(amount);
            vc.patientID.setText(id.toString());
            vc.visitID.setText(visiteId);
            Patient patient =PatientDAO.getPatientByID(id.toString());
            vc.patientLabel.setText(patient.getFirstName()+" "+patient.getLastName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;

    }



}
