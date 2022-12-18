package com.example.docapp.dao;

import com.example.docapp.util.DBUtil;
import com.example.docapp.models.Role;
import com.example.docapp.models.RolesUtilisateur;
import com.example.docapp.models.Utilisateur;
import com.example.docapp.util.Encryptor;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Vector;

public class UtilisateurDAO {
    public int addUtilisateur(Utilisateur utilisateur, Vector<Role> roles, Vector<RolesUtilisateur> rolesUtilisateurs) {
        int statusCode;
        int id;
        PreparedStatement psAddUser = null;

        try {
            Connection connection = DBUtil.getConnection();

            String insertUserQuery = "INSERT INTO utilisateur (first_name, last_name, email, password, cin, phone) VALUES (?, ?, ?, ?, ?, ?)";
            psAddUser = connection.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS);
            psAddUser.setString(1, utilisateur.getFirstName());
            psAddUser.setString(2, utilisateur.getLastName());
            psAddUser.setString(3, utilisateur.getEmail());
            psAddUser.setString(4, Encryptor.encryptString(utilisateur.getPassword()));
            psAddUser.setString(5, utilisateur.getCin());
            psAddUser.setString(6, utilisateur.getPhoneNumber());
            psAddUser.executeUpdate();

            ResultSet resultSet = psAddUser.getGeneratedKeys();

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
                    preparedStatement2.setInt(6, rolesUtilisateurs.get(i).isCanDelete()?1:0);
                    preparedStatement2.executeUpdate();
                    if(preparedStatement2!=null){
                        preparedStatement2.close();
                    }
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

    public int deleteUtilisateur (int id){
        PreparedStatement psAddP = null;
        PreparedStatement psAddP2 = null;
        ResultSet queryOutput = null;
        int statusCode=0;

        try {
            Connection connection = DBUtil.getConnection();

            psAddP2 = connection.prepareStatement("DELETE from utilisateur_role WHERE id_utilisateur=?");
            psAddP = connection.prepareStatement("DELETE from utilisateur WHERE id=?");

            psAddP.setInt(1, id);
            psAddP2.setInt(1, id);


            psAddP2.executeUpdate();
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

}