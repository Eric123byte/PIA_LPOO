
package gymposem509.controlador;

import gymposem509.modelo.Cliente;
import gymposem509.modelo.ControlAccesoClientesMorales;
import gymposem509.modelo.GestionClientesMorales;
import gymposem509.modelo.Membresia;
import gymposem509.modelo.ProcesadorPagos509;
import gymposem509.modelo.SistemaMembresia2608;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MenuClientesController {
    
    @FXML
    private VBox contenido;
    @FXML
    private Button registrar;
    @FXML
    private Button actualizar;
    @FXML
    private Button buscar;
    @FXML
    private Button eliminar;
    @FXML
    private Button entradaSalida;
    @FXML
    private Button salir;
    
    private List<Cliente> clientes = new ArrayList<>();
    private GestionClientesMorales gestor = new GestionClientesMorales();
    
    public void initialize() {
        
        // Cargamos a todos los clientes
        gestor.cargarClientes(clientes);
        
        Cliente.inicializarContador(clientes);
        
        // Prionó el boton registrar
        registrar.setOnAction(evento -> {
            contenido.getChildren().clear();
            
            MenuItem bronce = new MenuItem("Membrecia bronce");
            MenuItem plata = new MenuItem("Membresía plata");
            MenuItem oro = new MenuItem("Membresía oro");
            MenuItem efectivo = new MenuItem("Efectivo");
            MenuItem tarjeta = new MenuItem("Tarjeta");
            
            Label indicador_menu_clientes = new Label("Registrar cliente");
            Label id = new Label("ID: " + String.valueOf(Cliente.getContadorId()));
            TextField nombres = new TextField();
            TextField apellidos = new TextField();
            TextField telefono = new TextField();
            MenuButton membresia = new MenuButton("Membresía", null, bronce, plata, oro);
            MenuButton pago = new MenuButton("Metodo de pago", null, efectivo, tarjeta);
            Button aceptar = new Button("Aceptar");
            Button cancelar = new Button("Cancelar");
            String[] membresia_seleccionada = {"nulo"};
            String[] pago_seleccionado = {"nulo"};
            
            nombres.setPromptText("Nombre");
            apellidos.setPromptText("Apellidos");
            telefono.setPromptText("Teléfono");
            
            // VALIDACIONES PARA LOS TEXTFIELD
            // Validación para nombres: solo letras y espacios
            nombres.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                    nombres.setText(oldValue);
                }
            });
            
            // Validación para apellidos: solo letras y espacios
            apellidos.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                    apellidos.setText(oldValue);
                }
            });
            
            // Validación para teléfono: solo números y máximo 10 dígitos
            telefono.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    telefono.setText(oldValue);
                } else if (newValue.length() > 10) {
                    telefono.setText(oldValue);
                }
            });
            
            contenido.getChildren().add(indicador_menu_clientes);
            contenido.getChildren().add(id);
            contenido.getChildren().add(nombres);
            contenido.getChildren().add(apellidos);
            contenido.getChildren().add(telefono);
            contenido.getChildren().add(membresia);
            contenido.getChildren().add(pago);
            contenido.getChildren().add(aceptar);
            contenido.getChildren().add(cancelar);
            
            // Botones de membresías
            bronce.setOnAction(event -> {
                membresia.setText("Membresía bronce");
                membresia_seleccionada[0] = "Bronce";
            });
            
            plata.setOnAction(event -> {
                membresia.setText("Membresía plata");
                membresia_seleccionada[0] = "Plata";
            });
            
            oro.setOnAction(event -> {
                membresia.setText("Membresía oro");
                membresia_seleccionada[0] = "Oro";
            });
            
            // Botones de metodo de pago
            efectivo.setOnAction(event -> {
                pago.setText("Efectivo");
                pago_seleccionado[0] = "efectivo";
            });
            
            tarjeta.setOnAction(event -> {
                pago.setText("Tarjeta");
                pago_seleccionado[0] = "tarjeta";
            });
            
            // Accion del boton Aceptar al ser presionado
            aceptar.setOnAction(event -> {
                String nombre_cliente = nombres.getText();
                String apellidos_cliente = apellidos.getText();
                String telefono_cliente = telefono.getText();
                int id_cliente = Cliente.getContadorId();
                boolean find = false;
                
                // VALIDACIONES ADICIONALES AL ACEPTAR
                if (nombre_cliente.trim().isEmpty()) {
                    mostrarAlerta("Error", "El nombre es obligatorio");
                    return;
                }
                
                if (apellidos_cliente.trim().isEmpty()) {
                    mostrarAlerta("Error", "Los apellidos son obligatorios");
                    return;
                }
                
                if (telefono_cliente.trim().isEmpty()) {
                    mostrarAlerta("Error", "El teléfono es obligatorio");
                    return;
                }
                
                if (telefono_cliente.length() < 10) {
                    mostrarAlerta("Error", "El teléfono debe tener 10 dígitos");
                    return;
                }
                
                id.setText("ID: " + String.valueOf(id_cliente));
                
                System.out.println(Cliente.getContadorId());
                
                for(Cliente c: clientes){
                    System.out.println("c.getId_cliente(): " + c.getId_cliente() + ", id_cliente: " + id_cliente);
                    if(c.getId_cliente() == id_cliente){
                        find = true;
                        System.out.println("ID ya ingresado");
                        break;
                    }
                }
                
                if(!find && !(pago_seleccionado[0].equalsIgnoreCase("nulo") || membresia_seleccionada[0].equalsIgnoreCase("nulo")) ){
                    contenido.getChildren().removeIf(node -> 
                        (node instanceof TextField || node instanceof Button || node instanceof MenuButton)
                    );
                    
                    // Pago con efectivo
                    SistemaMembresia2608 sistema_m = new SistemaMembresia2608();
                    List<Membresia> membresias = new ArrayList<>();
                    double precio;
                    int puntos;

                    if(membresia_seleccionada[0].equals("Oro")){
                        precio = sistema_m.precio_oro;
                        puntos = sistema_m.puntos_oro;
                    } else {
                        if(membresia_seleccionada[0].equals("Plata")){
                            precio = sistema_m.precio_plata;
                            puntos = sistema_m.puntos_plata;
                        } else {
                            precio = sistema_m.precio_bronce;
                            puntos = sistema_m.puntos_bronce;
                        }
                    }
                    
                    Label monto = new Label("Monto: $" + String.valueOf(precio));
                    TextField descuento = new TextField("0"); // Valor por defecto 0
                    Button aceptar_monto = new Button("Aceptar");
                    Button cancelar_monto = new Button("Cancelar");
                    
                    // VALIDACIÓN PARA DESCUENTO: solo números y punto decimal
                    descuento.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.matches("\\d*(\\.\\d*)?")) {
                            descuento.setText(oldValue);
                        }
                    });
                    
                    contenido.getChildren().add(monto);
                    contenido.getChildren().add(descuento);
                    
                    if(pago_seleccionado[0].equals("tarjeta")){
                        TextField num_tarjeta = new TextField();
                        TextField fecha_cad = new TextField();
                        TextField nombre_titular = new TextField();
                        
                        num_tarjeta.setPromptText("Número de tarjeta (16 dígitos)");
                        fecha_cad.setPromptText("MM/YY");
                        nombre_titular.setPromptText("Nombre del titular");
                        
                        // VALIDACIONES PARA TARJETA
                        // Validación número de tarjeta: solo números, máximo 16 dígitos
                        num_tarjeta.textProperty().addListener((observable, oldValue, newValue) -> {
                            if (!newValue.matches("\\d*")) {
                                num_tarjeta.setText(oldValue);
                            } else if (newValue.length() > 16) {
                                num_tarjeta.setText(oldValue);
                            }
                        });
                        
                        // Validación fecha caducidad: formato MM/YY
                        fecha_cad.textProperty().addListener((observable, oldValue, newValue) -> {
                            if (!newValue.matches("\\d{0,2}/?\\d{0,2}")) {
                                fecha_cad.setText(oldValue);
                            } else if (newValue.length() > 5) {
                                fecha_cad.setText(oldValue);
                            }
                        });
                        
                        // Validación nombre titular: solo letras y espacios
                        nombre_titular.textProperty().addListener((observable, oldValue, newValue) -> {
                            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                                nombre_titular.setText(oldValue);
                            }
                        });
                        
                        contenido.getChildren().add(num_tarjeta);
                        contenido.getChildren().add(fecha_cad);
                        contenido.getChildren().add(nombre_titular);
                    }
                    
                    contenido.getChildren().add(aceptar_monto);
                    contenido.getChildren().add(cancelar_monto);

                    aceptar_monto.setOnAction(even -> {
                        // VALIDACIONES FINALES ANTES DE PROCESAR PAGO
                        if (pago_seleccionado[0].equals("tarjeta")) {
                            Node numTarjetaNode = contenido.getChildren().stream()
                                .filter(node -> node instanceof TextField && ((TextField)node).getPromptText() != null 
                                    && ((TextField)node).getPromptText().contains("tarjeta"))
                                .findFirst().orElse(null);
                            Node fechaCadNode = contenido.getChildren().stream()
                                .filter(node -> node instanceof TextField && ((TextField)node).getPromptText() != null 
                                    && ((TextField)node).getPromptText().contains("MM/YY"))
                                .findFirst().orElse(null);
                            Node nombreTitularNode = contenido.getChildren().stream()
                                .filter(node -> node instanceof TextField && ((TextField)node).getPromptText() != null 
                                    && ((TextField)node).getPromptText().contains("titular"))
                                .findFirst().orElse(null);
                            
                            if (numTarjetaNode != null && fechaCadNode != null && nombreTitularNode != null) {
                                TextField numTarjetaField = (TextField) numTarjetaNode;
                                TextField fechaCadField = (TextField) fechaCadNode;
                                TextField nombreTitularField = (TextField) nombreTitularNode;
                                
                                if (numTarjetaField.getText().length() != 16) {
                                    mostrarAlerta("Error", "El número de tarjeta debe tener 16 dígitos");
                                    return;
                                }
                                
                                if (fechaCadField.getText().length() != 5 || !fechaCadField.getText().matches("\\d{2}/\\d{2}")) {
                                    mostrarAlerta("Error", "La fecha de caducidad debe tener el formato MM/YY");
                                    return;
                                }
                                
                                if (nombreTitularField.getText().trim().isEmpty()) {
                                    mostrarAlerta("Error", "El nombre del titular es obligatorio");
                                    return;
                                }
                            }
                        }
                        
                        // Validar que el descuento sea un número válido
                        double descuentoValue;
                        try {
                            descuentoValue = Double.parseDouble(descuento.getText());
                            if (descuentoValue < 0 || descuentoValue > 100) {
                                mostrarAlerta("Error", "El descuento debe estar entre 0 y 100");
                                return;
                            }
                        } catch (NumberFormatException e) {
                            mostrarAlerta("Error", "El descuento debe ser un número válido");
                            return;
                        }
                        
                        Cliente c_nuevo = new Cliente(nombre_cliente, apellidos_cliente, telefono_cliente);
                        System.out.println("Puntos de 'puntos': " + puntos);
                        c_nuevo.setPuntos(puntos);
                        System.out.println("Puntos de 'c_nuevo': " + c_nuevo.getPuntos());
                        ProcesadorPagos509 procesar_pago = new ProcesadorPagos509();
                        String total = String.valueOf(precio * (1 - (Double.parseDouble(descuento.getText()) / 100)));
                        
                        System.out.println(c_nuevo);
                        
                        sistema_m.cargarMembresias(membresias);
                        sistema_m.agregarMembresia(membresias, id_cliente, membresia_seleccionada[0]);
                        procesar_pago.procesarPago(Double.parseDouble(total));
                        procesar_pago.guardarFactura(c_nuevo.getId_cliente(), nombre_cliente, apellidos_cliente, String.valueOf(precio), membresia_seleccionada[0], descuento.getText(), total, pago_seleccionado[0]);
                        gestor.agregarClientes(clientes, c_nuevo);
                        Cliente.incrementarContador();
                        cancelar.fire();
                    });

                    cancelar_monto.setOnAction(even -> {
                        cancelar.fire();
                    });
                } else {
                    if (membresia_seleccionada[0].equalsIgnoreCase("nulo")) {
                        mostrarAlerta("Error", "Debe seleccionar una membresía");
                    }
                    if (pago_seleccionado[0].equalsIgnoreCase("nulo")) {
                        mostrarAlerta("Error", "Debe seleccionar un método de pago");
                    }
                }
            });
            
            cancelar.setOnAction(event -> {
                contenido.getChildren().clear();
            });
            
        });
        
        actualizar.setOnAction(evento -> {
            contenido.getChildren().clear();

            // Buscar cliente a actualizar
            Label tituloBusqueda = new Label("Buscar cliente a actualizar");
            TextField campoBusqueda = new TextField();
            campoBusqueda.setPromptText("Ingrese nombre del cliente...");
            campoBusqueda.setPrefWidth(200);

            // VALIDACIÓN PARA BÚSQUEDA: solo letras y espacios
            campoBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                    campoBusqueda.setText(oldValue);
                }
            });

            Button botonBuscar = new Button("Buscar");
            Button botonCancelar = new Button("Cancelar");

            // Área para mostrar resultados de búsqueda
            VBox resultadosContainer = new VBox(10);
            ScrollPane scrollResultados = new ScrollPane();
            scrollResultados.setFitToWidth(true);
            scrollResultados.setPrefViewportHeight(200);
            scrollResultados.setContent(resultadosContainer);

            HBox busquedaLayout = new HBox(10);
            busquedaLayout.getChildren().addAll(campoBusqueda, botonBuscar, botonCancelar);

            contenido.getChildren().addAll(tituloBusqueda, busquedaLayout, scrollResultados);

            // Acción de búsqueda para actualizar
            botonBuscar.setOnAction(event -> {
                String nombreBuscado = campoBusqueda.getText().trim();

                if (nombreBuscado.isEmpty()) {
                    mostrarAlerta("Advertencia", "Por favor ingrese un nombre para buscar");
                    return;
                }

                resultadosContainer.getChildren().clear();

                List<Cliente> clientesEncontrados = buscarClientesPorNombre(clientes, nombreBuscado);

                if (clientesEncontrados.isEmpty()) {
                    Label sinResultados = new Label("No se encontraron clientes con el nombre: " + nombreBuscado);
                    resultadosContainer.getChildren().add(sinResultados);
                } else {
                    Label tituloResultados = new Label("Seleccione el cliente a actualizar:");
                    tituloResultados.setStyle("-fx-font-weight: bold;");
                    resultadosContainer.getChildren().add(tituloResultados);

                    for (Cliente cliente : clientesEncontrados) {
                        HBox clienteRow = crearFilaClienteParaActualizar(cliente);
                        resultadosContainer.getChildren().add(clienteRow);
                    }
                }
            });

            botonCancelar.setOnAction(event -> {
                contenido.getChildren().clear();
            });
        });
        
        buscar.setOnAction(evento -> {
            contenido.getChildren().clear();

            // Campo de búsqueda
            TextField campoBusqueda = new TextField();
            campoBusqueda.setPromptText("Ingrese nombre del cliente...");
            campoBusqueda.setPrefWidth(200);

            // VALIDACIÓN PARA BÚSQUEDA: solo letras y espacios
            campoBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                    campoBusqueda.setText(oldValue);
                }
            });

            Button botonBuscar = new Button("Buscar");
            Button botonCancelar = new Button("Cancelar");

            // Mostrar resultados
            VBox resultadosContainer = new VBox(10);
            ScrollPane scrollResultados = new ScrollPane();
            scrollResultados.setFitToWidth(true);
            scrollResultados.setPrefViewportHeight(300);
            scrollResultados.setContent(resultadosContainer);

            // Layout de búsqueda
            HBox busquedaLayout = new HBox(10);
            busquedaLayout.getChildren().addAll(campoBusqueda, botonBuscar, botonCancelar);

            // Agregar al contenido principal
            contenido.getChildren().addAll(busquedaLayout, scrollResultados);

            // Acción de búsqueda
            botonBuscar.setOnAction(event -> {
                String nombreBuscado = campoBusqueda.getText().trim();

                if (nombreBuscado.isEmpty()) {
                    mostrarAlerta("Advertencia", "Por favor ingrese un nombre para buscar");
                    return;
                }

                // Limpiar resultados anteriores
                resultadosContainer.getChildren().clear();

                // Buscar clientes
                List<Cliente> clientesEncontrados = buscarClientesPorNombre(clientes, nombreBuscado);

                if (clientesEncontrados.isEmpty()) {
                    Label sinResultados = new Label("No se encontraron clientes con el nombre: " + nombreBuscado);
                    resultadosContainer.getChildren().add(sinResultados);
                } else {
                    Label tituloResultados = new Label("Resultados encontrados (" + clientesEncontrados.size() + "):");
                    tituloResultados.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
                    resultadosContainer.getChildren().add(tituloResultados);

                    // Mostrar cada cliente encontrado
                    for (Cliente cliente : clientesEncontrados) {
                        VBox clienteCard = crearTarjetaCliente(cliente);
                        resultadosContainer.getChildren().add(clienteCard);
                    }
                }
            });

            botonCancelar.setOnAction(event -> {
                contenido.getChildren().clear();
            });
        });
        
        eliminar.setOnAction(evento -> {
            contenido.getChildren().clear();
            
            Label tituloBusqueda = new Label("Buscar cliente a eliminar");
            TextField campoBusqueda = new TextField();
            campoBusqueda.setPromptText("Ingrese nombre del cliente...");
            campoBusqueda.setPrefWidth(200);

            // VALIDACIÓN PARA BÚSQUEDA: solo letras y espacios
            campoBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                    campoBusqueda.setText(oldValue);
                }
            });

            Button botonBuscar = new Button("Buscar");
            Button botonCancelar = new Button("Cancelar");

            // Área para mostrar resultados de búsqueda
            VBox resultadosContainer = new VBox(10);
            ScrollPane scrollResultados = new ScrollPane();
            scrollResultados.setFitToWidth(true);
            scrollResultados.setPrefViewportHeight(200);
            scrollResultados.setContent(resultadosContainer);

            HBox busquedaLayout = new HBox(10);
            busquedaLayout.getChildren().addAll(campoBusqueda, botonBuscar, botonCancelar);

            contenido.getChildren().addAll(tituloBusqueda, busquedaLayout, scrollResultados);
            
            // Acción de búsqueda para eliminar
            botonBuscar.setOnAction(event -> {
                String nombreBuscado = campoBusqueda.getText().trim();

                if (nombreBuscado.isEmpty()) {
                    mostrarAlerta("Advertencia", "Por favor ingrese un nombre para buscar");
                    return;
                }

                resultadosContainer.getChildren().clear();

                List<Cliente> clientesEncontrados = buscarClientesPorNombre(clientes, nombreBuscado);

                if (clientesEncontrados.isEmpty()) {
                    Label sinResultados = new Label("No se encontraron clientes con el nombre: " + nombreBuscado);
                    resultadosContainer.getChildren().add(sinResultados);
                } else {
                    Label tituloResultados = new Label("Seleccione el cliente a eliminar:");
                    tituloResultados.setStyle("-fx-font-weight: bold;");
                    resultadosContainer.getChildren().add(tituloResultados);

                    for (Cliente cliente : clientesEncontrados) {
                        HBox clienteRow = crearFilaClienteEliminar(cliente);
                        resultadosContainer.getChildren().add(clienteRow);
                    }
                }
            });

            botonCancelar.setOnAction(event -> {
                contenido.getChildren().clear();
            });
        });
        
        entradaSalida.setOnAction(evento -> {
            contenido.getChildren().clear();

            // Buscar cliente a actualizar
            Label tituloBusqueda = new Label("Entrada / Salida del cliente");
            TextField campoBusqueda = new TextField();
            campoBusqueda.setPromptText("Ingrese nombre del cliente...");
            campoBusqueda.setPrefWidth(200);

            // VALIDACIÓN PARA BÚSQUEDA: solo letras y espacios
            campoBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                    campoBusqueda.setText(oldValue);
                }
            });

            Button botonBuscar = new Button("Buscar");
            Button botonCancelar = new Button("Cancelar");

            // Área para mostrar resultados de búsqueda
            VBox resultadosContainer = new VBox(10);
            ScrollPane scrollResultados = new ScrollPane();
            scrollResultados.setFitToWidth(true);
            scrollResultados.setPrefViewportHeight(200);
            scrollResultados.setContent(resultadosContainer);

            HBox busquedaLayout = new HBox(10);
            busquedaLayout.getChildren().addAll(campoBusqueda, botonBuscar, botonCancelar);

            contenido.getChildren().addAll(tituloBusqueda, busquedaLayout, scrollResultados);

            // Acción de búsqueda para actualizar
            botonBuscar.setOnAction(event -> {
                String nombreBuscado = campoBusqueda.getText().trim();

                if (nombreBuscado.isEmpty()) {
                    mostrarAlerta("Advertencia", "Por favor ingrese un nombre para buscar");
                    return;
                }

                resultadosContainer.getChildren().clear();

                List<Cliente> clientesEncontrados = buscarClientesPorNombre(clientes, nombreBuscado);

                if (clientesEncontrados.isEmpty()) {
                    Label sinResultados = new Label("No se encontraron clientes con el nombre: " + nombreBuscado);
                    resultadosContainer.getChildren().add(sinResultados);
                } else {
                    Label tituloResultados = new Label("Seleccione el cliente:");
                    tituloResultados.setStyle("-fx-font-weight: bold;");
                    resultadosContainer.getChildren().add(tituloResultados);

                    for (Cliente cliente : clientesEncontrados) {
                        HBox clienteRow = registrarEntradaSalida(cliente);
                        resultadosContainer.getChildren().add(clienteRow);
                    }
                }
            });

            botonCancelar.setOnAction(event -> {
                contenido.getChildren().clear();
            });
        });
        
        salir.setOnAction(evento -> {
            try {
                Parent menuPrincipal = FXMLLoader.load(getClass().getResource("/gymposem509/vista/guifxml.fxml"));
                Scene escena = salir.getScene();
                escena.setRoot(menuPrincipal);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        
    }
    
    
    // Funciones auxiliares
    private List<Cliente> buscarClientesPorNombre(List<Cliente> clientes, String nombre) {
        List<Cliente> resultados = new ArrayList<>();
        for (Cliente cliente : clientes) {
            if (cliente.getNombres().toLowerCase().contains(nombre.toLowerCase())) {
                resultados.add(cliente);
            }
        }
        return resultados;
    }

    private VBox crearTarjetaCliente(Cliente cliente) {
        VBox tarjeta = new VBox(5);
        tarjeta.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 10; -fx-background-color: #f9f9f9;");
        tarjeta.setPrefWidth(400);

        // Información del cliente
        SistemaMembresia2608 sistema_m = new SistemaMembresia2608();
        List<Membresia> membresia = new ArrayList<>();
        
        sistema_m.cargarMembresias(membresia);
        Membresia membresia_cli = sistema_m.buscarMembresia(membresia, cliente.getId_cliente());
        
//        Label texto = new Label(cliente.toString());
        Label idLabel = new Label("ID: " + cliente.getId_cliente());
        Label nombreLabel = new Label("Nombre: " + cliente.getNombres() + " " + cliente.getApellidos());
        Label telefonoLabel = new Label("Teléfono: " + cliente.getNum_telefono());
        Label membresiaLabel = new Label("Membresia: " + membresia_cli.getTipo_membresia());
        Label puntosLabel = new Label("Puntos: " + cliente.getPuntos());

        // Estilo para mejor presentación
        idLabel.setStyle("-fx-font-weight: bold;");
        nombreLabel.setStyle("-fx-font-size: 13;");

        tarjeta.getChildren().addAll(idLabel, nombreLabel, telefonoLabel, membresiaLabel, puntosLabel);

        return tarjeta;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private HBox crearFilaClienteParaActualizar(Cliente cliente) {
        HBox fila = new HBox(10);
        fila.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 10; -fx-background-color: #f9f9f9;");
        fila.setPrefWidth(400);

        // Información del cliente
        VBox infoCliente = new VBox(5);
        Label idLabel = new Label("ID: " + cliente.getId_cliente());
        Label nombreLabel = new Label("Nombre: " + cliente.getNombres() + " " + cliente.getApellidos());
        Label telefonoLabel = new Label("Teléfono: " + cliente.getNum_telefono());

        idLabel.setStyle("-fx-font-weight: bold;");

        infoCliente.getChildren().addAll(idLabel, nombreLabel, telefonoLabel);

        // Botón para seleccionar este cliente
        Button botonSeleccionar = new Button("Seleccionar");
        botonSeleccionar.setOnAction(event -> {
            mostrarFormularioActualizacion(cliente);
        });

        fila.getChildren().addAll(infoCliente, botonSeleccionar);

        return fila;
    }
    
    private HBox registrarEntradaSalida(Cliente cliente) {
        HBox fila = new HBox(10);
        fila.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 10; -fx-background-color: #f9f9f9;");
        fila.setPrefWidth(400);

        // Información del cliente
        VBox infoCliente = new VBox(5);
        Label idLabel = new Label("ID: " + cliente.getId_cliente());
        Label nombreLabel = new Label("Nombre: " + cliente.getNombres() + " " + cliente.getApellidos());
        Label telefonoLabel = new Label("Teléfono: " + cliente.getNum_telefono());

        idLabel.setStyle("-fx-font-weight: bold;");

        infoCliente.getChildren().addAll(idLabel, nombreLabel, telefonoLabel);
        
        ControlAccesoClientesMorales acceso_clientes = new ControlAccesoClientesMorales();
        
        // Botón para seleccionar este cliente
        Button botonEntrada = new Button("Entrada");
        Button botonSalida = new Button("Salida");
        botonEntrada.setOnAction(event -> {
            acceso_clientes.registrarEntrada(cliente.getId_cliente(), cliente.getNombres(), cliente.getApellidos());
            contenido.getChildren().clear();
        });
        botonSalida.setOnAction(event -> {
            acceso_clientes.registrarSalida(cliente.getId_cliente(), cliente.getNombres(), cliente.getApellidos());
            contenido.getChildren().clear();
        });

        fila.getChildren().addAll(infoCliente, botonEntrada, botonSalida);

        return fila;
    }
    
    private HBox crearFilaClienteEliminar(Cliente cliente) {
        HBox fila = new HBox(10);
        fila.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-padding: 10; -fx-background-color: #f9f9f9;");
        fila.setPrefWidth(400);

        // Información del cliente
        VBox infoCliente = new VBox(5);
        Label idLabel = new Label("ID: " + cliente.getId_cliente());
        Label nombreLabel = new Label("Nombre: " + cliente.getNombres() + " " + cliente.getApellidos());
        Label telefonoLabel = new Label("Teléfono: " + cliente.getNum_telefono());

        idLabel.setStyle("-fx-font-weight: bold;");

        infoCliente.getChildren().addAll(idLabel, nombreLabel, telefonoLabel);

        // Botón para seleccionar este cliente
        Button botonSeleccionar = new Button("Seleccionar");
        botonSeleccionar.setOnAction(event -> {
            mostrarFormularioEliminar(cliente);
        });

        fila.getChildren().addAll(infoCliente, botonSeleccionar);

        return fila;
    }
    
    private void mostrarFormularioActualizacion(Cliente cliente) {
        contenido.getChildren().clear();
        
        SistemaMembresia2608 sistema_m = new SistemaMembresia2608();
        List<Membresia> membresias = new ArrayList<>();
        
        sistema_m.cargarMembresias(membresias);
        
        Membresia membresia_cli = sistema_m.buscarMembresia(membresias, cliente.getId_cliente());
        
        Label titulo = new Label("Actualizar Cliente - ID: " + cliente.getId_cliente());
        titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        // Campos de formulario
        TextField campoNombres = new TextField(cliente.getNombres());
        campoNombres.setPromptText("Nombres");

        TextField campoApellidos = new TextField(cliente.getApellidos());
        campoApellidos.setPromptText("Apellidos");

        TextField campoTelefono = new TextField(cliente.getNum_telefono());
        campoTelefono.setPromptText("Teléfono");
        
        // VALIDACIONES PARA ACTUALIZACIÓN
        // Validación nombres: solo letras y espacios
        campoNombres.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                campoNombres.setText(oldValue);
            }
        });
        
        // Validación apellidos: solo letras y espacios
        campoApellidos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                campoApellidos.setText(oldValue);
            }
        });
        
        // Validación teléfono: solo números, máximo 10 dígitos
        campoTelefono.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                campoTelefono.setText(oldValue);
            } else if (newValue.length() > 10) {
                campoTelefono.setText(oldValue);
            }
        });
        
        MenuItem bronce = new MenuItem("Membrecia bronce");
        MenuItem plata = new MenuItem("Membresía plata");
        MenuItem oro = new MenuItem("Membresía oro");
        MenuButton membresia = new MenuButton("Membrecia " + membresia_cli.getTipo_membresia(), null, bronce, plata, oro);
        String[] membresia_seleccionada = {membresia_cli.getTipo_membresia()};
        
        // Botones de membresías
        bronce.setOnAction(event -> {
            membresia.setText("Membresía bronce");
            membresia_seleccionada[0] = "Bronce";
        });

        plata.setOnAction(event -> {
            membresia.setText("Membresía plata");
            membresia_seleccionada[0] = "Plata";
        });

        oro.setOnAction(event -> {
            membresia.setText("Membresía oro");
            membresia_seleccionada[0] = "Oro";
        });
        
        // Botones
        Button botonGuardar = new Button("Guardar Cambios");
        Button botonCancelar = new Button("Cancelar");

        HBox botonesLayout = new HBox(10);
        botonesLayout.getChildren().addAll(botonGuardar, botonCancelar);

        contenido.getChildren().addAll(
            titulo, campoNombres, campoApellidos, campoTelefono, membresia, botonesLayout
        );

        // Acción guardar cambios
        botonGuardar.setOnAction(event -> {
            String nuevosNombres = campoNombres.getText().trim();
            String nuevosApellidos = campoApellidos.getText().trim();
            String nuevoTelefono = campoTelefono.getText().trim();

            if (nuevosNombres.isEmpty() || nuevosApellidos.isEmpty() || nuevoTelefono.isEmpty()) {
                mostrarAlerta("Error", "Todos los campos son obligatorios");
                return;
            }
            
            if (nuevoTelefono.length() < 10) {
                mostrarAlerta("Error", "El teléfono debe tener 10 dígitos");
                return;
            }

            // Actualizar el cliente en la lista
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getId_cliente() == cliente.getId_cliente()) {
                    // Crear cliente actualizado
                    Cliente clienteActualizado = new Cliente(nuevosNombres, nuevosApellidos, nuevoTelefono);
                    Membresia membresiaNueva = new Membresia(cliente.getId_cliente(), membresia_seleccionada[0], membresia_cli.getInicio(), membresia_cli.getFin());
                    clienteActualizado.setId_cliente(cliente.getId_cliente());
                    clienteActualizado.setPuntos(cliente.getPuntos());

                    // Reemplazar en la lista
                    clientes.set(i, clienteActualizado);
                    for (int j = 0; j < membresias.size(); j++) {
                        if (membresias.get(j).getId_cliente() == cliente.getId_cliente())
                            membresias.set(j, membresiaNueva);
                    }
                    
                    gestor.actualizarCliente(clientes, cliente.getId_cliente(), nuevosNombres, nuevosApellidos, nuevoTelefono);
                    gestor.serializarCliente(clientes);
                    sistema_m.actualizarMembresia(membresias, cliente.getId_cliente(), membresia_seleccionada[0], membresia_cli.getInicio(), membresia_cli.getFin());
                    sistema_m.serializarMembresia(membresias);

                    System.out.println("Cliente actualizado exitosamente");
                    break;
                }
            }

            // Regresar al menú principal
            contenido.getChildren().clear();
        });

        botonCancelar.setOnAction(event -> {
            contenido.getChildren().clear();
        });
    }
    
    private void mostrarFormularioEliminar(Cliente cliente) {
        contenido.getChildren().clear();

        Label titulo = new Label("Eliminar Cliente - ID: " + cliente.getId_cliente());
        titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        // Campos de formulario
        Label campoNombres = new Label("Nombre: " + cliente.getNombres());
        Label campoApellidos = new Label("Apellidos: " + cliente.getApellidos());
        Label campoTelefono = new Label("Telefono: " + cliente.getNum_telefono());

        // Botones
        Button botonEliminar = new Button("Eliminar");
        Button botonCancelar = new Button("Cancelar");

        HBox botonesLayout = new HBox(10);
        botonesLayout.getChildren().addAll(botonEliminar, botonCancelar);

        contenido.getChildren().addAll(
            titulo, campoNombres, campoApellidos, campoTelefono, botonesLayout
        );

        // Acción eliminar cliente
        botonEliminar.setOnAction(event -> {
            clientes.remove(cliente);
            gestor.serializarCliente(clientes);
            
            // Regresar al menú principal
            contenido.getChildren().clear();
        });

        botonCancelar.setOnAction(event -> {
            contenido.getChildren().clear();
        });
    }
    
}
