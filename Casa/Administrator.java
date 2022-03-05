package Casa;
import Licitatii.*;

public class Administrator extends Angajat{
    //Constructorul va initializa campurile mostenite de la angajat
    public Administrator(String nume, int id, String tip) {
        super(nume, id, tip);
    }

    /***
     * Metoda pentru adaugarea unui nou produs in casa de licitatii
     * @param produs produsul ce urmeaza sa fie adaugat
     */
    public void adaugaProdus(Produs produs, CasaLicitatii casaLicitatii){
        casaLicitatii.adaugaProdus(produs);
    }
}
