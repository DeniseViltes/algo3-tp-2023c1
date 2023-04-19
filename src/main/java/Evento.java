import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeMap;

public class Evento implements ElementoCalendario {

    private String titulo;
    private String descripcion;
    private LocalDateTime fechaYHoraInicial;

    //creo que al final para la repeticion es más facil teniendo la duracion, pero por ahora funciona la fecha final
    private Duration duracionMinutos;
    private boolean esDeDiaCompleto;
    private Repeticion repeticion;
    private final TreeMap<LocalDateTime,Alarma> alarmas;
        /*
        no puede haber alarmas repetidas, o mejor dicho que suenen al mismo horario?
        en calendar si se puede poner dos alarmas al mismo horario (con distintos efectos),
         pero por lo que estuve lo que  estuve leyendo en Slack, si llamas a proxima alarma
         te devuelve una alarma

        treeMap, hashMap???? Depende de si se aceptan horarios repetidos
        pongo treeMap porque las ordena, asi que supongo que va a simplificar buscar la prox alarma

        https://guava.dev/releases/23.0/api/docs/com/google/common/collect/TreeMultimap.html
         un TreeMap que acepta keys duplicadas? por lo que vi en la documentacion no habría problema
        con LocalDateTime
        */


    public Evento(LocalDateTime inicioEvento) {
        this.titulo = null;
        this.descripcion = null;
        this.fechaYHoraInicial = inicioEvento;
        this.duracionMinutos=  Duration.ofHours(1);
        this.repeticion = null;
        this.esDeDiaCompleto = false;

        this.alarmas = new TreeMap<>();
        //ordeno las alarmas segun el horario en el que suenan?
        var alarmaDefault = new Alarma(inicioEvento.minusMinutes(10), Alarma.Efecto.NOTIFICACION);
        this.alarmas.put(alarmaDefault.getFechaYHora(), alarmaDefault);
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setFecha(LocalDateTime inicioEvento){ this.fechaYHoraInicial = inicioEvento; }
    public void setDuracion(Duration duracionMinutos){ this.duracionMinutos = duracionMinutos; }
    public void setEsDeDiaCompleto(boolean diaCompleto){
        this.esDeDiaCompleto = diaCompleto;
    }

    @Override
    public void agregarAlarma(LocalDateTime horarioAlarma, Alarma.Efecto efecto) {
        var nueva = new Alarma(horarioAlarma,efecto);
        alarmas.put(horarioAlarma,nueva);
    }

    @Override
    public void eliminarAlarma(Alarma alarma) {
        alarmas.remove(alarma.getFechaYHora());
    }

    @Override
    public Alarma proximaAlarma(LocalDateTime dateTime) {
        var par = this.alarmas.ceilingEntry(dateTime);
        return par.getValue();
    }

    public void setRepeticionDiaria(Integer intervalo){
        this.repeticion = new RepeticionDiaria(intervalo);
    }
    public void setRepeticionSemanal(Set<DayOfWeek> dias){
        this.repeticion = new RepeticionSemanal(dias);
    }
    public void setRepeticionMensual(){
        this.repeticion = new RepeticionMensual();
    }
    public void setRepeticionAnual(){
        this.repeticion = new RepeticionAnual();
    }

    public void setRepeticionVencimiento(LocalDateTime vencimiento){
        this.repeticion.setVencimiento(vencimiento);
    }
    public void setRepeticionCantidad(Integer cantidadRepeticiones){
        this.repeticion.setCantidadRepeticiones(fechaYHoraInicial, cantidadRepeticiones);
    }
    public void setRepeticionInfinita(){
        this.repeticion.setRepeticionInfinita();
    }


    public boolean iniciaEntreLosHorarios(LocalDateTime inicio, LocalDateTime fin){
        //varifica si la fecha inicial del evento está entre las fechas dadas
        return esIgualOEstaEntre(inicio,fin,this.fechaYHoraInicial);
    }

    public boolean tieneRepeticionEntreLosHorarios(LocalDateTime inicio, LocalDateTime fin){
        if(repeticion == null)
            return false;
        var fechaRepeticion = repeticion.Repetir(inicio);
        if(fechaRepeticion == null)
            return  false;
        return esIgualOEstaEntre(inicio,fin,fechaRepeticion);
    }

    private boolean esIgualOEstaEntre(LocalDateTime inicio, LocalDateTime fin, LocalDateTime t){
        return (t.equals(inicio) || t.isAfter(inicio)) && (t.equals(fin) || t.isBefore(fin));
    }


    public LocalDateTime getFechaInicial() {
        return fechaYHoraInicial;
    }

    public boolean tieneRepeticion(){
        return repeticion != null;
    }

    public  LocalDateTime proximaRepeticion (LocalDateTime inicio){
        return repeticion.Repetir(inicio);
    }
}
