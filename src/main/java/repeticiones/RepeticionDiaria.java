package repeticiones;

import java.time.LocalDateTime;

public class RepeticionDiaria extends Repeticion {
        private final Integer intervalo;
    public RepeticionDiaria(Integer intervalo) {
        this.intervalo = intervalo;
    }

    public int getIntervalo(){
        return intervalo;
    }
    @Override
    public void setCantidadRepeticiones(LocalDateTime inicio, int cantidadRepeticiones) {
        super.setCantidadRepeticiones(inicio, cantidadRepeticiones);
        this.vencimiento = inicio.plusDays((long)(cantidadRepeticiones-1)*intervalo);
    }

    @Override
    public LocalDateTime Repetir(LocalDateTime inicio) {
        var fechaRepeticion = inicio.plusDays(intervalo);

        if(noEstaVencida(fechaRepeticion))
            return fechaRepeticion;
        else
            return null;
    }

    @Override
    public String descripcionRepeticion() {
        if(intervalo == 1)
            return "Se repite todos los dias";
        else
            return "Se repite cada "+ intervalo + " dias";
    }
}
