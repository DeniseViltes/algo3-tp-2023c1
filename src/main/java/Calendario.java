import java.io.*;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Calendario implements Serializable {
    private final TreeSet<ElementoCalendario> elementosCalendario;

    public Calendario() {
        this.elementosCalendario = new TreeSet<>(new OrdenadorElementosPorHorario());
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
        return evento;
    }
    public Tarea crearTarea() {
        Tarea tarea = new Tarea(ahoraDefault());
        this.elementosCalendario.add(tarea);
        return tarea;
    }

    // Elimina el elemento original del calendario.
    public void eliminarElementoCalendario(ElementoCalendario elementoCalendario) {
        elementosCalendario.remove(elementoCalendario.getElementoOriginal(elementosCalendario));
    }

    //Para tarea muestra vencimiento y para evento muestra la inicial
    public LocalDateTime verFechaYHora(ElementoCalendario elemento){
        return  elemento.getFecha();
    }

    public void modificarTitulo(ElementoCalendario elemento, String titulo) {
        if (elemento != null && titulo != null)
            elemento.getElementoOriginal(elementosCalendario).setTitulo(titulo);
    }

    public void modificarDescripcion(ElementoCalendario elemento, String descripcion) {
        if (elemento != null && descripcion != null)
            elemento.getElementoOriginal(elementosCalendario).setDescripcion(descripcion);
    }

    //En el caso de ser una tarea setea la fecha de vencimiento, y si es un evento, setea el inicio
    public void modificarFecha(ElementoCalendario elemento, LocalDateTime inicioEvento) {
        if (elemento != null && inicioEvento != null)
            elemento.getElementoOriginal(elementosCalendario).setFecha(inicioEvento);
    }

    public void marcarDeDiaCompleto(ElementoCalendario elementoCalendario){
        elementoCalendario.getElementoOriginal(elementosCalendario).setDeDiaCompleto();
    }

    public void desmarcarDeDiaCompleto(ElementoCalendario elementoCalendario){
        var fecha = elementoCalendario.getFecha();
        elementoCalendario.getElementoOriginal(elementosCalendario).asignarDeFechaArbitraria(fecha);
    }

    public void modificarDuracion(Evento evento, Duration duracionMinutos) {
        if (evento != null && duracionMinutos != null){
                var nuevo = (Evento)evento.getElementoOriginal(elementosCalendario);
                nuevo.setDuracion(duracionMinutos);
            }
    }


    public void modificarAlarmaIntervalo(ElementoCalendario elemento,Alarma alarma,Duration intervalo) {
        elemento.getElementoOriginal(elementosCalendario).modificarIntervaloAlarma(alarma,intervalo);
    }

    public void modificarAlarmaFechaAbsoluta(ElementoCalendario elemento, Alarma alarma, LocalDateTime fechaYhora){
        elemento.getElementoOriginal(elementosCalendario).modificarFechaAbsolutaAlarma(alarma,fechaYhora);
    }

    public void modificarAlarmaEfecto(ElementoCalendario elemento, Alarma alarma, EfectoAlarma efecto){
        elemento.getElementoOriginal(elementosCalendario).modificarAlarmaEfecto(alarma,efecto);
    }

    public Alarma agregarAlarma(ElementoCalendario elemento,Duration intervalo){
        return elemento.getElementoOriginal(elementosCalendario).agregarAlarma(elemento.getFecha(), intervalo);
    }

    public Alarma agregarAlarmaAbsoluta(ElementoCalendario elemento,LocalDateTime horario){
        return elemento.getElementoOriginal(elementosCalendario).agregarAlarmaAbsoluta(horario);
    }

    public void eliminarAlarma(ElementoCalendario elemento, Alarma alarma){
        elemento.getElementoOriginal(elementosCalendario).eliminarAlarma(alarma);
    }
    public void  agregarRepeticionAnualEvento (Evento evento){
        Evento nuevo = (Evento) evento.getElementoOriginal(elementosCalendario);
            nuevo.setRepeticionAnual();

    }

    public void  agregarRepeticionMensualEvento (Evento evento){
            Evento nuevo = (Evento) evento.getElementoOriginal(elementosCalendario);
            nuevo.setRepeticionMensual();

    }
    public void  agregarRepeticionSemanalEvento (Evento evento){
        var dia = evento.getFecha();
        var dias = EnumSet.noneOf(DayOfWeek.class);
        dias.add(dia.getDayOfWeek());
            Evento nuevo = (Evento) evento.getElementoOriginal(elementosCalendario);
            nuevo.setRepeticionSemanal(dias);

    }

    public void modificarDiasRepeticionSemanal(Evento evento, Set<DayOfWeek> dias){
        Evento nuevo = (Evento) evento.getElementoOriginal(elementosCalendario);
        nuevo.modificarDiasRepeticionSemanal(dias);

    }

    public void  agregarRepeticionDiariaEvento (Evento evento){
            Evento nuevo = (Evento) evento.getElementoOriginal(elementosCalendario);
            nuevo.setRepeticionDiaria(1);

    }

    public void modificarIntervaloRepeticionDiaria(Evento evento, int intervalo){
            Evento nuevo = (Evento) evento.getElementoOriginal(elementosCalendario);
            nuevo.modificarIntervaloRepeticionDiaria(intervalo);
    }

    public void modificarCantidadRepeticiones(Evento evento,int cantidad){
            Evento nuevo = (Evento) evento.getElementoOriginal(elementosCalendario);
            nuevo.setRepeticionCantidad(cantidad);

    }


    public void modificarVencimientoRepeticion (Evento evento, LocalDateTime vencimiento){
            Evento nuevo = (Evento) evento.getElementoOriginal(elementosCalendario);
            nuevo.setRepeticionVencimiento(vencimiento);

    }

    public void eliminarRepeticion(Evento evento){
            Evento nuevo = (Evento) evento.getElementoOriginal(elementosCalendario);
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

    // Devuelve un TreeSet con todos los elementos entre el inicio y el fin. Los elementos serian eventos
    // con sus repeticiones y tareas.
    public TreeSet<ElementoCalendario> elementosEntreFechas(LocalDateTime inicio, LocalDateTime fin){
        var elementos = new TreeSet<ElementoCalendario>((new OrdenadorElementosPorHorario()));
        for (ElementoCalendario i : elementosCalendario) {
            i.agregarElementoAlSet(elementos,inicio,fin);
        }
        return elementos;
    }

    // Devuelve el efecto de la proxima alarma a sonar entre las fechas dadas.
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

    public static Calendario deserializar(InputStream is) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(is);
        Calendario obj = (Calendario) ois.readObject();
        return obj;
    }

    public void serializar(OutputStream os) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(this);
        oos.flush();
    }


}
