package Meni;

import Komponente.Dugme;
import Komponente.PoljeRekordi;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;



import java.util.ArrayList;

public class RekordiMeni extends Group{

    public RekordiMeni(GridPane panel, double sceneWIDTH, double HEIGHT, VBox povratniBox, ArrayList<PoljeRekordi> rekordi){

        Dugme button2 = new Dugme( "ZATVORI");
        button2.setOnMouseClicked(event -> {
            panel.getChildren().remove(panel.getChildren().size()-1);
            panel.add(povratniBox,0,0);
        });

        VBox rezultati=new VBox(10);
        rezultati.setMinWidth(250);
        rezultati.setMaxWidth(400);
        rezultati.setAlignment(Pos.CENTER);
        rezultati.setOpacity(1);

        rezultati.backgroundProperty().set(new Background(new BackgroundFill(Color.AZURE,new CornerRadii(2), new Insets(-20))));
        rezultati.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(2), null, new Insets(-20))));

        for (int i=0; i<10; i++){
            rezultati.getChildren().add(rekordi.get(i));
        }

        rezultati.getChildren().add(button2.getDugmeSaPostoljem());

        GridPane gp=new GridPane();
        gp.setPrefWidth(sceneWIDTH);
        gp.setPrefHeight(HEIGHT);
        gp.setAlignment(Pos.CENTER);
        gp.add(rezultati,0,0);
        this.getChildren().add(gp);
    }
}
