package Licitatii;

public class TablouBuilder {
    //tablou reprezinta tabloul ce va fi construit
    private Tablou tablou = new Tablou();

    //Metodele urmatoare apeleaza setterii corespunzatori fiecarei variabile
    //din Tablou sau din Produs(pentru cele mostenite)
    public TablouBuilder withId(int id){
        tablou.setId(id);
        return this;
    }

    public TablouBuilder withAn(int an){
        tablou.setAn(an);
        return this;
    }

    public TablouBuilder withNume(String nume){
        tablou.setNume(nume);
        return this;
    }

    public TablouBuilder withPretMinim(double pretMinim){
        tablou.setPretMinim(pretMinim);
        return this;
    }

    public TablouBuilder withPretVanzare(double pretVanzare){
        tablou.setPretVanzare(pretVanzare);
        return this;
    }

    public TablouBuilder withNumePictor(String numePictor){
        tablou.setNumePictor(numePictor);
        return this;
    }

    public TablouBuilder withCuloare(Culoare culoare){
        tablou.setCuloare(culoare);
        return this;
    }

    //Intoarce tabloul construit
    public Tablou build(){
        return tablou;
    }
}
