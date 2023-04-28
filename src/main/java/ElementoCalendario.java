import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;


public interface ElementoCalendario {
    void setTitulo(String titulo);

    void setDescripcion(String descripcion);

    void setFecha(LocalDateTime inicioEvento);
    void setDeDiaCompleto();

    String getTitulo();

    String getDescripcion();
    LocalDateTime getFecha();
    void asignarDeFechaArbitraria(LocalDateTime nuevaInicial);

    Alarma agregarAlarmaAbsoluta(LocalDateTime horarioAlarma);
    Alarma agregarAlarma(Duration intervalo);
    void eliminarAlarma(Alarma alarma);
    LocalDateTime proximaAlarma(LocalDateTime dateTime);

    void modificarIntervaloAlarma (Alarma alarma, Duration intervalo);

    void modificarFechaAbsolutaAlarma(Alarma alarma, LocalDateTime fecha);

    void modificarAlarmaEfecto (Alarma alarma, EfectoAlarma efecto);

    boolean iniciaEntreLosHorarios(LocalDateTime inicio, LocalDateTime fin);

    void agregarElementoAlSet(Set<ElementoCalendario> elementos, LocalDateTime inicio, LocalDateTime fin);

    EfectoAlarma sonarProximaAlarma(LocalDateTime fecha);

}
