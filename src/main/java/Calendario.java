import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Calendario {
    private final ArrayList<Evento> eventos;
    private final ArrayList<Tarea> tareas;

    public Calendario() {
        this.eventos = new ArrayList<>();
        this.tareas = new ArrayList<>();
    }

    // default crearEvento solo necesita la fecha del evento y la duracion default es de 1 hora
    public void crearEvento(LocalDateTime inicioEvento){
        Evento evento = new Evento(inicioEvento);
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
        if (evento != null && diaCompleto != null)
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
    public void crearTarea(LocalDateTime vencimiento){
        Tarea tarea = new Tarea(vencimiento);
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
        if (tarea != null && diaCompleto != null)
            tarea.setEsDeDiaCompleto(diaCompleto);
    }

    public void modificarVencimientoTarea(Tarea tarea, LocalDateTime vencimiento){
        if (tarea != null && vencimiento != null)
            tarea.setVencimiento(vencimiento);
    }

    public void modificarEstadoTarea(Tarea tarea, boolean completada){
        if (tarea != null && completada != null)
            tarea.setEstado(completada);
    }

    // Hay que ver como implementamos la repeticion
    public void modificarAlarmaTarea(Tarea tarea, boolean repeticion){
    }
    public void eliminarTarea(Tarea tarea){
        tareas.remove(tarea);
    }



    public ArrayList<Evento> eventosDeLaFecha(LocalDate fecha){
        var eventosdDelDia = new ArrayList<Evento>();
        for (Evento i : eventos){
            if (i.getFechaInicial().equals(fecha))
                eventosdDelDia.add(i);
        }
        return eventosdDelDia;
    }

}
