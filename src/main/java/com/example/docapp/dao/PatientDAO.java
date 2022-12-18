package com.example.docapp.dao;

import com.example.docapp.dataAccess.DBUtil;
import com.example.docapp.models.Patient;
import com.example.docapp.models.Utilisateur;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.Vector;

public class PatientDAO {


    public static int addPatient(ActionEvent event, Patient patient) {
        PreparedStatement psAddP = null;
        ResultSet queryOutput = null;
        int statusCode=0;

        try {
            Connection connection = DBUtil.getConnection();

            psAddP = connection.prepareStatement("INSERT INTO patient (first_name,last_name,birth_date,cin,phone) VALUES (?, ?, ?, ?, ?)");
            psAddP.setString(1, patient.getFirstName());
            psAddP.setString(2, patient.getLastName());
            psAddP.setDate(3, (Date) patient.getBirthDate());
            psAddP.setString(4, patient.getCin());
            psAddP.setString(5, patient.getPhoneNumber());

            psAddP.executeUpdate();
            statusCode=201;
        } catch (SQLException e) {
            statusCode = 400;
            throw new RuntimeException(e);

        } finally {
            try {
                if (psAddP != null) {
                    psAddP.close();
                }
                if (queryOutput != null) {
                    queryOutput.close();
                }
            } catch (SQLException e) {

                statusCode = 400;
                throw new RuntimeException(e);
            }
        }
        DBUtil.stopConnection();
        return statusCode;
    }


    public int editPatient(ActionEvent event, Patient patient){


        PreparedStatement psAddP = null;
        ResultSet queryOutput = null;
        int statusCode=0;

        try {
            Connection connection = DBUtil.getConnection();

            psAddP = connection.prepareStatement("UPDATE patient set first_name=?,last_name=?,birth_date=?,cin=?,phone=? WHERE id=?");
            psAddP.setString(1, patient.getFirstName());
            psAddP.setString(2, patient.getLastName());
            psAddP.setDate(3, (Date) patient.getBirthDate());
            psAddP.setString(4, patient.getCin());
            psAddP.setString(5, patient.getPhoneNumber());
            psAddP.setInt(6, patient.getId());

            psAddP.executeUpdate();
            statusCode=201;
        } catch (SQLException e) {
            statusCode = 400;
            throw new RuntimeException(e);
        } finally {
            try {
                if (psAddP != null) {
                    psAddP.close();
                }
                if (queryOutput != null) {
                    queryOutput.close();
                }
            } catch (SQLException e) {
                statusCode = 400;
                throw new RuntimeException(e);
            }
        }
        DBUtil.stopConnection();
        return statusCode;
    }


     public int deletePatient(int id){

         PreparedStatement psAddP = null;
         ResultSet queryOutput = null;
         int statusCode=0;

         try {
             Connection connection = DBUtil.getConnection();

             psAddP = connection.prepareStatement("DELETE patient WHERE id=?");

             psAddP.setInt(1, id);

             psAddP.executeUpdate();
             statusCode=200;
         } catch (SQLException e) {
             statusCode = 400;
             throw new RuntimeException(e);
         } finally {
             try {
                 if (psAddP != null) {
                     psAddP.close();
                 }
                 if (queryOutput != null) {
                     queryOutput.close();
                 }
             } catch (SQLException e) {
                 statusCode = 400;
                 throw new RuntimeException(e);
             }
         }
         DBUtil.stopConnection();
         return statusCode;
    }


    public Vector <Patient> getPatients(){
        Vector<Patient> patients = new Vector<Patient>();
        Patient patient = new Patient();
        PreparedStatement psLogin = null;
        ResultSet queryOutput = null;

        try {
            Connection connection = DBUtil.getConnection();

            psLogin = connection.prepareStatement("SELECT * FROM patient");
            queryOutput = psLogin.executeQuery();

            while (queryOutput.next()) {
                patient.setId(queryOutput.getInt("id"));
                patient.setFirstName(queryOutput.getString("first_name"));
                patient.setLastName(queryOutput.getString("last_name"));
                patient.setBirthDate(queryOutput.getDate("birth_date"));
                patient.setCin(queryOutput.getString("cin"));
                patient.setPhoneNumber(queryOutput.getString("phone"));
                patient.setDescription(queryOutput.getString("description"));

                patients.add(patient);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (queryOutput != null) {
                try {
                    queryOutput.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (psLogin != null) {
                try {
                    psLogin.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            DBUtil.stopConnection();
        }


        return patients;
    }

}
