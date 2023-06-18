
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;


public class ControladorEscenaCrearTarea {
    private Tarea tarea;
    private Calendario calendario;

    @FXML
    private CheckBox checkDiaCompleto;
    @FXML
    private TextField descripcionEvento;

    @FXML
    private TextField tituloEvento;

    @FXML
    private TextField fechaVencimiento;

    @FXML
    private TextField horarioVencimiento;

    private final DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");


   void initElemento(Calendario calendario, Tarea tarea){
       this.tarea = tarea;
       this.calendario = calendario;
       tituloEvento.setText(tarea.getTitulo());
       var fecha = tarea.getFecha();
       this.fechaVencimiento.setText(fecha.toLocalDate().format(formatterFecha));
       this.horarioVencimiento.setText(fecha.toLocalTime().format(formatterHora));

       var spinnerAlarmas = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,Integer.MAX_VALUE,10);
       this.intervaloAlarma.setValueFactory(spinnerAlarmas);

       initChoiceBoxAlarma();
       for (Alarma value : tarea.getAlarmas()) agregarBotonesDeAlarma(value);

       if(tarea.isEsDeDiaCompleto()){
           checkDiaCompleto.setSelected(true);
       }
   }





    @FXML
    void volverAVistaPrincipal(ActionEvent event) throws IOException {
        //para evitar problemas, los pongo aca en vez de guardarse automaticamente
        modificarInicio();
        Stage stage = (Stage) checkDiaCompleto.getScene().getWindow();
        stage.close();
    }

    @FXML
    void setearDeDiaCompleto(ActionEvent event) {
        if(checkDiaCompleto.isSelected())
            calendario.marcarDeDiaCompleto(tarea);
        else{
            calendario.desmarcarDeDiaCompleto(tarea);
        }
    }


    @FXML
    void modificarDescripcion(KeyEvent event) {
        if(descripcionEvento.getText()!= null)
            calendario.modificarDescripcion(tarea,descripcionEvento.getText());
    }
    @FXML
    void modificarTitulo(KeyEvent event) {
        if(tituloEvento.getText()!= null)
            calendario.modificarTitulo(tarea,tituloEvento.getText());
    }

    private void cargarAlertaFormato () throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/alertas/alertaFormatoFechas.fxml"));
        VBox view = loader.load();
        final Stage stage = new Stage();
        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.show();
        calendario.eliminarElementoCalendario(tarea);
    }

    @FXML
    void modificarInicio() throws IOException {
        try {
        var fechaInicial = LocalDate.parse(this.fechaVencimiento.getText(), formatterFecha);
        var horarioInicial = LocalTime.parse(this.horarioVencimiento.getText(), formatterHora);
        calendario.modificarFecha(tarea, fechaInicial.atTime(horarioInicial));
        }catch (DateTimeParseException e){
            cargarAlertaFormato();
        }
    }


    // Alarmas

    @FXML
    private Spinner<Integer> intervaloAlarma;

    @FXML
    private ChoiceBox<String> tipoDeIntervalo;

    @FXML
    private VBox vBoxAlarmas;


    @FXML
    void agregarAlarma(ActionEvent event) {
        var tipoIntervalo = convertir(tipoDeIntervalo.getValue());
        var intervalo = Duration.of(intervaloAlarma.getValue(),tipoIntervalo);
        var alarma = calendario.agregarAlarma(tarea,intervalo);
        agregarBotonesDeAlarma(alarma);
    }

    void agregarBotonesDeAlarma(Alarma alarma){
        HBox contenedor = new HBox();
        var botonAlarma = nodoAlarma(alarma);
        var botonEliminar = nodoEliminar(alarma,contenedor);
        contenedor.getChildren().add(botonAlarma);
        contenedor.getChildren().add(botonEliminar);


        vBoxAlarmas.getChildren().add(contenedor);
    }
    private Node nodoAlarma(Alarma alarma){
        var label = new Label();
        label.setMinWidth(180);
        label.setMinHeight(25);
        label.setPadding(new Insets(2,5,2,5));
        label.setAlignment(Pos.CENTER_LEFT);
        label.setText(alarma.getEfecto().toString() + ", "+ alarma.getFechaYHora().format(formatterFecha));
        return label;
    }

    private Node nodoEliminar(Alarma alarma, Pane contenedor) {
        Button boton = new Button("X");
        boton.setMinWidth(25);
        boton.setMinHeight(25);
        boton.setPadding(new Insets(2,5,2,5));
        boton.setOnAction(event -> {
            calendario.eliminarAlarma(tarea,alarma);
            vBoxAlarmas.getChildren().remove(contenedor);
        });
        return boton;
    }
    private void initChoiceBoxAlarma(){
        this.tipoDeIntervalo.getItems().add("minutos");
        this.tipoDeIntervalo.getItems().add("horas");
        this.tipoDeIntervalo.getItems().add("dias");
        this.tipoDeIntervalo.getItems().add("semanas");

        this.tipoDeIntervalo.setValue("minutos");
    }


    ChronoUnit convertir (String tipo ){
        switch (tipo){
            case "minutos" -> {
                return ChronoUnit.MINUTES;
            }
            case "horas" -> {
                return ChronoUnit.HOURS;
            }
            case "dias" -> {
                return ChronoUnit.DAYS;
            }
            case "semanas"-> {
                return ChronoUnit.WEEKS;
            }
        }
        return null;
    }


}
