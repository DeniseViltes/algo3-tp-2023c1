import Fechas.Dia;
import Fechas.Mes;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

public class ControladorEscenaSemanal{
    private Calendario calendario;

    private Controlador controlador;

    @FXML
    private Button btn_hoy;

    @FXML
    private MenuButton menuCrear;

    @FXML
    private Button btn_anterior;
    @FXML
    private Button btn_siguiente;
    @FXML
    private Label label_mes;
    @FXML
    private Label diaSabado;
    @FXML
    private Label diaDomingo;
    @FXML
    private Label diaLunes;
    @FXML
    private Label diaMartes;
    @FXML
    private Label diaMiercoles;
    @FXML
    private Label diaJueves;
    @FXML
    private Label diaViernes;

    @FXML
    void crearEvento(ActionEvent event) throws IOException {
        controlador.crearEvento(event);
    }

    @FXML
    void crearTarea(ActionEvent event) throws IOException {
        controlador.crearTarea(event);
    }

    private LocalDateTime dia_mostrado;

    public void initEscenaSemanal(Controlador controlador) {

        this.controlador = controlador;

        label_mes.setText(Mes.valueOf(LocalDateTime.now().getMonth().toString()).getMesEspañol() + " " + LocalDateTime.now().getYear());

        dia_mostrado = LocalDateTime.now();
        marcarDiaActual();
        mostrarSemana(dia_mostrado);
        btn_hoy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dia_mostrado = LocalDateTime.now();
                label_mes.setText(Mes.valueOf(dia_mostrado.getMonth().toString()).getMesEspañol() + " " + dia_mostrado.getYear());
                mostrarSemana(dia_mostrado);
                marcarDiaActual();
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
            }
        });

    }

    public void setearDia(String dia, int numero) {
        switch (dia){
            case "LUN":
                diaLunes.setText(dia + '\n' + numero);
                break;
            case "MAR":
                diaMartes.setText(dia + '\n' + numero);
                break;
            case "MIE":
                diaMiercoles.setText(dia + '\n' + numero);
                break;
            case "JUE":
                diaJueves.setText(dia + '\n' + numero);
                break;
            case "VIE":
                diaViernes.setText(dia + '\n' + numero);
                break;
            case "SAB":
                diaSabado.setText(dia + '\n' + numero);
                break;
            case "DOM":
                diaDomingo.setText(dia + '\n' + numero);
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
                diaLunes.setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
                break;
            case "MAR":
                diaMartes.setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
                break;
            case "MIE":
                diaMiercoles.setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
                break;
            case "JUE":
                diaJueves.setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
                break;
            case "VIE":
                diaViernes.setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
                break;
            case "SAB":
                diaSabado.setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
                break;
            case "DOM":
                diaDomingo.setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
                break;
        }
    }

    public void marcarDiaNormal(){
        String dia = Dia.valueOf(LocalDateTime.now().getDayOfWeek().toString()).getDiaEspañol();
        switch (dia){
            case "LUN":
                diaLunes.setStyle(diaMartes.getStyle());
                break;
            case "MAR":
                diaMartes.setStyle(diaLunes.getStyle());
                break;
            case "MIE":
                diaMiercoles.setStyle(diaLunes.getStyle());
                break;
            case "JUE":
                diaJueves.setStyle(diaLunes.getStyle());
                break;
            case "VIE":
                diaViernes.setStyle(diaLunes.getStyle());
                break;
            case "SAB":
                diaSabado.setStyle(diaLunes.getStyle());
                break;
            case "DOM":
                diaDomingo.setStyle(diaLunes.getStyle());
                break;
        }
    }
}
