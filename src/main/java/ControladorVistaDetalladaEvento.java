import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ControladorVistaDetalladaEvento extends  ControladorVistaDetallada{

    @FXML
    private Label descripcion;

    @FXML
    private Label fechaFinal;
    @FXML
    private Label titulo;

    @FXML
    private Label fechaInicio;
    @FXML
    private Label repeticion;

    @FXML
    private ListView<String> listaAlarmas;

    private final DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    @Override
    public void initVista(ElementoCalendario elemento) {
        Evento evento = (Evento) elemento;
        titulo.setText(evento.getTitulo());
        descripcion.setText(evento.getDescripcion());
        var inicio = evento.getFecha();
        var fin = evento.getFechaYHoraFinal();
        /*TODO
        Por ahora dejo asi las fechas, pero estaria bueno formatear la fecha
        como en google calendar
         */
        fechaInicio.setText(inicio.format(formatter));
        fechaFinal.setText(fin.format(formatter));
        if(evento.tieneRepeticion())
            repeticion.setText("Tiene repeticiones");//ver que hacemos aca
        var alarmas = evento.getAlarmas();

        listaAlarmas.getItems().addAll(formatearVistaAlarmas(alarmas));
    }
    public List<String> formatearVistaAlarmas(Collection<Alarma> alarmas){
        var iterador = alarmas.iterator();
        var elementos = new ArrayList<String>();
        while (iterador.hasNext()){
            var alarma = iterador.next();
            elementos.add(alarma.getFechaYHora().format(formatter));
        }
        return elementos;
    }
}
