import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Tarea {

    private String titulo;
    private String descripcion;
    private boolean completado;
    //Corsi dijo que se podia hacer subclases para diferenciar los tipos de tares, vale la pena??
    private boolean esDeDiaCompleto;
    private LocalDateTime vencimiento;

    private ArrayList<Alarma> alarmas;

    public Tarea(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completado = false;
        this.alarmas = new ArrayList<>();

        //igual que en evento, dejo inicializado asi por ahora
        // en google calendar se trunca media hora, no la hora completa
        var horaActualTruncada = LocalTime.now().truncatedTo(ChronoUnit.HOURS);
        this.esDeDiaCompleto = false;
        this.vencimiento = LocalDateTime.of(LocalDate.now(),horaActualTruncada.plusHours(1));
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
