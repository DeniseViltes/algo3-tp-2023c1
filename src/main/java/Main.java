import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Inicio.fxml"));
        VBox view = loader.load();
        Controlador controlador = loader.getController();
        var scene = new Scene(view,1920,1080);
        stage.setScene(scene);
        stage.setTitle("Calendario-Algo3");
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("otros/style.css")).toExternalForm());
        controlador.init(stage, "serializa.cal");
        stage.show();
    }
}
