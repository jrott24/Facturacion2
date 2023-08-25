package com.jrott.java.jdbc.DAO;

import com.jrott.java.jdbc.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDAO {

    private final Connection connection;

    public UserRoleDAO(Connection connection) {
        this.connection = connection;
    }

    // Guarda un nuevo registro de rol de usuario
    public void guardar(UserRole userRole) throws SQLException {
        String sql = "INSERT INTO user_roles (usuario, rol) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, userRole.getUsuario());
        statement.setString(2, userRole.getRol());
        statement.execute();
    }

    // Devuelve todos los registros de roles de usuario
    public List<UserRole> listar() throws SQLException {
        String sql = "SELECT usuario, rol FROM user_roles";
        ResultSet resultSet = connection.createStatement().executeQuery(sql);

        List<UserRole> userRoles = new ArrayList<>();
        while (resultSet.next()) {
            userRoles.add(new UserRole(
                    resultSet.getString("usuario"),
                    resultSet.getString("rol")
            ));
        }

        return userRoles;
    }

    // Devuelve el rol de usuario para un usuario dado
    public String obtenerRol(String usuario) throws SQLException {
        String sql = "SELECT rol FROM user_roles WHERE usuario = ?";
        ResultSet resultSet = connection.prepareStatement(sql).setString(1, usuario).executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("rol");
        }

        return null;
    }
}
