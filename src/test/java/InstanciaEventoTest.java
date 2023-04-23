import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class InstanciaEventoTest {

    private  final LocalDateTime ahora = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    private final LocalDateTime magnana = ahora.plusDays(1);

    private final Duration diezMinutos = Duration.ofMinutes(10);

    @Test
    public void crearInstancia() {
        var evento = new Evento(ahora);
        var instancia = new InstanciaEvento(evento,ahora);

        Assert.assertEquals(evento.getFecha(),instancia.getFecha());
        Assert.assertEquals(evento.getTitulo(),instancia.getTitulo());
        Assert.assertEquals(evento.getDescripcion(),instancia.getDescripcion());
    }

    @Test
    public void probarAlarmaParaInstancia() {
        var evento = new Evento(ahora);
        var instancia = new InstanciaEvento(evento,ahora);
        var horarioAlarma = ahora.minus(diezMinutos);
        Assert.assertEquals(evento.sonarProximaAlarma(horarioAlarma),instancia.sonarProximaAlarma(horarioAlarma));
    }


    @Test
    public void crearInstanciaConDistintaFecha() {

        var evento = new Evento(ahora);
        var instancia = new InstanciaEvento(evento,magnana);
        Assert.assertNotEquals(evento.getFecha(),instancia.getFecha());
    }

    @Test
    public void probarAlarmaParaInstanciaConDistintaFecha() {
        var evento = new Evento(ahora);
        var instancia = new InstanciaEvento(evento,magnana);
        var horarioAlarma = ahora.minus(diezMinutos);
        var horarioAlarmaMagnana = magnana.minus(diezMinutos);

        Assert.assertNotEquals(evento.proximaAlarma(horarioAlarma),instancia.proximaAlarma(horarioAlarma));
        Assert.assertEquals(evento.sonarProximaAlarma(horarioAlarma),instancia.sonarProximaAlarma(horarioAlarmaMagnana));
    }

    @Test
    public void cambiarTitulo() {
        var evento = new Evento(ahora);
        var instancia = new InstanciaEvento(evento,magnana);

        instancia.setTitulo("Nuevo titulo");
        Assert.assertEquals(instancia.getTitulo(),evento.getTitulo());

    }

    @Test
    public void alarmasAbsolutasParaInstancia(){
        var evento = new Evento(ahora);
        var instancia = new InstanciaEvento(evento,ahora);
        var alarmaAbs = ahora.plusDays(5);
        instancia.agregarAlarmaAbsoluta(alarmaAbs);

        Assert.assertEquals(evento.proximaAlarma(alarmaAbs),instancia.proximaAlarma(alarmaAbs));
    }
    @Test
    public void alarmasAbsolutasParaInstanciaConDistintaFecha(){
        var evento = new Evento(ahora);
        var instancia = new InstanciaEvento(evento,magnana);
        var alarmaAbs = ahora.plusDays(5);
        instancia.agregarAlarmaAbsoluta(alarmaAbs);
        var horarioAsonarInstancia = alarmaAbs.plusDays(1);

        Assert.assertNotEquals(evento.proximaAlarma(alarmaAbs),instancia.proximaAlarma(alarmaAbs));
        Assert.assertEquals(evento.sonarProximaAlarma(alarmaAbs),instancia.sonarProximaAlarma(horarioAsonarInstancia));
    }


}