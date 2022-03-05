package Licitatii;

public class BijuterieBuilder {
    //bijuterie reprezinta bijuteria ce va fi construita
    private Bijuterie bijuterie = new Bijuterie();

    //Metodele urmatoare apeleaza setterii corespunzatori fiecarei variabile
    //din Bijuterie sau din Produs(pentru cele mostenite)
    public BijuterieBuilder withId(int id){
        bijuterie.setId(id);
        return this;
    }

    public BijuterieBuilder withAn(int an){
        bijuterie.setAn(an);
        return this;
    }

    public BijuterieBuilder withNume(String nume){
        bijuterie.setNume(nume);
        return this;
    }

    public BijuterieBuilder withPretMinim(double pretMinim){
        bijuterie.setPretMinim(pretMinim);
        return this;
    }

    public BijuterieBuilder withPretVanzare(double pretVanzare){
        bijuterie.setPretVanzare(pretVanzare);
        return this;
    }

    public BijuterieBuilder withPiatraPretioasa(boolean piatraPretioasa){
        bijuterie.setPiatraPretioasa(piatraPretioasa);
        return this;
    }

    public BijuterieBuilder withMaterial(String material){
        bijuterie.setMaterial(material);
        return this;
    }

    //Intoarce bijuteria construita
    public Bijuterie build(){
        return bijuterie;
    }
}
