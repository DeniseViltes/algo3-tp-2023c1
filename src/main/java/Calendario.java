import java.time.LocalDateTime;
import java.util.ArrayList;

public class Calendario {


    private final ArrayList<Evento> eventos;
    private final ArrayList<Tarea> tareas;
    public Calendario() {
        this.eventos = new ArrayList<>();
        this.tareas = new ArrayList<>();
    }

    public void crearTarea(String titulo, String descripcion){
        Tarea tarea = new Tarea(titulo, descripcion);
        this.tareas.add(tarea);
    }


    // default crearEvento?
    public void crearEvento(String titulo, String descripcion){
        Evento evento = new Evento(titulo, descripcion);
        this.eventos.add(evento);
    }
    public void crearEvento(String titulo,
                            String descripcion,
                            LocalDateTime inicio, LocalDateTime fin){
        var evento = new Evento(titulo,descripcion);
        evento.setFechaYHoraFinal(inicio);
        evento.setFechaYHoraFinal(fin);
        this.eventos.add(evento);
    }
    public void crearEvento(String titulo, String descripcion, LocalDateTime dia){
        var evento = new Evento(titulo,descripcion);
        evento.setEsDeDiaCompleto(true);
        evento.setFechaYHoraInicial(dia.toLocalDate().atStartOfDay());
        evento.setFechaYHoraFinal(dia.toLocalDate().atTime(23,59)); //o mejor queda en null?
        this.eventos.add(evento);
    }


    public void eliminarEvento(Evento evento){
        //por string o directo el evento??
        eventos.remove(evento);
    }

    // dejo estos getters para poder hacer algunas pruebas simples
    public ArrayList<Evento> getEventos() {
        return eventos;
    }

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }


}
