package com.jrott.java.jdbc;

import com.jrott.java.jdbc.DAO.FacturasDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionFactura {

    private final FacturasDAO facturasDAO;
    private final Scanner scanner;

    public GestionFactura(FacturasDAO facturasDAO, Scanner scanner) {
        this.facturasDAO = facturasDAO;
        this.scanner = scanner;
    }

    public void gestionarFactura() throws SQLException {
        // Solicitamos los datos de la factura
        System.out.println("Introduce el número de factura: ");
        int numeroFactura = scanner.nextInt();
        System.out.println("Introduce la fecha de la factura: ");
        String fechaFactura = scanner.next();
        System.out.println("Introduce el nombre del cliente: ");
        String nombreCliente = scanner.nextLine();
        System.out.println("Introduce la lista de productos de la factura: ");
        List<Producto> productos = new ArrayList<>();
        try {
            while (true) {
                System.out.println("Introduce el código del producto: ");
                int codigoProducto = scanner.nextInt();
                System.out.println("Introduce la cantidad del producto: ");
                int cantidad = scanner.nextInt();
                Producto producto = new Producto(codigoProducto, cantidad);
                productos.add(producto);
                System.out.println("¿Desea añadir otro producto? (1 = Sí, 2 = No)");
                int opcion = scanner.nextInt();
                if (opcion != 1) {
                    break;
                }
            }

            // Creamos la factura
            Factura factura = new Factura(numeroFactura, fechaFactura, nombreCliente, productos);

            // Insertamos la factura en la base de datos
            facturasDAO.save(factura);

            // Calculamos el total de la factura
            int total = 0;
            for (Producto producto : productos) {
                total += (int) (producto.getPrecio() * producto.getCantidad());
            }

            // Imprimimos el total de la factura
            System.out.println("El total de la factura es de " + total + " euros");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
