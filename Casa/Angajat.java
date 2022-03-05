package Casa;

public abstract class Angajat {
    private final String nume;
    private final String tip;
    private final int id;

    //Constructor ce initializeaza campurile angajatului
    public Angajat(String nume, int id, String tip) {
        this.nume = nume;
        this.id = id;
        this.tip = tip;
    }

    //Getter ce intoarce numele angajatului
    public String getNume() {
        return nume;
    }
}
