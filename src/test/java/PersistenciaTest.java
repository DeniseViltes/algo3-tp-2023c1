import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class PersistenciaTest {
    private final LocalDateTime hoy = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    private final LocalDateTime magnana = hoy.plusDays(1);

    private final Duration diezMinutos = Duration.ofMinutes(10);

    private final LocalDateTime ahora = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    @Test
    public void PersistenciaCalenarioVacio() throws IOException, ClassNotFoundException {
        var calendario = new Calendario();
        var listadoOriginal = calendario.elementosEntreFechas(hoy, magnana);

        var bytes = new ByteArrayOutputStream();
        calendario.serializar(bytes);

        var calendarioDeserializado = Calendario.deserializar(new ByteArrayInputStream(bytes.toByteArray()));
        var listadoDeserializado = calendarioDeserializado.elementosEntreFechas(hoy,magnana);
        Assert.assertNotNull(calendarioDeserializado);
        Assert.assertEquals(listadoOriginal.size(),listadoDeserializado.size());
    }

    @Test
    public void PersistenciaCalendarioConUnEvento() throws IOException, ClassNotFoundException {
        var calendario = new Calendario();
        var listadoOriginal = calendario.elementosEntreFechas(hoy, magnana);
        var evento = calendario.crearEvento();

        var bytes = new ByteArrayOutputStream();
        calendario.serializar(bytes);

        var calendarioDeserializado = Calendario.deserializar(new ByteArrayInputStream(bytes.toByteArray()));
        var listadoDeserializado = calendarioDeserializado.elementosEntreFechas(hoy,magnana);
        Assert.assertNotNull(calendarioDeserializado);
        Assert.assertEquals(listadoOriginal.size(),listadoDeserializado.size());

    }
}