import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Calendario {
    //todos los evetos se crean con new, asi que son distintos, y creo que no conviene tener repetidos
    //y depaso quedan ordenados
    private static TreeSet<Evento> eventos;

    private final TreeSet<Tarea> tareas;

    public Calendario() {
        this.eventos = new TreeSet<>(new OrdenarElementosPorHorario());
        this.tareas = new TreeSet<>(new OrdenarElementosPorHorario());
    }


    // default crearEvento solo necesita la fecha del evento y la duracion default es de 1 hora
    //Esta bien que devuelva Evento?? o habria que devolver instanciaEvento
    public Evento crearEvento() {
        var horaActualTruncada = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        Evento evento = new Evento(horaActualTruncada.plusHours(1));
        this.eventos.add(evento);
        return evento;
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
    public void  agregarRepeticionAnualEvento (Evento evento){
        evento.setRepeticionAnual();
    }
    public void  agregarRepeticionMensuakEvento (Evento evento){
        evento.setRepeticionMensual();
    }
    public void  agregarRepeticionSemanalEvento (Evento evento, Set<DayOfWeek> dias){
        evento.setRepeticionSemanal(dias);
    }
    public void  agregarRepeticionDiariaEvento (Evento evento, int intervalo){
        evento.setRepeticionDiaria(intervalo);
    }
    public void modificarCantidadRepeticiones(Evento evento,int cantidad){
        evento.setRepeticionCantidad(cantidad);
    }

    // Para modificar a repeticion infinita, vencimiento debe ser null.
    public void modificarVencimientoRepeticion (Evento evento, LocalDateTime vencimiento){
        evento.setRepeticionVencimiento(vencimiento);
    }

    public void eliminarRepeticion(Evento evento){
        evento.eliminarRepeticion();
    }

    public void eliminarEvento(Evento evento) {
        eventos.remove(evento);
    }


    // default crearTarea solo necesita el vencimiento
    public void crearTarea() {
        var horaActualTruncada = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        Tarea tarea = new Tarea(horaActualTruncada);
        this.tareas.add(tarea);
    }

    public void marcarTareaCompleta(Tarea tarea) {
        if (tarea != null)
            tarea.setEstado(true);
    }

    public void marcarTareaIncompleta(Tarea tarea) {
        if (tarea != null)
            tarea.setEstado(false);
    }

    public void eliminarTarea(Tarea tarea) {
        tareas.remove(tarea);
    }

    //el calendario almacena eventos, pero solo muestra instancias de eventos, no el evento en si
    public List<InstanciaEvento> eventosEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
        var listadoEventos = new ArrayList<InstanciaEvento>();
        for (Evento i : eventos) {
            if (i.iniciaEntreLosHorarios(inicio, fin))
                listadoEventos.add( new InstanciaEvento(i,i.getFecha()));
            var j = i.getFecha();
            while (i.tieneRepeticionEntreLosHorarios(j,fin)){
                j = i.proximaRepeticion(j);
                listadoEventos.add(new InstanciaEvento(i,j));
            }
        }
        return listadoEventos;
    }

    public Set<Tarea>  tareasEntreFechas(LocalDateTime inicio, LocalDateTime fin){
        return  null;
    }

    //se puede hacer sin fecha final?
    public EfectoAlarma sonarProximaAlarma(LocalDateTime fechaYHora, LocalDateTime fin){

        return null;
    }

}
