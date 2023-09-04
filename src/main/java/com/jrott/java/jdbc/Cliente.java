package com.jrott.java.jdbc;

import java.util.List;

public class Cliente {
    private int id;
    private String nombre;
    private String apellido;

    public static Cliente buscarClientePorId(int id, List<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null; // Devuelve null si no se encuentra el cliente
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}