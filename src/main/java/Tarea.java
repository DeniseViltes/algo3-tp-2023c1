import java.time.LocalDateTime;
import java.util.ArrayList;

public class Tarea implements ElementoCalendario{

    private String titulo;
    private String descripcion;
    private boolean completado;
    private LocalDateTime vencimiento;

    //Corsi dijo que se podia hacer subclases para diferenciar los tipos de tares, vale la pena??
    // No se, quizas es mas prolijo hacerlo con subclases, por ahora yo seguiria con el boolean
    // Y en un futuro, vemos.
    private boolean esDeDiaCompleto;



    private ArrayList<Alarma> alarmas;

    public Tarea(LocalDateTime vencimiento) {
        this.titulo = null;
        this.descripcion = null;
        this.completado = false;
        this.vencimiento = vencimiento;
        this.esDeDiaCompleto = false;


        this.alarmas = new ArrayList<>();
        //igual que en evento, dejo inicializado asi por ahora
        // en google calendar se trunca media hora, no la hora completa
        //var horaActualTruncada = LocalTime.now().truncatedTo(ChronoUnit.HOURS);
        //this.vencimiento = LocalDateTime.of(LocalDate.now(),horaActualTruncada.plusHours(1));
    }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setEsDeDiaCompleto(boolean esDeDiaCompleto) {
        this.esDeDiaCompleto = esDeDiaCompleto;
    }
    public void setEstado(boolean completado) { this.completado = completado; }
    public void setFecha(LocalDateTime vencimiento) {
        this.vencimiento = vencimiento;
    }

    // Funcion a implementar en un futuro
    public void setAlarma(boolean alarma) {}



    public boolean EsDeDiaCompleto() {
        return esDeDiaCompleto;
    }



    // Comento los getters de momento
//    public String getTitulo() { return this.titulo; }
//    public String getDescripcion() {
//        return this.descripcion;
//    }
//    public LocalDateTime getVencimiento() {
//        return vencimiento;
//    }

}
