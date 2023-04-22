import java.util.Comparator;

public class OrdenarElementosPorHorario implements Comparator<ElementoCalendario> {
    public int compare(ElementoCalendario a, ElementoCalendario b){
        return a.getFecha().compareTo(b.getFecha());
    }
}