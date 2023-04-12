public class Evento {

    private final String titulo;
    private final String descripcion;
    public Evento(String t, String d) {
        this.titulo = t;
        this.descripcion = d;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }


}
