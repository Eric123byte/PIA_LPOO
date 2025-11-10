
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
    private void mostrarMenuClientes() {
        cargarVista("/gymposem509/vista/menu_clientes.fxml");
    }
    
    @FXML
    private void mostrarMenuEmpleados() {
        cargarVista("/gymposem509/vista/menu_empleados.fxml");
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
        
        Button clientes = new Button();
        Button empleados = new Button();
        Button inventario = new Button();
        Button actividades = new Button();
        
        clientes.setText("Clientes");
        empleados.setText("Empleados");
        inventario.setText("Inventario");
        actividades.setText("Actividades");
        
        menu_lateral.getChildren().add(clientes);
        menu_lateral.getChildren().add(empleados);
        menu_lateral.getChildren().add(inventario);
        menu_lateral.getChildren().add(actividades);
        
        // Presiona el boton que despliega el submenú de clientes
        clientes.setOnAction(evento -> {
            mostrarMenuClientes();
        });
        
        // Presiona el boton que despliega el submenú de empleados
        empleados.setOnAction(evento -> {
            mostrarMenuEmpleados();
        });
        
    }
}
