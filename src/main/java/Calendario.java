import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Calendario {
    private final TreeSet<ElementoCalendario> elementosCalendario;

    public Calendario() {
        this.elementosCalendario = new TreeSet<>(new OrdenarElementosPorHorario());
    }

    private LocalDateTime ahoraDefault (){
        var ahora = LocalDateTime.now();
        ahora = ahora.truncatedTo(ChronoUnit.HOURS);
        return ahora.plusHours(1);
    }
    public InstanciaEvento crearEvento() {
        Evento evento = new Evento(ahoraDefault());
        this.elementosCalendario.add(evento);
        return new InstanciaEvento(evento,evento.getFecha());
    }
    public Tarea crearTarea() {
        Tarea tarea = new Tarea(ahoraDefault());
        this.elementosCalendario.add(tarea);
        return tarea;
    }
    public void eliminarElementoCalendario(ElementoCalendario elementoCalendario) {
        elementosCalendario.remove(elementoCalendario);
    }

    public void eliminarElementoCalendario(InstanciaEvento instanciaEvento) {
        eliminarElementoCalendario(instanciaEvento.getEvento());
    }

    //Para tarea muestra vencimiento y para evento muestra la incial
    public LocalDateTime verFechaYHora(ElementoCalendario elemento){
        return  elemento.getFecha();
    }

    public void modificarTitulo(ElementoCalendario elemento, String titulo) {
        if (elemento != null && titulo != null)
            elemento.setTitulo(titulo);
    }

    public void modificarDescripcion(ElementoCalendario elemento, String descripcion) {
        if (elemento != null && descripcion != null)
            elemento.setDescripcion(descripcion);
    }

    //En el caso de ser una tarea setea la fecha de vencimiento, y si es un evento, setea el inicio
    public void modificarFecha(ElementoCalendario elemento, LocalDateTime inicioEvento) {
        if (elemento != null && inicioEvento != null)
            elemento.setFecha(inicioEvento);
    }

    public void marcarDeDiaCompleto(ElementoCalendario elementoCalendario){
        elementoCalendario.setDeDiaCompleto();
    }

    public void desmarcarDeDiaCompleto(ElementoCalendario elementoCalendario){
        var fecha = elementoCalendario.getFecha();
        elementoCalendario.asignarDeFechaArbitraria(fecha);
    }

    public void modificarDuracion(Evento evento, Duration duracionMinutos) {
        if (evento != null && duracionMinutos != null)
            evento.setDuracion(duracionMinutos);
    }


    public void modificarAlarmaIntervalo(ElementoCalendario elemento,Alarma alarma,Duration intervalo) {
        elemento.modificarIntervaloAlarma(alarma,intervalo);
    }

    public void modificarAlarmaFechaAbsoluta(ElementoCalendario elemento, Alarma alarma, LocalDateTime fechaYhora){
        elemento.modificarFechaAbsolutaAlarma(alarma,fechaYhora);
    }

    public void eliminarAlarma(ElementoCalendario elemento, Alarma alarma){
        elemento.eliminarAlarma(alarma);
    }
    public void  agregarRepeticionAnualEvento (InstanciaEvento instancia){
        var evento = instancia.getEvento();
        evento.setRepeticionAnual();
    }

    public void  agregarRepeticionMensualEvento (InstanciaEvento instancia){
        var evento = instancia.getEvento();
        evento.setRepeticionMensual();
    }
    public void  agregarRepeticionSemanalEvento (InstanciaEvento instancia){
        var evento = instancia.getEvento();
        var dia = instancia.getFecha();
        var dias = EnumSet.noneOf(DayOfWeek.class);
        dias.add(dia.getDayOfWeek());
        evento.setRepeticionSemanal(dias);
    }

    public void modificarDiasRepeticionSemanal(InstanciaEvento instancia, Set<DayOfWeek> dias){
        var evento = instancia.getEvento();
        var repeticion = evento.getRepeticion();
        evento.setRepeticionSemanal(dias);
        evento.setRepeticionVencimiento(repeticion.getVencimiento());
    }

    public void  agregarRepeticionDiariaEvento (InstanciaEvento instancia){
        var evento = instancia.getEvento();
        var intervaloDeault = 1;
        evento.setRepeticionDiaria(1);
    }

    public void modificarIntervaloRepeticionDiaria(InstanciaEvento instancia, int intervalo){
        var evento = instancia.getEvento();
        var repeticion = evento.getRepeticion();
        evento.setRepeticionDiaria(intervalo);
        evento.setRepeticionVencimiento(repeticion.getVencimiento());
    }

    public void modificarCantidadRepeticiones(InstanciaEvento instancia,int cantidad){
        var evento = instancia.getEvento();
        evento.setRepeticionCantidad(cantidad);
    }


    public void modificarVencimientoRepeticion (InstanciaEvento instancia, LocalDateTime vencimiento){
        var evento = instancia.getEvento();
        evento.setRepeticionVencimiento(vencimiento);
    }

    public void eliminarRepeticion(InstanciaEvento instancia){
        var evento = instancia.getEvento();
        evento.eliminarRepeticion();
    }



    public void marcarTareaCompleta(Tarea tarea) {
        if (tarea != null)
            tarea.completar();
    }

    public void marcarTareaIncompleta(Tarea tarea) {
        if (tarea != null)
            tarea.descompletar();
    }

    public TreeSet<ElementoCalendario> elementosEntreFechas(LocalDateTime inicio, LocalDateTime fin){
        var elementos = new TreeSet<ElementoCalendario>((new OrdenarElementosPorHorario()));
        for (ElementoCalendario i : elementosCalendario) {
            i.agregarElementoAlSet(elementos,inicio,fin);
        }
        return elementos;
    }

    public EfectoAlarma sonarProximaAlarma(LocalDateTime fechaYHora, LocalDateTime fin){
        var elementos = elementosEntreFechas(fechaYHora,fin);
        if(elementos == null)
            return null;
        EfectoAlarma efecto = null;
        LocalDateTime date = fin;
        for (ElementoCalendario i : elementos){
            var horarioAlarma = i.proximaAlarma(fechaYHora);
            if (!horarioAlarma.isAfter(date)){
                date = horarioAlarma;
                efecto = i.sonarProximaAlarma(horarioAlarma);
            }
        }
        return efecto;
    }

}
