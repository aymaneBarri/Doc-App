package com.example.docapp.dao;

import com.example.docapp.dataAccess.DBUtil;
import com.example.docapp.models.Role;
import com.example.docapp.models.RolesUtilisateur;
import com.example.docapp.models.Utilisateur;

import java.sql.*;
import java.util.Vector;

public class UtilisateurDAO {


    public int addUtilisateur(Utilisateur utilisateur, Vector<Role> roles, Vector<RolesUtilisateur> rolesUtilisateurs) {
        int statusCode = 0;
        int id=0;
        DBUtil dbutil= new DBUtil();
        try {
            Connection connection = DBUtil.getConnection();
            String query = "INSERT INTO utilisateur (first_name, last_name, email, password, cin, phone) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, utilisateur.getFirstName());
            preparedStatement.setString(2, utilisateur.getLastName());
            preparedStatement.setString(3, utilisateur.getEmail());
            preparedStatement.setString(4, dbutil.encryptPassword(utilisateur.getPassword()));
            preparedStatement.setString(5, utilisateur.getCin());
            preparedStatement.setString(6, utilisateur.getPhoneNumber());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
               for (int i = 0; i < roles.size(); i++) {
                    String query2 = "INSERT INTO utilisateur_role (id_role, id_utilisateur,canView,canAdd,canModify,canDelete) VALUES (?, ?,?,?,?,?)";
                    PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                    preparedStatement2.setInt(1, roles.get(i).getId());
                    preparedStatement2.setInt(2, id);
                    preparedStatement2.setInt(3, rolesUtilisateurs.get(i).isCanView()?1:0);
                    preparedStatement2.setInt(4, rolesUtilisateurs.get(i).isCanAdd()?1:0);
                    preparedStatement2.setInt(5, rolesUtilisateurs.get(i).isCanModify()?1:0);
                    preparedStatement2.setInt(5, rolesUtilisateurs.get(i).isCanDelete()?1:0);
                    preparedStatement2.executeUpdate();
                    if(preparedStatement2!=null){
                        preparedStatement2.close();
                    }
                }

            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            statusCode = 201;
        } catch (SQLException e) {
            statusCode = 400;
            e.printStackTrace();
        }

        DBUtil.stopConnection();
        return statusCode;

    }

}