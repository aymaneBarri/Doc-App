package com.example.docapp.dao;

import com.example.docapp.models.RendezVous;
import com.example.docapp.models.Utilisateur;
import com.example.docapp.models.Visite;
import com.example.docapp.util.DBUtil;
import com.example.docapp.util.DateFormatter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Vector;

public class VisiteDAO {
    
        public static int addVisite(Visite visite) {
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
                pasAddVisite.setInt(1,  Utilisateur.currentUser.getId());
                pasAddVisite.setString(2, "Ajout d'une visite");
                pasAddVisite.setString(3, LocalDateTime.now().format(DateFormatter.formatter));
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

        public static int editVisite(Visite visite){

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
                psEditR.setInt(1,  Utilisateur.currentUser.getId());
                psEditR.setString(2, "Modification de la visite id = "+visite.getId());
                psEditR.setString(3, LocalDateTime.now().format(DateFormatter.formatter));
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

        public static int deleteVisite(String id ){
            PreparedStatement psDeleteR = null;
            ResultSet queryOutput = null;
            int statusCode=0;

            try {
                Connection connection = DBUtil.getConnection();

                psDeleteR = connection.prepareStatement("DELETE FROM visite WHERE id = ?");
                psDeleteR.setString(1, id);

                psDeleteR.executeUpdate();
                
                psDeleteR = connection.prepareStatement("insert into action  (id_utilisateur,action,action_time) values (?,?,?)");
                psDeleteR.setInt(1,  Utilisateur.currentUser.getId());
                psDeleteR.setString(2, "Suppression de la visite id = "+id);
                psDeleteR.setString(3, LocalDateTime.now().format(DateFormatter.formatter));
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

        public  static  Vector<Visite> getAllVisites(String search) {
            PreparedStatement psGetAllVisites = null;
            ResultSet queryOutput = null;
            Vector<Visite> visites = new Vector<Visite>();

            try {
                Connection connection = DBUtil.getConnection();

                psGetAllVisites = connection.prepareStatement("SELECT * FROM visite  where visit_date like ? or description like ? or assurance like ? or illness like ? or amount like ?  or prescription like ? or id_patient in (select id from patient where first_name like ? or last_name like ?) ");
                psGetAllVisites.setString(1, "%"+search+"%");
                psGetAllVisites.setString(2, "%"+search+"%");
                psGetAllVisites.setString(3, "%"+search+"%");
                psGetAllVisites.setString(4, "%"+search+"%");
                psGetAllVisites.setString(5, "%"+search+"%");
                psGetAllVisites.setString(6, "%"+search+"%");
                psGetAllVisites.setString(7, "%"+search+"%");
                psGetAllVisites.setString(8, "%"+search+"%");




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

    public  static  Vector<Visite> getRecentVistes(String id) {
        PreparedStatement psGetAllVisites = null;
        ResultSet queryOutput = null;
        Vector<Visite> visites = new Vector<Visite>();
        try {
            Connection connection = DBUtil.getConnection();

            psGetAllVisites = connection.prepareStatement("SELECT id, amount, visit_date FROM visite where id_patient like ? order by visit_date desc");
            psGetAllVisites.setString(1, "%"+id+"%");

            queryOutput = psGetAllVisites.executeQuery();

            while (queryOutput.next()) {
                Visite visite = new Visite();
                visite.setId(queryOutput.getInt("id"));
                visite.setVisit_date(queryOutput.getString("visit_date"));
               /* visite.setDescription(queryOutput.getString("description"));
                visite.setAssurance(queryOutput.getString("assurance"));
                visite.setIllness(queryOutput.getString("illness"));*/
                visite.setAmount(queryOutput.getDouble("amount"));
                /*visite.setId_patient(queryOutput.getInt("id_patient"));*/
                /*visite.setPrescription(queryOutput.getString("prescription"));*/
                System.out.println("visite: " + visite);
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

    public  static  Vector<Visite> getVistes(String id) {
        PreparedStatement psGetAllVisites = null;
        ResultSet queryOutput = null;
        Vector<Visite> visites = new Vector<Visite>();
        try {
            Connection connection = DBUtil.getConnection();

            psGetAllVisites = connection.prepareStatement("SELECT id, visit_date, description, assurance, illness, amount, id_patient, prescription FROM visite where id_patient like ? order by visit_date desc");
            psGetAllVisites.setString(1, "%"+id+"%");

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
      public  static  Visite getVisiteById( String id) {
            PreparedStatement psGetAllVisites = null;
            ResultSet queryOutput = null;
            Vector<Visite> visites = new Vector<Visite>();

            try {
                Connection connection = DBUtil.getConnection();

                psGetAllVisites = connection.prepareStatement("SELECT * FROM visite WHERE id = ?");
                psGetAllVisites.setString(1, id);
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
