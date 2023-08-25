package com.jrott.java.jdbc;

import java.util.Objects;

public class Producto {

    private int id;
    private int codigoProducto;
    private int cantidad;
    private float precio;

    public Producto() {
    }

    public Producto(int codigoProducto, int cantidad) {
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Producto producto = (Producto) o;
        return codigoProducto == producto.codigoProducto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoProducto);
    }

    @Override
    public String toString() {
        return "Producto{" +
                "codigoProducto=" + codigoProducto +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                '}';
    }

    public float getImporte() {
        return precio * cantidad;
    }
}
