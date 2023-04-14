import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class CalendarioTest {
    @Test
    public void crearEventoDefault() {
        var calendario = new Calendario();

        calendario.crearEvento(null);
        var e = calendario.eventosDeLaFecha(LocalDate.now()).get(0);

        Assert.assertEquals(e.getTitulo(),"My Event");
        assertNull(e.getDescripcion());
        Assert.assertEquals(e.getFechaInicial(), LocalDate.now());
    }

    @Test
    public void crearTareaDefault() {
        var calendario = new Calendario();
        calendario.crearTarea("Nueva tarea","hacer algo");


    }
}