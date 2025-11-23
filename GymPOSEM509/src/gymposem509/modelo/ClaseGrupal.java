package gymposem509.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ClaseGrupal implements Serializable {
    
    private static int contadorId = 1;
    
    private int id_clase;
    private String nombre;
    private String instructor;
    private LocalDate fecha;
    private LocalTime hora;
    private int cupoMaximo;
    
    public ClaseGrupal() {
    }

    public ClaseGrupal(String nombre, String instructor, LocalDate fecha, LocalTime hora, int cupoMaximo) {
        this.id_clase = contadorId++;
        this.nombre = nombre;
        this.instructor = instructor;
        this.fecha = fecha;
        this.hora = hora;
        this.cupoMaximo = cupoMaximo;
    }

    public static void inicializarContador(List<ClaseGrupal> clases) {
        int maxId = 0;
        if (clases != null) {
            for (ClaseGrupal c : clases) {
                if (c.getId_clase() > maxId) {
                    maxId = c.getId_clase();
                }
            }
        }
        contadorId = maxId + 1;
    }

    public int getId_clase() {
        return id_clase;
    }

    public void setId_clase(int id_clase) {
        this.id_clase = id_clase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }
    
    @Override
    public String toString() {
        return "ID Clase: " + id_clase +
                "\nNombre: " + nombre +
                "\nInstructor: " + instructor +
                "\nFecha: " + fecha +
                "\nHora: " + hora +
                "\nCupo máximo: " + cupoMaximo;
    }
}
