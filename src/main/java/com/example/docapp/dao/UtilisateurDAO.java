package com.example.docapp.dao;

import com.example.docapp.models.*;
import com.example.docapp.util.DBUtil;
import com.example.docapp.util.Encryptor;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
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

    public static int editUtilisateur(Utilisateur utilisateur){

        PreparedStatement psEditUtilisateur = null;
        ResultSet queryOutput = null;
        int statusCode=0;

        try {
            Connection connection = DBUtil.getConnection();

            psEditUtilisateur = connection.prepareStatement("UPDATE utilisateur SET first_name = ? , last_name = ? , email = ? , password = ? , cin = ? , phone = ? WHERE id = ?");
            psEditUtilisateur.setString(1, utilisateur.getFirstName());
            psEditUtilisateur.setString(2, utilisateur.getLastName());
            psEditUtilisateur.setString(3,  utilisateur.getEmail());
            psEditUtilisateur.setString(4,  utilisateur.getPassword());
            psEditUtilisateur.setString(5,  utilisateur.getCin());
            psEditUtilisateur.setString(6,  utilisateur.getPhoneNumber());
            psEditUtilisateur.setInt(7,  utilisateur.getId());
            psEditUtilisateur.executeUpdate();

            psEditUtilisateur = connection.prepareStatement("INSERT INTO action (id_utilisateur, action, action_time) VALUES (?, ?, ?)");
            System.out.println(Utilisateur.currentUser.getId());
            psEditUtilisateur.setInt(1, Utilisateur.currentUser.getId());
            psEditUtilisateur.setString(2, "Modification de l'utilisateur id = " + utilisateur.getId());
            psEditUtilisateur.setString(3, LocalDateTime.now().toString());
            psEditUtilisateur.executeUpdate();

            statusCode=201;
        } catch (SQLException e) {
            statusCode = 400;
            System.out.println(e.getMessage());
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

    public Vector<Utilisateur> getUtilisateurs(){
        Vector<Utilisateur> utilisateurs = new Vector<Utilisateur>();
        Utilisateur utilisateur = null;
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
     public Vector<RolesUtilisateur> getUtilisateurRoles(int id){
         Vector<RolesUtilisateur> rolesUtilisateur = new Vector<RolesUtilisateur>();
         RolesUtilisateur roleUtilisateur = new RolesUtilisateur();
         PreparedStatement psLogin = null;
         ResultSet queryOutput = null;

         try {
             Connection connection = DBUtil.getConnection();

             psLogin = connection.prepareStatement("SELECT * FROM utilisateur_role WHERE id_utilisateur=?");
                psLogin.setInt(1, id);
             queryOutput = psLogin.executeQuery();

             while (queryOutput.next()) {
                    roleUtilisateur.setIdUtilisateur(queryOutput.getInt("id_utilisateur"));
                    roleUtilisateur.setIdRole(queryOutput.getInt("id_role"));
                    roleUtilisateur.setCanView(queryOutput.getInt("canView")==1?true:false);
                    roleUtilisateur.setCanAdd(queryOutput.getInt("canAdd")==1?true:false);
                    roleUtilisateur.setCanModify(queryOutput.getInt("canModify")==1?true:false);
                    roleUtilisateur.setCanDelete(queryOutput.getInt("canDelete")==1?true:false);
                    rolesUtilisateur.add(roleUtilisateur);
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
         return rolesUtilisateur;
     }


        public int updateUtilisateur (Utilisateur utilisateur, Vector<RolesUtilisateur> rolesUtilisateurs){
            PreparedStatement psAddUser = null;
            PreparedStatement psAddUser2 = null;
            ResultSet queryOutput = null;
            int statusCode=0;

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

                for(int i=0; i<rolesUtilisateurs.size(); i++){
                    psAddUser2.setInt(1, rolesUtilisateurs.get(i).isCanView()?1:0);
                    psAddUser2.setInt(2, rolesUtilisateurs.get(i).isCanAdd()?1:0);
                    psAddUser2.setInt(3, rolesUtilisateurs.get(i).isCanModify()?1:0);
                    psAddUser2.setInt(4, rolesUtilisateurs.get(i).isCanDelete()?1:0);
                    psAddUser2.setInt(5, rolesUtilisateurs.get(i).getIdUtilisateur());
                    psAddUser2.setInt(6, rolesUtilisateurs.get(i).getIdRole());
                    psAddUser2.executeUpdate();
                }

                psAddUser.executeUpdate();
                statusCode=200;
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

public Role getRole(int id){
    Role role = new Role();
    PreparedStatement psLogin = null;
    ResultSet queryOutput = null;

    try {
        Connection connection = DBUtil.getConnection();

        psLogin = connection.prepareStatement("SELECT * FROM role WHERE id=?");
        psLogin.setInt(1, id);
        queryOutput = psLogin.executeQuery();

        while (queryOutput.next()) {
            role.setId(queryOutput.getInt("id"));
            role.setSubject(queryOutput.getString("subject"));
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
    return role;
}


    public Utilisateur getUserByID(String id) {

        Utilisateur user = new Utilisateur();
        PreparedStatement psLogin = null;
        ResultSet queryOutput = null;

        try {
            Connection connection = DBUtil.getConnection();
            psLogin = connection.prepareStatement("SELECT * FROM utilisateur where id = ?");
            psLogin.setString(1, id);
            queryOutput = psLogin.executeQuery();
            if (queryOutput.next()) {
                user.setId(queryOutput.getInt("id"));
                user.setFirstName(queryOutput.getString("first_name"));
                user.setLastName(queryOutput.getString("last_name"));
                user.setEmail(queryOutput.getString("email"));
                user.setCin(queryOutput.getString("cin"));
                user.setPhoneNumber(queryOutput.getString("phone"));
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
}