package Komponente;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import PomocneKlase.Pozicija;


public class Nisan extends Path implements Pozicija {
    double x;
    double y;

    public Nisan(double velicina, double X, double Y){
        this.x=X;
        this.y=Y;
        double krak=velicina/2;

        this.getElements().addAll(
                new MoveTo(-krak,krak*0.1),
                new HLineTo(-krak*0.1),
                new VLineTo(krak),
                new HLineTo(krak*0.1),
                new VLineTo(krak*0.1),
                new HLineTo(krak),
                new VLineTo(-krak*0.1),

                new HLineTo(krak*0.1),
                new VLineTo(-krak),
                new HLineTo(-krak*0.1),
                new VLineTo(-krak*0.1),
                new HLineTo(-krak),
                new VLineTo(krak*0.1)
        );
        this.setFill(Color.RED);

    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }


    public double getScreenX() {
        Bounds bounds = this.getBoundsInLocal();
        Bounds screenBounds = this.localToScreen(bounds);
        x = (int) (screenBounds.getWidth() / 2 + screenBounds.getMinX());
        return x;
    }


    public double getScreenY() {
        Bounds bounds = this.getBoundsInLocal();
        Bounds screenBounds = this.localToScreen(bounds);
        x = (int) (screenBounds.getWidth() / 2 + screenBounds.getMinX());
        return y;
    }

    @Override
    public void setKoordinate(double X, double Y) {
        this.x=X;
        this.y=Y;
    }

    @Override
    public void pomeriZa(double X, double Y) {
        this.x=this.x+X;
        this.y=this.y+Y;
    }
}
