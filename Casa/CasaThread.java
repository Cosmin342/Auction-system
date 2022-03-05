package Casa;

import Licitatii.*;

import java.io.File;
import java.util.Scanner;

public class CasaThread implements Runnable{
    CasaLicitatii casaLicitatii;
    private final String numeFisierComenzi;
    private final String numeFisierDate;

    //Constructor ce initializeaza campurile thread-ului
    public CasaThread(CasaLicitatii casaLicitatii, String numeFisier, String numeFisierDate) {
        this.casaLicitatii = casaLicitatii;
        this.numeFisierComenzi = numeFisier;
        this.numeFisierDate = numeFisierDate;
    }

    @Override
    /***
     * Metoda run va incarca datele despre casa de licitatii din fisier si va executa
     * comenzile date de un administrator in alt fisier
     */
    public void run() {
        casaLicitatii.citesteDate(numeFisierDate);
        try {
            //Se foloseste un scanner pentru citirea comenzilor
            Scanner scanner = new Scanner(new File(numeFisierComenzi));
            Administrator administrator = null;
            while (scanner.hasNextLine()){
                String comanda = scanner.nextLine();
                String tip = ProcesorDate.extrageTipComanda(comanda);
                switch (tip){
                    case "autentificare":
                        //Comanda de autentificare va cauta administratorul in sistem
                        administrator = casaLicitatii.cautaAdministrator(comanda);
                        break;
                    case "adaugaprodus":
                        //Comanda adaugaprodus va duce la adaugarea unui nou produs in casa
                        Produs produs = ProcesorDate.creeazaProdus(comanda);
                        administrator.adaugaProdus(produs, casaLicitatii);
                        break;
                    case "delogare":
                        //delogare va seta administratorul pe null
                        administrator = null;
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
}
