import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class EventoTest {

    //que tan mal está esto??
    private LocalDateTime ahoraTruncado =  LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);

    @Test
    public void EventoDefault() {
        var evento = new Evento(ahoraTruncado);
        var horaFinalDefault = ahoraTruncado.plusHours(1);

        Assert.assertEquals("My Event", evento.getTitulo());
        Assert.assertNull(evento.getDescripcion());
        Assert.assertEquals(horaFinalDefault,evento.getFechaYHoraFinal());
    }

    @Test
    public void modificarADiaCompleto() {
        var evento = new Evento(ahoraTruncado);
        var alarma = evento.agregarAlarma(Duration.ofMinutes(10), EfectoAlarma.NOTIFICACION);
        var alarma2 = evento.agregarAlarma(Duration.ofMinutes(20), EfectoAlarma.SONIDO);
        var alarma3 = evento.agregarAlarma(Duration.ofMinutes(30), EfectoAlarma.SONIDO);
        Assert.assertEquals(3,evento.cantidadDeAlarmas());

        evento.AsignarDeDiaCompleto();
        var horarioFinal =ahoraTruncado.truncatedTo(ChronoUnit.DAYS).withHour(23).withMinute(59);
        Assert.assertEquals(horarioFinal,evento.getFechaYHoraFinal());
        Assert.assertEquals(0,evento.cantidadDeAlarmas());
    }
}