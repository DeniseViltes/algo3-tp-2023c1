
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.time.Duration;


public class ControladorEscenaEvento{
    private Evento evento;
    private Calendario calendario;

    @FXML
    private TextField descripcionEvento;

    @FXML
    private TextField tituloEvento;


    @FXML
    private DatePicker selecionadorFechaFinal;

    @FXML
    private DatePicker selecionadorFechaInicio;
    @FXML
    private Spinner<Integer> cantRepeticiones;


    void  initEvento ( Calendario calendario,Evento evento){
        this.evento = evento;
        this.calendario =calendario;
        tituloEvento.setText(evento.getTitulo());
        selecionadorFechaInicio.setChronology(evento.getFecha().getChronology());
        selecionadorFechaFinal.setChronology(evento.getFechaYHoraFinal().getChronology());
        cantRepeticiones.setDisable(true);
    }
    @FXML
    void volverAVistaPrincipal(ActionEvent event){
        Stage stage = (Stage) selecionadorFechaFinal.getScene().getWindow();
        stage.close();
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
    void modificarFinal(ActionEvent event) {
        var duracion = Duration.between(selecionadorFechaFinal.getValue(),selecionadorFechaInicio.getValue());
        calendario.modificarDuracion(evento,duracion);
    }

    @FXML
    void modificarInicio(ActionEvent event) {
        calendario.modificarFecha(evento,selecionadorFechaInicio.getValue().atStartOfDay());
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
            calendario.agregarRepeticionDiariaEvento(evento);
        }
        calendario.modificarCantidadRepeticiones(evento,cantRepeticiones.getValue());
    }


}
