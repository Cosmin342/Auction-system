package Licitatii;

public class MobilaBuilder {
    //mobila reprezinta mobila ce va fi construita
    private Mobila mobila = new Mobila();

    //Metodele urmatoare apeleaza setterii corespunzatori fiecarei variabile
    //din Mobila sau din Produs(pentru cele mostenite)
    public MobilaBuilder withId(int id){
        mobila.setId(id);
        return this;
    }

    public MobilaBuilder withAn(int an){
        mobila.setAn(an);
        return this;
    }

    public MobilaBuilder withNume(String nume){
        mobila.setNume(nume);
        return this;
    }

    public MobilaBuilder withPretMinim(double pretMinim){
        mobila.setPretMinim(pretMinim);
        return this;
    }

    public MobilaBuilder withPretVanzare(double pretVanzare){
        mobila.setPretVanzare(pretVanzare);
        return this;
    }

    public MobilaBuilder withTip(String tip){
        mobila.setTip(tip);
        return this;
    }

    public MobilaBuilder withMaterial(String material){
        mobila.setMaterial(material);
        return this;
    }

    //Intoarce mobila construita
    public Mobila build(){
        return mobila;
    }
}
