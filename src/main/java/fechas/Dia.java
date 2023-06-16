package fechas;

public enum Dia {
    MONDAY("MONDAY", "LUN"),
    TUESDAY("TUESDAY", "MAR"),
    WEDNESDAY("WEDNESDAY", "MIE"),
    THURSDAY("THURSDAY", "JUE"),
    FRIDAY("FRIDAY", "VIE"),
    SATURDAY("SATURDAY", "SAB"),
    SUNDAY("SUNDAY", "DOM");
    Dia(String ingles, String espanol) {
        this.espanol = espanol;
    }

    private final String espanol;

    public String getDiaEspanol() {
        return espanol;
    }

}
