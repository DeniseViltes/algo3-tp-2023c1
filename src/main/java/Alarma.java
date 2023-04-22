import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Alarma {

    /*
    -Una fecha y hora absoluta
    -Un intervalo de tiempo relativo a la fecha y hora del evento/tarea (ej: “30 minutos antes”).

    como manejamos esto??
    */

    private  LocalDateTime fechaYHora;
    private Duration intervalo;
    private LocalDateTime referencia;
    private EfectoAlarma efecto;

    public Alarma(LocalDateTime fechaReferencia) {
        this.intervalo = Duration.ofMinutes(10); //intervalo default 10 min;
        this.efecto = EfectoAlarma.NOTIFICACION; //efecto default del tipo notificación
        this.referencia = fechaReferencia;
        fechaASonar();
    }

    private void fechaASonar(){

       this.fechaYHora = this.referencia.minus(this.intervalo);
    }

    public void setReferencia(LocalDateTime referencia) {
        this.referencia = referencia;
        fechaASonar();
    }

    public void setIntervalo(Duration intervalo) {
        this.intervalo = intervalo;
        fechaASonar();
    }

    public void setAlarmaAbsoluta(LocalDateTime fechaYHora){
        this.intervalo = Duration.ZERO;
        this.fechaYHora = fechaYHora;
    }
    public void setEfecto(EfectoAlarma efecto) {
        this.efecto = efecto;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public EfectoAlarma sonar(LocalDateTime actual){
        if (actual.equals(this.fechaYHora))
            return efecto; //supongo que esto pasa cuando suena la alarma????
        return null;//se puede devolver null?
    }

    public boolean esDeFechaAbsoluta(){
        return intervalo.isZero();
    }

}
