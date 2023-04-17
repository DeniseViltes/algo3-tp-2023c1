import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RepeticionSemanal extends Repeticion {
    private ArrayList<DayOfWeek> dias;

    public RepeticionSemanal(Integer intervalo, ArrayList<DayOfWeek> dias) {
        super();
        this.dias = dias;
    }

    @Override
    public LocalDateTime Repetir(LocalDateTime inicio) {
        return null;
    }
}
