package gymposem509.controlador;

import gymposem509.modelo.ControlAccesoEmpleadosMorales;
import gymposem509.modelo.Empleado;
import gymposem509.modelo.GestionEmpleadosMorales;
import java.io.IOException; // ¡Importante!
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader; // ¡Importante!
import javafx.fxml.Initializable;
import javafx.scene.Node; // ¡Importante!
import javafx.scene.Parent; // ¡Importante!
import javafx.scene.Scene; // ¡Importante!
import javafx.scene.control.*; // ¡Importante!
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage; // ¡Importante!


public class LoginController implements Initializable {

    // --- Tus FXML ---
    @FXML
    private TextField idTextField;

    @FXML
    private TextField contrasenaTextField;

    @FXML
    private Button logginButton;
    @FXML
    private  Button registrarButton;
    @FXML
    private Label lbError; // (¡Añadí este @FXML, asumo que quieres usar la etiqueta de error!)

    // --- Tu lógica de negocio ---
    private List<Empleado> emp = new ArrayList<>();
    private GestionEmpleadosMorales empleados = new GestionEmpleadosMorales();
    private ControlAccesoEmpleadosMorales controlAcceso = new ControlAccesoEmpleadosMorales();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Empleado de prueba
        Empleado emp1 = new Empleado("Eric", "Morales", 19, "direccion", 0, "psswrd", "Adnibistrador", 100000.0, true);
        //empleados.agregarEmpleado(emp, emp1);
        empleados.cargarEmpleado(emp); // Carga empleados del .dat
    }

    /**
     * Este método se llama cuando presionas "Sign In"
     */

    public void btnIngresar(ActionEvent event) {

        String e_id = idTextField.getText();
        String e_pswrd = contrasenaTextField.getText(); // ¡Leemos del PasswordField!
        boolean find = false;

        for(Empleado e: emp){
            System.out.println(e.getNombre());
            if(e_id.equals(String.valueOf(e.getId())) && e.isAcceso()){
                find = true;
                if(e_pswrd.equals(e.getPasswrd())){
                    // --- ¡ÉXITO! ---
                    System.out.println("Sesion iniciada");
                    controlAcceso.registrarEntrada(Integer.parseInt(e_id), e.getNombre(), e.getApellido());

                    // ¡1. Muestra la alerta de ÉXITO!
                    mostrarAlerta("Inicio de Sesión", "¡Inicio de sesión correcto! Bienvenido.", Alert.AlertType.INFORMATION);

                    // ¡2. Cambia la escena!
                    try {
                        cambiarDeEscena(event, "guifxml.fxml", "GymPOS - Dashboard"); //
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                } else {
                    // --- ¡ERROR DE CONTRASEÑA! ---
                    System.out.println("Acceso denegado");
                    lbError.setText("Contraseña incorrecta.");
                    // ¡Muestra la alerta de ERROR!
                    mostrarAlerta("Error", "Datos inválidos. Contraseña incorrecta.", Alert.AlertType.ERROR);
                }
                break; // Salimos del bucle
            }
        }

        if(!find) {
            // --- ¡ERROR DE USUARIO! ---
            System.out.println("No se encontro el ID o no tiene acceso");
            // ¡Muestra la alerta de ERROR!
            mostrarAlerta("Error", "Datos inválidos. ID no encontrado o sin acceso.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Este método se llama cuando presionas "Registrar"
     */

    public void btnRegistrar(ActionEvent event) {

        // --- NUEVA LÓGICA (Como pediste) ---
        System.out.println("Botón 'Registrar' presionado. Cargando escena de registro...");
        
        // ¡Oki! Aquí va la lógica para cargar tu FXML de registro.
        // ¡Solo quita los comentarios cuando tengas el archivo listo!
        
        try {
            cambiarDeEscena(event, "registroView.fxml", "Registro de Empleado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Un método "helper" (ayudante) para cambiar de escena
     */
    public void cambiarDeEscena(ActionEvent event, String fxmlFile, String newTitle) throws IOException {
        // 1. Carga el nuevo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/" + fxmlFile));
        Parent root = loader.load();

        if(fxmlFile.equals("registroView.fxml")){
            RegistroController c = loader.getController();
            c.setEmp(emp);
            c.setEmpleados(empleados);
        }

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

    /**
     * Un método "helper" (ayudante) para mostrar alertas
     */
    public void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null); // (Se ve más limpio sin cabecera)
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}