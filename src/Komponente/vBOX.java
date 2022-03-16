package Komponente;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class vBOX extends VBox {

    public vBOX(){
        super();
        this.backgroundProperty().set(new Background(new BackgroundFill(Color.AZURE,new CornerRadii(2), new Insets(-20))));
        this.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(2), null, new Insets(-20))));
        this.setAlignment(Pos.CENTER);
        this.setOpacity(1);
    }

    public vBOX(double spacing){
        this();
        this.setSpacing(spacing);
    }

    public vBOX(Node... node){
        this();
        this.getChildren().addAll(node);
    }

    public vBOX(double spacing, Node... node){
        this();
        this.setSpacing(spacing);
        this.getChildren().addAll(node);
    }

}
