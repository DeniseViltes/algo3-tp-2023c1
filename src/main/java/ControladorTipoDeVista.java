import javafx.scene.Node;

public interface ControladorTipoDeVista {
    void actualizarCalendario(Calendario calendario);

    void limpiarCalendario();
    Node setear_texto_dia_completo(ElementoCalendario el, boolean tieneVencimiento);

    Node setear_texto(ElementoCalendario el, boolean tieneVencimiento);

    }
