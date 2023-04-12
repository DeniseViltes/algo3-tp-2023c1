import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.ArrayList;

public class Repeticion {
    public enum Frecuencia {NOREPITE,DIARIA, SEMANAL, MENSUAL, ANUAL}

    private Frecuencia frecuencia;
    private Integer intervaloDiario;

    private ArrayList<DayOfWeek> dias;

    public Repeticion(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
        if (frecuencia == Frecuencia.DIARIA)
            intervaloDiario = 1;
        if(frecuencia == Frecuencia.SEMANAL){
            dias = new ArrayList<>();
            dias.add(LocalDateTime.now().getDayOfWeek());
        }
    }

    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
        if(frecuencia == Frecuencia.DIARIA && intervaloDiario == null)
            intervaloDiario = 1;
        if(frecuencia == Frecuencia.SEMANAL && dias == null) {
            dias = new ArrayList<>();
            dias.add(LocalDateTime.now().getDayOfWeek());
        }
    }

    public Integer getIntervaloDiario() {
        return intervaloDiario;
    }

    public ArrayList<DayOfWeek> getDias() {
        return dias;
    }
}
