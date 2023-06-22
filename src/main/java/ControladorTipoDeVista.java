import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

public interface ControladorTipoDeVista {
    /*
    Agrega los eventos y tareas del calendario a la vista
     */
    void actualizarCalendario(Calendario calendario);
    /*
    Elimina todos los elementos correspondientes al calendario, de la vista (no los borra del modelo)
     */
    void limpiarCalendario();

    /*
    Crea el boton correspondiente al evento/tarea, en el caso de que sea de dia completo
     */
    Node setear_texto_dia_completo(ElementoCalendario el, boolean tieneVencimiento);

    /*
    Crea el boton correspondiente al evento/tarea
     */
    Node setear_texto(ElementoCalendario el, boolean tieneVencimiento);

    @FXML
    void mostrarAyuda(ActionEvent event);

    }
