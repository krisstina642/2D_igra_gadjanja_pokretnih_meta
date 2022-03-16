package Komponente;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class DugmeIkonica extends Group implements DugmePostolje{
    Background pattern1;
    Background pattern2;
    Background current;
    VBox vb;

    public DugmeIkonica(Image img1, Image img2, int size){
        vb=new VBox();
        vb.setAlignment(Pos.CENTER);
        vb.setMinWidth(size);
        vb.setMinHeight(size);
        vb.setOpacity(1);
        ImagePattern patt1 = new ImagePattern(img1);
        ImagePattern patt2 = new ImagePattern(img2);
        pattern1=new Background(new BackgroundFill(patt1, new CornerRadii(2), new Insets(-5)));
        pattern2=new Background(new BackgroundFill(patt2, new CornerRadii(2), new Insets(-5)));
        current = pattern1;
        vb.backgroundProperty().set(current);
        vb.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(2), null, new Insets(-5))));
        vb.getChildren().add(new Text(" "));
        this.getChildren().add(vb);

        this.setOnMouseEntered(e->
        {
            vb.backgroundProperty().set(current==pattern1?pattern2:pattern1);
            vb.setScaleX(1.2);
            vb.setScaleY(1.2);
        });
        this.setOnMouseExited(e->
        {
            vb.setScaleX(1);
            vb.setScaleY(1);
            vb.backgroundProperty().set(current);
        });
        this.setOnMouseClicked(e->
        {
            current = (current==pattern1)?pattern2:pattern1;
            vb.backgroundProperty().set(current);
        });

    }

    public void setOff(){
        setCurrent(pattern2);
    }
    public void setOn(){
        setCurrent(pattern1);
    }

    private void setCurrent(Background pattern){
        current = pattern;
        vb.backgroundProperty().set(current);
    }

    @Override
    public GridPane getDugmeSaPostoljem() {
        Rectangle r=new Rectangle((this.getBoundsInLocal().getWidth()+30)*1.2, (this.getBoundsInLocal().getHeight()+30)*1.2);
        r.setOpacity(0);
        GridPane g=new GridPane();
        g.setAlignment(Pos.CENTER);
        g.add(r,0,0);
        g.add(this,0,0);
        g.setHalignment(this, HPos.CENTER);
        return g;
    }
}
