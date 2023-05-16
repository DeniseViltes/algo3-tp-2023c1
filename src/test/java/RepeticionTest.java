import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.TreeSet;


public class RepeticionTest {

    private  final LocalDateTime ahora = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    private final LocalDateTime magnana = ahora.plusDays(1);

    private final Duration diezMinutos = Duration.ofMinutes(10);

    @Test
    public void probarAlarmaParaRepeticion() {
        var evento = new Evento(ahora);
        var instancia = evento.crearRepeticion(ahora);
        var horarioAlarma = ahora.minus(diezMinutos);
        Assert.assertEquals(evento.sonarProximaAlarma(horarioAlarma),instancia.sonarProximaAlarma(horarioAlarma));
    }


    @Test
    public void crearRepeticionConDistintaFecha() {

        var evento = new Evento(ahora);
        var instancia = evento.crearRepeticion(ahora.plusDays(1));
        Assert.assertNotEquals(evento.getFecha(),instancia.getFecha());
    }

    @Test
    public void probarAlarmaParaRepeticionConDistintaFecha() {
        var evento = new Evento(ahora);
        var instancia = evento.crearRepeticion(ahora.plusDays(1));
        var horarioAlarma = ahora.minus(diezMinutos);
        var horarioAlarmaMagnana = magnana.minus(diezMinutos);

        Assert.assertNotEquals(evento.horarioProximaAlarma(horarioAlarma),instancia.horarioProximaAlarma(horarioAlarma));
        Assert.assertEquals(evento.sonarProximaAlarma(horarioAlarma),instancia.sonarProximaAlarma(horarioAlarmaMagnana));
    }

    @Test
    public void alarmasAbsolutasParaRepeticion(){
        var calendario = new Calendario();
        Evento evento = calendario.crearEvento();
        evento.setRepeticionDiaria(1);
        TreeSet<ElementoCalendario> elementos = calendario.elementosEntreFechas(ahora.minusHours(1), ahora.plusDays(1));
        var alarmaAbs = ahora.plusDays(5);
        Evento repeticion = (Evento) elementos.last();
        Alarma alarma = calendario.agregarAlarmaAbsoluta(repeticion,alarmaAbs);
        TreeSet<ElementoCalendario> elementos2 = calendario.elementosEntreFechas(ahora.minusHours(1), ahora.plusDays(1).plusHours(20));
        Assert.assertEquals(elementos2.first().horarioProximaAlarma(ahora.plusDays(1)).plusDays(1),elementos2.last().horarioProximaAlarma(ahora.plusDays(2)));

        calendario.modificarAlarmaFechaAbsoluta(repeticion, alarma, ahora.plusDays(10));
        TreeSet<ElementoCalendario> elementos3 = calendario.elementosEntreFechas(ahora.minusHours(1), ahora.plusDays(1).plusHours(20));
        Assert.assertEquals(elementos3.first().horarioProximaAlarma(alarmaAbs).plusDays(1),elementos3.last().horarioProximaAlarma(alarmaAbs));
    }

    @Test
    public void alarmasRemoverParaRepeticion(){
        var calendario = new Calendario();
        Evento evento = calendario.crearEvento();
        Evento repeticion = evento.crearRepeticion(ahora.minusMinutes(10));
        var alarmaAbs = ahora.plusDays(5);
        repeticion.agregarAlarmaAbsoluta(alarmaAbs);
        TreeSet<ElementoCalendario> elementos = calendario.elementosEntreFechas(ahora.minusDays(2), ahora.plusDays(2));

        Assert.assertEquals(elementos.first().horarioProximaAlarma(alarmaAbs),elementos.last().horarioProximaAlarma(alarmaAbs));
    }

    @Test
    public void alarmasAbsolutasParaRepeticionConDistintaFecha(){
        var evento = new Evento(ahora);
        var instancia = evento.crearRepeticion(magnana);
        var alarmaAbs = ahora.plusDays(5);
        instancia.agregarAlarmaAbsoluta(alarmaAbs);
        var horarioAsonarInstancia = alarmaAbs.plusDays(1);

        Assert.assertNotEquals(evento.horarioProximaAlarma(alarmaAbs),instancia.horarioProximaAlarma(alarmaAbs));
        Assert.assertEquals(evento.sonarProximaAlarma(alarmaAbs),instancia.sonarProximaAlarma(horarioAsonarInstancia));
    }


}