import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

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

    /*
    defino que dos alarmas son iguales cuando tienen el mismo intervalo, suenan a la misma hora
    y tinen el mismo efecto
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alarma alarma)) return false;
        return Objects.equals(fechaYHora, alarma.fechaYHora) && Objects.equals(intervalo, alarma.intervalo) && Objects.equals(referencia, alarma.referencia) && efecto == alarma.efecto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechaYHora, intervalo, referencia, efecto);
    }
}
