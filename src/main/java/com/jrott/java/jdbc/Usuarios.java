package com.jrott.java.jdbc;

import java.sql.SQLException;

public class Usuarios {

    private int id;
    private String nombre;
    private String apellido;
    private String username;
    private byte[] contraseñaHash;
    private String rol;

    public Usuarios(int id, String nombre, String apellido, String username, byte[] contraseñaHash, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.contraseñaHash = contraseñaHash;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getContraseñaHash() {
        return contraseñaHash;
    }

    public void setContraseñaHash(byte[] contraseñaHash) {
        this.contraseñaHash = contraseñaHash;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }


    protected void ejecutarQuery(String sql, Object... parametros) throws SQLException {

    }
}
