package Meni;

import Komponente.Dugme;
import MainDodatak.Akcije;
import MainDodatak.AkcijaPauze;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import PomocneKlase.StatusIgre;
import Komponente.vBOX;

public class PauzaMeni {

    public static GridPane dodajMeni(Akcije a, double sceneWIDTH, double HEIGHT, StatusIgre status){

    Dugme button1=new Dugme( "NASTAVI",100);
        button1.setOnMouseClicked(event -> {
            a.odabirPauze(AkcijaPauze.NASTAVI, status);
    });

    Dugme button2 = new Dugme( "PONOVI",100);
        button2.setOnMouseClicked(event -> {
            a.odabirPauze(AkcijaPauze.PONOVO, status);
        });

        Dugme button3=new Dugme( "POČETNI MENI",100);
        button3.setOnMouseClicked(event -> {
            a.odabirPauze(AkcijaPauze.POCETNI_MENI, status);
        });

        Dugme button4 = new Dugme( "KRAJ",100);
        button4.setOnMouseClicked(event -> {
            a.odabirPauze(AkcijaPauze.KRAJ, status);
        });

    vBOX rezultati=new vBOX(20,button1.getDugmeSaPostoljem(), button2.getDugmeSaPostoljem(), button3.getDugmeSaPostoljem(), button4.getDugmeSaPostoljem());

        rezultati.setMinWidth(200);

    GridPane gp=new GridPane();
       gp.setPrefWidth(sceneWIDTH);
       gp.setPrefHeight(HEIGHT);
       gp.setAlignment(Pos.CENTER);
       gp.add(rezultati,0,0);
        return gp;
    }
}
