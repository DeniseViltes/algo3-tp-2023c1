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


    // default crearEvento? Es necesario poner el titulo? En google calendar no es necesario
    public void crearEvento(String titulo){
        Evento evento = new Evento();
        if (titulo!=null)
            evento.setTitulo(titulo);
        this.eventos.add(evento);
    }

    // Hay que repetir modificar, crear y eliminar para tarea, hay que revisar esto

    public void eliminarEvento(Evento evento){
        //por string o directo el evento??
        eventos.remove(evento);
    }


}
