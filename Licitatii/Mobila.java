package Licitatii;

public class Mobila extends Produs{
    private String tip, material;

    public void setTip(String tip) {
        this.tip = tip;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    //Metoda intoarce datele despre mobila sub forma unui sir
    public String toString() {
        return "Mobila " + tip + " din " + material + "\n" + super.toString();
    }

}
