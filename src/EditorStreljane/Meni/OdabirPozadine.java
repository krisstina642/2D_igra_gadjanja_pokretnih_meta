package EditorStreljane.Meni;

import EditorStreljane.Editor;
import Komponente.Dugme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import PomocneKlase.Boja;
import Komponente.vBOX;
import java.io.*;
import java.nio.file.*;

public class OdabirPozadine extends Group{
    public OdabirPozadine(double WIDTH, double HEIGHT, Editor editor){


        Text naslov=new Text(" ODABERI NEKU OD BOJA IZ PALETE ZA POZADINU ILI UČITAJ NOVU SLIKU");
        vBOX rezultati=new vBOX(20, naslov);
        HBox colors=new HBox(20);
        HBox colors2=new HBox(20);
        HBox colors3=new HBox(20);
        colors.setAlignment(Pos.CENTER);


        for(int i=0; i<18; i++){
            Dugme button=new Dugme(50, 20);
            button.setPozadinaColor(Boja.getBoja(i));
            int finalI = i;
            if (i<6) colors.getChildren().add(button.getDugmeSaPostoljem());
            else if (i<12) colors2.getChildren().add(button.getDugmeSaPostoljem());
            else colors3.getChildren().add(button.getDugmeSaPostoljem());
            button.setOnMouseReleased(event -> {
                editor.setPozadina(Boja.getBoja(finalI));
            });


        }
        rezultati.getChildren().addAll(colors, colors2, colors3);
        FileChooser.ExtensionFilter imageFilter
                = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif");
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().add(imageFilter);

        Dugme btn = new Dugme("ODABERI POZADINU");
        btn.setOnMouseReleased(event -> {
            final File file = fileChooser.showOpenDialog(editor.getStage());
            final Path source = Paths.get(file.toString()); //original file

            File dest = new File("config/"+source.getFileName().toString());
            System.out.println(dest.getAbsolutePath());

            try {
                Files.copy(source, dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            editor.setPozadina(dest.getAbsolutePath());
        });

        Dugme nazad = new Dugme("   NAZAD   ");
        Dugme napred = new Dugme(" PRESKOČI ");

        nazad.setOnMouseClicked(event -> {
            editor.back();
        });

        napred.setOnMouseClicked(event -> {
            editor.skip();
        });

        rezultati.getChildren().add(btn.getDugmeSaPostoljem());

        GridPane gp=new GridPane();
        gp.setPrefWidth(WIDTH);
        gp.setPrefHeight(HEIGHT);
        gp.setAlignment(Pos.CENTER);
        gp.add(rezultati,1,1);
        gp.setHgap(15);
        gp.setVgap(100);
        gp.add(nazad.getDugmeSaPostoljem(),0,2);
        gp.add(napred.getDugmeSaPostoljem(),2,2);
        gp.setPadding(new Insets(0, 10, 0, 10));
        this.getChildren().add(gp);
    }

}
