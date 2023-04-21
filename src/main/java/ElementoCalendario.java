import java.time.Duration;
import java.time.LocalDateTime;

//Pruebo poner una interfaz asi  no tenemos que duplicar todo para tarea
public interface ElementoCalendario {

    void setTitulo(String titulo);

    void setDescripcion(String descripcion);

    void setFecha(LocalDateTime inicioEvento);

    void setEsDeDiaCompleto(boolean diaCompleto);

    //creo que el evento viene con una alarma default siempre
    void agregarAlarmaAbsoluta(LocalDateTime horarioAlarma, Alarma.Efecto efecto);
    Alarma agregarAlarma(Duration intervalo, Alarma.Efecto efecto);
    void eliminarAlarma(Alarma alarma);
    Alarma proximaAlarma(LocalDateTime dateTime);

    void modificarIntervaloAlarma (Alarma alarma, Duration intervalo);

    void modificarFechaAbsolutaAlarma(Alarma alarma, LocalDateTime fecha);

    void modificarAlarmaEfecto (Alarma alarma, Alarma.Efecto efecto);
}
