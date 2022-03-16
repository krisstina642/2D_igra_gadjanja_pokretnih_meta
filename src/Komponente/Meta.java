package Komponente;

import MainDodatak.Akcije;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import PomocneKlase.TezinaNivoa;

import java.util.List;


public class Meta extends Group {

        private Animation animacija;
        private Akcije action;
        private double scale = 1;
        private Meta meta;

        private int brojPrstena;
        private double poluprecnik;
        private AnimationTimer at;

        public void setScale(double d){
            scale=d;
        }

    public Meta (TezinaNivoa tezina, Color boja, double poluprecnik, Akcije action){
        meta=this;
       this.action = action;
        if (tezina==TezinaNivoa.EASY) this.brojPrstena=3;
        else if (tezina==TezinaNivoa.MEDIUM) this.brojPrstena=5;
        else if (tezina==TezinaNivoa.HARD) this.brojPrstena=7;

        this.poluprecnik=poluprecnik;

        Group root=new Group();

        for (int i=brojPrstena;i>0;i--){

            int bodovi=150;
            switch (i){
                case 2: { bodovi=120; break;}
                case 3: { bodovi=100; break;}
                case 4: { bodovi=80; break;}
                case 5: { bodovi=60; break;}
                case 6: { bodovi=40; break;}
                case 7: { bodovi=20; break;}
            }

            Circle c;
            Text t=new Text(""+bodovi);
            t.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 9));
            t.setStrokeWidth(0);
            t.getTransforms().addAll(
                    new Translate(-t.getBoundsInParent().getWidth()/2,-t.getBoundsInParent().getHeight()/2),
                    new Translate(-t.getBoundsInParent().getMinX(),-t.getBoundsInParent().getMinY())
            );

            if (i==1){
                c=new Circle(20,boja);
                t.setFill(Color.BLACK);
            }
            else if (i%2==0){
                c=new Circle(20*i,Color.WHITE);
                c.setStroke(Color.BLACK);
                t.setFill(boja);
            }
            else {
                c=new Circle(20*i,Color.BLACK);
                t.setFill(boja);
            }
            t.getTransforms().add(
                    new Translate((i==1)?0:10+20*(i-1),0)
            );
            root.getChildren().addAll(c,t);
                    }
        root.getTransforms().addAll(
                new Scale(poluprecnik/(20*brojPrstena),poluprecnik/(20*brojPrstena))
        );
        getChildren().addAll(root);
    }

    public int Bounus(){
        int bodovi=0;
        if (this.scale<0.6) bodovi= 80;
        else if (this.scale<0.7) bodovi= 40;
        else if (this.scale<0.8) bodovi= 20;
        else if (this.scale<0.9) bodovi= 10;
        if (brojPrstena==3 && this.scale<0.9) return bodovi/2;
        if (brojPrstena==7 && this.scale<0.9) return bodovi+20;
        return bodovi;

    };

    public int brojBodova(double poluprecnik){

        double prsten = (poluprecnik/(this.poluprecnik/this.brojPrstena));
        if (prsten<=1) { return 150;}
        if (prsten<=2) { return 120;}
        if (prsten<=3) { return 100;}
        if (prsten<=4) { return 80; }
        if (prsten<=5) { return 60; }
        if (prsten<=6) { return 40; }
        return 20;

    };

    public void stopAnimacija() {
        if (this.animacija!=null) this.animacija.stop();
    }

    public void pauseAnimacija() {
        if (this.animacija!=null) this.animacija.pause();
    }

    public void continueAnimacija() {
        if (this.animacija!=null) this.animacija.play();
    }

    public void setAnimacija(Animation a) {
        this.animacija=a;
    }

    public void dodajUGrupuIListu(Group grupa, List<Meta> lista){

        at=new AnimationTimer() {
            @Override
            public void handle(long now) {
                meta.setScale(meta.getScaleX());
            }
        };
        at.start();

        this.setEventHandler(MouseEvent.MOUSE_PRESSED, e->{

            Image paintImage=new Image("Slike/stain1.png");
            Rectangle rec= new Rectangle(poluprecnik*2*getScaleX(), poluprecnik*2*getScaleY()) ;
            rec.setFill(new ImagePattern(paintImage));
            rec.setOpacity(0.9);
            rec.getTransforms().add(
                    new Translate(this.getBoundsInParent().getMinX(),this.getBoundsInParent().getMinY())
            );
            grupa.getChildren().add(rec);

            double x=e.getX();
            double y=e.getY();

            double r=Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
            if( r > poluprecnik ) return;
            int vrednost_bodova=brojBodova(r);
            int bonus=Bounus();

            this.stopAnimacija();
            lista.remove(this);
            action.azurirajPogodjeneMete(vrednost_bodova+bonus, r/(poluprecnik/brojPrstena)<=1);

            Text bodovi=new Text(Integer.toString(vrednost_bodova));
            bodovi.setStrokeWidth(1);
            bodovi.setFont(Font.font(32));
            bodovi.getTransforms().add(
                    new Translate(this.getBoundsInParent().getMinX()+x+poluprecnik,this.getBoundsInParent().getMinY()+y+poluprecnik)
            );
            bodovi.setFill(Color.WHITE);
            bodovi.setStroke(Color.BLACK);
            Text bonusBodovi=new Text("+"+bonus);
            bonusBodovi.setStroke(Color.RED);
            bonusBodovi.setFont(Font.font(25));
            bonusBodovi.getTransforms().add(
                    new Translate(this.getBoundsInParent().getMinX()+x+poluprecnik+10,this.getBoundsInParent().getMinY()+y+poluprecnik-20)
            );
            bonusBodovi.setFill(Color.WHITE);
            //bonusBodovi.setStrokeType(StrokeType.OUTSIDE);
            grupa.getChildren().add(bodovi);
            if (bonus>0) grupa.getChildren().add(bonusBodovi);

            AnimationTimer at=new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (bodovi.getOpacity()==0) {
                        grupa.getChildren().remove(bodovi);
                        if(bonus>0) grupa.getChildren().remove(bonusBodovi);
                        this.stop();
                    }
                    if(bonus>0) {
                        bonusBodovi.setOpacity(bonusBodovi.getOpacity()-0.004);
                        if(bonusBodovi.getFont().getSize() > 10) bonusBodovi.setFont(Font.font(bonusBodovi.getFont().getSize()-0.2));
                    }

                    bodovi.setOpacity(bodovi.getOpacity()-0.004);
                    if(bodovi.getFont().getSize() > 10) bodovi.setFont(Font.font(bodovi.getFont().getSize()-0.2));
                }
            };
            at.start();
            nestaniIzGrupe(grupa);
        });

        grupa.getChildren().addAll(this);
        lista.add(this);
        this.toBack();

    }

    public void nestaniIzGrupe(Group grupa){
        //this.setMouseTransparent(true);

        this.setOnMousePressed(event -> {});
        AnimationTimer metaTimer=new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (Meta.this.getOpacity()==0) {
                    grupa.getChildren().remove(Meta.this);
                    this.stop();
                }
                Meta.this.setOpacity(Meta.this.getOpacity()-0.01);
            }
        };
        metaTimer.start();

    }

    public int getMaxBonus() {
        return (brojPrstena==3)? 40:(brojPrstena==5)? 80:100;
    }
}
