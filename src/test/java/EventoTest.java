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

        Assert.assertEquals("My Event", evento.getTitulo());
        Assert.assertNull(evento.getDescripcion());
        Assert.assertEquals(horaFinalDefault,evento.getFechaYHoraFinal());
    }

    @Test
    public void modificarADiaCompleto() {
        var evento = new Evento(ahoraTruncado);
        var alarma = evento.agregarAlarma(diezMinutos);
        alarma.setEfecto(EfectoAlarma.MAIL);
        evento.setDeDiaCompleto();
        var horarioFinal =ahoraTruncado.truncatedTo(ChronoUnit.DAYS).withHour(23).withMinute(59);
        Assert.assertEquals(horarioFinal,evento.getFechaYHoraFinal());
        Assert.assertNull(evento.proximaAlarma(ahoraTruncado));

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
       // Assert.assertEquals(0,evento.cantidadDeAlarmas());
    }
    @Test
    public void modificarFechaDeEventoConAlarmas(){
        var evento = new Evento(ahoraTruncado);
        var alarma = evento.agregarAlarma(Duration.ofMinutes(10));
        evento.modificarAlarmaEfecto(alarma, EfectoAlarma.NOTIFICACION);
        var alarma2 = evento.agregarAlarma(Duration.ofMinutes(30));
        evento.modificarAlarmaEfecto(alarma2, EfectoAlarma.NOTIFICACION);


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
        //Assert.assertEquals(1,evento.cantidadDeAlarmas());
    }

    @Test
    public void agregarAlarmasFechaAbs() {
        var evento = new Evento(ahoraTruncado);
        var alarma = evento.agregarAlarmaAbsoluta(magnana);
        evento.modificarAlarmaEfecto(alarma, EfectoAlarma.NOTIFICACION);

        Assert.assertEquals(magnana,alarma.getFechaYHora());

    }

    @Test
    public void sonarProximaAlarma() {
        var evento = new Evento(ahoraTruncado);
        evento.setFecha(magnana);
        var alarma = evento.agregarAlarma(diezMinutos);
        alarma.setEfecto(EfectoAlarma.SONIDO);

        var horaASonar = magnana.minus(diezMinutos);

        Assert.assertEquals(horaASonar, evento.proximaAlarma(ahoraTruncado));
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
        Assert.assertEquals(fechaACambiar,evento.proximaAlarma(ahoraTruncado));

    }

    @Test
    public void agregarRepeticion() {
        var evento = new Evento(ahoraTruncado);
        Assert.assertFalse(evento.tieneRepeticionEntreLosHorarios(ahoraTruncado,magnana));

        evento.setRepeticionVencimiento(magnana); //esto no se tiene que romper
        evento.setRepeticionCantidad(5000000);

        evento.setRepeticionDiaria(1);

        Assert.assertTrue(evento.tieneRepeticionEntreLosHorarios(ahoraTruncado,magnana));
        Assert.assertEquals(magnana,evento.proximaRepeticion(ahoraTruncado));
    }

    @Test
    public void verSiHayRepeticiones() {
        var evento = new Evento(ahoraTruncado);
        var dias = EnumSet.allOf(DayOfWeek.class); //no entendi para nada como funciona enumSet
        //pero creo que tendriamos que recibir este tipo de Set?, no cualquier set
        var masDosDias = ahoraTruncado.plusDays(2);


        evento.setRepeticionSemanal(dias);
        evento.setRepeticionVencimiento(ahoraTruncado.plusDays(1));

        //TODO en calendario se tiene que proporcionar una repeticion
        // default, pero aca solo recibe el set

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