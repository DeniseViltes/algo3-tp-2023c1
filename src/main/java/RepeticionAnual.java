import java.time.LocalDateTime;

public class RepeticionAnual extends Repeticion {

    public RepeticionAnual() {
        super();
    }

    @Override
    public LocalDateTime Repetir(LocalDateTime inicio){
        var repeticion = inicio.plusYears(1);
        var vencimiento = getVencimiento();
        var cantidadRepeciones = getCantidadRepeticiones();

        if(cantidadRepeciones == null && vencimiento == null)
            return repeticion;

        if (vencimiento!=null && (repeticion.isBefore(vencimiento) || repeticion.equals(vencimiento)))
            return repeticion;

        if(cantidadRepeciones ==null)
                return null;
        var fechaRepeticionFinal = horarioFinalSegunCantidadRepeticiones(inicio, cantidadRepeciones);

        if (repeticion.isBefore(fechaRepeticionFinal) || repeticion.equals(fechaRepeticionFinal)) {
            return repeticion;
        }


        return null;
    }

    private LocalDateTime horarioFinalSegunCantidadRepeticiones(LocalDateTime inicio,int cantidad){
        return inicio.plusYears(cantidad);
    }

}
