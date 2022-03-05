package Licitatii;

public class SumaLicitatie {
    private int idLicitatie, idClient;
    private double suma;

    //Constructor ce initializeaza campurile din SumaLicitatie
    public SumaLicitatie(int idLicitatie, double suma, int idClient) {
        this.idLicitatie = idLicitatie;
        this.suma = suma;
        this.idClient = idClient;
    }

    public int getIdLicitatie() {
        return idLicitatie;
    }

    public double getSuma() {
        return suma;
    }

    public int getIdClient() {
        return idClient;
    }

    @Override
    //Metoda ce compara doua obiecte de tip SumaLicitatie
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SumaLicitatie that = (SumaLicitatie) o;
        return idLicitatie == that.idLicitatie;
    }
}
