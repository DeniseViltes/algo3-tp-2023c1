import fechas.Dia;
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
import java.time.temporal.ChronoUnit;
import java.util.TreeSet;

public class ControladorEscenaDiaria implements ControladorTipoDeVista{

    private Controlador controlador;

    private String style_normal = null;

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
    private Label diaLabel;
    @FXML
    private VBox dia;

    @FXML
    void setSemana(ActionEvent event) {
        controlador.setSemana(event);
    }

    @FXML
    void setMes(ActionEvent event)  {
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


    /*
    Inicializa los parametros de la escena diaria
     */
    public void initEscenaDiaria(Controlador controlador, Calendario calendario) {

        this.controlador = controlador;
        dia_mostrado = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        label_mes.setText(dia_mostrado.getDayOfMonth() + " " + Mes.valueOf(dia_mostrado.getMonth().toString()).getMesEspanol() + " " + dia_mostrado.getYear());
        mostrarDia(dia_mostrado);
        marcarDia();
        actualizarCalendario(calendario);

        btn_hoy.setOnAction(actionEvent -> {
            dia_mostrado = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
            label_mes.setText(dia_mostrado.getDayOfMonth() + " " + Mes.valueOf(dia_mostrado.getMonth().toString()).getMesEspanol() + " " + dia_mostrado.getYear());
            mostrarDia(dia_mostrado);
            marcarDia();
            limpiarCalendario();
            actualizarCalendario(calendario);
        });

        btn_anterior.setOnAction(actionEvent -> {
            dia_mostrado = dia_mostrado.minusDays(1);
            label_mes.setText(dia_mostrado.getDayOfMonth() + " " + Mes.valueOf(dia_mostrado.getMonth().toString()).getMesEspanol() + " " + dia_mostrado.getYear());
            mostrarDia(dia_mostrado);
            marcarDiaNormal();
            if(dia_mostrado.getDayOfYear() == (LocalDateTime.now().getDayOfYear()))
                marcarDia();
            limpiarCalendario();
            actualizarCalendario(calendario);
        });

        btn_siguiente.setOnAction(actionEvent -> {
            dia_mostrado = dia_mostrado.plusDays(1);
            label_mes.setText(dia_mostrado.getDayOfMonth() + " " + Mes.valueOf(dia_mostrado.getMonth().toString()).getMesEspanol() + " " + dia_mostrado.getYear());
            mostrarDia(dia_mostrado);
            marcarDiaNormal();
            if(dia_mostrado.getDayOfYear() == (LocalDateTime.now().getDayOfYear()))
                marcarDia();
            limpiarCalendario();
            actualizarCalendario(calendario);
        });

        menuFecha.setText("Dia");
    }

    public void limpiarCalendario(){
        while(dia.getChildren().size() != 1){
            dia.getChildren().remove(dia.getChildren().size()-1);
        }
    }


    public void actualizarCalendario(Calendario calendario){

        TreeSet<ElementoCalendario> elementos = calendario.elementosEntreFechas(dia_mostrado, dia_mostrado.plusDays(1));

        for (ElementoCalendario elemento : elementos){
            if(elemento.isEsDeDiaCompleto()){
                dia.getChildren().add(1, setear_texto_dia_completo(elemento, elemento.tieneVencimiento()));
            } else{
                dia.getChildren().add(setear_texto(elemento, elemento.tieneVencimiento()));
            }

        }
    }


    public Node setear_texto_dia_completo(ElementoCalendario el, boolean tieneVencimiento){
        if(tieneVencimiento){
            Button btn = new Button();
            btn.setMinWidth(1000);
            btn.setPadding(new Insets(7));
            btn.setMinHeight(20);
            btn.setAlignment(Pos.CENTER_LEFT);
            btn.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: white; -fx-background-color: #7988c6;-fx-cursor: hand; ");
            btn.setText(el.getTitulo());
            btn.setOnAction(actionEvent -> {
                ControladorMostrarInformacion controlador = new ControladorMostrarInformacion();
                controlador.mostrar_informacion(this.controlador, this, el, btn);
            });
            return btn;
        }
        else{
            CheckBox btn = new CheckBox();
            btn.setMinWidth(1000);
            btn.setAlignment(Pos.CENTER_LEFT);
            btn.setMinHeight(20);
            btn.setPadding(new Insets(7));
            if(((Tarea) el).estaCompleta())
                btn.setSelected(true);
            btn.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: white; -fx-background-color: #1a73e8; -fx-cursor: hand; ");
            btn.setText(el.getTitulo());
            btn.setOnAction(actionEvent -> {
                ControladorMostrarInformacion controlador = new ControladorMostrarInformacion();
                controlador.mostrar_informacion(this.controlador, this, el, btn);
            });
            return btn;
        }
    }



    public Node setear_texto(ElementoCalendario el, boolean tieneVencimiento){
        if(tieneVencimiento){
            Button btn = new Button();
            btn.setMinWidth(1000);
            btn.setMinHeight(60);
            btn.setPadding(new Insets(7));
            btn.setStyle("-fx-cursor: hand; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: white; -fx-background-color: #7988c6;");
            btn.setAlignment(Pos.CENTER_LEFT);
            btn.setText(el.getTitulo() + '\n' + el.getFecha().getHour() + ":" + String.format("%02d", el.getFecha().getMinute()) + " - " + ((Evento)el).getFechaYHoraFinal().getHour() + ":" + String.format("%02d", ((Evento)el).getFechaYHoraFinal().getMinute()));
            btn.setOnAction(actionEvent -> {
                ControladorMostrarInformacion controlador = new ControladorMostrarInformacion();
                controlador.mostrar_informacion(this.controlador,this, el, btn);
            });
            return btn;
        }
        else{
            CheckBox btn = new CheckBox();
            btn.setMinWidth(1000);
            btn.setPadding(new Insets(7));
            btn.setMinHeight(60);
            btn.setAlignment(Pos.CENTER_LEFT);
            btn.setStyle("-fx-cursor: hand; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-text-fill: white; -fx-background-color: #1a73e8;");
            if(((Tarea) el).estaCompleta())
                btn.setSelected(true);
            btn.setText(el.getTitulo() + '\n' + el.getFecha().getHour() + ":" + String.format("%02d", el.getFecha().getMinute()));
            btn.setOnAction(actionEvent -> {
                ControladorMostrarInformacion controlador = new ControladorMostrarInformacion();
                controlador.mostrar_informacion(this.controlador, this,el, btn);
            });
            return btn;
        }
    }


    public void mostrarDia(LocalDateTime dia_mostrado) {
        diaLabel.setText(Dia.valueOf(dia_mostrado.getDayOfWeek().toString()).getDiaEspanol() + '\n' + dia_mostrado.getDayOfMonth());
    }

    /*
    Marca el dia elegido
     */
    public void marcarDia(){
        if(style_normal == null){
            style_normal = diaLabel.getStyle();
        }
        diaLabel.setStyle("-fx-background-radius: 80px; -fx-border-radius: 80px; -fx-text-fill: white; -fx-background-color: #1a73e8; ");
    }

    public void marcarDiaNormal() {
        diaLabel.setStyle(style_normal);
    }

    @FXML
    public void mostrarAyuda(ActionEvent event) {
        controlador.mostrarAyuda();
    }

}
