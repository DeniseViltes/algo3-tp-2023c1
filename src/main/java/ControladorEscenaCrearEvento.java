import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
import java.util.Arrays;


public class ControladorEscenaCrearEvento {
    private Evento evento;
    private Calendario calendario;

    @FXML
    private CheckBox checkDiaCompleto;
    @FXML
    private TextField descripcionEvento;

    @FXML
    private TextField tituloEvento;
    @FXML
    private TextField fechaFinal;

    @FXML
    private TextField fechaIncio;

    @FXML
    private TextField horarioInicio;
    @FXML
    private TextField horarioFinal;
    @FXML
    private ChoiceBox<String> tipoDeEfecto;


    @FXML
    private Spinner<Integer> cantRepeticiones;


    private final DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter formatterFechaYHora = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    void  initElemento ( Calendario calendario,Evento evento){
        this.evento = evento;
        this.calendario = calendario;
        tituloEvento.setText(evento.getTitulo());
        var fechaEvento = evento.getFecha();
        this.fechaIncio.setText(fechaEvento.toLocalDate().format(formatterFecha));
        this.horarioInicio.setText(fechaEvento.toLocalTime().format(formatterHora));

        var fechaFinalEvento = evento.getFechaYHoraFinal();
        this.fechaFinal.setText(fechaFinalEvento.toLocalDate().format(formatterFecha));
        this.horarioFinal.setText(fechaFinalEvento.toLocalTime().format(formatterHora));


        cantRepeticiones.setDisable(true);
        var spinnerRepeticiones = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,Integer.MAX_VALUE);
        this.cantRepeticiones.setValueFactory(spinnerRepeticiones);
        var spinnerAlarmas = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,Integer.MAX_VALUE,10);
        this.intervaloAlarma.setValueFactory(spinnerAlarmas);

        initChoiceBoxAlarma();
        for (Alarma value : evento.getAlarmas()) agregarBotonesDeAlarma(value);

        if(evento.isEsDeDiaCompleto()){
            checkDiaCompleto.setSelected(true);
        }
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



    @FXML
    void volverAVistaPrincipal(ActionEvent event){
        try {
            guardarCambios();
            Stage stage = (Stage) checkDiaCompleto.getScene().getWindow();
            stage.close();
        }catch (DateTimeParseException e){
            cargarAlertaFormato();
        }
    }


    void setearDeDiaCompleto() {
        if(checkDiaCompleto.isSelected())
            calendario.marcarDeDiaCompleto(evento);
        else{
            calendario.desmarcarDeDiaCompleto(evento);
        }
    }


    @FXML
    void modificarDescripcion(KeyEvent event) {
        if(descripcionEvento.getText()!= null)
            calendario.modificarDescripcion(evento,descripcionEvento.getText());
    }
    @FXML
    void modificarTitulo(KeyEvent event) {
        if(tituloEvento.getText()!= null)
            calendario.modificarTitulo(evento,tituloEvento.getText());
    }

    @FXML
    void modificarFinal(){
        var fechaFinal = LocalDate.parse(this.fechaFinal.getText(), formatterFecha);
        var fechaInicial = LocalDate.parse(this.fechaIncio.getText(), formatterFecha);
        var horarioInicial = LocalTime.parse(this.horarioInicio.getText(), formatterHora);
        var horarioFinal = LocalTime.parse(this.horarioFinal.getText(), formatterHora);
        var duracion = Duration.between(fechaInicial.atTime(horarioInicial),fechaFinal.atTime(horarioFinal));
        calendario.modificarDuracion(evento,duracion);
    }

    private void cargarAlertaFormato (){
        String uno= "Para las fechas utilice el formato: yyyy-MM-dd ";
        String dos ="Para los horarios utilice : HH:mm";
        var alerta = new CreadorDeAlerta();
        alerta.mostrarAlerta("Formato de texto incorrecto", Arrays.asList(uno,dos));

    }

    @FXML
    void modificarInicio(){
        var fechaInicial = LocalDate.parse(this.fechaIncio.getText(), formatterFecha);
        var horarioInicial = LocalTime.parse(this.horarioInicio.getText(), formatterHora);
        calendario.modificarFecha(evento, fechaInicial.atTime(horarioInicial));
    }



    @FXML
    private CheckBox botonRepeticion;

    @FXML
    void tieneRepeticion(ActionEvent event) {
        if(!botonRepeticion.isSelected()) {
            cantRepeticiones.setDisable(true);
            calendario.eliminarRepeticion(evento);
        }else {
            cantRepeticiones.setDisable(false);
            calendario.agregarRepeticionDiariaEvento(evento);
        }
    }

    @FXML
    void guardarCantRepeticiones(MouseEvent event) {
        if(cantRepeticiones.getValue() == null){
            calendario.modificarCantidadRepeticiones(evento,0); //TODO agregar alerta
        }
        calendario.modificarCantidadRepeticiones(evento,cantRepeticiones.getValue());
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
        try {
        guardarCambios();
        var intervalo = convertirStringADuracion(tipoDeIntervalo.getValue(),intervaloAlarma.getValue());
        var efecto = tipoDeEfecto.getValue();
        var alarma = calendario.agregarAlarma(evento,intervalo);
        calendario.modificarAlarmaEfecto(evento,alarma,EfectoAlarma.convertirStringAEfectoAlarma(efecto));
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
        boton.setMinWidth(25);
        boton.setMinHeight(25);
        boton.setPadding(new Insets(2,5,2,5));
        boton.setOnAction(event -> {
            calendario.eliminarAlarma(evento,alarma);
            vBoxAlarmas.getChildren().remove(contenedor);
        });
        return boton;
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
                //La semana no tiene duracion especifica en chronoUnit
                return Duration.of(intervalo*7, ChronoUnit.DAYS);
            }
        }
        return null;
    }

    private void guardarCambios() throws DateTimeParseException{
        modificarFinal();
        modificarInicio();
        setearDeDiaCompleto();
    }



}
