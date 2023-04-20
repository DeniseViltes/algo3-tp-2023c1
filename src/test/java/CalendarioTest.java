import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class CalendarioTest {

    //Eventos
    @Test
    public void crearEventoDefault() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        //despues agregar algunos getters para verificar que se creo bien



    }

    @Test
    public void eventoConUnaRepeticionMensualInfinita() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        evento.setRepeticionMensual();
        evento.setRepeticionInfinita();

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
    public void eventoConUnaRepeticionAnual() {//tarda mucho este test
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
        repeticionAnual.setRepeticionInfinita();

        evento.setRepeticionAnual();
        var horaEvento = evento.getFechaInicial();
        var listadoDeEventos = calendario.eventosEntreFechas(horaEvento,horaEvento.plusYears(1));
        //arreglar esto para que no queden numeros random (sumo una hora por como se crea el evento default)
        Assert.assertEquals(2,listadoDeEventos.size());
    }
    @Test
    public void eliminarEventoConUnaRepeticionAnual() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        var repeticionAnual = new RepeticionAnual();
        repeticionAnual.setRepeticionInfinita();

        evento.setRepeticionAnual();
        var horaEvento = evento.getFechaInicial();
        var listado = calendario.eventosEntreFechas(horaEvento,horaEvento.plusYears(3));


        Assert.assertEquals(4, listado.size());

        calendario.eliminarEvento(evento);

        listado = calendario.eventosEntreFechas(horaEvento,horaEvento.plusYears(3));

        Assert.assertEquals(0, listado.size());

    }
}
