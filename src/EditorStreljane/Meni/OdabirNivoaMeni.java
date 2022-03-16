package EditorStreljane.Meni;

import EditorStreljane.Editor;
import Komponente.Dugme;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import PomocneKlase.JsonFajl;
import Komponente.vBOX;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


public class OdabirNivoaMeni extends Group {

        public OdabirNivoaMeni(double WIDTH, double HEIGHT, Editor editor){

            Text naslov=new Text(" ODABERI NIVO");

            HBox prviRed = new HBox(0);
            HBox drugiRed =new HBox(0);
            HBox hbox=prviRed;

            for (int i=0;i<6; i++){

                Dugme button = new Dugme( 180, 120 );
                button.setPozadinaImage(new Image("Slike/Nivo"+(i+1)+".png"));
                VBox vBox = new VBox(10, new Text("Nivo"+(i+1)), button.getDugmeSaPostoljem());
                vBox.setAlignment(Pos.CENTER);
                if (i==3) hbox=drugiRed;
                hbox.getChildren().add(vBox);

                int finalI = i;
                button.setOnMouseReleased(event -> {
                    JsonFajl JSON_Nivo = new JsonFajl("config/Nivo"+(finalI +1)+".json");
                    editor.setJSON(JSON_Nivo);

                    Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(editor.getStage());
                    dialog.setResizable(false);

                    Text tt1=new Text(" Da li želiš da učitaš neku od postojećih konfiguracija ");
                    tt1.setFont(Font.font(15));
                    Text tt2=new Text(" ili da napraviš nivo ispočetka? ");
                    tt2.setFont(Font.font(15));
                    Dugme oldB = new Dugme( " Učitaj postojeću ",150 );
                    Dugme newB = new Dugme( " Napravi nivo ispočetka ",150 );
                    VBox box=new VBox(10, tt1, tt2, oldB.getDugmeSaPostoljem(), newB.getDugmeSaPostoljem());
                    box.setMinSize(450,180);
                    box.setAlignment(Pos.CENTER);
                    box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,new CornerRadii(2), null, new Insets(-5))));

                    Scene dialogScene = new Scene(box);
                    dialog.setScene(dialogScene);
                    dialog.show();
                    newB.setOnMouseClicked(event1 -> {
                        dialog.close();
                    });

                    FileChooser.ExtensionFilter imageFilter
                            = new FileChooser.ExtensionFilter("JSON Files", "*.json");
                    FileChooser fileChooser=new FileChooser();
                    fileChooser.getExtensionFilters().add(imageFilter);
                    fileChooser.setInitialDirectory(new File ("config"));

                    oldB.setOnMouseClicked(event1 -> {
                        final File file = fileChooser.showOpenDialog(editor.getStage());
                        final Path source = Paths.get(file.toString()); //original file
                        editor.getJson().copyFrom(source);
                        dialog.close();
                        editor.close();
                    });

                });
            }

            vBOX vBox=new vBOX(20, naslov, prviRed, drugiRed);
            GridPane gp=new GridPane();
            gp.setPrefWidth(WIDTH);
            gp.setPrefHeight(HEIGHT);
            gp.setAlignment(Pos.CENTER);
            gp.add(vBox,0,0);
            this.getChildren().add(gp); // kad stavim SAMO grid Pane ne radi skaliranje scene
        }


}
