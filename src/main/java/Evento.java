import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public class Evento extends ElementoCalendario {
    private Duration duracion;
    private Repeticion repeticion;

    /*
    Crea un evento nuevo apartir de una fecha dada, con una duración de 1 hora,
    este evento incia solo con un titulo y una alarma default (10 minutos antes del inicio)
     */
    public Evento(LocalDateTime inicioEvento) {
        super(inicioEvento);
        this.setTitulo("My Event");
        this.duracion =  Duration.ofHours(1);
        this.repeticion = null;
        agregarAlarma(inicioEvento, Duration.ofMinutes(10));
    }
    public void setDuracion(Duration duracionMinutos){
        this.duracion = duracionMinutos;
    }


    public LocalDateTime getFechaYHoraFinal(){
        return this.getFecha().plus(this.duracion);
    }

    @Override
    public void setFecha(LocalDateTime inicioEvento){
        super.setFecha(inicioEvento);
        if(isEsDeDiaCompleto())
            super.setFecha(inicioEvento.truncatedTo(ChronoUnit.DAYS));
    }
    /*
    Cuando se marca el evento como de dia completo, si el evento previamiente
    tenia una duración mayor a un dia, esto se mantiene.
    Ademas se eliminan todas las alarmas previas.
     */
    @Override
    public void setDeDiaCompleto(){
        super.setDeDiaCompleto();
        var nuevaInicial = getFecha().toLocalDate();
        setFecha(nuevaInicial.atStartOfDay());
        if (duracion.compareTo(Duration.ofDays(1))<0)
            this.duracion = Duration.ofHours(23).plusMinutes(59);
        else this.duracion = this.duracion.truncatedTo(ChronoUnit.DAYS).plusHours(23).plusMinutes(59);
    }
    @Override
    public void asignarDeFechaArbitraria(LocalDateTime nuevaInicial){

        super.asignarDeFechaArbitraria(nuevaInicial);
        this.duracion = this.duracion.truncatedTo(ChronoUnit.DAYS).plusMinutes(30);
        agregarAlarma(nuevaInicial, Duration.ofMinutes(10));

    }


    @Override
    public boolean comparar(ElementoCalendario elemento) {
        if(getTitulo().equals(elemento.getTitulo()) && getDescripcion().equals(elemento.getDescripcion()))
            return this.equals(elemento) || esUnaRepeticion((Evento) elemento);

        return false;
    }

    private boolean esUnaRepeticion(Evento elemento){
        var i = getFecha();
        while (tieneRepeticionEntreLosHorarios(i, elemento.getFecha().plusMinutes(1))){
            if(i.equals(elemento.getFecha()))
                return true;
            i = proximaRepeticion(i);
        }
        return i.equals(elemento.getFecha());
    }


    public void setRepeticionDiaria(Integer intervalo){
        this.repeticion = new RepeticionDiaria(intervalo);
    }
    public void setRepeticionSemanal(Set<DayOfWeek> dias){
        this.repeticion = new RepeticionSemanal(dias);
    }
    public void setRepeticionMensual(){
        this.repeticion = new RepeticionMensual();
    }
    public void setRepeticionAnual(){
        this.repeticion = new RepeticionAnual();
    }

    public void setRepeticionVencimiento(LocalDateTime vencimiento){
        if(repeticion == null)
                return;
        this.repeticion.setVencimiento(vencimiento);
    }

    public void setRepeticionCantidad(int cantidadRepeticiones){
        if(repeticion == null)
            return;
        this.repeticion.setCantidadRepeticiones(getFecha(), cantidadRepeticiones);
    }

    public void eliminarRepeticion (){
        this.repeticion = null;
    }


    public boolean tieneRepeticionEntreLosHorarios(LocalDateTime inicio, LocalDateTime fin){
        if(repeticion == null)
            return false;
        var fechaRepeticion = repeticion.Repetir(inicio);
        if(fechaRepeticion == null)
            return  false;
        return esIgualOEstaEntre(inicio,fin,fechaRepeticion);
    }

    private boolean esIgualOEstaEntre(LocalDateTime inicio, LocalDateTime fin, LocalDateTime t){
        return (t.equals(inicio) || t.isAfter(inicio)) && (t.equals(fin) || t.isBefore(fin));
    }


    public  LocalDateTime proximaRepeticion (LocalDateTime inicio){
        if (repeticion == null)
                return null;
        return repeticion.Repetir(inicio);
    }

    private void cargarAlarmasRepeticion(Evento evento){
        //evento.alarmas.clear();
        for (Alarma i : getAlarmas()){
            var nueva = i.copiarConNuevaReferencia(evento.getFecha());
            if(i.esDeFechaAbsoluta()) {
                var nuevaFechaAbs = i.getFechaYHora().plusHours(ChronoUnit.HOURS.between(getFecha(), evento.getFecha()));
                nueva.setAlarmaAbsoluta(nuevaFechaAbs);
            }
            evento.agregarAlarma(nueva);
        }
    }

    public  Evento crearRepeticion (LocalDateTime inicio){
        Evento repeticion = new Evento(inicio);
        repeticion.setTitulo(getTitulo());
        repeticion.setDescripcion(getDescripcion());
        repeticion.setDuracion(this.duracion);
        if(isEsDeDiaCompleto())
            repeticion.setDeDiaCompleto();
        cargarAlarmasRepeticion(repeticion);
        return repeticion;
    }


    public void agregarElementoAlSet(Set<ElementoCalendario> elementos, LocalDateTime inicio, LocalDateTime fin) {
        if (this.iniciaEntreLosHorarios(inicio, fin)){
            elementos.add(this);
        }
        var j = getFecha();
        while (this.tieneRepeticionEntreLosHorarios(j,fin)) {
            j = this.proximaRepeticion(j);
            Evento repeticion = crearRepeticion(j);
            if (repeticion.iniciaEntreLosHorarios(inicio, fin)){
                elementos.add(repeticion);
            }
        }
    }

    public LocalDateTime getRepeticionVencimiento() {
        return repeticion.getVencimiento();
    }
}
