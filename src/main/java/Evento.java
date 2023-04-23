import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public Evento(LocalDateTime inicioEvento) {
        this.titulo = "My Event";  //le pongo esto asi queda un poco más lindo
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


    public LocalDateTime getFechaYHoraFinal(){
        return this.fechaYHoraInicial.plus(this.duracion);
    }

    public void setFecha(LocalDateTime inicioEvento){
        var al = new TreeMap<>(alarmas);
        alarmas.clear();
        this.fechaYHoraInicial = inicioEvento;
        for (Alarma i : al.values() ){
            modificarReferenciaAlarma(i,inicioEvento);
        }
    }
    public void setDeDiaCompleto(){
        //las alarmas de dia completo suenan un dia antes a las 9 de la mañana?
        this.esDeDiaCompleto = true;
        var nuevaInicial = this.fechaYHoraInicial.toLocalDate();
        //esto lo pongo asi por ahora, por lo que parece cuando se guarda crea el evento ya no te
        //guarda la hora en la que estaba antes, solo se mantienen las fechas
        //además así nos aseguramos de que cumpla el horario que tiene que durar un día completo
        this.fechaYHoraInicial = nuevaInicial.atStartOfDay();
        if (duracion.compareTo(Duration.ofDays(1))<0)
            this.duracion = Duration.ofHours(23).plusMinutes(59);
        else this.duracion = this.duracion.truncatedTo(ChronoUnit.DAYS);
        //En google calendar directamente elimina TODAS las alarmas cuando se modifica esto
        this.alarmas.clear();
    }
    public void asignarDeFechaArbitraria(){

        this.esDeDiaCompleto = false;
        var horaInicial = LocalTime.of(8,0);
        var nuevaInicial = LocalDateTime.of(this.fechaYHoraInicial.toLocalDate(),horaInicial);
        this.fechaYHoraInicial = nuevaInicial;

        //google calendar saca todas las alarmas y deja una de 10 min;
        this.alarmas.clear();
        var nuevaAlarma = new Alarma(nuevaInicial);
        this.alarmas.put(nuevaAlarma.getFechaYHora(),nuevaAlarma);
    }


    public String getTitulo() {
        return this.titulo;
    }


    public String getDescripcion() {
        return this.descripcion;
    }


    public int cantidadDeAlarmas(){
        return alarmas.size();
    }

    @Override
    public Alarma agregarAlarmaAbsoluta(LocalDateTime horarioAlarma) {
        var nueva = new Alarma(this.fechaYHoraInicial);
        nueva.setAlarmaAbsoluta(horarioAlarma);
        alarmas.put(horarioAlarma, nueva);
        return nueva;
    }
    @Override
    public Alarma agregarAlarma(Duration intervalo) {
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
    public LocalDateTime proximaAlarma(LocalDateTime fecha) {
        var alarma = proximaAlarmaEvento(fecha);
        if (alarma == null)
                return null;
        return alarma.getFechaYHora();
    }
    @Override
    public EfectoAlarma sonarProximaAlarma(LocalDateTime fecha){
        var alarma = proximaAlarmaEvento(fecha);
        if (alarma == null)
            return null;
        return  alarma.sonar(fecha);
    }

    private Alarma proximaAlarmaEvento(LocalDateTime fecha){
        var par = this.alarmas.ceilingEntry(fecha);
        if(par == null)
            return  null;
        return par.getValue();

    }

    private void modificarReferenciaAlarma (Alarma alarma, LocalDateTime referncia){
        alarmas.remove(alarma.getFechaYHora());
        alarma.setReferencia(referncia);
        alarmas.put(alarma.getFechaYHora(), alarma);
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
    public void modificarAlarmaEfecto(Alarma alarma, EfectoAlarma efecto) {
        alarmas.remove(alarma.getFechaYHora());
        alarma.setEfecto(efecto);
        alarmas.put(alarma.getFechaYHora(), alarma);

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

    // me parece que es un poco peligroso poner cantidad como Integer
    public void setRepeticionCantidad(int cantidadRepeticiones){
        this.repeticion.setCantidadRepeticiones(fechaYHoraInicial, cantidadRepeticiones);
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


    public LocalDateTime getFecha() {
        return fechaYHoraInicial;
    }

    public  LocalDateTime proximaRepeticion (LocalDateTime inicio){
        return repeticion.Repetir(inicio);
    }

    public TreeMap<LocalDateTime, Alarma> getAlarmas() {
        return alarmas;
    }

}
