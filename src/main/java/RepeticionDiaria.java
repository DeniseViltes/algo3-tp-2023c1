import java.time.LocalDateTime;

// Estas irian en otro archivo pero estamos viendo todavia como lo implementamos
public class RepeticionDiaria extends Repeticion {
        private final Integer intervalo;
    public RepeticionDiaria(Integer intervalo) {
        this.intervalo = intervalo;
    }


    @Override
    public void setCantidadRepeticiones(LocalDateTime inicio, long cantidadRepeticiones) {
        this.vencimiento = inicio.plusDays((cantidadRepeticiones-1)*intervalo);
    }

    @Override
    public LocalDateTime Repetir(LocalDateTime inicio) {
        var fechaRepeticion = inicio.plusDays(intervalo);

        if(noEstaVencida(fechaRepeticion))
            return fechaRepeticion;
        else
            return null;
    }
}
