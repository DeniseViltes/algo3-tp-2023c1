import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class EventoTest {

    //que tan mal está esto??
    private final LocalDateTime ahoraTruncado =  LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    private final Duration diezMinutos = Duration.ofMinutes(10);

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
        var alarma = evento.agregarAlarma(Duration.ofMinutes(10));
        evento.modificarAlarmaEfecto(alarma, EfectoAlarma.NOTIFICACION);
        var alarma2 = evento.agregarAlarma(Duration.ofMinutes(20));
        evento.modificarAlarmaEfecto(alarma2, EfectoAlarma.SONIDO);
        var alarma3 = evento.agregarAlarma(Duration.ofMinutes(30));
        evento.modificarAlarmaEfecto(alarma3, EfectoAlarma.SONIDO);
        Assert.assertEquals(3,evento.cantidadDeAlarmas());

        evento.setDeDiaCompleto();
        var horarioFinal =ahoraTruncado.truncatedTo(ChronoUnit.DAYS).withHour(23).withMinute(59);
        Assert.assertEquals(horarioFinal,evento.getFechaYHoraFinal());
        Assert.assertEquals(0,evento.cantidadDeAlarmas());
    }
    @Test
    public void modificarADiaCompletoSiDuraMasDeUnDia(){
        var evento = new Evento(ahoraTruncado);
        var variosDias = Duration.ofDays(5).plusHours(5);
        evento.setDuracion(variosDias);
        var alarma = evento.agregarAlarma(Duration.ofMinutes(10));
        evento.modificarAlarmaEfecto(alarma, EfectoAlarma.NOTIFICACION);
        var alarma2 = evento.agregarAlarma(Duration.ofMinutes(20));
        evento.modificarAlarmaEfecto(alarma2, EfectoAlarma.SONIDO);
        var alarma3 = evento.agregarAlarma(Duration.ofMinutes(30));
        evento.modificarAlarmaEfecto(alarma3, EfectoAlarma.SONIDO);


        evento.setDeDiaCompleto();
        var horarioFinal =ahoraTruncado.truncatedTo(ChronoUnit.DAYS).plusDays(5);

        Assert.assertEquals(horarioFinal,evento.getFechaYHoraFinal());
        Assert.assertEquals(0,evento.cantidadDeAlarmas());
    }
    @Test
    public void modificarFechaDeEventoConAlarmas(){
        var evento = new Evento(ahoraTruncado);
        var alarma = evento.agregarAlarma(Duration.ofMinutes(10));
        evento.modificarAlarmaEfecto(alarma, EfectoAlarma.NOTIFICACION);
        var alarma2 = evento.agregarAlarma(Duration.ofMinutes(30));
        evento.modificarAlarmaEfecto(alarma2, EfectoAlarma.NOTIFICACION);

        var magnana = ahoraTruncado.plusDays(1);
        evento.setFecha(ahoraTruncado.plusDays(1));

        var proxAlarma = evento.proximaAlarma(ahoraTruncado.plusHours(12));

        Assert.assertEquals(magnana.minusMinutes(30),proxAlarma);
    }

    @Test
    public void modificarAFechaArbitraria() {
        var evento = new Evento(ahoraTruncado);
        var variosDias = Duration.ofDays(5).plusHours(5);
        evento.setDuracion(variosDias);
        evento.setDeDiaCompleto();
        var alarma = evento.agregarAlarma(Duration.ofMinutes(10));
        evento.modificarAlarmaEfecto(alarma, EfectoAlarma.NOTIFICACION);
        var alarma2 = evento.agregarAlarma(Duration.ofMinutes(20));
        evento.modificarAlarmaEfecto(alarma2, EfectoAlarma.SONIDO);
        var alarma3 = evento.agregarAlarma(Duration.ofMinutes(30));
        evento.modificarAlarmaEfecto(alarma3, EfectoAlarma.SONIDO);


        evento.asignarDeFechaArbitraria(ahoraTruncado);
        var horarioFinal = ahoraTruncado.plusDays(5);

        Assert.assertEquals(horarioFinal,evento.getFechaYHoraFinal());
        Assert.assertEquals(1,evento.cantidadDeAlarmas());
    }

    @Test
    public void agregarAlarmasFechaAbs() {
        var evento = new Evento(ahoraTruncado);
        var magnana = ahoraTruncado.plusDays(1);
        var alarma = evento.agregarAlarmaAbsoluta(magnana);
        evento.modificarAlarmaEfecto(alarma, EfectoAlarma.NOTIFICACION);

        Assert.assertEquals(magnana,alarma.getFechaYHora());

    }

    @Test
    public void sonarProximaAlarma() {
        var evento = new Evento(ahoraTruncado);
        var magnana = ahoraTruncado.plusDays(1);
        evento.setFecha(magnana);
        var alarma = evento.agregarAlarma(diezMinutos);

        var horaASonar = magnana.minus(diezMinutos);

        Assert.assertEquals(horaASonar, evento.proximaAlarma(ahoraTruncado));
        Assert.assertEquals(EfectoAlarma.NOTIFICACION,evento.sonarProximaAlarma(horaASonar));
    }
}