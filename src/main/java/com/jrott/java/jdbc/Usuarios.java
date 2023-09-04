package com.jrott.java.jdbc;

import java.sql.Connection;

public class Usuarios {
    private static Connection connection;
    private int id;
    private String nombre;
    private String apellido;
    private String username;
    private String email;
    private String accesos;

    public Usuarios(int id, String nombre, String apellido, String username, String usuario, String contraseña, String tipoAcceso) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;

    }

    public Usuarios(int id, String nombre, String apellido, String usuario, String contraseña, String tipoAcceso) {
    }

}
