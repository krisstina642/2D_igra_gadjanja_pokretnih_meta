package Komponente;

import javafx.scene.text.Text;

public class Brojac {
    private double Broj;
    private Text Broj_txt;
    private boolean isInt;
    private String prefiks;

    public Brojac(double Broj, String prefiks ){
        this.Broj=Broj;
        this.prefiks=prefiks;
        this.Broj_txt=new Text(0,0,prefiks+""+Broj);
        isInt=false;
    }

    public double getBroj() {
        return Broj;
    }

    public Text getText() {
        return Broj_txt;
    }

    public void setBroj(double broj) {
        Broj = broj;
       if (isInt) Broj_txt.setText(prefiks+""+(int)Broj);
       else Broj_txt.setText(prefiks+""+Broj);
    }

    public void inc(){
        setBroj(++Broj);
    }

    public void dec(){
        setBroj(--Broj);
    }

    public void add(double broj){
        setBroj(Broj+broj);
    }

    public void sub(double broj){
        setBroj(Broj-broj);
    }

    public void set_int() {
        isInt=true;
    }
    public void set_double() {
        isInt=false;
    }
}
