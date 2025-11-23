package gymposem509.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class GestionCalendarioClases {
    
    public List<ClaseGrupal> cargarClases(List<ClaseGrupal> clases) {
        File carpeta = new File("Calendario");
        if (!carpeta.exists() || !carpeta.isDirectory()) {
            System.out.println("La carpeta 'Calendario' no existe o no es un directorio.");
            return null;
        }

        File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".dat") || name.endsWith(".ser"));
        if (archivos == null || archivos.length == 0) {
            System.out.println("No hay archivos en la carpeta 'Calendario'.");
            return null;
        }

        Arrays.sort(archivos, Comparator.comparingLong(File::lastModified));

        File ultimoArchivo = archivos[archivos.length - 1];
        System.out.println("Leyendo archivo de calendario: " + ultimoArchivo.getName());

        try (FileInputStream fis = new FileInputStream(ultimoArchivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            @SuppressWarnings("unchecked")
            List<ClaseGrupal> c = (List<ClaseGrupal>) ois.readObject();

            if (clases != null) {
                clases.addAll(c);
            }
            return c;

        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de calendario. " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de calendario. " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Error de clase al leer el archivo de calendario. " + e.getMessage());
            return null;
        }
    }
    
    public String guardarClases(List<ClaseGrupal> clases) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreArchivo = "calendario_" + timestamp + ".dat";

        try (FileOutputStream fos = new FileOutputStream(nombreArchivo);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(new ArrayList<>(clases));
            System.out.println("Clases guardadas en archivo " + nombreArchivo);

        } catch (IOException e) {
            System.out.println("Error al guardar las clases: " + e.getMessage());
            return null;
        }

        File carpeta = new File("Calendario");
        if (!carpeta.exists()) {
            boolean creada = carpeta.mkdirs();
            if (!creada) {
                System.out.println("No se pudo crear la carpeta 'Calendario'.");
            }
        }

        File origen = new File(nombreArchivo);
        File destino = new File(carpeta, nombreArchivo);
        if (origen.renameTo(destino)) {
            System.out.println("Archivo movido a la carpeta 'Calendario'.");
        } else {
            System.out.println("No se pudo mover el archivo de calendario.");
        }

        return nombreArchivo;
    }
    
    public ClaseGrupal buscarClasePorId(List<ClaseGrupal> clases, int id) {
        if (clases == null) return null;
        for (ClaseGrupal c : clases) {
            if (c.getId_clase() == id) {
                return c;
            }
        }
        return null;
    }
    
    public void eliminarClase(List<ClaseGrupal> clases, int id) {
        if (clases == null) return;
        Iterator<ClaseGrupal> it = clases.iterator();
        while (it.hasNext()) {
            ClaseGrupal c = it.next();
            if (c.getId_clase() == id) {
                it.remove();
                break;
            }
        }
    }
}
