package Komponente;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import PomocneKlase.Boja;


public class PaletaBoja extends VBox {

    private Rectangle choosen;

    public PaletaBoja(boolean bool){
        super(5);
        HBox firstLine=new HBox(5);
        HBox secondLine=new HBox(5);

        for(int i=0; i<16; i++){
            Rectangle R= new Rectangle(20,20);
            if (i==0) choosen = R;
            R.setFill(bool? Boja.getBoja(i): Boja.getBoja2(i));
            R.setStroke(Color.BLACK);
            R.setStrokeWidth(1);
            R.setStrokeType(StrokeType.OUTSIDE);

            if (i<8) firstLine.getChildren().add(R);
            else secondLine.getChildren().add(R);

            R.setOnMouseClicked(event -> {
                if (choosen!=null) {
                    choosen.setStroke(Color.BLACK);
                    choosen.setStrokeWidth(1);
                    choosen.setStrokeType(StrokeType.OUTSIDE);
                }
                choosen = R;
                choosen.setStroke(Color.RED);
                choosen.setStrokeWidth(2);
                choosen.setStrokeType(StrokeType.CENTERED);
            });
        }

        choosen.setStrokeWidth(2);
        choosen.setStroke(Color.RED);
        choosen.setStrokeType(StrokeType.CENTERED);

        this.getChildren().addAll(firstLine,secondLine);
    }

    public void restart(){
        if (choosen!=null) {
            choosen.setStroke(Color.BLACK);
            choosen.setStrokeWidth(1);
            choosen.setStrokeType(StrokeType.OUTSIDE);
        }
        choosen=null;
    }

    public Color getColor(){
         return (choosen==null)? null : (Color) choosen.getFill();
    }

    public void addAnotherOne(PaletaBoja pp){
        this.setOnMousePressed(event->{
            pp.restart();
        });
    }


}
