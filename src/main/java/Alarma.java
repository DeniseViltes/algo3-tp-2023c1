import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;


public class Alarma implements Serializable {
    private  LocalDateTime fechaYHora;
    private Duration intervalo;
    private LocalDateTime referencia;
    private EfectoAlarma efecto;
    /*
    Crea una alarma a partir de una fecha de referencia que suena diez minutos antes de esta fecha
    con un efecto del tipo calendario
    */
    public Alarma(LocalDateTime fechaReferencia, Duration intervalo) {
        this.intervalo = intervalo;
        this.efecto = EfectoAlarma.NOTIFICACION;
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
            return efecto;
        return null;
    }

    public boolean esDeFechaAbsoluta(){
        return intervalo.isZero();
    }

    public Alarma copiarConNuevaReferencia(LocalDateTime nuevaFecha){
        var nueva = new Alarma(nuevaFecha,this.intervalo);
        nueva.efecto = this.efecto;
        return nueva;
    }

}
