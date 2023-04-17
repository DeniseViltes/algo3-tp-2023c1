import java.time.LocalDateTime;

public class InstanciaEvento {
    private final Evento evento;

    private LocalDateTime fecha;

    public InstanciaEvento(Evento evento) {
        this.evento = evento;
        this.fecha = evento.getFechaInicial();
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Evento getEvento() {
        return evento;
    }
}
