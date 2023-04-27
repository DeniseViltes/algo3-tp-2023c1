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
    Crea un evento nuevo apartir de una fecha dada, con una duración de 1 hora,
    este evento incia solo con un titulo y una alarma default (10 minutos antes del inicio)
     */
    public Evento(LocalDateTime inicioEvento) {
        this.titulo = "My Event";
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
        if(esDeDiaCompleto)
            this.fechaYHoraInicial = inicioEvento.truncatedTo(ChronoUnit.DAYS);
        else this.fechaYHoraInicial = inicioEvento;
        for (Alarma i : al.values() ){
            modificarReferenciaAlarma(i,inicioEvento);
        }
    }
    /*
    Cuando se marca el evento como de dia completo, si el evento previamiente
    tenia una duración mayor a un dia, esto se mantiene.
    Ademas se eliminan todas las alarmas previas.
     */
    public void setDeDiaCompleto(){
        this.esDeDiaCompleto = true;
        var nuevaInicial = this.fechaYHoraInicial.toLocalDate();
        this.fechaYHoraInicial = nuevaInicial.atStartOfDay();
        if (duracion.compareTo(Duration.ofDays(1))<0)
            this.duracion = Duration.ofHours(23).plusMinutes(59);
        else this.duracion = this.duracion.truncatedTo(ChronoUnit.DAYS).plusHours(23).plusMinutes(59);
        this.alarmas.clear();
    }

    public void asignarDeFechaArbitraria(LocalDateTime nuevaInicial){

        this.esDeDiaCompleto = false;
        this.fechaYHoraInicial = nuevaInicial;
        this.duracion = this.duracion.truncatedTo(ChronoUnit.DAYS).plusMinutes(30);

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


    public Alarma agregarAlarmaAbsoluta(LocalDateTime horarioAlarma) {
        var nueva = new Alarma(this.fechaYHoraInicial);
        nueva.setAlarmaAbsoluta(horarioAlarma);
        alarmas.put(horarioAlarma, nueva);
        return nueva;
    }

    public Alarma agregarAlarma(Duration intervalo) {
        var nueva = new Alarma(this.fechaYHoraInicial);
        nueva.setIntervalo(intervalo);
        alarmas.put(nueva.getFechaYHora(),nueva);
        return nueva;
    }


    public void eliminarAlarma(Alarma alarma) {
        alarmas.remove(alarma.getFechaYHora());
    }


    public LocalDateTime proximaAlarma(LocalDateTime fecha) {
        var alarma = proximaAlarmaEvento(fecha);
        if (alarma == null)
                return null;
        return alarma.getFechaYHora();
    }

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


    private void modificarReferenciaAlarma (Alarma alarma, LocalDateTime referencia){
        alarmas.remove(alarma.getFechaYHora());
        alarma.setReferencia(referencia);
        alarmas.put(alarma.getFechaYHora(), alarma);
    }


    public void modificarIntervaloAlarma(Alarma alarma, Duration intervalo) {
        alarmas.remove(alarma.getFechaYHora());
        alarma.setIntervalo(intervalo);
        alarmas.put(alarma.getFechaYHora(), alarma);
    }


    public void modificarFechaAbsolutaAlarma(Alarma alarma, LocalDateTime fecha) {
        alarmas.remove(alarma.getFechaYHora());
        alarma.setAlarmaAbsoluta(fecha);
        alarmas.put(alarma.getFechaYHora(), alarma);
    }


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
        if(repeticion == null)
                return;
        this.repeticion.setVencimiento(vencimiento);
    }

    public void setRepeticionCantidad(int cantidadRepeticiones){
        if(repeticion == null)
            return;
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
        if (repeticion == null)
                return null;
        return repeticion.Repetir(inicio);
    }

    @Override
    public void añadirElementoAlSet(Set<ElementoCalendario> elementos) {
        elementos.add(new InstanciaEvento((Evento) this,this.getFecha()));
    }

    public TreeMap<LocalDateTime, Alarma> getAlarmas() {
        return alarmas;
    }

    public Repeticion getRepeticion() {
        return repeticion;
    }
}
