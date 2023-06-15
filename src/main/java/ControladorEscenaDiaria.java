import Fechas.Dia;
import Fechas.Mes;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.TreeSet;

public class ControladorEscenaDiaria {

    private Controlador controlador;

    private String style_normal = null;

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
    private Label diaLabel;
    @FXML
    private VBox dia;

    @FXML
    void setSemana(ActionEvent event) throws IOException {
        controlador.setSemana(event);
    }

    @FXML
    void setDia(ActionEvent event) throws IOException {
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

    public void initEscenaDiaria(Controlador controlador, Calendario calendario) {

        this.controlador = controlador;
        dia_mostrado = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        label_mes.setText(dia_mostrado.getDayOfMonth() + " " + Mes.valueOf(dia_mostrado.getMonth().toString()).getMesEspañol() + " " + dia_mostrado.getYear());
        mostrarDia(dia_mostrado);
        marcarDia();
        actualizarCalendario(calendario, dia_mostrado);

        btn_hoy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dia_mostrado = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
                label_mes.setText(dia_mostrado.getDayOfMonth() + " " + Mes.valueOf(dia_mostrado.getMonth().toString()).getMesEspañol() + " " + dia_mostrado.getYear());
                mostrarDia(dia_mostrado);
                marcarDia();
                limpiarCalendario();
                actualizarCalendario(calendario, dia_mostrado);
            }
        });

        btn_anterior.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dia_mostrado = dia_mostrado.minusDays(1);
                label_mes.setText(dia_mostrado.getDayOfMonth() + " " + Mes.valueOf(dia_mostrado.getMonth().toString()).getMesEspañol() + " " + dia_mostrado.getYear());
                mostrarDia(dia_mostrado);
                marcarDiaNormal();
                if(dia_mostrado.getDayOfYear() == (LocalDateTime.now().getDayOfYear()))
                    marcarDia();
                limpiarCalendario();
                actualizarCalendario(calendario, dia_mostrado);
            }
        });

        btn_siguiente.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dia_mostrado = dia_mostrado.plusDays(1);
                label_mes.setText(dia_mostrado.getDayOfMonth() + " " + Mes.valueOf(dia_mostrado.getMonth().toString()).getMesEspañol() + " " + dia_mostrado.getYear());
                mostrarDia(dia_mostrado);
                marcarDiaNormal();
                if(dia_mostrado.getDayOfYear() == (LocalDateTime.now().getDayOfYear()))
                    marcarDia();
                limpiarCalendario();
                actualizarCalendario(calendario, dia_mostrado);
            }
        });

        menuFecha.setText("Dia");
    }

    public void limpiarCalendario(){
        for(int i = 1 ; i < dia.getChildren().size(); i++)
            dia.getChildren().remove(i);
    }

    public void actualizarCalendario(Calendario calendario, LocalDateTime dia_mostrar){

        TreeSet<ElementoCalendario> elementos = calendario.elementosEntreFechas(dia_mostrar, dia_mostrar.plusDays(1));

        for (ElementoCalendario elemento : elementos){
            Button btn = new Button();
            btn.setMinWidth(1000);
            btn.setAlignment(Pos.CENTER_LEFT);
            btn.setText(elemento.getTitulo() + '\n' + elemento.getFecha().getHour() + ":" + String.format("%02d", elemento.getFecha().getMinute()) + " - " + ((Evento) elemento).getFechaYHoraFinal().getHour() + ":" + String.format("%02d", ((Evento) elemento).getFechaYHoraFinal().getMinute()));
            dia.getChildren().add(btn);

        }


    }

    public void mostrarDia(LocalDateTime dia_mostrado) {
        diaLabel.setText(Dia.valueOf(dia_mostrado.getDayOfWeek().toString()).getDiaEspañol() + '\n' + dia_mostrado.getDayOfMonth());
    }

    public void marcarDia(){
        if(style_normal == null){
            style_normal = diaLabel.getStyle();
        }
        diaLabel.setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
    }

    public void marcarDiaNormal() {
        diaLabel.setStyle(style_normal);
    }
}
