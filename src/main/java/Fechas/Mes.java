package Fechas;

public enum Mes {
    JANUARY("JANUARY", "Enero"),
    FEBRUARY("FEBRUARY", "Febrero"),
    MARCH("MARCH", "Marzo"),
    APRIL("APRIL", "Abril"),
    MAY("MAY", "Mayo"),
    JUNE("JUNE", "Junio"),
    JULY("JULY", "Julio"),
    AUGUST("AUGUST", "Agosto"),
    SEPTEMBER("SEPTEMBER", "Septiembre"),
    OCTOBER("OCTOBER", "Octubre"),
    NOVEMBER("NOVEMBER", "Noviembre"),
    DECEMBER("DECEMBER", "Diciembre");

    private Mes(String ingles, String español) {
        this.ingles = ingles;
        this.español = español;
    }

    private String ingles;
    private String español;

    public String getMesEspañol() {
        return español;
    }

}
