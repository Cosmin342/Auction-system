package Casa;

import Licitatii.*;
import Persoana.*;

import java.util.ArrayList;

public class Broker extends Angajat{
    //Broker-ul va retine, pe langa campurile din angajat lista clientilor asociati,
    //un observator pentru notificarea clientilor, si un camp ce retine id-ul licitatiei curente
    private ArrayList<Client> clienti;
    private Observator observator;
    private int idLicitatieCurenta;

    //Constructorul va initializa campurile mostenite de la angajat
    public Broker(String nume, int id, String tip) {
        super(nume, id, tip);
    }

    /***
     * Metoda prin care broker-ul elimina un produs vandut
     * @param produs reprezinta produsul vandut
     */
    public void eliminaProdus(Produs produs, CasaLicitatii casaLicitatii){
        casaLicitatii.stergeProdus(produs);
    }

    /***
     * Metoda prin care broker-ul adauga un nou client in lista sa de clienti
     * @param client clientul de adaugat
     */
    public void adaugaClient(Client client){
        if (clienti == null){
            clienti = new ArrayList<>();
        }
        clienti.add(client);
    }

    //Setter pentru observator
    public void ataseazaObservator(Observator observator){
        this.observator = observator;
    }

    /***
     * Metoda pentru trimiterea unui mesaj la observator
     * @param mesaj mesajul trimis
     */
    public void notificaObservator(String mesaj){
        if (observator != null) {
            observator.afiseazaNotificare(mesaj);
        }
    }

    /***
     * Metoda prin care este notificat broker-ul de inceperea unei licitatii,
     * el anuntand ulterior clientii
     * @param idLicitatieCurenta id-ul licitatiei curente
     * @param idProdus id-ul produsului pentru care se liciteaza
     */
    public void ataseazaLicitatia(int idLicitatieCurenta, int idProdus) {
        this.idLicitatieCurenta = idLicitatieCurenta;
        if (observator != null) {
            this.notificaObservator("Licitatia " + idLicitatieCurenta +
                    " pentru produsul cu id-ul " + idProdus + " incepe");
        }
    }

    /***
     * Metoda ce preia banii clientilor si ii transmite casei de licitatii
     * @param pretMinim pretul minim de licitat
     * @return un arraylist ce contine sumele
     */
    public ArrayList<SumaLicitatie> preiaBanii(double pretMinim){
        ArrayList<SumaLicitatie> sumeLicitate = new ArrayList<>();
        if (clienti == null){
            return sumeLicitate;
        }
        for (Client c:clienti){
            //Se verifica daca actualul client participa la licitatia curenta
            ArrayList<SumaLicitatie> sume = c.getMaximeLicitatii();
            for (SumaLicitatie suma:sume){
                if (suma.getIdLicitatie() == this.idLicitatieCurenta){
                    //Daca da, se preia suma data de acesta
                    SumaLicitatie sumaLicitata = c.plateste(pretMinim, suma);
                    //Daca este nula, inseamna ca actualul client nu mai are fonduri
                    //pentru a continua licitatia
                    if (sumaLicitata == null){
                        continue;
                    }
                    sumeLicitate.add(sumaLicitata);
                    break;
                }
            }
        }
        return sumeLicitate;
    }

    /***
     * Metoda prin care broker-ul ia un comision castigatorului, daca acesta se
     * afla in lista lui
     * @param sumaLicitata suma castigatoare
     * @return intoarce true daca a luat comision si false in caz contrar
     */
    public boolean comisionObtinut(SumaLicitatie sumaLicitata){
        double comisionTotal = 0;
        int idClient = sumaLicitata.getIdClient();
        if (clienti == null){
            return false;
        }
        for (Client c:clienti){
            //Daca s-a gasit clientul castigator, se va lua comisionul corespunator
            //in functie de tipul de persoana si de numarul de participari
            if (c.getId() == idClient) {
                if ((c instanceof PersoanaFizica) && (c.getNrParticipari() < 5)){
                    comisionTotal = sumaLicitata.getSuma() * 0.2;
                }
                if ((c instanceof PersoanaFizica) && (c.getNrParticipari() > 5)){
                    comisionTotal = sumaLicitata.getSuma() * 0.15;
                }
                if ((c instanceof PersoanaJuridica) && (c.getNrParticipari() < 25)){
                    comisionTotal = sumaLicitata.getSuma() * 0.25;
                }
                if ((c instanceof PersoanaJuridica) && (c.getNrParticipari() > 25)){
                    comisionTotal = sumaLicitata.getSuma() * 0.1;
                }
                this.notificaObservator(super.getNume() + " a obtinut un comision de "
                        + comisionTotal);
                return true;
            }
        }
        return false;
    }

    /***
     * Metoda ce informeaza client-ul aflat la licitatie
     * @param idClient id-ul clientului
     * @param mesaj mesajul trimis de casa de licitatii
     */
    public void cautaClientSiInformeaza(int idClient, String mesaj){
        if (clienti != null) {
            for (Client c : clienti) {
                if (c.getId() == idClient) {
                    notificaObservator(mesaj);
                    return;
                }
            }
        }
    }

    /***
     * Metoda ce anunta castigatorul licitatiei si care preia banii de la acesta
     * @param idClient client-ul castigator
     * @param produsCastigat produsul castigat
     * @param suma suma oferita
     */
    protected void anuntaCastigator(int idClient, Produs produsCastigat, double suma){
        if (clienti != null) {
            for (Client c : clienti) {
                if (c.getId() == idClient) {
                    //Se actualizeaza numarul de licitatii castigate
                    int nrLicitatiiCastigate = c.getNrLicitatiiCastigate();
                    nrLicitatiiCastigate++;
                    c.setNrLicitatiiCastigate(nrLicitatiiCastigate);
                    notificaObservator("Clientul " + c.getNume() +
                            " a castigat licitatia pentru produsul " + produsCastigat.getNume());
                    //Se actualizeaza si suma garantata pentru licitatii
                    double garantat = c.getSumaGarantata() - suma;
                    c.setSumaGarantata(garantat);
                    //Apoi se actualizeaza sold-ul persoanei
                    double sold = c.soldDisponibil() - suma;
                    c.setSold(sold);
                    break;
                }
            }
        }
    }

    /***
     * Metoda ce elimina sumele garantate de clientii necastigatori
     */
    protected void stergeMaxime() {
        if (clienti != null) {
            for (Client c : clienti) {
                c.cautaSiEliminaMaxim(idLicitatieCurenta);
            }
            idLicitatieCurenta = -1;
        }
    }

    /***
     * Metoda care verifica existenta unui client in lista de clienti a
     * broker-ului
     * @param idClient id-ul clientului cautat
     * @return 1 daca acesta exista si 0 altfel
     */
    protected int cautaClient(int idClient){
        if (clienti == null){
            return 0;
        }
        for (Client client:clienti){
            if (client.getId() == idClient){
                return 1;
            }
        }
        return 0;
    }
}
