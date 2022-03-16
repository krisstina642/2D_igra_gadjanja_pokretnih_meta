package Meni;

import Komponente.Dugme;
import Komponente.PoljeRekordi;
import MainDodatak.Akcije;
import MainDodatak.AkcijaStartMenija;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import Komponente.vBOX;

import java.util.ArrayList;


public class Pobednik {
    static int maxLen = 25;
    public static GridPane otvoriPanel(Akcije a, double sceneWIDTH, double HEIGHT, int bodovi, int vreme, ArrayList<PoljeRekordi> rekordi ){
        Text ime=new Text(" UNESI IME ");

        Text bodoviText=new Text(" REZULTAT: "+(bodovi-10*vreme)+" // UKUPNO BODOVI-10 * VREME");

        TextField tf= new TextField();
        tf.setAlignment(Pos.CENTER);

        Dugme button2 = new Dugme( "OK");
        button2.setOnMouseClicked(event -> {
            boolean p= false;
            String s= tf.getText();
            if (s.length()>maxLen) s = s.substring(0,maxLen);
            if(s.length()==0) s="Anonymous";
            for (int i=0; i<10; i++){
                 if(rekordi.get(i).getBodovi()<=bodovi-10*vreme && !p) {
                     p = true;
                     rekordi.add( i, new PoljeRekordi(bodovi-10*vreme, s,200));
                 }
                 else if (p && rekordi.get(i).getIme().equals(s)){
                    p = false;
                    rekordi.remove(i);
                    break;
                 }
                 else if (!p && rekordi.get(i).getIme().equals(s)) break;
            }
            if (p) rekordi.remove(rekordi.get(rekordi.size()-1));
            a.odabirPocetnogMenija(AkcijaStartMenija.REZULTATI);
        });


        vBOX rezultati=new vBOX(10, ime, tf, button2.getDugmeSaPostoljem(), bodoviText);
        rezultati.setMinWidth(250);
        rezultati.setMaxWidth(400);

        GridPane gp=new GridPane();
        gp.setPrefWidth(sceneWIDTH);
        gp.setPrefHeight(HEIGHT);
        gp.setAlignment(Pos.CENTER);
        gp.add(rezultati,0,0);
        return gp;
    }
}
