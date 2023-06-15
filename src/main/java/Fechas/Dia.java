package Fechas;

public enum Dia {
    MONDAY("MONDAY", "LUN"),
    TUESDAY("TUESDAY", "MAR"),
    WEDNESDAY("WEDNESDAY", "MIE"),
    THURSDAY("THURSDAY", "JUE"),
    FRIDAY("FRIDAY", "VIE"),
    SATURDAY("SATURDAY", "SAB"),
    SUNDAY("SUNDAY", "DOM");
    private Dia(String ingles, String español) {
        this.ingles = ingles;
        this.español = español;
    }

    private String ingles;
    private String español;

    public String getDiaEspañol() {
        return español;
    }

}
