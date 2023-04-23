import java.time.Duration;
import java.time.LocalDateTime;
import java.util.TreeMap;

public class InstanciaEvento implements ElementoCalendario {

    //tendria que implementar ElementoCalendario??
    // Estaria bueno pero complica demasiado las cosas porque las repeticiones no tienen alarmas propias
    // Entonces no tiene sentido que implemente un monton de metodos que estan en ElementoCalendario asi que lo saco
    private final Evento evento;

    private final LocalDateTime fecha;
    private final TreeMap<LocalDateTime, Alarma> alarmasInstancia;

    public InstanciaEvento(Evento evento,LocalDateTime fecha) {
        this.evento = evento;
        this.fecha = fecha;
        this.alarmasInstancia = new TreeMap<>();
        cargarAlarmas();
    }

    public void cargarAlarmas(){
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

    @Override
    public void setTitulo(String titulo) {
        evento.setTitulo(titulo);
    }

    @Override
    public void setDescripcion(String descripcion) {
        evento.setDescripcion(descripcion);
    }

    @Override
    public void setFecha(LocalDateTime inicioEvento) {
        evento.setFecha(inicioEvento);
    }

    @Override
    public void setDeDiaCompleto() {
        evento.setDeDiaCompleto();
    }

    @Override
    public String getTitulo() {
        return evento.getTitulo();
    }

    @Override
    public String getDescripcion() {
        return evento.getDescripcion();
    }

    @Override
    public void asignarDeFechaArbitraria(LocalDateTime nuevaInicial) {
        evento.asignarDeFechaArbitraria(nuevaInicial);
    }

    @Override
    public LocalDateTime getFecha() {
        return fecha;
    }

    @Override
    public Alarma agregarAlarmaAbsoluta(LocalDateTime horarioAlarma) {
        return  evento.agregarAlarmaAbsoluta(horarioAlarma);
    }

    @Override
    public Alarma agregarAlarma(Duration intervalo) {
        var alarma = evento.agregarAlarma(intervalo);
        cargarAlarmas();
        return alarma;
    }

    //Quedo medio horrible esto, no tengo forma de rastrear la alarma original.
    //Tenemos que chusmear el patron observer para la proxima entrega
    @Override
    public void eliminarAlarma(Alarma alarma) {
        evento.eliminarAlarma(alarma);
        cargarAlarmas();  //??

    }

    @Override
    public LocalDateTime proximaAlarma(LocalDateTime dateTime) {
        var par = this.alarmasInstancia.ceilingEntry(dateTime);
        if(par == null)
            return  null;
        return par.getKey();
    }

    @Override
    public void modificarIntervaloAlarma(Alarma alarma, Duration intervalo) {
        evento.modificarIntervaloAlarma(alarma,intervalo);
        cargarAlarmas();

    }

    @Override
    public void modificarFechaAbsolutaAlarma(Alarma alarma, LocalDateTime fecha) {
       evento.modificarFechaAbsolutaAlarma(alarma,fecha);
    }

    @Override
    public void modificarAlarmaEfecto(Alarma alarma, EfectoAlarma efecto) {
        evento.modificarAlarmaEfecto(alarma,efecto);
        cargarAlarmas();
    }

    @Override
    public EfectoAlarma sonarProximaAlarma(LocalDateTime fecha) {

        var alarma = alarmasInstancia.get(proximaAlarma(fecha));

        return alarma.sonar(fecha);
    }

    @Override
    public boolean iniciaEntreLosHorarios(LocalDateTime inicio, LocalDateTime fin) {
        return esIgualOEstaEntre(inicio,fin,this.fecha);
    }

    private boolean esIgualOEstaEntre(LocalDateTime inicio, LocalDateTime fin, LocalDateTime t){
        return (t.equals(inicio) || t.isAfter(inicio)) && (t.equals(fin) || t.isBefore(fin));
    }
}
