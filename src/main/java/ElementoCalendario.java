import java.time.Duration;
import java.time.LocalDateTime;

//Pruebo poner una interfaz asi  no tenemos que duplicar todo para tarea
public interface ElementoCalendario {
    void setTitulo(String titulo);

    void setDescripcion(String descripcion);

    void setFecha(LocalDateTime inicioEvento);
    void setDeDiaCompleto();
    void asignarDeFechaArbitraria(LocalDateTime nuevaInicial);

    LocalDateTime getFecha();
    Alarma agregarAlarmaAbsoluta(LocalDateTime horarioAlarma);
    Alarma agregarAlarma(Duration intervalo);
    void eliminarAlarma(Alarma alarma);
    LocalDateTime proximaAlarma(LocalDateTime dateTime);

    void modificarIntervaloAlarma (Alarma alarma, Duration intervalo);

    void modificarFechaAbsolutaAlarma(Alarma alarma, LocalDateTime fecha);

    void modificarAlarmaEfecto (Alarma alarma, EfectoAlarma efecto);

    boolean iniciaEntreLosHorarios(LocalDateTime inicio, LocalDateTime fin);

    EfectoAlarma sonarProximaAlarma(LocalDateTime fecha);

}
