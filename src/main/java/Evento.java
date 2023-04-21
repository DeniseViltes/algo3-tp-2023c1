import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.TreeMap;

public class Evento implements ElementoCalendario {

    private String titulo;
    private String descripcion;
    private LocalDateTime fechaYHoraInicial;
    private Duration duracion;
    private boolean esDeDiaCompleto;
    private Repeticion repeticion;

    private final TreeMap<LocalDateTime,Alarma> alarmas;
        /*
        no puede haber alarmas repetidas, o mejor dicho que suenen al mismo horario?
        en calendar si se puede poner dos alarmas al mismo horario (con distintos efectos),
         pero por lo que estuve lo que  estuve leyendo en Slack, si llamas a proxima alarma
         te devuelve una alarma

        https://guava.dev/releases/23.0/api/docs/com/google/common/collect/TreeMultimap.html
         un TreeMap que acepta keys duplicadas? por lo que vi en la documentacion no habría problema
        con LocalDateTime
        */


    public Evento(LocalDateTime inicioEvento) {
        this.titulo = null;
        this.descripcion = null;
        this.fechaYHoraInicial = inicioEvento;
        this.duracion =  Duration.ofHours(1);
        this.repeticion = null;
        this.esDeDiaCompleto = false;

        this.alarmas = new TreeMap<>();
        var alarmaDefault = new Alarma(inicioEvento);
        this.alarmas.put(alarmaDefault.getFechaYHora(),alarmaDefault);
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setDuracion(Duration duracionMinutos){
        this.duracion = duracionMinutos;
    }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }




    public void setFecha(LocalDateTime inicioEvento){
        this.fechaYHoraInicial = inicioEvento;
    }
    public void setEsDeDiaCompleto(boolean diaCompleto){
        //las alarmas de dia completo suenan un dia antes a las 9 de la mañana?
        this.fechaYHoraInicial = this.fechaYHoraInicial.truncatedTo(ChronoUnit.DAYS);
        this.esDeDiaCompleto = diaCompleto;
        //hay que actualizar todos los horarios de las alarmas?????
    }



    @Override
    public void agregarAlarmaAbsoluta(LocalDateTime horarioAlarma, Alarma.Efecto efecto) {
        var nueva = new Alarma(this.fechaYHoraInicial);
        nueva.setAlarmaAbsoluta(horarioAlarma);
        alarmas.put(horarioAlarma, nueva);
    }
    @Override
    public Alarma agregarAlarma(Duration intervalo, Alarma.Efecto efecto) {
        var nueva = new Alarma(this.fechaYHoraInicial);
        nueva.setIntervalo(intervalo);
        alarmas.put(nueva.getFechaYHora(),nueva);
        return nueva;
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

    @Override
    public void modificarIntervaloAlarma(Alarma alarma, Duration intervalo) {
        alarmas.remove(alarma.getFechaYHora());
        alarma.setIntervalo(intervalo);
        alarmas.put(alarma.getFechaYHora(), alarma);
    }

    @Override
    public void modificarFechaAbsolutaAlarma(Alarma alarma, LocalDateTime fecha) {
        alarmas.remove(alarma.getFechaYHora());
        alarma.setAlarmaAbsoluta(fecha);
        alarmas.put(alarma.getFechaYHora(), alarma);
    }

    @Override
    public void modificarAlarmaEfecto(Alarma alarma, Alarma.Efecto efecto) {
        alarma.setEfecto(efecto);
    }
/*

       Comento esto por ahora asi no tenemos que hacer una modificación (en Calendario) para cada tipo,
       sino que se reciba el tipo de repeticion y se maneje con polimorfismo, no se si es la mejor opcion,
        pero va a quedar mejor desde calendario.


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
    }*/


    public void setRepeticion(Repeticion tipo){
        this.repeticion = tipo;
    }

    public void setRepeticionVencimiento(LocalDateTime vencimiento){
        this.repeticion.setVencimiento(vencimiento);
    }

    // me parece que es un poco peligroso poner cantidad como Integer
    public void setRepeticionCantidad(Integer cantidadRepeticiones){
        this.repeticion.setCantidadRepeticiones(fechaYHoraInicial, cantidadRepeticiones);
    }
    public void setRepeticionInfinita(){
        this.repeticion.setRepeticionInfinita();
    }

    public void eliminarRepeticion (){
        this.repeticion = null;
    }

    public boolean iniciaEntreLosHorarios(LocalDateTime inicio, LocalDateTime fin){
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

    public  LocalDateTime proximaRepeticion (LocalDateTime inicio){
        return repeticion.Repetir(inicio);
    }

    public TreeMap<LocalDateTime, Alarma> getAlarmas() {
        return alarmas;
    }
}
