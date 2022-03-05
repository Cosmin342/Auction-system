package Licitatii;

import Casa.*;
import Persoana.*;

import java.io.File;
import java.util.*;

public class ProcesorDate {
    /***
     * Metoda ce va citit dintr-un fisier datele despre clienti si va
     * intoarce o lista
     * @param numeFisier numele fisierului ce contine datele
     * @return intoarce o lista ce contine clientii
     */
    public static ArrayList<Client> incarcareClienti(String numeFisier){
        ArrayList<Client> clienti = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(numeFisier));
            //i va reprezenta id-ul clientului
            int i = 0;
            while (scanner.hasNextLine()){
                //Se creeaza o noua persoana cu id-ul i si pe baza liniei curente din fisier
                Client persoana = creeazaPersoana(i, scanner.nextLine());
                clienti.add(persoana);
                i++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return clienti;
    }

    /***
     * Metoda ce creeaza un client pe baza datelor primite ca parametru
     * @param id id-ul noului client
     * @param client sirul din care se vor extrage datele
     * @return intoarce clientul creat
     */
    public static Client creeazaPersoana(int id, String client){
        StringTokenizer tokenizer = new StringTokenizer(client, "|");
        //Se extrag pe rand tipul, numele si adresa, iar apoi, in functie de tip,
        //date suplimentare
        String tip = tokenizer.nextToken();
        String nume = tokenizer.nextToken();
        String adresa = tokenizer.nextToken();
        if (tip.equals("Persoana Juridica")) {
            double sold = Double.parseDouble(tokenizer.nextToken());
            String tipCompanie = tokenizer.nextToken();
            double capital = Double.parseDouble(tokenizer.nextToken());
            TipCompanie tipC = TipCompanie.SA;
            if (tipCompanie.equals("SRL")) {
                tipC = TipCompanie.SRL;
            }
            return new PersoanaJuridica(id, nume, adresa, tipC, sold, capital);
        }
        String data = tokenizer.nextToken();
        double sold = Double.parseDouble(tokenizer.nextToken());
        return new PersoanaFizica(id, nume, adresa, data, sold);
    }

    /***
     * Metoda ce extrage tipul unei comenzi
     * @param comanda comanda caruia i se doreste tipul
     * @return un string reprezentand tipul
     */
    public static String extrageTipComanda(String comanda){
        StringTokenizer tokenizer = new StringTokenizer(comanda);
        return tokenizer.nextToken();
    }

    /***
     * Metoda ce extrage numele administratorului dintr-o comanda
     * @param comanda comanda data
     * @return numele adminsitratorului
     */
    public static String extrageNumeAdministrator(String comanda){
        StringTokenizer tokenizer = new StringTokenizer(comanda);
        tokenizer.nextToken();
        return tokenizer.nextToken();
    }

    /***
     * Metoda ce extrage tipul unui produs
     * @param produs un string reprezentand datele despre produs
     * @return tipul produsului
     */
    public static String extrageTip(String produs){
        StringTokenizer tokenizer = new StringTokenizer(produs, "|");
        return tokenizer.nextToken();
    }

    /***
     * Metoda ce creeaza un tablou pe baza unui sir ce reprezinta datele
     * despre acesta
     * @param tablou un string reprezentand datele despre tablou
     * @param delim reprezinta sirul ce separa datele despre tablou
     * @return intoarce tabloul
     */
    public static Tablou creeazaTablou(String tablou, String delim){
        StringTokenizer tokenizer = new StringTokenizer(tablou, delim);
        tokenizer.nextToken();
        int id = Integer.parseInt(tokenizer.nextToken());
        String nume = tokenizer.nextToken();
        int an = Integer.parseInt(tokenizer.nextToken());
        double pretMinim = Double.parseDouble(tokenizer.nextToken());
        String numePictor = tokenizer.nextToken();
        String culoare = tokenizer.nextToken();
        Culoare tipCuloare = Culoare.ACRILIC;
        if (culoare.equals("ulei")){
            tipCuloare = Culoare.ULEI;
        }
        if (culoare.equals("tempera")){
            tipCuloare = Culoare.TEMPERA;
        }
        return new TablouBuilder()
                .withAn(an)
                .withCuloare(tipCuloare)
                .withId(id)
                .withNume(nume)
                .withNumePictor(numePictor)
                .withPretMinim(pretMinim)
                .build();
    }

    /***
     * Metoda ce creeaza mobila pe baza unui sir ce reprezinta datele
     * despre aceasta
     * @param mobila un string reprezentand datele despre mobila
     * @param delim reprezinta sirul ce separa datele despre mobila
     * @return intoarce mobila
     */
    public static Mobila creeazaMobila(String mobila, String delim){
        StringTokenizer tokenizer = new StringTokenizer(mobila, delim);
        tokenizer.nextToken();
        int id = Integer.parseInt(tokenizer.nextToken());
        String nume = tokenizer.nextToken();
        int an = Integer.parseInt(tokenizer.nextToken());
        double pretMinim = Double.parseDouble(tokenizer.nextToken());
        String tip = tokenizer.nextToken();
        String material = tokenizer.nextToken();
        return new MobilaBuilder()
                .withAn(an)
                .withTip(tip)
                .withId(id)
                .withNume(nume)
                .withMaterial(material)
                .withPretMinim(pretMinim)
                .build();
    }

    /***
     * Metoda ce creeaza o bijuterie pe baza unui sir ce reprezinta datele
     * despre aceasta
     * @param bijuterie un string reprezentand datele despre bijuterie
     * @param delim reprezinta sirul ce separa datele despre bijuterie
     * @return intoarce bijuteria
     */
    public static Bijuterie creeazaBijuterie(String bijuterie, String delim){
        StringTokenizer tokenizer = new StringTokenizer(bijuterie, delim);
        tokenizer.nextToken();
        int id = Integer.parseInt(tokenizer.nextToken());
        String nume = tokenizer.nextToken();
        int an = Integer.parseInt(tokenizer.nextToken());
        double pretMinim = Double.parseDouble(tokenizer.nextToken());
        String material = tokenizer.nextToken();
        String piatraPretioasa = tokenizer.nextToken();
        boolean piatra = false;
        if (piatraPretioasa.equals("da")){
            piatra = true;
        }
        return new BijuterieBuilder()
                .withAn(an)
                .withPiatraPretioasa(piatra)
                .withId(id)
                .withNume(nume)
                .withMaterial(material)
                .withPretMinim(pretMinim)
                .build();
    }


    /***
     * Metoda ce creeaza un angajat pentru casa de licitatii pe baza
     * unui sir de caractere
     * @param angajat datele despre angajat
     * @return intoarce angajatul creat
     */
    public static Angajat creeazaAngajat(String angajat){
        StringTokenizer tokenizer = new StringTokenizer(angajat, "|");
        String tip = tokenizer.nextToken();
        int id = Integer.parseInt(tokenizer.nextToken());
        String nume = tokenizer.nextToken();
        //Daca tipul e administrator, se creeaza un administrator
        if (tip.equals("Administrator")){
            return new Administrator(nume, id, tip);
        }
        //Altfel, se creeaza un broker
        return new Broker(nume, id, tip);
    }

    /***
     * Metoda ce creeaza un produs pe baza datelor dintr-o comanda
     * @param comanda comanda data pentru creearea produsului
     * @return produsul creat
     */
    public static Produs creeazaProdus(String comanda) {
        StringTokenizer tokenizer = new StringTokenizer(comanda);
        tokenizer.nextToken();
        String tip = tokenizer.nextToken();
        int id = Integer.parseInt(tokenizer.nextToken());
        int an = Integer.parseInt(tokenizer.nextToken());
        double pretMinim = Double.parseDouble(tokenizer.nextToken());
        //In functie de tip, se creeaza un tablou, mobila sau o bijuterie
        if (tip.equals("tablou")) {
           return creeazaTablou(id, an, pretMinim, tokenizer);
        }
        if (tip.equals("bijuterie")){
            return creeazaBijuterie(id, an, pretMinim, tokenizer);
        }
        return creeazaMobila(id, an, pretMinim, tokenizer);
    }

    //Metoda ce creeaza mobila pe baza datelor date ca parametru si a unui
    //StringTokenizer pentru extragerea de date suplimentare
    private static Mobila creeazaMobila(int id, int an, double pret, StringTokenizer tokenizer) {
        String tip = tokenizer.nextToken();
        String material = tokenizer.nextToken();
        //Pentru obtinerea numelui se va folosi un stringbuilder pentru
        //concatenarea partilor numelui
        StringBuilder nume = new StringBuilder();
        while (tokenizer.hasMoreTokens()){
            nume.append(tokenizer.nextToken()).append(" ");
        }
        nume = new StringBuilder(nume.substring(0, nume.length() - 1));
        return new MobilaBuilder()
                .withAn(an)
                .withTip(tip)
                .withId(id)
                .withNume(nume.toString())
                .withMaterial(material)
                .withPretMinim(pret)
                .build();
    }

    //Metoda ce creeaza o bijuterie pe baza datelor date ca parametru si a unui
    //StringTokenizer pentru extragerea de date suplimentare
    private static Bijuterie creeazaBijuterie(int id, int an, double pret, StringTokenizer tokenizer) {
        String material = tokenizer.nextToken();
        String piatra = tokenizer.nextToken();
        boolean piatraPretioasa = true;
        if (piatra.equals("nu")){
            piatraPretioasa = false;
        }
        StringBuilder nume = new StringBuilder();
        while (tokenizer.hasMoreTokens()){
            nume.append(tokenizer.nextToken()).append(" ");
        }
        nume = new StringBuilder(nume.substring(0, nume.length() - 1));
        return new BijuterieBuilder()
                .withAn(an)
                .withPiatraPretioasa(piatraPretioasa)
                .withId(id)
                .withNume(nume.toString())
                .withMaterial(material)
                .withPretMinim(pret)
                .build();
    }

    //Metoda ce creeaza un tablou pe baza datelor date ca parametru si a unui
    //StringTokenizer pentru extragerea de date suplimentare
    private static Tablou creeazaTablou(int id, int an, double pret, StringTokenizer tokenizer) {
        String culoare = tokenizer.nextToken();
        Culoare tipCuloare = Culoare.ACRILIC;
        if (culoare.equals("ulei")){
            tipCuloare = Culoare.ULEI;
        }
        if (culoare.equals("tempera")){
            tipCuloare = Culoare.TEMPERA;
        }
        StringBuilder autor = extrageNumeTablou(tokenizer);
        StringBuilder nume = new StringBuilder();
        while (tokenizer.hasMoreTokens()){
            String parte = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens()) {
                nume.append(parte);
                break;
            }
            nume.append(parte).append(" ");
        }
        return new TablouBuilder()
                .withAn(an)
                .withId(id)
                .withCuloare(tipCuloare)
                .withNume(nume.toString())
                .withPretMinim(pret)
                .withNumePictor(autor.toString())
                .build();
    }

    //Metoda ce extrage numele unui tablou utilizand un StringTokenizer
    private static StringBuilder extrageNumeTablou(StringTokenizer tokenizer) {
        StringBuilder autor = new StringBuilder();
        while (true) {
            String parte = tokenizer.nextToken();
            //Se vor extrage parti din nume pana cand se intalneste o parte care
            //se termina cu |
            if (parte.endsWith("|")){
                parte = parte.substring(0, parte.length() - 1);
                autor.append(parte);
                break;
            }
            autor.append(parte).append(" ");
        }
        return autor;
    }

    /***
     * Metoda ce permite creearea unui client pe baza unui sir de caractere
     * @param date sirul de caractere ce contine date despte client
     * @param id id-ul noului client
     * @return intoarce clientul creat
     */
    public static Client creeazaClient(String date, int id) {
        StringTokenizer tokenizer = new StringTokenizer(date);
        tokenizer.nextToken();
        String nume = tokenizer.nextToken();
        String tip = tokenizer.nextToken() + " " + tokenizer.nextToken();
        //Pentru adresa se va folosi un stringBuilder
        StringBuilder adresa = new StringBuilder();
        while (true){
            String parte = tokenizer.nextToken();
            //in momentul in care o parte a adresei se termina cu |, se
            //opreste while-ul
            if (parte.endsWith("|")){
                adresa.append(parte);
                break;
            }
            adresa.append(parte).append(" ");
        }
        //In functie de tip se extrag date suplimentare
        if (tip.equals("Persoana Fizica")){
            String dataNastere = tokenizer.nextToken();
            double sold = Double.parseDouble(tokenizer.nextToken());
            return new PersoanaFizica(id, nume, adresa.toString(), dataNastere, sold);
        }
        double sold = Double.parseDouble(tokenizer.nextToken());
        String tipC = tokenizer.nextToken();
        double capital = Double.parseDouble(tokenizer.nextToken());
        TipCompanie tipCompanie = TipCompanie.SA;
        if (tipC.equals("SRL")){
            tipCompanie = TipCompanie.SRL;
        }
        return new PersoanaJuridica(id, nume, adresa.toString(), tipCompanie, sold, capital);
    }
}
