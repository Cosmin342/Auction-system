package Persoana;

public class PersoanaFizica extends Client{
    private String dataNastere;

    //Constructor ce initializeaza campurile persoanei fizice si pe cele mostenite
    public PersoanaFizica(int id, String nume, String adresa, String dataNastere, double sold) {
        super(id, nume, adresa, sold);
        this.dataNastere = dataNastere;
    }

    @Override
    //Metoda ce intoarce datele despre persoana fizica
    public String toString() {
        return "Persoana Fizica " + super.toString() + " " + dataNastere;
    }
}
