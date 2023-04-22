import java.time.LocalDateTime;

public class RepeticionAnual extends Repeticion {

    public RepeticionAnual() {
        super();
    }

    @Override
    public void setCantidadRepeticiones(LocalDateTime inicio, int cantidadRepeticiones){
        this.vencimiento = inicio.plusYears(cantidadRepeticiones-1);
    }

    @Override
    public LocalDateTime Repetir(LocalDateTime inicio){
        var fechaRepeticion = inicio.plusYears(1);

        if(!estaVencida(fechaRepeticion))
            return fechaRepeticion;
        else
            return null;
    }



}
