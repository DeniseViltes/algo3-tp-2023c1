import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Calendario {
    private final TreeSet<ElementoCalendario> elementosCalendario;

    public Calendario() {
        this.elementosCalendario = new TreeSet<>(new OrdenadorElementosPorHorario());
    }

    private LocalDateTime ahoraDefault (){
        var ahora = LocalDateTime.now();
        ahora = ahora.truncatedTo(ChronoUnit.HOURS);
        return ahora.plusHours(1);
    }
    public Evento crearEvento() {
        Evento evento = new Evento(ahoraDefault());
        this.elementosCalendario.add(evento);
        return evento;
    }
    public Tarea crearTarea() {
        Tarea tarea = new Tarea(ahoraDefault());
        this.elementosCalendario.add(tarea);
        return tarea;
    }
    public void eliminarElementoCalendario(ElementoCalendario elementoCalendario) {
        elementosCalendario.remove(this.getEventoOriginal(elementoCalendario));
    }

    //Para tarea muestra vencimiento y para evento muestra la incial
    public LocalDateTime verFechaYHora(ElementoCalendario elemento){
        return  elemento.getFecha();
    }

    public ElementoCalendario getEventoOriginal(ElementoCalendario evento){
        for (ElementoCalendario elemento : elementosCalendario){
            if(elemento.comparar(evento))
                return elemento;
        }
        return null;
    }

    public void modificarTitulo(ElementoCalendario elemento, String titulo) {
        if (elemento != null && titulo != null)
            this.getEventoOriginal(elemento).setTitulo(titulo);
    }

    public void modificarDescripcion(ElementoCalendario elemento, String descripcion) {
        if (elemento != null && descripcion != null)
            this.getEventoOriginal(elemento).setDescripcion(descripcion);
    }

    //En el caso de ser una tarea setea la fecha de vencimiento, y si es un evento, setea el inicio
    public void modificarFecha(ElementoCalendario elemento, LocalDateTime inicioEvento) {
        if (elemento != null && inicioEvento != null)
            this.getEventoOriginal(elemento).setFecha(inicioEvento);
    }

    public void marcarDeDiaCompleto(ElementoCalendario elementoCalendario){
            this.getEventoOriginal(elementoCalendario).setDeDiaCompleto();
    }

    public void desmarcarDeDiaCompleto(ElementoCalendario elementoCalendario){
        var fecha = elementoCalendario.getFecha();
        this.getEventoOriginal(elementoCalendario).asignarDeFechaArbitraria(fecha);
    }

    public void modificarDuracion(Evento evento, Duration duracionMinutos) {
        if (evento != null && duracionMinutos != null){
                var nuevo = (Evento)this.getEventoOriginal(evento);
                nuevo.setDuracion(duracionMinutos);
            }
    }


    public void modificarAlarmaIntervalo(ElementoCalendario elemento,Alarma alarma,Duration intervalo) {
        this.getEventoOriginal(elemento).modificarIntervaloAlarma(alarma,intervalo);
    }

    public void modificarAlarmaFechaAbsoluta(ElementoCalendario elemento, Alarma alarma, LocalDateTime fechaYhora){
        this.getEventoOriginal(elemento).modificarFechaAbsolutaAlarma(alarma,fechaYhora);
    }

    public void modificarAlarmaEfecto(ElementoCalendario elemento, Alarma alarma, EfectoAlarma efecto){
        this.getEventoOriginal(elemento).modificarAlarmaEfecto(alarma,efecto);
    }

    public Alarma agregarAlarma(ElementoCalendario elemento,Duration intervalo){
        return this.getEventoOriginal(elemento).agregarAlarma(intervalo,elemento.getFecha());
    }

    public Alarma agregarAlarmaAbsoluta(ElementoCalendario elemento,LocalDateTime horario){
        return this.getEventoOriginal(elemento).agregarAlarmaAbsoluta(horario);
    }

    public void eliminarAlarma(ElementoCalendario elemento, Alarma alarma){
        this.getEventoOriginal(elemento).eliminarAlarma(alarma);
    }
    public void  agregarRepeticionAnualEvento (Evento evento){
        Evento nuevo = (Evento) this.getEventoOriginal(evento);
            nuevo.setRepeticionAnual();

    }

    public void  agregarRepeticionMensualEvento (Evento evento){
            Evento nuevo = (Evento) this.getEventoOriginal(evento);
            nuevo.setRepeticionMensual();

    }
    public void  agregarRepeticionSemanalEvento (Evento evento){
        var dia = evento.getFecha();
        var dias = EnumSet.noneOf(DayOfWeek.class);
        dias.add(dia.getDayOfWeek());
            Evento nuevo = (Evento) this.getEventoOriginal(evento);
            nuevo.setRepeticionSemanal(dias);

    }

    public void modificarDiasRepeticionSemanal(Evento evento, Set<DayOfWeek> dias){
        Evento nuevo = (Evento) this.getEventoOriginal(evento);
        var fechaVencimiento = nuevo.getRepeticionVencimiento();
        nuevo.setRepeticionSemanal(dias);
        nuevo.setRepeticionVencimiento(fechaVencimiento);

    }

    public void  agregarRepeticionDiariaEvento (Evento evento){
            Evento nuevo = (Evento) this.getEventoOriginal(evento);
            nuevo.setRepeticionDiaria(1);

    }

    public void modificarIntervaloRepeticionDiaria(Evento evento, int intervalo){
            Evento nuevo = (Evento) this.getEventoOriginal(evento);
            var fechaVencimiento = nuevo.getRepeticionVencimiento();
            nuevo.setRepeticionDiaria(intervalo);
            nuevo.setRepeticionVencimiento(fechaVencimiento);

    }

    public void modificarCantidadRepeticiones(Evento evento,int cantidad){
            Evento nuevo = (Evento) this.getEventoOriginal(evento);
            nuevo.setRepeticionCantidad(cantidad);

    }


    public void modificarVencimientoRepeticion (Evento evento, LocalDateTime vencimiento){
            Evento nuevo = (Evento) this.getEventoOriginal(evento);
            nuevo.setRepeticionVencimiento(vencimiento);

    }

    public void eliminarRepeticion(Evento evento){
            Evento nuevo = (Evento) this.getEventoOriginal(evento);
            nuevo.eliminarRepeticion();

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
        var elementos = new TreeSet<ElementoCalendario>((new OrdenadorElementosPorHorario()));
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
            var horarioAlarma = i.horarioProximaAlarma(fechaYHora);
            if (!horarioAlarma.isAfter(date)){
                date = horarioAlarma;
                efecto = i.sonarProximaAlarma(horarioAlarma);
            }
        }
        return efecto;
    }

}
