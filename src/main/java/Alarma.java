import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;


public class Alarma implements Serializable {

    //Fecha de la alarma
    private  LocalDateTime fechaYHora;

    //El tiempo entre la alarma y el elemento del calendario para el cual se utiliza la alarma.
    private Duration intervalo;

    // La fecha del elemento para el cual se utiliza la alarma.
    private LocalDateTime referencia;
    private EfectoAlarma efecto;
    /*
    Crea una alarma a partir de una fecha de referencia que suena un intervalo
     antes de esta fecha con un efecto del tipo Notificacion
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
    public Duration getIntervalo() {
        return intervalo;
    }

    public EfectoAlarma sonar(LocalDateTime actual){
        if (actual.equals(this.fechaYHora))
            return efecto;
        return null;
    }

    public EfectoAlarma getEfecto() {return efecto; }

    public boolean esDeFechaAbsoluta(){
        return intervalo.isZero();
    }

    // Crea una nueva alarma tomando el mismo intervalo y el mismo efecto que esta alarma.
    // Se crea en base a la fecha de referencia del elemento pasada como parametro.
    public Alarma copiarConNuevaReferencia(LocalDateTime nuevaFecha){
        var nueva = new Alarma(nuevaFecha,this.intervalo);
        nueva.efecto = this.efecto;
        return nueva;
    }

    public Duration getIntervalo() {
        return intervalo;
    }

    public EfectoAlarma getEfecto() {
        return efecto;
    }
}
