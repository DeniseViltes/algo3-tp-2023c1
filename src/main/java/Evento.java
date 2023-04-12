import java.time.LocalDate;
import java.time.LocalTime;

public class Evento {

    private String titulo;
    private String descripcion;
    private LocalDate fecha;
    private LocalTime horaInicial;
    private LocalTime horaFinal;
    private boolean esDeDiaCompleto;
    public Evento(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;

        //Dejo inicializado por ahora
        this.fecha = LocalDate.now();
        this.horaInicial = LocalTime.of(8,0);
        this.horaFinal = LocalTime.of(9,0);
    }
    public String getTitulo() {
        return this.titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public boolean esEsDeDiaCompleto() {
        return esDeDiaCompleto;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setEsDeDiaCompleto(boolean esDeDiaCompleto) {
        this.esDeDiaCompleto = esDeDiaCompleto;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHoraInicial(LocalTime horaInicial) {
        this.horaInicial = horaInicial;
    }

    public void setHoraFinal(LocalTime horaFinal) {
        this.horaFinal = horaFinal;
    }
}
