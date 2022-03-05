package Persoana;

public class PersoanaJuridica extends Client{
    private TipCompanie companie;
    private double capitalSocial;

    //Constructor ce initializeaza campurile persoanei juridice si pe cele mostenite
    public PersoanaJuridica(int id, String nume, String adresa, TipCompanie companie,
                            double sold, double capitalSocial) {
        super(id, nume, adresa, sold);
        this.companie = companie;
        this.capitalSocial = capitalSocial;
    }

    @Override
    //Metoda ce intoarce datele despre persoana fizica
    public String toString() {
        return "Persoana Juridica " + super.toString() + " " +
                capitalSocial + " " + companie;
    }

}
