import Fechas.Dia;
import Fechas.Mes;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
        for(int i = 1 ; i < diaSabado.getChildren().size(); i++)
            diaSabado.getChildren().remove(i);
        for(int i = 1 ; i < diaViernes.getChildren().size(); i++)
            diaViernes.getChildren().remove(i);
        for(int i = 1 ; i < diaJueves.getChildren().size(); i++)
            diaJueves.getChildren().remove(i);
        for(int i = 1 ; i < diaMiercoles.getChildren().size(); i++)
            diaMiercoles.getChildren().remove(i);
        for(int i = 1 ; i < diaLunes.getChildren().size(); i++)
            diaLunes.getChildren().remove(i);
        for(int i = 1 ; i < diaMartes.getChildren().size(); i++)
            diaMartes.getChildren().remove(i);
        for(int i = 1 ; i < diaDomingo.getChildren().size(); i++)
            diaDomingo.getChildren().remove(i);
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
            Label lbl = new Label(elemento.getTitulo());
            diaDomingo.getChildren().add(lbl);
        }
        for (ElementoCalendario elemento : lunes){
            Label lbl = new Label(elemento.getTitulo());
            diaLunes.getChildren().add(lbl);
        }
        for (ElementoCalendario elemento : martes){
            Label lbl = new Label(elemento.getTitulo());
            diaMartes.getChildren().add(lbl);
        }
        for (ElementoCalendario elemento : miercoles){
            Label lbl = new Label(elemento.getTitulo());
            diaMiercoles.getChildren().add(lbl);
        }
        for (ElementoCalendario elemento : jueves){
            Label lbl = new Label(elemento.getTitulo());
            diaJueves.getChildren().add(lbl);
        }
        for (ElementoCalendario elemento : viernes){
            Label lbl = new Label(elemento.getTitulo());
            diaViernes.getChildren().add(lbl);
        }
        for (ElementoCalendario elemento : sabado){
            Label lbl = new Label(elemento.getTitulo());
            diaSabado.getChildren().add(lbl);
        }


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
