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
    public void crearEvento(){
        var horaActualTruncada = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        Evento evento = new Evento(horaActualTruncada);
        this.eventos.add(evento);
    }

    //Habria que ver como manejamos los errores
    public void modificarTituloEvento(Evento evento, String titulo){
        if (evento != null && titulo != null)
            evento.setTitulo(titulo);
    }

    public void modificarDescripcionEvento(Evento evento, String descripcion){
        if (evento != null && descripcion != null)
            evento.setDescripcion(descripcion);
    }

    public void modificarInicioEvento(Evento evento, LocalDateTime inicioEvento){
        if (evento != null && inicioEvento != null)
            evento.setInicio(inicioEvento);
    }

    public void modificarFinalEvento(Evento evento, LocalDateTime finalEvento){
        if (evento != null && finalEvento != null)
            evento.setFinal(finalEvento);
    }

    public void modificarTipoEvento(Evento evento, boolean diaCompleto){
        if (evento != null && diaCompleto != false)
            evento.setEsDeDiaCompleto(diaCompleto);
    }

    //Funciones a completar en un futuro cuando tengamos implementado repeticion y alarma
    public void modificarRepeticionEvento(Evento evento, boolean repeticion){
    }
    public void modificarAlarmaEvento(Evento evento, boolean alarma){
    }
    public void eliminarEvento(Evento evento){
        eventos.remove(evento);
    }

    // default crearTarea solo necesita el vencimiento
    public void crearTarea(){
        var horaActualTruncada = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        Tarea tarea = new Tarea(horaActualTruncada);
        this.tareas.add(tarea);
    }

    public void modificarTituloTarea(Tarea tarea, String titulo){
        if (tarea != null && titulo != null)
            tarea.setTitulo(titulo);
    }

    public void modificarDescripcionTarea(Tarea tarea, String descripcion){
        if (tarea != null && descripcion != null)
            tarea.setDescripcion(descripcion);
    }
    public void modificarTipoTarea(Tarea tarea, boolean diaCompleto){
        if (tarea != null)
            tarea.setEsDeDiaCompleto(diaCompleto);
    }

    public void modificarVencimientoTarea(Tarea tarea, LocalDateTime vencimiento){
        if (tarea != null && vencimiento != null)
            tarea.setVencimiento(vencimiento);
    }

    public void marcarTareaCompleta(Tarea tarea){
        if (tarea != null)
            tarea.setEstado(true);
    }
    public void marcarTareaIncompleta(Tarea tarea){
        if (tarea != null)
            tarea.setEstado(false);
    }
    // Hay que ver como implementamos la repeticion
    public void modificarAlarmaTarea(Tarea tarea, boolean repeticion){
    }
    public void eliminarTarea(Tarea tarea){
        tareas.remove(tarea);
    }

    public ArrayList<Evento> eventosDeLaFecha(LocalDate fecha){
        var eventosdDelDia = new ArrayList<Evento>();
        var dia = fecha.atStartOfDay();
        for (Evento i : eventos){
            if (i.iniciaEntreLasFechas(dia, dia.plusDays(1)))
                eventosdDelDia.add(i);
        }
        return eventosdDelDia;
    }

}
