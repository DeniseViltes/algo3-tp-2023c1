import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Tarea {

    private String titulo;
    private String descripcion;
    private boolean completado;

    private boolean esDeDiaCompleto;
    private LocalDateTime vencimiento;

    public Tarea(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completado = false;

        //igual que en evento, dejo inicializado asi por ahora
        // en google calendar se trunca media hora, no la hora completa
        this.esDeDiaCompleto = false;
        this.vencimiento = LocalDateTime.of(LocalDate.now(),LocalTime.now().truncatedTo(ChronoUnit.HOURS).plusMinutes(60));
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void completarTarea() {
        this.completado = true;
    }

    public LocalDateTime getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(LocalDateTime vencimiento) {
        this.vencimiento = vencimiento;
    }

    public boolean isEsDeDiaCompleto() {
        return esDeDiaCompleto;
    }

    public void setEsDeDiaCompleto(boolean esDeDiaCompleto) {
        this.esDeDiaCompleto = esDeDiaCompleto;
    }
}
