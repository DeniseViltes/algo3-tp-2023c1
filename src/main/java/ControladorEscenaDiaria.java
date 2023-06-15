import Fechas.Dia;
import Fechas.Mes;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;

import java.io.IOException;
import java.time.LocalDateTime;

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
    private Label dia;

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

    public void initEscenaDiaria(Controlador controlador) {

        this.controlador = controlador;
        dia_mostrado = LocalDateTime.now();
        label_mes.setText(dia_mostrado.getDayOfMonth() + " " + Mes.valueOf(dia_mostrado.getMonth().toString()).getMesEspañol() + " " + dia_mostrado.getYear());
        mostrarDia(dia_mostrado);
        marcarDia();

        btn_hoy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dia_mostrado = LocalDateTime.now();
                label_mes.setText(dia_mostrado.getDayOfMonth() + " " + Mes.valueOf(dia_mostrado.getMonth().toString()).getMesEspañol() + " " + dia_mostrado.getYear());
                mostrarDia(dia_mostrado);
                marcarDia();
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
            }
        });

        menuFecha.setText("Dia");
    }

    public void mostrarDia(LocalDateTime dia_mostrado) {
        dia.setText(Dia.valueOf(dia_mostrado.getDayOfWeek().toString()).getDiaEspañol() + '\n' + dia_mostrado.getDayOfMonth());
    }

    public void marcarDia(){
        if(style_normal == null){
            style_normal = dia.getStyle();
        }
        dia.setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
    }

    public void marcarDiaNormal() {
        dia.setStyle(style_normal);
    }
}
