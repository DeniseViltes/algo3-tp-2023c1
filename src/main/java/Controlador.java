import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;


public class Controlador {
    private Calendario calendario;

    public Stage stage;

    private ControladorTipoDeVista controladorActual;
    private String sonidoPath; //TODO agregar pantalla principal

    @FXML
    private SplitMenuButton menuCrear;

    @FXML
    private SplitMenuButton menuFecha;

    public void init(Stage stage, String pathArchivoCalendario){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/EscenaSemanal.fxml"));
            VBox view = loader.load();
            this.stage = stage;
            Scene scene = new Scene(view);
            inicializarCalendario(pathArchivoCalendario);
            ControladorEscenaSemanal controlador = loader.getController();
            controlador.initEscenaSemanal(this, calendario);
            controladorActual = controlador;
            initListener(pathArchivoCalendario);
            this.sonidoPath = "src/main/resources/pacman-dies.mp3";
            iniciarTimer();
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        }catch (IOException e){
            cargarAlertaEscenaNoEncontrada();
        }
    }

    private void cargarAlertaEscenaNoEncontrada(){
        String uno= "Hubieron problemas para iniciar el calendario ";
        String dos ="Por favor, revise que esten todos los archivos";
        var alerta = new CreadorDeAlerta();
        alerta.mostrarAlerta("No se encontro el archivo de vista", Arrays.asList(uno,dos));
    }

    public void eliminarElementoCalendario (ElementoCalendario elemento){
        calendario.eliminarElementoCalendario(elemento);
    }
    @FXML
    void setSemana(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/EscenaSemanal.fxml"));
            VBox view = loader.load();

            Scene scene = new Scene(view, 1600, 1000);
            ControladorEscenaSemanal controlador = loader.getController();
            menuFecha.setText("Semana");

            controlador.initEscenaSemanal(this, calendario);
            controladorActual = controlador;
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        }
        catch (IOException e){
            cargarAlertaEscenaNoEncontrada();
        }
    }

    @FXML
    void setDia(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/EscenaDiaria.fxml"));
            VBox view = loader.load();

            Scene scene = new Scene(view, 1600, 1000);
            ControladorEscenaDiaria controlador = loader.getController();
            controlador.initEscenaDiaria(this, calendario);
            controladorActual = controlador;
            stage.setScene(scene);
            stage.setFullScreen(true);
            menuFecha.setText("Dia");
            stage.show();
        }catch (IOException e){
            cargarAlertaEscenaNoEncontrada();
        }
    }

    @FXML
    void setMes(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/EscenaMensual.fxml"));
            VBox view = loader.load();

            Scene scene = new Scene(view, 1600, 1000);
            ControladorEscenaMensual controlador = loader.getController();
            menuFecha.setText("Mes");

            controlador.initEscenaMensual(this, calendario);
            controladorActual = controlador;
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        }catch (IOException e){
            cargarAlertaEscenaNoEncontrada();
        }
    }


    @FXML
    void crearEvento(ActionEvent event){
        var evento = calendario.crearEvento();
       modificarEvento(evento);
    }

    @FXML
    void crearTarea(ActionEvent event){
        var tarea = calendario.crearTarea();
        modificarTarea(tarea);
    }


    public void modificarEvento(Evento evento){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/VentanasExtra/EscenaModificarEvento.fxml"));
            AnchorPane view = loader.load();
            final Stage stageEvento = new Stage();
            stageEvento.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(view);
            ControladorEscenaCrearEvento controlador = loader.getController();

            controlador.initElemento(calendario, evento);
            stageEvento.setTitle("Nuevo evento");
            stageEvento.setScene(scene);

            setearStageSecundario(stageEvento);
        }catch (IOException e){
            cargarAlertaEscenaNoEncontrada();
        }
    }


    public void cambiarEstadoTarea(Tarea tarea, boolean estado){
        if (estado)
            calendario.marcarTareaCompleta(tarea);
        else calendario.marcarTareaIncompleta(tarea);
    }
    public void modificarTarea(Tarea tarea) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/VentanasExtra/EscenaModificarTarea.fxml"));
            AnchorPane view = loader.load();
            final Stage stageTarea = new Stage();
            stageTarea.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(view);
            ControladorEscenaCrearTarea controlador = loader.getController();
            controlador.initElemento(calendario, tarea);
            stageTarea.setScene(scene);
            setearStageSecundario(stageTarea);
        }
        catch (IOException e){
            cargarAlertaEscenaNoEncontrada();
        }
    }

    private void initListener(String fileName){
        calendario.agregarListener(() -> {
            try {
                ProcesadorDeArchivoCalendario.guardarCalendarioEnArchivo(calendario,fileName);
                controladorActual.limpiarCalendario();
                controladorActual.actualizarCalendario(calendario);
            } catch (IOException e) {
                throw new RuntimeException(e); //no tendira que tirar ninguna alerta
            }
        });
    }

    public void inicializarCalendario(String fileName) {
        try {
            this.calendario = ProcesadorDeArchivoCalendario.leerCalendarioDeArchivo(fileName);
        } catch (IOException | ClassNotFoundException e) {
            this.calendario = new Calendario(); //TODO ver si esto va aca o en el Main
        }
    }

    private void iniciarTimer(){
        var timer = new AnimationTimer() {
            @Override
            public void handle(long tiempo) {
                LocalDateTime localDateTime = getLocalDateTime();
                var p = calendario.sonarProximaAlarma(localDateTime.minusSeconds(1), localDateTime.plusMinutes(1));
                if (p != null) {
                    ejecutarAlarma(p);
                }
                }
            };
            
       timer.start();
    }


    private void ejecutarAlarma(EfectoAlarma p) {
        switch (p){
            case NOTIFICACION -> mostrarNotificacion();
            case SONIDO -> sonarAlarma();
            case MAIL -> mandarMail();
        }

    }
    private void mostrarNotificacion(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/VentanasExtra/Alarma.fxml"));
            VBox view = loader.load();
            final Stage stage = new Stage();
            Scene scene = new Scene(view);
            stage.setScene(scene);
            setearStageSecundario(stage);
        }
        catch (IOException e){
            cargarAlertaEscenaNoEncontrada();
        }
    }

    private void sonarAlarma(){
        Media sonido = new Media(new File(sonidoPath).toURI().toString());//TODO probar alarmas con sonido
        MediaPlayer reproductor = new MediaPlayer(sonido);

        reproductor.play();

    }

    private void mandarMail(){
        cargarAlertaMail();
    }


    private void cargarAlertaMail(){
        String uno= "Todavia no esta disponible la opcion de enviar mails";
        String dos ="Pruebe con otro efecto";
        var alerta = new CreadorDeAlerta();
        alerta.mostrarAlerta("No es posible enviar mails aun", Arrays.asList(uno,dos));
    }

    private LocalDateTime getLocalDateTime(){
        return LocalDateTime.now();
    }


    private void setearStageSecundario(Stage stage){
        this.stage.toBack();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }


    //TODO agregar como funciona el calendario
}
