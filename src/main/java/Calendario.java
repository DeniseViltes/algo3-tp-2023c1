import java.io.*;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Calendario implements Serializable {
    private final TreeSet<ElementoCalendario> elementosCalendario;
    private transient List<CalendarioListener> listeners;

    public Calendario() {
        listeners = new ArrayList<>();
        this.elementosCalendario = new TreeSet<>(new OrdenadorElementosPorHorario());
    }

    public void agregarListener(CalendarioListener listener){
        if(listeners == null)
            listeners = new ArrayList<>();
        listeners.add(listener);
    }
    public void notificarListeners(){
        for (CalendarioListener listener : listeners){
            listener.updateCalendario();
        }
    }

    // Devuelve la fecha actual truncada a la proxima hora.
    private LocalDateTime ahoraDefault (){
        var ahora = LocalDateTime.now();
        ahora = ahora.truncatedTo(ChronoUnit.HOURS);
        return ahora.plusHours(1);
    }
    public Evento crearEvento() {
        Evento evento = new Evento(ahoraDefault());
        this.elementosCalendario.add(evento);
        notificarListeners();
        return evento;
    }
    public Tarea crearTarea() {
        Tarea tarea = new Tarea(ahoraDefault());
        this.elementosCalendario.add(tarea);
        notificarListeners();
        return tarea;
    }

    // Elimina el elemento original del calendario.
    public void eliminarElementoCalendario(ElementoCalendario elementoCalendario) {
        elementosCalendario.remove(elementoCalendario.getElementoOriginal(elementosCalendario));
        notificarListeners();
    }

    //Para tarea muestra vencimiento y para evento muestra la inicial
    public LocalDateTime verFechaYHora(ElementoCalendario elemento){
        return  elemento.getFecha();
    }

    public void modificarTitulo(ElementoCalendario elemento, String titulo) {
        if (elemento != null && titulo != null)
            elemento.getElementoOriginal(elementosCalendario).setTitulo(titulo);
        notificarListeners();
    }

    public void modificarDescripcion(ElementoCalendario elemento, String descripcion) {
        if (elemento != null && descripcion != null)
            elemento.getElementoOriginal(elementosCalendario).setDescripcion(descripcion);
        notificarListeners();
    }

    //En el caso de ser una tarea setea la fecha de vencimiento, y si es un evento, setea el inicio
    public void modificarFecha(ElementoCalendario elemento, LocalDateTime inicioEvento) {
        if (elemento != null && inicioEvento != null)
            elemento.getElementoOriginal(elementosCalendario).setFecha(inicioEvento);
        notificarListeners();
    }

    public void marcarDeDiaCompleto(ElementoCalendario elementoCalendario){
        elementoCalendario.getElementoOriginal(elementosCalendario).setDeDiaCompleto();
        notificarListeners();
    }

    public void desmarcarDeDiaCompleto(ElementoCalendario elementoCalendario){
        var fecha = elementoCalendario.getFecha();
        elementoCalendario.getElementoOriginal(elementosCalendario).asignarDeFechaArbitraria(fecha);
        notificarListeners();
    }

    public void modificarDuracion(Evento evento, Duration duracionMinutos) {
        if (evento != null && duracionMinutos != null){
                var nuevo = (Evento)evento.getElementoOriginal(elementosCalendario);
                nuevo.setDuracion(duracionMinutos);
            }
        notificarListeners();
    }


    public void modificarAlarmaIntervalo(ElementoCalendario elemento,Alarma alarma,Duration intervalo) {
        elemento.getElementoOriginal(elementosCalendario).modificarIntervaloAlarma(alarma,intervalo);
        notificarListeners();
    }

    public void modificarAlarmaFechaAbsoluta(ElementoCalendario elemento, Alarma alarma, LocalDateTime fechaYhora){
        elemento.getElementoOriginal(elementosCalendario).modificarFechaAbsolutaAlarma(alarma,fechaYhora);
        notificarListeners();
    }

    public void modificarAlarmaEfecto(ElementoCalendario elemento, Alarma alarma, EfectoAlarma efecto){
        elemento.getElementoOriginal(elementosCalendario).modificarAlarmaEfecto(alarma,efecto);
        notificarListeners();
    }

    public Alarma agregarAlarma(ElementoCalendario elemento,Duration intervalo){
        var original = elemento.getElementoOriginal(elementosCalendario).agregarAlarma(elemento.getFecha(), intervalo);
        notificarListeners();
        return original;
    }

    public Alarma agregarAlarmaAbsoluta(ElementoCalendario elemento,LocalDateTime horario){
        var original = elemento.getElementoOriginal(elementosCalendario).agregarAlarmaAbsoluta(horario);
        notificarListeners();
        return original;
    }

    public void eliminarAlarma(ElementoCalendario elemento, Alarma alarma){
        elemento.getElementoOriginal(elementosCalendario).eliminarAlarma(alarma);
        notificarListeners();
    }
    public void  agregarRepeticionAnualEvento (Evento evento){
        Evento nuevo = (Evento) evento.getElementoOriginal(elementosCalendario);
            nuevo.setRepeticionAnual();
        notificarListeners();

    }

    public void  agregarRepeticionMensualEvento (Evento evento){
        Evento nuevo = (Evento) evento.getElementoOriginal(elementosCalendario);
        nuevo.setRepeticionMensual();
        notificarListeners();
    }
    public void  agregarRepeticionSemanalEvento (Evento evento){
        var dia = evento.getFecha();
        var dias = EnumSet.noneOf(DayOfWeek.class);
        dias.add(dia.getDayOfWeek());
            Evento nuevo = (Evento) evento.getElementoOriginal(elementosCalendario);
            nuevo.setRepeticionSemanal(dias);
        notificarListeners();
    }

    public void modificarDiasRepeticionSemanal(Evento evento, Set<DayOfWeek> dias){
        Evento nuevo = (Evento) evento.getElementoOriginal(elementosCalendario);
        nuevo.modificarDiasRepeticionSemanal(dias);
        notificarListeners();
    }

    public void  agregarRepeticionDiariaEvento (Evento evento){
            Evento nuevo = (Evento) evento.getElementoOriginal(elementosCalendario);
            nuevo.setRepeticionDiaria(1);
        notificarListeners();
    }

    public void modificarIntervaloRepeticionDiaria(Evento evento, int intervalo){
            Evento nuevo = (Evento) evento.getElementoOriginal(elementosCalendario);
            nuevo.modificarIntervaloRepeticionDiaria(intervalo);
        notificarListeners();
    }

    public void modificarCantidadRepeticiones(Evento evento,int cantidad){
            Evento nuevo = (Evento) evento.getElementoOriginal(elementosCalendario);
            nuevo.setRepeticionCantidad(cantidad);
        notificarListeners();

    }


    public void modificarVencimientoRepeticion (Evento evento, LocalDateTime vencimiento){
            Evento nuevo = (Evento) evento.getElementoOriginal(elementosCalendario);
            nuevo.setRepeticionVencimiento(vencimiento);
        notificarListeners();
    }

    public void eliminarRepeticion(Evento evento){
            Evento nuevo = (Evento) evento.getElementoOriginal(elementosCalendario);
            nuevo.eliminarRepeticion();
        notificarListeners();
    }



    public void marcarTareaCompleta(Tarea tarea) {
        if (tarea != null)
            tarea.completar();
        notificarListeners();
    }

    public void marcarTareaIncompleta(Tarea tarea) {
        if (tarea != null)
            tarea.descompletar();
        notificarListeners();
    }

    // Devuelve un TreeSet con todos los elementos entre el inicio y el fin. Los elementos serian eventos
    // con sus repeticiones y tareas.
    public TreeSet<ElementoCalendario> elementosEntreFechas(LocalDateTime inicio, LocalDateTime fin){
        var elementos = new TreeSet<>((new OrdenadorElementosPorHorario()));
        for (ElementoCalendario i : elementosCalendario) {
            i.agregarElementoAlSet(elementos,inicio,fin);
        }
        return elementos;
    }

    // Devuelve el efecto de la proxima alarma a sonar entre las fechas dadas.
    public ElementoCalendario sonarProximaAlarma(LocalDateTime fechaYHora, LocalDateTime fin){
        var elementos = elementosEntreFechas(fechaYHora,fin);
        if(elementos == null)
            return null;
        ElementoCalendario el = null;
        LocalDateTime date = fin;
        for (ElementoCalendario i : elementos){
            var horarioAlarma = i.horarioProximaAlarma(fechaYHora);
            if (horarioAlarma!=null && !horarioAlarma.isAfter(date)){
                date = horarioAlarma;
                el = i;
            }
        }
        return el;
    }

    public static Calendario deserializar(InputStream is) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(is);
        return (Calendario) ois.readObject();
    }

    public void serializar(OutputStream os) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(this);
        oos.flush();
    }


}
