import java.time.Duration;
import java.time.LocalDateTime;

//Pruebo poner una interfaz asi  no tenemos que duplicar todo para tarea
public interface ElementoCalendario {
    void setTitulo(String titulo);

    void setDescripcion(String descripcion);

    void setFecha(LocalDateTime inicioEvento);

    String getTitulo ();
    String getDescripcion ();

    LocalDateTime getFecha();
    Alarma agregarAlarmaAbsoluta(LocalDateTime horarioAlarma, EfectoAlarma efecto);
    Alarma agregarAlarma(Duration intervalo, EfectoAlarma efecto);
    void eliminarAlarma(Alarma alarma);
    Alarma proximaAlarma(LocalDateTime dateTime);

    void modificarIntervaloAlarma (Alarma alarma, Duration intervalo);

    void modificarFechaAbsolutaAlarma(Alarma alarma, LocalDateTime fecha);

    void modificarAlarmaEfecto (Alarma alarma, EfectoAlarma efecto);
}
