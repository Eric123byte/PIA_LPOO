
package gymposem509.modelo;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

public class ProcesadorPagos509 {
    
    public double leerDinero() {
        File carpeta = new File("Pagos");
        if (!carpeta.exists() || !carpeta.isDirectory()) {
            System.out.println("La carpeta 'Pagos' no existe o no es un directorio.");
            return 0.0;
        }

        File archivoMonto = new File(carpeta, "MontoActual");

        if (!archivoMonto.exists()) {
            System.out.println("El archivo 'MontoActual' no existe en la carpeta 'Pagos'.");
            return 0.0;
        }

        System.out.println("Leyendo archivo: " + archivoMonto.getName());

        try (FileInputStream fis = new FileInputStream(archivoMonto);
             DataInputStream dis = new DataInputStream(fis)) {

            double monto = dis.readDouble();
            System.out.println("Monto leído: " + monto);
            return monto;

        } catch (FileNotFoundException e) {
            System.out.println("\nNo se encontró el archivo MontoActual. " + e.getMessage());
            return 0.0;
        } catch (IOException e) {
            System.out.println("Error de E/S al leer el archivo. " + e.getMessage());
            return 0.0;
        }
    }
    
    public void procesarPago(double montoNuevo) {
        File carpeta = new File("Pagos");
        if (!carpeta.exists()) {
            boolean creada = carpeta.mkdirs();
            if (creada) {
                System.out.println("Carpeta 'Pagos' creada.");
            } else {
                System.out.println("No se pudo crear la carpeta 'Pagos'.");
                return;
            }
        }

        File archivoMonto = new File(carpeta, "MontoActual.dat");
        double montoActual = 0.0;

        // Si el archivo existe, leer el monto actual
        if (archivoMonto.exists()) {
            try (FileInputStream fis = new FileInputStream(archivoMonto);
                 DataInputStream dis = new DataInputStream(fis)) {
                
                montoActual = dis.readDouble();
                System.out.println("Monto actual leído: " + montoActual);
                
            } catch (FileNotFoundException e) {
                System.out.println("No se encontró el archivo MontoActual. " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Error de E/S al leer el archivo. " + e.getMessage());
            }
        } else {
            System.out.println("Archivo MontoActual no existe. Se creará uno nuevo.");
        }

        // Sumar el nuevo monto
        double nuevoTotal = montoActual + montoNuevo;
        System.out.println("Monto nuevo a agregar: " + montoNuevo);
        System.out.println("Nuevo total: " + nuevoTotal);

        // Guardar el nuevo total (sobrescribe el archivo existente)
        try (FileOutputStream fos = new FileOutputStream(archivoMonto);
             DataOutputStream dos = new DataOutputStream(fos)) {
            
            dos.writeDouble(nuevoTotal);
            System.out.println("Monto guardado exitosamente en: " + archivoMonto.getAbsolutePath());
            
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo crear/sobrescribir el archivo. " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de E/S al guardar el archivo. " + e.getMessage());
        }
    }
    
    public void guardarFactura(int id, String nombre, String apellido, String monto, String membresia, String descuento, String total, String metodo_pago) {
        String timestamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String nombreArchivo = "factura_id" + String.valueOf(id) + "_" + timestamp + ".txt";
        
        try (FileWriter fw = new FileWriter(nombreArchivo, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            out.printf("\nGymPOSEM509\nTiquet de suscripcion a membresia\n============================================\n" +
                    "ID del cliente: " + id + "\nNombre del cliente: " + nombre + " " + apellido + "\nMembresia: " + membresia +
                    "\nFecha y hora: " + LocalDateTime.now() + "\nSubtotal: " + monto + "\nDescuento: " + descuento + " porciento" + "\nTotal: " + total + "\nMetodo de pago: " + metodo_pago);
        } catch (IOException e) {
            System.out.println("Error al guardar al empleado: " + e.getMessage());
        }
        moverArchivo(nombreArchivo, "Pagos/Facturas/" + nombreArchivo, "Facturas");
    }
    
    public void moverArchivo(String inicio, String fin, String carpeta){
        Path origen = Paths.get(inicio);
        Path destino = Paths.get(fin);
        File carpetaBackup = new File("Pagos/" + carpeta);
        if (!carpetaBackup.exists()) {
            boolean creada = carpetaBackup.mkdirs();
            if (creada) {
                System.out.println("Carpeta 'Pagos' creada.");
            } else {
                System.out.println("No se pudo crear la carpeta 'Pagos'.");
            }
        }
        try {
            Files.move(origen, destino, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println("Error al mover el archivo: " + e.getMessage());
        }
    }
    
}
