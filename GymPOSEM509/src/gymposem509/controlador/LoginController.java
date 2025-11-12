
package gymposem509.controlador;

import MainApp.Main;
import gymposem509.modelo.ControlAccesoEmpleadosMorales;
import gymposem509.modelo.Empleado;
import gymposem509.modelo.GestionEmpleadosMorales;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginController {
    
    @FXML
    private TextField idTextField;

    @FXML
    private Button logginButton;

    @FXML
    private  Button registrarButton;

    @FXML
    private TextField contrasenaTextField;

    
    public void initialize() {

        List<Empleado> emp = new ArrayList<>();
        GestionEmpleadosMorales empleados = new GestionEmpleadosMorales();
        
        // Empleado de prueba
        Empleado emp1 = new Empleado("Eric", "Morales", 19, "direccion", 0, "psswrd", "Adnibistrador", 100000.0, true);
//        Empleado emp2 = new Empleado("Juan", "Perez", 21, "direccion", 1, "12345", "chalan", 100.0, false);
         empleados.agregarEmpleado(emp, emp1);
//        empleados.agregarEmpleado(emp, emp2);

        // Apenas se ejecute el programa leerá a todos los empleados que hay registrados
        empleados.cargarEmpleado(emp);

        // Acciones del boton "Aceptar" del menú de inicio de sesion
        logginButton.setOnAction(event -> {
            
            ControlAccesoEmpleadosMorales controlAcceso = new ControlAccesoEmpleadosMorales();
            String e_id = idTextField.getText();
            String e_pswrd = contrasenaTextField.getText();
            boolean find = false;
            
            for(Empleado e: emp){
                if(e_id.equals(String.valueOf(e.getId())) && e.isAcceso()){
                    find = true;
                    if(e_pswrd.equals(e.getPasswrd())){
                        System.out.println("Sesion iniciada");
                        controlAcceso.registrarEntrada(Integer.parseInt(e_id), e.getNombre(), e.getApellido());
                        Main.cambiarEscena("guifxml.fxml", "Aplicación Principal");
                    } else {
                        System.out.println("Acceso denegado");
                    }
                    break;
                }
            }
            if(!find)
                System.out.println("No se encontro el ID o no tiene acceso");
            empleados.mostrarEmpleados(emp);
        });

        
    }
}
