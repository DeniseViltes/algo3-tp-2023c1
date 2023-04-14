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
        var e = calendario.getEventos().get(0);
        Assert.assertEquals(e.getTitulo(),"Evento nuevo");
        assertNull(e.getDescripcion());
        Assert.assertEquals(e.getFechaYHoraInicial(), LocalDateTime.of(LocalDate.now(), LocalTime.of(8,0)));
    }

    @Test
    public void crearTareaDefault() {
        var calendario = new Calendario();
        calendario.crearTarea("Nueva tarea","hacer algo");

        var t = calendario.getTareas().get(0);
        Assert.assertEquals(t.getTitulo(),"Nueva tarea");
        Assert.assertEquals(t.getDescripcion(),"hacer algo");
        Assert.assertEquals(t.getVencimiento(), LocalDateTime.of(LocalDate.now(),LocalTime.of(19,0)));

    }
}