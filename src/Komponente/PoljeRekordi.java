package Komponente;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PoljeRekordi extends Group {
    private String ime;
    private int bodovi;

    public PoljeRekordi(int bodovi, String ime, int size) {
        this.ime=ime;
        this.bodovi=bodovi;
        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);
        vb.setOpacity(1);
        vb.backgroundProperty().set(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(2), new Insets(-5))));
        vb.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID, new CornerRadii(2), BorderStroke.MEDIUM, new Insets(-5))));
        vb.getChildren().add(new Text("  "+this.ime + " " + this.bodovi+" "));
        vb.setMinWidth(size);
        vb.setMinHeight(25);
        this.getChildren().add(vb);
    }

    public String getIme() {
        return ime;
    }

    public int getBodovi() {
        return bodovi;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setBodovi(int bodovi) {
        this.bodovi = bodovi;
    }
}
