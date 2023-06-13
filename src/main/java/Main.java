import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Intento1.fxml"));
        AnchorPane view = loader.load();
        var scene = new Scene(view);
        stage.setScene(scene);
        stage.setTitle("Calendario-Algo3");
        stage.show();
    }
}
