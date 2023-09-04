package com.jrott.java.jdbc;

import java.sql.*;

public class LoginValidation {

    public static String validateUser(String username, String password) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            // Establecer conexión con la base de datos
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/facturacion", "root", "sasa");

            // Preparar la consulta SQL
            String sql = "SELECT tipo_acceso FROM usuarios WHERE nombre_usuario = ? AND contraseña = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Ejecutar consulta y obtener resultados
            resultSet = preparedStatement.executeQuery();

            // Verificar resultados
            if (resultSet.next()) {
                String tipoAcceso = resultSet.getString("tipo_acceso");
                if ("admin".equals(tipoAcceso) || "cajero".equals(tipoAcceso)) {
                    UsuarioActual.getInstance().setUsuario(username);
                    return tipoAcceso;
                }
            }
        } catch (SQLException ignored) {
        }
        return null;
    }
}