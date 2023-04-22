import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class CalendarioTest {

    //Eventos
    @Test
    public void crearEvento() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();




    }

    @Test
    public void eventoConUnaRepeticionMensualInfinita() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        evento.setRepeticionMensual();

        LocalDateTime fechaActual = LocalDate.now().atStartOfDay();
        var listadoDeEventosPlusDias = calendario.eventosEntreFechas(fechaActual,fechaActual.plusDays(2));
        var listadoDeEventosPlusAño = calendario.eventosEntreFechas(fechaActual,fechaActual.plusYears(1).plusDays(2));

        Assert.assertEquals(1,listadoDeEventosPlusDias.size());
        Assert.assertEquals(13,listadoDeEventosPlusAño.size());


    }

    @Test
    public void eventoConUnaRepeticionMensualCantidad() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        evento.setRepeticionMensual();
        evento.setRepeticionCantidad(6);

        LocalDateTime fechaActual = LocalDate.now().atStartOfDay();
        var listadoDeEventosPlusDias = calendario.eventosEntreFechas(fechaActual,fechaActual.plusDays(2));
        var listadoDeEventosPlusMeses = calendario.eventosEntreFechas(fechaActual,fechaActual.plusMonths(2).plusDays(2));
        var listadoDeEventosPlusAño = calendario.eventosEntreFechas(fechaActual,fechaActual.plusYears(1).plusDays(2));

        Assert.assertEquals(1,listadoDeEventosPlusDias.size());
        Assert.assertEquals(3,listadoDeEventosPlusMeses.size());
        Assert.assertEquals(6,listadoDeEventosPlusAño.size());


    }



    @Test
    public void eventoConUnaRepeticionAnual() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        evento.setRepeticionAnual();
        evento.setRepeticionCantidad(2);

        LocalDateTime fechaActual = LocalDate.now().atStartOfDay();
        var listadoDeEventos = calendario.eventosEntreFechas(fechaActual,fechaActual.plusYears(1).plusDays(2));

        Assert.assertEquals(2,listadoDeEventos.size());
    }
    @Test
    public void eventoConRepeticionAnualConVencimiento(){
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        var repeticionAnual = new RepeticionAnual();
        var horaActualTruncada = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        repeticionAnual.setVencimiento(LocalDateTime.of(LocalDate.now().plusYears(1), LocalTime.from(horaActualTruncada)));
        evento.setRepeticionAnual();
        var listadoDeEventos = calendario.eventosEntreFechas(horaActualTruncada,horaActualTruncada.plusYears(2));
        Assert.assertEquals(2,listadoDeEventos.size());
    }

    @Test
    public void eventoConRepeticionAnualInfinita() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        var repeticionAnual = new RepeticionAnual();

        evento.setRepeticionAnual();
        var horaEvento = evento.getFecha();
        var listadoDeEventos = calendario.eventosEntreFechas(horaEvento,horaEvento.plusYears(1));
        Assert.assertEquals(2,listadoDeEventos.size());
    }
    @Test
    public void eliminarEventoConUnaRepeticionAnual() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        var repeticionAnual = new RepeticionAnual();

        evento.setRepeticionAnual();
        var horaEvento = evento.getFecha();
        var listado = calendario.eventosEntreFechas(horaEvento,horaEvento.plusYears(3));


        Assert.assertEquals(4, listado.size());

        calendario.eliminarEvento(evento);

        listado = calendario.eventosEntreFechas(horaEvento,horaEvento.plusYears(3));

        Assert.assertEquals(0, listado.size());

    }
}
