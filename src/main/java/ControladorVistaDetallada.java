import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorVistaDetallada {

    private ElementoCalendario elementoCalendario;

    @FXML
    private Label label;
    @FXML
    void volverAVistaPrincipal(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Intento1.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    void initVista(ElementoCalendario elemento){
        this.elementoCalendario = elemento;
        label.setText(elemento.getTitulo());
    }
}
