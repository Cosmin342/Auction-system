package Persoana;

import Casa.CasaLicitatii;
import Licitatii.*;

import java.util.ArrayList;

public class Client {
    private final int id;
    private int nrParticipari = 0;
    private int nrLicitatiiCastigate = 0;
    private String nume;
    private final String adresa;
    private ArrayList<SumaLicitatie> maximeLicitatii;
    private double sumaGarantata = 0, sold;

    //Constructor ce initializeaza campurile clientului
    public Client(int id, String nume, String adresa, double sold) {
        this.id = id;
        this.nume = nume;
        this.adresa = adresa;
        this.sold = sold;
    }

    //Urmatoarele metode reprezinta getteri si setteri pentru campurile din Client
    public String getNume() {
        return nume;
    }

    public int getId() {
        return id;
    }

    public int getNrParticipari() {
        return nrParticipari;
    }

    public int getNrLicitatiiCastigate() {
        return nrLicitatiiCastigate;
    }

    public ArrayList<SumaLicitatie> getMaximeLicitatii() {
        return maximeLicitatii;
    }

    public void setNrLicitatiiCastigate(int nrLicitatiiCastigate) {
        this.nrLicitatiiCastigate = nrLicitatiiCastigate;
    }

    public void setSumaGarantata(double sumaGarantata) {
        this.sumaGarantata = sumaGarantata;
    }

    public double getSumaGarantata() {
        return sumaGarantata;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double soldDisponibil(){
        return this.sold;
    }

    public void setSold(double sold) {
        this.sold = sold;
    }

    /***
     * Metoda ce verifica daca actualul client mai are bani sa liciteze
     * @param sumaMaxima suma pe care clientul ar oferi-o la
     *                   urmatoarea licitatie
     * @return true sau false on functie de rezultat
     */
    public boolean verificaSoldSiPret(double sumaMaxima) {
        return !(this.soldDisponibil() - this.getSumaGarantata() < sumaMaxima);
    }

    /***
     * Metoda prin care clientul curent este inscris la licitatie
     * @param idProdus id-ul produsului dorit
     * @param sumaMaxima suma maxima pe care clientul ar oferi-o
     */
    public void inscriereLaLicitatie(int idProdus, double sumaMaxima, CasaLicitatii casaLicitatii){
        //Se verifica intai sold-ul clientului
        if (!this.verificaSoldSiPret(sumaMaxima)){
            System.out.println("Clientul " + this.nume +
                    " nu are fonduri suficiente pentru a licita");
            return;
        }
        //Se inregistreaza clientul la licitatie si se obtine id-ul licitatiei
        int idLicitatie = casaLicitatii.inregistreazaClient(this, idProdus);
        if (maximeLicitatii == null){
            maximeLicitatii = new ArrayList<>();
        }
        //Apoi se adauha in arraylist-ul de maxime, suma garantata pentru licitatie
        SumaLicitatie sumaLicitatie = new SumaLicitatie(idLicitatie, sumaMaxima, this.id);
        maximeLicitatii.add(sumaLicitatie);
        this.nrParticipari++;
        this.sumaGarantata += sumaMaxima;
    }

    @Override
    //Metoda prin care se verifica daca doi clienti sunt egali
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && nrParticipari == client.nrParticipari
                && nrLicitatiiCastigate == client.nrLicitatiiCastigate &&
                nume.equals(client.nume) && adresa.equals(client.adresa);
    }

    @Override
    //Metoda ce returneaza datele despre client
    public String toString() {
        return id + " " + nume + " " + adresa +
                " licit castigate " + nrLicitatiiCastigate + " particip " + nrParticipari;
    }

    /***
     * Metoda ce returneaza suma platita de un client la o licitatie la un pas
     * @param pretMinim pretul de la care pleaca pasul curent din licitatie
     * @param sumaLicitatie suma garantata de client
     * @return suma platita de client la pasul curent
     */
    public SumaLicitatie plateste(double pretMinim, SumaLicitatie sumaLicitatie) {
        //Daca pretul minim a depasit pretul garantat de client, acesta nu mai ofera ceva
        if (pretMinim > sumaLicitatie.getSuma()){
            return null;
        }
        //Altfel, clientul ofera pretul minim plus un procent random din diferenta
        //celor doua sume
        double procent = Math.random();
        double suma = pretMinim + procent * (sumaLicitatie.getSuma() - pretMinim);
        return new SumaLicitatie(sumaLicitatie.getIdLicitatie(),
                suma, this.id);
    }

    /***
     * Metoda ce va elimina suma garantata de un client pentru o licitatie, daca el
     * a castigat licitatia
     * @param idLicitatieCurenta id-ul licitatiei terminate
     */
    public void cautaSiEliminaMaxim(int idLicitatieCurenta) {
        for (SumaLicitatie sumaLicitatie:maximeLicitatii){
            if (sumaLicitatie.getIdLicitatie() == idLicitatieCurenta){
                sumaGarantata -= sumaLicitatie.getSuma();
                maximeLicitatii.remove(sumaLicitatie);
                break;
            }
        }
    }
}
