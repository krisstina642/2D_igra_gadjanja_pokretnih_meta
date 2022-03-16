package Meni;

import Komponente.Dugme;
import Komponente.DugmeIkonica;
import MainDodatak.Akcije;
import MainDodatak.AkcijaStartMenija;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import Komponente.vBOX;


public class PodesavanjaMeni extends GridPane{
    double prethodna_jacina_muzike;
    double prethodna_jacina_efekata;

    public PodesavanjaMeni(GridPane pane, double sceneWIDTH, double HEIGHT, VBox povratniBox, Akcije a, int fullscrn){

        prethodna_jacina_muzike = a.getJacinaMuzike();
        prethodna_jacina_efekata = a.getJacinaEfekata();

        Text muzikaJacina = new Text(" MUZIKA ");
        Text efektiJacina = new Text(" EFEKTI ");
        Text fullScrnText = new Text(" CEO EKRAN ");

        Slider sliderMusic = new Slider(0,1, prethodna_jacina_muzike);
        Slider sliderEffects = new Slider(0,1, prethodna_jacina_efekata);

        Image imgMusicMute = new Image("Slike/iconMusicMute.png");
        Image imgMusicSound = new Image("Slike/iconMusicSound.png");
        DugmeIkonica musicButton = new DugmeIkonica(imgMusicSound, imgMusicMute,30);
        Image imgEffectsMute = new Image("Slike/iconEffectsMute.png");
        Image imgEffectsSound = new Image("Slike/iconEffectsSound.png");
        DugmeIkonica effectButton = new DugmeIkonica(imgEffectsSound, imgEffectsMute,30);
        Image imgCheckEmpty = new Image("Slike/empty_check.png");
        Image imgCheck = new Image("Slike/check.png");
        DugmeIkonica checkFullScrn = new DugmeIkonica( a.getFullScreen()?imgCheck:imgCheckEmpty,a.getFullScreen()?imgCheckEmpty:imgCheck,20);
        if (prethodna_jacina_muzike==0) {prethodna_jacina_muzike=1; musicButton.setOff();}
        if (prethodna_jacina_efekata==0) {prethodna_jacina_efekata=1; effectButton.setOff();}
        if (fullscrn==1) checkFullScrn.setOff();

        sliderEffects.setOnMouseClicked(event -> {
            a.setJacinaEfekata(sliderEffects.getValue());
            prethodna_jacina_efekata = sliderEffects.getValue();
            if (sliderEffects.getValue()==0) effectButton.setOff();
            else effectButton.setOn();
        });
        sliderMusic.setOnMouseClicked(event -> {
            a.setJacinaMuzike(sliderMusic.getValue());
            prethodna_jacina_muzike =sliderMusic.getValue();
            if (sliderMusic.getValue()==0) musicButton.setOff();
            else musicButton.setOn();
        });
        sliderMusic.setOnMouseDragged(event -> {
            a.setJacinaMuzike(sliderMusic.getValue());
            prethodna_jacina_muzike = sliderMusic.getValue();
            if (sliderMusic.getValue()==0) musicButton.setOff();
            else musicButton.setOn();
        });

        musicButton.setOnMousePressed(event -> {
            if (sliderMusic.getValue()>0) {
                sliderMusic.setValue(0);
            }
            else sliderMusic.setValue(prethodna_jacina_muzike);
            a.setJacinaMuzike(sliderMusic.getValue());
        });

        effectButton.setOnMousePressed(event -> {
            if (sliderEffects.getValue()>0) sliderEffects.setValue(0);
            else sliderEffects.setValue(prethodna_jacina_efekata);
            a.setJacinaEfekata(sliderEffects.getValue());
        });

        checkFullScrn.setOnMouseReleased(event -> a.setFullScreen());

        HBox muzika = new HBox(10, musicButton.getDugmeSaPostoljem(), sliderMusic);
        muzika.setAlignment(Pos.CENTER);
        HBox efekti = new HBox(10, effectButton.getDugmeSaPostoljem(), sliderEffects);
        efekti.setAlignment(Pos.CENTER);

        HBox fullscreen = new HBox(10,fullScrnText, checkFullScrn.getDugmeSaPostoljem());
        fullscreen.setAlignment(Pos.CENTER);
        
        Dugme zatvoriButton = new Dugme( "ZATVORI");
        zatvoriButton.setOnMouseClicked(event -> {
            pane.getChildren().remove(this);
            pane.add(povratniBox,0,0);
        });
        Dugme bezPreprekaButton = new Dugme(" BEZ PREPREKA ");
        bezPreprekaButton.setOnMouseClicked(event -> a.odabirPocetnogMenija(AkcijaStartMenija.PODESAVANJA_BEZ_PREPREKA));

        Dugme saPreprekamaButton = new Dugme(" SA PREPREKAMA ");
        saPreprekamaButton.setOnMouseClicked(event -> a.odabirPocetnogMenija(AkcijaStartMenija.PODESAVANJA_SA_PREPREKAMA));

        HBox buttons34 =new HBox(bezPreprekaButton.getDugmeSaPostoljem(), saPreprekamaButton.getDugmeSaPostoljem());

        vBOX vBox=new vBOX(10, muzikaJacina, muzika, efektiJacina, efekti, fullscreen, buttons34, zatvoriButton.getDugmeSaPostoljem());
        vBox.setMinWidth(250);
        vBox.setMaxWidth(400);
        
        this.setPrefWidth(sceneWIDTH);
        this.setPrefHeight(HEIGHT);
        this.setAlignment(Pos.CENTER);
        this.add(vBox,0,0);
    }
}
