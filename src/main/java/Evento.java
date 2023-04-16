
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Evento {

    private String titulo;
    private String descripcion;
    private LocalDateTime fechaYHoraInicial;
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
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setInicio(LocalDateTime inicioEvento){ this.fechaYHoraInicial = inicioEvento; }
    public void setFinal(LocalDateTime finalEvento){ this.fechaYHoraFinal = finalEvento; }
    public void setEsDeDiaCompleto(boolean diaCompleto){
        this.esDeDiaCompleto = diaCompleto;
    }

    //Funciones a implementar en un futuro cuando tengamos implementado repeticiones y alarmas
    public void setRepeticion(boolean repeticion,Repeticion tipo){
        if (!repeticion)
            this.repeticion =null;
        this.repeticion = tipo;
    }
    public void setAlarma(boolean alarma){ }




    // Estuve viendo como funciona en Google Calendar y se puede editar para que sea un evento de todo el dia o no.
    // Y, cuando sacas que sea de todo el dia, vuelve a tener el horario que estaba configurado antes.
    // Por eso, creo que convendria no truncar los horarios, sino, manejar eso en la etapa de la interfaz.
    // O sea, dejar aca el horario completo y cuando se settea como evento de todo el dia, utilizar
    // truncar para que solo se muestre el dia


    public boolean iniciaEntreLasFechas(LocalDateTime inicio, LocalDateTime fin){
        return !fechaYHoraInicial.isBefore(inicio) && !fechaYHoraInicial.isAfter(fin);
    }

}
