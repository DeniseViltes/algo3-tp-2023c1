import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
        Stage stage = (Stage) selecionadorFechaInicio.getScene().getWindow();
        stage.close();
    }
    void  initTarea (Tarea tarea){
        this.tarea = tarea;
    }
}
