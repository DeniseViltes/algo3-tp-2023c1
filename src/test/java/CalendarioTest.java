import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public class CalendarioTest {
    private final LocalDateTime hoy = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    private final LocalDateTime magnana = hoy.plusDays(1);

    private final Duration diezMinutos = Duration.ofMinutes(10);

    private final LocalDateTime ahora = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);

    @Test
    public void crearEvento() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        Assert.assertEquals(1,calendario.elementosEntreFechas(hoy, magnana).size());
        Assert.assertEquals(ahora.plusHours(1),calendario.verFechaYHora(evento));
    }

    @Test
    public void modificarEvento() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        calendario.modificarFecha(evento, ahora.plusHours(17));
        Assert.assertEquals(1,calendario.elementosEntreFechas(hoy, magnana.plusDays(1)).size());
        Assert.assertEquals(ahora.plusHours(17),calendario.verFechaYHora(evento));
    }

    @Test
    public void eliminarEvento() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        Assert.assertEquals(1,calendario.elementosEntreFechas(hoy, magnana.plusDays(1)).size());
        Assert.assertEquals(ahora.plusHours(1),calendario.verFechaYHora(evento));
        calendario.eliminarElementoCalendario(evento);
        Assert.assertEquals(0,calendario.elementosEntreFechas(hoy, magnana.plusDays(1)).size());

    }

    @Test
    public void crearTarea() {
        var calendario = new Calendario();
        var tarea = calendario.crearTarea();
        Assert.assertEquals(1,calendario.elementosEntreFechas(hoy, magnana).size());
        Assert.assertEquals(ahora.plusHours(1),calendario.verFechaYHora(tarea));
    }

    @Test
    public void marcarTareaCompleta() {
        var calendario = new Calendario();
        var tarea = calendario.crearTarea();
        calendario.marcarTareaCompleta(tarea);
        Assert.assertTrue(tarea.estaCompleta());
    }

    @Test
    public void marcarTareaIncompleta() {
        var calendario = new Calendario();
        var tarea = calendario.crearTarea();
        calendario.marcarTareaIncompleta(tarea);
        Assert.assertFalse(tarea.estaCompleta());
    }

    @Test
    public void modificarTarea() {
        var calendario = new Calendario();
        var tarea = calendario.crearTarea();
        calendario.modificarFecha(tarea, hoy.plusHours(17));
        var tarea2 = calendario.crearTarea();
        calendario.modificarFecha(tarea2, hoy.plusHours(25));
        Assert.assertEquals(1,calendario.elementosEntreFechas(hoy, magnana).size());
        Assert.assertEquals(hoy.plusHours(25),calendario.verFechaYHora(tarea2));
    }

    @Test
    public void eliminarTarea() {
        var calendario = new Calendario();
        var tarea = calendario.crearTarea();
        calendario.modificarFecha(tarea, hoy.plusHours(17));
        Assert.assertEquals(1,calendario.elementosEntreFechas(hoy, magnana.plusDays(1)).size());
        calendario.eliminarElementoCalendario(tarea);
        Assert.assertEquals(0,calendario.elementosEntreFechas(hoy, magnana.plusDays(1)).size());

    }

    @Test
    public void eventoConUnaRepeticionMensualInfinita() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        calendario.agregarRepeticionMensualEvento(evento);
        LocalDateTime fechaActual = LocalDate.now().atStartOfDay();
        var listadoDeEventosPlusDias = calendario.elementosEntreFechas(fechaActual,fechaActual.plusDays(2));
        var listadoDeEventosPlusAgno = calendario.elementosEntreFechas(fechaActual.minusDays(1),fechaActual.plusYears(1).plusDays(2));

        Assert.assertEquals(1,listadoDeEventosPlusDias.size());
        Assert.assertEquals(13,listadoDeEventosPlusAgno.size());

    }

    @Test
    public void eventoConUnaRepeticionDiariaInfinita() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        calendario.agregarRepeticionDiariaEvento(evento);
        calendario.modificarIntervaloRepeticionDiaria(evento,2);

        LocalDateTime fechaActual = LocalDate.now().atStartOfDay();
        var listadoDeEventosPlusDias = calendario.elementosEntreFechas(fechaActual,fechaActual.plusDays(2));
        var listadoDeEventosPlusAgno = calendario.elementosEntreFechas(fechaActual,fechaActual.plusYears(1).plusDays(2));

        Assert.assertEquals(1,listadoDeEventosPlusDias.size());
        Assert.assertEquals(184,listadoDeEventosPlusAgno.size());

    }

    @Test
    public void eventoConUnaRepeticionDiariaVencimiento() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        calendario.agregarRepeticionDiariaEvento(evento);
        calendario.modificarIntervaloRepeticionDiaria(evento,3);
        calendario.modificarVencimientoRepeticion(evento, magnana.plusDays(7));

        LocalDateTime fechaActual = LocalDate.now().atStartOfDay();
        var listadoDeEventosPlusDias = calendario.elementosEntreFechas(fechaActual,fechaActual.plusDays(2));
        var listadoDeEventosPlusAgno = calendario.elementosEntreFechas(fechaActual,fechaActual.plusYears(1).plusDays(2));

        Assert.assertEquals(1,listadoDeEventosPlusDias.size());
        Assert.assertEquals(3,listadoDeEventosPlusAgno.size());

    }

    @Test
    public void eventoConUnaRepeticionDiariaCantidad() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        calendario.agregarRepeticionDiariaEvento(evento);
        calendario.modificarIntervaloRepeticionDiaria(evento,5);
        calendario.modificarCantidadRepeticiones(evento, 20);

        LocalDateTime fechaActual = LocalDate.now().atStartOfDay();
        var listadoDeEventosPlusDias = calendario.elementosEntreFechas(fechaActual,fechaActual.plusDays(20));
        var listadoDeEventosPlusAgno = calendario.elementosEntreFechas(fechaActual,fechaActual.plusYears(1).plusDays(2));

        Assert.assertEquals(4,listadoDeEventosPlusDias.size());
        Assert.assertEquals(20,listadoDeEventosPlusAgno.size());

    }

    @Test
    public void eventoConUnaRepeticionSemanalInfinita() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        calendario.agregarRepeticionSemanalEvento(evento);
        calendario.modificarDiasRepeticionSemanal(evento, Set.of(ahora.getDayOfWeek(),ahora.plusDays(2).getDayOfWeek()));

        LocalDateTime fechaActual = LocalDate.now().atStartOfDay();
        var listadoDeEventosPlusDias = calendario.elementosEntreFechas(ahora.minusDays(1),ahora.plusDays(4));
        var listadoDeEventosPlusAgno = calendario.elementosEntreFechas(ahora.minusDays(1),fechaActual.plusYears(1).plusDays(2));

        Assert.assertEquals(2,listadoDeEventosPlusDias.size());
        Assert.assertEquals(106,listadoDeEventosPlusAgno.size());

    }

    @Test
    public void eventoConUnaRepeticionSemanalVencimiento() {
        LocalDateTime fechaActual = LocalDate.now().atStartOfDay();
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        calendario.agregarRepeticionSemanalEvento(evento);
        calendario.modificarDiasRepeticionSemanal(evento, Set.of(fechaActual.getDayOfWeek(),fechaActual.plusDays(2).getDayOfWeek()));
        calendario.modificarVencimientoRepeticion(evento, magnana.plusDays(25));


        var listadoDeEventosPlusDias = calendario.elementosEntreFechas(fechaActual.minusDays(1),fechaActual.plusDays(6));
        var listadoDeEventosPlusAgno = calendario.elementosEntreFechas(fechaActual.minusDays(1),fechaActual.plusYears(1).plusDays(2));

        Assert.assertEquals(2,listadoDeEventosPlusDias.size());
        Assert.assertEquals(8,listadoDeEventosPlusAgno.size());

    }

    @Test
    public void eventoConUnaRepeticionSemanalCantidad() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        calendario.agregarRepeticionSemanalEvento(evento);
        calendario.modificarDiasRepeticionSemanal(evento, Set.of(ahora.getDayOfWeek(),ahora.plusDays(1).getDayOfWeek()));
        calendario.modificarCantidadRepeticiones(evento, 20);

        LocalDateTime fechaActual = LocalDate.now().atStartOfDay();
        var listadoDeEventosPlusDias = calendario.elementosEntreFechas(ahora,fechaActual.plusDays(21));
        var listadoDeEventosPlusAgno = calendario.elementosEntreFechas(ahora,fechaActual.plusYears(1).plusDays(2));

        Assert.assertEquals(6,listadoDeEventosPlusDias.size());
        Assert.assertEquals(20,listadoDeEventosPlusAgno.size());

    }

    @Test
    public void eventoConUnaRepeticionMensualCantidad() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        calendario.agregarRepeticionMensualEvento(evento);
        calendario.modificarCantidadRepeticiones(evento,6);

        LocalDateTime fechaActual = LocalDate.now().atStartOfDay();
        var listadoDeEventosPlusDias = calendario.elementosEntreFechas(fechaActual,fechaActual.plusDays(2));
        var listadoDeEventosPlusMeses = calendario.elementosEntreFechas(fechaActual,fechaActual.plusMonths(2).plusDays(2));
        var listadoDeEventosPlusAgno = calendario.elementosEntreFechas(fechaActual,fechaActual.plusYears(1).plusDays(2));

        Assert.assertEquals(1,listadoDeEventosPlusDias.size());
        Assert.assertEquals(3,listadoDeEventosPlusMeses.size());
        Assert.assertEquals(6,listadoDeEventosPlusAgno.size());
    }

    @Test
    public void eventoConUnaRepeticionMensualInfinito() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        calendario.agregarRepeticionMensualEvento(evento);

        LocalDateTime fechaActual = LocalDate.now().atStartOfDay();
        var listadoDeEventosPlusDias = calendario.elementosEntreFechas(fechaActual,fechaActual.plusDays(2));
        var listadoDeEventosPlusMeses = calendario.elementosEntreFechas(fechaActual,fechaActual.plusMonths(2).plusDays(2));
        var listadoDeEventosPlusAgno = calendario.elementosEntreFechas(fechaActual,fechaActual.plusYears(1).plusDays(2));

        Assert.assertEquals(1,listadoDeEventosPlusDias.size());
        Assert.assertEquals(3,listadoDeEventosPlusMeses.size());
        Assert.assertEquals(13,listadoDeEventosPlusAgno.size());
    }

    @Test
    public void eventoConUnaRepeticionMensualVencimiento() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        calendario.agregarRepeticionMensualEvento(evento);
        calendario.modificarVencimientoRepeticion(evento, magnana.plusMonths(4));

        LocalDateTime fechaActual = LocalDate.now().atStartOfDay();
        var listadoDeEventosPlusDias = calendario.elementosEntreFechas(fechaActual,fechaActual.plusDays(2));
        var listadoDeEventosPlusMeses = calendario.elementosEntreFechas(fechaActual,fechaActual.plusMonths(2).plusDays(2));
        var listadoDeEventosPlusAgno = calendario.elementosEntreFechas(fechaActual,fechaActual.plusYears(1).plusDays(2));

        Assert.assertEquals(1,listadoDeEventosPlusDias.size());
        Assert.assertEquals(3,listadoDeEventosPlusMeses.size());
        Assert.assertEquals(5,listadoDeEventosPlusAgno.size());
    }

    @Test
    public void eventoConUnaRepeticionAnual() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        calendario.agregarRepeticionAnualEvento(evento);
        calendario.modificarCantidadRepeticiones(evento,2);

        LocalDateTime fechaActual = LocalDate.now().atStartOfDay();
        var listadoDeEventos = calendario.elementosEntreFechas(fechaActual,fechaActual.plusYears(1).plusDays(2));

        Assert.assertEquals(2,listadoDeEventos.size());
    }
    @Test
    public void eventoConRepeticionAnualConVencimiento(){
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        var horaActualTruncada = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        calendario.agregarRepeticionAnualEvento(evento);
        calendario.modificarVencimientoRepeticion(evento,LocalDateTime.now().plusYears(1).plusDays(1));

        var listadoDeEventos = calendario.elementosEntreFechas(horaActualTruncada.minusDays(1),horaActualTruncada.plusYears(10));
        Assert.assertEquals(2,listadoDeEventos.size());
    }

    @Test
    public void eventoConRepeticionAnualInfinita() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();

        calendario.agregarRepeticionAnualEvento(evento);
        var horaEvento = evento.getFecha();
        var listadoDeEventos = calendario.elementosEntreFechas(horaEvento,horaEvento.plusYears(1).plusHours(1));
        Assert.assertEquals(2,listadoDeEventos.size());
    }
    @Test
    public void eliminarEventoConUnaRepeticionAnual() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();

        calendario.agregarRepeticionAnualEvento(evento);
        var horaEvento = evento.getFecha();
        var listado = calendario.elementosEntreFechas(horaEvento,horaEvento.plusYears(3).plusHours(1));


        Assert.assertEquals(4, listado.size());
        calendario.eliminarElementoCalendario(evento);
       listado = calendario.elementosEntreFechas(horaEvento,horaEvento.plusYears(3));


        Assert.assertEquals(0, listado.size());

    }

    @Test
    public void modificarEventoConUnaRepeticionAnual() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();

        calendario.agregarRepeticionAnualEvento(evento);
        var horaEvento = evento.getFecha();
        var listado = calendario.elementosEntreFechas(horaEvento,horaEvento.plusYears(3).plusHours(1));
        Assert.assertEquals("My Event", listado.first().getTitulo());
        Assert.assertEquals("My Event", listado.last().getTitulo());
        Assert.assertEquals(horaEvento, listado.first().getFecha());
        Assert.assertEquals(horaEvento.plusYears(3), listado.last().getFecha());
        calendario.modificarTitulo(evento, "Final");
        calendario.modificarFecha(evento, magnana.plusMonths(2).plusHours(5));
        listado = calendario.elementosEntreFechas(horaEvento,horaEvento.plusYears(3));
        Assert.assertEquals("Final", listado.first().getTitulo());
        Assert.assertEquals("Final", listado.last().getTitulo());
        Assert.assertEquals(magnana.plusMonths(2).plusHours(5), listado.first().getFecha());
        Assert.assertEquals(magnana.plusYears(2).plusMonths(2).plusHours(5), listado.last().getFecha());

    }

    @Test
    public void sonarProximaAlarma() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        calendario.modificarFecha(evento, magnana);
        var alarma = evento.agregarAlarma(magnana, diezMinutos);
        alarma.setEfecto(EfectoAlarma.SONIDO);

        var horaASonar = magnana.minus(diezMinutos);

        Assert.assertEquals(horaASonar, evento.horarioProximaAlarma(hoy));
        Assert.assertEquals(EfectoAlarma.SONIDO, calendario.sonarProximaAlarma(hoy, hoy.plusDays(5)).sonarProximaAlarma(horaASonar));
    }
    @Test
    public void sonarAlarmaRepeticion() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        calendario.agregarRepeticionDiariaEvento(evento);
        var horaInicioEvento = evento.getFecha();
        var elementos = calendario.elementosEntreFechas(horaInicioEvento.minusMinutes(60), horaInicioEvento.plusDays(1).minusHours(10));
        Assert.assertEquals(1,elementos.size());
        var horaASonar = horaInicioEvento.minus(diezMinutos).plusDays(1);
        Assert.assertEquals(EfectoAlarma.NOTIFICACION,calendario.sonarProximaAlarma(horaASonar, horaASonar.plusDays(1)).sonarProximaAlarma(horaASonar));
    }

    @Test
    public void modificarApartirDeUnaRepeticion() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        evento.setDescripcion("antes de la prueba");
        evento.setRepeticionDiaria(1);
        var listadoEventos = calendario.elementosEntreFechas(hoy.minusDays(1), magnana.plusDays(3));
        var repeticionEvento = listadoEventos.pollLast();
        calendario.modificarDescripcion(repeticionEvento, "probando comparador");

        Assert.assertEquals("probando comparador",evento.getDescripcion());
    }

    @Test
    public void eliminarApartirDeUnaRepeticion() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        evento.setRepeticionDiaria(1);
        var listadoEventos = calendario.elementosEntreFechas(hoy.minusDays(1), magnana.plusDays(3));
        var repeticionEvento = listadoEventos.pollLast();
        calendario.eliminarElementoCalendario(repeticionEvento);
        listadoEventos = calendario.elementosEntreFechas(hoy.minusDays(1), magnana.plusDays(3));

        Assert.assertEquals(0,listadoEventos.size());
    }

}
