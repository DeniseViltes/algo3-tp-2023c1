import java.time.LocalDateTime;
import java.util.TreeMap;

public class InstanciaEvento {
    private final Evento evento;

    private LocalDateTime fecha;
    private TreeMap<LocalDateTime, Alarma> alarmasInstancia;

    public InstanciaEvento(Evento evento,LocalDateTime fecha) {
        this.evento = evento;
        this.fecha = fecha;
        this.alarmasInstancia = new TreeMap<>();
        cargarAlarmas();
    }

    public void cargarAlarmas(){
        var alarmasOriginales = evento.getAlarmas();
        for (Alarma i : alarmasOriginales.values()){
            i.setReferencia(fecha);
            alarmasInstancia.put(i.getFechaYHora(),i);
        }
    }

}
