import Casa.CasaLicitatii;
import Casa.CasaThread;
import Persoana.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Se utilizeaza un scanner pentru citirea celor 4 fisiere de intrare
        Scanner scanner = new Scanner(System.in);
        String numeFisierCasa = scanner.nextLine();
        String numeFisierAdmin = scanner.nextLine();
        String numeFisierClienti = scanner.nextLine();
        String numeFisierComenzi = scanner.nextLine();
        //Se creaza doua thread-uri: unul pentru incarcarea casei de licitatii cu angajatii si pentru
        //comenzile date de administrator(i) si altul pentru incarcarea clientilor si a comenzilor
        //date de acestia
        ClientiThread clientiThread = new ClientiThread(CasaLicitatii.getINSTANTA(),
                numeFisierComenzi, numeFisierClienti);
        Thread t = new Thread(new CasaThread(CasaLicitatii.getINSTANTA(),
                numeFisierAdmin, numeFisierCasa));
        Thread t2 = new Thread(clientiThread);
        t.start();
        t2.start();
    }
}
