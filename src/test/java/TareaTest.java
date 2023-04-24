import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TareaTest {
    private final LocalDateTime ahoraTruncado =  LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    private final Duration diezMinutos = Duration.ofMinutes(10);


    @Test
    public void crearTarea() {
        var tarea = new Tarea(ahoraTruncado);

        Assert.assertEquals(ahoraTruncado, tarea.getFecha());
        Assert.assertFalse(tarea.estaCompleta());
        Assert.assertNull(tarea.proximaAlarma(ahoraTruncado));
    }

    @Test
    public void tareaDeDiaCompleto() {
        var tarea = new Tarea(ahoraTruncado);
        tarea.setDeDiaCompleto();
        var vencimientoEsperado  = fechaAlFinalDelDia(ahoraTruncado);

        Assert.assertEquals(vencimientoEsperado,tarea.getFecha());
        Assert.assertNull(tarea.proximaAlarma(ahoraTruncado));
    }


    private LocalDateTime fechaAlFinalDelDia(LocalDateTime fechaYHora){
        var fecha = fechaYHora.toLocalDate().atStartOfDay();
        var duracionUnDia = Duration.ofHours(23).plusMinutes(59);
        return fecha.plus(duracionUnDia);
    }
    
}