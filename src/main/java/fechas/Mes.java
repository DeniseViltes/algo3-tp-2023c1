package fechas;

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

    Mes(String ingles, String espanol) {
        this.espanol = espanol;
    }

    private final String espanol;

    public String getMesEspanol() {
        return espanol;
    }

}
