package com.jrott.java.jdbc;

import java.time.LocalDate;

public class DetalleFactura {

    private int id;
    private int numeroFactura;
    private LocalDate fechaFactura;
    private int idProducto;
    private int cantidad;
    private double precio;
    private Producto productos;


    @Override
    public String toString() {
        return "DetalleFactura{" +
                "id=" + id +
                ", numeroFactura=" + numeroFactura +
                ", fechaFactura=" + fechaFactura +
                ", idProducto=" + idProducto +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                ", producto=" + productos +
                '}';
    }
}
