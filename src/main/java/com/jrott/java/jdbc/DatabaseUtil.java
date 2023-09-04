package com.jrott.java.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseUtil {
    private static final Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/facturacion", "root", "sasa");
        } catch (SQLException e) {
            throw new RuntimeException("Error conectando a la base de datos", e);
        }
    }

    public static List<Cliente> obtenerClientesDeBD() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setApellido(resultSet.getString("apellido"));
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    public static List<Producto> obtenerProductosDeBD() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                // Crea una nueva instancia de Producto para cada fila en el resultado
                Producto producto = new Producto();

                // Rellena los campos del objeto producto con los valores de la fila actual
                producto.setId(resultSet.getInt("id"));
                producto.setNombre(resultSet.getString("nombre"));
                producto.setPrecio(resultSet.getDouble("precio"));

                // Añade el producto a la lista de productos
                productos.add(producto);
            }
        }
        return productos;
    }

    public static void guardarFacturaEnBD(Factura factura) throws SQLException {
        String sqlFactura = "INSERT INTO facturas (id, fecha, cliente, usuario, total) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlFactura, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, factura.getFecha());
            preparedStatement.setInt(2, factura.getCliente().getId());
            preparedStatement.setString(3, factura.getUsuario());
            preparedStatement.setDouble(4, factura.getTotal());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    factura.setId(generatedKeys.getInt(1));
                }
            }
        }

        String sqlProductos = "INSERT INTO factura_productos (id_factura, id_producto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlProductos)) {
            for (Map.Entry<Producto, Integer> entry : factura.getProductos().entrySet()) {
                Producto producto = entry.getKey();
                int cantidad = entry.getValue();
                preparedStatement.setInt(1, factura.getId());
                preparedStatement.setInt(2, producto.getId());
                preparedStatement.setInt(3, cantidad);
                preparedStatement.executeUpdate();
            }
        }
    }
}

