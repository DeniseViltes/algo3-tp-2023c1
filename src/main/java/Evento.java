import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Evento {

    private String titulo;
    private String descripcion;
    private LocalDateTime fechaYHoraInicial;
    //Creo que conviene guardar la fecha de finalizacion
    private LocalDateTime fechaYHoraFinal;
    private boolean esDeDiaCompleto;
    private Repeticion repeticion;



    private final ArrayList<Alarma> alarmas;


    public Evento(LocalDateTime inicioEvento) {
        //Dejo asi inicializado por ahora
        this.titulo = null;
        this.descripcion = null;
        this.fechaYHoraInicial = inicioEvento;
        this.fechaYHoraFinal = inicioEvento.plusHours(1); //la duracion default es de 1 hora
        this.repeticion = null;

        this.alarmas = new ArrayList<>();
        //Esto no entendi bien porque la hora inicial seria la de ahora
        //var horaActualTruncada = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        //this.fechaYHoraInicial = horaActualTruncada.plusHours(1);
        //this.duration = Duration.ofHours(1);
        //this.repeticion = new Repeticion(Repeticion.Frecuencia.NOREPITE); //REVISAR
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setInicio(LocalDateTime inicioEvento){ this.fechaYHoraInicial = inicioEvento; }
    public void setFinal(LocalDateTime finalEvento){ this.fechaYHoraFinal = finalEvento; }
    public void setEsDeDiaCompleto(boolean diaCompleto){ this.esDeDiaCompleto = diaCompleto; }

    //Funciones a implementar en un futuro cuando tengamos implementado repeticiones y alarmas
    public void setRepeticion(boolean repeticion){ }
    public void setAlarma(boolean alarma){ }




    // Estuve viendo como funciona en Google Calendar y se puede editar para que sea un evento de todo el dia o no.
    // Y, cuando sacas que sea de todo el dia, vuelve a tener el horario que estaba configurado antes.
    // Por eso, creo que convendria no truncar los horarios, sino, manejar eso en la etapa de la interfaz.
    // O sea, dejar aca el horario completo y cuando se settea como evento de todo el dia, utilizar
    // truncar para que solo se muestre el dia

//    public void setEsDeDiaCompleto(boolean esDeDiaCompleto) {
//        this.esDeDiaCompleto = true;
//        this.fechaYHoraInicial = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
//        //this.duration = Duration.ofDays(1);
//    }
//    public void setFechaYHoraInicial(LocalDateTime fechaYHoraInicial) {
//        if (esDeDiaCompleto)
//            this.fechaYHoraInicial = fechaYHoraInicial.truncatedTo(ChronoUnit.DAYS); //probar
//        this.fechaYHoraInicial = fechaYHoraInicial;
//    }




    // Si vamos a usar fecha final, esto habria que eliminar

//    public void setDuration(Duration duracion ) {
//    //long, String, Duration?-> ver funcior parse
//    if (esDeDiaCompleto) {
//        //this.duration = duracion.truncatedTo(ChronoUnit.DAYS);
//    }
//    //this.duration = duracion;
//    }




    // Los getters por ahora los comento porque a menos que sea demasiado necesario creo que no tendriamos que usarlos

//    public String getTitulo() {
//        return this.titulo;
//    }
//
//    public String getDescripcion() {
//        return this.descripcion;
//    }
//    public LocalDate getFechaInicial(){
//        return LocalDate.from(this.fechaYHoraInicial);
//    }
//
//    public  LocalTime getHoraInical(){
//        return  LocalTime.from(this.fechaYHoraInicial);
//    }
//    public LocalDateTime getFechaYHoraFinal(){
//        return fechaYHoraInicial.plus(duration);
//    }


    public boolean iniciaEntreLasFechas(LocalDateTime inicio, LocalDateTime fin){
        if (fechaYHoraInicial.isBefore(inicio) || fechaYHoraInicial.isAfter(fin))
                return false;
        return true;
    }
}
