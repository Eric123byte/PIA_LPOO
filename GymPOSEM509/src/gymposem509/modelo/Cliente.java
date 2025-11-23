
package gymposem509.modelo;

import java.io.Serializable;
import java.util.List;

public class Cliente implements Serializable{
    
    // Atributos
    private static int contadorId = 1;
    private int id_cliente;
    private String nombres;
    private String apellidos;
    private String num_telefono;
    private int puntos;
    
    // Coinstructores
    public Cliente() {
    }
    
    public Cliente(String nombres, String apellidos, String num_telefono) {
        this.id_cliente = contadorId;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.num_telefono = num_telefono;
        System.out.println("ID en Cliente.Java: " + this.id_cliente);
    }
    
    public static synchronized void incrementarContador() {
        contadorId++;
//        guardarUltimoId();
    }
    
    public static void eliminarContadorClienteNoUsado(){
        contadorId--;
    }
    
    // Método para inicializar el contador basado en clientes existentes
    public static void inicializarContador(List<Cliente> clientes) {
        if (!clientes.isEmpty()) {
            int maxId = clientes.stream().mapToInt(Cliente::getId_cliente).max().orElse(0);
            contadorId = maxId + 1;
        }
    }
    
    // Getters y Setters
    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNum_telefono() {
        return num_telefono;
    }

    public void setNum_telefono(String num_telefono) {
        this.num_telefono = num_telefono;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public static int getContadorId() {
        return contadorId;
    }
    
    @Override
    public String toString(){
        return "ID: " + id_cliente +
                "\nNombre: " + nombres +
                "\nApellidos: " + apellidos +
                "\nTelefono: " + num_telefono +
                "\nPuntos: " + puntos;
    }
    
}
