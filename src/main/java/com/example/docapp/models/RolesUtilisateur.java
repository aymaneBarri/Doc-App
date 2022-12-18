package com.example.docapp.models;

public class RolesUtilisateur {
    private int idUtilisateur;
    private int idRole;
    private boolean canView;
    private boolean canAdd;
    private boolean canModify;
    private boolean canDelete;

    public RolesUtilisateur() {
        idUtilisateur = 0;
        idRole = 0;
        canView = false;
        canAdd = false;
        canModify = false;
        canDelete = false;
    }

    public RolesUtilisateur(int idUtilisateur, int idRole, boolean canView, boolean canAdd, boolean canModify, boolean canDelete) {
        this.idUtilisateur = idUtilisateur;
        this.idRole = idRole;
        this.canView = canView;
        this.canAdd = canAdd;
        this.canModify = canModify;
        this.canDelete = canDelete;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public boolean isCanView() {
        return canView;
    }

    public void setCanView(boolean canView) {
        this.canView = canView;
    }

    public boolean isCanAdd() {
        return canAdd;
    }

    public void setCanAdd(boolean canAdd) {
        this.canAdd = canAdd;
    }

    public boolean isCanModify() {
        return canModify;
    }

    public void setCanModify(boolean canModify) {
        this.canModify = canModify;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }


    @Override
    public String toString() {
        return "RolesUtilisateur{" +
                "idUtilisateur=" + idUtilisateur +
                ", idRole=" + idRole +
                ", canView=" + canView +
                ", canAdd=" + canAdd +
                ", canModify=" + canModify +
                ", canDelete=" + canDelete +
                '}';
    }
}
