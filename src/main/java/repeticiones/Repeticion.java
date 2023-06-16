package repeticiones;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Repeticion implements Serializable {

    // Vencimiento de las repeticiones.
    protected LocalDateTime vencimiento;
    protected int cantidadRepeticiones;
    public Repeticion() {
        this.vencimiento = null;
        this.cantidadRepeticiones = 0;
    }

    // Setea el vencimiento en el caso que sea una fecha exacta.
    public void setVencimiento(LocalDateTime vencimiento){
        this.cantidadRepeticiones = 0;
        this.vencimiento = vencimiento;
    }

    // Se setea la cantidad de repeticiones hasta que venza. Automaticamente cuando se llama al metodo
    // se calcula segun la cantidad de repeticiones la fecha exacta en la que vence y se guarda ese valor en el
    // atributo vencimiento.
    public void setCantidadRepeticiones(LocalDateTime inicio, int cantidadRepeticiones){
        this.cantidadRepeticiones = cantidadRepeticiones;
    }

    protected boolean noEstaVencida(LocalDateTime fecha){
        if(vencimiento == null)
            return true;
        else
            return fecha.isBefore(vencimiento) || fecha.equals(vencimiento);
    }

    // El argumento es la fecha del elemento original o de alguna repeticion.
    // Devuelve la fecha de la proxima repeticion desde esa fecha en caso de que no haya vencido, si esta vencida
    // devuelve NULL.
    public abstract LocalDateTime Repetir(LocalDateTime inicio);

    public LocalDateTime getVencimiento() {
        return vencimiento;
    }

    public int getCantidadRepeticiones() {
        return cantidadRepeticiones;
    }

    // Devuelve true si el vencimiento de las repeticiones esta definido por la cantidad de repeticiones.
    public boolean tieneVencimientoPorCantidadRepeticiones() {
        return this.cantidadRepeticiones != 0;
    }

    public abstract String descripcionRepeticion();
}

