package Casa;

import Persoana.Client;

public interface PrelucratorLicitatii {
    void pornestelicitatia(int idProdus);
    void cautaSiInscrieLaLicitatie(Client client, String comanda, int nrClienti);
}
