package com.szemereadam.cma.model;

public enum ApplicationUserPermission {

    ADMIN_READ("admin:read"),
    CLIENT_READ("client:read");

    private String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
