package com.jrott.java.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRole {

    private final Connection connection;

    public UserRole(Connection connection) {
        this.connection = connection;
    }


    public void asignarRol(String nombreUsuario, String rol) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO user_roles (nombreUsuario, rol) VALUES (?, ?)");
        statement.setString(1, nombreUsuario);
        statement.setString(2, rol);
        statement.executeUpdate();
    }

    public void eliminarRol(String nombreUsuario, String rol) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM user_roles WHERE nombreUsuario = ? AND rol = ?");
        statement.setString(1, nombreUsuario);
        statement.setString(2, rol);
        statement.executeUpdate();
    }

    public boolean tieneRol(String nombreUsuario, String rol) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_roles WHERE nombreUsuario = ? AND rol = ?");
        statement.setString(1, nombreUsuario);
        statement.setString(2, rol);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public List<String> listarRoles(String nombreUsuario) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT rol FROM user_roles WHERE nombreUsuario = ?");
        statement.setString(1, nombreUsuario);
        ResultSet resultSet = statement.executeQuery();
        List<String> roles = new ArrayList<>();
        while (resultSet.next()) {
            roles.add(resultSet.getString("rol"));
        }
        return roles;
    }
}
