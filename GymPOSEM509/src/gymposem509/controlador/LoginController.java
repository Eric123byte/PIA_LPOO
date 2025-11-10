
package gymposem509.controlador;

import MainApp.Main;
import gymposem509.modelo.ControlAccesoEmpleadosMorales;
import gymposem509.modelo.Empleado;
import gymposem509.modelo.GestionEmpleadosMorales;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginController {
    
    @FXML
    private VBox datos_sesion;
    
    @FXML
    private HBox barra_boton_registrar;
    
    @FXML
    private Label label;
    
    @FXML
    private Button botonRegistrar;
    
    @FXML
    private TextField sesion_id;
    
    @FXML
    private TextField sesion_password;
    
    public void initialize() {
        
        List<Empleado> emp = new ArrayList<>();
        GestionEmpleadosMorales empleados = new GestionEmpleadosMorales();
        
        // Empleado de prueba
//        Empleado emp1 = new Empleado("Eric", "Morales", 19, "direccion", 0, "psswrd", "Adnibistrador", 100000.0, true);
//        Empleado emp2 = new Empleado("Juan", "Perez", 21, "direccion", 1, "12345", "chalan", 100.0, false);
//        empleados.agregarEmpleado(emp, emp1);
//        empleados.agregarEmpleado(emp, emp2);

        // Apenas se ejecute el programa leerá a todos los empleados que hay registrados
        empleados.cargarEmpleado(emp);
        
        label.setText("Inicio de sesión");
        
        Button botonAceptar = new Button();
        Button botonRegistrar = new Button();
        TextField sesion_id = new TextField();
        TextField sesion_password = new TextField();
        
        
        botonAceptar.setText("Aceptar");
        botonRegistrar.setText("Registrar");
        sesion_id.setPromptText("ID del empleado");
        sesion_password.setPromptText("Contraseña");
        
        datos_sesion.getChildren().add(sesion_id);
        datos_sesion.getChildren().add(sesion_password);
        datos_sesion.getChildren().add(botonAceptar);
        barra_boton_registrar.getChildren().add(botonRegistrar);
        
        // Acciones del boton "Acpetar" del menú de inicio de sesion
        botonAceptar.setOnAction(event -> {
            
            ControlAccesoEmpleadosMorales controlAcceso = new ControlAccesoEmpleadosMorales();
            String e_id = sesion_id.getText();
            String e_pswrd = sesion_password.getText();
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
        
        // Acciones del boton "Registrar" del menú de inicio de sesion
        botonRegistrar.setOnAction(event -> {
            datos_sesion.getChildren().clear();
            barra_boton_registrar.getChildren().clear();
            
            Button iniciar_sesion = new Button();
            Button aceptarRegistro = new Button();
            MenuItem op1 = new MenuItem("Acceso permitido");
            MenuItem op2 = new MenuItem("Acceso denegado");
            
            TextField nombre = new TextField();
            TextField apellido = new TextField();
            TextField edad = new TextField();
            TextField direccion = new TextField();
            TextField id = new TextField();
            TextField puesto = new TextField();
            TextField salario = new TextField();
            MenuButton acceso = new MenuButton("Acceso", null, op1, op2);
            TextField password = new TextField();
            int[] tiene_acceso = {0};
            
            nombre.setText("Nombres");
            apellido.setText("Apellidos");
            edad.setText("Edad");
            direccion.setText("Direccion");
            id.setText("ID");
            puesto.setText("Puesto");
            salario.setText("Salario");
            password.setText("Contraseña");
            
            datos_sesion.getChildren().add(nombre);
            datos_sesion.getChildren().add(apellido);
            datos_sesion.getChildren().add(edad);
            datos_sesion.getChildren().add(direccion);
            datos_sesion.getChildren().add(id);
            datos_sesion.getChildren().add(puesto);
            datos_sesion.getChildren().add(salario);
            datos_sesion.getChildren().add(acceso);
            datos_sesion.getChildren().add(password);
            
            iniciar_sesion.setText("Iniciar Sesion");
            aceptarRegistro.setText("Aceptar");
            barra_boton_registrar.getChildren().add(iniciar_sesion);
            datos_sesion.getChildren().add(aceptarRegistro);
            
            // Regresa al menú de Inicio de sesion cuando presione este boton
            iniciar_sesion.setOnAction(evento -> {
                datos_sesion.getChildren().clear();
                barra_boton_registrar.getChildren().clear();
                
                datos_sesion.getChildren().add(sesion_id);
                datos_sesion.getChildren().add(sesion_password);
                datos_sesion.getChildren().add(botonAceptar);
                barra_boton_registrar.getChildren().add(botonRegistrar);
            });
            
            // Opcion de dar acceso al programa
            op1.setOnAction(evento -> {
                acceso.setText("Acceso permitido");
                tiene_acceso[0] = 1;
            });
            
            // Opcion de negar el acceso al programa
            op2.setOnAction(evento -> {
                acceso.setText("Acceso denegado");
                tiene_acceso[0] = 0;
            });
            
            // Registra al empleado si no esta yá registrado
            aceptarRegistro.setOnAction(evento -> {
                int campo_id = Integer.parseInt(id.getText());
                boolean found = false;
                for(Empleado e: emp){
                    if(campo_id == e.getId()){
                        found = true;
                        Label error = new Label();
                        error.setText("ID ya ingresado.\nFavor de ingresar uno nuevo.");
                        datos_sesion.getChildren().add(error);
                        break;
                    }
                }
                
                if(!found){
                    String campo_nombre = nombre.getText();
                    String campo_apellido = apellido.getText();
                    String campo_edad = edad.getText();
                    String campo_direccion = direccion.getText();
                    String campo_puesto = puesto.getText();
                    double campo_salario = Double.parseDouble(salario.getText());
                    String campo_password = password.getText();
                    boolean acceso_actual;
                    
                    if(tiene_acceso[0] == 1){
                        acceso_actual = true;
                    } else {
                        acceso_actual = false;
                    }
                    
                    Empleado e1 = new Empleado(campo_nombre, campo_apellido, Integer.parseInt(campo_edad), campo_direccion, campo_id, campo_password, campo_puesto, campo_salario, acceso_actual);
                    empleados.agregarEmpleado(emp, e1);
                    
                    iniciar_sesion.fire();
                }
                
                System.out.println("Boton aceptar registro");
            });
            
            System.out.println("Registrar");
        });
        
    }
}
