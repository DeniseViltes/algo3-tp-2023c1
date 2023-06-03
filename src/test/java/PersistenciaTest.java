import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class PersistenciaTest {
    private final LocalDateTime hoy = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    private final LocalDateTime magnana = hoy.plusDays(1);

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
        var evento = calendario.crearEvento();
        calendario.modificarTitulo(evento,"Prueba persistencia");
        var listadoOriginal = calendario.elementosEntreFechas(hoy, magnana);
        var bytes = new ByteArrayOutputStream();
        calendario.serializar(bytes);

        var calendarioDeserializado = Calendario.deserializar(new ByteArrayInputStream(bytes.toByteArray()));
        var listadoDeserializado = calendarioDeserializado.elementosEntreFechas(hoy,magnana);
        var eventoDeserializado = listadoDeserializado.first();
        Assert.assertNotNull(calendarioDeserializado);
        Assert.assertEquals(listadoOriginal.size(),listadoDeserializado.size());
        Assert.assertEquals(evento.getTitulo(),eventoDeserializado.getTitulo());
    }

    @Test
    public void PersistenciaCalendarioConVariosElementos() throws IOException, ClassNotFoundException {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        calendario.modificarTitulo(evento,"Prueba persistencia");
        calendario.modificarDescripcion(evento,"Es un evento");
        calendario.agregarRepeticionDiariaEvento(evento);
        var tarea = calendario.crearTarea();
        calendario.modificarFecha(tarea,magnana);
        calendario.modificarTitulo(tarea,"Prueba persistencia");
        calendario.modificarDescripcion(tarea,"Es una tarea");

        var listadoOriginal = calendario.elementosEntreFechas(hoy, magnana.plusDays(2));

        var bytes = new ByteArrayOutputStream();
        calendario.serializar(bytes);

        var calendarioDeserializado = Calendario.deserializar(new ByteArrayInputStream(bytes.toByteArray()));
        var listadoDeserializado = calendarioDeserializado.elementosEntreFechas(hoy,magnana.plusDays(2));

        var iterador = listadoDeserializado.iterator();
        var eventoDeserializado = iterador.next();
        var tareaDeserializada = iterador.next();
        Assert.assertNotNull(calendarioDeserializado);
        Assert.assertEquals(listadoOriginal.size(),listadoDeserializado.size());
        Assert.assertEquals(evento.getTitulo(),eventoDeserializado.getTitulo());
        Assert.assertEquals(evento.getDescripcion(),eventoDeserializado.getDescripcion());
        Assert.assertEquals(tarea.getTitulo(),tareaDeserializada.getTitulo());
        Assert.assertEquals(tarea.getDescripcion(),tareaDeserializada.getDescripcion());
    }


    @Test
    public void guardarEnArchivo() throws IOException, ClassNotFoundException {
        var calendario = new Calendario();
        var evento = calendario.crearEvento();
        evento.setRepeticionDiaria(1);
        var listadoEventos = calendario.elementosEntreFechas(hoy.minusDays(1), magnana.plusDays(3));
        ProcesadorDeArchivoCalendario.guardarCalendarioEnArchivo(calendario,"serializa.cal");
        Calendario cal2 = ProcesadorDeArchivoCalendario.leerCalendarioDeArchivo("serializa.cal");
        var listadoEventos2 = cal2.elementosEntreFechas(hoy.minusDays(1), magnana.plusDays(3));

        Assert.assertTrue(listadoEventos.first().comparar(listadoEventos2.first()));
    }
}