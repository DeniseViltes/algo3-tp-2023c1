import jdk.jfr.Event;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Calendario {
    private final TreeSet<ElementoCalendario> elementosCalendario;

    public Calendario() {
        this.elementosCalendario = new TreeSet<>(new OrdenarElementosPorHorario());
    }

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
    public void eliminarElementoCalendario(ElementoCalendario elementoCalendario) {
        elementosCalendario.remove(elementoCalendario);
        if(elementosCalendario.contains(elementoCalendario))
            elementosCalendario.remove(elementoCalendario);
        else
            elementosCalendario.remove(this.getEventoOriginal(elementoCalendario));
    }

    //Para tarea muestra vencimiento y para evento muestra la incial
    public LocalDateTime verFechaYHora(ElementoCalendario elemento){
        return  elemento.getFecha();
    }

    public ElementoCalendario getEventoOriginal(ElementoCalendario evento){
        for (ElementoCalendario elemento : elementosCalendario){
            if(elemento.comparar(evento))
                return elemento;
        }
        return null;
    }

    public void modificarTitulo(ElementoCalendario elemento, String titulo) {
        if (elemento != null && titulo != null)
            if(elementosCalendario.contains(elemento))
                elemento.setTitulo(titulo);
            else
                this.getEventoOriginal(elemento).setTitulo(titulo);
    }

    public void modificarDescripcion(ElementoCalendario elemento, String descripcion) {
        if (elemento != null && descripcion != null)
            if(elementosCalendario.contains(elemento))
                elemento.setDescripcion(descripcion);
            else
                this.getEventoOriginal(elemento).setDescripcion(descripcion);
    }

    //En el caso de ser una tarea setea la fecha de vencimiento, y si es un evento, setea el inicio
    public void modificarFecha(ElementoCalendario elemento, LocalDateTime inicioEvento) {
        if (elemento != null && inicioEvento != null)
            if(elementosCalendario.contains(elemento))
                elemento.setFecha(inicioEvento);
            else
                this.getEventoOriginal(elemento).setFecha(inicioEvento);
    }

    public void marcarDeDiaCompleto(ElementoCalendario elementoCalendario){
            if(elementosCalendario.contains(elementoCalendario))
                elementoCalendario.setDeDiaCompleto();
            else
                this.getEventoOriginal(elementoCalendario).setDeDiaCompleto();
    }

    public void desmarcarDeDiaCompleto(ElementoCalendario elementoCalendario){
        var fecha = elementoCalendario.getFecha();
        if(elementosCalendario.contains(elementoCalendario))
            elementoCalendario.asignarDeFechaArbitraria(fecha);
        else
            this.getEventoOriginal(elementoCalendario).asignarDeFechaArbitraria(fecha);
    }

    public void modificarDuracion(Evento evento, Duration duracionMinutos) {
        if (evento != null && duracionMinutos != null)
            if(elementosCalendario.contains(evento))
                evento.setDuracion(duracionMinutos);
            else {
                Evento nuevo = (Evento) this.getEventoOriginal(evento);
                nuevo.setDuracion(duracionMinutos);
            }
    }


    public void modificarAlarmaIntervalo(ElementoCalendario elemento,Alarma alarma,Duration intervalo) {
        if(elementosCalendario.contains(elemento))
            elemento.modificarIntervaloAlarma(alarma,intervalo);
        else
            this.getEventoOriginal(elemento).modificarIntervaloAlarma(alarma,intervalo);
    }

    public void modificarAlarmaFechaAbsoluta(ElementoCalendario elemento, Alarma alarma, LocalDateTime fechaYhora){
        if(elementosCalendario.contains(elemento))
            elemento.modificarFechaAbsolutaAlarma(alarma,fechaYhora);
        else
            this.getEventoOriginal(elemento).modificarFechaAbsolutaAlarma(alarma,fechaYhora);
    }

    public void modificarAlarmaEfecto(ElementoCalendario elemento, Alarma alarma, EfectoAlarma efecto){
        if(elementosCalendario.contains(elemento))
            elemento.modificarAlarmaEfecto(alarma,efecto);
        else
            this.getEventoOriginal(elemento).modificarAlarmaEfecto(alarma,efecto);
    }

    public Alarma agregarAlarma(ElementoCalendario elemento,Duration intervalo){
        Alarma alarma;
        if(elementosCalendario.contains(elemento))
            alarma = elemento.agregarAlarma(intervalo);
        else
            alarma = this.getEventoOriginal(elemento).agregarAlarma(intervalo);

        return alarma;
    }

    public Alarma agregarAlarmaAbsoluta(ElementoCalendario elemento,LocalDateTime horario){
        Alarma alarma;
        if(elementosCalendario.contains(elemento))
            alarma = elemento.agregarAlarmaAbsoluta(horario);
        else
            alarma = this.getEventoOriginal(elemento).agregarAlarmaAbsoluta(horario);

        return alarma;
    }

    public void eliminarAlarma(ElementoCalendario elemento, Alarma alarma){
        if(elementosCalendario.contains(elemento))
            elemento.eliminarAlarma(alarma);
        else
            this.getEventoOriginal(elemento).eliminarAlarma(alarma);
    }
    public void  agregarRepeticionAnualEvento (Evento evento){
        if(elementosCalendario.contains(evento))
            evento.setRepeticionAnual();
        else {
            Evento nuevo = (Evento) this.getEventoOriginal(evento);
            nuevo.setRepeticionAnual();
        }
    }

    public void  agregarRepeticionMensualEvento (Evento evento){
        if(elementosCalendario.contains(evento))
            evento.setRepeticionMensual();
        else {
            Evento nuevo = (Evento) this.getEventoOriginal(evento);
            nuevo.setRepeticionMensual();
        }
    }
    public void  agregarRepeticionSemanalEvento (Evento evento){
        var dia = evento.getFecha();
        var dias = EnumSet.noneOf(DayOfWeek.class);
        dias.add(dia.getDayOfWeek());
        if(elementosCalendario.contains(evento))
            evento.setRepeticionSemanal(dias);
        else {
            Evento nuevo = (Evento) this.getEventoOriginal(evento);
            nuevo.setRepeticionSemanal(dias);
        }
    }

    public void modificarDiasRepeticionSemanal(Evento evento, Set<DayOfWeek> dias){
        var repeticion = evento.getRepeticion();
        if(elementosCalendario.contains(evento)) {
            evento.setRepeticionSemanal(dias);
            evento.setRepeticionVencimiento(repeticion.getVencimiento());
        }
        else {
            Evento nuevo = (Evento) this.getEventoOriginal(evento);
            nuevo.setRepeticionSemanal(dias);
            nuevo.setRepeticionVencimiento(repeticion.getVencimiento());
        }
    }

    public void  agregarRepeticionDiariaEvento (Evento evento){
        if(elementosCalendario.contains(evento))
            evento.setRepeticionDiaria(1);
        else {
            Evento nuevo = (Evento) this.getEventoOriginal(evento);
            nuevo.setRepeticionDiaria(1);
        }
    }

    public void modificarIntervaloRepeticionDiaria(Evento evento, int intervalo){
        var repeticion = evento.getRepeticion();
        if(elementosCalendario.contains(evento)) {
            evento.setRepeticionDiaria(intervalo);
            evento.setRepeticionVencimiento(repeticion.getVencimiento());
        }
        else {
            Evento nuevo = (Evento) this.getEventoOriginal(evento);
            nuevo.setRepeticionDiaria(intervalo);
            nuevo.setRepeticionVencimiento(repeticion.getVencimiento());
        }
    }

    public void modificarCantidadRepeticiones(Evento evento,int cantidad){
        if(elementosCalendario.contains(evento))
            evento.setRepeticionCantidad(cantidad);
        else {
            Evento nuevo = (Evento) this.getEventoOriginal(evento);
            nuevo.setRepeticionCantidad(cantidad);
        }
    }


    public void modificarVencimientoRepeticion (Evento evento, LocalDateTime vencimiento){
        if(elementosCalendario.contains(evento))
            evento.setRepeticionVencimiento(vencimiento);
        else {
            Evento nuevo = (Evento) this.getEventoOriginal(evento);
            nuevo.setRepeticionVencimiento(vencimiento);
        }
    }

    public void eliminarRepeticion(Evento evento){
        if(elementosCalendario.contains(evento))
            evento.eliminarRepeticion();
        else {
            Evento nuevo = (Evento) this.getEventoOriginal(evento);
            nuevo.eliminarRepeticion();
        }
    }



    public void marcarTareaCompleta(Tarea tarea) {
        if (tarea != null)
            tarea.completar();
    }

    public void marcarTareaIncompleta(Tarea tarea) {
        if (tarea != null)
            tarea.descompletar();
    }

    public TreeSet<ElementoCalendario> elementosEntreFechas(LocalDateTime inicio, LocalDateTime fin){
        var elementos = new TreeSet<ElementoCalendario>((new OrdenarElementosPorHorario()));
        for (ElementoCalendario i : elementosCalendario) {
            i.agregarElementoAlSet(elementos,inicio,fin);
        }
        return elementos;
    }

    public EfectoAlarma sonarProximaAlarma(LocalDateTime fechaYHora, LocalDateTime fin){
        var elementos = elementosEntreFechas(fechaYHora,fin);
        if(elementos == null)
            return null;
        EfectoAlarma efecto = null;
        LocalDateTime date = fin;
        for (ElementoCalendario i : elementos){
            var horarioAlarma = i.proximaAlarma(fechaYHora);
            if (!horarioAlarma.isAfter(date)){
                date = horarioAlarma;
                efecto = i.sonarProximaAlarma(horarioAlarma);
            }
        }
        return efecto;
    }

}
