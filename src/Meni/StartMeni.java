package Meni;

import Komponente.Dugme;
import Komponente.PoljeRekordi;
import MainDodatak.Akcije;
import MainDodatak.AkcijaStartMenija;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class StartMeni extends  GridPane {

    PodesavanjaMeni settingsMeni;

    public StartMeni(Akcije a, double sceneWIDTH, double HEIGHT, ArrayList<PoljeRekordi> rekordi, int fullscrn){


        Dugme startButton=new Dugme( "POČNI",100);
        startButton.setOnMouseClicked(event -> {
            a.odabirPocetnogMenija(AkcijaStartMenija.POCNI);
        });

        Dugme editButton = new Dugme( "EDITOR STRELJANE",100);
        editButton.setOnMouseClicked(event -> {
            a.odabirPocetnogMenija(AkcijaStartMenija.EDITOR_STRELJANE);
        });

        Dugme endButton = new Dugme( "KRAJ",100);
        endButton.setOnMouseClicked(event -> {
            a.odabirPocetnogMenija(AkcijaStartMenija.KRAJ);
        });

        Dugme settingsButton=new Dugme( "PODEŠAVANJA",100);
        Dugme helpButton=new Dugme( "POMOĆ",100);
        Dugme infoButton = new Dugme( "INFORMACIJE",100);


        VBox vBox=new VBox(20,startButton.getDugmeSaPostoljem(), settingsButton.getDugmeSaPostoljem(), editButton.getDugmeSaPostoljem(), helpButton.getDugmeSaPostoljem(), infoButton.getDugmeSaPostoljem(), endButton.getDugmeSaPostoljem());
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(200);
        vBox.setOpacity(1);

        vBox.backgroundProperty().set(new Background(new BackgroundFill(Color.AZURE,new CornerRadii(5), new Insets(-20))));
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(5), null, new Insets(-20))));

        this.setPrefWidth(sceneWIDTH);
        this.setPrefHeight(HEIGHT);
        this.setAlignment(Pos.CENTER);
        this.add(vBox,0,0);

        Dugme resultButton = new Dugme( "REZULTATI",100);
        resultButton.setOnMouseClicked(event -> {
            this.getChildren().remove(vBox);
            this.add(new RekordiMeni(this, sceneWIDTH, HEIGHT, vBox, rekordi), 0, 0);
        });

        helpButton.setOnMouseClicked(event -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File(Paths.get("uputstvo.pdf").toString());
                    Desktop.getDesktop().open(myFile);
                } catch (IOException ex) {
                    // no application registered for PDFs
                }
            }
        });

        settingsMeni=new PodesavanjaMeni(this, sceneWIDTH, HEIGHT, vBox, a, fullscrn);

        settingsButton.setOnMouseClicked(event -> {
            this.getChildren().remove(vBox);
            this.add(settingsMeni, 0, 0);
        });

        infoButton.setOnMouseClicked(event -> {
            this.getChildren().remove(vBox);
            this.add(new InfoMeni(this, sceneWIDTH, HEIGHT, vBox), 0, 0);
        });

        GridPane g2=resultButton.getDugmeSaPostoljem();
        vBox.getChildren().add(2, g2);

    }
}
