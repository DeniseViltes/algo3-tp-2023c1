import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;


public class Controlador implements Initializable {
    private Calendario calendario;
    @FXML
    private ListView<ElementoCalendario> listaDeElementos;

    @FXML
    private Button masInfo;

    @FXML
    void obtenerMasInfo(ActionEvent event) {
        System.out.println("Armar Nueva Escena");
    }

    @FXML
    private MenuButton menuCrear;


    @FXML
    void crearEvento(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EscenaCrearEvento.fxml"));
        AnchorPane view = loader.load();

        Stage stage = (Stage) menuCrear.getScene().getWindow();
        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.calendario = ProcesadorDeArchivoCalendario.leerCalendarioDeArchivo("serializa.cal");
        } catch (IOException | ClassNotFoundException e) {
            this.calendario = new Calendario();
        }

        var hoy = LocalDateTime.now().toLocalDate();
        var elementos = calendario.elementosEntreFechas(hoy.atStartOfDay(),hoy.atTime(LocalTime.MAX));

        listaDeElementos.getItems().addAll(elementos);
    }
}
