import java.time.LocalDateTime;
import java.util.TreeMap;

public class Tarea implements ElementoCalendario{

    private String titulo;
    private String descripcion;
    private boolean completado;
    private LocalDateTime vencimiento;

    private boolean esDeDiaCompleto;
    private TreeMap<LocalDateTime,Alarma> alarmas;

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

    @Override
    public void agregarAlarma(LocalDateTime horarioAlarma, Alarma.Efecto efecto) {

    }


    @Override
    public void eliminarAlarma(Alarma alarma) {

    }

    @Override
    public Alarma proximaAlarma(LocalDateTime dateTime) {

        return null;
    }

    public void setEstado(boolean completado) { this.completado = completado; }
    public void setFecha(LocalDateTime vencimiento) {
        this.vencimiento = vencimiento;
    }

    // Funcion a implementar en un futuro


    public boolean EsDeDiaCompleto() {
        return esDeDiaCompleto;
    }

}
