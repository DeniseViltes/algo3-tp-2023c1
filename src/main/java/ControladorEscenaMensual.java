import fechas.Mes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ControladorEscenaMensual implements ControladorTipoDeVista{

    public Controlador controlador;

    @FXML
    private Button btn_hoy;

    @FXML
    private MenuButton menuCrear;

    @FXML
    private MenuButton menuFecha;
    @FXML
    private Label titulo;

    @FXML
    private Button btn_anterior;
    @FXML
    private Button btn_siguiente;
    @FXML
    private Label label_mes;

    @FXML
    private Label dia1Label;
    @FXML
    private Label dia2Label;
    @FXML
    private Label dia3Label;
    @FXML
    private Label dia4Label;
    @FXML
    private Label dia5Label;
    @FXML
    private Label dia6Label;
    @FXML
    private Label dia7Label;
    @FXML
    private Label dia8Label;
    @FXML
    private Label dia9Label;
    @FXML
    private Label dia10Label;
    @FXML
    private Label dia11Label;
    @FXML
    private Label dia12Label;
    @FXML
    private Label dia13Label;
    @FXML
    private Label dia14Label;
    @FXML
    private Label dia15Label;
    @FXML
    private Label dia16Label;
    @FXML
    private Label dia17Label;
    @FXML
    private Label dia18Label;
    @FXML
    private Label dia19Label;
    @FXML
    private Label dia20Label;
    @FXML
    private Label dia21Label;
    @FXML
    private Label dia22Label;
    @FXML
    private Label dia23Label;
    @FXML
    private Label dia24Label;
    @FXML
    private Label dia25Label;
    @FXML
    private Label dia26Label;
    @FXML
    private Label dia27Label;
    @FXML
    private Label dia28Label;
    @FXML
    private Label dia29Label;
    @FXML
    private Label dia30Label;
    @FXML
    private Label dia31Label;
    @FXML
    private Label dia32Label;
    @FXML
    private Label dia33Label;
    @FXML
    private Label dia34Label;
    @FXML
    private Label dia35Label;
    @FXML
    private VBox dia1;
    @FXML
    private VBox dia2;
    @FXML
    private VBox dia3;
    @FXML
    private VBox dia4;
    @FXML
    private VBox dia5;
    @FXML
    private VBox dia6;
    @FXML
    private VBox dia7;
    @FXML
    private VBox dia8;
    @FXML
    private VBox dia9;
    @FXML
    private VBox dia10;
    @FXML
    private VBox dia11;
    @FXML
    private VBox dia12;
    @FXML
    private VBox dia13;
    @FXML
    private VBox dia14;
    @FXML
    private VBox dia15;
    @FXML
    private VBox dia16;
    @FXML
    private VBox dia17;
    @FXML
    private VBox dia18;
    @FXML
    private VBox dia19;
    @FXML
    private VBox dia20;
    @FXML
    private VBox dia21;
    @FXML
    private VBox dia22;
    @FXML
    private VBox dia23;
    @FXML
    private VBox dia24;
    @FXML
    private VBox dia25;
    @FXML
    private VBox dia26;
    @FXML
    private VBox dia27;
    @FXML
    private VBox dia28;
    @FXML
    private VBox dia29;
    @FXML
    private VBox dia30;
    @FXML
    private VBox dia31;
    @FXML
    private VBox dia32;
    @FXML
    private VBox dia33;
    @FXML
    private VBox dia34;
    @FXML
    private VBox dia35;

    private Label [] diasLabel;
    private VBox [] dias;



    @FXML
    void setSemana(ActionEvent event) {
        controlador.setSemana(event);
    }

    @FXML
    void setDia(ActionEvent event) {
        controlador.setDia(event);
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
    private int anio_mostrado;

    public void initEscenaMensual(Controlador controlador, Calendario calendario) {

        this.controlador = controlador;

        label_mes.setText(Mes.valueOf(LocalDateTime.now().getMonth().toString()).getMesEspanol() + " " + LocalDateTime.now().getYear());
        mes_mostrado = LocalDateTime.now().getMonthValue();
        anio_mostrado = LocalDateTime.now().getYear();
        inicializarDiasLabel();
        inicializarDias();
        marcarDiaActual();
        mostrarMes(mes_mostrado, anio_mostrado);
        actualizarCalendario(calendario);
        btn_hoy.setOnAction(actionEvent -> {
            label_mes.setText(Mes.valueOf(LocalDateTime.now().getMonth().toString()).getMesEspanol() + " " + LocalDateTime.now().getYear());
            mes_mostrado = LocalDateTime.now().getMonthValue();
            anio_mostrado = LocalDateTime.now().getYear();
            marcarDiaActual();
            mostrarMes(mes_mostrado, anio_mostrado);
            limpiarCalendario();
            actualizarCalendario(calendario);
        });

        btn_anterior.setOnAction(actionEvent -> {
            mes_mostrado = mes_mostrado - 1;
            if(mes_mostrado == 0) {
                mes_mostrado = 12;
                anio_mostrado = anio_mostrado - 1;
            }

            label_mes.setText(Mes.valueOf(Month.of(mes_mostrado).toString()).getMesEspanol() + " " + anio_mostrado);
            mostrarMes(mes_mostrado, anio_mostrado);
            marcarDiaNormal();
            if(mes_mostrado == (LocalDateTime.now().getMonthValue()))
                marcarDiaActual();
            limpiarCalendario();
            actualizarCalendario(calendario);
        });

        btn_siguiente.setOnAction(actionEvent -> {
            mes_mostrado = mes_mostrado + 1;
            if(mes_mostrado == 13) {
                mes_mostrado = 1;
                anio_mostrado = anio_mostrado + 1;
            }

            label_mes.setText(Mes.valueOf(Month.of(mes_mostrado).toString()).getMesEspanol() + " " + anio_mostrado);
            mostrarMes(mes_mostrado, anio_mostrado);
            marcarDiaNormal();
            if(mes_mostrado == (LocalDateTime.now().getMonthValue()))
                marcarDiaActual();
            limpiarCalendario();
            actualizarCalendario(calendario);
        });

        menuFecha.setText("Mes");

    }

    public void limpiarCalendario(){
        for(int i = 0; i < 35; i++){
            while(dias[i].getChildren().size() != 1){
                dias[i].getChildren().remove(dias[i].getChildren().size()-1);
            }
        }
    }

    public void actualizarCalendario(Calendario calendario){
        boolean esDomingo = false;
        LocalDateTime dia = LocalDateTime.of(anio_mostrado, mes_mostrado,1,1,1).truncatedTo(ChronoUnit.DAYS);

        while(!esDomingo){
            if(dia.getDayOfWeek().toString().equals("SUNDAY")) {
                esDomingo = true;
            }
            else {
                dia = dia.minusDays(1);

            }
        }
        List<TreeSet<ElementoCalendario>> elementos = new ArrayList<>();

        for(int i = 0; i < 35; i++){
            elementos.add(calendario.elementosEntreFechas(dia, dia.plusDays(1)));
            dia = dia.plusDays(1);
        }

        for(int i = 0; i < 35; i++){
            for (ElementoCalendario elemento : elementos.get(i)){
                if(elemento.isEsDeDiaCompleto()){
                    dias[i].getChildren().add(1, setear_texto_dia_completo(elemento, elemento.tieneVencimiento()));
                } else{
                    dias[i].getChildren().add(setear_texto(elemento, elemento.tieneVencimiento()));
                }
            }
        }

    }

    public Node setear_texto(ElementoCalendario el, boolean tieneVencimiento){
        if(tieneVencimiento){
            Button btn = new Button();
            btn.setMinWidth(150);
            btn.setMinHeight(15);
            btn.setPadding(new Insets(2,5,2,5));
            btn.setStyle("-fx-font-size: 10; -fx-cursor: hand; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: black; -fx-background-color: white;");
            btn.setAlignment(Pos.CENTER_LEFT);
            btn.setText(el.getFecha().getHour()  + " - " + ((Evento)el).getFechaYHoraFinal().getHour() + " " + el.getTitulo() );
            btn.setOnAction(actionEvent -> {
                ControladorMostrarInformacion controlador = new ControladorMostrarInformacion();
                controlador.mostrar_informacion(this.controlador,this, el, btn);
            });
            return btn;
        }
        else{
            CheckBox btn = new CheckBox();
            btn.setMinWidth(150);
            btn.setMinHeight(20);
            btn.setAlignment(Pos.CENTER_LEFT);
            btn.setPadding(new Insets(3,5,3,5));
            btn.setStyle("-fx-font-size: 10; -fx-cursor: hand; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: black; -fx-background-color: white;");
            if(((Tarea) el).estaCompleta())
                btn.setSelected(true);
            btn.setText(el.getFecha().getHour() + " " + el.getTitulo());
            btn.setOnAction(actionEvent -> {
                ControladorMostrarInformacion controlador = new ControladorMostrarInformacion();
                controlador.mostrar_informacion(this.controlador,this, el, btn);
            });
            return btn;
        }
    }

    public Node setear_texto_dia_completo(ElementoCalendario el, boolean tieneVencimiento){
        if(tieneVencimiento){
            Button btn = new Button();
            btn.setMinWidth(150);
            btn.setPadding(new Insets(1,5,1,5));
            btn.setMinHeight(15);
            btn.setAlignment(Pos.CENTER_LEFT);
            btn.setStyle("-fx-font-size: 10;-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: white; -fx-background-color: #7988c6;-fx-cursor: hand; ");
            btn.setText(el.getTitulo());
            btn.setOnAction(actionEvent -> {
                ControladorMostrarInformacion controlador = new ControladorMostrarInformacion();
                controlador.mostrar_informacion(this.controlador,this, el, btn);
            });
            return btn;
        }
        else{
            CheckBox btn = new CheckBox();
            btn.setMinWidth(150);
            btn.setAlignment(Pos.CENTER_LEFT);
            btn.setMinHeight(20);
            btn.setPadding(new Insets(3,5,3,5));
            if(((Tarea) el).estaCompleta())
                btn.setSelected(true);
            btn.setStyle("-fx-font-size: 10;-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: white; -fx-background-color: #1a73e8; -fx-cursor: hand; ");
            btn.setText(el.getTitulo());
            btn.setOnAction(actionEvent -> {
                ControladorMostrarInformacion controlador = new ControladorMostrarInformacion();
                controlador.mostrar_informacion(this.controlador,this, el, btn);
            });
            return btn;
        }
    }


    public void inicializarDiasLabel(){
        diasLabel = new Label[35];
        int i = 0;
        diasLabel[i++] = dia1Label;
        diasLabel[i++] = dia2Label;
        diasLabel[i++] = dia3Label;
        diasLabel[i++] = dia4Label;
        diasLabel[i++] = dia5Label;
        diasLabel[i++] = dia6Label;
        diasLabel[i++] = dia7Label;
        diasLabel[i++] = dia8Label;
        diasLabel[i++] = dia9Label;
        diasLabel[i++] = dia10Label;
        diasLabel[i++] = dia11Label;
        diasLabel[i++] = dia12Label;
        diasLabel[i++] = dia13Label;
        diasLabel[i++] = dia14Label;
        diasLabel[i++] = dia15Label;
        diasLabel[i++] = dia16Label;
        diasLabel[i++] = dia17Label;
        diasLabel[i++] = dia18Label;
        diasLabel[i++] = dia19Label;
        diasLabel[i++] = dia20Label;
        diasLabel[i++] = dia21Label;
        diasLabel[i++] = dia22Label;
        diasLabel[i++] = dia23Label;
        diasLabel[i++] = dia24Label;
        diasLabel[i++] = dia25Label;
        diasLabel[i++] = dia26Label;
        diasLabel[i++] = dia27Label;
        diasLabel[i++] = dia28Label;
        diasLabel[i++] = dia29Label;
        diasLabel[i++] = dia30Label;
        diasLabel[i++] = dia31Label;
        diasLabel[i++] = dia32Label;
        diasLabel[i++] = dia33Label;
        diasLabel[i++] = dia34Label;
        diasLabel[i] = dia35Label;
    }

    public void inicializarDias(){
        dias = new VBox[35];
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

    public void mostrarMes(int mes_mostrado, int anio_mostrado){
        boolean esDomingo = false;
        LocalDateTime dia = LocalDateTime.of(anio_mostrado, mes_mostrado,1,1,1).truncatedTo(ChronoUnit.DAYS);
        while(!esDomingo){
            if(dia.getDayOfWeek().toString().equals("SUNDAY")) {
                esDomingo = true;
            }
            else {
                dia = dia.minusDays(1);
            }
        }

        int i = 0;
        diasLabel[i++].setText("DOM" + '\n' + dia.getDayOfMonth());
        dia = dia.plusDays(1);
        diasLabel[i++].setText("LUN" + '\n' + dia.getDayOfMonth());
        dia = dia.plusDays(1);
        diasLabel[i++].setText("MAR" + '\n' + dia.getDayOfMonth());
        dia = dia.plusDays(1);
        diasLabel[i++].setText("MIE" + '\n' + dia.getDayOfMonth());
        dia = dia.plusDays(1);
        diasLabel[i++].setText("JUE" + '\n' + dia.getDayOfMonth());
        dia = dia.plusDays(1);
        diasLabel[i++].setText("VIE" + '\n' + dia.getDayOfMonth());
        dia = dia.plusDays(1);
        diasLabel[i].setText("SAB" + '\n' + dia.getDayOfMonth());
        dia = dia.plusDays(1);

        for(int j = 7; j < 35; j++){
            diasLabel[j].setText(" "+dia.getDayOfMonth());
            dia = dia.plusDays(1);
        }

    }

    public void marcarDiaActual(){
        boolean esDomingo = false;
        LocalDateTime dia = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),1, 1, 1).truncatedTo(ChronoUnit.DAYS);
        int cant_dias = 0;
        while(!esDomingo){
            if(dia.getDayOfWeek().toString().equals("SUNDAY")) {
                esDomingo = true;
            }
            else {
                dia = dia.minusDays(1);
                cant_dias++;
            }
        }

        diasLabel[(LocalDateTime.now().getDayOfMonth())-1+cant_dias].setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
    }

    public void marcarDiaNormal(){
        boolean esDomingo = false;
        LocalDateTime dia = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),1, 1, 1).truncatedTo(ChronoUnit.DAYS);
        int cant_dias = 0;
        while(!esDomingo){
            if(dia.getDayOfWeek().toString().equals("SUNDAY")) {
                esDomingo = true;
            }
            else {
                dia = dia.minusDays(1);
                cant_dias++;
            }
        }

        diasLabel[(LocalDateTime.now().getDayOfMonth())-1+cant_dias].setStyle(diasLabel[(LocalDateTime.now().getDayOfMonth())+cant_dias].getStyle());
    }


    @FXML
    public void mostrarAyuda(ActionEvent event) {
        controlador.mostrarAyuda();
    }


}
