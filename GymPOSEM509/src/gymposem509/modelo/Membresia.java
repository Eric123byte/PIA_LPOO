
package gymposem509.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Membresia implements Serializable{
    
    private int id_cliente;
    private String tipo_membresia;
    private LocalDate inicio;
    private LocalDate fin;

    public Membresia() {
    }

    public Membresia(int id_cliente, String tipo_membresia, LocalDate inicio, LocalDate fin) {
        this.id_cliente = id_cliente;
        this.tipo_membresia = tipo_membresia;
        this.inicio = inicio;
        this.fin = fin;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getTipo_membresia() {
        return tipo_membresia;
    }

    public void setTipo_membresia(String tipo_membresia) {
        this.tipo_membresia = tipo_membresia;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }
    
    @Override
    public String toString(){
        return "\nID: " + id_cliente +
                "\nTipo de membresia: " + tipo_membresia + 
                "\nFecha de inicio: " + inicio +
                "\nFecha de finalizacion: " + fin;
    }
    
}
