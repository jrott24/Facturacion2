package com.jrott.java.jdbc.DAO;

import com.jrott.java.jdbc.Usuarios;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class UsuariosDAO {

    private Connection connection;

    public UsuariosDAO(Connection connection) throws SQLException {
        // Conecta con la base de datos
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mi_base_de_datos", "root", "password");
    }

    public void close() throws SQLException {
        // Cierra la conexión con la base de datos
        this.connection.close();
    }

    public Usuarios findByUsername(String nombreUsuario) throws SQLException {
        // Consulta a la base de datos para obtener el usuario
        var sql = "SELECT * FROM usuarios WHERE nombreUsuario = ?";
        ResultSet resultSet;
        resultSet = this.ejecutarQuery(sql, nombreUsuario);

        // Si el usuario no existe, devuelve null
        if (!resultSet.next()) {
            return null;
        }

        // Devuelve el usuario
        var id = resultSet.getInt("id");
        var nombre = resultSet.getString("nombre");
        var apellido = resultSet.getString("apellido");
        var username = resultSet.getString("username");
        var contraseñaHash = resultSet.getBytes("contraseñaHash");
        var rol = resultSet.getString("rol");

        return new Usuarios(id, nombre, apellido, username, contraseñaHash, rol);
    }

    private ResultSet ejecutarQuery(String sql, String nombreUsuario) throws SQLException {
        // Crea una declaración preparada
        var preparedStatement = this.connection.prepareStatement(sql);

        // Establece el parámetro de la declaración preparada
        preparedStatement.setString(1, nombreUsuario);

        // Ejecuta la declaración preparada
        return preparedStatement.executeQuery();
    }
    public void guardarAll(List<Usuarios> usuarios) throws SQLException {
        String sql = "INSERT INTO usuarios (nombreUsuario, contraseña, rol) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        for (Usuarios usuario : usuarios) {
            statement.setString(1, usuario.getUsername());
            statement.setString(2, Arrays.toString(usuario.getContraseñaHash()));
            statement.setString(3, usuario.getRol());
            statement.addBatch();
        }

        statement.executeBatch();
    }

}

