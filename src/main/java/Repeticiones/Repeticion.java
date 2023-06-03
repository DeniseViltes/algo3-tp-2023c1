package Repeticiones;

import java.time.LocalDateTime;

public abstract class Repeticion {
    protected LocalDateTime vencimiento;
    public Repeticion() {
        this.vencimiento = null;
    }
    public void setVencimiento(LocalDateTime vencimiento){
        this.vencimiento = vencimiento;
    }
    public abstract void setCantidadRepeticiones(LocalDateTime inicio, long cantidadRepeticiones);

    protected boolean noEstaVencida(LocalDateTime fecha){
        if(vencimiento == null)
            return true;
        else
            return fecha.isBefore(vencimiento) || fecha.equals(vencimiento);
    }

    public abstract LocalDateTime Repetir(LocalDateTime inicio);

    public LocalDateTime getVencimiento() {
        return vencimiento;
    }
}

