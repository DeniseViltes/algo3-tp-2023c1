import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;


public class Controlador implements Initializable {
    private Calendario calendario;
    /*@FXML
    private ListView<ElementoCalendario> listaDeElementos;

    @FXML
    private Button masInfo;*/
    @FXML
    private SplitMenuButton menuCrear;
/*
    @FXML
    void obtenerMasInfo(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/VistaDetalladaEvento.fxml"));
        AnchorPane view = loader.load();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(view);
        ControladorVistaDetallada controlador = loader.getController();
        var elemento = listaDeElementos.getSelectionModel().getSelectedItem();
        controlador.initVista(elemento);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void habilitarVistaDetallada(MouseEvent event) {
        this.masInfo.setDisable(false);
    }*/


    @FXML
    void crearEvento(ActionEvent event) throws IOException {
        var evento = calendario.crearEvento();
        //listaDeElementos.getItems().add(evento);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/EscenaCrearEvento.fxml"));
        AnchorPane view = loader.load();
        //Stage stage = (Stage) menuCrear.getScene().getWindow();
        final Stage stage = new Stage();
        Scene scene = new Scene(view);
        ControladorEscenaEvento controlador = loader.getController();

        controlador.initEvento(evento);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void crearTarea(ActionEvent event) throws IOException {
        var tarea = calendario.crearTarea();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/EscenaCrearTarea.fxml"));
        AnchorPane view = loader.load();
        //Stage stage = (Stage) menuCrear.getScene().getWindow();
        final Stage stage = new Stage();
        Scene scene = new Scene(view);
        ControladorEscenaTarea controlador = loader.getController();
        controlador.initTarea(tarea);
        stage.setScene(scene);
        stage.show();
    }


//    private void actualizarVista(){
//        var hoy = LocalDateTime.now().toLocalDate();
//        var elementos = calendario.elementosEntreFechas(hoy.atStartOfDay(),hoy.atTime(LocalTime.MAX));
//        listaDeElementos.getItems().addAll(elementos);
//    }
    public void init() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/EscenaSemanal.fxml"));
        VBox view = loader.load();
        Stage stage = (Stage) menuCrear.getScene().getWindow();
        Scene scene = new Scene(view);
        ControladorEscenaSemanal controlador = loader.getController();
        controlador.initEscenaSemanal(this);
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

        //listaDeElementos.getItems().addAll(elementos);
        //listaDeElementos.setEditable(true);
        //this.masInfo.setDisable(true);
    }
}
