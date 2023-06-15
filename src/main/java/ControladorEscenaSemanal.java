import Fechas.Dia;
import Fechas.Mes;
import com.sun.javafx.scene.control.SelectedCellsMap;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.TreeSet;
import java.util.function.Predicate;

public class ControladorEscenaSemanal{

    private Controlador controlador;

    @FXML
    private Button btn_hoy;

    @FXML
    private MenuButton menuCrear;

    @FXML
    private MenuButton menuFecha;

    @FXML
    private Button btn_anterior;
    @FXML
    private Button btn_siguiente;
    @FXML
    private Label label_mes;
    @FXML
    private Label diaSabadoLabel;
    @FXML
    private VBox diaSabado;
    @FXML
    private Label diaDomingoLabel;
    @FXML
    private VBox diaDomingo;
    @FXML
    private VBox diaLunes;
    @FXML
    private Label diaLunesLabel;
    @FXML
    private VBox diaMartes;
    @FXML
    private Label diaMartesLabel;
    @FXML
    private Label diaMiercolesLabel;
    @FXML
    private VBox diaMiercoles;
    @FXML
    private Label diaJuevesLabel;
    @FXML
    private VBox diaJueves;
    @FXML
    private Label diaViernesLabel;
    @FXML
    private VBox diaViernes;

    @FXML
    void setSemana(ActionEvent event) throws IOException {
    }

    @FXML
    void setDia(ActionEvent event) throws IOException {
        controlador.setDia(event);
    }

    @FXML
    void setMes(ActionEvent event) throws IOException {
        controlador.setMes(event);
    }

    @FXML
    void crearEvento(ActionEvent event) throws IOException {
        controlador.crearEvento(event);
    }

    @FXML
    void crearTarea(ActionEvent event) throws IOException {
        controlador.crearTarea(event);
    }

    private LocalDateTime dia_mostrado;

    public void initEscenaSemanal(Controlador controlador, Calendario calendario) {

        this.controlador = controlador;

        label_mes.setText(Mes.valueOf(LocalDateTime.now().getMonth().toString()).getMesEspañol() + " " + LocalDateTime.now().getYear());

        dia_mostrado = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        marcarDiaActual();
        mostrarSemana(dia_mostrado);
        actualizarCalendario(calendario, dia_mostrado);
        btn_hoy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dia_mostrado = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
                label_mes.setText(Mes.valueOf(dia_mostrado.getMonth().toString()).getMesEspañol() + " " + dia_mostrado.getYear());
                mostrarSemana(dia_mostrado);
                marcarDiaActual();
                limpiarCalendario();
                actualizarCalendario(calendario, dia_mostrado);
            }
        });

        btn_anterior.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dia_mostrado = dia_mostrado.minusDays(7);
                label_mes.setText(Mes.valueOf(dia_mostrado.getMonth().toString()).getMesEspañol() + " " + dia_mostrado.getYear());
                mostrarSemana(dia_mostrado);
                marcarDiaNormal();
                if(dia_mostrado.getDayOfYear() == (LocalDateTime.now().getDayOfYear()))
                    marcarDiaActual();
                limpiarCalendario();
                actualizarCalendario(calendario, dia_mostrado);
            }
        });

        btn_siguiente.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dia_mostrado = dia_mostrado.plusDays(7);
                label_mes.setText(Mes.valueOf(dia_mostrado.getMonth().toString()).getMesEspañol() + " " + dia_mostrado.getYear());
                mostrarSemana(dia_mostrado);
                marcarDiaNormal();
                if(dia_mostrado.getDayOfYear() == (LocalDateTime.now().getDayOfYear()))
                    marcarDiaActual();
                limpiarCalendario();
                actualizarCalendario(calendario, dia_mostrado);
            }
        });

        menuFecha.setText("Semana");

    }

    public void limpiarCalendario(){
        while(diaSabado.getChildren().size() != 1){
            diaSabado.getChildren().remove(diaSabado.getChildren().size()-1);
        }
        while(diaDomingo.getChildren().size() != 1){
            diaDomingo.getChildren().remove(diaDomingo.getChildren().size()-1);
        }
        while(diaLunes.getChildren().size() != 1){
            diaLunes.getChildren().remove(diaLunes.getChildren().size()-1);
        }
        while(diaMartes.getChildren().size() != 1){
            diaMartes.getChildren().remove(diaMartes.getChildren().size()-1);
        }
        while(diaMiercoles.getChildren().size() != 1){
            diaMiercoles.getChildren().remove(diaMiercoles.getChildren().size()-1);
        }
        while(diaJueves.getChildren().size() != 1){
            diaJueves.getChildren().remove(diaJueves.getChildren().size()-1);
        }
        while(diaViernes.getChildren().size() != 1){
            diaViernes.getChildren().remove(diaViernes.getChildren().size()-1);
        }
    }

    public void actualizarCalendario(Calendario calendario, LocalDateTime dia){
        boolean esDomingo = false;
        while(esDomingo == false){
            if(dia.getDayOfWeek().toString() == "SUNDAY") {
                esDomingo = true;
            }
            else {
                dia = dia.minusDays(1);
            }
        }

        TreeSet<ElementoCalendario> domingo = calendario.elementosEntreFechas(dia, dia.plusDays(1));
        dia = dia.plusDays(1);
        TreeSet<ElementoCalendario> lunes = calendario.elementosEntreFechas(dia, dia.plusDays(1));
        dia = dia.plusDays(1);
        TreeSet<ElementoCalendario> martes = calendario.elementosEntreFechas(dia, dia.plusDays(1));
        dia = dia.plusDays(1);
        TreeSet<ElementoCalendario> miercoles = calendario.elementosEntreFechas(dia, dia.plusDays(1));
        dia = dia.plusDays(1);
        TreeSet<ElementoCalendario> jueves = calendario.elementosEntreFechas(dia, dia.plusDays(1));
        dia = dia.plusDays(1);
        TreeSet<ElementoCalendario> viernes = calendario.elementosEntreFechas(dia, dia.plusDays(1));
        dia = dia.plusDays(1);
        TreeSet<ElementoCalendario> sabado = calendario.elementosEntreFechas(dia, dia.plusDays(1));

        for (ElementoCalendario elemento : domingo){
            if(elemento.isEsDeDiaCompleto()){
                diaDomingo.getChildren().add(1, setear_texto_dia_completo(elemento, elemento.tieneVencimiento()));
            } else{
                diaDomingo.getChildren().add(setear_texto(elemento, elemento.tieneVencimiento()));
            }
        }
        for (ElementoCalendario elemento : lunes){
            if(elemento.isEsDeDiaCompleto()){
                diaLunes.getChildren().add(1, setear_texto_dia_completo(elemento, elemento.tieneVencimiento()));
            } else{
                diaLunes.getChildren().add(setear_texto(elemento, elemento.tieneVencimiento()));
            }
        }
        for (ElementoCalendario elemento : martes){
            if(elemento.isEsDeDiaCompleto()){
                diaMartes.getChildren().add(1, setear_texto_dia_completo(elemento, elemento.tieneVencimiento()));
            } else{
                diaMartes.getChildren().add(setear_texto(elemento, elemento.tieneVencimiento()));
            }
        }
        for (ElementoCalendario elemento : miercoles){
            if(elemento.isEsDeDiaCompleto()){
                diaMiercoles.getChildren().add(1, setear_texto_dia_completo(elemento, elemento.tieneVencimiento()));
            } else{
                diaMiercoles.getChildren().add(setear_texto(elemento, elemento.tieneVencimiento()));
            }
        }
        for (ElementoCalendario elemento : jueves){
            if(elemento.isEsDeDiaCompleto()){
                diaJueves.getChildren().add(1, setear_texto_dia_completo(elemento, elemento.tieneVencimiento()));
            } else{
                diaJueves.getChildren().add(setear_texto(elemento, elemento.tieneVencimiento()));
            }
        }
        for (ElementoCalendario elemento : viernes){
            if(elemento.isEsDeDiaCompleto()){
                diaViernes.getChildren().add(1, setear_texto_dia_completo(elemento, elemento.tieneVencimiento()));
            } else{
                diaViernes.getChildren().add(setear_texto(elemento, elemento.tieneVencimiento()));
            }
        }
        for (ElementoCalendario elemento : sabado){
            if(elemento.isEsDeDiaCompleto()){
                diaSabado.getChildren().add(1, setear_texto_dia_completo(elemento, elemento.tieneVencimiento()));
            } else{
                diaSabado.getChildren().add(setear_texto(elemento, elemento.tieneVencimiento()));
            }
        }

    }

    public Node setear_texto(ElementoCalendario el, boolean tieneVencimiento){
        if(tieneVencimiento){
            Button btn = new Button();
            btn.setMinWidth(150);
            btn.setMinHeight(60);
            btn.setPadding(new Insets(7));
            btn.setStyle("-fx-cursor: hand; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: white; -fx-background-color: #7988c6;");
            btn.setAlignment(Pos.CENTER_LEFT);
            btn.setText(el.getTitulo() + '\n' + el.getFecha().getHour() + ":" + String.format("%02d", el.getFecha().getMinute()) + " - " + ((Evento)el).getFechaYHoraFinal().getHour() + ":" + String.format("%02d", ((Evento)el).getFechaYHoraFinal().getMinute()));
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    mostrar_informacion(el, tieneVencimiento);
                }
            });
            return btn;
        }
        else{
            CheckBox btn = new CheckBox();
            btn.setMinWidth(150);
            btn.setPadding(new Insets(7));
            btn.setMinHeight(60);
            btn.setAlignment(Pos.CENTER_LEFT);
            btn.setStyle("-fx-cursor: hand; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: white; -fx-background-color: #1a73e8;");
            if(((Tarea) el).estaCompleta())
                btn.setSelected(true);
            btn.setText(el.getTitulo() + '\n' + el.getFecha().getHour() + ":" + String.format("%02d", el.getFecha().getMinute()));
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    mostrar_informacion(el, tieneVencimiento);
                }
            });
            return btn;
        }
    }

    public Node setear_texto_dia_completo(ElementoCalendario el, boolean tieneVencimiento){
        if(tieneVencimiento){
            Button btn = new Button();
            btn.setMinWidth(150);
            btn.setPadding(new Insets(7));
            btn.setMinHeight(20);
            btn.setAlignment(Pos.CENTER_LEFT);
            btn.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: white; -fx-background-color: #7988c6;-fx-cursor: hand; ");
            btn.setText(el.getTitulo());
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    mostrar_informacion(el, tieneVencimiento);
                }
            });
            return btn;
        }
        else{
            CheckBox btn = new CheckBox();
            btn.setMinWidth(150);
            btn.setAlignment(Pos.CENTER_LEFT);
            btn.setMinHeight(20);
            btn.setPadding(new Insets(7));
            if(((Tarea) el).estaCompleta())
                btn.setSelected(true);
            btn.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: white; -fx-background-color: #1a73e8; -fx-cursor: hand; ");
            btn.setText(el.getTitulo());
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    mostrar_informacion(el, tieneVencimiento);
                }
            });
            return btn;
        }
    }
    public void mostrar_informacion(ElementoCalendario el, boolean tieneVencimiento){
        final Stage dialog = new Stage();
        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.TOP_CENTER);

        Label titulo = new Label(el.getTitulo());
        titulo.setStyle("-fx-font-size: 30;");

        CheckBox todoElDia = new CheckBox("Todo el dia");
        if(el.isEsDeDiaCompleto())
            todoElDia.setSelected(true);

        Label descripcion = new Label(el.getDescripcion());
        descripcion.setStyle("-fx-font-size: 15;");

        dialogVbox.getChildren().add(titulo);

        //alarmas

        if(tieneVencimiento){
            LocalDateTime dia = el.getFecha();
            LocalDateTime diaFinal = ((Evento) el).getFechaYHoraFinal();
            Label fecha = new Label(dia.getDayOfMonth() + " " + Mes.valueOf(dia.getMonth().toString()).getMesEspañol() +" "+ dia.getYear() + " a "+diaFinal.getDayOfMonth() + " " + Mes.valueOf(diaFinal.getMonth().toString()).getMesEspañol() +" "+ diaFinal.getYear());
            fecha.setStyle("-fx-font-size: 15;");

            dialogVbox.getChildren().add(fecha);
            dialogVbox.getChildren().add(todoElDia);

            Label repeticion = new Label(((Evento) el).descripcionRepeticion());
            repeticion.setStyle("-fx-font-size: 15;");

            dialogVbox.getChildren().add(repeticion);
        }
        else{
            //fecha

            //dialogVbox.getChildren().add(fecha);
            dialogVbox.getChildren().add(todoElDia);

            //completa
        }

        dialogVbox.getChildren().add(descripcion);

        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setTitle(el.getTitulo());
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public void setearDia(String dia, int numero) {
        switch (dia){
            case "LUN":
                diaLunesLabel.setText(dia + '\n' + numero);
                break;
            case "MAR":
                diaMartesLabel.setText(dia + '\n' + numero);
                break;
            case "MIE":
                diaMiercolesLabel.setText(dia + '\n' + numero);
                break;
            case "JUE":
                diaJuevesLabel.setText(dia + '\n' + numero);
                break;
            case "VIE":
                diaViernesLabel.setText(dia + '\n' + numero);
                break;
            case "SAB":
                diaSabadoLabel.setText(dia + '\n' + numero);
                break;
            case "DOM":
                diaDomingoLabel.setText(dia + '\n' + numero);
                break;
        }
    }

    public void mostrarSemana(LocalDateTime dia){
        boolean esDomingo = false;
        while(esDomingo == false){
            if(dia.getDayOfWeek().toString() == "SUNDAY") {
                esDomingo = true;
            }
            else {
                dia = dia.minusDays(1);
            }
        }

        setearDia(Dia.valueOf(dia.getDayOfWeek().toString()).getDiaEspañol(), dia.getDayOfMonth());
        setearDia(Dia.valueOf(dia.plusDays(1).getDayOfWeek().toString()).getDiaEspañol(), dia.plusDays(1).getDayOfMonth());
        setearDia(Dia.valueOf(dia.plusDays(2).getDayOfWeek().toString()).getDiaEspañol(), dia.plusDays(2).getDayOfMonth());
        setearDia(Dia.valueOf(dia.plusDays(3).getDayOfWeek().toString()).getDiaEspañol(), dia.plusDays(3).getDayOfMonth());
        setearDia(Dia.valueOf(dia.plusDays(4).getDayOfWeek().toString()).getDiaEspañol(), dia.plusDays(4).getDayOfMonth());
        setearDia(Dia.valueOf(dia.plusDays(5).getDayOfWeek().toString()).getDiaEspañol(), dia.plusDays(5).getDayOfMonth());
        setearDia(Dia.valueOf(dia.plusDays(6).getDayOfWeek().toString()).getDiaEspañol(), dia.plusDays(6).getDayOfMonth());

    }

    public void marcarDiaActual(){
        String dia = Dia.valueOf(LocalDateTime.now().getDayOfWeek().toString()).getDiaEspañol();
        switch (dia){
            case "LUN":
                diaLunesLabel.setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
                break;
            case "MAR":
                diaMartesLabel.setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
                break;
            case "MIE":
                diaMiercolesLabel.setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
                break;
            case "JUE":
                diaJuevesLabel.setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
                break;
            case "VIE":
                diaViernesLabel.setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
                break;
            case "SAB":
                diaSabadoLabel.setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
                break;
            case "DOM":
                diaDomingoLabel.setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
                break;
        }
    }

    public void marcarDiaNormal(){
        String dia = Dia.valueOf(LocalDateTime.now().getDayOfWeek().toString()).getDiaEspañol();
        switch (dia){
            case "LUN":
                diaLunesLabel.setStyle(diaMartes.getStyle());
                break;
            case "MAR":
                diaMartesLabel.setStyle(diaLunes.getStyle());
                break;
            case "MIE":
                diaMiercolesLabel.setStyle(diaLunes.getStyle());
                break;
            case "JUE":
                diaJuevesLabel.setStyle(diaLunes.getStyle());
                break;
            case "VIE":
                diaViernesLabel.setStyle(diaLunes.getStyle());
                break;
            case "SAB":
                diaSabadoLabel.setStyle(diaLunes.getStyle());
                break;
            case "DOM":
                diaDomingoLabel.setStyle(diaLunes.getStyle());
                break;
        }
    }
}
