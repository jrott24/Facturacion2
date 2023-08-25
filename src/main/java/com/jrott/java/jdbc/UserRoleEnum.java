package com.jrott.java.jdbc;

public enum UserRoleEnum {

    ADMIN(1),
    USER(2);

    private int id;

    UserRoleEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static UserRoleEnum getById(int id) {
        for (UserRoleEnum role : values()) {
            if (role.getId() == id) {
                return role;
            }
        }

        return null;
    }
}
