import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;


public class ControladorEscenaCrearTarea {
    private Tarea tarea;
    private Calendario calendario;

    private ArrayList<Alarma> alarmas;
    @FXML
    private CheckBox checkDiaCompleto;

    @FXML
    private TextArea descripcionEvento;

    @FXML
    private TextField tituloEvento;

    @FXML
    private TextField fechaVencimiento;

    @FXML
    private TextField horarioVencimiento;


    private final DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");

    private final DateTimeFormatter formatterFechaYHora = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");


   void initElemento(Calendario calendario, Tarea tarea){
       this.tarea = tarea;
       this.calendario = calendario;
       tituloEvento.setText(tarea.getTitulo());
       var fecha = tarea.getFecha();
       this.fechaVencimiento.setText(fecha.toLocalDate().format(formatterFecha));
       this.horarioVencimiento.setText(fecha.toLocalTime().format(formatterHora));

       alarmas = new ArrayList<>();
       descripcionEvento.setText(tarea.getDescripcion());

       var spinnerAlarmas = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,Integer.MAX_VALUE,10);
       this.intervaloAlarma.setValueFactory(spinnerAlarmas);

       initChoiceBoxAlarma();
       for (Alarma value : tarea.getAlarmas()) agregarBotonesDeAlarma(value);

       if(tarea.isEsDeDiaCompleto()){
           checkDiaCompleto.setSelected(true);
       }
   }

    @FXML
    void guardar(ActionEvent event){
        try {
            guardarCambios();
            Stage stage = (Stage) checkDiaCompleto.getScene().getWindow();
            stage.close();
        }catch (DateTimeParseException e){
            cargarAlertaFormato();
        }
    }

    @FXML
    void cancelar(ActionEvent event){
        try {
            Stage stage = (Stage) checkDiaCompleto.getScene().getWindow();
            stage.close();
        }catch (DateTimeParseException e){
            cargarAlertaFormato();
        }
    }


    void setearDeDiaCompleto() {
        if(checkDiaCompleto.isSelected())
            calendario.marcarDeDiaCompleto(tarea);
        else{
            calendario.desmarcarDeDiaCompleto(tarea);
        }

    }


    void modificarDescripcion() {
        if(descripcionEvento.getText()!= null)
            calendario.modificarDescripcion(tarea,descripcionEvento.getText());
    }

    void modificarTitulo() {
        if(tituloEvento.getText()!= null)
            calendario.modificarTitulo(tarea,tituloEvento.getText());
    }

    private void cargarAlertaFormato () {
        String uno= "Para las fechas utilice el formato: yyyy-MM-dd ";
        String dos ="Para los horarios utilice : HH:mm";
        var alerta = new CreadorDeAlerta();
        alerta.mostrarAlerta("Formato de texto incorrecto", Arrays.asList(uno,dos));

    }


    void modificarFecha(){
        var fechaInicial = LocalDate.parse(this.fechaVencimiento.getText(), formatterFecha);
        var horarioInicial = LocalTime.parse(this.horarioVencimiento.getText(), formatterHora);
        calendario.modificarFecha(tarea, fechaInicial.atTime(horarioInicial));
    }


    // Alarmas

    @FXML
    private Spinner<Integer> intervaloAlarma;

    @FXML
    private ChoiceBox<String> tipoDeIntervalo;

    @FXML
    private VBox vBoxAlarmas;
    @FXML
    private ChoiceBox<String> tipoDeEfecto;


    @FXML
    void agregarAlarma(ActionEvent event) {
        try {
            var fechaInicial = LocalDate.parse(this.fechaVencimiento.getText(), formatterFecha);
            var horarioInicial = LocalTime.parse(this.horarioVencimiento.getText(), formatterHora);
            var intervalo = convertirStringADuracion(tipoDeIntervalo.getValue(),intervaloAlarma.getValue());
            var efecto = tipoDeEfecto.getValue();
            var alarma = new Alarma(fechaInicial.atTime(horarioInicial), intervalo);
            alarma.setEfecto(EfectoAlarma.convertirStringAEfectoAlarma(efecto));
            agregarBotonesDeAlarma(alarma);
        }catch (DateTimeParseException e){
            cargarAlertaFormato();
        }
    }

    void agregarBotonesDeAlarma(Alarma alarma){
        HBox contenedor = new HBox();
        var botonAlarma = nodoAlarma(alarma);
        var botonEliminar = nodoEliminar(alarma,contenedor);
        contenedor.getChildren().add(botonAlarma);
        contenedor.getChildren().add(botonEliminar);
        alarmas.add(alarma);
        vBoxAlarmas.getChildren().add(contenedor);
    }
    private Node nodoAlarma(Alarma alarma){
        var label = new Label();
        label.setMinWidth(180);
        label.setMinHeight(25);
        label.setPadding(new Insets(2,5,2,5));
        label.setAlignment(Pos.CENTER_LEFT);
        label.setText(alarma.getEfecto().toString() + ", "+ alarma.getFechaYHora().format(formatterFechaYHora));
        return label;
    }

    private Node nodoEliminar(Alarma alarma, Pane contenedor) {
        Button boton = new Button("X");
        boton.setMinWidth(15);
        boton.setMinHeight(15);
        boton.setPadding(new Insets(2,5,2,5));
        boton.setOnAction(event -> {
            calendario.eliminarAlarma(tarea,alarma);
            alarmas.remove(alarma);
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

        this.tipoDeEfecto.getItems().add("Notificacion");
        this.tipoDeEfecto.getItems().add("Mail");
        this.tipoDeEfecto.getItems().add("Sonido");
        this.tipoDeEfecto.setValue("Notificacion");
    }



    Duration convertirStringADuracion (String tipo , long intervalo){
        switch (tipo){
            case "minutos" -> {
                return Duration.of(intervalo, ChronoUnit.MINUTES);
            }
            case "horas" -> {
                return Duration.of(intervalo, ChronoUnit.HOURS);
            }
            case "dias" -> {
                return Duration.of(intervalo, ChronoUnit.DAYS);
            }
            case "semanas"-> {
                return Duration.of(intervalo*7, ChronoUnit.DAYS);
            }
        }
        return null;
    }
    private void guardarCambios() throws DateTimeParseException{
        modificarFecha();
        setearDeDiaCompleto();
        modificarDescripcion();
        modificarTitulo();
        for(Alarma al : alarmas){
            calendario.agregarAlarma(tarea, al.getIntervalo());
            calendario.modificarAlarmaEfecto(tarea, al,EfectoAlarma.convertirStringAEfectoAlarma(al.getEfecto().toString()));
        }
    }


}
