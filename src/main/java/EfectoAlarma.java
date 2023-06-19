import java.io.Serializable;

public enum EfectoAlarma implements Serializable {
    NOTIFICACION("Notificacion"),
    SONIDO("Sonido"),
    MAIL("Mail");

    private final String tipo;

    EfectoAlarma(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return tipo;
    }

    //TODO ver si esta bien agregar esto
    public static EfectoAlarma convertirStringAEfectoAlarma(String nombre){
        return EfectoAlarma.valueOf(nombre.toUpperCase());
    }

}
