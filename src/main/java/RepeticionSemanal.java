import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

public class RepeticionSemanal extends Repeticion {
    private final Set<DayOfWeek> dias;

    public RepeticionSemanal(Set<DayOfWeek> dias) {
        super();
        this.dias = dias;
    }

    @Override
    public void setCantidadRepeticiones(LocalDateTime inicio, long cantidadRepeticiones) {
        var semanas = (cantidadRepeticiones-1)/dias.size();
        var diasSumar = 0;
        var resto = cantidadRepeticiones-1-(semanas*dias.size());
        List <Integer> valores_dias = ListaDiasConRepeticion(inicio);

        while(resto != 0) {
            diasSumar += valores_dias.get(0);
            valores_dias.remove(0);
            resto -= 1;
        }
        var fecha = inicio.plusWeeks(semanas);
        this.vencimiento = fecha.plusDays(diasSumar);
    }

    @Override
    public LocalDateTime Repetir(LocalDateTime inicio) {
        List <Integer> valores_dias = ListaDiasConRepeticion(inicio);

        var fechaRepeticion = inicio.plusDays(valores_dias.get(0));
        if(noEstaVencida(fechaRepeticion))
            return fechaRepeticion;
        else
            return null;
    }

    // Funcion que devuelve una lista ordenada con la cantidad de dias que faltan para cada dia de la semana en que
    // se repita el evento tomando como dia de partida el correspondiente a inicio
    private List <Integer> ListaDiasConRepeticion(LocalDateTime inicio){
        var valor_dia_inicial = inicio.getDayOfWeek().getValue();
        List <Integer> valores_dias = new LinkedList<>();

        for(DayOfWeek dia: dias){
            if(valor_dia_inicial > dia.getValue()){
                valores_dias.add((7 + dia.getValue() - valor_dia_inicial));
            }
            else if(valor_dia_inicial < dia.getValue()){
                valores_dias.add((dia.getValue() - valor_dia_inicial));
            }
        }
        Collections.sort(valores_dias);

        return valores_dias;

    }
}
