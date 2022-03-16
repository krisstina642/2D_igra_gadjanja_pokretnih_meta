package MainDodatak;

import PomocneKlase.StatusIgre;

public interface Akcije {

    public void odabirPauze(AkcijaPauze p, StatusIgre status);
    public void odabirPocetnogMenija(AkcijaStartMenija s);
    public void odabirMedjunivoa(AkcijaMedjunivoa p);
    public void setJacinaMuzike(double value);
    public void setJacinaEfekata(double value);
    public double getJacinaMuzike();
    public double getJacinaEfekata();
    public void azurirajPogodjeneMete(int bodovi, boolean center);
    public void setFullScreen();
    public boolean getFullScreen();
}
