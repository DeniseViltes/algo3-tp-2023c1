import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeMap;

public class InstanciaEvento implements ElementoCalendario {


    private final Evento evento;

    private LocalDateTime fecha;
    private final TreeMap<LocalDateTime, Alarma> alarmasInstancia;

    public InstanciaEvento(ElementoCalendario elemento,LocalDateTime fecha) {
        this.evento = (Evento) elemento;
        this.fecha = fecha;
        this.alarmasInstancia = new TreeMap<>();
        cargarAlarmas();
    }

    private void cargarAlarmas(){
        alarmasInstancia.clear();
        var alarmasOriginales = evento.getAlarmas();
        for (Alarma i : alarmasOriginales.values()){
            var nueva = i.copiarConNuevaReferencia(fecha);
            if(i.esDeFechaAbsoluta()) {
                var nuevaFechaAbs = calcularFechaAbsoluta(i.getFechaYHora());
                nueva.setAlarmaAbsoluta(nuevaFechaAbs);
            }
            alarmasInstancia.put(nueva.getFechaYHora(), nueva);
        }
    }

    private LocalDateTime calcularFechaAbsoluta(LocalDateTime fechaAlarmaAbs){
        var intervalo = Duration.between(evento.getFecha(),fecha);
        return  fechaAlarmaAbs.plus(intervalo);
    }


    public void setTitulo(String titulo) {
        evento.setTitulo(titulo);
    }


    public void setDescripcion(String descripcion) {
        evento.setDescripcion(descripcion);
    }


    public void setFecha(LocalDateTime inicioEvento) {
        if(this.evento.getFecha() == this.fecha)
            this.fecha = inicioEvento;
        evento.setFecha(inicioEvento);
    }


    public void setDeDiaCompleto() {
        evento.setDeDiaCompleto();
    }


    public String getTitulo() {
        return evento.getTitulo();
    }


    public String getDescripcion() {
        return evento.getDescripcion();
    }


    public void asignarDeFechaArbitraria(LocalDateTime nuevaInicial) {
        evento.asignarDeFechaArbitraria(nuevaInicial);
    }


    public LocalDateTime getFecha() {
        return fecha;
    }


    public Alarma agregarAlarmaAbsoluta(LocalDateTime horarioAlarma) {
       var alarma = evento.agregarAlarmaAbsoluta(horarioAlarma);
        cargarAlarmas();
        return alarma;
    }


    public Alarma agregarAlarma(Duration intervalo) {
        var alarma = evento.agregarAlarma(intervalo);
        cargarAlarmas();
        return alarma;
    }



    public void eliminarAlarma(Alarma alarma) {
        evento.eliminarAlarma(alarma);
        cargarAlarmas();

    }


    public LocalDateTime proximaAlarma(LocalDateTime dateTime) {
        var par = this.alarmasInstancia.ceilingEntry(dateTime);
        if(par == null)
            return  null;
        return par.getKey();
    }

    public void modificarIntervaloAlarma(Alarma alarma, Duration intervalo) {
        evento.modificarIntervaloAlarma(alarma,intervalo);
        cargarAlarmas();

    }

    public void modificarFechaAbsolutaAlarma(Alarma alarma, LocalDateTime fecha) {
       evento.modificarFechaAbsolutaAlarma(alarma,fecha);
    }

    public void modificarAlarmaEfecto(Alarma alarma, EfectoAlarma efecto) {
        evento.modificarAlarmaEfecto(alarma,efecto);
        cargarAlarmas();
    }


    public EfectoAlarma sonarProximaAlarma(LocalDateTime fecha) {

        var alarma = alarmasInstancia.get(proximaAlarma(fecha));

        return alarma.sonar(fecha);
    }


    public boolean iniciaEntreLosHorarios(LocalDateTime inicio, LocalDateTime fin) {
        return esIgualOEstaEntre(inicio,fin,this.fecha);
    }

    @Override
    public boolean tieneRepeticionEntreLosHorarios(LocalDateTime inicio, LocalDateTime fin) {
        return false;
    }

    @Override
    public LocalDateTime proximaRepeticion(LocalDateTime inicio) {
        return null;
    }

    @Override
    public void añadirElementoAlSet(Set<ElementoCalendario> elementos) {
        elementos.add(this);
    }

    private boolean esIgualOEstaEntre(LocalDateTime inicio, LocalDateTime fin, LocalDateTime t){
        return (t.equals(inicio) || t.isAfter(inicio)) && (t.equals(fin) || t.isBefore(fin));
    }

    public Evento getEvento() {
        return evento;
    }
}
