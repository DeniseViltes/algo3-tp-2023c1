
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


public class ControladorEscenaEvento{
    private Evento evento;

    @FXML
    private TextField descripcionEvento;

    @FXML
    private TextField tituloEvento;


    @FXML
    private DatePicker selecionadorFechaFinal;

    @FXML
    private DatePicker selecionadorFechaInicio;


    void  initEvento (Evento evento){
        this.evento = evento;
    }

    @FXML
    void volverAVistaPrincipal(ActionEvent event) throws IOException {
        Stage stage = (Stage) selecionadorFechaFinal.getScene().getWindow();
        stage.close();
    }
    @FXML
    void modificarDescripcion(ActionEvent event) {
        System.out.println("psa algo");

    }
    @FXML
    void modificarTitulo(KeyEvent event) {
        if(tituloEvento.getText()!= null)
            evento.setTitulo(tituloEvento.getText());
    }

}
