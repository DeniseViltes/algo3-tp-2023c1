import java.time.LocalDateTime;

public class InstanciaEvento {
    private final Evento evento;

    private LocalDateTime fecha;

    public InstanciaEvento(Evento evento,LocalDateTime fecha) {
        this.evento = evento;
        this.fecha = fecha;
    }

}
