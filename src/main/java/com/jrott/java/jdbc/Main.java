package com.jrott.java.jdbc;

import com.jrott.java.jdbc.DAO.FacturasDAO;
import com.jrott.java.jdbc.DAO.UsuariosDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.*;

public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/tienda";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "";
    Scanner scanner = new Scanner(System.in);

    public Main() throws SQLException {
    }

    public static void main(String[] args) throws IOException, SQLException {
        // Obtenemos la conexión a la base de datos
        Connection connection = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);

        // Declaramos un escáner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // Solicitamos al usuario su nombre de usuario y contraseña
        // Creamos una instancia de la clase AutenticarUsuarios
        System.out.println("Introduce el nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.println("Introduce la contraseña: ");
        String contraseña = scanner.nextLine();

        // Autenticamos al usuario
        AutenticarUsuarios autenticarUsuarios = new AutenticarUsuarios(connection);
        boolean autenticado = autenticarUsuarios.autenticar(connection, nombreUsuario, contraseña);

        if (autenticado) {
            // El usuario está autenticado

            // Creamos una instancia de la clase Gestion
            Gestion gestion = new Gestion(connection);

            // Mostramos el menú de opciones
            gestion.mostrarMenu();
        } else {
            // El usuario no está autenticado

            System.out.println("El nombre de usuario o la contraseña son incorrectos");
        }
        // Cerramos la conexión a la base de datos
        connection.close();
    }


    public void mostrarMenu() throws SQLException {
        System.out.println("¿Qué deseas hacer?");
        System.out.println("1. Añadir usuario");
        System.out.println("2. Modificar usuario");
        System.out.println("3. Eliminar usuario");
        System.out.println("4. Listar usuarios");

        int opcion = Integer.parseInt(System.console().readLine());

        switch (opcion) {
            case 1:
                añadirUsuario();
                break;
            case 2:
                modificarUsuario();
                break;
            case 3:
                eliminarUsuario();
                break;
            case 4:
                listarUsuarios();
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    public void añadirUsuario() throws SQLException {
        // Solicitamos al usuario el nombre de usuario y la contraseña
        System.out.println("Introduce el nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.println("Introduce la contraseña: ");
        String contraseña = scanner.nextLine();
        System.out.println("Introduce el rol: ");
        UserRole rol = UserRole.valueOf(scanner.nextLine());

        // Comprobamos si el usuario está autenticado
        boolean autenticado = AutenticarUsuarios.autenticar(connection, nombreUsuario, contraseña);

        // Si el usuario no está autenticado, no se permite añadir usuarios
        if (!autenticado) {
            System.out.println("No estás autorizado para añadir usuarios");
            return;
        }

        // Añadimos el usuario a la base de datos
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO usuarios (nombreUsuario, contraseña, rol) VALUES (?, ?, ?)");
        statement.setString(1, nombreUsuario);
        statement.setString(2, contraseña);
        statement.setString(3, rol.toString());
        statement.execute();

        System.out.println("Usuario añadido correctamente");
    }


    // Añadimos los métodos necesarios
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "password");

    // Creamos las instancias de las DAO
    UsuariosDAO usuariosDAO = new MySQLUsuariosDAO(connection);
    UserRoleDAO userRoleDAO = new MySQLUserRoleDAO(connection);
    static FacturasDAO facturasDAO = new MySQLFacturasDAO(connection);

    // Realizamos algunas operaciones
    List<Usuarios> usuarios = asList(
            new Usuarios("usuario1", "contraseña1", "cliente"),
            new Usuarios("usuario2", "contraseña2", "empleado")
    );
        usuariosDAO.saveAll(usuarios);

    List<UserRole> userRoles = asList(
            new UserRole("usuario1", "cliente"),
            new UserRole("usuario2", "empleado")
    );
        userRoleDAO.saveAll(userRoles);

    List<Factura> facturas = asList(
            new Factura(1, LocalDate.now(), "Cliente 1", asList("Producto 1", "Producto 2")),
            new Factura(2, LocalDate.now(), "Empleado 1", asList("Producto 3", "Producto 4"))
    );
        facturasDAO.saveAll(facturas);


    private static void generarReporteFacturasDia(Connection connection) throws SQLException {
        // Obtenemos las facturas del día
        List<Factura> facturasDia = facturasDAO.findByFecha(LocalDate.now());

        // Imprimimos el reporte
        System.out.println("Facturas del día:");
        facturasDia.forEach(System.out::println);
    }

    // Añadimos los métodos necesarios para la compatibilidad con las clases mencionadas

    public static void gestionarProductos(Connection connection) throws SQLException {
        // Creamos una instancia de Gestion
        Gestion gestion = new Gestion(connection);

        // Mostramos el menú de opciones de productos
        gestion.gestionarProductos();
    }

    public static void gestionarUsuarios(Connection connection) throws SQLException {
        // Creamos una instancia de Gestion
        Gestion gestion = new Gestion(connection);

        // Mostramos el menú de opciones de usuarios
        gestion.gestionarUsuarios();
    }

    public static void listarFacturas(Connection connection) throws SQLException {
        // Creamos una instancia de ReporteFacturas
        ReporteFacturas reporteFacturas = new ReporteFacturas();

        // Generamos el reporte de todas las facturas
        reporteFacturas.listarFacturas(connection);
    }

    public static void listarFacturasPorRol(UserRole role, Connection connection) throws SQLException {
        // Creamos una instancia de ReporteFacturas
        ReporteFacturas reporteFacturas = new ReporteFacturas();

        // Generamos el reporte de las facturas del rol especificado
        reporteFacturas.listarFacturasPorRol(role, connection);
    }

    public static void listarFacturasPorFecha(Date fechaInicial, Date fechaFinal, Connection connection) throws SQLException {
        // Creamos una instancia de ReporteFacturas
        ReporteFacturas reporteFacturas = new ReporteFacturas();

        // Generamos el reporte de las facturas de las fechas especificadas
        reporteFacturas.listarFacturasPorFecha(fechaInicial, fechaFinal, connection);
    }
}