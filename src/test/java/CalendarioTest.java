import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class CalendarioTest {
    private final LocalDateTime hoy = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    private final LocalDateTime magnana = hoy.plusDays(1);

    private final LocalDateTime ahora = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    //Eventos
    @Test
    public void crearEvento() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        Assert.assertEquals(1,calendario.elementosEntreFechas(hoy, magnana).size());
        Assert.assertEquals(ahora.plusHours(1),calendario.verFechaYHora(evento));
    }

    @Test
    public void eventoConUnaRepeticionMensualInfinita() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        calendario.agregarRepeticionMensualEvento(evento);
        LocalDateTime fechaActual = LocalDate.now().atStartOfDay();
        var listadoDeEventosPlusDias = calendario.elementosEntreFechas(fechaActual,fechaActual.plusDays(2));
        var listadoDeEventosPlusAgno = calendario.elementosEntreFechas(magnana,fechaActual.plusYears(1).plusDays(2));

        Assert.assertEquals(1,listadoDeEventosPlusDias.size());
        Assert.assertEquals(12,listadoDeEventosPlusAgno.size());

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
        var vencimiento = LocalDateTime.of(LocalDate.now().plusYears(1), LocalTime.from(horaActualTruncada).plusHours(1));
        calendario.modificarVencimientoRepeticion(evento,vencimiento);

        var listadoDeEventos = calendario.elementosEntreFechas(horaActualTruncada,horaActualTruncada.plusYears(10));
        Assert.assertEquals(2,listadoDeEventos.size());
    }

    @Test
    public void eventoConRepeticionAnualInfinita() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();

        calendario.agregarRepeticionAnualEvento(evento);
        var horaEvento = evento.getFecha();
        var listadoDeEventos = calendario.elementosEntreFechas(horaEvento,horaEvento.plusYears(1));
        Assert.assertEquals(2,listadoDeEventos.size());
    }
    @Test
    public void eliminarEventoConUnaRepeticionAnual() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();

        calendario.agregarRepeticionAnualEvento(evento);
        var horaEvento = evento.getFecha();
        var listado = calendario.elementosEntreFechas(horaEvento,horaEvento.plusYears(3));


        Assert.assertEquals(4, listado.size());
        calendario.eliminarEvento(evento);
       listado = calendario.elementosEntreFechas(horaEvento,horaEvento.plusYears(3));


        Assert.assertEquals(0, listado.size());

    }

}
