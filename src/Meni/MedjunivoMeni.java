package Meni;

import Komponente.Dugme;
import MainDodatak.Akcije;
import MainDodatak.AkcijaMedjunivoa;
import MainDodatak.AkcijaPauze;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import Komponente.Brojac;
import PomocneKlase.StatusIgre;
import Komponente.vBOX;

public class MedjunivoMeni {

    public static GridPane dodajMeni(Akcije a, double sceneWIDTH, double HEIGHT, Rectangle POZADINA, int BROJ_POGODAKA_NIVO, int BROJ_META_NIVO_NEPROMENJIVI,
                                     boolean kraj_sledeci, int UKUPAN_BROJ_POGODAKA, int UKUPAN_BROJ_META, StatusIgre statusIgre, Brojac BODOVI_UKUPNO, double BODOVI_NIVO, double MAX_BONUS){

        FadeTransition ft =new FadeTransition(Duration.seconds(1),POZADINA);
        ft.setToValue(0.4);
        ft.play();

        Text textBrojPogodakaNivo=new Text( "BROJ POGODAKA NA NIVOU: "+BROJ_POGODAKA_NIVO+"/"+BROJ_META_NIVO_NEPROMENJIVI);

        Dugme button1=new Dugme( kraj_sledeci?"POČNI IZ POČETKA" : "SLEDEĆI NIVO",100);
        button1.setOnMouseClicked(event -> {
            ft.stop();
            a.odabirMedjunivoa(AkcijaMedjunivoa.SLEDECI_NIVO);
        });

        Dugme button2 = new Dugme( !kraj_sledeci?"PONOVI":"GLAVNI MENI",100);
        button2.setOnMouseClicked(event -> {
            ft.stop();
            if (kraj_sledeci) a.odabirPauze(AkcijaPauze.POCETNI_MENI,null);
            else a.odabirMedjunivoa(AkcijaMedjunivoa.PONOVO);
        });

        Text brojPogodaka=new Text( "UKUPAN BROJ POGODAKA: "+UKUPAN_BROJ_POGODAKA+"/"+UKUPAN_BROJ_META);

        Text poruka=new Text(statusIgre==StatusIgre.USPESNO_UNISTENO? "UNIŠTILI STE SVE METE":
                (statusIgre==StatusIgre.POTROSENA_MUNICIJA? "POTROŠENA MUNICIJA":
                                                            "NESTALE METE"));
        Text bodovi_moguci=new Text("BODOVI NA NIVOU: "+BODOVI_NIVO+"/"+(BROJ_META_NIVO_NEPROMENJIVI*150+MAX_BONUS));
        Text ukupanBrojBodova=new Text("IMATE UKUPNO: "+BODOVI_UKUPNO.getBroj()+ " BODOVA");


        vBOX rezultati=new vBOX(20, poruka, textBrojPogodakaNivo, brojPogodaka, bodovi_moguci, ukupanBrojBodova, button1.getDugmeSaPostoljem(), button2.getDugmeSaPostoljem());

        GridPane gp=new GridPane();
        gp.setPrefWidth(sceneWIDTH);
        gp.setPrefHeight(HEIGHT);
        gp.setAlignment(Pos.CENTER);
        gp.add(rezultati,0,0);
        return gp;
    }
}
