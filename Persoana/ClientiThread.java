package Persoana;

import Casa.CasaLicitatii;
import Licitatii.ProcesorDate;

import java.io.File;
import java.util.*;

public class ClientiThread implements Runnable{
    private ArrayList<Client> clienti;
    private CasaLicitatii casaLicitatii;
    private String numeFisierComenzi;
    private String numeFisierClienti;

    //Constructorul initializeaza campurile thread-ului, fara clienti
    public ClientiThread(CasaLicitatii casaLicitatii, String numeFisier, String numeFisierClienti) {
        this.casaLicitatii = casaLicitatii;
        this.numeFisierComenzi = numeFisier;
        this.numeFisierClienti = numeFisierClienti;
    }

    @Override
    /***
     * Metoda ce va incarca clientii si va executa comenzile date de acestia
     */
    public void run() {
        try {
            clienti = ProcesorDate.incarcareClienti(numeFisierClienti);
            //Se vor citi comenzile utilizand Scanner
            Scanner scanner = new Scanner(new File(numeFisierComenzi));
            //Prima data se va executa thread-ul ce incarca datele despre
            //casa de licitatii, iar pentru a nu corupe casa, thread-ul actual
            //va "dormi" 1s
            Thread.sleep(1000);
            Client client = null;
            while (scanner.hasNextLine()){
                String comanda = scanner.nextLine();
                String tip = ProcesorDate.extrageTipComanda(comanda);
                switch (tip){
                    case "autentificare":
                        //Comanda de autentificare va cauta clientul in arrayList
                        client = cautaClient(comanda, clienti);
                        break;
                    case "liciteazapentru":
                        //liciteazapentru va inscrie clientul la o licitatie pentru un produs
                        casaLicitatii.cautaSiInscrieLaLicitatie(client, comanda, clienti.size());
                        System.out.println();
                        break;
                    case "afiseazaproduse":
                        //Se afiseaza toate produsele disponibile
                        casaLicitatii.afisare();
                        System.out.println();
                        break;
                    case "delogare":
                        client = null;
                        break;
                    case "inregistrare":
                        //Comanda inregistrare va adauga un client nou
                        clienti.add(ProcesorDate.creeazaClient(comanda, clienti.size()));
                        break;
                    case "inchidere":
                        return;
                    default:
                        break;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //Metoda ce cauta un client in arrayList-ul de clienti dupa nume
    private Client cautaClient(String comanda, ArrayList<Client> clienti) {
        StringTokenizer tokenizer = new StringTokenizer(comanda);
        tokenizer.nextToken();
        String nume = tokenizer.nextToken();
        for (Client client : clienti){
            if (client.getNume().equals(nume)){
                return client;
            }
        }
        System.out.println("Clientul " + nume + " nu este inregistrat");
        return null;
    }
}
