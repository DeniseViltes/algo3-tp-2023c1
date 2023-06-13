import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;


public class Controlador implements Initializable {

    private Calendario calendario = new Calendario();

    @FXML
    private ListView<ElementoCalendario> listaDeElementos;

    @FXML
    private Button masInfo;


    @FXML
    void obtenerMasInfo(ActionEvent event) {
        System.out.println("Armar Nueva Escena");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
