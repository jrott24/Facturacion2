package com.jrott.java.jdbc;

import com.jrott.java.jdbc.DAO.FacturasDAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.*;
import java.util.List;

public class ReporteFacturas {

    private final FacturasDAO facturasDAO;

    public ReporteFacturas(FacturasDAO facturasDAO) {
        this.facturasDAO = facturasDAO;
    }

    public void generarReporte(List<Factura> rutaArchivo) throws IOException, SQLException {
        // Obtenemos la lista de facturas
        List<Factura> facturas = facturasDAO.findAll();

        // Abrimos el archivo
        File archivo = new File(String.valueOf(rutaArchivo));
        FileOutputStream outputStream = new FileOutputStream(archivo);
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");

        // Escribimos el encabezado del archivo
        writer.write("Número de factura | Fecha | Cliente | Importe\n");

        // Escribimos las facturas
        for (Factura factura : facturas) {
            writer.write(factura.getNumeroFactura() + " | " + factura.getFechaFactura() + " | " + factura.getNombreCliente() + " | " + factura.getTotal() + "\n");
        }

        // Cerramos el archivo
        writer.close();
    }
}
