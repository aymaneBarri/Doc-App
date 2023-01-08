package com.example.docapp.models;

import java.util.Vector;

public class Role {
    private int id;
    private String name;
    private Vector<Permission> permissions;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(int id, String name) {
        this(name);
    }

    public Role(int id, String name, Vector<Permission> permissions) {
        this(id, name);
        this.permissions = permissions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Vector<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return name;
    }
}
