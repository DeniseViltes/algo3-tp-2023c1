import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class Controlador implements Initializable {
    private Calendario calendario;
    private final LocalDate hoy = LocalDateTime.now().toLocalDate();
    @FXML
    private ListView<ElementoCalendario> listaDeElementos;

    @FXML
    private Button masInfo;
    @FXML
    private MenuButton menuCrear;
    @FXML
    private Label labelFecha;

    @FXML
    private Label visualizacion;


    @FXML
    private Label labelFechaVisualizacion;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");



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
    }


    @FXML
    void crearEvento(ActionEvent event) throws IOException {
        var evento = calendario.crearEvento();
        listaDeElementos.getItems().add(evento);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/EscenaCrearEvento.fxml"));
        AnchorPane view = loader.load();
        Stage stage = (Stage) menuCrear.getScene().getWindow();
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
        Stage stage = (Stage) menuCrear.getScene().getWindow();
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.calendario = ProcesadorDeArchivoCalendario.leerCalendarioDeArchivo("serializa.cal");
        } catch (IOException | ClassNotFoundException e) {
            this.calendario = new Calendario();
        }

        var elementos = calendario.elementosEntreFechas(hoy.atStartOfDay(),hoy.atTime(LocalTime.MAX));

        listaDeElementos.getItems().addAll(elementos);
        listaDeElementos.setEditable(true);
        this.masInfo.setDisable(true);
        labelFechaVisualizacion.setText(hoy.getDayOfWeek().toString()+ ", "+hoy.getDayOfMonth()+" "+ hoy.getYear());
        labelFecha.setText(hoy.format(formatter));
        visualizacion.setText("Dia");
    }


    @FXML
    void cambiarAVistaSemanal(ActionEvent event) {
        var fechaInicial = fechaDeInicioDeLaSemana(hoy).atStartOfDay();
        var fechaFinal= fechaFinalDeLaSemana(hoy).atTime(LocalTime.MAX);
        actualizarVista(fechaInicial,fechaFinal);
        visualizacion.setText("Semana");
        labelFechaVisualizacion.setText(fechaInicial.getMonth().toString() +", "+ fechaInicial.getYear());
    }

    @FXML
    void cambiarAVistaDiaria(ActionEvent event) {
        actualizarVista(hoy.atStartOfDay(),hoy.atTime(LocalTime.MAX));
        visualizacion.setText("Dia");
        labelFechaVisualizacion.setText(hoy.getDayOfWeek().toString()+ ", "+hoy.getDayOfMonth()+" "+ hoy.getYear());
    }
    @FXML
    void cambiarAVistaMensual(ActionEvent event) {

        var fechaInicial = fechaInicioMensual(hoy).atStartOfDay();
        var fechaFinal=  fechaFinalMes(hoy).atTime(LocalTime.MAX);
        actualizarVista(fechaInicial,fechaFinal);
        visualizacion.setText("Mes");
        labelFechaVisualizacion.setText(fechaInicial.getMonth().toString() +", "+ fechaInicial.getYear());
    }

    private void actualizarVista(LocalDateTime inicio, LocalDateTime fin){
        listaDeElementos.getItems().clear();
        var elementos = calendario.elementosEntreFechas(inicio,fin);
        listaDeElementos.getItems().addAll(elementos);
    }


    @FXML
    void retroceder(ActionEvent event) {
        var inicio = LocalDate.parse(labelFecha.getText(),formatter);
        switch (visualizacion.getText()) {
            case "Dia" -> {
                inicio = inicio.minusDays(1);
                actualizarVista(inicio.atStartOfDay(), inicio.atTime(LocalTime.MAX));
                labelFecha.setText(inicio.format(formatter));
                labelFechaVisualizacion.setText(inicio.getDayOfWeek().toString()+ ", "+inicio.getDayOfMonth()+" "+ inicio.getYear());
            }
            case "Semana" -> {
                inicio = inicio.minusWeeks(1);
                actualizarVista(fechaDeInicioDeLaSemana(inicio).atStartOfDay(), fechaFinalDeLaSemana(inicio).atTime(LocalTime.MAX));
                labelFecha.setText(inicio.format(formatter));
                labelFechaVisualizacion.setText(inicio.getMonth().toString() +", "+ inicio.getYear());
            }
            case "Mes" -> {
                inicio = inicio.minusMonths(1);
                actualizarVista(fechaInicioMensual(inicio).atStartOfDay(), fechaFinalMes(inicio).atTime(LocalTime.MAX));
                labelFechaVisualizacion.setText(inicio.getMonth().toString() +", "+ inicio.getYear());
                labelFecha.setText(inicio.format(formatter));
            }
            default -> {
            }
        }
    }
    @FXML
    void avanzar(ActionEvent event) {
        var inicio = LocalDate.parse(labelFecha.getText(),formatter);
        switch (visualizacion.getText()) {
            case "Dia" -> {
                inicio = inicio.plusDays(1);
                actualizarVista(inicio.atStartOfDay(), inicio.atTime(LocalTime.MAX));
                labelFecha.setText(inicio.format(formatter));
                labelFechaVisualizacion.setText(inicio.getDayOfWeek().toString()+ ", "+inicio.getDayOfMonth()+" "+ inicio.getYear());
            }
            case "Semana" -> {
                inicio = inicio.plusWeeks(1);
                actualizarVista(fechaDeInicioDeLaSemana(inicio).atStartOfDay(), fechaFinalDeLaSemana(inicio).atTime(LocalTime.MAX));
                labelFecha.setText(inicio.format(formatter));
                labelFechaVisualizacion.setText(inicio.getMonth().toString() +", "+ inicio.getYear());
            }
            case "Mes" -> {
                inicio = inicio.plusMonths(1);
                actualizarVista(fechaInicioMensual(inicio).atStartOfDay(), fechaFinalMes(inicio).atTime(LocalTime.MAX));
                labelFechaVisualizacion.setText(inicio.getMonth().toString() +", "+ inicio.getYear());
                labelFecha.setText(inicio.format(formatter));
            }
            default -> {
            }
        }
    }


    private LocalDate fechaDeInicioDeLaSemana(LocalDate date){

        var dia = date.getDayOfWeek();
        return date.minusDays(dia.getValue());
    }
    private LocalDate fechaFinalDeLaSemana(LocalDate date){

        return fechaDeInicioDeLaSemana(date).plusDays(6);
    }
    private  LocalDate fechaInicioMensual(LocalDate date){
        return date.withDayOfMonth(1);
    }
    private LocalDate fechaFinalMes(LocalDate date){
        return date.withDayOfMonth(date.lengthOfMonth());
    }
}
