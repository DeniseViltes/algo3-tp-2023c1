package repeticiones;

import java.time.LocalDateTime;

public class RepeticionMensual extends Repeticion {

    public RepeticionMensual() {
        super();
    }

    @Override
    public void setCantidadRepeticiones(LocalDateTime inicio, int cantidadRepeticiones) {
        super.setCantidadRepeticiones(inicio, cantidadRepeticiones);
        this.vencimiento = inicio.plusMonths(cantidadRepeticiones-1);
    }

    @Override
    public LocalDateTime Repetir(LocalDateTime inicio) {
        var fechaRepeticion = inicio.plusMonths(1);

        if(noEstaVencida(fechaRepeticion))
            return fechaRepeticion;
        else
            return null;
    }

    @Override
    public String descripcionRepeticion() {
        return "Se repite mensualmente";
    }
}
