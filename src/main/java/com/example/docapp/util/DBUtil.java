package com.example.docapp.util;

import com.example.docapp.dao.PermissionDAO;
import com.example.docapp.dao.RoleDAO;
import com.example.docapp.models.Role;
import com.example.docapp.models.Utilisateur;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.regex.Pattern;

import static com.example.docapp.util.Encryptor.encryptString;

public class DBUtil {
    public static Connection connection = null;

    public static Connection getConnection(){
        String databaseName = "docapp";
        String databaseUser = "root";
        String databasePassword = "";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (SQLException | ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de connexion");
            alert.setContentText("Veuillez lancer le serveur et la base de données.");
            alert.showAndWait();

        }

        return connection;
    }

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static void stopConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int login(ActionEvent event, String email, String password) {
        PreparedStatement psLogin = null;
        ResultSet queryOutput = null;
        int statusCode = 0;

        try {
            Connection connection = DBUtil.getConnection();

            psLogin = connection.prepareStatement("SELECT * FROM utilisateur WHERE email = ?");
            psLogin.setString(1, email.toLowerCase().trim());
            queryOutput = psLogin.executeQuery();
            if (queryOutput.isBeforeFirst()) {
                while (queryOutput.next()) {
                    password = encryptString(password);
                    if (!(queryOutput.getString("password").equals(password))){
                        statusCode = 400;
                    } else {
                        int id = queryOutput.getInt(1);
                        int idRole = queryOutput.getInt(8);
                        Utilisateur.currentUser = new Utilisateur(id, queryOutput.getString(2), queryOutput.getString(3), queryOutput.getString(4), queryOutput.getString(5), queryOutput.getString(6), queryOutput.getString(7), idRole);
                        Role role = RoleDAO.getRoleById(idRole);
                        role.setPermissions(PermissionDAO.getPermissionsByRole(idRole));
                        Utilisateur.currentUser.setRole(role);

                        statusCode = 200;
                    }
                }
            } else {
                statusCode = 400;
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

        return statusCode;
    }
}
