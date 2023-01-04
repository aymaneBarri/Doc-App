package com.example.docapp.dao;

import com.example.docapp.models.Action;
import com.example.docapp.models.Patient;
import com.example.docapp.models.Utilisateur;
import com.example.docapp.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Vector;

public class ActionDAO {
    public static int addAction(Action action) {
        PreparedStatement statement = null;
        int statusCode = 0;

        try {
            Connection connection = DBUtil.getConnection();

            statement = connection.prepareStatement("INSERT INTO action (action, action_time, id_utilisateur) values (?,?,?)");
            statement.setString(1, action.getAction());
            statement.setString(2, action.getActionTime());
            statement.setInt(3, action.getId_utilisateur());
            statement.executeUpdate();

            statusCode=201;
        } catch (SQLException e) {
            statusCode = 400;
            throw new RuntimeException(e);

        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    statusCode = 400;
                    e.printStackTrace();
                }
            }

            DBUtil.stopConnection();
        }

        return statusCode;
    }

    public static Vector<Action> getActions(){
        Vector<Action> actions = new Vector<Action>();
        Action action;
        PreparedStatement psLogin = null;
        ResultSet queryOutput = null;

        try {
            Connection connection = DBUtil.getConnection();
            psLogin = connection.prepareStatement("SELECT * FROM action");
            queryOutput = psLogin.executeQuery();
            while (queryOutput.next()) {
                action = new Action();
                action.setId(queryOutput.getInt("id"));
                action.setAction(queryOutput.getString("action"));
                action.setActionTime(queryOutput.getString("action_time"));
                action.setId_utilisateur(queryOutput.getInt("id_utilisateur"));


                actions.add(action);
                System.out.println(action);
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

        return actions;
    }
}
