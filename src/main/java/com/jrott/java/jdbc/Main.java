package com.jrott.java.jdbc;

import com.jrott.java.jdbc.DAO.FacturasDAO;
import com.jrott.java.jdbc.DAO.ProductosDAO;
import com.jrott.java.jdbc.DAO.UsuariosDAO;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;
import static com.jrott.java.jdbc.DAO.ProductosDAO.*;

public class Main {

    public static int idUsuario;

    public static void main(String[] args) throws Exception {
        DriverManager.getConnection("jdbc:mysql://localhost:3306/facturacion", "root", "sasa");
        Scanner scanner = new Scanner(System.in);
        String username;
        String password;
        String tipoAcceso;

        do {
            System.out.println("Ingrese su nombre de usuario:");
            username = scanner.nextLine();

            System.out.println("Ingrese su contraseña:");
            password = scanner.nextLine();

            try {
                tipoAcceso = LoginValidation.validateUser(username, password);

                if (tipoAcceso != null) {
                    System.out.println("Login exitoso. Tipo de acceso: " + tipoAcceso);
                    showMainMenu(scanner, tipoAcceso); // Llamamos al método principal aquí si el login es exitoso
                    break; // Salir del bucle
                } else {
                    System.out.println("Login incorrecto. Por favor, inténtelo de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Se produjo un error al intentar el inicio de sesión. Mensaje de error: " + e.getMessage());
            }
        } while (true);
    }


        public static void showMainMenu(Scanner scanner, String tipoAcceso) {
        do {
            System.out.println("Menú principal");
            System.out.println("1. Usuarios");
            System.out.println("2. Productos");
            System.out.println("3. Facturas");
            System.out.println("4. Salir");
            String option = scanner.nextLine();
            try {
                switch (option) {
                    case "1" -> {
                        if ("admin".equals(tipoAcceso)) {
                            mostrarMenuAdminUsuarios();
                        } else {
                            mostrarMenuCajeroUsuarios();
                        }
                    }
                    case "2" -> {
                        if ("admin".equals(tipoAcceso)) {
                            mostrarMenuProductos();
                        } else {
                            System.out.println("Acceso denegado");
                        }
                    }
                    case "3" -> mostrarMenuFacturas();
                    case "4" -> System.exit(0);
                    default -> System.out.println("Opción no válida");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public static void mostrarMenuCajeroUsuarios() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String opcion;
        UsuariosDAO usuariosDAO = new UsuariosDAO();
        do {
            System.out.println("Menú de usuarios (Cajero)");
            System.out.println("1. Listar usuarios");
            System.out.println("2. Buscar usuario por id");
            System.out.println("3. Salir");

            opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    UsuariosDAO.listarUsuarios();
                    break;
                case "2":
                    System.out.println("Introduce el ID del usuario:");
                    int idUsuario = Integer.parseInt(scanner.nextLine());
                    UsuariosDAO.buscarUsuarioPorId();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        } while (true);
    }

    public static void mostrarMenuCajeroProductos() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String opcion;
        ProductosDAO productosDAO = new ProductosDAO();
        // Mostramos el menú de productos para cajeros
        do {
            System.out.println("Menú de productos (Cajero)");
            System.out.println("1. Listar productos");
            System.out.println("2. Buscar producto por id");
            System.out.println("3. Salir");
            opcion = scanner.nextLine();
            switch (opcion) {
                case "1" -> ProductosDAO.listarProductos();
                case "2" -> {
                    System.out.println("Introduce el ID del producto:");
                    int idProducto = Integer.parseInt(scanner.nextLine());
                    ProductosDAO.buscarProductoPorId(idProducto);
                }
                case "3" -> {
                    return;
                }
                default -> System.out.println("Opción no válida");
            }
        } while (true);
    }

    public static void mostrarMenuAdminUsuarios() {
        Scanner scanner = new Scanner(System.in);
        String opcion;
        try {
            do {
                System.out.println("Menú de administración de usuarios");
                System.out.println("1. Listar usuarios");
                System.out.println("2. Buscar usuario por id");
                System.out.println("3. Añadir usuario");
                System.out.println("4. Modificar usuario");
                System.out.println("5. Eliminar usuario");
                System.out.println("6. Salir");
                opcion = scanner.nextLine();
                switch (opcion) {
                    case "1":
                        UsuariosDAO.listarUsuarios();
                        break;
                    case "2":
                        System.out.print("Introduce el id del usuario a buscar: ");
                        int idBuscar = Integer.parseInt(scanner.nextLine());
                        UsuariosDAO.buscarUsuarioPorId();
                        break;
                    case "3":
                        // Obtener los datos del usuario a añadir y añadirlo
                        System.out.println("Introduce los datos del usuario a añadir:");
                        String nombre = getInput("Nombre: ", scanner);
                        String apellidos = getInput("Apellidos: ", scanner);
                        String nombre_usuario = getInput("Usuario: ", scanner);
                        String contrasena = getInput("Contraseña: ", scanner);
                        String tipoAcceso = getInput("Tipo de acceso (admin o cajero): ", scanner);
                        UsuariosDAO.añadirUsuario(nombre, apellidos, nombre_usuario, contrasena, tipoAcceso);
                        break;
                    case "4":
                        // Similarmente para modificar un usuario
                        int idModificar = Integer.parseInt(getInput("Introduce el id del usuario a modificar: ", scanner));
                        nombre = getInput("Nombre (vacío para no modificar): ", scanner);
                        apellidos = getInput("Apellidos (vacío para no modificar): ", scanner);
                        nombre_usuario = getInput("Usuario (vacío para no modificar): ", scanner);
                        contrasena = getInput("Contraseña (vacío para no modificar): ", scanner);
                        tipoAcceso = getInput("Tipo de acceso (vacío para no modificar): ", scanner);
                        UsuariosDAO.modificarUsuario(idModificar, nombre, apellidos, nombre_usuario, contrasena, tipoAcceso);
                        break;
                    case "5":
                        int idEliminar = Integer.parseInt(getInput("Introduce el id del usuario a eliminar: ", scanner));
                        UsuariosDAO.eliminarUsuario(idEliminar);
                        break;
                    case "6":
                        return;
                    default:
                        System.out.println("Opción no válida");
                }
            } while (true);
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número válido");
        }
    }

    private static String getInput(String prompt, Scanner scanner) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static void mostrarMenuProductos() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String opcion;
        do {
            System.out.println("\nMenú de administración de productos");
            System.out.println("1. Listar productos");
            System.out.println("2. Buscar producto por ID");
            System.out.println("3. Añadir producto");
            System.out.println("4. Modificar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextLine();
            switch (opcion) {
                case "1" -> ProductosDAO.listarProductos(); // Asumiendo que este método está implementado
                case "2" -> {
                    System.out.print("Introduce el ID del producto a buscar: ");
                    int idBuscar = Integer.parseInt(scanner.nextLine());
                    ProductosDAO.buscarProductoPorId(idBuscar); // Asumiendo que este método está implementado
                }
                case "3" -> {
                    System.out.print("Introduce los datos del producto a añadir (nombre, descripción, precio): ");
                    String nombre = scanner.nextLine();
                    String descripcion = scanner.nextLine();
                    double precio = Double.parseDouble(scanner.nextLine());
                    añadirProductoNuevo(nombre, descripcion, precio);
                }
                case "4" -> {
                    System.out.print("Introduce el ID del producto a modificar: ");
                    int idModificar = Integer.parseInt(scanner.nextLine());
                    System.out.print("Introduce los nuevos datos del producto (nombre, descripción, precio): ");
                    String nuevoNombre = scanner.nextLine();
                    String nuevaDescripcion = scanner.nextLine();
                    double nuevoPrecio = Double.parseDouble(scanner.nextLine());
                    modificarProductoExistente(idModificar, nuevoNombre, nuevaDescripcion, nuevoPrecio);
                }
                case "5" -> {
                    System.out.print("Introduce el ID del producto a eliminar: ");
                    int idEliminar = Integer.parseInt(scanner.nextLine());
                    eliminarProductoExistente(idEliminar);
                }
                case "6" -> {
                    return;
                }
                default -> System.out.println("Opción no válida");
            }
        } while (true);
    }

    public static void mostrarMenuFacturas() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String opcion;
        do {
            System.out.println("\nMenú de administración de facturas");
            System.out.println("1. Listar facturas");
            System.out.println("2. Buscar factura por id");
            System.out.println("3. Crear factura");
            System.out.println("4. Editar factura");
            System.out.println("5. Eliminar factura");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextLine();
            switch (opcion) {
                case "1" -> FacturasDAO.listarFacturas();
                case "2" -> {
                    System.out.print("Introduce el id de la factura a buscar: ");
                    int idBuscar = Integer.parseInt(scanner.nextLine());
                    FacturasDAO.buscarFacturaPorId(idBuscar);
                }
                case "3" -> {
                    System.out.print("Introduce la fecha (yyyy-mm-dd): ");
                    String fecha = scanner.nextLine();
                    int idCliente = -1;
                    do {
                        System.out.print("Introduce el id del cliente: ");
                        try {
                            idCliente = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Por favor, introduce un número válido para el id del cliente.");
                        }
                    } while (true);
                    int idUsuario = -1;
                    do {
                        System.out.print("Introduce el id del usuario: ");
                        try {
                            idUsuario = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Por favor, introduce un número válido para el id del usuario.");
                        }
                    } while (true);
                    double total = -1;
                    do {
                        System.out.print("Introduce el total de la factura: ");
                        try {
                            total = Double.parseDouble(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Por favor, introduce un número válido para el total de la factura.");
                        }
                    } while (true);
                    FacturasDAO.crearFactura(fecha, idCliente, idUsuario, total);
                }
                case "4" -> {
                    System.out.print("Introduce el id de la factura a editar: ");
                    int idEditar = Integer.parseInt(scanner.nextLine());
                    System.out.print("Introduce la nueva fecha (yyyy-mm-dd): ");
                    String nuevaFecha = scanner.nextLine();
                    System.out.print("Introduce el nuevo id del cliente: ");
                    int nuevoIdCliente = Integer.parseInt(scanner.nextLine());
                    System.out.print("Introduce el nuevo id del usuario: ");
                    int nuevoIdUsuario = Integer.parseInt(scanner.nextLine());
                    System.out.print("Introduce el nuevo total de la factura: ");
                    double nuevoTotal = Double.parseDouble(scanner.nextLine());
                    FacturasDAO.modificarFactura(idEditar, nuevoIdUsuario, nuevoIdCliente, java.sql.Date.valueOf(nuevaFecha), nuevoTotal);
                }
                case "5" -> {
                    System.out.print("Introduce el id de la factura a eliminar: ");
                    int idEliminar = Integer.parseInt(scanner.nextLine());
                    FacturasDAO.eliminarFactura(idEliminar);
                }
                case "6" -> {
                    return;
                }
                default -> System.out.println("Opción no válida");
            }
        } while (true);
    }

    public static void mostrarFactura(String fecha, Cliente cliente, Map<Producto, Integer> productos, double total, String usuario) {
        System.out.println("------- Factura -------");
        System.out.println("Fecha: " + fecha);
        System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellido());
        System.out.println("Usuario: " + usuario);
        System.out.println("\nProductos:");
        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            System.out.println(entry.getKey().getNombre() + " - " + entry.getValue() + " unidades - $" + entry.getKey().getPrecio());
        }
        System.out.println("\nTotal: $" + total);
        System.out.println("----------------------");
    }

    public static class MainApp {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            String opcion;
            do {
                System.out.println("\n---- Menú principal ----");
                System.out.println("1. Crear nueva factura");
                System.out.println("2. Mostrar todas las facturas");
                System.out.println("3. Buscar factura por ID");
                System.out.println("4. Editar factura");
                System.out.println("5. Eliminar factura");
                System.out.println("6. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextLine();
                switch (opcion) {
                    case "1" -> {
                        try {
                            Facturacion.crearNuevaFactura();
                        } catch (SQLException e) {
                            System.out.println("Error al crear la factura: " + e.getMessage());
                        }
                    }
                    case "2" ->
                        // Supongo que hay un método que haga esto en Facturacion
                            Facturacion.mostrarTodasLasFacturas();
                    case "3" -> {
                        System.out.print("Introduce el ID de la factura: ");
                        int idBuscar = Integer.parseInt(scanner.nextLine());
                        Facturacion.buscarFacturaPorId(idBuscar);
                    }
                    case "4" -> {
                        // Supongo que hay un método que haga esto en Facturacion
                        System.out.print("Introduce el ID de la factura a editar: ");
                        int idEditar = Integer.parseInt(scanner.nextLine());
                        Facturacion.editarFactura(idEditar);
                    }
                    case "5" -> {
                        // Supongo que hay un método que haga esto en Facturacion
                        System.out.print("Introduce el ID de la factura a eliminar: ");
                        int idEliminar = Integer.parseInt(scanner.nextLine());
                        Facturacion.eliminarFactura(idEliminar);
                    }
                    case "6" -> {
                        System.out.println("Saliendo...");
                        return;
                    }
                    default -> System.out.println("Opción no válida");
                }
            } while (true);
        }
    }
}





