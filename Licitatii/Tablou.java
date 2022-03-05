package Licitatii;

public class Tablou extends Produs{
    private String numePictor;
    private Culoare culoare;

    public void setNumePictor(String numePictor) {
        this.numePictor = numePictor;
    }

    public void setCuloare(Culoare culoare) {
        this.culoare = culoare;
    }

    @Override
    //Metoda intoarce datele despre un tablou sub forma unui sir
    public String toString() {
        return "Tablou de " + numePictor + "\nCuloare: " + culoare + "\n" + super.toString();
    }
}
