
package gymposem509.controlador;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Controller {
    
    @FXML
    private StackPane contenido;
    
    @FXML
    private VBox menu_lateral;

    @FXML
    private Button clientes;

    @FXML
    private Button empleados;

    @FXML
    private Button inventario;

    @FXML
    private Button actividades;

    
    @FXML
    private void mostrarMenuClientes() {
        cargarVista("/gymposem509/vista/menu_clientes.fxml");
    }
    
    @FXML
    private void mostrarMenuEmpleados() {
        cargarVista("/gymposem509/vista/menu_empleados.fxml");
    }
    
    @FXML
    private void mostrarMenuInventario() {
        cargarVista("/gymposem509/vista/menu_inventario.fxml");
    }
    
    private void cargarVista(String rutaFXML) {
        try {
            Parent vista = FXMLLoader.load(getClass().getResource(rutaFXML));

            vista.setOpacity(0);
            contenido.getChildren().setAll(vista);

            FadeTransition fade = new FadeTransition(Duration.millis(400), vista);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void initialize() {

        
        // Presiona el boton que despliega el submenú de clientes
        clientes.setOnAction(evento -> {
            mostrarMenuClientes();
        });
        
        // Presiona el boton que despliega el submenú de empleados
        empleados.setOnAction(evento -> {
            mostrarMenuEmpleados();
        });
        
        inventario.setOnAction(evento -> {
            mostrarMenuInventario();
        });
        
    }
}
