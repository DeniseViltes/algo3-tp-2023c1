import java.time.LocalDateTime;

public abstract class Repeticion {
    protected LocalDateTime vencimiento;
    //si vencimiento y cantidad son null, la repeticion es infinita
    public Repeticion() {
        this.vencimiento = null;
    }
    //o es de vencimiento o tiene cantidad de repeticiones, no puede tener las dos
    public void setVencimiento(LocalDateTime vencimiento){
        this.vencimiento = vencimiento;
    }
    public abstract void setCantidadRepeticiones(LocalDateTime inicio, int cantidadRepeticiones);

    protected boolean estaVencida(LocalDateTime fecha){
        if(vencimiento == null)
            return false;
        else
            return vencimiento.isBefore(fecha);
    }
    //no quiero que desde calendario se obtenga esto, solo quiero acceder a esto desde las subclases de repeticion

    public abstract LocalDateTime Repetir(LocalDateTime inicio);

}

