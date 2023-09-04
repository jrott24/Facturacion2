package com.jrott.java.jdbc.DAO;

import com.jrott.java.jdbc.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductosDAO {

    static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/facturacion", "root", "sasa");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listarProductos() throws SQLException {
        String sql = "SELECT * FROM productos";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            List<Producto> productos = new ArrayList<>();

            while (resultSet.next()) {
                Producto producto = new Producto();
                producto.setId(resultSet.getInt("id"));
                producto.setNombre(resultSet.getString("nombre"));
                producto.setDescripcion(resultSet.getString("descripcion"));
                producto.setPrecio(resultSet.getDouble("precio"));

                productos.add(producto);
            }
            System.out.println("Lista de productos:");
            for (Producto producto : productos) {
                System.out.println(producto.toString());
            }
        }
    }

    public static void buscarProductoPorId(int idProducto) throws SQLException {
        String sql = "SELECT * FROM productos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idProducto);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Producto productoEncontrado = new Producto(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("descripcion"),
                            resultSet.getDouble("precio")
                    );
                    System.out.println("Producto encontrado: ");
                    System.out.println(productoEncontrado.toString());
                } else {
                    System.out.println("Producto con el ID especificado no encontrado.");
                }
            }
        }
    }

    public static void añadirProductoNuevo(String nombre, String descripcion, double precio) throws SQLException {
        String sql = "INSERT INTO productos (nombre, descripcion, precio) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setDouble(3, precio);
            statement.executeUpdate();
        }
    }

    public static void modificarProductoExistente(int idModificar, String nombre, String descripcion, double precio) throws SQLException {
        String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setDouble(3, precio);
            statement.setInt(4, idModificar);
            statement.executeUpdate();
        }
    }

    public static void eliminarProductoExistente(int idEliminar) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idEliminar);
            statement.executeUpdate();
        }
    }
}

