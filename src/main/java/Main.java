import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Inicio.fxml"));
        VBox view = loader.load();
        Controlador controlador = loader.getController();
        var scene = new Scene(view,1920,1080);
        stage.setScene(scene);
        stage.setTitle("Calendario-Algo3");
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        controlador.init();
        stage.show();
    }
}
