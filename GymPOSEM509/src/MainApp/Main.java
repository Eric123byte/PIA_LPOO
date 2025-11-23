package MainApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    
    private static Stage stagePrincipal;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        stagePrincipal = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gymposem509/vista/login.fxml"));
        Scene scene = new Scene(loader.load());

        String css = Objects.requireNonNull(this.getClass().getResource("/Recursos/style/login.css")).toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setScene(scene);
        primaryStage.setTitle("GymPOSEM509");
        primaryStage.show();
        Parent root = FXMLLoader.load(getClass().getResource("/gymposem509/vista/login.fxml"));
    }
    
    public static void cambiarEscena(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/gymposem509/vista/" + fxml));
            Scene nuevaEscena = new Scene(loader.load());
            stagePrincipal.setScene(nuevaEscena);
            stagePrincipal.setTitle(titulo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}