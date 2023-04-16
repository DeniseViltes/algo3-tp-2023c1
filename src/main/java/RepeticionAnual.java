import java.time.LocalDateTime;

public class RepeticionAnual extends Repeticion {
    //otra forma de hacerlo sin un contador?
    private int contador = 0;
    public RepeticionAnual() {
        super();
    }

    @Override
    public LocalDateTime Repetir(LocalDateTime inicio){
        var repeticion = inicio.plusYears(1);
        var vencimiento = getVencimiento();
        var cantidadRepeciones = getCantidadRepeticiones();

        if (vencimiento!=null && repeticion.isBefore(vencimiento))
            return repeticion;

        if (cantidadRepeciones!=null && contador< cantidadRepeciones) {
            contador++;
            return repeticion;
        }

        if(cantidadRepeciones==null && vencimiento == null)
            return repeticion;

        return null;
    }
}
