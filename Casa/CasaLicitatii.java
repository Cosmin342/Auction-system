package Casa;

import Persoana.*;
import Licitatii.*;
import java.io.*;
import java.util.*;

public class CasaLicitatii implements PrelucratorLicitatii{
    private ArrayList<Produs> produse;
    private ArrayList<Client> clienti;
    private ArrayList<Licitatie> licitatiiActive;
    private ArrayList<Broker> brokeri;
    private ArrayList<Administrator> administratori;

    //INSTANTA reprezinta instanta unica a casei
    private static CasaLicitatii INSTANTA;

    //Constructor fara parametri ce initializeaza casa
    public CasaLicitatii() {
    }

    /***
     * Metoda ce intoarce instanta unica a casei de licitatii
     * @return intoarce casa de licitatii
     */
    public static CasaLicitatii getINSTANTA() {
        if (INSTANTA == null){
            INSTANTA = new CasaLicitatii();
        }
        return INSTANTA;
    }

    /***
     * Metoda utilizata de administrator pentru adugarea de noi produse
     * @param produs un produs ce va fi adaugat in casa de licitatii
     */
    protected void adaugaProdus(Produs produs){
        produse.add(produs);
    }

    //Metoda utilizata pentru citirea angajatilor utilizand scanner
    private void citesteAngajati(Scanner cititor){
        while (cititor.hasNextLine()){
            String date = cititor.nextLine();
            if (date.equals("Produse")){
                break;
            }
            //Se creeaza angajatul si se adauga in lista corespunzatoare tipului sau
            Angajat angajat = ProcesorDate.creeazaAngajat(date);
            if (angajat instanceof Administrator){
                if (administratori == null){
                    administratori = new ArrayList<>();
                }
                administratori.add((Administrator) angajat);
            }
            if (angajat instanceof Broker){
                if (brokeri == null){
                    brokeri = new ArrayList<>();
                }
                brokeri.add((Broker) angajat);
            }
        }
    }

    /***
     * Metoda care citeste datele despre casa de licitatii din fisier
     * @param numeFisier numele fisierului din care se face citirea
     */
    public void citesteDate(String numeFisier){
        try {
            Scanner cititor = new Scanner(new File(numeFisier));
            //Intai sunt incarcati angajatii in sistem
            citesteAngajati(cititor);
            //Se va folosi un produsFactory pentru crearea produselor
            ProdusFactory produsFactory = new ProdusFactory();
            if (produse == null){
                produse = new ArrayList<>();
            }
            while (cititor.hasNextLine()){
                String produs = cititor.nextLine();
                Produs prod = produsFactory.creeazaProdus(produs, "|");
                produse.add(prod);
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    /***
     * Metoda ce permite afisarea produselor din casa
     */
    public void afisare(){
        Prezentator.afisare(produse);
    }

    /***
     * Metoda ce permite broker-ului sa elimine un produs vandut
     * @param produs produsul eliminat din casa de licitatii
     */
    protected void stergeProdus(Produs produs) {
        produse.remove(produs);
    }

    //Metoda ce adauga un client in casa de licitatii
    private void adaugaClient(Client client){
        int verificare = 0;
        if (clienti == null){
            verificare = 1;
            clienti = new ArrayList<>();
        }
        if (verificare == 0){
            for (Client c:clienti){
                if (c.equals(client)){
                    return;
                }
            }
        }
        clienti.add(client);
    }

    //Metoda ce creeaza o licitatie pentru un produs
    private int creeazaLicitatie(int idProdus){
        int idLicitatie = 1;
        //Daca nu exista alte licitatii, se initializeaza arraylist-ul
        //si se introduce noua licitatie
        if (licitatiiActive == null){
            licitatiiActive = new ArrayList<>();
            licitatiiActive.add(new Licitatie(idLicitatie,idProdus));
        }
        else {
            //Altfel, se verifica daca exista deja o licitatie pentru produs
            int gasit = 0;
            for (Licitatie licitatie : licitatiiActive) {
                //Daca exista, se incrementeaza numarul de participanti la aceasta
                if (licitatie.getIdProdus() == idProdus){
                    int nrParticipanti = licitatie.getNrParticipanti();
                    nrParticipanti++;
                    licitatie.setNrParticipanti(nrParticipanti);
                    idLicitatie = licitatie.getId();
                    gasit = 1;
                    break;
                }
            }
            //Altfel, se adauga o noua licitatie
            if (gasit == 0) {
                idLicitatie = licitatiiActive.size() + 1;
                licitatiiActive.add(new Licitatie(idLicitatie, idProdus));
            }
        }
        return idLicitatie;
    }

    /***
     * Metoda ce inregistreaza un client pentru o licitatie
     * @param client clientul de inregistrat
     * @param idProdus id-ul produsului dorit
     * @return intoarce id-ul licitatiei
     */
    public int inregistreazaClient(Client client, int idProdus) {
        //Se creeaza o licitatie pentru produs daca nu exista
        int idLicitatie = creeazaLicitatie(idProdus);
        //Se cauta broker-ul clientului
        int indexBroker = existaClient(client);
        //Daca nu are broker, i se asociaza unul random
        if (indexBroker == -1) {
            Random random = new Random();
            indexBroker = random.nextInt(brokeri.size() - 1);
            brokeri.get(indexBroker).adaugaClient(client);
        }
        //I se asociaza broker-ului un obsevator, iar acesta afiseaza faptul
        //ca actualul client s-a inregistrat la licitatie
        for (Produs p : produse) {
            if (p.getId() == idProdus) {
                Observator observator = new Observator(brokeri.get(indexBroker));
                brokeri.get(indexBroker).notificaObservator("Clientul " +
                        client.getNume() + " liciteaza pentru produsul " + p.getNume());
            }
        }
        adaugaClient(client);
        return idLicitatie;
    }

    //Metoda ce intoarce indexul broker-ului unui client
    private int existaClient(Client client) {
        if (clienti == null){
            return -1;
        }
        for (Client c : clienti){
            if (c.getNume().equals(client.getNume())){
                for (Broker broker : brokeri){
                    if (broker.cautaClient(c.getId()) == 1){
                        return brokeri.indexOf(broker);
                    }
                }
            }
        }
        return -1;
    }

    /***
     * Metoda ce porneste licitatia pentru un produs si in care
     * se executa mecanismul propriu zis de licitare
     * @param idProdus id-ul produsului licitat
     */
    public void pornestelicitatia(int idProdus) {
        //Se cauta licitatia pentru produsul dorit
        Licitatie licitatie = cautaLicitatie(idProdus);
        Produs produsLicitat = null;
        //Brokerii anunta clientii de inceperea licitatiei
        for (Broker b:brokeri){
            b.ataseazaLicitatia(licitatie.getId(), licitatie.getIdProdus());
        }
        //Se cauta produsul licitat
        for (Produs p:produse){
            if (p.getId() == licitatie.getIdProdus()){
                produsLicitat = p;
                break;
            }
        }
        double pretMinim = 0;
        SumaLicitatie suma = null;
        //Se executa pasii din licitatie
        for (int i = 0; i < licitatie.getNrPasiMaxim(); i++){
            //Se calculeaza maximul la fiecare pas
            suma = calculeazaMaxim(pretMinim);
            pretMinim = suma.getSuma();
            int pas = i + 1;
            //Brokerii anunta clientii care este suma maxima la pasul curent si cine a oferit-o
            for (Broker b:brokeri){
                b.cautaClientSiInformeaza(suma.getIdClient(),
                        "Clientul cu id-ul " + suma.getIdClient() +
                                " a oferit suma maxima de " + suma.getSuma() + " la pasul " + pas);
            }
        }
        //La terminarea licitatiei se anunta castigatorul si se sterg produsul, licitatia si
        //sumele garantate de clienti
        terminaLicitatia(suma, produsLicitat, licitatie);
    }

    //Metoda ce calculeaza maximul la un pas al licitatiei
    private SumaLicitatie calculeazaMaxim(double pretMinim){
        SumaLicitatie suma = null;
        //sumeLaAcestPas retine toate sumele primite la pasul curent
        ArrayList<SumaLicitatie> sumeLaAcestPas = new ArrayList<>();
        for (Broker b:brokeri){
            //Se preiau sumele de la brokeri si se adauga in arraylist-ul principal
            ArrayList<SumaLicitatie> sumeBroker = b.preiaBanii(pretMinim);
            sumeLaAcestPas.addAll(sumeBroker);
        }
        double maxim = 0;
        //Se determina suma maxima oferita si se returneaza
        for (SumaLicitatie sumaLicitatie : sumeLaAcestPas){
            if (sumaLicitatie.getSuma() > maxim){
                maxim = sumaLicitatie.getSuma();
                suma = sumaLicitatie;
            }
        }
        return suma;
    }

    //Metoda ce anunta catigatorul unei licitatii si elimina produsul din casa si actualizeaza
    //datele clientilor
    private void terminaLicitatia(SumaLicitatie suma, Produs produsLicitat, Licitatie licitatie){
        //Daca s-a atins suma minima de vanzare, produsul este vandut
        if (suma.getSuma() >= produsLicitat.getPretMinim()){
            Broker broker = null;
            produsLicitat.setPretVanzare(suma.getSuma());
            //Se cauta broker-ul care are asociat clientul castigator si il anunta
            for (Broker b:brokeri){
                b.anuntaCastigator(suma.getIdClient(), produsLicitat, suma.getSuma());
                if (b.comisionObtinut(suma)){
                    broker = b;
                    break;
                }
            }
            //Apoi broker-ul va scoate din casa de licitatii produsul vandut
            broker.eliminaProdus(produsLicitat, this);
        }
        else {
            //Altfel este anuntat faptul ca produsul nu a fost vandut
            for (Broker b:brokeri) {
                b.notificaObservator("Produsul " + produsLicitat.getNume() + " cu id-ul " +
                        produsLicitat.getId() + " nu a putut fi vandut");
            }
        }
        //Fiecare broker va elimina apoi sumele garantate de clienti pentru licitatie
        for (Broker broker : brokeri){
            broker.stergeMaxime();
        }
        //In final, este stearsa licitatia curenta
        licitatiiActive.remove(licitatie);
    }

    //Metoda ce intoarce licitatia pentru produsul cu id-ul dat
    private Licitatie cautaLicitatie(int idProdus) {
        for(Licitatie licitatie:licitatiiActive){
            if (licitatie.getIdProdus() == idProdus){
                return licitatie;
            }
        }
        return null;
    }

    /***
     * Metoda ce cauta un administrator in baza de date a casei de licitatii
     * @param comanda comanda de autentificare din care se va extrage
     *                numele administratorului
     * @return administratorul, daca acesta exista
     */
    public Administrator cautaAdministrator(String comanda){
        String nume = ProcesorDate.extrageNumeAdministrator(comanda);
        for (Administrator administrator:administratori){
            if (administrator.getNume().equals(nume)){
                return administrator;
            }
        }
        return null;
    }

    /***
     * Metoda ce cauta un produs si inscrie clientul la licitatia pentru produsul
     * respectiv
     * @param client clientul ce liciteaza pentru produs
     * @param comanda comanda din care va fi extras numele produsului
     * @param nrClienti numarul de clienti inregistrati
     */
    public void cautaSiInscrieLaLicitatie(Client client, String comanda, int nrClienti) {
        StringTokenizer tokenizer = new StringTokenizer(comanda);
        tokenizer.nextToken();
        //Se extrage numele produsului utilizand StringBuilder
        StringBuilder numeProdus = new StringBuilder();
        while (true){
            String parteNume = tokenizer.nextToken();
            if (parteNume.endsWith("\"")){
                numeProdus.append(parteNume);
                numeProdus = new StringBuilder(numeProdus.substring(1, numeProdus.length() - 1));
                break;
            }
            numeProdus.append(parteNume).append(" ");
        }
        //maxim reprezinta maximul pe care il poate oferi clientul pentru produs
        double maxim = Double.parseDouble(tokenizer.nextToken());
        int idProdus = -1;
        //Se cauta produsul dorit
        for (Produs produs:produse){
            if (produs.getNume().equals(numeProdus.toString())){
                idProdus = produs.getId();
                break;
            }
        }
        if (idProdus == -1){
            System.out.println("Produsul nu exista in casa de licitatii");
            return;
        }
        //Clientul este inscris la licitatie, iar daca numarul de clienti inregistrati
        //pentru licitatie este egal cu jumatate din clienti, licitatia va porni
        client.inscriereLaLicitatie(idProdus, maxim, this);
        if (licitatiiActive == null){
            return;
        }
        for (Licitatie licitatie:licitatiiActive){
            if (licitatie.getIdProdus() == idProdus){
                if (licitatie.getNrParticipanti() == nrClienti / 2){
                    pornestelicitatia(idProdus);
                    return;
                }
            }
        }
    }
}
