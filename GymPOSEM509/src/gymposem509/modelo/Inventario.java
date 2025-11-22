package gymposem509.modelo;

import java.io.Serializable;
import java.util.List;

public class Inventario implements Serializable {
    
    // Atributos
    private static int contadorId = 1;
    private int id_producto;
    private String nombre;
    private String descripcion;
    private String categoria;
    private int cantidad;
    private double precioUnitario;

    // Constructores
    public Inventario() {
    }

    public Inventario(String nombre, String descripcion, String categoria, int cantidad, double precioUnitario) {
        this.id_producto = contadorId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;

        System.out.println("ID en Inventario.java: " + this.id_producto);
    }

    public static synchronized void incrementarContador() {
        contadorId++;
    }

    public static void eliminarContadorProductoNoUsado() {
        contadorId--;
    }

    // Inicializar contador según productos existentes
    public static void inicializarContador(List<Inventario> productos) {
        if (!productos.isEmpty()) {
            int maxId = productos.stream()
                    .mapToInt(Inventario::getId_producto)
                    .max()
                    .orElse(0);

            contadorId = maxId + 1;
        }
    }

    // Getters y Setters
    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public static int getContadorId() {
        return contadorId;
    }

    @Override
    public String toString() {
        return "ID Producto: " + id_producto +
                "\nNombre: " + nombre +
                "\nDescripción: " + descripcion +
                "\nCategoría: " + categoria +
                "\nCantidad: " + cantidad +
                "\nPrecio Unitario: " + precioUnitario;
    }
}