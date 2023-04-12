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

    public void crearEvento(String titulo, String descripcion){
        Evento evento = new Evento(titulo, descripcion);
        this.eventos.add(evento);
    }
}
