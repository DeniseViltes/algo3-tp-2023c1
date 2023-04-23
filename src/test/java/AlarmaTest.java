import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class AlarmaTest {
    private final LocalDateTime ahora = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    private final LocalDateTime diezMinutosAntes = ahora.minusMinutes(10);
    @Test
    public void alarmaDefault() {
        var alarma = new Alarma(ahora);

        Assert.assertEquals(diezMinutosAntes,alarma.getFechaYHora());
        Assert.assertEquals(EfectoAlarma.NOTIFICACION,alarma.sonar(diezMinutosAntes));
        Assert.assertNull(alarma.sonar(ahora));
    }

    @Test
    public void cambiarRefernecia() {
        var alarma = new Alarma(ahora);


        var nuevaReferencia = ahora.plusDays(1);
        alarma.setReferencia(nuevaReferencia);

        Assert.assertEquals(diezMinutosAntes.plusDays(1),alarma.getFechaYHora());
    }

    @Test
    public void cambiarIntervalo() {
        var alarma = new Alarma(ahora);
        var intervaloNuevo = Duration.ofHours(100);

        alarma.setIntervalo(intervaloNuevo);

        Assert.assertEquals(ahora.minus(intervaloNuevo),alarma.getFechaYHora());
    }

    @Test
    public void alarmaAbsoluta() {
        var alarma = new Alarma(ahora);
        var fecha = ahora.plusDays(100);

        alarma.setAlarmaAbsoluta(fecha);

        Assert.assertEquals(fecha,alarma.getFechaYHora());
    }

    @Test
    public void sonarAlarma() {

        var alarma = new Alarma(ahora);
        Assert.assertEquals(EfectoAlarma.NOTIFICACION,alarma.sonar(diezMinutosAntes));
        alarma.setEfecto(EfectoAlarma.MAIL);
        Assert.assertEquals(EfectoAlarma.MAIL,alarma.sonar(diezMinutosAntes));
        alarma.setEfecto(EfectoAlarma.SONIDO);
        Assert.assertEquals(EfectoAlarma.SONIDO,alarma.sonar(diezMinutosAntes));

    }
}