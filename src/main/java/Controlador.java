import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;


public class Controlador {
    public Calendario calendario;

    public Stage stage;

    private final int alturaEscena = 1000;
    private final int anchoEscena = 1600;
    private ControladorTipoDeVista controladorActual;

    @FXML
    private SplitMenuButton menuCrear;

    @FXML
    private SplitMenuButton menuFecha;

    /*
    Inicializa el contrador principal con la escena de vista semanal
    ademas, incializa el calendario y subscribe al controlador como
    un observador de calendario
     */
    public void init(Stage stage, String pathArchivoCalendario){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/EscenaSemanal.fxml"));
            VBox view = loader.load();
            this.stage = stage;
            Scene scene = new Scene(view, anchoEscena, alturaEscena);
            inicializarCalendario(pathArchivoCalendario);
            ControladorEscenaSemanal controlador = loader.getController();
            controlador.initEscenaSemanal(this, calendario);
            controladorActual = controlador;
            initListener(pathArchivoCalendario);
            iniciarTimer();
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            cargarAlertaEscenaNoEncontrada();
        }
    }
    /*
    crea el formato de una alerta para cuando no se encuentra o hay problemas con la lectura
    de algun archivo de escenas
     */
    private void cargarAlertaEscenaNoEncontrada(){
        String uno= "Hubieron problemas para iniciar el calendario ";
        String dos ="Por favor, revise que esten todos los archivos";
        var alerta = new CreadorDeAlerta();
        alerta.mostrarAlerta("No se encontro el archivo de vista", Arrays.asList(uno,dos));
        stage.close();
    }
    /*
    Eliminar un elemento del calendario
     */
    public void eliminarElementoCalendario (ElementoCalendario elemento){
        calendario.eliminarElementoCalendario(elemento);
    }

    /*
    Cambia la escena actual, a la escena semanal
     */
    @FXML
    void setSemana(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/EscenaSemanal.fxml"));
            VBox view = loader.load();

            Scene scene = new Scene(view, anchoEscena, alturaEscena);
            ControladorEscenaSemanal controlador = loader.getController();
            menuFecha.setText("Semana");

            controlador.initEscenaSemanal(this, calendario);
            controladorActual = controlador;
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e){
            cargarAlertaEscenaNoEncontrada();
        }
    }

    /*
    Cambia la escena actual, a la escena diaria
     */
    @FXML
    void setDia(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/EscenaDiaria.fxml"));
            VBox view = loader.load();

            Scene scene = new Scene(view, anchoEscena, alturaEscena);
            ControladorEscenaDiaria controlador = loader.getController();
            controlador.initEscenaDiaria(this, calendario);
            controladorActual = controlador;
            stage.setScene(scene);
            menuFecha.setText("Dia");
            stage.show();
        }catch (IOException e){
            cargarAlertaEscenaNoEncontrada();
        }
    }
    /*
    Cambia la escena actual, a la escena mensual
     */
    @FXML
    void setMes(ActionEvent event)  {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/EscenaMensual.fxml"));
            VBox view = loader.load();

            Scene scene = new Scene(view, anchoEscena, alturaEscena);
            ControladorEscenaMensual controlador = loader.getController();
            menuFecha.setText("Mes");

            controlador.initEscenaMensual(this, calendario);
            controladorActual = controlador;
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            cargarAlertaEscenaNoEncontrada();
        }
    }

    /*
    Agrega un evento nuevo al calendario
     */
    @FXML
    void crearEvento(ActionEvent event){
        var evento = calendario.crearEvento();
       modificarEvento(evento);
    }
    /*
    Agrega una tarea nueva al calendario
     */
    @FXML
    void crearTarea(ActionEvent event){
        var tarea = calendario.crearTarea();
        modificarTarea(tarea);
    }

    /*
    Carga un stage nuevo para modificar un evento
     */
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
            stageEvento.setTitle("Modificar evento");
            stageEvento.setScene(scene);
            stageEvento.show();

        }catch (IOException e){
            cargarAlertaEscenaNoEncontrada();
        }
    }

    /*
    Cambia el estado (completo o incompleto) de una tarea
     */
    public void cambiarEstadoTarea(Tarea tarea, boolean estado){
        if (estado)
            calendario.marcarTareaCompleta(tarea);
        else calendario.marcarTareaIncompleta(tarea);
    }
    /*
    Cambia si es de dia completo o no, a un elemento
     */
    public void cambiarDiaCompleto(ElementoCalendario el, boolean estado){
        if (estado)
            calendario.marcarDeDiaCompleto(el);
        else calendario.desmarcarDeDiaCompleto(el);
    }
    /*
    Carga la escena para modificar una tarea
     */
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
            stageTarea.show();

        }
        catch (IOException e){
            cargarAlertaEscenaNoEncontrada();
        }
    }
    /*
    subscribe al controlador al calendario
     */
    private void initListener(String fileName){
        calendario.agregarListener(() -> {
            try {
                ProcesadorDeArchivoCalendario.guardarCalendarioEnArchivo(calendario,fileName);
                controladorActual.limpiarCalendario();
                controladorActual.actualizarCalendario(calendario);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    /*
    Carga el calendario, a partir de un archivo, o de no poderse esto, crea uno nuevo
     */
    public void inicializarCalendario(String fileName) {
        try {
            this.calendario = ProcesadorDeArchivoCalendario.leerCalendarioDeArchivo(fileName);
        } catch (IOException | ClassNotFoundException e) {
            this.calendario = new Calendario();
        }
    }
    /*
    Inicializa el timer para las alarmas
     */
    private void iniciarTimer(){
        var timer = new AnimationTimer() {
            @Override
            public void handle(long tiempo) {
                long deciSegundos = tiempo/100000000;
                if(deciSegundos%60 == 0){
                    LocalDateTime localDateTime = getLocalDateTime();
                    var p = calendario.sonarProximaAlarma(localDateTime.minusMinutes(1), localDateTime.plusMonths(1));
                    if (p != null && localDateTime.truncatedTo(ChronoUnit.MINUTES).equals(p.horarioProximaAlarma(localDateTime.minusMinutes(1)))) {
                        ControladorMostrarNotificacion controlador = new ControladorMostrarNotificacion();
                        controlador.mostrar_informacion(p);
                        calendario.eliminarAlarma(p, p.proximaAlarma(localDateTime.minusMinutes(1)));

                    }
                }

                }
            };
            
       timer.start();
    }


    private LocalDateTime getLocalDateTime(){
        return LocalDateTime.now();
    }


    /*
   Muestra un cartel de ayuda, en este caso, con una explicacion minima del funcionamiento
   del calendario
     */
    public void mostrarAyuda(){
        try {
           var ayuda = new FileReader("src/main/resources/otros/ayuda.txt");
            var lector = new LineNumberReader(new BufferedReader(ayuda));
            String renglon;
            var contenido = new ArrayList<String>();
            while((renglon= lector.readLine()) != null) {
                contenido.add(renglon);
            }
            VBox contenedor  = new VBox(10);
            contenedor.setAlignment(Pos.TOP_CENTER);
            for (String i : contenido) {
                contenedor.getChildren().add(new Text(i));
            }
            final Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(contenedor);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
