import java.time.LocalDateTime;

public class RepeticionMensual extends Repeticion {

    public RepeticionMensual() {
        super();
    }

    @Override
    public void setCantidadRepeticiones(LocalDateTime inicio, Integer cantidadRepeticiones) {
        this.vencimiento = inicio.plusMonths(cantidadRepeticiones-1);
    }

    @Override
    public LocalDateTime Repetir(LocalDateTime inicio) {
        var fechaRepeticion = inicio.plusMonths(1);

        if(!estaVencida(fechaRepeticion))
            return fechaRepeticion;
        else
            return null;
    }
}
