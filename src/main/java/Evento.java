import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Evento {

    private String titulo;
    private String descripcion;

    private LocalDateTime fechaYHoraInicial;
    private Duration duration; //o fecha?
    private boolean esDeDiaCompleto;
    private final ArrayList<Alarma> alarmas;
    private Repeticion repeticion;

    public Evento() {
        //Dejo asi inicializado por ahora
        this.titulo = "My Event";
        this.descripcion = null;
        this.alarmas = new ArrayList<>();
        var horaActualTruncada = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        this.fechaYHoraInicial = horaActualTruncada.plusHours(1);
        this.duration = Duration.ofHours(1);
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

    public void setEsDeDiaCompleto(boolean esDeDiaCompleto) {
        this.esDeDiaCompleto = true;
        this.fechaYHoraInicial = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        this.duration = Duration.ofDays(1);
    }


    public void setFechaYHoraInicial(LocalDateTime fechaYHoraInicial) {
        if (esDeDiaCompleto)
            this.fechaYHoraInicial = fechaYHoraInicial.truncatedTo(ChronoUnit.DAYS); //probar
        this.fechaYHoraInicial = fechaYHoraInicial;
    }

    public void setDuration(Duration duracion ) {
    //long, String, Duration?-> ver funcior parse
    if (esDeDiaCompleto) {
        this.duration = duracion.truncatedTo(ChronoUnit.DAYS);
    }

    this.duration = duracion;
    }
}
