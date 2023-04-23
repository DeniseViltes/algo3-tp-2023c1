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
    public void setEsDeDiaCompleto(boolean esDeDiaCompleto) {
        this.esDeDiaCompleto = esDeDiaCompleto;
    }

    public boolean iniciaEntreLosHorarios(LocalDateTime inicio, LocalDateTime fin){
        return esIgualOEstaEntre(inicio,fin,this.vencimiento);
    }
    public void setEstado(boolean completado) { this.completado = completado; }

    @Override
    public EfectoAlarma sonarProximaAlarma(LocalDateTime fecha) {
        return null;
    }

    private boolean esIgualOEstaEntre(LocalDateTime inicio, LocalDateTime fin, LocalDateTime t){
        return (t.equals(inicio) || t.isAfter(inicio)) && (t.equals(fin) || t.isBefore(fin));
    }

    @Override
    public void eliminarAlarma(Alarma alarma) {
        alarmas.remove(alarma.getFechaYHora());
    }

    @Override
    public LocalDateTime proximaAlarma(LocalDateTime dateTime) {
        var par = this.alarmas.ceilingEntry(dateTime);
        if(par == null)
            return  null;
        return par.getKey();
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

    public void setFecha(LocalDateTime vencimiento) {
        var al = new TreeMap<>(alarmas);
        alarmas.clear();
        this.vencimiento = vencimiento;
        for (Alarma i : al.values() ){
            modificarReferenciaAlarma(i,vencimiento);
        }
    }
    private void modificarReferenciaAlarma (Alarma alarma, LocalDateTime referencia){
        alarmas.remove(alarma.getFechaYHora());
        alarma.setReferencia(referencia);
        alarmas.put(alarma.getFechaYHora(), alarma);
    }

    @Override
    public LocalDateTime getFecha() {
        return this.vencimiento;
    }

    @Override
    public Alarma agregarAlarmaAbsoluta(LocalDateTime horarioAlarma) {
        var nueva = new Alarma(this.vencimiento);
        nueva.setAlarmaAbsoluta(horarioAlarma);
        alarmas.put(horarioAlarma, nueva);
        return nueva;
    }

    @Override
    public Alarma agregarAlarma(Duration intervalo) {
        var nueva = new Alarma(this.vencimiento);
        nueva.setIntervalo(intervalo);
        alarmas.put(nueva.getFechaYHora(),nueva);
        return nueva;
    }

    public void setDeDiaCompleto(){
        this.esDeDiaCompleto = true;
        var nuevoVencimiento = this.vencimiento.toLocalDate();
        this.vencimiento = nuevoVencimiento.atTime(23,59);
        this.alarmas.clear();
    }

    public void asignarDeFechaArbitraria(LocalDateTime nuevoVencimiento){

        this.esDeDiaCompleto = false;
        this.vencimiento = nuevoVencimiento;
        //en calendar no se puede poner alarmas a las tareas
        this.alarmas.clear();
        var nuevaAlarma = new Alarma(nuevoVencimiento);
        this.alarmas.put(nuevaAlarma.getFechaYHora(),nuevaAlarma);
    }

}
