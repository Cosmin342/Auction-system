package Licitatii;

public abstract class Produs {
    private int id, an;
    private String nume;
    private double pretVanzare, pretMinim;

    //Metodele urmatoare reprezinta setteri si getteri pentru campurile din Produs
    public void setId(int id) {
        this.id = id;
    }

    public void setAn(int an) {
        this.an = an;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPretVanzare(double pretVanzare) {
        this.pretVanzare = pretVanzare;
    }

    public void setPretMinim(double pretMinim) {
        this.pretMinim = pretMinim;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }


    public double getPretMinim() {
        return pretMinim;
    }

    @Override
    //Metoda intoarce datele despre produs sub forma unui sir
    public String toString() {
        return "ID: " + id + "\nNume: " + nume + "\nAn: " + an;
    }
}
