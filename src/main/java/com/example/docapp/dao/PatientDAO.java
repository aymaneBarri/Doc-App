package com.example.docapp.dao;

import com.example.docapp.util.DBUtil;
import com.example.docapp.models.Patient;
import javafx.event.ActionEvent;

import java.sql.*;
import java.util.Vector;

public class PatientDAO {

    public static int addPatient(ActionEvent event, Patient patient) {
        PreparedStatement psAddPatient = null;
        ResultSet queryOutput = null;
        int statusCode=0;

        try {
            Connection connection = DBUtil.getConnection();

            psAddPatient = connection.prepareStatement("INSERT INTO patient (first_name,last_name,birth_date,cin,phone) VALUES (?, ?, ?, ?, ?)");
            psAddPatient.setString(1, patient.getFirstName());
            psAddPatient.setString(2, patient.getLastName());
            psAddPatient.setDate(3, (Date) patient.getBirthDate());
            psAddPatient.setString(4, patient.getCin());
            psAddPatient.setString(5, patient.getPhoneNumber());

            psAddPatient.executeUpdate();
            statusCode=201;
        } catch (SQLException e) {
            statusCode = 400;
            throw new RuntimeException(e);

        } finally {
            if (psAddPatient != null) {
                try {
                    psAddPatient.close();
                } catch (SQLException e) {
                    statusCode = 400;
                    e.printStackTrace();
                }
            }

            DBUtil.stopConnection();
        }

        return statusCode;
    }

    public int editPatient(ActionEvent event, Patient patient){
        PreparedStatement psAddP = null;
        ResultSet queryOutput = null;
        int statusCode=0;

        try {
            Connection connection = DBUtil.getConnection();

            psAddP = connection.prepareStatement("UPDATE patient SET first_name = ?, last_name = ?, birth_date = ?, cin = ?, phone = ? WHERE id = ?");
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
            if (psAddP != null) {
                try {
                    psAddP.close();
                } catch (SQLException e) {
                    statusCode = 400;
                    e.printStackTrace();
                }
            }

            DBUtil.stopConnection();
        }

        return statusCode;
    }

    public int deletePatient(int id){
        PreparedStatement psAddP = null;
        ResultSet queryOutput = null;
        int statusCode=0;

        try {
            Connection connection = DBUtil.getConnection();

            psAddP = connection.prepareStatement("DELETE from patient WHERE id=?");

            psAddP.setInt(1, id);

            psAddP.executeUpdate();
            statusCode=200;
        } catch (SQLException e) {
            statusCode = 400;
            throw new RuntimeException(e);
        } finally {
            if (psAddP != null) {
                try {
                    psAddP.close();
                } catch (SQLException e) {
                    statusCode = 400;
                    e.printStackTrace();
                }
            }

            DBUtil.stopConnection();
        }

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
