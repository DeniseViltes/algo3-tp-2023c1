import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


import javafx.scene.Scene;



import javafx.scene.control.SplitMenuButton;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


import java.time.LocalDateTime;


import java.time.temporal.ChronoUnit;



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

        Scene scene = new Scene(view);
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

        Scene scene = new Scene(view);
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

        Scene scene = new Scene(view);
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
        loader.setLocation(getClass().getResource("/EscenaCrearEvento.fxml"));
        AnchorPane view = loader.load();
        final Stage stage = new Stage();
        Scene scene = new Scene(view);
        ControladorEscenaEvento controlador = loader.getController();

        controlador.initEvento(calendario,evento);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void crearTarea(ActionEvent event) throws IOException {
        var tarea = calendario.crearTarea();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/EscenaCrearTarea.fxml"));
        AnchorPane view = loader.load();
        final Stage stage = new Stage();
        Scene scene = new Scene(view);
        ControladorEscenaTarea controlador = loader.getController();
        controlador.initTarea(tarea);
        stage.setScene(scene);
        stage.show();
    }


    public void init(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/EscenaSemanal.fxml"));
        VBox view = loader.load();
        this.stage = stage;
        Scene scene = new Scene(view);
        inicializarCalendario();
        ControladorEscenaSemanal controlador = loader.getController();
        controlador.initEscenaSemanal(this, calendario);
        initListeners();
        stage.setScene(scene);
        stage.show();
    }

    private void initListeners(){
        calendario.agregarListener(() -> {
            try {
                ProcesadorDeArchivoCalendario.guardarCalendarioEnArchivo(calendario,"serializa.cal");
            } catch (IOException e) {
                throw new RuntimeException(e); //TODO agregar alerta
            }
        });
    }

    public void inicializarCalendario() {
        try {
            this.calendario = ProcesadorDeArchivoCalendario.leerCalendarioDeArchivo("serializa.cal");
        } catch (IOException | ClassNotFoundException e) {
            this.calendario = new Calendario();
        }

        Evento evento = calendario.crearEvento();
        LocalDateTime dia = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        System.out.println(dia);
        calendario.modificarFecha(evento, dia.plusHours(5));
        calendario.modificarTitulo(evento, "Se aprueba el TP");
        calendario.agregarRepeticionDiariaEvento(evento);

    }

}
