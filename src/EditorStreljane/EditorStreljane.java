package EditorStreljane;

import EditorStreljane.Meni.NacrtajOblikeMeni;
import EditorStreljane.Meni.OdabirNivoaMeni;
import EditorStreljane.Meni.OdabirPozadine;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import PomocneKlase.JsonFajl;
import PomocneKlase.SkaliranjePozornice;


public class EditorStreljane implements Editor {
    private final double WIDTH;
    private final double HEIGHT;
    private final double heightForGame;
    private JsonFajl jsonFajl;
    private final Scene SCENE;
    private final Stage primaryStage;


    public EditorStreljane(double WIDTH, double HEIGHT, double heightForGame, Stage parentStage){
        double x=parentStage.getX();
        double y=parentStage.getY();
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.heightForGame=heightForGame;

        SCENE = new Scene(new OdabirNivoaMeni(WIDTH, heightForGame, this), WIDTH, HEIGHT);
        primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initOwner(parentStage);
        primaryStage.setTitle("Editor Streljane");
        primaryStage.setScene(SCENE);

        primaryStage.setX(x + 50);
        primaryStage.setY(y + 50);
        primaryStage.setResizable(false);
        RadialGradient gradient1 = new RadialGradient(0, .1, 0.5, 0.5, 1, true,
                CycleMethod.NO_CYCLE, new Stop(0, Color.STEELBLUE),
                new Stop(1, Color.BLACK));
        SCENE.setFill(gradient1);

        primaryStage.show();
        SkaliranjePozornice.podesiSkaliranje(primaryStage);
    }


    @Override
    public JsonFajl getJson() {
        return jsonFajl;
    }

    @Override
    public void back() {
        SCENE.setRoot(new OdabirNivoaMeni(WIDTH, heightForGame, this));
    }



    @Override
    public void setJSON(JsonFajl json) {
        jsonFajl = json;
        SkaliranjePozornice.promeniKoren(primaryStage, new OdabirPozadine(WIDTH, heightForGame, this));
    }

    @Override
    public void skip() {
        ImagePattern pozadina= jsonFajl.getBackgroundPatern();
        if (pozadina!=null) setPozadina(jsonFajl.readString("poz"));
        else if (jsonFajl.getBackgroundColor()!=null) setPozadina(jsonFajl.getBackgroundColor());
    }

    @Override
    public void close() {
        primaryStage.close();
    }

    @Override
    public void setPozadina(Color color) {
        jsonFajl.addString("BOJA", color.toString());
        jsonFajl.addString("poz", "?");
        jsonFajl.write();
        NacrtajOblikeMeni nacrtajOblikeMeni=new NacrtajOblikeMeni( WIDTH, HEIGHT,heightForGame, color, this);
        SkaliranjePozornice.promeniKoren(primaryStage, nacrtajOblikeMeni );
        addUndoRedoHandler(nacrtajOblikeMeni);
    }

    @Override
    public void setPozadina(String string) {
        jsonFajl.addString("BOJA", "?");
        jsonFajl.addString("poz", string);
        jsonFajl.write();
        NacrtajOblikeMeni nacrtajOblikeMeni=new NacrtajOblikeMeni( WIDTH, HEIGHT,heightForGame, string, this);
        SkaliranjePozornice.promeniKoren(primaryStage, nacrtajOblikeMeni);
        addUndoRedoHandler(nacrtajOblikeMeni);
    }
    @Override
    public Stage getStage() {
        return primaryStage;
    }

    private void addUndoRedoHandler(NacrtajOblikeMeni g){
        SCENE.setOnKeyPressed(event -> {
            if(event.getCode()== KeyCode.Z && event.isControlDown()) {
                g.undo();
            }
            else if(event.getCode()== KeyCode.Y && event.isControlDown()) {
                g.redo();
            }
        });

    }
}
