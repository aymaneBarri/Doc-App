package com.example.docapp.dao;

import com.example.docapp.models.*;
import com.example.docapp.util.DBUtil;
import com.example.docapp.util.DateFormatter;
import com.example.docapp.util.Encryptor;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class UtilisateurDAO {
    public static int addUtilisateur(Utilisateur utilisateur) {
        int statusCode;
        int id;
        PreparedStatement psAddUser = null;

//        try (
//                Connection connection = dataSource.getConnection();
//                PreparedStatement statement = connection.prepareStatement(SQL_INSERT,
//                        Statement.RETURN_GENERATED_KEYS);
//        ) {
//            statement.setString(1, user.getName());
//            statement.setString(2, user.getPassword());
//            statement.setString(3, user.getEmail());
//            // ...
//
//            int affectedRows = statement.executeUpdate();
//
//            if (affectedRows == 0) {
//                throw new SQLException("Creating user failed, no rows affected.");
//            }
//
//            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    user.setId(generatedKeys.getLong(1));
//                } else {
//                    throw new SQLException("Creating user failed, no ID obtained.");
//                }
//            }
//        }

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
//            System.out.println(utilisateur+"\n"+role);
            int affectedRows = psAddUser.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = psAddUser.getGeneratedKeys()) {
                if (generatedKeys.next()) {
//                    utilisateur.setId(generatedKeys.getInt(1));
//
//                    for (Permission permission : permissions) {
//                        String query2 = "INSERT INTO permission VALUES (?, ?, ?, ?, ?, ?)";
//                        PreparedStatement psAddUserRole = connection.prepareStatement(query2);
//                        psAddUserRole.setInt(1, utilisateur.getId());
//                        psAddUserRole.setString(2, permission.getSubject());
//                        psAddUserRole.setInt(3, permission.isCanView() ? 1 : 0);
//                        psAddUserRole.setInt(4, permission.isCanAdd() ? 1 : 0);
//                        psAddUserRole.setInt(5, permission.isCanModify() ? 1 : 0);
//                        psAddUserRole.setInt(6, permission.isCanDelete() ? 1 : 0);
//                        psAddUserRole.executeUpdate();
//                        psAddUserRole.close();
//                    }

                    Action action = new Action("Ajout d'un nouveau utilisateur id: " + generatedKeys.getInt(1), LocalDateTime.now().format(DateFormatter.formatter), Utilisateur.currentUser.getId());
                    ActionDAO.addAction(action);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }


//            ResultSet resultSet = psAddUser.getGeneratedKeys();
//
//            if (resultSet.next()) {
//                id = resultSet.getInt(1);
//
//                for (Permission permission : permissions) {
//                    String query2 = "INSERT INTO permission VALUES (?, ?, ?, ?, ?, ?)";
//                    PreparedStatement psAddUserRole = connection.prepareStatement(query2);
//                    psAddUserRole.setInt(1, insertedUsersId);
//                    psAddUserRole.setString(2, permission.getSubject());
//                    psAddUserRole.setInt(3, permission.isCanView() ? 1 : 0);
//                    psAddUserRole.setInt(4, permission.isCanAdd() ? 1 : 0);
//                    psAddUserRole.setInt(5, permission.isCanModify() ? 1 : 0);
//                    psAddUserRole.setInt(6, permission.isCanDelete() ? 1 : 0);
//                    psAddUserRole.executeUpdate();
//                    psAddUserRole.close();
//                }
//            }

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
                System.out.println("password not changed");
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
                System.out.println("password changed");
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
            System.out.println(e.getMessage());
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

//    public static Vector<Permission> getUserPermissions(int id) {
//        Vector<Permission> permissions = new Vector<Permission>();
//        Permission permission;
//        PreparedStatement psGetUserPermissions = null;
//        ResultSet queryOutput = null;
//
//        try {
//            Connection connection = DBUtil.getConnection();
//
//            psGetUserPermissions = connection.prepareStatement("SELECT * FROM permission WHERE id_role = ?");
//            psGetUserPermissions.setInt(1, id);
//            queryOutput = psGetUserPermissions.executeQuery();
//
//            while (queryOutput.next()) {
//                permission = new Permission();
//                permission.setIdRole(queryOutput.getInt("id_utilisateur"));
//                permission.setSubject(queryOutput.getString("subject"));
//                permission.setCanView(queryOutput.getInt("canView") == 1);
//                permission.setCanAdd(queryOutput.getInt("canAdd") == 1);
//                permission.setCanModify(queryOutput.getInt("canModify") == 1);
//                permission.setCanDelete(queryOutput.getInt("canDelete") == 1);
//                permissions.add(permission);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (queryOutput != null) {
//                try {
//                    queryOutput.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (psGetUserPermissions != null) {
//                try {
//                    psGetUserPermissions.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            DBUtil.stopConnection();
//        }
//        return permissions;
//    }


    public int updateUtilisateur(Utilisateur utilisateur, Vector<Permission> rolesUtilisateurs) {
        PreparedStatement psAddUser = null;
        PreparedStatement psAddUser2 = null;
        ResultSet queryOutput = null;
        int statusCode = 0;

        try {
            Connection connection = DBUtil.getConnection();

            psAddUser = connection.prepareStatement("UPDATE utilisateur SET first_name=?, last_name=?, email=?, cin=?, phone=?, password=? WHERE id=?");
            psAddUser2 = connection.prepareStatement("UPDATE utilisateur_role SET canView=?, canAdd=?, canModify=?, canDelete=? WHERE id_utilisateur=? AND id_role=?");

            psAddUser.setString(1, utilisateur.getFirstName());
            psAddUser.setString(2, utilisateur.getLastName());
            psAddUser.setString(3, utilisateur.getEmail());
            psAddUser.setString(4, utilisateur.getCin());
            psAddUser.setString(5, utilisateur.getPhoneNumber());
            psAddUser.setString(6, utilisateur.getPassword());
            psAddUser.setInt(7, utilisateur.getId());

            for (int i = 0; i < rolesUtilisateurs.size(); i++) {
                psAddUser2.setInt(1, rolesUtilisateurs.get(i).isCanView() ? 1 : 0);
                psAddUser2.setInt(2, rolesUtilisateurs.get(i).isCanAdd() ? 1 : 0);
                psAddUser2.setInt(3, rolesUtilisateurs.get(i).isCanModify() ? 1 : 0);
                psAddUser2.setInt(4, rolesUtilisateurs.get(i).isCanDelete() ? 1 : 0);
                psAddUser2.setInt(5, rolesUtilisateurs.get(i).getIdRole());
                psAddUser2.setString(6, rolesUtilisateurs.get(i).getSubject());
                psAddUser2.executeUpdate();
            }

            psAddUser.executeUpdate();
            statusCode = 200;
        } catch (SQLException e) {
            statusCode = 400;
            throw new RuntimeException(e);
        } finally {
            if (psAddUser != null) {
                try {
                    psAddUser.close();
                } catch (SQLException e) {
                    statusCode = 400;
                    e.printStackTrace();
                }
            }

            DBUtil.stopConnection();
        }

        return statusCode;

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
            System.out.println("User retrieved" + user);
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

    public static Role getUserRole(Utilisateur utilisateur) {
        Role role = new Role();
        PreparedStatement psGetRole = null;
        ResultSet queryOutput = null;

        try {
            Connection connection = DBUtil.getConnection();
            psGetRole = connection.prepareStatement("SELECT * FROM role where id = ?");
            psGetRole.setInt(1, utilisateur.getIdRole());
            queryOutput = psGetRole.executeQuery();

            if (queryOutput.next()) {
                role.setId(queryOutput.getInt("id"));
                role.setName(queryOutput.getString("name"));
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

            if (psGetRole != null) {
                try {
                    psGetRole.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            DBUtil.stopConnection();
        }

        return role;
    }

    public static int editPermissions(Utilisateur utilisateur, Vector<Permission> permissions) {

        PreparedStatement psEditPermissions = null;
        int statusCode = 0;

        try {
            Connection connection = DBUtil.getConnection();

            for (Permission permission : permissions) {
                psEditPermissions = connection.prepareStatement("UPDATE permission SET canView = ?, canAdd = ?, canModify = ?, canDelete = ? WHERE id_utilisateur = ? AND subject = ?");
                psEditPermissions.setBoolean(1, permission.isCanView());
                psEditPermissions.setBoolean(2, permission.isCanAdd());
                psEditPermissions.setBoolean(3, permission.isCanModify());
                psEditPermissions.setBoolean(4, permission.isCanDelete());
                psEditPermissions.setInt(5, utilisateur.getId());
                psEditPermissions.setString(6, permission.getSubject());
            }

            assert psEditPermissions != null;
            psEditPermissions.executeUpdate();

            psEditPermissions = connection.prepareStatement("INSERT INTO action (id_utilisateur, action, action_time) VALUES (?, ?, ?)");
            psEditPermissions.setInt(1, Utilisateur.currentUser.getId());
            psEditPermissions.setString(2, "Modification de l'utilisateur id = " + utilisateur.getId());
            psEditPermissions.setString(3, LocalDateTime.now().format(DateFormatter.formatter));
            psEditPermissions.executeUpdate();

            statusCode = 201;
        } catch (SQLException e) {
            statusCode = 400;
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (psEditPermissions != null) {
                try {
                    psEditPermissions.close();
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