package com.jrott.java.jdbc;

import java.sql.Connection;
import java.util.Map;

public class Factura {
    private static Connection connection;
    private int id;
    private String fecha;
    private Cliente cliente;
    private Map<Producto, Integer> productos;
    private double total;
    private String usuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Map<Producto, Integer> getProductos() {
        return productos;
    }

    public void setProductos(Map<Producto, Integer> productos) {
        this.productos = productos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", fecha='" + fecha + '\'' +
                ", cliente='" + cliente + '\'' +
                ", usuario='" + usuario + '\'' +
                ", total=" + total +
                '}';
    }

}


