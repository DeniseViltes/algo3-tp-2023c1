import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorEscenaTarea {
    private Tarea tarea;

    @FXML
    private TextField descripcionTarea;

    @FXML
    private DatePicker selecionadorFechaInicio;

    @FXML
    private TextField tituloTarea;

    @FXML
    void modificarTitulo(KeyEvent event) {
        tarea.setTitulo(tituloTarea.getText());
    }
    @FXML
    void volverAVistaPrincipal(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Intento1.fxml"));
        AnchorPane view = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.show();
    }
    void  initTarea (Tarea tarea){
        this.tarea = tarea;
    }
}
