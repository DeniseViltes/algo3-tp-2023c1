import java.time.Duration;
import java.time.LocalDateTime;
import java.util.TreeMap;

public class InstanciaEvento implements ElementoCalendario {

    //tendria que implementar ElementoCalendario??
    private final Evento evento;

    private LocalDateTime fecha;
    private TreeMap<LocalDateTime, Alarma> alarmasInstancia;

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
            if(!i.esDeFechaAbsoluta()) {
                i.setReferencia(fecha);
                alarmasInstancia.put(i.getFechaYHora(), i);
            }
        }
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
    public String getTitulo() {
        return evento.getTitulo();
    }

    @Override
    public String getDescripcion() {
        return evento.getDescripcion();
    }

    @Override
    public LocalDateTime getFecha() {
        return fecha;
    }

    @Override
    public Alarma agregarAlarmaAbsoluta(LocalDateTime horarioAlarma, EfectoAlarma efecto) {
        return  evento.agregarAlarmaAbsoluta(horarioAlarma,efecto);
    }

    @Override
    public Alarma agregarAlarma(Duration intervalo, EfectoAlarma efecto) {
        var alarma = evento.agregarAlarma(intervalo,efecto);
       cargarAlarmas();  //????
        return alarma;
    }

    //Quedo medio horrible esto, no tengo forma de ratrear la alarma original.
    //Tenemos que chusmear el patron observer para la proxima entrega
    @Override
    public void eliminarAlarma(Alarma alarma) {
        evento.eliminarAlarma(alarma);
        cargarAlarmas();  //??

    }

    @Override
    public Alarma proximaAlarma(LocalDateTime dateTime) {
        var par = this.alarmasInstancia.ceilingEntry(dateTime);
        if(par == null)
            return  null;
        return par.getValue();
    }

    @Override
    public void modificarIntervaloAlarma(Alarma alarma, Duration intervalo) {

    }

    @Override
    public void modificarFechaAbsolutaAlarma(Alarma alarma, LocalDateTime fecha) {

    }

    @Override
    public void modificarAlarmaEfecto(Alarma alarma, EfectoAlarma efecto) {

    }
}
