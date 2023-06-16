import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

public class Tarea extends ElementoCalendario {
    private boolean completado;


    /*
    Crea una Tarea  incompleta a partir de una fecha dada, sin alarmas
     */
    public Tarea(LocalDateTime vencimiento) {
        super(vencimiento);
        this.setTitulo("My Task");
        this.completado = false;
    }
    @Override
    public void setDeDiaCompleto() {
        super.setDeDiaCompleto();
        var vencimiento = fechaAlFinalDelDia(super.getFecha());
        setFecha(vencimiento);
    }

    public void agregarElementoAlSet(Set<ElementoCalendario> elementos, LocalDateTime inicio, LocalDateTime fin) {
        if (this.iniciaEntreLosHorarios(inicio, fin))
            elementos.add(this);
    }

    public void completar(){
        this.completado = true;
    }
    public void descompletar(){
        this.completado = false;
    }


    public boolean estaCompleta (){
        return completado;
    }

    @Override
    public void setFecha(LocalDateTime vencimiento) {
        super.setFecha(vencimiento);
        if (super.isEsDeDiaCompleto())
            super.setFecha(fechaAlFinalDelDia(vencimiento));
    }

    private LocalDateTime fechaAlFinalDelDia(LocalDateTime fechaYHora){
        var fecha = fechaYHora.toLocalDate().atStartOfDay();
        var duracionUnDia = Duration.ofHours(23).plusMinutes(59);
        return fecha.plus(duracionUnDia);
    }

    public boolean tieneVencimiento(){
        return false;
    }


}
