import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TareaTest {
    private final LocalDateTime ahoraTruncado =  LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    private final Duration diezMinutos = Duration.ofMinutes(10);
    private final  LocalDateTime magnana = ahoraTruncado.plusDays(1);


    @Test
    public void crearTarea() {
        var tarea = new Tarea(ahoraTruncado);
        tarea.setTitulo("Sacar la basura");
        tarea.setDescripcion("El basurero esta lleno");

        Assert.assertEquals("Sacar la basura", tarea.getTitulo());
        Assert.assertEquals("El basurero esta lleno", tarea.getDescripcion());

        Assert.assertEquals(ahoraTruncado, tarea.getFecha());
        Assert.assertFalse(tarea.estaCompleta());
        Assert.assertNull(tarea.horarioProximaAlarma(ahoraTruncado));
    }

    @Test
    public void tareaDeDiaCompleto() {
        var tarea = new Tarea(ahoraTruncado);
        tarea.setDeDiaCompleto();
        var vencimientoEsperado  = fechaAlFinalDelDia(ahoraTruncado);

        Assert.assertEquals(vencimientoEsperado,tarea.getFecha());
        Assert.assertNull(tarea.horarioProximaAlarma(ahoraTruncado));
    }


    private LocalDateTime fechaAlFinalDelDia(LocalDateTime fechaYHora){
        var fecha = fechaYHora.toLocalDate().atStartOfDay();
        var duracionUnDia = Duration.ofHours(23).plusMinutes(59);
        return fecha.plus(duracionUnDia);
    }

    @Test
    public void agregarAlarmasFechaAbs() {
        var tarea = new Tarea(ahoraTruncado);
        var alarma = tarea.agregarAlarmaAbsoluta(magnana);
        tarea.modificarAlarmaEfecto(alarma, EfectoAlarma.NOTIFICACION);

        Assert.assertEquals(magnana,alarma.getFechaYHora());
        Assert.assertEquals(magnana,tarea.horarioProximaAlarma(ahoraTruncado));

    }

    @Test
    public void sonarProximaAlarma() {
        var tarea = new Tarea(ahoraTruncado);
        tarea.setFecha(magnana);
        var alarma = tarea.agregarAlarma(magnana, diezMinutos);
        tarea.modificarAlarmaEfecto(alarma,EfectoAlarma.SONIDO);

        var horaASonar = magnana.minus(diezMinutos);

        Assert.assertEquals(horaASonar, tarea.horarioProximaAlarma(ahoraTruncado));
        Assert.assertEquals(EfectoAlarma.SONIDO, tarea.sonarProximaAlarma(horaASonar));
    }

    @Test
    public void sonarAlarmaSiYaPasoElHorarioASonar() {
        var tarea = new Tarea(ahoraTruncado);

        var horaASonar = ahoraTruncado.minus(diezMinutos);

        var despuesDeLaHoraASonar = horaASonar.plusMinutes(1);
        Assert.assertNull(tarea.sonarProximaAlarma(despuesDeLaHoraASonar));
    }

    @Test
    public void modificarFechaSiEsDeDiaCompleto() {
        var tarea = new Tarea(ahoraTruncado);
        tarea.setDeDiaCompleto();
        var fechaACambiar = LocalDateTime.now().plusHours(62);
        tarea.setFecha(fechaACambiar);

        var fechaEsperada = fechaACambiar.toLocalDate().atStartOfDay().plusHours(23).plusMinutes(59);

        Assert.assertEquals(fechaEsperada,tarea.getFecha());

    }

    @Test
    public void modificarFechaSiEsDeFechaArbitraria() {
        var tarea = new Tarea(ahoraTruncado);
        var fechaACambiar = LocalDateTime.now().plusHours(62);

        tarea.setFecha(fechaACambiar);
        Assert.assertEquals(fechaACambiar,tarea.getFecha());
    }


    @Test
    public void fechaAbsolutaDeAlarma() {
        var tarea = new Tarea(ahoraTruncado);
        var fechaACambiar = LocalDateTime.now().plusHours(62);

        var alarma = tarea.agregarAlarmaAbsoluta(ahoraTruncado);
        tarea.modificarFechaAbsolutaAlarma(alarma, fechaACambiar);
        Assert.assertEquals(fechaACambiar,tarea.horarioProximaAlarma(ahoraTruncado));

    }
    
}