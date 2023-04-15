import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.ArrayList;

public class Repeticion {
    //public enum Frecuencia {NOREPITE, DIARIA, SEMANAL, MENSUAL, ANUAL}
    //private Frecuencia frecuencia;

    // Ahi consulte en Slack si el intervalo es solo para el diario o puede ser en cualquier caso, depende de eso
    // queda en esta clase o se va a la de RepeticionDiaria
    private Integer intervalo;

    // Esto no se bien como implementarlo, me imagine que si vencimiento es null, es infinito.
    // Pero quizas sea mejor implementar otra clase para esto o nose.
    private LocalDateTime vencimiento;
    private  Integer cantidadRepeticiones;

    public Repeticion(Integer intervalo) {
        this.intervalo = intervalo;
        this.vencimiento = null;
        this.cantidadRepeticiones = null;
    }

    // Frecuencia por el momento no la usariamos
//    public Frecuencia getFrecuencia() {
//        return frecuencia;
//    }

//    public void setFrecuencia(Frecuencia frecuencia) {
//        this.frecuencia = frecuencia;
//        if(frecuencia == Frecuencia.DIARIA && intervaloDiario == null)
//            intervaloDiario = 1;
//        if(frecuencia == Frecuencia.SEMANAL && dias == null) {
//            dias = new ArrayList<>();
//            dias.add(LocalDateTime.now().getDayOfWeek());
//        }
//    }

//    public Integer getIntervaloDiario() {
//        return intervaloDiario;
//    }
//
//    public ArrayList<DayOfWeek> getDias() {
//        return dias;
//    }

    public void setVencimiento(LocalDateTime vencimiento){
        this.vencimiento = vencimiento;
    }
    public void setCantidadRepeticiones(Integer cantidadRepeticiones){
        this.cantidadRepeticiones = cantidadRepeticiones;
    }

}

// Estas irian en otro archivo pero estamos viendo todavia como lo implementamos
public class RepeticionDiaria extends Repeticion{

    public RepeticionDiaria(Integer intervalo) {
        super(intervalo);
    }
}

public class RepeticionSemanal extends Repeticion{
    private ArrayList<DayOfWeek> dias;

    public RepeticionSemanal(Integer intervalo, ArrayList<DayOfWeek> dias) {
        super(intervalo);
        this.dias = dias;
    }
}

public class RepeticionMensual extends Repeticion{

    public RepeticionMensual(Integer intervalo) {
        super(intervalo);
    }
}

public class RepeticionAnual extends Repeticion{

    public RepeticionAnual(Integer intervalo) {
        super(intervalo);
    }
}
