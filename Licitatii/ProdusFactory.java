package Licitatii;

public class ProdusFactory {
    /***
     * Metoda ce creeaza un anumit fel de produs in funtie de tipul sau
     * @param produs sirul ce contine datele despre produs
     * @param delim delimitatorul ce desparte datele in sir
     * @return intoarce produsul
     */
    public Produs creeazaProdus(String produs, String delim){
        String tip = ProcesorDate.extrageTip(produs);
        switch (tip){
            case "Tablou":
                return ProcesorDate.creeazaTablou(produs, delim);
            case "Mobila":
                return ProcesorDate.creeazaMobila(produs, delim);
            case "Bijuterie":
                return ProcesorDate.creeazaBijuterie(produs, delim);
            default:
                return null;
        }
    }
}
