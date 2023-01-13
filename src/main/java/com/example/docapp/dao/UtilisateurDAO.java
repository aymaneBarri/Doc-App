package com.example.docapp.dao;

import com.example.docapp.models.*;
import com.example.docapp.util.DBUtil;
import com.example.docapp.util.DateFormatter;
import com.example.docapp.util.Encryptor;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Vector;

public class UtilisateurDAO {
    public static int addUtilisateur(Utilisateur utilisateur) {
        int statusCode;
        PreparedStatement psAddUser = null;

        try {
            Connection connection = DBUtil.getConnection();

            String insertUserQuery = "INSERT INTO utilisateur (first_name, last_name, email, password, cin, phone, id_role) VALUES (?, ?, ?, ?, ?, ?, ?)";
            psAddUser = connection.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS);
            psAddUser.setString(1, utilisateur.getFirstName());
            psAddUser.setString(2, utilisateur.getLastName());
            psAddUser.setString(3, utilisateur.getEmail());
            psAddUser.setString(4, Encryptor.encryptString(utilisateur.getPassword()));
            psAddUser.setString(5, utilisateur.getCin());
            psAddUser.setString(6, utilisateur.getPhoneNumber());
            psAddUser.setLong(7, utilisateur.getIdRole());

            int affectedRows = psAddUser.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = psAddUser.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Action action = new Action("Ajout d'un nouvel utilisateur id: " + generatedKeys.getInt(1), LocalDateTime.now().format(DateFormatter.formatter), Utilisateur.currentUser.getId());
                    ActionDAO.addAction(action);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            psAddUser.close();
            statusCode = 201;
        } catch (SQLException e) {
            statusCode = 400;
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } finally {
            if (psAddUser != null) {
                try {
                    psAddUser.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            DBUtil.stopConnection();
        }

        return statusCode;
    }

    public static int editUtilisateur(Utilisateur utilisateur, boolean isPasswordChanged) {

        PreparedStatement psEditUtilisateur = null;
        ResultSet queryOutput = null;
        int statusCode = 0;

        try {
            Connection connection = DBUtil.getConnection();
            String query = "";
            if (!isPasswordChanged) {
                query = "UPDATE utilisateur SET first_name = ?, last_name = ?, email = ?, cin = ?, phone = ?, id_role = ? WHERE id = ?";
                psEditUtilisateur = connection.prepareStatement(query);
                psEditUtilisateur.setString(1, utilisateur.getFirstName());
                psEditUtilisateur.setString(2, utilisateur.getLastName());
                psEditUtilisateur.setString(3, utilisateur.getEmail());
                psEditUtilisateur.setString(4, utilisateur.getCin());
                psEditUtilisateur.setString(5, utilisateur.getPhoneNumber());
                psEditUtilisateur.setInt(6, utilisateur.getIdRole());
                psEditUtilisateur.setInt(7, utilisateur.getId());
            } else {
                psEditUtilisateur = connection.prepareStatement("UPDATE utilisateur SET first_name = ? , last_name = ? , email = ? , password = ? , cin = ? , phone = ?, id_role = ? WHERE id = ?");
                psEditUtilisateur.setString(1, utilisateur.getFirstName());
                psEditUtilisateur.setString(2, utilisateur.getLastName());
                psEditUtilisateur.setString(3, utilisateur.getEmail());
                psEditUtilisateur.setString(4, Encryptor.encryptString(utilisateur.getPassword()));
                psEditUtilisateur.setString(5, utilisateur.getCin());
                psEditUtilisateur.setString(6, utilisateur.getPhoneNumber());
                psEditUtilisateur.setInt(7, utilisateur.getIdRole());
                psEditUtilisateur.setInt(8, utilisateur.getId());

            }
            psEditUtilisateur.executeUpdate();

            Action action = new Action("Modification de l'utilisateur id = " + utilisateur.getId(), LocalDateTime.now().format(DateFormatter.formatter), Utilisateur.currentUser.getId());
            ActionDAO.addAction(action);

            statusCode = 201;
        } catch (SQLException e) {
            statusCode = 400;
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } finally {
            if (psEditUtilisateur != null) {
                try {
                    psEditUtilisateur.close();
                } catch (SQLException e) {
                    statusCode = 400;
                    e.printStackTrace();
                }
            }

            DBUtil.stopConnection();
        }

        return statusCode;
    }

    public static int deleteUtilisateur(int id) {
        PreparedStatement psDeleteUser = null;
        PreparedStatement psAddP2 = null;
        int statusCode = 0;

        try {
            Connection connection = DBUtil.getConnection();

            psDeleteUser = connection.prepareStatement("DELETE from utilisateur WHERE id = ?");
            psDeleteUser.setInt(1, id);
            psDeleteUser.executeUpdate();

            Action action = new Action("Suppression de l'utilisateur id = " + id, LocalDateTime.now().format(DateFormatter.formatter), Utilisateur.currentUser.getId());
            ActionDAO.addAction(action);

            statusCode = 200;
        } catch (SQLException e) {
            statusCode = 400;
            throw new RuntimeException(e);
        } finally {
            if (psDeleteUser != null) {
                try {
                    psDeleteUser.close();
                } catch (SQLException e) {
                    statusCode = 400;
                    e.printStackTrace();
                }
            }

            DBUtil.stopConnection();
        }

        return statusCode;
    }

    public static Vector<Utilisateur> getUtilisateurs() {
        Vector<Utilisateur> utilisateurs = new Vector<Utilisateur>();
        Utilisateur utilisateur;
        PreparedStatement psLogin = null;
        ResultSet queryOutput = null;

        try {
            Connection connection = DBUtil.getConnection();

            psLogin = connection.prepareStatement("SELECT * FROM utilisateur");
            queryOutput = psLogin.executeQuery();

            while (queryOutput.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(queryOutput.getInt("id"));
                utilisateur.setFirstName(queryOutput.getString("first_name"));
                utilisateur.setLastName(queryOutput.getString("last_name"));
                utilisateur.setEmail(queryOutput.getString("email"));
                utilisateur.setCin(queryOutput.getString("cin"));
                utilisateur.setPhoneNumber(queryOutput.getString("phone"));
                utilisateur.setPassword(queryOutput.getString("password"));
                utilisateur.setIdRole(queryOutput.getInt("id_role"));
                utilisateurs.add(utilisateur);
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

        return utilisateurs;
    }

    public static Utilisateur getUserByID(int id) {

        Utilisateur user = new Utilisateur();
        PreparedStatement psLogin = null;
        ResultSet queryOutput = null;

        try {
            Connection connection = DBUtil.getConnection();
            psLogin = connection.prepareStatement("SELECT * FROM utilisateur where id = ?");
            psLogin.setInt(1, id);
            queryOutput = psLogin.executeQuery();
            if (queryOutput.next()) {
                user.setId(queryOutput.getInt("id"));
                user.setFirstName(queryOutput.getString("first_name"));
                user.setLastName(queryOutput.getString("last_name"));
                user.setEmail(queryOutput.getString("email"));
                user.setCin(queryOutput.getString("cin"));
                user.setPhoneNumber(queryOutput.getString("phone"));
                user.setIdRole(queryOutput.getInt("id_role"));
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

        return user;
    }

    public static Vector<Utilisateur> searchUtilisateurs(String search) {

        Vector<Utilisateur> utilisateurs = new Vector<Utilisateur>();
        Utilisateur utilisateur;
        PreparedStatement psLogin = null;
        ResultSet queryOutput = null;

        try {
            Connection connection = DBUtil.getConnection();

            psLogin = connection.prepareStatement("SELECT * FROM utilisateur WHERE first_name LIKE ? OR last_name LIKE ? OR email LIKE ? OR cin LIKE ? OR phone LIKE ? ORDER BY id DESC");
            psLogin.setString(1, "%"+search+"%");
            psLogin.setString(2, "%"+search+"%");
            psLogin.setString(3, "%"+search+"%");
            psLogin.setString(4, "%"+search+"%");
            psLogin.setString(5, "%"+search+"%");

            queryOutput = psLogin.executeQuery();

            while (queryOutput.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(queryOutput.getInt("id"));
                utilisateur.setFirstName(queryOutput.getString("first_name"));
                utilisateur.setLastName(queryOutput.getString("last_name"));
                utilisateur.setEmail(queryOutput.getString("email"));
                utilisateur.setCin(queryOutput.getString("cin"));
                utilisateur.setPhoneNumber(queryOutput.getString("phone"));


                utilisateurs.add(utilisateur);
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

        return utilisateurs;
    }
}