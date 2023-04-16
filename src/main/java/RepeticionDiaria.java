import java.time.LocalDateTime;
import java.util.ArrayList;

// Estas irian en otro archivo pero estamos viendo todavia como lo implementamos
public class RepeticionDiaria extends Repeticion {
        private Integer intervalo;
    public RepeticionDiaria(Integer intervalo) {
        this.intervalo = intervalo;
    }

    public void setIntervalo(Integer intervalo) {
        this.intervalo = intervalo;
    }

    @Override
    public ArrayList<LocalDateTime> Repetir(LocalDateTime inicio) {
        return null;
    }
}
