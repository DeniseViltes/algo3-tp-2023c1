import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Calendario {
    private final ArrayList<Evento> eventos;
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
    public void modificarInicio(ElementoCalendario elemento, LocalDateTime inicioEvento) {
        if (elemento != null && inicioEvento != null)
            elemento.setFecha(inicioEvento);
    }

    //solo los eventos tienen final/duracion
    public void modificarFinal(Evento evento, LocalDateTime finalEvento) {
        if (evento != null && finalEvento != null)
            evento.setFinal(finalEvento);
    }

    public void modificarTipo(ElementoCalendario elemento, boolean diaCompleto) {
        if (elemento != null)
            elemento.setEsDeDiaCompleto(diaCompleto);
    }

    //Funciones a completar en un futuro cuando tengamos implementado repeticion y alarma

    //tendria que recibir una repeticion?
    public void modificarRepeticionEvento(Evento evento, Repeticion repeticion) {
        evento.setRepeticion(repeticion);
    }

    public void modificarAlarmaEvento(Evento evento, boolean alarma) {
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

    //el calendario almacena eventos, pero solo devuelve instancias de eventos, no el evento en si
    public ArrayList<InstanciaEvento> eventosEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
        var listadoEventos = new ArrayList<InstanciaEvento>();
        for (Evento i : eventos) {
            if (i.iniciaEntreLosHorarios(inicio, fin))
                listadoEventos.add( new InstanciaEvento(i));
            var j = inicio;
            while (i.tieneRepeticionEntreLosHorarios(j,fin)){
                var instancia = new InstanciaEvento(i);
                instancia.setFecha(i.proximaRepeticion(j));
                listadoEventos.add(instancia);

                j = i.proximaRepeticion(j);
            }
        }
        return listadoEventos;
    }

}
