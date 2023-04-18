import java.time.LocalDateTime;

public class Alarma {

    /*
    -Una fecha y hora absoluta
    -Un intervalo de tiempo relativo a la fecha y hora del evento/tarea (ej: “30 minutos antes”).

    como manejamos esto??
    */

    private final LocalDateTime fechaYHora;
    public enum Efecto{NOTIFICACION, SONIDO, MAIL}; //tendria que ser una clase aparte?

    private Efecto efecto;

    public Alarma(LocalDateTime fechaYHora, Efecto efecto) {
        this.fechaYHora = fechaYHora;
        this.efecto = efecto;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public Efecto sonar(LocalDateTime actual){
        if (actual.equals(this.fechaYHora))
            return efecto; //supongo que esto pasa cuando suena la alarma????
        return null;//se puede devolver null?
    }
}
