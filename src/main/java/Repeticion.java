import java.time.LocalDateTime;

public abstract class Repeticion {
    private LocalDateTime vencimiento;
    private  Integer cantidadRepeticiones;
    //si vencimiento y cantidad son null, la repeticion es infinita
    public Repeticion() {
        this.vencimiento = null;
        this.cantidadRepeticiones = null;
    }
    //o es de vencimiento o tiene cantidad de repeticiones, no puede tener las dos
    public void setVencimiento(LocalDateTime vencimiento){
        this.vencimiento = vencimiento;
        this.cantidadRepeticiones = null;
    }
    public void setCantidadRepeticiones(Integer cantidadRepeticiones){
        this.cantidadRepeticiones = cantidadRepeticiones;
        this.vencimiento = null;
    }

    //no quiero que desde calendario se obtenga esto, solo quiero acceder a esto desde las subclases de repeticion
    protected LocalDateTime getVencimiento() {
        return vencimiento;
    }

    protected Integer getCantidadRepeticiones() {
        return cantidadRepeticiones;
    }

    public abstract LocalDateTime Repetir(LocalDateTime inicio);

}

