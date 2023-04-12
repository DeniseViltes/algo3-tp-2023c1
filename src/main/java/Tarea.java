public class Tarea {

    private final String titulo;
    private final String descripcion;
    private boolean completado;

    public Tarea(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completado = false;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void completarTarea() {
        this.completado = true;
    }
}
