package Meni;

import Komponente.Dugme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class InfoMeni extends Group {

    public InfoMeni(GridPane panel, double sceneWIDTH, double HEIGHT, VBox povratniBox){

        Dugme button2 = new Dugme( "ZATVORI");
        button2.setOnMouseClicked(event -> {
            panel.getChildren().remove(panel.getChildren().size()-1);
            panel.add(povratniBox,0,0);
        });

        VBox vBox=new VBox(10);
        vBox.setMinWidth(250);
        vBox.setMaxWidth(400);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setOpacity(1);
        HBox hBox =new HBox(new Text("METE v0.3"));
        hBox.setAlignment(Pos.CENTER);

        vBox.backgroundProperty().set(new Background(new BackgroundFill(Color.AZURE,new CornerRadii(2), new Insets(-20))));
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(2), null, new Insets(-20))));
        vBox.getChildren().add(hBox);
        vBox.getChildren().add(new Text(" "));
        vBox.getChildren().add(new Text("Diplomski rad osnovnih akademskih studija"));
        vBox.getChildren().add(new Text("Opis:  Cilj igre je uništiti sve pokretne mete sa što"));
        vBox.getChildren().add(new Text("           boljim prolaznim vremenom i tako se naći"));
        vBox.getChildren().add(new Text("           u vrhu rang-liste najbolje plasiranih igrača."));
        vBox.getChildren().add(new Text(" "));
        vBox.getChildren().add(new Text("Autor: Babić Kristina"));
        vBox.getChildren().add(new Text("Mentor: prof. dr. Igor Tartalja"));
        vBox.getChildren().add(new Text(" "));
        vBox.getChildren().add(new Text("© Univerzitet u Beogradu – Elektrotehnički fakultet, 2021"));
        vBox.getChildren().add(new Text(" "));
        vBox.getChildren().add(button2.getDugmeSaPostoljem());

        GridPane gp=new GridPane();
        gp.setPrefWidth(sceneWIDTH);
        gp.setPrefHeight(HEIGHT);
        gp.setAlignment(Pos.CENTER);
        gp.add(vBox,0,0);
        this.getChildren().add(gp);
    }
}
