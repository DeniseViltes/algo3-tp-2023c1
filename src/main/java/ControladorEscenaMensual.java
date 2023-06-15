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
import java.time.Month;

public class ControladorEscenaMensual {

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
    private Label dia1;
    @FXML
    private Label dia2;
    @FXML
    private Label dia3;
    @FXML
    private Label dia4;
    @FXML
    private Label dia5;
    @FXML
    private Label dia6;
    @FXML
    private Label dia7;
    @FXML
    private Label dia8;
    @FXML
    private Label dia9;
    @FXML
    private Label dia10;
    @FXML
    private Label dia11;
    @FXML
    private Label dia12;
    @FXML
    private Label dia13;
    @FXML
    private Label dia14;
    @FXML
    private Label dia15;
    @FXML
    private Label dia16;
    @FXML
    private Label dia17;
    @FXML
    private Label dia18;
    @FXML
    private Label dia19;
    @FXML
    private Label dia20;
    @FXML
    private Label dia21;
    @FXML
    private Label dia22;
    @FXML
    private Label dia23;
    @FXML
    private Label dia24;
    @FXML
    private Label dia25;
    @FXML
    private Label dia26;
    @FXML
    private Label dia27;
    @FXML
    private Label dia28;
    @FXML
    private Label dia29;
    @FXML
    private Label dia30;
    @FXML
    private Label dia31;
    @FXML
    private Label dia32;
    @FXML
    private Label dia33;
    @FXML
    private Label dia34;
    @FXML
    private Label dia35;

    private Label [] dias;



    @FXML
    void setSemana(ActionEvent event) throws IOException {
        controlador.setSemana(event);
    }

    @FXML
    void setDia(ActionEvent event) throws IOException {
        controlador.setDia(event);
    }

    @FXML
    void setMes(ActionEvent event) throws IOException {
    }

    @FXML
    void crearEvento(ActionEvent event) throws IOException {
        controlador.crearEvento(event);
    }

    @FXML
    void crearTarea(ActionEvent event) throws IOException {
        controlador.crearTarea(event);
    }

    private int mes_mostrado;
    private int año_mostrado;

    public void initEscenaMensual(Controlador controlador) {

        this.controlador = controlador;

        label_mes.setText(Mes.valueOf(LocalDateTime.now().getMonth().toString()).getMesEspañol() + " " + LocalDateTime.now().getYear());
        mes_mostrado = LocalDateTime.now().getMonthValue();
        año_mostrado = LocalDateTime.now().getYear();
        inicializarDias();
        marcarDiaActual();
        mostrarMes(mes_mostrado, año_mostrado);
        btn_hoy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                label_mes.setText(Mes.valueOf(LocalDateTime.now().getMonth().toString()).getMesEspañol() + " " + LocalDateTime.now().getYear());
                mes_mostrado = LocalDateTime.now().getMonthValue();
                año_mostrado = LocalDateTime.now().getYear();
                marcarDiaActual();
                mostrarMes(mes_mostrado, año_mostrado);
            }
        });

        btn_anterior.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mes_mostrado = mes_mostrado - 1;
                if(mes_mostrado == 0) {
                    mes_mostrado = 12;
                    año_mostrado = año_mostrado - 1;
                }

                label_mes.setText(Mes.valueOf(Month.of(mes_mostrado).toString()).getMesEspañol() + " " + año_mostrado);
                mostrarMes(mes_mostrado, año_mostrado);
                marcarDiaNormal();
                if(mes_mostrado == (LocalDateTime.now().getMonthValue()))
                    marcarDiaActual();
            }
        });

        btn_siguiente.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mes_mostrado = mes_mostrado + 1;
                if(mes_mostrado == 13) {
                    mes_mostrado = 1;
                    año_mostrado = año_mostrado + 1;
                }

                label_mes.setText(Mes.valueOf(Month.of(mes_mostrado).toString()).getMesEspañol() + " " + año_mostrado);
                mostrarMes(mes_mostrado, año_mostrado);
                marcarDiaNormal();
                if(mes_mostrado == (LocalDateTime.now().getMonthValue()))
                    marcarDiaActual();
            }
        });

        menuFecha.setText("Mes");

    }

    public void inicializarDias(){
        dias = new Label[35];
        int i = 0;
        dias[i++] = dia1;
        dias[i++] = dia2;
        dias[i++] = dia3;
        dias[i++] = dia4;
        dias[i++] = dia5;
        dias[i++] = dia6;
        dias[i++] = dia7;
        dias[i++] = dia8;
        dias[i++] = dia9;
        dias[i++] = dia10;
        dias[i++] = dia11;
        dias[i++] = dia12;
        dias[i++] = dia13;
        dias[i++] = dia14;
        dias[i++] = dia15;
        dias[i++] = dia16;
        dias[i++] = dia17;
        dias[i++] = dia18;
        dias[i++] = dia19;
        dias[i++] = dia20;
        dias[i++] = dia21;
        dias[i++] = dia22;
        dias[i++] = dia23;
        dias[i++] = dia24;
        dias[i++] = dia25;
        dias[i++] = dia26;
        dias[i++] = dia27;
        dias[i++] = dia28;
        dias[i++] = dia29;
        dias[i++] = dia30;
        dias[i++] = dia31;
        dias[i++] = dia32;
        dias[i++] = dia33;
        dias[i++] = dia34;
        dias[i] = dia35;
    }

    public void mostrarMes(int mes_mostrado, int año_mostrado){
        boolean esDomingo = false;
        LocalDateTime dia = LocalDateTime.of(año_mostrado, mes_mostrado,1,1,1);
        int cant_dias = 0;
        while(esDomingo == false){
            if(dia.getDayOfWeek().toString() == "SUNDAY") {
                esDomingo = true;
            }
            else {
                dia = dia.minusDays(1);
                cant_dias++;
            }
        }

        int i = 0;
        dias[i++].setText("DOM" + '\n' + dia.getDayOfMonth());
        dia = dia.plusDays(1);
        dias[i++].setText("LUN" + '\n' + dia.getDayOfMonth());
        dia = dia.plusDays(1);
        dias[i++].setText("MAR" + '\n' + dia.getDayOfMonth());
        dia = dia.plusDays(1);
        dias[i++].setText("MIE" + '\n' + dia.getDayOfMonth());
        dia = dia.plusDays(1);
        dias[i++].setText("JUE" + '\n' + dia.getDayOfMonth());
        dia = dia.plusDays(1);
        dias[i++].setText("VIE" + '\n' + dia.getDayOfMonth());
        dia = dia.plusDays(1);
        dias[i].setText("SAB" + '\n' + dia.getDayOfMonth());
        dia = dia.plusDays(1);

        for(int j = 7; j < 35; j++){
            dias[j].setText(""+dia.getDayOfMonth());
            dia = dia.plusDays(1);
        }

    }

    public void marcarDiaActual(){
        boolean esDomingo = false;
        LocalDateTime dia = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),1, 1, 1);
        int cant_dias = 0;
        while(esDomingo == false){
            if(dia.getDayOfWeek().toString() == "SUNDAY") {
                esDomingo = true;
            }
            else {
                dia = dia.minusDays(1);
                cant_dias++;
            }
        }

        dias[(LocalDateTime.now().getDayOfMonth())-1+cant_dias].setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
    }

    public void marcarDiaNormal(){
        boolean esDomingo = false;
        LocalDateTime dia = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),1, 1, 1);
        int cant_dias = 0;
        while(esDomingo == false){
            if(dia.getDayOfWeek().toString() == "SUNDAY") {
                esDomingo = true;
            }
            else {
                dia = dia.minusDays(1);
                cant_dias++;
            }
        }

        dias[(LocalDateTime.now().getDayOfMonth())-1+cant_dias].setStyle(dias[(LocalDateTime.now().getDayOfMonth())+cant_dias].getStyle());
    }
}
