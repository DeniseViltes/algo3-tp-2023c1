import java.time.LocalDateTime;
import java.util.ArrayList;

public class Evento implements ElementoCalendario {

    private String titulo;
    private String descripcion;
    private LocalDateTime fechaYHoraInicial;

    //creo que al final para la repeticion es más facil teniendo la duracion, pero por ahora funciona la fecha final
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
    public void setFecha(LocalDateTime inicioEvento){ this.fechaYHoraInicial = inicioEvento; }
    public void setFinal(LocalDateTime finalEvento){ this.fechaYHoraFinal = finalEvento; }
    public void setEsDeDiaCompleto(boolean diaCompleto){
        this.esDeDiaCompleto = diaCompleto;
    }

    //Funciones a implementar en un futuro cuando tengamos implementado repeticiones y alarmas
    public void setRepeticion(Repeticion tipo){
        this.repeticion = tipo;
    }
    public void setAlarma(boolean alarma){ }




    public boolean iniciaEntreLosHorarios(LocalDateTime inicio, LocalDateTime fin){
        //varifica si la fecha inicial del evento está entre las fechas dadas
        return !fechaYHoraInicial.isBefore(inicio) && !fechaYHoraInicial.isAfter(fin);
    }

    public boolean tieneRepeticionEntreLosHorarios(LocalDateTime inicio, LocalDateTime fin){
        if(repeticion == null)
            return false;
        var fechaRepeticion = repeticion.Repetir(inicio);
        if(fechaRepeticion == null)
            return  false;
        return fechaRepeticion.isAfter(inicio) && fechaRepeticion.isBefore(fin);
    }


    public LocalDateTime getFechaInicial() {
        return fechaYHoraInicial;
    }

    public boolean tieneRepeticion(){
        return repeticion != null;
    }

    public  LocalDateTime proximaRepeticion (LocalDateTime inicio){
        return repeticion.Repetir(inicio);
    }
}
