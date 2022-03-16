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


public class Dugme extends Group implements DugmePostolje{
        private VBox vb;
        private Background background;
        private boolean image;
        private int width;
        private int height;

    public Dugme(String string){
        vb=new VBox();
        vb.setAlignment(Pos.CENTER);
        vb.setOpacity(1);
        image = false;
        background= new Background(new BackgroundFill(Color.WHITE,new CornerRadii(2), new Insets(-5)));
        vb.backgroundProperty().set(background);
        vb.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(2), null, new Insets(-5))));
        vb.getChildren().add(new Text(string));
        this.getChildren().add(vb);
        this.setOnMouseEntered(e->
        {
            vb.backgroundProperty().set((image)? background:
                    new Background(new BackgroundFill(Color.CYAN,new CornerRadii(2), new Insets(-5))));
            if (image) vb.setBorder(new Border(new BorderStroke(Color.BLUE,BorderStrokeStyle.SOLID,new CornerRadii(2), null, new Insets(-5))));
            vb.setScaleX(1.2);
            vb.setScaleY(1.2);
        });
        this.setOnMouseExited(e->
        {
            vb.setScaleX(1);
            vb.setScaleY(1);
            vb.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(2), null, new Insets(-5))));
            vb.backgroundProperty().set(background);
        });
    }

    public Dugme(String string, int size) {
        this(string);
        vb.setMinWidth(size);
        this.width = size;
        this.height = size;
    }

    public Dugme(int width, int height){
        this("");
        image=true;
        this.width = width;
        this.height = height;
        vb.setMinWidth(width);
        vb.setMinHeight(height);
    }
    @Override
    public GridPane getDugmeSaPostoljem() {
        Rectangle r=new Rectangle(((image)? (width+5): (vb.getBoundsInParent().getWidth()+5))*1.3+5, ((image)? (height+5) :(vb.getBoundsInParent().getHeight()+5))*1.3+5);
        r.setOpacity(0);
        GridPane g=new GridPane();
        g.setAlignment(Pos.CENTER);
        g.add(r,0,0);
        g.add(this,0,0);
        g.setHalignment(this, HPos.CENTER);
        g.setAlignment(Pos.CENTER);
        return g;
    }

    public void setPozadinaImage(Image img){
        image=true;
        background=new Background(new BackgroundFill(new ImagePattern(img), new CornerRadii(2), new Insets(-5)));
        vb.setBackground(background);
    }

    public void setPozadinaColor(Color color){
        image=true;
        background=new Background(new BackgroundFill(color, new CornerRadii(2), new Insets(-5)));
        vb.setBackground(background);
    }
}
