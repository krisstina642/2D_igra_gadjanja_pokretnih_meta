package EditorStreljane;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import PomocneKlase.JsonFajl;


public interface Editor {
    JsonFajl getJson();
    void setJSON(JsonFajl json);
    void setPozadina(Color color);
    void setPozadina(String string);
    void back();
    void skip();
    void close();
    Stage getStage();
}
