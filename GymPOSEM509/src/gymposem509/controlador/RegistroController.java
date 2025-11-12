package gymposem509.controlador;

import gymposem509.modelo.Empleado;
import gymposem509.modelo.GestionEmpleadosMorales;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegistroController{

    @FXML
    private TextField nombre;

    @FXML
    private TextField apellidos;

    @FXML
    private TextField edad;

    @FXML
    private TextField direccion;

    @FXML
    private TextField id;

    @FXML
    private ChoiceBox<String> puesto;

    @FXML
    private TextField salario;

    @FXML
    private  TextField contrasena;

    private List<Empleado> emp = new ArrayList<>();
    private GestionEmpleadosMorales empleados = new GestionEmpleadosMorales();

    public void setEmp(List<Empleado> emp) {
        this.emp = emp;
    }

    public void setEmpleados(GestionEmpleadosMorales empleados) {
        this.empleados = empleados;
    }

    public void initialize(){
        System.out.println("RegistroController inicializado.");

        // ¡Aquí está la magia!
        // Añadimos las opciones de puestos al ChoiceBox
        puesto.getItems().addAll("Recepcionista", "Entrenador", "Gerente", "Limpieza");

        // (Opcional: poner un valor por defecto)
        puesto.setValue("Recepcionista");
    }

    public void regresarScreen(ActionEvent e) throws IOException {
        cambiarDeEscena(e,"login.fxml","GymPOSEM509");
    }

    public void cambiarDeEscena(ActionEvent event, String fxmlFile, String newTitle) throws IOException {
        // 1. Carga el nuevo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/" + fxmlFile));
        Parent root = loader.load();

        // 2. (Opcional: Si necesitas pasar datos al nuevo controlador)
        // Controller c = loader.getController();
        // c.inicializar("Dato a pasar");

        // 3. Obtiene la ventana (Stage) actual
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // 4. Pone la nueva escena en la ventana
        Scene scene = new Scene(root);

        String css = Objects.requireNonNull(this.getClass().getResource("/Recursos/style/login.css")).toExternalForm();
        scene.getStylesheets().add(css);


        stage.setScene(scene);
        stage.setTitle(newTitle);
        stage.centerOnScreen();
        stage.show();
    }

    // Registrar usuario
    public void registrarUsuario(ActionEvent event){
        int campo_id = Integer.parseInt(id.getText());
        boolean found = false;
        for(Empleado e: emp){
            if(campo_id == e.getId()){
                found = true;
                Label error = new Label();
                System.out.println("ID ya ingresado.\nFavor de ingresar uno nuevo.");
                break;
            }
        }

        if(!found){
            String campo_nombre = nombre.getText();
            String campo_apellido = apellidos.getText();
            String campo_edad = edad.getText();
            String campo_direccion = direccion.getText();
            String campo_puesto = puesto.getValue();
            double campo_salario = Double.parseDouble(salario.getText());
            String campo_password = contrasena.getText();
            boolean acceso_actual;


            Empleado e1 = new Empleado(campo_nombre, campo_apellido, Integer.parseInt(campo_edad), campo_direccion, campo_id, campo_password, campo_puesto, campo_salario, true);
            String x = empleados.agregarEmpleado(emp, e1);

        }
    }

    public void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null); // (Se ve más limpio sin cabecera)
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }


}
