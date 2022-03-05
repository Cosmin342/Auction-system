package Licitatii;

import java.util.Random;

public class Licitatie {
    private int id, nrParticipanti = 1, idProdus, nrPasiMaxim;

    //Constructor ce initializeaza campurile id si idProdus, respectiv
    //campul nrPasiMaxim cu o valoare random intre 1 si 6
    public Licitatie(int id, int idProdus) {
        this.id = id;
        this.idProdus = idProdus;
        Random random = new Random();
        this.nrPasiMaxim = random.nextInt(5) + 1;
    }

    //Metodele urmatoare reprezinta getteri si setteri pentru campurile licitatiei
    public int getIdProdus() {
        return idProdus;
    }

    public int getNrParticipanti() {
        return nrParticipanti;
    }

    public void setNrParticipanti(int nrParticipanti) {
        this.nrParticipanti = nrParticipanti;
    }

    public int getId() {
        return id;
    }

    public int getNrPasiMaxim() {
        return nrPasiMaxim;
    }
}
