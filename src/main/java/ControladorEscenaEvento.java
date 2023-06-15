
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;


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


    void  initEvento ( Calendario calendario,Evento evento){
        this.evento = evento;
        tituloEvento.setText(evento.getTitulo());
        selecionadorFechaInicio.setChronology(evento.getFecha().getChronology());
        selecionadorFechaFinal.setChronology(evento.getFechaYHoraFinal().getChronology());
    }
    @FXML
    void volverAVistaPrincipal(ActionEvent event) throws IOException {
        Stage stage = (Stage) selecionadorFechaFinal.getScene().getWindow();
        stage.close();
    }
    @FXML
    void modificarDescripcion(ActionEvent event) {
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
}
