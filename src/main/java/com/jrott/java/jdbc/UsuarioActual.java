package com.jrott.java.jdbc;

public class UsuarioActual {
    private static UsuarioActual instance;
    private String usuario;

    private UsuarioActual() {
    }

    public static UsuarioActual getInstance() {
        if (instance == null) {
            instance = new UsuarioActual();
        }
        return instance;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
