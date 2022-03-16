package Komponente;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;

public class Metak extends Path {

    public Metak(double velicinaY){
        Stop[] stops1={new Stop(0, Color.GOLD), new Stop(0.72,Color.GOLDENROD), new Stop(0.73,Color.BROWN)};

        LinearGradient lg=new LinearGradient(0.5,0,0.5,1,true, CycleMethod.NO_CYCLE,stops1);
        getElements().addAll(
                new MoveTo (0,0),
                new LineTo (0,-2*velicinaY/3),
                new QuadCurveTo(velicinaY/10,-velicinaY,velicinaY/5,-2*velicinaY/3),
                new LineTo(velicinaY/5,0),
                new LineTo(0,0),
                new LineTo(0,-velicinaY/5),
                new LineTo(velicinaY/5,-velicinaY/5)
        );
        this.setFill(lg);
    }
}
