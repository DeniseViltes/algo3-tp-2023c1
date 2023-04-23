import java.time.Duration;
import java.time.LocalDateTime;


public class Alarma {
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

    public Alarma copiarConNuevaReferencia(LocalDateTime nuevaFecha){
        var nueva = new Alarma(nuevaFecha);
        nueva.intervalo = this.intervalo;
        nueva.efecto = this.efecto;
        return nueva;
    }

}
