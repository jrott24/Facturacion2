package com.jrott.java.jdbc;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.jrott.java.jdbc.Cliente.buscarClientePorId;
import static com.jrott.java.jdbc.Main.mostrarFactura;
import static com.jrott.java.jdbc.Producto.buscarProductoPorId;

public class Facturacion {
    private static final List<Factura> listaDeFacturas = new ArrayList<>();
    public static double calcularTotal(Map<Producto, Integer> productosElegidos) {
        double total = 0;
        for (Map.Entry<Producto, Integer> entry : productosElegidos.entrySet()) {
            total += entry.getKey().getPrecio() * entry.getValue();
        }
        return total;
    }

    // Método para obtener la fecha actual
    public static String obtenerFechaActual() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    // Método para obtener el usuario actual (Aquí, simplemente lo he hardcodeado como "usuarioEjemplo", pero podrías adaptarlo para tu caso)
    public static String obtenerUsuarioActual() {
        return "usuarioEjemplo"; // Este valor debe ser obtenido de la lógica de autenticación de tu aplicación
    }

    public static void crearNuevaFactura() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        List<Cliente> clientes = DatabaseUtil.obtenerClientesDeBD();
        List<Producto> productos = DatabaseUtil.obtenerProductosDeBD();

        System.out.println("Elija un cliente (ingrese el ID): ");
        for (Cliente cliente : clientes) {
            System.out.println(cliente.getId() + " - " + cliente.getNombre() + " " + cliente.getApellido());
        }
        int clienteId = Integer.parseInt(scanner.nextLine());
        Cliente clienteElegido = buscarClientePorId(clienteId, clientes);

        Map<Producto, Integer> productosElegidos = new HashMap<>();
        String opcion;
        do {
            System.out.println("Elija un producto (ingrese el ID): ");
            for (Producto producto : productos) {
                System.out.println(producto.getId() + " - " + producto.getNombre() + " - $" + producto.getPrecio());
            }
            int productoId = Integer.parseInt(scanner.nextLine());
            Producto productoElegido = buscarProductoPorId(productoId, productos);
            System.out.println("Cantidad: ");
            int cantidad = Integer.parseInt(scanner.nextLine());
            productosElegidos.put(productoElegido, cantidad);
            System.out.println("¿Desea agregar más productos? (s/n)");
            opcion = scanner.nextLine();
        } while (opcion.equalsIgnoreCase("s"));

        double total = calcularTotal(productosElegidos); // Usando el método para calcular el total
        String fechaActual = obtenerFechaActual(); // Usando el método para obtener la fecha actual
        String usuarioActual = obtenerUsuarioActual(); // Usando el método para obtener el usuario actual

        Factura factura = new Factura();
        factura.setFecha(fechaActual);
        factura.setCliente(clienteElegido);
        factura.setProductos(productosElegidos);
        factura.setTotal(total);

        DatabaseUtil.guardarFacturaEnBD(factura);

        // Mostrar la factura generada
        if (clienteElegido != null) {
            mostrarFactura(fechaActual, clienteElegido, productosElegidos, total, usuarioActual);
        }
    }

    public static void mostrarTodasLasFacturas() {
        for (Factura factura : listaDeFacturas) {
            // Aquí puedes personalizar cómo deseas mostrar la factura
            System.out.println("ID: " + factura.getId());
            System.out.println("Fecha: " + factura.getFecha());
            System.out.println("Total: " + factura.getTotal());
            System.out.println("-------------------------");
        }
    }

    public static void buscarFacturaPorId(int idBuscar) {
        for (Factura factura : listaDeFacturas) {
            if (factura.getId() == idBuscar) {
                // Aquí puedes personalizar cómo deseas mostrar la factura
                System.out.println("ID: " + factura.getId());
                System.out.println("Fecha: " + factura.getFecha());
                System.out.println("Total: " + factura.getTotal());
                return;
            }
        }
        System.out.println("Factura no encontrada");
    }

    public static void editarFactura(int idEditar) {
        Scanner scanner = new Scanner(System.in);
        for (Factura factura : listaDeFacturas) {
            if (factura.getId() == idEditar) {
                System.out.println("Ingrese la nueva fecha: ");
                String nuevaFecha = scanner.nextLine();
                factura.setFecha(nuevaFecha);
                // Aquí puedes agregar más campos para editar
                return;
            }
        }
        System.out.println("Factura no encontrada");
    }

    public static void eliminarFactura(int idEliminar) {
        Factura facturaAEliminar = null;
        for (Factura factura : listaDeFacturas) {
            if (factura.getId() == idEliminar) {
                facturaAEliminar = factura;
                break;
            }
        }
        if (facturaAEliminar != null) {
            listaDeFacturas.remove(facturaAEliminar);
            System.out.println("Factura eliminada");
        } else {
            System.out.println("Factura no encontrada");
        }
    }
}





