import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


import javafx.scene.Scene;



import javafx.scene.control.SplitMenuButton;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class Controlador {
    private Calendario calendario;

    public Stage stage;


    @FXML
    private SplitMenuButton menuCrear;

    @FXML
    private SplitMenuButton menuFecha;

    @FXML
    void setSemana(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/EscenaSemanal.fxml"));
        VBox view = loader.load();

        Scene scene = new Scene(view,600,400);
        ControladorEscenaSemanal controlador = loader.getController();
        menuFecha.setText("Semana");

        controlador.initEscenaSemanal(this, calendario);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void setDia(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/EscenaDiaria.fxml"));
        VBox view = loader.load();

        Scene scene = new Scene(view,600,400);
        ControladorEscenaDiaria controlador = loader.getController();


        controlador.initEscenaDiaria(this, calendario);
        stage.setScene(scene);
        menuFecha.setText("Dia");
        stage.show();
    }

    @FXML
    void setMes(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/EscenaMensual.fxml"));
        VBox view = loader.load();

        Scene scene = new Scene(view,600,400);
        ControladorEscenaMensual controlador = loader.getController();
        menuFecha.setText("Mes");

        controlador.initEscenaMensual(this, calendario);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void crearEvento(ActionEvent event) throws IOException {
        var evento = calendario.crearEvento();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/EscenaModificarEvento.fxml"));
        AnchorPane view = loader.load();
        final Stage stage = new Stage();
        Scene scene = new Scene(view,600,400);
        ControladorEscenaEvento controlador = loader.getController();

        controlador.initElemento(calendario,evento);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void crearTarea(ActionEvent event) throws IOException {
        var tarea = calendario.crearTarea();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/EscenaModificarTarea.fxml"));
        AnchorPane view = loader.load();
        final Stage stage = new Stage();
        Scene scene = new Scene(view,600,400);
        ControladorEscenaTarea controlador = loader.getController();
        controlador.initElemento(calendario,tarea);
        stage.setScene(scene);
        stage.show();
    }


    public void init(Stage stage, String pathArchivoCalendario) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/EscenaSemanal.fxml"));
        VBox view = loader.load();
        this.stage = stage;
        Scene scene = new Scene(view,600,400);
        inicializarCalendario( pathArchivoCalendario);
        ControladorEscenaSemanal controlador = loader.getController();
        controlador.initEscenaSemanal(this, calendario);
        initListeners(pathArchivoCalendario);
        stage.setScene(scene);
        stage.show();
    }

    private void initListeners(String fileName){
        calendario.agregarListener(() -> {
            try {
                ProcesadorDeArchivoCalendario.guardarCalendarioEnArchivo(calendario,fileName);
            } catch (IOException e) {
                throw new RuntimeException(e); //TODO agregar alerta
            }
        });
    }

    public void inicializarCalendario(String fileName) {
        try {
            this.calendario = ProcesadorDeArchivoCalendario.leerCalendarioDeArchivo(fileName);
        } catch (IOException | ClassNotFoundException e) {
            this.calendario = new Calendario();
        }
    }

}
