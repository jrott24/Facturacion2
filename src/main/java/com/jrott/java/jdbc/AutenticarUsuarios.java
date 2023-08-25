package com.jrott.java.jdbc;

import com.jrott.java.jdbc.DAO.UsuariosDAO;
import jdk.internal.access.JavaIOFileDescriptorAccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Base64;

public class AutenticarUsuarios {

    private final UsuariosDAO usuariosDAO;

    public AutenticarUsuarios(Connection usuariosDAO) {
        this.usuariosDAO = (UsuariosDAO) usuariosDAO;
    }

    public static boolean autenticar(JavaIOFileDescriptorAccess connection, String nombreUsuario, String contraseña) throws SQLException {
        // Codificamos la contraseña
        String contraseñaCodificada = Base64.getEncoder().encodeToString(contraseña.getBytes());

        // Buscamos al usuario en la base de datos
        Usuarios usuario = usuariosDAO.findByUsername(nombreUsuario);

        // Si el usuario existe y la contraseña coincide, autenticamos al usuario
        if (usuario != null) {
            usuario.getContraseñaHash();
        }
        return false;
    }

    // Añadimos el codificador

    public static String codificar(String cadena) {
        return Base64.getEncoder().encodeToString(cadena.getBytes());
    }

    // Añadimos el descodificador

    public static String descodificar(String cadenaCodificada) {
        return new String(Base64.getDecoder().decode(cadenaCodificada));
    }
}
