package Casa;

import java.util.ArrayList;

public class Prezentator {
    //Metoda generica ce permite afisarea elementelor dintr-o lista
    public static<T> void afisare(ArrayList<T> lista){
        for (T t:lista){
            System.out.println(t);
            System.out.println();
        }
    }
}
