package com.jrott.java.jdbc.DAO;

import com.jrott.java.jdbc.Cliente;
import com.jrott.java.jdbc.Factura;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class FacturasDAO {

    private static Connection connection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/facturacion";
        String user = "root";
        String password = "sasa";
        return DriverManager.getConnection(url, user, password);
    }

    public static List<Factura> listarFacturas() {
        String sql = "SELECT * FROM facturas";

        try (Connection connection = connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            List<Factura> facturas = new ArrayList<>();

            while (resultSet.next()) {
                Factura factura = new Factura();
                factura.setId(resultSet.getInt("id"));
                factura.setFecha(resultSet.getString("fecha"));
                factura.setCliente((Cliente) resultSet.getObject("cliente"));
                factura.setUsuario(resultSet.getString("usuario"));
                factura.setTotal(resultSet.getDouble("total"));

                facturas.add(factura);
            }


            for (Factura factura : facturas) {
                System.out.println(factura);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void buscarFacturaPorId(int idFactura) throws SQLException {
        String sql = "SELECT * FROM facturas WHERE id = ?";
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idFactura);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                }
            }
        }
    }

    public static void crearFactura(String fecha, int idCliente, int idUsuario, double total) throws SQLException {
        String sql = "INSERT INTO facturas (fecha, cliente, usuario, total) VALUES (?, ?, ?, ?)";
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, fecha);
            statement.setInt(2, idCliente);
            statement.setInt(3, idUsuario);
            statement.setDouble(4, total);
            statement.executeUpdate();
        }
    }

    public static void modificarFactura(int idFactura, int idUsuario, int idCliente, Date fecha, double total) throws SQLException {
        String sql = "UPDATE facturas SET fecha = ?, cliente_id = ?, usuario = ?, total = ? WHERE id = ?";
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, String.valueOf(fecha));
            statement.setInt(2, idCliente);
            statement.setInt(3, idUsuario);
            statement.setDouble(4, total);
            statement.setInt(5, idFactura);
            statement.executeUpdate();
        }
    }

    public static void eliminarFactura(int idFactura) throws SQLException {
        String sql = "DELETE FROM facturas WHERE id = ?";
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idFactura);
            statement.executeUpdate();
        }
    }
}
