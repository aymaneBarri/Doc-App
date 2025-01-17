package com.example.docapp.dao;

import com.example.docapp.models.Action;
import com.example.docapp.models.Permission;
import com.example.docapp.models.Role;
import com.example.docapp.models.Utilisateur;
import com.example.docapp.util.DBUtil;
import com.example.docapp.util.DateFormatter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Vector;

public class PermissionDAO {
    public static int addPermission(Permission permission) {
        PreparedStatement psAddPermission = null;
        int statusCode = 0;

        try {
            Connection connection = DBUtil.getConnection();

            psAddPermission = connection.prepareStatement("INSERT INTO permission VALUES (?, ?, ?, ?, ?, ?)");
            psAddPermission.setInt(1, permission.getIdRole());
            psAddPermission.setString(2, permission.getSubject());
            psAddPermission.setInt(3, permission.isCanView() ? 1 : 0);
            psAddPermission.setInt(4, permission.isCanAdd() ? 1 : 0);
            psAddPermission.setInt(5, permission.isCanModify() ? 1 : 0);
            psAddPermission.setInt(6, permission.isCanDelete() ? 1 : 0);
            psAddPermission.executeUpdate();

            statusCode=201;
        } catch (SQLException e) {
            statusCode = 400;
            throw new RuntimeException(e);

        } finally {
            if (psAddPermission != null) {
                try {
                    psAddPermission.close();
                } catch (SQLException e) {
                    statusCode = 400;
                    e.printStackTrace();
                }
            }

            DBUtil.stopConnection();
        }

        return statusCode;
    }

    public static int editPermission(Permission permission) {
        PreparedStatement psEditPermission = null;
        int statusCode = 0;

        try {
            Connection connection = DBUtil.getConnection();

            psEditPermission = connection.prepareStatement("UPDATE permission SET canView = ?, canAdd = ?, canModify = ?, canDelete = ? WHERE id_role = ? AND subject = ?");
            psEditPermission.setInt(1, permission.isCanView() ? 1 : 0);
            psEditPermission.setInt(2, permission.isCanAdd() ? 1 : 0);
            psEditPermission.setInt(3, permission.isCanModify() ? 1 : 0);
            psEditPermission.setInt(4, permission.isCanDelete() ? 1 : 0);
            psEditPermission.setInt(5, permission.getIdRole());
            psEditPermission.setString(6, permission.getSubject());
            psEditPermission.executeUpdate();

            statusCode = 201;
        } catch (SQLException e) {
            statusCode = 400;
            throw new RuntimeException(e);
        } finally {
            if (psEditPermission != null) {
                try {
                    psEditPermission.close();
                } catch (SQLException e) {
                    statusCode = 400;
                    e.printStackTrace();
                }
            }

            DBUtil.stopConnection();
        }

        return statusCode;
    }

    public static int deleteRole(Role role) {
        PreparedStatement statement = null;
        ResultSet queryOutput = null;
        int statusCode = 0;

        try {
            Connection connection = DBUtil.getConnection();

            statement = connection.prepareStatement("DELETE FROM role WHERE id = ?");
            statement.setInt(1, role.getId());
            statement.executeUpdate();

            Action action = new Action("Suppression du rôle id = " + role.getId(), LocalDateTime.now().format(DateFormatter.formatter), Utilisateur.currentUser.getId());
            ActionDAO.addAction(action);

            statusCode = 200;
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

    public static Vector<Permission> getPermissionsByRole(int idRole) {
        Vector<Permission> permissions = new Vector<Permission>();
        Permission permission;
        PreparedStatement statement = null;
        ResultSet queryOutput = null;
        int statusCode = 0;

        try {
            Connection connection = DBUtil.getConnection();

            statement = connection.prepareStatement("SELECT * FROM permission WHERE id_role = ?");
            statement.setInt(1, idRole);
            queryOutput = statement.executeQuery();

            while (queryOutput.next()) {
                permission = new Permission();
                permission.setIdRole(queryOutput.getInt("id_role"));
                permission.setSubject(queryOutput.getString("subject"));
                permission.setCanView(queryOutput.getInt("canView") == 1);
                permission.setCanAdd(queryOutput.getInt("canAdd") == 1);
                permission.setCanModify(queryOutput.getInt("canModify") == 1);
                permission.setCanDelete(queryOutput.getInt("canDelete") == 1);
                permissions.add(permission);
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

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            DBUtil.stopConnection();
        }

        return permissions;
    }
}
