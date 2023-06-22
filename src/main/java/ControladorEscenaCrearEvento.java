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


public class ControladorEscenaCrearEvento {
    private Evento evento;
    private Calendario calendario;

    private ArrayList<Alarma> alarmas;

    @FXML
    private CheckBox checkDiaCompleto;

    @FXML
    private TextArea descripcionEvento;

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
    private Spinner<Integer> intervalo;


    private final DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter formatterFechaYHora = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");


    /*
    Inicializa los parametros del controlador de la escena para crear o modificar un evento
     */
    void initElemento(Calendario calendario, Evento evento) {
        this.evento = evento;
        this.calendario = calendario;
        tituloEvento.setText(evento.getTitulo());
        var fechaEvento = evento.getFecha();
        this.fechaIncio.setText(fechaEvento.toLocalDate().format(formatterFecha));
        this.horarioInicio.setText(fechaEvento.toLocalTime().format(formatterHora));
        alarmas = new ArrayList<>();

        var fechaFinalEvento = evento.getFechaYHoraFinal();
        this.fechaFinal.setText(fechaFinalEvento.toLocalDate().format(formatterFecha));
        this.horarioFinal.setText(fechaFinalEvento.toLocalTime().format(formatterHora));
        descripcionEvento.setText(evento.getDescripcion());

        var spinnerAlarmas = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 10);
        this.intervaloAlarma.setValueFactory(spinnerAlarmas);

        initChoiceBoxAlarma();
        for (Alarma value : evento.getAlarmas()) agregarBotonesDeAlarma(value);

        if (evento.isEsDeDiaCompleto()) {
            checkDiaCompleto.setSelected(true);
        }

        var spinnerIntervalo = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE);
        if (evento.tieneRepeticion()) {
            botonRepeticion.setSelected(true);
            intervalo.setDisable(false);
            spinnerIntervalo.setValue(evento.getIntervaloRepeticionDiaria());

        } else {
            botonRepeticion.setSelected(false);
            intervalo.setDisable(true);
        }
        this.intervalo.setValueFactory(spinnerIntervalo);
    }


    /*
    Inicializa los choice box de alarma
     */
    private void initChoiceBoxAlarma() {
        this.tipoDeIntervalo.getItems().add("minutos");
        this.tipoDeIntervalo.getItems().add("horas");
        this.tipoDeIntervalo.getItems().add("dias");
        this.tipoDeIntervalo.getItems().add("semanas");
        this.tipoDeIntervalo.setValue("minutos");

        this.tipoDeEfecto.getItems().add("Notificacion");
        this.tipoDeEfecto.setValue("Notificacion");
    }


    /*
    Guarda todos los cambios efectuados en la escena y los carga al evento
     */
    @FXML
    void guardar(ActionEvent event) {
        try {
            guardarCambios();
            Stage stage = (Stage) checkDiaCompleto.getScene().getWindow();
            stage.close();
        } catch (DateTimeParseException e) {
            cargarAlertaFormato();
        }
    }

    /*
    Activa los botones de la repeticion
     */
    @FXML
    void activarIntervalo() {
        intervalo.setDisable(!botonRepeticion.isSelected());
    }

    /*
    Cancela la creacion/modificacion del evento
     */
    @FXML
    void cancelar(ActionEvent event) {
        try {
            Stage stage = (Stage) checkDiaCompleto.getScene().getWindow();
            stage.close();
        } catch (DateTimeParseException e) {
            cargarAlertaFormato();
        }
    }


    void setearDeDiaCompleto() {
        if (checkDiaCompleto.isSelected())
            calendario.marcarDeDiaCompleto(evento);
        else {
            calendario.desmarcarDeDiaCompleto(evento);
        }
    }


    void modificarDescripcion() {
        if (descripcionEvento.getText() != null)
            calendario.modificarDescripcion(evento, descripcionEvento.getText());
    }

    void modificarTitulo() {
        if (tituloEvento.getText() != null)
            calendario.modificarTitulo(evento, tituloEvento.getText());
    }


    /*
    Modifica la fecha final del evento
     */
    void modificarFinal() {
        var fechaFinal = LocalDate.parse(this.fechaFinal.getText(), formatterFecha);
        var fechaInicial = LocalDate.parse(this.fechaIncio.getText(), formatterFecha);
        var horarioInicial = LocalTime.parse(this.horarioInicio.getText(), formatterHora);
        var horarioFinal = LocalTime.parse(this.horarioFinal.getText(), formatterHora);
        var duracion = Duration.between(fechaInicial.atTime(horarioInicial), fechaFinal.atTime(horarioFinal));
        calendario.modificarDuracion(evento, duracion);
    }

    private void cargarAlertaFormato() {
        String uno = "Para las fechas utilice el formato: yyyy-MM-dd ";
        String dos = "Para los horarios utilice : HH:mm";
        var alerta = new CreadorDeAlerta();
        alerta.mostrarAlerta("Formato de texto incorrecto", Arrays.asList(uno, dos));
    }

    void modificarInicio() {
        var fechaInicial = LocalDate.parse(this.fechaIncio.getText(), formatterFecha);
        var horarioInicial = LocalTime.parse(this.horarioInicio.getText(), formatterHora);
        calendario.modificarFecha(evento, fechaInicial.atTime(horarioInicial));
    }


    @FXML
    private CheckBox botonRepeticion;


    void tieneRepeticion() {
        if (!botonRepeticion.isSelected()) {
            intervalo.setDisable(true);
            calendario.eliminarRepeticion(evento);
        } else {
            intervalo.setDisable(false);
            calendario.agregarRepeticionDiariaEvento(evento);
        }
    }


    void guardarIntervalo() {
        if (intervalo.getValue() == null) {
            calendario.modificarIntervaloRepeticionDiaria(evento, 1);
        }
        calendario.modificarIntervaloRepeticionDiaria(evento, intervalo.getValue());
    }


    // Alarmas

    @FXML
    private Spinner<Integer> intervaloAlarma;

    @FXML
    private ChoiceBox<String> tipoDeIntervalo;

    @FXML
    private VBox vBoxAlarmas;

    /*
    Crea una alarma nueva
     */
    @FXML
    void agregarAlarma(ActionEvent event) {
        try {
        var fechaInicial = LocalDate.parse(this.fechaIncio.getText(), formatterFecha);
        var horarioInicial = LocalTime.parse(this.horarioInicio.getText(), formatterHora);
        var intervalo = convertirStringADuracion(tipoDeIntervalo.getValue(),intervaloAlarma.getValue());
        var efecto = tipoDeEfecto.getValue();
        var alarma = new Alarma(fechaInicial.atTime(horarioInicial), intervalo);
        alarma.setEfecto(EfectoAlarma.convertirStringAEfectoAlarma(efecto));
        agregarBotonesDeAlarma(alarma);
        }catch (DateTimeParseException e){
            cargarAlertaFormato();
        }
    }

    /*
    Agrega los botones para ver la alarma
     */

    void agregarBotonesDeAlarma(Alarma alarma){
        HBox contenedor = new HBox();
        var botonAlarma = nodoAlarma(alarma);
        var botonEliminar = nodoEliminar(alarma,contenedor);
        contenedor.getChildren().add(botonAlarma);
        contenedor.getChildren().add(botonEliminar);
        alarmas.add(alarma);
        vBoxAlarmas.getChildren().add(contenedor);
    }

    /*
    Crea  un label con los datos de la alarma
     */
    private Node nodoAlarma(Alarma alarma){
        var label = new Label();
        label.setMinWidth(180);
        label.setMinHeight(25);
        label.setPadding(new Insets(2,5,2,5));
        label.setAlignment(Pos.CENTER_LEFT);
        label.setText(alarma.getEfecto().toString() + ", "+ alarma.getFechaYHora().format(formatterFechaYHora));
        return label;
    }

    /*
    Crea boton que permite eliminar una alarma
     */
    private Node nodoEliminar(Alarma alarma, Pane contenedor) {
        Button boton = new Button("X");
        boton.setMinWidth(15);
        boton.setMinHeight(15);
        boton.setPadding(new Insets(2,5,2,5));
        boton.setOnAction(event -> {
            calendario.eliminarAlarma(evento,alarma);
            alarmas.remove(alarma);
            vBoxAlarmas.getChildren().remove(contenedor);
        });
        return boton;
    }

    /*
    Convierte el string a una duracion
     */
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

    /*
    Guarda todos los cambios efectuados en la escena
     */
    private void guardarCambios() throws DateTimeParseException{
        modificarFinal();
        modificarInicio();
        setearDeDiaCompleto();
        modificarDescripcion();
        modificarTitulo();
        tieneRepeticion();
        if(botonRepeticion.isSelected())
            guardarIntervalo();
        for(Alarma al : alarmas){
            calendario.agregarAlarma(evento, al.getIntervalo());
            calendario.modificarAlarmaEfecto(evento, al,EfectoAlarma.convertirStringAEfectoAlarma(al.getEfecto().toString()));
        }
    }



}
