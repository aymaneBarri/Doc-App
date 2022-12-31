package com.example.docapp.dao;

import com.example.docapp.models.Patient;
import com.example.docapp.models.RendezVous;
import com.example.docapp.models.Utilisateur;
import com.example.docapp.util.DBUtil;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Vector;

public class RendezVousDAO {

        public static int addRendezVous(RendezVous rendezVous ) {
            PreparedStatement psAddRendezVous = null;
            ResultSet queryOutput = null;
            int statusCode=0;

            try {
                Connection connection = DBUtil.getConnection();

                psAddRendezVous = connection.prepareStatement("INSERT INTO rendez_vous (rendez_vous_date,id_patient,description) VALUES (?, ?, ?)");
                psAddRendezVous.setString(1, rendezVous.getRendezVousDate());
                psAddRendezVous.setInt(2, rendezVous.getId_patient());
                psAddRendezVous.setString(3,  rendezVous.getDescription());

                psAddRendezVous.executeUpdate();

                psAddRendezVous = connection.prepareStatement("insert into action  (id_utilisateur,action,action_time) values (?,?,?)");
                psAddRendezVous.setInt(1,  Utilisateur.currentUser.getId());
                psAddRendezVous.setString(2, "Ajout d'un rendez vous");
                psAddRendezVous.setString(3, LocalDateTime.now().toString());
                psAddRendezVous.executeUpdate();



                statusCode=201;
            } catch (SQLException e) {
                statusCode = 400;
                throw new RuntimeException(e);

            } finally {
                if (psAddRendezVous != null) {
                    try {
                        psAddRendezVous.close();
                    } catch (SQLException e) {
                        statusCode = 400;
                        e.printStackTrace();
                    }
                }

                DBUtil.stopConnection();
            }

            return statusCode;
        }
    public static int editRendezVous( RendezVous rendezVous ){
        PreparedStatement psEditR = null;
        ResultSet queryOutput = null;
        int statusCode=0;

        try {
            Connection connection = DBUtil.getConnection();

            psEditR = connection.prepareStatement("UPDATE rendez_vous SET rendez_vous_date = ?, id_patient = ?, description = ?  WHERE id = ?");
            psEditR.setString(1, rendezVous.getRendezVousDate());
            psEditR.setInt(2, rendezVous.getId_patient());
            psEditR.setString(3,  rendezVous.getDescription());
            psEditR.setInt(4, rendezVous.getId());



            psEditR.executeUpdate();

            psEditR = connection.prepareStatement("insert into action  (id_utilisateur,action,action_time) values (?,?,?)");
            psEditR.setInt(1,  Utilisateur.currentUser.getId());
            psEditR.setString(2, "Edit Rendez Vous "+rendezVous.getId());
            psEditR.setString(3,  LocalDateTime.now().toString());
            psEditR.executeUpdate();

            statusCode=201;
        } catch (SQLException e) {
            statusCode = 400;
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (psEditR != null) {
                try {
                    psEditR.close();
                } catch (SQLException e) {
                    statusCode = 400;
                    e.printStackTrace();
                }
            }

            DBUtil.stopConnection();
        }

        return statusCode;
    }

    public static Vector<RendezVous> getAllRendezVous(){
        Vector<RendezVous> rendezVous = new Vector<RendezVous>();
        PreparedStatement psGetAllRendezVous = null;
        ResultSet queryOutput = null;
        try {
            Connection connection = DBUtil.getConnection();
            psGetAllRendezVous = connection.prepareStatement("SELECT * FROM rendez_vous");
            queryOutput = psGetAllRendezVous.executeQuery();
            while (queryOutput.next()) {
                RendezVous rendezVous1 = new RendezVous();
                rendezVous1.setId(queryOutput.getInt("id"));
                rendezVous1.setRendezVousDate(queryOutput.getString("rendez_vous_date"));
                rendezVous1.setId_patient(queryOutput.getInt("id_patient"));
                rendezVous1.setDescription(queryOutput.getString("description"));
                rendezVous.add(rendezVous1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (psGetAllRendezVous != null) {
                try {
                    psGetAllRendezVous.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (queryOutput != null) {
                try {
                    queryOutput.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DBUtil.stopConnection();
        }
        return rendezVous;
    }
    public static RendezVous getRendezVousById(Integer id){
        Vector<RendezVous> rendezVous = new Vector<RendezVous>();
        PreparedStatement psGetAllRendezVous = null;
        ResultSet queryOutput = null;
        try {
            Connection connection = DBUtil.getConnection();
            psGetAllRendezVous = connection.prepareStatement("SELECT * FROM rendez_vous WHERE id = ?");
            psGetAllRendezVous.setInt(1,id );

            queryOutput = psGetAllRendezVous.executeQuery();
            while (queryOutput.next()) {
                RendezVous rendezVous1 = new RendezVous();
                rendezVous1.setId(queryOutput.getInt("id"));
                rendezVous1.setRendezVousDate(queryOutput.getString("rendez_vous_date"));
                rendezVous1.setId_patient(queryOutput.getInt("id_patient"));
                rendezVous1.setDescription(queryOutput.getString("description"));
                rendezVous.add(rendezVous1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (psGetAllRendezVous != null) {
                try {
                    psGetAllRendezVous.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (queryOutput != null) {
                try {
                    queryOutput.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DBUtil.stopConnection();
        }
        return rendezVous.get(0);
    }

    public static int deleteRendezVous(Integer id){
        PreparedStatement psDeleteR = null;
        ResultSet queryOutput = null;
        int statusCode=0;

        try {
            Connection connection = DBUtil.getConnection();

            psDeleteR = connection.prepareStatement("DELETE FROM rendez_vous WHERE id = ?");
            psDeleteR.setInt(1, id);

            psDeleteR.executeUpdate();

            psDeleteR = connection.prepareStatement("insert into action  (id_utilisateur,action,action_time) values (?,?,?)");
            psDeleteR.setInt(1, Utilisateur.currentUser.getId());
            psDeleteR.setString(2, "Delete Rendez Vous id =  "+id);
            psDeleteR.setString(3,  LocalDateTime.now().toString());
            psDeleteR.executeUpdate();

            statusCode=201;
        } catch (SQLException e) {
            statusCode = 400;
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (psDeleteR != null) {
                try {
                    psDeleteR.close();
                } catch (SQLException e) {
                    statusCode = 400;
                    e.printStackTrace();
                }
            }

            DBUtil.stopConnection();
        }

        return statusCode;
    }


}
