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
import java.time.*;


public class Controlador {
    private Calendario calendario;

    public Stage stage;

    private ControladorTipoDeVista controladorActual;
    private String sonidoPath; //TODO agregar pantalla principal

    @FXML
    private SplitMenuButton menuCrear;

    @FXML
    private SplitMenuButton menuFecha;

    public Calendario getCalendario(){
        return calendario;
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
        controladorActual = controlador;
        initListener(pathArchivoCalendario);
        this.sonidoPath = "src/main/resources/pacman-dies.mp3";
        iniciarTimer();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void setSemana(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/EscenaSemanal.fxml"));
        VBox view = loader.load();

        Scene scene = new Scene(view,600,400);
        ControladorEscenaSemanal controlador = loader.getController();
        menuFecha.setText("Semana");

        controlador.initEscenaSemanal(this, calendario);
        controladorActual = controlador;
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
        controladorActual = controlador;
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
        controladorActual = controlador;
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void crearEvento(ActionEvent event) throws IOException {
        var evento = calendario.crearEvento();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/VentanasExtra/EscenaModificarEvento.fxml"));
        AnchorPane view = loader.load();
        final Stage stageEvento = new Stage();
        stageEvento.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(view);
        ControladorEscenaCrearEvento controlador = loader.getController();

        controlador.initElemento(calendario,evento);
        stageEvento.setTitle("Nuevo evento");
        stageEvento.setScene(scene);

        setearStageSecundario(stageEvento);
    }

    @FXML
    void crearTarea(ActionEvent event) throws IOException {
        var tarea = calendario.crearTarea();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/VentanasExtra/EscenaModificarTarea.fxml"));
        AnchorPane view = loader.load();
        final Stage stageTarea = new Stage();
        stageTarea.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(view);
        ControladorEscenaCrearTarea controlador = loader.getController();
        controlador.initElemento(calendario,tarea);
        stageTarea.setScene(scene);
        setearStageSecundario(stageTarea);
    }



    private void initListener(String fileName){
        calendario.agregarListener(() -> {
            try {
                ProcesadorDeArchivoCalendario.guardarCalendarioEnArchivo(calendario,fileName);
                controladorActual.limpiarCalendario();
                controladorActual.actualizarCalendario(calendario);
            } catch (IOException e) {
                throw new RuntimeException(e); //TODO agregar alerta
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
                    try {
                        ejecutarAlarma(p);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                }
            };
            
       timer.start();
    }


    //puedo agregar esto en Efecto Alarma???? o mejor hacer otro enum?
    private void ejecutarAlarma(EfectoAlarma p) throws IOException {
        switch (p){
            case NOTIFICACION -> mostrarNotificacion();
            case SONIDO -> sonarAlarma();
            case MAIL -> mandarMail();
        }

    }
    private void mostrarNotificacion() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/VentanasExtra/Alarma.fxml"));
        VBox view = loader.load();
        final Stage stage = new Stage();
        Scene scene = new Scene(view);
        stage.setScene(scene);
        setearStageSecundario(stage);
    }

    private void sonarAlarma(){
        Media sonido = new Media(new File(sonidoPath).toURI().toString());//TODO probar alarmas con sonido
        MediaPlayer reproductor = new MediaPlayer(sonido);

        reproductor.play();

    }

    private void mandarMail(){
        System.out.println("Esto es un mail");//TODO ver como enviar un mail, hay que usar la API Spring????
    }

    private LocalDateTime getLocalDateTime(){
        return LocalDateTime.now();
    }




    private void setearStageSecundario(Stage stage){
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }


    //TODO agregar como funciona el calendario
}
