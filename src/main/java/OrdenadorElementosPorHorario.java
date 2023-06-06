import java.io.Serializable;
import java.util.Comparator;

public class OrdenadorElementosPorHorario implements Comparator<ElementoCalendario>, Serializable {
    public int compare(ElementoCalendario a, ElementoCalendario b){
        var comparador = a.getFecha().compareTo(b.getFecha());
        if (comparador == 0)
            return a.hashCode()- b.hashCode();
        return comparador;
    }

}