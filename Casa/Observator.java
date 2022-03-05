package Casa;

public class Observator {
    //Observatorul va avea un broker asociat care il va informa
    Broker broker;

    //Constructorul initializeaza brokerul si ii ataseaza observatorul curent
    public Observator(Broker broker){
        this.broker = broker;
        this.broker.ataseazaObservator(this);
    }

    /***
     * Metoda ce afiseaza notificarea primita de la broker
     * @param notificare mesajul de afisat
     */
    protected void afiseazaNotificare(String notificare){
        System.out.println(notificare);
    }
}
