import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Evento {

    private String titulo;
    private String descripcion;
    private LocalDateTime fechaYHoraInicial;
    private LocalDateTime fechaYHoraFinal;
    private boolean esDeDiaCompleto;
    private ArrayList<Alarma> alarmas;

    private Repeticion repeticion;

    public Evento(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.alarmas = new ArrayList<>();

        //Dejo inicializado por ahora
        this.esDeDiaCompleto = false;
        this.fechaYHoraInicial = LocalDateTime.of(LocalDate.now(), LocalTime.of(8,0));
        this.fechaYHoraFinal = LocalDateTime.of(LocalDate.now(),LocalTime.of(9,0));
        this.repeticion = new Repeticion(Repeticion.Frecuencia.NOREPITE); //REVISAR
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaYHoraInicial(LocalDateTime fechaYHoraInicial) {
        this.fechaYHoraInicial = fechaYHoraInicial;
    }

    public void setFechaYHoraFinal(LocalDateTime fechaYHoraFinal) {
        this.fechaYHoraFinal = fechaYHoraFinal;
    }
    public void setEsDeDiaCompleto(boolean esDeDiaCompleto) {
        this.esDeDiaCompleto = esDeDiaCompleto;
    }

    public LocalDateTime getFechaYHoraInicial() {
        return fechaYHoraInicial;
    }

    public LocalDateTime getFechaYHoraFinal() {
        return fechaYHoraFinal;
    }

    public boolean isEsDeDiaCompleto() {
        return esDeDiaCompleto;
    }
}
