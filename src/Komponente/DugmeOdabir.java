package Komponente;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class DugmeOdabir extends Group implements DugmePostolje {
    private VBox vb;
    private boolean choosen;
    private Background background1;
    private Background background2;

    public DugmeOdabir(Color color, Color color2){
        vb=new VBox();
        vb.setAlignment(Pos.CENTER);
        vb.setOpacity(1);
        background1 = new Background(new BackgroundFill(color,new CornerRadii(0), new Insets(0)));
        background2 = new Background(new BackgroundFill(color2,new CornerRadii(0), new Insets(0)));
        vb.backgroundProperty().set(background1);

        EventHandler<MouseEvent> mouseEnter = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setBig();
            }
        };
        EventHandler<MouseEvent> mouseExit = event -> {
            if(!choosen){
                setSmall();
            }
        };
        EventHandler<MouseEvent> mouseClick = event -> {
            if (!choosen) {
                choosen = true;
            } else {
                choosen = false;
            }
        };

        this.setEventHandler(MouseEvent.MOUSE_ENTERED, mouseEnter);
        this.setEventHandler(MouseEvent.MOUSE_EXITED, mouseExit);
        this.setEventHandler(MouseEvent.MOUSE_CLICKED, mouseClick);
    }

    public DugmeOdabir(Node oblik, Color color, Color color2){
        this(color,color2);
        vb.getChildren().add(oblik);
        this.getChildren().add(vb);
    }

    private void setSmall(){
        vb.setScaleX(1);
        vb.setScaleY(1);
        vb.setBorder(Border.EMPTY);
        vb.backgroundProperty().set(background1);
    }

    private void setBig(){
        vb.setScaleX(1.2);
        vb.setScaleY(1.2);
        vb.backgroundProperty().set(background2);
        vb.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(0), null, new Insets(0))));
    }

    public void restart(){
        choosen=false;
        setSmall();
    }

    @Override
    public GridPane getDugmeSaPostoljem() {
        Rectangle r=new Rectangle((this.getBoundsInLocal().getWidth())*1.2, (this.getBoundsInLocal().getHeight())*1.2);
        GridPane g=new GridPane();
        r.setOpacity(0);
        g.setAlignment(Pos.CENTER);
        g.add(r,0,0);
        g.add(this,0,0);
        g.setHalignment(this, HPos.CENTER);
        return g;
    }
}
