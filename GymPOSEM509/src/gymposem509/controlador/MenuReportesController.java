package gymposem509.controlador;

import MainApp.Main;
import gymposem509.modelo.GeneradorReportes;
import gymposem509.modelo.TipoReporte;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class MenuReportesController {
    
    @FXML
    private ComboBox<TipoReporte> cbTipoReporte;
    
    @FXML
    private Button btnGenerar;
    
    @FXML
    private Button btnRegresar;
    
    @FXML
    private Label lblEstado;
    
    public void initialize() {
        cbTipoReporte.getItems().addAll(TipoReporte.values());
        
        btnGenerar.setOnAction(e -> generarReporte());
        
        btnRegresar.setOnAction(evento -> {
            try {
                Parent menuPrincipal = FXMLLoader.load(getClass().getResource("/gymposem509/vista/guifxml.fxml"));
                Scene escena = btnRegresar.getScene();
                escena.setRoot(menuPrincipal);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
    private void generarReporte() {
        TipoReporte tipo = cbTipoReporte.getValue();
        if (tipo == null) {
            lblEstado.setText("Selecciona un tipo de reporte.");
            return;
        }
        
        lblEstado.setText("Generando reporte en segundo plano...");
        
        GeneradorReportes tarea = new GeneradorReportes(tipo, "admin");
        Thread hilo = new Thread(tarea);
        hilo.setDaemon(true); // para que no bloquee el cierre de la app
        hilo.start();
        
        // Actualizar estado cuando termine
        new Thread(() -> {
            try {
                hilo.join();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            Platform.runLater(() -> lblEstado.setText("Reporte generado. Revisa la carpeta 'Reportes'."));
        }).start();
    }
}
