package com.jrott.java.jdbc;

import java.sql.Connection;
import java.util.Scanner;

public  class Gestion {

    protected Scanner scanner;

    public Gestion(Connection connection) {
        scanner = new Scanner(System.in);
    }

    public void gestionarProductos() {

    }

    public void gestionarUsuarios() {

    }
    public void generarReporteFacturasDia() {

    }

    public void mostrarMenu() {
        System.out.println("¿Qué desea hacer?");
        System.out.println("1. Gestionar productos");
        System.out.println("2. Gestionar usuarios");
        System.out.println("3. Generar reporte de facturas");
        System.out.println("4. Salir");

        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                gestionarProductos();
                break;
            case 2:
                gestionarUsuarios();
                break;
            case 3:
                generarReporteFacturasDia();
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("Opción no válida");
        }
    }



    protected Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
