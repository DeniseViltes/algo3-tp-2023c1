import java.time.Duration;
import java.time.LocalDateTime;
import java.util.TreeMap;

public class Tarea implements ElementoCalendario{

    private String titulo;
    private String descripcion;
    private boolean completado;
    private LocalDateTime vencimiento;

    private boolean esDeDiaCompleto;
    private final TreeMap<LocalDateTime,Alarma> alarmas;


    /*
    Crea una Tarea  incompleta a partir de una fecha dada, sin alarmas
     */
    public Tarea(LocalDateTime vencimiento) {
        this.titulo = null;
        this.descripcion = null;
        this.completado = false;
        this.vencimiento = vencimiento;
        this.esDeDiaCompleto = false;

        this.alarmas = new TreeMap<>();
    }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setDeDiaCompleto() {
        this.esDeDiaCompleto = true;
        this.vencimiento = fechaAlFinalDelDia(this.vencimiento);
        this.alarmas.clear();
    }
     
    public LocalDateTime getFecha() {
        return this.vencimiento;
    }

    public boolean iniciaEntreLosHorarios(LocalDateTime inicio, LocalDateTime fin){
        return esIgualOEstaEntre(inicio,fin,this.vencimiento);
    }

    public void completar(){
        this.completado = true;
    }
    public void descompletar(){
        this.completado = false;
    }

     
    public String getTitulo() {
        return titulo;
    }

     
    public String getDescripcion() {
        return descripcion;
    }

    public void asignarDeFechaArbitraria(LocalDateTime nuevoVencimiento){
        this.esDeDiaCompleto = false;
        this.vencimiento = nuevoVencimiento;
        this.alarmas.clear();
    }

    public boolean estaCompleta (){
        return completado;
    }
     
    public EfectoAlarma sonarProximaAlarma(LocalDateTime fecha) {
        var horarioProxAlarma = proximaAlarma(fecha);
        if(horarioProxAlarma == null)
            return null;
        var alarma = alarmas.get(horarioProxAlarma);
        return alarma.sonar(fecha);
    }


    private boolean esIgualOEstaEntre(LocalDateTime inicio, LocalDateTime fin, LocalDateTime t){
        return (t.equals(inicio) || t.isAfter(inicio)) && (t.equals(fin) || t.isBefore(fin));
    }

     
    public void eliminarAlarma(Alarma alarma) {
        alarmas.remove(alarma.getFechaYHora());
    }

     
    public LocalDateTime proximaAlarma(LocalDateTime dateTime) {
        var par = this.alarmas.ceilingEntry(dateTime);
        if(par == null)
            return  null;
        return par.getKey();
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

    public void setFecha(LocalDateTime vencimiento) {
        var al = new TreeMap<>(alarmas);
        this.vencimiento = vencimiento;
        alarmas.clear();
        if (esDeDiaCompleto)
            this.vencimiento = fechaAlFinalDelDia(vencimiento);
        for (Alarma i : al.values() ){
            modificarReferenciaAlarma(i,vencimiento);
        }
    }

    private LocalDateTime fechaAlFinalDelDia(LocalDateTime fechaYHora){
        var fecha = fechaYHora.toLocalDate().atStartOfDay();
        var duracionUnDia = Duration.ofHours(23).plusMinutes(59);
        return fecha.plus(duracionUnDia);
    }
    private void modificarReferenciaAlarma (Alarma alarma, LocalDateTime referencia){
        alarmas.remove(alarma.getFechaYHora());
        alarma.setReferencia(referencia);
        alarmas.put(alarma.getFechaYHora(), alarma);
    }


     
    public Alarma agregarAlarmaAbsoluta(LocalDateTime horarioAlarma) {
        var nueva = new Alarma(this.vencimiento);
        nueva.setAlarmaAbsoluta(horarioAlarma);
        alarmas.put(horarioAlarma, nueva);
        return nueva;
    }

     
    public Alarma agregarAlarma(Duration intervalo) {
        var nueva = new Alarma(this.vencimiento);
        nueva.setIntervalo(intervalo);
        alarmas.put(nueva.getFechaYHora(), nueva);
        return nueva;
    }



}
