package com.example.docapp.dao;

import com.example.docapp.models.Action;
import com.example.docapp.models.Utilisateur;
import com.example.docapp.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

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
}
