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
    public void eventoConUnaRepeticionAnual() {//tarda mucho este test
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        var repeticionAnual = new RepeticionAnual();
        repeticionAnual.setCantidadRepeticiones(1);

        calendario.modificarRepeticionEvento(evento,repeticionAnual);
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
        calendario.modificarRepeticionEvento(evento,repeticionAnual);
        var listadoDeEventos = calendario.eventosEntreFechas(horaActualTruncada,horaActualTruncada.plusYears(2));
        Assert.assertEquals(2,listadoDeEventos.size());
    }

    @Test
    public void eventoConRepeticionAnualInfinita() {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        var repeticionAnual = new RepeticionAnual();
        repeticionAnual.setRepeticionInfinita();

        calendario.modificarRepeticionEvento(evento,repeticionAnual);
        var horaEvento = evento.getFechaInicial();
        var listadoDeEventos = calendario.eventosEntreFechas(horaEvento,horaEvento.plusYears(1));
        //arreglar esto para que no queden numeros random (sumo una hora por como se crea el evento default)

        Assert.assertEquals(2,listadoDeEventos.size());
    }
}