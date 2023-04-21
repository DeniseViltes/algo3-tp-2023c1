import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class EventoTest {

    @Test
    public void EventoDefault() {
    }

    @Test
    public void modificarDiaCompleto() {
        var tiempo = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        var evento = new Evento(tiempo);
        var alarma = evento.agregarAlarma(Duration.ofMinutes(10), Alarma.Efecto.NOTIFICACION);
        var alarma2 = evento.agregarAlarma(Duration.ofMinutes(20), Alarma.Efecto.SONIDO);
        var alarma3 = evento.agregarAlarma(Duration.ofMinutes(30), Alarma.Efecto.SONIDO);
        evento.setEsDeDiaCompleto(true);

        Assert.assertEquals(0,evento.cantidadDeAlarmas());
    }
}