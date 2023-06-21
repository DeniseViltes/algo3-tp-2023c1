import org.junit.Assert;
import org.junit.Test;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.EnumSet;



public class EventoTest {

    //que tan mal está esto??
    private final LocalDateTime ahoraTruncado =  LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    private final Duration diezMinutos = Duration.ofMinutes(10);

    private final  LocalDateTime magnana = ahoraTruncado.plusDays(1);

    @Test
    public void EventoDefault() {
        var evento = new Evento(ahoraTruncado);
        var horaFinalDefault = ahoraTruncado.plusHours(1);

        evento.setDescripcion("Final de Algo3");

        Assert.assertEquals("Final de Algo3", evento.getDescripcion());

        Assert.assertEquals("My Event", evento.getTitulo());
        Assert.assertEquals(horaFinalDefault,evento.getFechaYHoraFinal());
    }

    @Test
    public void modificarADiaCompleto() {
        var evento = new Evento(ahoraTruncado);
        var alarma = evento.agregarAlarma(ahoraTruncado, diezMinutos);
        alarma.setEfecto(EfectoAlarma.MAIL);
        evento.setDeDiaCompleto();
        var horarioFinal =ahoraTruncado.truncatedTo(ChronoUnit.DAYS).withHour(23).withMinute(59);
        Assert.assertEquals(horarioFinal,evento.getFechaYHoraFinal());
        Assert.assertNull(evento.horarioProximaAlarma(ahoraTruncado));

    }
    @Test
    public void modificarADiaCompletoSiDuraMasDeUnDia(){
        var evento = new Evento(ahoraTruncado);
        var variosDias = Duration.ofDays(5).plusHours(5);
        evento.setDuracion(variosDias);
        var alarma = evento.agregarAlarma(ahoraTruncado, Duration.ofMinutes(10));
        evento.modificarAlarmaEfecto(alarma, EfectoAlarma.NOTIFICACION);
        var alarma2 = evento.agregarAlarma(ahoraTruncado, Duration.ofMinutes(20));
        evento.modificarAlarmaEfecto(alarma2, EfectoAlarma.SONIDO);
        var alarma3 = evento.agregarAlarma(ahoraTruncado, Duration.ofMinutes(30));
        evento.modificarAlarmaEfecto(alarma3, EfectoAlarma.SONIDO);

        Assert.assertEquals(EfectoAlarma.NOTIFICACION,evento.sonarProximaAlarma(ahoraTruncado.minusMinutes(10)));

        evento.setDeDiaCompleto();
        var horarioFinal =ahoraTruncado.truncatedTo(ChronoUnit.DAYS).plusDays(5).plusHours(23).plusMinutes(59);

        Assert.assertEquals(horarioFinal,evento.getFechaYHoraFinal());
        Assert.assertNull(evento.sonarProximaAlarma(ahoraTruncado.minusDays(1)));

    }
    @Test
    public void modificarFechaDeEventoConAlarmas(){
        var evento = new Evento(ahoraTruncado);
        var alarma = evento.agregarAlarma(ahoraTruncado, Duration.ofMinutes(10));
        evento.modificarAlarmaEfecto(alarma, EfectoAlarma.NOTIFICACION);
        var alarma2 = evento.agregarAlarma(ahoraTruncado, Duration.ofMinutes(30));
        evento.modificarAlarmaEfecto(alarma2, EfectoAlarma.NOTIFICACION);

        Assert.assertEquals(ahoraTruncado.plusMinutes(60),evento.getFechaYHoraFinal());

        evento.setFecha(ahoraTruncado.plusDays(1));

        var proxAlarma = evento.horarioProximaAlarma(ahoraTruncado);

        Assert.assertEquals(ahoraTruncado.plusDays(1).plusMinutes(60),evento.getFechaYHoraFinal());
        Assert.assertEquals(magnana.minusMinutes(30),proxAlarma);
    }

    @Test
    public void modificarAFechaArbitraria() {
        var evento = new Evento(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        var variosDias = Duration.ofDays(5).plusHours(5);
        evento.setDuracion(variosDias);
        evento.setDeDiaCompleto();
        var alarma = evento.agregarAlarma(ahoraTruncado, Duration.ofMinutes(10));
        evento.modificarAlarmaEfecto(alarma, EfectoAlarma.NOTIFICACION);
        var alarma2 = evento.agregarAlarma(ahoraTruncado, Duration.ofMinutes(20));
        evento.modificarAlarmaEfecto(alarma2, EfectoAlarma.SONIDO);
        var alarma3 = evento.agregarAlarma(ahoraTruncado, Duration.ofMinutes(30));
        evento.modificarAlarmaEfecto(alarma3, EfectoAlarma.SONIDO);


        evento.asignarDeFechaArbitraria(ahoraTruncado);
        var horarioFinal = ahoraTruncado.plusDays(5).plusMinutes(30);

        Assert.assertEquals(horarioFinal,evento.getFechaYHoraFinal());
        Assert.assertEquals(EfectoAlarma.NOTIFICACION, evento.sonarProximaAlarma(ahoraTruncado.minusMinutes(10)));
    }

    @Test
    public void agregarAlarmasFechaAbs() {
        var evento = new Evento(ahoraTruncado);
        var alarma = evento.agregarAlarmaAbsoluta(magnana);
        evento.modificarAlarmaEfecto(alarma, EfectoAlarma.NOTIFICACION);

        Assert.assertEquals(magnana,alarma.getFechaYHora());
        Assert.assertEquals(magnana,evento.horarioProximaAlarma(ahoraTruncado));

    }

    @Test
    public void sonarProximaAlarma() {
        var evento = new Evento(ahoraTruncado);
        evento.setFecha(magnana);
        var alarma = evento.agregarAlarma(magnana, diezMinutos);
        evento.modificarAlarmaEfecto(alarma,EfectoAlarma.SONIDO);

        var horaASonar = magnana.minus(diezMinutos);

        Assert.assertEquals(horaASonar, evento.horarioProximaAlarma(ahoraTruncado));
        Assert.assertEquals(EfectoAlarma.SONIDO, evento.sonarProximaAlarma(horaASonar));
    }

    @Test
    public void sonarAlarmaSiYaPasoElHorarioASonar() {
        var evento = new Evento(ahoraTruncado);

        var horaASonar = ahoraTruncado.minus(diezMinutos);

        var despuesDeLaHoraASonar = horaASonar.plusMinutes(1);
        Assert.assertNull(evento.sonarProximaAlarma(despuesDeLaHoraASonar));
    }

    @Test
    public void modificarFechaSiEsDeDiaCompleto() {
        var evento = new Evento(ahoraTruncado);
        evento.setDeDiaCompleto();
        var fechaACambiar = LocalDateTime.now().plusHours(62);
        evento.setFecha(fechaACambiar);

        var fechaEsperada = fechaACambiar.toLocalDate().atStartOfDay();

        Assert.assertEquals(fechaEsperada,evento.getFecha());

    }

    @Test
    public void modificarFechaSiEsDeFechaArbitraria() {
        var evento = new Evento(ahoraTruncado);
        var fechaACambiar = LocalDateTime.now().plusHours(62);

        evento.setFecha(fechaACambiar);
        Assert.assertEquals(fechaACambiar,evento.getFecha());
    }


    @Test
    public void fechaAbsolutaDeAlarma() {
        var evento = new Evento(ahoraTruncado);
        var fechaACambiar = LocalDateTime.now().plusHours(62);

        var alarma = evento.agregarAlarmaAbsoluta(ahoraTruncado);
        evento.modificarFechaAbsolutaAlarma(alarma, fechaACambiar);
        Assert.assertEquals(fechaACambiar,evento.horarioProximaAlarma(ahoraTruncado));

    }

    @Test
    public void agregarRepeticion() {
        var evento = new Evento(ahoraTruncado);
        Assert.assertFalse(evento.tieneRepeticionEntreLosHorarios(ahoraTruncado,magnana));

        evento.setRepeticionVencimiento(magnana); //esto no se tiene que romper
        evento.setRepeticionCantidad(5000000);

        evento.setRepeticionDiaria(1);

        Assert.assertTrue(evento.tieneRepeticionEntreLosHorarios(ahoraTruncado,magnana.plusHours(1)));
        Assert.assertEquals(magnana,evento.proximaRepeticion(ahoraTruncado));
    }

    @Test
    public void verSiHayRepeticiones() {
        var evento = new Evento(ahoraTruncado);
        var dias = EnumSet.allOf(DayOfWeek.class);
        var masDosDias = ahoraTruncado.plusDays(2);


        evento.setRepeticionSemanal(dias);
        evento.setRepeticionVencimiento(ahoraTruncado.plusDays(1));

        Assert.assertTrue(evento.tieneRepeticionEntreLosHorarios(ahoraTruncado,masDosDias));
        Assert.assertFalse(evento.tieneRepeticionEntreLosHorarios(magnana,masDosDias));
    }

    @Test
    public void verficarRepeticionEliminada() {
        var evento = new Evento(ahoraTruncado);
        evento.setRepeticionAnual();
        var vencimiento = ahoraTruncado.plusYears(2);
        evento.setRepeticionVencimiento(vencimiento);

        evento.eliminarRepeticion();

        Assert.assertFalse(evento.tieneRepeticionEntreLosHorarios(ahoraTruncado,vencimiento));
    }
}