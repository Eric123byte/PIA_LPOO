package gymposem509.modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class ControlAccesoInventarioMorales {
    
    public void registrarEntrada(int id_producto, String nombreProducto, String categoria) {
        String timestamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String nombreArchivo = "entrada_inventario_" + timestamp + ".txt";
        Acceso ae = new Acceso(id_producto, nombreProducto, categoria);

        try (FileWriter fw = new FileWriter(nombreArchivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {

            ae.setFecha_entrada(LocalDateTime.now());
            out.printf(
                "ID del producto: " + ae.getId() +
                ", Nombre: " + ae.getNombre() +
                ", Categoría: " + ae.getApellidos() + // usando campo “apellidos” como categoría
                ", Hora de entrada: " + ae.getFecha_entrada() + "\n"
            );

        } catch (IOException e) {
            System.out.println("Error al guardar producto: " + e.getMessage());
        }

        moverArchivo(nombreArchivo, "AccesoInventario/Entrada/" + nombreArchivo, "Entrada");
    }

    public void registrarSalida(int id_producto, String nombreProducto, String categoria) {
        String timestamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String nombreArchivo = "salida_inventario_" + timestamp + ".txt";
        Acceso ae = new Acceso(id_producto, nombreProducto, categoria);

        try (FileWriter fw = new FileWriter(nombreArchivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {

            ae.setFecha_salida(LocalDateTime.now());
            out.printf(
                "ID del producto: " + ae.getId() +
                ", Nombre: " + ae.getNombre() +
                ", Categoría: " + ae.getApellidos() +
                ", Hora de salida: " + ae.getFecha_salida() + "\n"
            );

        } catch (IOException e) {
            System.out.println("Error al guardar producto: " + e.getMessage());
        }

        moverArchivo(nombreArchivo, "AccesoInventario/Salida/" + nombreArchivo, "Salida");
    }

    public void moverArchivo(String inicio, String fin, String carpeta){
        Path origen = Paths.get(inicio);
        Path destino = Paths.get(fin);
        File carpetaBackup = new File("AccesoInventario/" + carpeta);

        if (!carpetaBackup.exists()) {
            boolean creada = carpetaBackup.mkdirs();
            if (creada) {
                System.out.println("Carpeta 'AccesoInventario' creada.");
            } else {
                System.out.println("No se pudo crear la carpeta 'AccesoInventario'.");
            }
        }

        try {
            Files.move(origen, destino, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println("Error al mover el archivo: " + e.getMessage());
        }
    }
}