import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;


public abstract class ElementoCalendario {
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaYHoraCaracteristica;
    private boolean esDeDiaCompleto;

    private final TreeMap<LocalDateTime,Alarma> alarmas;

    public ElementoCalendario(LocalDateTime fechaYHoraCaracteristica) {
        this.fechaYHoraCaracteristica = fechaYHoraCaracteristica;
        this.descripcion = "";
        this.esDeDiaCompleto = false;
        this.alarmas = new TreeMap<>();
    }

    public boolean isEsDeDiaCompleto() {
        return esDeDiaCompleto;
    }

    void setTitulo(String titulo){
        this.titulo = titulo;
    }

    void setDescripcion(String descripcion){ this.descripcion = descripcion; }

    void  setFecha(LocalDateTime inicioEvento){
        var al = new TreeMap<>(alarmas);
        this.fechaYHoraCaracteristica = inicioEvento;
        alarmas.clear();
        for (Alarma i : al.values() ){
            modificarReferenciaAlarma(i,inicioEvento);
        }
    }
    void setDeDiaCompleto(){
        this.esDeDiaCompleto = true;
        this.alarmas.clear();
    }

    String getTitulo(){return titulo;}

    boolean comparar(ElementoCalendario elemento){
        return this.equals(elemento);
    }

    String getDescripcion(){return descripcion;}
    LocalDateTime getFecha(){return fechaYHoraCaracteristica;}
    void asignarDeFechaArbitraria(LocalDateTime nuevaInicial){
        this.esDeDiaCompleto = false;
        this.fechaYHoraCaracteristica = nuevaInicial;
        alarmas.clear();
    }

    Alarma agregarAlarmaAbsoluta(LocalDateTime horarioAlarma){
        var nueva = new Alarma(fechaYHoraCaracteristica,Duration.ZERO);
        nueva.setAlarmaAbsoluta(horarioAlarma);
        alarmas.put(horarioAlarma, nueva);
        return nueva;
    }
    Alarma agregarAlarma(Duration intervalo, LocalDateTime fecha){
        var nueva = new Alarma(fecha,intervalo);
        alarmas.put(nueva.getFechaYHora(),nueva);
        return nueva;
    }
    void eliminarAlarma(Alarma alarma){
        alarmas.remove(alarma.getFechaYHora());
    }
    LocalDateTime horarioProximaAlarma(LocalDateTime dateTime){
        var alarma = proximaAlarma(dateTime);
        if (alarma == null)
            return null;
        return alarma.getFechaYHora();
    }

    void modificarIntervaloAlarma (Alarma alarma, Duration intervalo){
        alarmas.remove(alarma.getFechaYHora());
        alarma.setIntervalo(intervalo);
        alarmas.put(alarma.getFechaYHora(), alarma);
    }

    void modificarFechaAbsolutaAlarma(Alarma alarma, LocalDateTime fecha){
        alarmas.remove(alarma.getFechaYHora());
        alarma.setAlarmaAbsoluta(fecha);
        alarmas.put(alarma.getFechaYHora(), alarma);
    }

    void modificarAlarmaEfecto (Alarma alarma, EfectoAlarma efecto){
        alarmas.remove(alarma.getFechaYHora());
        alarma.setEfecto(efecto);
        alarmas.put(alarma.getFechaYHora(), alarma);
    }

    boolean iniciaEntreLosHorarios(LocalDateTime inicio, LocalDateTime fin){
        return esIgualOEstaEntre(inicio,fin,this.fechaYHoraCaracteristica);
    }
    private boolean esIgualOEstaEntre(LocalDateTime inicio, LocalDateTime fin, LocalDateTime t){
        return (t.equals(inicio) || t.isAfter(inicio)) && (t.equals(fin) || t.isBefore(fin));
    }
    abstract void agregarElementoAlSet(Set<ElementoCalendario> elementos, LocalDateTime inicio, LocalDateTime fin);

    EfectoAlarma sonarProximaAlarma(LocalDateTime fecha){
        var alarma = proximaAlarma(fecha);
        if (alarma == null)
            return null;
        return  alarma.sonar(fecha);
    }

    private Alarma proximaAlarma(LocalDateTime fecha){
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
    void agregarAlarma(Alarma alarma){
        alarmas.put(alarma.getFechaYHora(),alarma);
    }

    public Collection<Alarma> getAlarmas() {
        return alarmas.values();
    }
}
