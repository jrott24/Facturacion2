package com.jrott.java.jdbc.DAO;

import com.jrott.java.jdbc.Factura;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class FacturasDAO {

    private Connection connection;

    public FacturasDAO(Connection connection) {
        this.connection = connection;
    }

    protected abstract String getTablaFacturas();

    protected abstract String getIdColumna();

    protected abstract String getFechaFacturaColumna();

    protected abstract String getNombreClienteColumna();

    protected abstract String getProductosColumna();

    public void save(Factura factura) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO " + getTablaFacturas() + " (" + getIdColumna() + ", " + getFechaFacturaColumna() + ", " + getNombreClienteColumna() + ", " + getProductosColumna() + ") VALUES (?, ?, ?, ?)");
        statement.setInt(1, factura.getId());
        statement.setDate(2, new Date(factura.getFechaFactura().getTime()));
        statement.setString(3, factura.getNombreCliente());
        statement.setString(4, factura.getProductos().toString());
        statement.executeUpdate();
    }

    public Factura findById(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + getTablaFacturas() + " WHERE " + getIdColumna() + " = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return new Factura(
                    resultSet.getInt(getIdColumna()),
                    resultSet.getDate(getFechaFacturaColumna()),
                    resultSet.getString(getNombreClienteColumna()),
                    resultSet.getString(getProductosColumna()).replaceAll("[\\[\\]]", "").split(", ")
            );
        } else {
            return null;
        }
    }

    public List<Factura> findAll() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + getTablaFacturas());
        ResultSet resultSet = statement.executeQuery();
        List<Factura> facturas = new ArrayList<>();
        while (resultSet.next()) {
            facturas.add(new Factura(
                    resultSet.getInt(getIdColumna()),
                    resultSet.getDate(getFechaFacturaColumna()),
                    resultSet.getString(getNombreClienteColumna()),
                    resultSet.getString(getProductosColumna()).replaceAll("[\\[\\]]", "").split(", ")
            ));
        }
        return facturas;
    }

    public void update(Factura factura) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE " + getTablaFacturas() + " SET " + getFechaFacturaColumna() + " = ?, " + getNombreClienteColumna() + " = ?, " + getProductosColumna() + " = ? WHERE " + getIdColumna() + " = ?");
        statement.setDate(1, new Date(factura.getFechaFactura().getTime()));
        statement.setString(2, factura.getNombreCliente());
        statement.setString(3, factura.getProductos().toString());
        statement.setInt(4, factura.getId());
        statement.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM " + getTablaFacturas() + " WHERE " + getIdColumna() + " = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }


    public abstract List<Factura> findByFecha(LocalDate now);
}
