
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;


public class ControladorEscenaEvento{
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
    private Spinner<Integer> cantRepeticiones;


    private final DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");


    void  initEvento ( Calendario calendario,Evento evento){
        this.evento = evento;
        this.calendario =calendario;
        tituloEvento.setText(evento.getTitulo());
        var fechaEvento = evento.getFecha();
        this.fechaIncio.setText(fechaEvento.toLocalDate().format(formatterFecha));
        this.horarioInicio.setText(fechaEvento.toLocalTime().format(formatterHora));

        var fechaFinalEvento = evento.getFechaYHoraFinal();
        this.fechaFinal.setText(fechaFinalEvento.toLocalDate().format(formatterFecha));
        this.horarioFinal.setText(fechaFinalEvento.toLocalTime().format(formatterHora));


        cantRepeticiones.setDisable(true);
        var spinnerRepeticiones = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,Integer.MAX_VALUE,0);
        this.cantRepeticiones.setValueFactory(spinnerRepeticiones);
        var spinnerAlarmas = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,Integer.MAX_VALUE,10);
        this.intervaloAlarma.setValueFactory(spinnerAlarmas);

        initChoiceBoxAlarma();
        var alarma = evento.getAlarmas().iterator();
        if(alarma.hasNext()) {
            var botonAlarma = nodoAlarma(alarma.next());
            vBoxAlarmas.getChildren().add(botonAlarma);
        }

    }

    private void initChoiceBoxAlarma(){
        this.tipoDeIntervalo.getItems().add("minutos");
        this.tipoDeIntervalo.getItems().add("horas");
        this.tipoDeIntervalo.getItems().add("dias");
        this.tipoDeIntervalo.getItems().add("semanas");
    }

    //Scene dialogScene = new Scene(dialogVbox, 300, 200);

    private Node nodoAlarma(Alarma alarma){
        Button btn = new Button();
        btn.setMinWidth(200);
        btn.setMinHeight(20);
        btn.setPadding(new Insets(2,5,2,5));
        btn.setAlignment(Pos.CENTER_LEFT);
        //Por ahora solo notificacion
        btn.setText(alarma.getEfecto().toString() + ", "+ alarma.getFechaYHora().toString());
        return btn;
    }

    @FXML
    void volverAVistaPrincipal(ActionEvent event) throws IOException {
        //para evitar problemas, los pongo aca en vez de guardarse automaticamente
        modificarFinal();
        modificarInicio();
        Stage stage = (Stage) checkDiaCompleto.getScene().getWindow();
        stage.close();
    }

    @FXML
    void setearDeDiaCompleto(ActionEvent event) {
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
    void modificarFinal() throws IOException {

        try{
        var fechaFinal = LocalDate.parse(this.fechaFinal.getText(), formatterFecha);
        var fechaInicial = LocalDate.parse(this.fechaIncio.getText(), formatterFecha);
        var horarioInicial = LocalTime.parse(this.horarioInicio.getText(), formatterHora);
        var horarioFinal = LocalTime.parse(this.horarioFinal.getText(), formatterHora);
        var duracion = Duration.between(fechaFinal.atTime(horarioFinal),fechaInicial.atTime(horarioInicial));
        calendario.modificarDuracion(evento,duracion);

        }
        catch (DateTimeParseException e){
            cargarAlertaFormato();
        }

    }

    private void cargarAlertaFormato () throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/alertaFormatoFechas.fxml"));
        VBox view = loader.load();
        final Stage stage = new Stage();
        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.show();
        calendario.eliminarElementoCalendario(evento);
    }

    @FXML
    void modificarInicio() throws IOException {
        try {
        var fechaInicial = LocalDate.parse(this.fechaIncio.getText(), formatterFecha);
        var horarioInicial = LocalTime.parse(this.horarioInicio.getText(), formatterHora);
        calendario.modificarFecha(evento, fechaInicial.atTime(horarioInicial));
        }catch (DateTimeParseException e){
            cargarAlertaFormato();
        }
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
        var tipoIntervalo = convertir(tipoDeIntervalo.getValue());
        var intervalo = Duration.of(intervaloAlarma.getValue(),tipoIntervalo);
        var alarma = calendario.agregarAlarma(evento,intervalo);
        var botonAlarma = nodoAlarma(alarma);
        vBoxAlarmas.getChildren().add(botonAlarma);
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
