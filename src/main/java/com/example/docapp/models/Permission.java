package com.example.docapp.models;

public class Permission {
    private int idRole;
    private String subject;
    private boolean canView;
    private boolean canAdd;
    private boolean canModify;
    private boolean canDelete;

    public Permission() {
        idRole = 0;
        subject = "";
        canView = false;
        canAdd = false;
        canModify = false;
        canDelete = false;
    }

    public Permission(String subject, boolean canView, boolean canAdd, boolean canModify, boolean canDelete) {
        this.subject = subject;
        this.canView = canView;
        this.canAdd = canAdd;
        this.canModify = canModify;
        this.canDelete = canDelete;
    }

    public Permission(int idUtilisateur, String subject, boolean canView, boolean canAdd, boolean canModify, boolean canDelete) {
        this(subject, canView, canAdd, canModify, canDelete);
        this.idRole = idUtilisateur;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idUtilisateur) {
        this.idRole = idUtilisateur;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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
        return "Permission{" +
                "idRole=" + idRole +
                ", subject=" + subject +
                ", canView=" + canView +
                ", canAdd=" + canAdd +
                ", canModify=" + canModify +
                ", canDelete=" + canDelete +
                '}';
    }
}
