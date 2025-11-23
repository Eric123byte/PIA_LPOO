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
import java.util.List;

public class ControlVentasInventarioMorales {
    
    public void registrarVenta(int id_cliente, String nombreCliente, List<Inventario> productosVendidos) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreArchivo = "venta_" + timestamp + ".txt";

        try (FileWriter fw = new FileWriter(nombreArchivo, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            LocalDateTime ahora = LocalDateTime.now();
            double total = 0;

            out.printf("VENTA | Dia: %s | Cliente: %s | FechaHora: %s\n\n",
                    new SimpleDateFormat("dd/MM/yyyy").format(new Date()),
                    nombreCliente,
                    ahora);

            for (Inventario p : productosVendidos) {
                out.printf("ID: %d | Nombre: %s | Categoría: %s | Cantidad vendida: %d | Precio: %.2f\n",
                        p.getId_producto(),
                        p.getNombre(),
                        p.getCategoria(),
                        p.getCantidad(),
                        p.getPrecioUnitario());
                total += p.getCantidad() * p.getPrecioUnitario();
            }

            out.printf("\nTotal: %.2f\n", total);

        } catch (IOException e) {
            System.out.println("Error al guardar la venta: " + e.getMessage());
        }

        moverArchivo(nombreArchivo, "Ventas/" + nombreArchivo, "Ventas");
    }

    private void moverArchivo(String inicio, String fin, String carpeta){
        Path origen = Paths.get(inicio);
        Path destino = Paths.get(fin);
        File carpetaBackup = new File(carpeta);

        if (!carpetaBackup.exists()) {
            boolean creada = carpetaBackup.mkdirs();
            if (creada) {
                System.out.println("Carpeta '" + carpeta + "' creada.");
            } else {
                System.out.println("No se pudo crear la carpeta '" + carpeta + "'.");
            }
        }

        try {
            Files.move(origen, destino, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println("Error al mover el archivo: " + e.getMessage());
        }
    }
}