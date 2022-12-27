package com.example.docapp.dao;

import com.example.docapp.models.RendezVous;
import com.example.docapp.models.Visite;
import com.example.docapp.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Vector;

public class VisiteDAO {
    
        public static int addVisite(Visite visite,int user_id) {
            PreparedStatement pasAddVisite = null;
            ResultSet queryOutput = null;
            int statusCode=0;

            try {
                Connection connection = DBUtil.getConnection();

                pasAddVisite = connection.prepareStatement("INSERT INTO visite (visit_date,description,assurance,illness,amount,id_patient,prescription) VALUES (?, ?, ?, ?, ?, ?, ?)");
                pasAddVisite.setString(1, visite.getVisit_date());
                pasAddVisite.setString(2, visite.getDescription());
                pasAddVisite.setString(3,  visite.getAssurance());
                pasAddVisite.setString(4,  visite.getIllness());
                pasAddVisite.setDouble(5,  visite.getAmount());
                pasAddVisite.setInt(6,  visite.getId_patient());
                pasAddVisite.setString(7,  visite.getPrescription());


                pasAddVisite.executeUpdate();
                pasAddVisite = connection.prepareStatement("insert into action  (id_utilisateur,action,action_time) values (?,?,?)");
                pasAddVisite.setInt(1, user_id);
                pasAddVisite.setString(2, "Ajout d'une visite");
                pasAddVisite.setString(3, LocalDateTime.now().toString());
                pasAddVisite.executeUpdate();
                statusCode=201;
            } catch (SQLException e) {
                statusCode = 400;
                throw new RuntimeException(e);

            } finally {
                if (pasAddVisite != null) {
                    try {
                        pasAddVisite.close();
                    } catch (SQLException e) {
                        statusCode = 400;
                        e.printStackTrace();
                    }
                }

                DBUtil.stopConnection();
            }

            return statusCode;
        
            
        }

        public static int editVisite(Visite visite,int user_id){

            PreparedStatement psEditR = null;
            ResultSet queryOutput = null;
            int statusCode=0;

            try {
                Connection connection = DBUtil.getConnection();

                psEditR = connection.prepareStatement("UPDATE visite SET visit_date = ? , description = ? , assurance = ? , illness = ? , amount = ? , id_patient = ? , prescription = ?  WHERE id = ?");
                psEditR.setString(1, visite.getVisit_date());
                psEditR.setString(2, visite.getDescription());
                psEditR.setString(3,  visite.getAssurance());
                psEditR.setString(4,  visite.getIllness());
                psEditR.setDouble(5,  visite.getAmount());
                psEditR.setInt(6,  visite.getId_patient());
                psEditR.setString(7,  visite.getPrescription());
                psEditR.setInt(8, visite.getId());

                psEditR.executeUpdate();
                psEditR = connection.prepareStatement("insert into action  (id_utilisateur,action,action_time) values (?,?,?)");
                psEditR.setInt(1, user_id);
                psEditR.setString(2, "Modification de la visite id = "+visite.getId());
                psEditR.setString(3, LocalDateTime.now().toString());
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

        public static int deleteVisite(Visite visite , int user_id){
            PreparedStatement psDeleteR = null;
            ResultSet queryOutput = null;
            int statusCode=0;

            try {
                Connection connection = DBUtil.getConnection();

                psDeleteR = connection.prepareStatement("DELETE FROM visite WHERE id = ?");
                psDeleteR.setInt(1, visite.getId());

                psDeleteR.executeUpdate();
                
                psDeleteR = connection.prepareStatement("insert into action  (id_utilisateur,action,action_time) values (?,?,?)");
                psDeleteR.setInt(1, user_id);
                psDeleteR.setString(2, "Suppression de la visite id = "+visite.getId());
                psDeleteR.setString(3, LocalDateTime.now().toString());
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

        public  static  Vector<Visite> getAllVisites() {
            PreparedStatement psGetAllVisites = null;
            ResultSet queryOutput = null;
            Vector<Visite> visites = new Vector<Visite>();

            try {
                Connection connection = DBUtil.getConnection();

                psGetAllVisites = connection.prepareStatement("SELECT * FROM visite");
                queryOutput = psGetAllVisites.executeQuery();

                while (queryOutput.next()) {
                    Visite visite = new Visite();
                    visite.setId(queryOutput.getInt("id"));
                    visite.setVisit_date(queryOutput.getString("visit_date"));
                    visite.setDescription(queryOutput.getString("description"));
                    visite.setAssurance(queryOutput.getString("assurance"));
                    visite.setIllness(queryOutput.getString("illness"));
                    visite.setAmount(queryOutput.getDouble("amount"));
                    visite.setId_patient(queryOutput.getInt("id_patient"));
                    visite.setPrescription(queryOutput.getString("prescription"));

                    visites.add(visite);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                if (psGetAllVisites != null) {
                    try {
                        psGetAllVisites.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                DBUtil.stopConnection();
            }

            return visites;
        }
      public  static  Visite getVisiteById( Visite vs) {
            PreparedStatement psGetAllVisites = null;
            ResultSet queryOutput = null;
            Vector<Visite> visites = new Vector<Visite>();

            try {
                Connection connection = DBUtil.getConnection();

                psGetAllVisites = connection.prepareStatement("SELECT * FROM visite WHERE id = ?");
                psGetAllVisites.setInt(1, vs.getId());
                queryOutput = psGetAllVisites.executeQuery();

                while (queryOutput.next()) {
                    Visite visite = new Visite();
                    visite.setId(queryOutput.getInt("id"));
                    visite.setVisit_date(queryOutput.getString("visit_date"));
                    visite.setDescription(queryOutput.getString("description"));
                    visite.setAssurance(queryOutput.getString("assurance"));
                    visite.setIllness(queryOutput.getString("illness"));
                    visite.setAmount(queryOutput.getDouble("amount"));
                    visite.setId_patient(queryOutput.getInt("id_patient"));
                    visite.setPrescription(queryOutput.getString("prescription"));

                    visites.add(visite);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                if (psGetAllVisites != null) {
                    try {
                        psGetAllVisites.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                DBUtil.stopConnection();
            }

            return visites.get(0);
        }

}
