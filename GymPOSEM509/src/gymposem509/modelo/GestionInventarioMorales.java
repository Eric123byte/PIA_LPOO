package gymposem509.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class GestionInventarioMorales {
    
    // Cargar inventario desde archivo .dat más reciente
    public List<Inventario> cargarInventario(List<Inventario> inventario){
        File carpeta = new File("Inventario");
        if (!carpeta.exists() || !carpeta.isDirectory()) {
            System.out.println("La carpeta 'Inventario' no existe o no es un directorio.");
            return null;
        }

        File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".dat") || name.endsWith(".ser"));
        if (archivos == null || archivos.length == 0) {
            System.out.println("No hay archivos en la carpeta 'Inventario'.");
            return null;
        }

        Arrays.sort(archivos, Comparator.comparingLong(File::lastModified));

        File ultimoArchivo = archivos[archivos.length - 1];
        System.out.println("Leyendo archivo: " + ultimoArchivo.getName());

        try (FileInputStream fis = new FileInputStream(ultimoArchivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            @SuppressWarnings("unchecked")
            List<Inventario> inv = (List<Inventario>) ois.readObject();

            if (inventario != null) {
                inventario.addAll(inv);
            }
            return inv;

        } catch (FileNotFoundException e) {
            System.out.println("\nNo se encontró el archivo. " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println("Error de E/S al leer el archivo. " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Clase Inventario no encontrada: " + e.getMessage());
            return null;
        }
    }
    
    // Guardar inventario en archivo DAT
    public String serializarInventario(List<Inventario> inventario){
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreArchivo = "inventario_" + timestamp + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(inventario);
        } catch (IOException e) {
            System.out.println("Error al guardar inventario: " + e.getMessage());
            return null;
        }
        moverArchivo(nombreArchivo, "Inventario/" + nombreArchivo);
        return nombreArchivo;
    }
    
    // Mover archivo guardado a carpeta Inventario
    public void moverArchivo(String inicio, String fin){
        Path origen = Paths.get(inicio);
        Path destino = Paths.get(fin);
        File carpetaBackup = new File("Inventario");
        if (!carpetaBackup.exists()) {
            boolean creada = carpetaBackup.mkdirs();
            if (creada) {
                System.out.println("Carpeta 'Inventario' creada.");
            } else {
                System.out.println("No se pudo crear la carpeta 'Inventario'.");
            }
        }
        try {
            Files.move(origen, destino, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println("Error al mover el archivo: " + e.getMessage());
        }
    }
    
    // Agregar un nuevo producto con datos separados
    public String agregarInventario(List<Inventario> inventario,
                                    String nombre,
                                    String descripcion,
                                    String categoria,
                                    int cantidad,
                                    double precioUnitario){
        
        Inventario item = new Inventario(nombre, descripcion, categoria, cantidad, precioUnitario);
        inventario.add(item);
        return this.serializarInventario(inventario);
    }
    
    // Agregar un objeto Inventario ya creado
    public String agregarInventario(List<Inventario> inventario, Inventario item){
        inventario.add(item);
        return this.serializarInventario(inventario);
    }
    
    // Actualizar un producto
    public List<Inventario> actualizarInventario(List<Inventario> inventario,
                                                 int id,
                                                 String nombre,
                                                 String descripcion,
                                                 String categoria,
                                                 int cantidad,
                                                 double precioUnitario){
        
        Iterator<Inventario> it = inventario.iterator();
        while(it.hasNext()){
            Inventario prod = it.next();
            if(prod.getId_producto() == id){
                prod.setNombre(nombre);
                prod.setDescripcion(descripcion);
                prod.setCategoria(categoria);
                prod.setCantidad(cantidad);
                prod.setPrecioUnitario(precioUnitario);
                break;
            }
        }
        return inventario;
    }

    // Buscar por nombre
    public List<Inventario> buscarProducto(List<Inventario> inventario, String nombre){
        List<Inventario> encontrados = new ArrayList<>();
        for (Inventario i : inventario) {
            if (i.getNombre().equalsIgnoreCase(nombre)) {
                encontrados.add(i);
            }
        }
        return encontrados;
    }

    // Mostrar como string
    public String mostrarInventario(List<Inventario> inventario){
        StringBuilder sb = new StringBuilder();
        for (Inventario p : inventario) {
            sb.append(p.toString()).append("\n----------------\n");
        }
        return sb.toString();
    }
}