import java.time.LocalDateTime;

public class Alarma {
    private LocalDateTime fechaYHora;
    public enum Efecto{NOTIFICACION, SONIDO, MAIL};

    private Efecto efecto;

    public Alarma(LocalDateTime fechaYHora, Efecto efecto) {
        this.fechaYHora = fechaYHora;
        this.efecto = efecto;
    }
}
