package com.example.docapp.dataAccess;

import com.example.docapp.models.Utilisateur;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.regex.Pattern;

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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

    public String encryptPassword(String password){
        String encryptedPassword = "";
        for (int i = 0; i < password.length(); i++) {
            encryptedPassword += (char) (password.charAt(i) + 1);
        }
        return encryptedPassword;
    }
    public String decryptPassword(String password){
        String decryptedPassword = "";
        for (int i = 0; i < password.length(); i++) {
            decryptedPassword += (char) (password.charAt(i) - 1);
        }
        return decryptedPassword;
    }

    public static void login(ActionEvent event, String email, String password) {
        PreparedStatement psLogin = null;
        ResultSet queryOutput = null;

        try {
            Connection connection = DBUtil.getConnection();

            psLogin = connection.prepareStatement("SELECT * FROM utilisateur WHERE email = ?");
            psLogin.setString(1, email);
            DBUtil dbUtil = new DBUtil();
            queryOutput = psLogin.executeQuery();

            if (queryOutput.isBeforeFirst()) {
                while (queryOutput.next()) {
                    if (!(queryOutput.getString("password").equals(dbUtil.encryptPassword(password)))) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Informations incorrectes!");
                        alert.show();
                    } else {
                        Utilisateur utilisateur = new Utilisateur(queryOutput.getInt(1), queryOutput.getString(2), queryOutput.getString(3), queryOutput.getString(4), queryOutput.getString(5), queryOutput.getString(6), queryOutput.getString(7));

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Bienvenu " + utilisateur.getFirstName() + " " + utilisateur.getLastName());
                        alert.show();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Informations incorrectes!");
                alert.show();
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
    }

//    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//    //Connection
//    private static Connection conn = null;
//
//    private static String databaseName = "docapp";
//    private static String databaseUser = "root";
//    private static String databasePassword = "";
//    private static String url = "jdbc:mysql://localhost/" + databaseName;
//
//    //Connection String
//    //String connStr = "jdbc:oracle:thin:Username/Password@IP:Port/SID";
//    //Username=HR, Password=HR, IP=localhost, IP=1521, SID=xe
//    private static final String connStr = "jdbc:oracle:thin:HR/HR@localhost:1521/xe";
//    //Connect to DB
//    public static void dbConnect() throws SQLException, ClassNotFoundException {
//        //Setting Oracle JDBC Driver
//        try {
//            Class.forName(JDBC_DRIVER);
//        } catch (ClassNotFoundException e) {
//            System.out.println("Where is your MySql JDBC Driver?");
//            e.printStackTrace();
//            throw e;
//        }
//        System.out.println("MySql JDBC Driver Registered!");
//        //Establish the Oracle Connection using Connection String
//        try {
//            conn = DriverManager.getConnection(url, databaseUser, databasePassword);
//        } catch (SQLException e) {
//            System.out.println("Connection Failed! Check output console" + e);
//            e.printStackTrace();
//            throw e;
//        }
//    }
//    //Close Connection
//    public static void dbDisconnect() throws SQLException {
//        try {
//            if (conn != null && !conn.isClosed()) {
//                conn.close();
//            }
//        } catch (Exception e){
//            throw e;
//        }
//    }
}
