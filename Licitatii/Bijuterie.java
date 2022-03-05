package Licitatii;

public class Bijuterie extends Produs{
    private String material;
    private boolean piatraPretioasa;

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setPiatraPretioasa(boolean piatraPretioasa) {
        this.piatraPretioasa = piatraPretioasa;
    }

    @Override
    //Metoda intoarce datele despre o bijuterie sub forma unui sir
    public String toString() {
        if (piatraPretioasa) {
            return "Bijuterie din " + material + " cu piatra pretioasa " + "\n" + super.toString();
        }
        return "Bijuterie din " + material + " fara piatra pretioasa " + "\n" + super.toString();
    }
}
