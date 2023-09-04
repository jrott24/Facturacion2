package com.jrott.java.jdbc.DAO;

import com.jrott.java.jdbc.Usuarios;

import java.sql.*;

import static com.jrott.java.jdbc.Main.idUsuario;

public class UsuariosDAO {
    static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/facturacion", "root", "sasa");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public static void añadirUsuario(String nombre, String apellidos, String nombre_usuario, String contraseña, String tipoAcceso) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, apellido, nombre_usuario, contraseña, tipo_acceso) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, nombre);
        statement.setString(2, apellidos);
        statement.setString(3, nombre_usuario);
        statement.setString(4, contraseña);
        statement.setString(5, tipoAcceso);
        statement.executeUpdate();
    }

    @Transactional
    public static void eliminarUsuario(int idUsuario) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idUsuario);
        statement.executeUpdate();
    }

    @Transactional
    public static void modificarUsuario(int idUsuario, String nombre, String apellidos, String nombre_usuario, String contraseña, String tipoAcceso) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, nombre_usuario = ?, contraseña = ?, tipo_acceso = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, nombre);
        statement.setString(2, apellidos);
        statement.setString(3, nombre_usuario);
        statement.setString(4, contraseña);
        statement.setString(5, tipoAcceso);
        statement.setInt(6, idUsuario);
        statement.executeUpdate();
    }

    @Transactional
    public static void buscarUsuarioPorId() throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, idUsuario);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            new Usuarios(
                    resultSet.getInt("id"),
                    resultSet.getString("nombre"),
                    resultSet.getString("apellido"),
                    resultSet.getString("nombre_usuario"),
                    resultSet.getString("contraseña"),
                    resultSet.getString("tipo_acceso")
            );
        } else {
        }
    }

    @Transactional
    public static void listarUsuarios() throws SQLException {
        String sql = "SELECT * FROM usuarios";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        System.out.println("Listado de Usuarios:");
        System.out.printf("%-20s %-20s %-20s %-20s%n", "Nombre", "Apellido", "Nombre de Usuario", "Tipo de Acceso");

        while (resultSet.next()) {
            String nombre = resultSet.getString("nombre");
            String apellido = resultSet.getString("apellido");
            String nombreUsuario = resultSet.getString("nombre_usuario");
            String tipoAcceso = resultSet.getString("tipo_acceso");

            // Validar si algún campo es null para reemplazarlo con "No disponible"
            nombre = nombre != null ? nombre : "No disponible";
            apellido = apellido != null ? apellido : "No disponible";
            nombreUsuario = nombreUsuario != null ? nombreUsuario : "No disponible";
            tipoAcceso = tipoAcceso != null ? tipoAcceso : "No disponible";

            System.out.printf("%-20s %-20s %-20s %-20s%n", nombre, apellido, nombreUsuario, tipoAcceso);
        }
    }

}
