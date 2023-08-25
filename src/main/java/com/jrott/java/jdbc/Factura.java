package com.jrott.java.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Factura {

    private int id;
    private int numeroFactura;
    private Date fechaFactura;
    private String nombreCliente;
    private List<Producto> productos;
    private double total;

    public Factura(int anInt, String date, String string, List<Producto> split) {
        this.productos = new ArrayList<>();
        this.id = id;
        this.numeroFactura = numeroFactura;
        this.fechaFactura = fechaFactura;
        this.nombreCliente = nombreCliente;
        this.productos = productos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public double getTotal() {
        int total = 0;
        for (Producto producto : productos) {
            total += (int) (producto.getPrecio() * producto.getCantidad());
        }
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", numeroFactura=" + numeroFactura +
                ", fechaFactura=" + fechaFactura +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", productos=" + productos +
                '}';
    }
}

