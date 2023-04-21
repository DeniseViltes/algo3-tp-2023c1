import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Set;

public class Calendario {
    private static ArrayList<Evento> eventos;
    private final ArrayList<Tarea> tareas;

    public Calendario() {
        this.eventos = new ArrayList<>();
        this.tareas = new ArrayList<>();
    }


    // default crearEvento solo necesita la fecha del evento y la duracion default es de 1 hora
    public Evento crearEvento() {
        var horaActualTruncada = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        Evento evento = new Evento(horaActualTruncada.plusHours(1));
        this.eventos.add(evento);
        return evento;
    }

    //Habria que ver como manejamos los errores
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

    //solo los eventos tienen final/duracion Lo pongo en Duration porque tiene mas sentido como habias dicho
    // Manejemoslo en minutos que es la unidad mas pequeña
    public void modificarDuracion(Evento evento, Duration duracionMinutos) {
        if (evento != null && duracionMinutos != null)
            evento.setDuracion(duracionMinutos);
    }

    public void modificarTipo(ElementoCalendario elemento, boolean diaCompleto) {
        if (elemento != null)
            elemento.setEsDeDiaCompleto(diaCompleto);
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
    public void  agregarRepeticionEvento (Evento evento, Repeticion repeticion){
        evento.setRepeticion(repeticion);
    }
    public void modificarCantidadRepeticiones(Evento evento,int cantidad){
        evento.setRepeticionCantidad(cantidad);
    }

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
    public ArrayList<InstanciaEvento> eventosEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
        var listadoEventos = new ArrayList<InstanciaEvento>();
        for (Evento i : eventos) {
            if (i.iniciaEntreLosHorarios(inicio, fin))
                listadoEventos.add( new InstanciaEvento(i,i.getFechaInicial()));
            var j = i.getFechaInicial();
            while (i.tieneRepeticionEntreLosHorarios(j,fin)){
                j = i.proximaRepeticion(j);
                listadoEventos.add(new InstanciaEvento(i,j));
            }
        }
        return listadoEventos;
    }

}
