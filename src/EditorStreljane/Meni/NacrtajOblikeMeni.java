package EditorStreljane.Meni;

import EditorStreljane.Editor;
import EditorStreljane.StanjeEditora;
import Komponente.Dugme;
import Komponente.DugmeOdabir;
import Komponente.Meta;
import Komponente.PaletaBoja;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import PomocneKlase.*;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class NacrtajOblikeMeni extends Group {
    private final double WIDTH;
    private final double HEIGHT;
    private final double heightForGame;
    private Editor editor;
    private Color color;
    private DugmeOdabir choosen;
    private ArrayList<Node> listOfShapes;
    private ArrayList<Circle> pomeranjeOblika = new ArrayList<>();
    private Node selectedNode;
    private PaletaBoja paleta1;
    private PaletaBoja paleta2;
    private StanjeEditora STANJE;
    private DugmeOdabir animationButton;
    private Rectangle animationBackground;
    private HBox alati;
    private DugmeOdabir polyButton;
    private ArrayList<Node> redo=new ArrayList<>();
    private DugmeOdabir targetButton;
    private Rectangle BACKGROUND1;
    private Rectangle BACKGROUND2;


    public NacrtajOblikeMeni(double WIDTH, double HEIGHT, double heightForGame, String string, Editor editor){

        this.HEIGHT=HEIGHT;
        this.WIDTH=WIDTH;
        this.color=Color.WHITE;
        this.heightForGame=heightForGame;
        this.editor = editor;
        File f=new File(string);

        ImagePattern pozadina= null;
        try {
            pozadina = new ImagePattern(new Image(f.toURI().toURL().toString()),0,0,1,1,true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BACKGROUND2= new Rectangle(WIDTH,heightForGame-5);
        BACKGROUND2.setFill(pozadina);
        BACKGROUND1 = new Rectangle(WIDTH,HEIGHT);
        BACKGROUND1.setFill(Color.WHITE);
        this.getChildren().addAll(BACKGROUND1, BACKGROUND2);
        init();
    }

    public NacrtajOblikeMeni(double WIDTH, double HEIGHT, double heightForGame, Color color, Editor editor){

        this.HEIGHT=HEIGHT;
        this.WIDTH=WIDTH;
        this.heightForGame=heightForGame;
        this.editor = editor;
        BACKGROUND2 = new Rectangle(WIDTH,heightForGame-5);
        BACKGROUND2.setFill(color);
        BACKGROUND1 = new Rectangle(WIDTH,HEIGHT);
        BACKGROUND1.setFill(Color.WHITE);
        this.getChildren().addAll(BACKGROUND1, BACKGROUND2);
        this.color=color;
        init();

    }

    private void init(){

        listOfShapes=new ArrayList<>();
      /* ArrayList<Node> temp =editor.getJson().getDistractions();
        for (int i=0;i<temp.size();i++){
            listOfShapes.add(temp.get(i));
            this.getChildren().add(temp.get(i));
            if (listOfShapes.get(i) instanceof Polygon || listOfShapes.get(i) instanceof Polyline ) addDragHandlerPoly();
            else if (temp.get(i) instanceof Line) addHandler((Line) temp.get(i));
            else if (temp.get(i) instanceof Ellipse) addHandler((Ellipse) temp.get(i));
            else if (temp.get(i) instanceof Path) addHandler((Path) temp.get(i));
            else if (temp.get(i) instanceof CubicCurve) addHandler((CubicCurve) temp.get(i));
        }*/

        STANJE= StanjeEditora.CRTANJE;
        double endX = HEIGHT-heightForGame-5;

        Line line=new Line( 5, 5, endX, endX);
        DugmeOdabir lineButton = new DugmeOdabir( line, Color.WHITE, color );
        Tooltip tooltipLine= new Tooltip("Prava Linija");
        tooltipLine.setShowDelay(Duration.millis(50));
        Tooltip.install(lineButton,tooltipLine);

        Rectangle rectangle = new Rectangle( 5,5,(endX-5)*1.3, endX-5);
        rectangle.setFill(null);
        rectangle.setStroke(Color.BLACK);
        DugmeOdabir recButton = new DugmeOdabir( rectangle, Color.WHITE, color );
        Tooltip tooltipRec= new Tooltip("Četvorougao");
        tooltipRec.setShowDelay(Duration.millis(50));
        Tooltip.install(recButton,tooltipRec);

        Polyline polyline = new Polyline(5,5, endX,5,endX/3*2, endX/3, endX, endX,5,endX, endX/3, endX/3*2);
        polyline.setFill(null);
        polyline.setStroke(Color.BLACK);
        polyButton= new DugmeOdabir( polyline, Color.WHITE, color );
        Tooltip tooltipPoly= new Tooltip("");
        tooltipPoly.setShowDelay(Duration.millis(50));
        Tooltip.install(polyButton,tooltipPoly);
        Tooltip tooltipPoly2= new Tooltip("Izlomljena linija\n Zadrži SHIFT za crtanje poligona");
        tooltipPoly2.setShowDelay(Duration.millis(50));
        Tooltip.install(polyButton,tooltipPoly2);

        Ellipse ellipse = new Ellipse(1.3*(endX)/2,(endX-5)/2);
        ellipse.setFill(null);
        ellipse.setStroke(Color.BLACK);
        DugmeOdabir ellButton = new DugmeOdabir( ellipse, Color.WHITE, color );
        Tooltip tooltipEll= new Tooltip("Elipsa\nZadrži SHIFT za crtanje kruga");
        tooltipEll.setShowDelay(Duration.millis(50));
        Tooltip.install(ellButton,tooltipEll);

        CubicCurve cubicCurve = new CubicCurve( 5, 5, (endX - 5)/3*2 + 5, 5,(endX - 5)/3+5,endX , endX, endX );
        cubicCurve.setFill(null);
        cubicCurve.setStroke(Color.BLACK);
        DugmeOdabir curButton = new DugmeOdabir( cubicCurve, Color.WHITE, color );
        Tooltip tooltipCur= new Tooltip("Kriva Linija");
        tooltipCur.setShowDelay(Duration.millis(50));
        Tooltip.install(curButton,tooltipCur);


        int tlen= (int)((endX-5)/2-2.5);

        String  text = "M 5 5 L "+endX+" 5 L "+endX+" 10 L "+(endX-tlen)+" 10 L "+(endX-tlen)+" "+endX+" L "+(5+tlen)+" "+endX+" L "+(5+tlen)+" 10 L 5 10 z";
        Path p= ObradaOblika.stringToPath(text);
        p.setFill(Color.BLACK);

        Rectangle rectanglePaint = new Rectangle( 5,5,(endX-5)*1.3, endX-5);
        Image paintImage=new Image("Slike/paint.png");
        rectanglePaint.setFill(new ImagePattern(paintImage));
        DugmeOdabir paintButton = new DugmeOdabir( rectanglePaint, Color.WHITE, color);
        Tooltip tooltipPaint= new Tooltip("Paleta za bojenje figura\nZadrži shift za bojenje oivičenja");
        tooltipPaint.setShowDelay(Duration.millis(50));
        Tooltip.install(paintButton,tooltipPaint);

        Meta target =new Meta(TezinaNivoa.EASY, Boja.getBoja(),(endX-5)/2, null);
        targetButton = new DugmeOdabir( target, Color.WHITE, color);
        Tooltip tooltipTarget= new Tooltip("Ubaci mete u igru");
        tooltipTarget.setShowDelay(Duration.millis(50));
        Tooltip.install(targetButton, tooltipTarget);

        animationBackground = new Rectangle( 5,5,(endX-5)*1.3, endX-5);
        Image animationImg=new Image("Slike/animation.png");
        animationBackground.setFill(new ImagePattern(animationImg));

        Rectangle doneBackground = new Rectangle( 5,5,(endX-5)*1.3, endX-5);
        Image doneImg=new Image("Slike/done.png");
        doneBackground.setFill(new ImagePattern(doneImg));
        DugmeOdabir doneButton = new DugmeOdabir( doneBackground, Color.WHITE, color);
        Tooltip tooltipDone= new Tooltip("Zavrsi sa izmenama");
        tooltipDone.setShowDelay(Duration.millis(50));
        Tooltip.install(doneButton,tooltipDone);

        DugmeOdabir txtButton = new DugmeOdabir( p, Color.WHITE, color );
        Tooltip tooltipTxt= new Tooltip("Ubaci tekst");
        tooltipTxt.setShowDelay(Duration.millis(50));
        Tooltip.install(txtButton,tooltipTxt);
        DugmeOdabir[] listaDugmadi={lineButton, recButton, polyButton, ellButton, curButton, txtButton, paintButton, targetButton};

/////////---------------------------------------------------

        doneButton.setOnMouseClicked(event -> {
            editor.getJson().addShapes(listOfShapes);
            editor.getJson().write();
            editor.getStage().close();

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(editor.getStage());
            dialog.setResizable(false);

            dialog.setOnCloseRequest(event1 -> editor.getStage().close());

            Text tt1=new Text(" Da li želite da sačuvate konfiguraciju pod novim imenom? ");
            tt1.setFont(Font.font(15));
            Dugme da= new Dugme( " Da ",150 );
            Dugme ne = new Dugme( " Ne ",150 );
            VBox box=new VBox(10, tt1, da.getDugmeSaPostoljem(), ne.getDugmeSaPostoljem());
            box.setMinSize(450,180);
            box.setAlignment(Pos.CENTER);
            box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,new CornerRadii(2), null, new Insets(-5))));

            Scene dialogScene = new Scene(box);
            dialog.setScene(dialogScene);
            dialog.show();

            ne.setOnMouseClicked(event1 -> dialog.close() );
            da.setOnMouseClicked(event1 -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save");
                fileChooser.setInitialDirectory(new File ("config"));
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON FILES", "*.json"));
                File file = fileChooser.showSaveDialog(editor.getStage());
                if (file!=null) {
                    JsonFajl jsonFajl = new JsonFajl(file.getPath());
                    jsonFajl.copyFrom(editor.getJson());
                }
                dialog.close();
            });
        });

        AtomicReference<Double> startX = new AtomicReference<>((double) 0);
        AtomicReference<Double> startY = new AtomicReference<>((double) 0);
        final Line[] newLine = {null};
        final Path[] newRectangle = {null};
        final Ellipse[] newEllipse = {null};
        final CubicCurve[] newCurve = {null};
        final Node[][] newShapes= new Node[][]{newLine,newRectangle,newEllipse, newCurve};

        for (int i=0; i<listaDugmadi.length;i++){
            int finalI = i;
            listaDugmadi[finalI].setOnMouseReleased(event -> {
                addDragHandlerPoly();
                if (alati.getChildren().contains(animationButton)) deleteAnimationOption();
                if (choosen!=null && choosen!=listaDugmadi[finalI]) choosen.restart();
                if (choosen==listaDugmadi[finalI]){
                    choosen=null;
                }
                else choosen=listaDugmadi[finalI];
                this.getChildren().removeAll(pomeranjeOblika);
                pomeranjeOblika.clear();
                selectedNode=null;
            });
        }

        Group mainGroup=this;

        EventHandler<MouseEvent> mousePressedHandler = event -> {
            if (event.getY()>heightForGame-5 || choosen==null) return;
            startX.set(event.getX());
            startY.set(event.getY());
            if(choosen==txtButton){
                Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(editor.getStage());

                TextField tf= new TextField();
                tf.setAlignment(Pos.CENTER);
                tf.setMaxWidth(180);
                Dugme button2 = new Dugme( "OK");
                Text tt=new Text("Unesite tekst");
                tt.setFont(Font.font(15));
                VBox dialogVbox = new VBox(10, tt, tf, button2.getDugmeSaPostoljem());
                dialogVbox.setAlignment(Pos.CENTER);
                dialogVbox.setMinWidth(200);
                dialogVbox.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,new CornerRadii(2), null, new Insets(-5))));

                Scene dialogScene = new Scene(dialogVbox);
                dialog.setScene(dialogScene);
                dialog.show();

                button2.setOnMouseClicked(event2 -> {
                    Text t=new Text(startX.get(), startY.get(), tf.getText());
                    t.setFont(Font.font(20));
                    t.setFill(color==Color.BLACK?Color.RED:Color.BLACK);
                    mainGroup.getChildren().add(t);
                    t.setId(""+listOfShapes.size());
                    editor.getJson().addString("figura"+listOfShapes.size(),"text");
                    listOfShapes.add(t);
                    dialog.close();

                    t.setOnMouseClicked(event1 -> {
                        if (STANJE== StanjeEditora.BOJENJE){
                            t.setFill((paleta1.getColor()==null)?paleta2.getColor():paleta1.getColor());
                        }
                    });
                    AtomicReference<Point2D> lastEvent= new AtomicReference<>(new Point2D(0, 0));
                        t.setOnMouseDragged(event1 -> {
                        if (t.getBoundsInParent().getMaxY()<heightForGame-5) lastEvent.set(new Point2D(event1.getX(), event1.getY()));
                        resetChoosen();
                        t.setX(event1.getX()-t.getBoundsInLocal().getWidth()/2);
                        t.setY(event1.getY());
                    });
                    t.setOnMouseReleased(event1 -> {
                        if (t.getBoundsInParent().getMaxY()<heightForGame-5) return;
                        t.setX(lastEvent.get().getX()-t.getBoundsInLocal().getWidth()/2);
                        t.setY(lastEvent.get().getY());
                    });
                });
            }
        };
        this.setOnMousePressed(mousePressedHandler);

        this.setOnMouseClicked(event -> {
            if (event.getY()>heightForGame-5 || choosen==null) return;
            if (choosen==polyButton && !event.isShiftDown()){
                    Circle c= new Circle(event.getX(),event.getY(),5);
                    c.setFill(color==Color.RED?Color.BLUE:Color.RED);
                    this.getChildren().add(c);
                    pomeranjeOblika.add(c);
                    Polyline newPoly = new Polyline();
                    for (int i=0;i<pomeranjeOblika.size();i++) newPoly.getPoints().addAll(pomeranjeOblika.get(i).getCenterX(), pomeranjeOblika.get(i).getCenterY());
                    newPoly.setStrokeWidth(3);
                    newPoly.setStroke(Color.BLACK);
                    if (pomeranjeOblika.size()>1) {
                        this.getChildren().remove(listOfShapes.get(listOfShapes.size()-1));
                        listOfShapes.remove(listOfShapes.size()-1);
                    }
                    this.getChildren().add(newPoly);
                    editor.getJson().addString("figura"+(listOfShapes.size()),"pl");
                    editor.getJson().addInt("bA"+(listOfShapes.size()),0);
                    newPoly.setId(""+listOfShapes.size());
                    listOfShapes.add(newPoly);


            }
            else if (choosen==polyButton && event.isShiftDown()){
                Circle c= new Circle(event.getX(),event.getY(),5);
                c.setFill(color==Color.RED?Color.BLUE:Color.RED);
                this.getChildren().add(c);
                pomeranjeOblika.add(c);
                Polygon newPoly = new Polygon();
                for (int i=0;i<pomeranjeOblika.size();i++) newPoly.getPoints().addAll(pomeranjeOblika.get(i).getCenterX(), pomeranjeOblika.get(i).getCenterY());
                newPoly.setStrokeWidth(3);
                newPoly.setFill(color);
                newPoly.setStroke(Color.BLACK);
                if (pomeranjeOblika.size()>1) {
                    this.getChildren().remove(listOfShapes.get(listOfShapes.size()-1));
                    listOfShapes.remove(listOfShapes.size()-1);
                }
                this.getChildren().add(newPoly);
                editor.getJson().addString("figura"+(listOfShapes.size()),"pg");
                editor.getJson().addInt("bA"+(listOfShapes.size()),0);
                newPoly.setId(""+listOfShapes.size());
                listOfShapes.add(newPoly);
            }
        });


        this.setOnMouseDragged(event -> {
           double  endNodeX = event.getX();
           double  endNodeY = event.getY();
           if (event.getY()>heightForGame-5 || choosen==null) return;
            if (choosen==recButton){
                if (this.getChildren().contains(newRectangle[0])){
                    newRectangle[0].getElements().clear();
                    newRectangle[0].getElements().addAll(
                            new MoveTo(startX.get(),startY.get()),
                            new LineTo(startX.get(), endNodeY),
                            new LineTo(endNodeX,endNodeY),
                            new LineTo(endNodeX,startY.get()),
                            new ClosePath()
                    );
                }
                else {
                    newRectangle[0]= new Path();
                    newRectangle[0].setFill(color);
                    newRectangle[0].setStrokeWidth(3);
                    newRectangle[0].setStroke(Color.BLACK);
                    this.getChildren().add(newRectangle[0]);
                }
                addHandler(newRectangle[0]);
                return;
            }

            if (choosen==lineButton){
                if (this.getChildren().contains(newLine[0])){
                    newLine[0].setEndX(endNodeX);
                    newLine[0].setEndY(endNodeY);
                    newLine[0].setStartX(startX.get());
                    newLine[0].setStartY(startY.get());
                }
                else {
                    newLine[0] =new Line();
                    newLine[0].setStrokeWidth(3);
                    this.getChildren().add(newLine[0]);
                }

                addHandler(newLine[0]);
                return;
            }

            if (choosen==ellButton){
                if (this.getChildren().contains(newEllipse[0])){
                    if (event.isShiftDown()){
                        double width=endNodeX-startX.get();
                        double height=endNodeY-startY.get();
                        newEllipse[0].setCenterY(Math.min(endNodeY,startY.get())+Math.abs(endNodeY-startY.get())/2);
                        newEllipse[0].setCenterX(Math.min(endNodeX,startX.get())+Math.abs(endNodeX-startX.get())/2);
                        newEllipse[0].setRadiusY(Math.sqrt(Math.pow(width,2)+Math.pow(height,2))/2);
                        newEllipse[0].setRadiusX(Math.sqrt(Math.pow(width,2)+Math.pow(height,2))/2);
                    }
                    else {
                        double radiusX = Math.abs(endNodeX - startX.get()) / 2;
                        double radiusY = Math.abs(endNodeY - startY.get()) / 2;
                        newEllipse[0].setRadiusX(radiusX);
                        newEllipse[0].setRadiusY(radiusY);
                        newEllipse[0].setCenterX(Math.min(startX.get(), endNodeX) + radiusX);
                        newEllipse[0].setCenterY(Math.min(startY.get(), endNodeY) + radiusY);
                    }
                }
                else {
                    newEllipse[0]= new Ellipse();
                    newEllipse[0].setFill(color);
                    newEllipse[0].setStrokeWidth(3);
                    newEllipse[0].setStroke(Color.BLACK);
                    this.getChildren().add(newEllipse[0]);
                }
                addHandler(newEllipse[0]);
                return;
            }
            if (choosen==curButton){
                if (this.getChildren().contains(newCurve[0])){
                    newCurve[0].setEndX(endNodeX);
                    newCurve[0].setEndY(endNodeY);
                    newCurve[0].setStartX(startX.get());
                    newCurve[0].setStartY(startY.get());
                   if((endNodeX>startX.get() && endNodeY>startY.get()) || (endNodeX<startX.get() && endNodeY<startY.get())) {
                       newCurve[0].setControlX1(Math.min(startX.get(),endNodeX));
                       newCurve[0].setControlY1(Math.max(startY.get(),endNodeY));
                       newCurve[0].setControlX2(Math.max(startX.get(),endNodeX));
                       newCurve[0].setControlY2(Math.min(startY.get(),endNodeY));
                       return;
                   }
                    newCurve[0].setControlX1(Math.max(startX.get(),endNodeX));
                    newCurve[0].setControlY1(Math.max(startY.get(),endNodeY));
                    newCurve[0].setControlX2(Math.min(startX.get(),endNodeX));
                    newCurve[0].setControlY2(Math.min(startY.get(),endNodeY));
                }
                else {
                    newCurve[0] =new CubicCurve();
                    newCurve[0].setFill(null);
                    newCurve[0].setStrokeWidth(3);
                    newCurve[0].setStroke(Color.BLACK);
                    this.getChildren().add(newCurve[0]);
                }
                addHandler(newCurve[0]);
                return;
            }
        });

        this.setOnMouseReleased(event -> {

            if(choosen==null) return;

            if (event.getY()>heightForGame-5 && (newLine[0]!=null || newCurve[0]!=null
                    || newRectangle[0]!=null  || newEllipse[0]!=null )) {
                this.getChildren().remove(this.getChildren().size()-1);
                newLine[0] = null; newCurve[0] = null; newRectangle[0] = null; newEllipse[0] = null;
                refresh();
                return;
            }
            for(int i=0;i<newShapes.length;i++) {
                if (newShapes[i][0]!=null) {
                    newShapes[i][0].setId(""+listOfShapes.size());
                    if (newShapes[i]==newLine) editor.getJson().addString("figura"+listOfShapes.size(),"line");
                    else if (newShapes[i] == newRectangle) editor.getJson().addString("figura"+listOfShapes.size(),"rectangle");
                    else if (newShapes[i] == newEllipse) editor.getJson().addString("figura"+listOfShapes.size(),"ellipse");
                    else if (newShapes[i] == newCurve) editor.getJson().addString("figura"+listOfShapes.size(),"curve");
                    editor.getJson().addInt("bA"+listOfShapes.size(),0);
                    listOfShapes.add(newShapes[i][0]);
                    newShapes[i][0] = null;
                    return;
                }
            }
                });

        alati=new HBox(20, lineButton, polyButton, recButton, ellButton, curButton, txtButton, paintButton, targetButton, doneButton);
        alati.setAlignment(Pos.CENTER);
        alati.setMinSize(WIDTH,HEIGHT-heightForGame);
        alati.getTransforms().add(
                new Translate(0,heightForGame-5)
        );

        paleta1=new PaletaBoja(false);
        paleta2=new PaletaBoja(true);
        paleta1.addAnotherOne(paleta2);
        paleta2.addAnotherOne(paleta1);

        Rectangle backPaint = new Rectangle( 5,5,(endX-5), endX-5);
        Image backImage=new Image("Slike/back.png");
        backPaint.setFill(new ImagePattern(backImage));
        DugmeOdabir backButton = new DugmeOdabir( backPaint, Color.WHITE, color);
        Tooltip tooltipBack= new Tooltip("Nazad");
        tooltipBack.setShowDelay(Duration.millis(50));
        Tooltip.install(backButton,tooltipBack);

        backButton.setOnMousePressed(event -> {
            editor.getStage().getScene().setCursor(Cursor.DEFAULT);
            alati.getChildren().clear();
            alati.getChildren().addAll(lineButton, polyButton, recButton, ellButton, curButton, txtButton, paintButton, targetButton, doneButton);
            STANJE= StanjeEditora.CRTANJE;
        });

        paintButton.setOnMousePressed(event -> {
                if (choosen!=null) resetChoosen();
                editor.getStage().getScene().setCursor(Cursor.HAND);
                this.getChildren().removeAll(pomeranjeOblika);
                pomeranjeOblika.clear();
                alati.getChildren().clear();
                Group g=new Group(paleta1);
                Group g2=new Group(paleta2);
                alati.getChildren().addAll(g, backButton.getDugmeSaPostoljem(), g2);
                paleta2.restart();
                alati.setAlignment(Pos.CENTER);
                STANJE= StanjeEditora.BOJENJE;
        });

        animationButton  = new DugmeOdabir( animationBackground, Color.WHITE, color);
        Tooltip tooltipAnimation= new Tooltip("Animiraj selektovani oblik");
        tooltipAnimation.setShowDelay(Duration.millis(50));
        Tooltip.install(animationButton, tooltipAnimation);

        animationButton.setOnMouseClicked(event -> {

            Rectangle backPaint2 = new Rectangle( 5,5,(endX-5), endX-5);
            Image backImage2=new Image("Slike/back.png");
            backPaint2.setFill(new ImagePattern(backImage2));
            DugmeOdabir backButton2 = new DugmeOdabir( backPaint2, Color.WHITE, color);
            Tooltip tooltipBack2= new Tooltip("Nazad");
            tooltipBack2.setShowDelay(Duration.millis(50));
            Tooltip.install(backButton2,tooltipBack2);

            backButton2.setOnMouseClicked(event1 -> {
                STANJE= StanjeEditora.CRTANJE;
                this.getChildren().remove(selectedNode);
                for (int i=0; i<listOfShapes.size(); i++ ) this.getChildren().add(listOfShapes.get(i));
                alati.getChildren().clear();
                alati.getChildren().addAll(lineButton, polyButton, recButton, ellButton, curButton, txtButton, paintButton,targetButton, doneButton);
                selectedNode=null;
                this.setEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedHandler);
            });

                STANJE= StanjeEditora.ANIMACIJA;
                this.removeEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedHandler);
                for (int i=0; i<listOfShapes.size(); i++ ) this.getChildren().remove(listOfShapes.get(i));
                for (int i=0; i<pomeranjeOblika.size(); i++ ) this.getChildren().remove(pomeranjeOblika.get(i));
                pomeranjeOblika.clear();
                this.getChildren().add(selectedNode);
                alati.getChildren().clear();
                new AnimirajOblikMeni(this, editor, heightForGame, color, alati, endX-5, backButton2);

        });

        targetButton.setOnMouseClicked(event -> {
            Rectangle backPaint2 = new Rectangle( 5,5,(endX-5), endX-5);
            Image backImage2=new Image("Slike/back.png");
            backPaint2.setFill(new ImagePattern(backImage2));
            DugmeOdabir backButton2 = new DugmeOdabir( backPaint2, Color.WHITE, color);
            Tooltip tooltipBack2= new Tooltip("Nazad");
            tooltipBack2.setShowDelay(Duration.millis(50));
            Tooltip.install(backButton2,tooltipBack2);

            backButton2.addEventHandler(MouseEvent.MOUSE_RELEASED,event1 -> {
                STANJE= StanjeEditora.CRTANJE;
                this.getChildren().clear();
                this.getChildren().addAll(BACKGROUND1, BACKGROUND2, alati);
                for (int i=0; i<listOfShapes.size(); i++) this.getChildren().add(listOfShapes.get(i));
                alati.getChildren().clear();
                alati.getChildren().addAll(lineButton, polyButton, recButton, ellButton, curButton, txtButton, paintButton,targetButton, doneButton);
                selectedNode=null;
                this.setEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedHandler);
            });

                STANJE= StanjeEditora.PRAVLJENJEMETE;
                this.removeEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedHandler);
                for (int i=0; i<listOfShapes.size(); i++ ) this.getChildren().remove(listOfShapes.get(i));
                for (int i=0; i<pomeranjeOblika.size(); i++ ) this.getChildren().remove(pomeranjeOblika.get(i));
                pomeranjeOblika.clear();
                alati.getChildren().clear();
                new DodajMeteMeni(this, editor, WIDTH, heightForGame, color, alati, endX-5, backButton2);

        });
        this.getChildren().add(alati);
        }

    private void addHandler(Line line){
        line.setOnMouseClicked(event -> {
            if (STANJE== StanjeEditora.ANIMACIJA) return;
            if (STANJE== StanjeEditora.BOJENJE){
                line.setStroke((paleta1.getColor()==null)?paleta2.getColor():paleta1.getColor());
                return;
            }
            this.getChildren().removeAll(pomeranjeOblika);
            pomeranjeOblika.clear();
            refresh();

            if (selectedNode==line){
                selectedNode=null;
                deleteAnimationOption();
            }
            else{
                setAnimationOption();
                Circle startDot=helpCircle(line.getStartX(),line.getStartY(), color);
                Circle endDot=helpCircle(line.getEndX(),line.getEndY(), color);
                Circle moveLineDot = helpCircle((line.getStartX()+line.getEndX())/2,(line.getStartY()+line.getEndY())/2, color);
                selectedNode = line;
                resetChoosen();

                AtomicReference<Point2D> lastEvent= new AtomicReference<>(new Point2D(0, 0));

                startDot.setOnMouseDragged(event1 -> {
                    if (line.getBoundsInParent().getMinX()>1 && line.getBoundsInParent().getMaxX()<WIDTH-1 &&
                            line.getBoundsInParent().getMinY()>1 && line.getBoundsInParent().getMaxY()<heightForGame-6) {
                        lastEvent.set(new Point2D(event1.getSceneX(), event1.getSceneY()));
                    }
                line.setStartX(event1.getX());
                line.setStartY(event1.getY());
                startDot.setCenterX(event1.getX());
                startDot.setCenterY(event1.getY());
                moveLineDot.setCenterX((line.getStartX()+line.getEndX())/2);
                moveLineDot.setCenterY((line.getStartY()+line.getEndY())/2);
            });
                startDot.setOnMouseReleased(event1 -> {
                    if (line.getBoundsInParent().getMinX()>1 && line.getBoundsInParent().getMaxX()<WIDTH-1 &&
                            line.getBoundsInParent().getMinY()>1 && line.getBoundsInParent().getMaxY()<heightForGame-6) return;
                    line.setStartX(lastEvent.get().getX());
                    line.setStartY(lastEvent.get().getY());
                    startDot.setCenterX(lastEvent.get().getX());
                    startDot.setCenterY(lastEvent.get().getY());
                    moveLineDot.setCenterX((line.getStartX()+line.getEndX())/2);
                    moveLineDot.setCenterY((line.getStartY()+line.getEndY())/2);
                });
            endDot.setOnMouseDragged(event1 -> {
                if (line.getBoundsInParent().getMinX()>1 && line.getBoundsInParent().getMaxX()<WIDTH-1 &&
                        line.getBoundsInParent().getMinY()>1 && line.getBoundsInParent().getMaxY()<heightForGame-6) {
                    lastEvent.set(new Point2D(event1.getSceneX(), event1.getSceneY()));
                }
                line.setEndX(event1.getX());
                line.setEndY(event1.getY());
                endDot.setCenterX(event1.getX());
                endDot.setCenterY(event1.getY());
                moveLineDot.setCenterX((line.getStartX()+line.getEndX())/2);
                moveLineDot.setCenterY((line.getStartY()+line.getEndY())/2);
            });
                endDot.setOnMouseReleased(event1 -> {
                    if (line.getBoundsInParent().getMinX()>1 && line.getBoundsInParent().getMaxX()<WIDTH-1 &&
                            line.getBoundsInParent().getMinY()>1 && line.getBoundsInParent().getMaxY()<heightForGame-6) return;
                    line.setEndX(lastEvent.get().getX());
                    line.setEndY(lastEvent.get().getY());
                    endDot.setCenterX(lastEvent.get().getX());
                    endDot.setCenterY(lastEvent.get().getY());
                    moveLineDot.setCenterX((line.getStartX()+line.getEndX())/2);
                    moveLineDot.setCenterY((line.getStartY()+line.getEndY())/2);
                });
                moveLineDot.setOnMouseDragged(event1 -> {
                    if (line.getBoundsInParent().getMinX()>1 && line.getBoundsInParent().getMaxX()<WIDTH-1 &&
                            line.getBoundsInParent().getMinY()>1 && line.getBoundsInParent().getMaxY()<heightForGame-6) {
                        lastEvent.set(new Point2D(event1.getSceneX(), event1.getSceneY()));
                    }
                    double addX=event1.getX()-moveLineDot.getCenterX();
                    double addY=event1.getY()-moveLineDot.getCenterY();
                    double startX = line.getStartX()+addX;
                    double startY = line.getStartY()+addY;
                    double endX = line.getEndX()+addX;
                    double endY = line.getEndY()+addY;
                    line.setStartX(startX);
                    line.setStartY(startY);
                    line.setEndX(endX);
                    line.setEndY(endY);
                    startDot.setCenterX(startX);
                    startDot.setCenterY(startY);
                    endDot.setCenterX(endX);
                    endDot.setCenterY(endY);
                    moveLineDot.setCenterX(event1.getX());
                    moveLineDot.setCenterY(event1.getY());
                });
                moveLineDot.setOnMouseReleased(event1 -> {
                    if (line.getBoundsInParent().getMinX()>1 && line.getBoundsInParent().getMaxX()<WIDTH-1 &&
                            line.getBoundsInParent().getMinY()>1 && line.getBoundsInParent().getMaxY()<heightForGame-6) return;
                    double addX=lastEvent.get().getX()-moveLineDot.getCenterX();
                    double addY=lastEvent.get().getY()-moveLineDot.getCenterY();
                    double startX = line.getStartX()+addX;
                    double startY = line.getStartY()+addY;
                    double endX = line.getEndX()+addX;
                    double endY = line.getEndY()+addY;
                    line.setStartX(startX);
                    line.setStartY(startY);
                    line.setEndX(endX);
                    line.setEndY(endY);
                    startDot.setCenterX(startX);
                    startDot.setCenterY(startY);
                    endDot.setCenterX(endX);
                    endDot.setCenterY(endY);
                    moveLineDot.setCenterX(lastEvent.get().getX());
                    moveLineDot.setCenterY(lastEvent.get().getY());
                });
            }
        });
        }

    private void addHandler(CubicCurve line){

        line.setOnMouseClicked(event -> {
            if (STANJE== StanjeEditora.ANIMACIJA) return;
            if (STANJE== StanjeEditora.BOJENJE){
                line.setStroke((paleta1.getColor()==null)?paleta2.getColor():paleta1.getColor());
                return;
            }
            this.getChildren().removeAll(pomeranjeOblika);
            pomeranjeOblika.clear();
            refresh();

            if (selectedNode==line){
                selectedNode=null;
                deleteAnimationOption();
            }
            else{
                setAnimationOption();
                Circle startDot=helpCircle(line.getStartX(),line.getStartY(),color);
                Circle endDot=helpCircle(line.getEndX(),line.getEndY(),color);
                Point2D d2P= BezjeovaKriva.findMiddle(line);
                Circle middleDot= helpCircle(d2P.getX(),d2P.getY(),color);
                Circle control1Dot = helpCircle(line.getControlX1(), line.getControlY1(),color);
                Circle control2Dot = helpCircle(line.getControlX2(), line.getControlY2(),color);
                selectedNode=line;
                resetChoosen();
                AtomicReference<Point2D> lastEvent= new AtomicReference<>(new Point2D(0, 0));

                startDot.setOnMouseDragged(event1 -> {
                    if (line.getBoundsInParent().getMinX()>1 && line.getBoundsInParent().getMaxX()<WIDTH-1 &&
                            line.getBoundsInParent().getMinY()>1 && line.getBoundsInParent().getMaxY()<heightForGame-6) {
                        lastEvent.set(new Point2D(event1.getSceneX(), event1.getSceneY()));
                    }
                    line.setStartX(event1.getX());
                    line.setStartY(event1.getY());
                    startDot.setCenterX(event1.getX());
                    startDot.setCenterY(event1.getY());
                    Point2D point= BezjeovaKriva.findMiddle(line);
                    middleDot.setCenterX(point.getX());
                    middleDot.setCenterY(point.getY());
                });
                startDot.setOnMouseReleased(event1 -> {
                    if (line.getBoundsInParent().getMinX()>1 && line.getBoundsInParent().getMaxX()<WIDTH-1 &&
                            line.getBoundsInParent().getMinY()>1 && line.getBoundsInParent().getMaxY()<heightForGame-6) return;
                    line.setStartX(lastEvent.get().getX());
                    line.setStartY(lastEvent.get().getY());
                    startDot.setCenterX(lastEvent.get().getX());
                    startDot.setCenterY(lastEvent.get().getY());
                    Point2D point= BezjeovaKriva.findMiddle(line);
                    middleDot.setCenterX(point.getX());
                    middleDot.setCenterY(point.getY());
                });

                endDot.setOnMouseDragged(event1 -> {
                    if (line.getBoundsInParent().getMinX()>1 && line.getBoundsInParent().getMaxX()<WIDTH-1 &&
                            line.getBoundsInParent().getMinY()>1 && line.getBoundsInParent().getMaxY()<heightForGame-6) {
                        lastEvent.set(new Point2D(event1.getSceneX(), event1.getSceneY()));
                    }
                    line.setEndX(event1.getX());
                    line.setEndY(event1.getY());
                    endDot.setCenterX(event1.getX());
                    endDot.setCenterY(event1.getY());
                    Point2D point= BezjeovaKriva.findMiddle(line);
                    middleDot.setCenterX(point.getX());
                    middleDot.setCenterY(point.getY());
                });
                endDot.setOnMouseReleased(event1 -> {
                    if (line.getBoundsInParent().getMinX()>1 && line.getBoundsInParent().getMaxX()<WIDTH-1 &&
                            line.getBoundsInParent().getMinY()>1 && line.getBoundsInParent().getMaxY()<heightForGame-6) return;
                    line.setEndX(lastEvent.get().getX());
                    line.setEndY(lastEvent.get().getY());
                    endDot.setCenterX(lastEvent.get().getX());
                    endDot.setCenterY(lastEvent.get().getY());
                    Point2D point= BezjeovaKriva.findMiddle(line);
                    middleDot.setCenterX(point.getX());
                    middleDot.setCenterY(point.getY());
                });
                middleDot.setOnMouseDragged(event1 -> {
                    if (line.getBoundsInParent().getMinX()>1 && line.getBoundsInParent().getMaxX()<WIDTH-1 &&
                            line.getBoundsInParent().getMinY()>1 && line.getBoundsInParent().getMaxY()<heightForGame-6) {
                        lastEvent.set(new Point2D(event1.getSceneX(), event1.getSceneY()));
                    }
                    double addX=event1.getX()-middleDot.getCenterX();
                    double addY=event1.getY()-middleDot.getCenterY();
                    double startX = line.getStartX()+addX;
                    double startY = line.getStartY()+addY;
                    double endX = line.getEndX()+addX;
                    double endY = line.getEndY()+addY;
                    line.setControlX1(line.getControlX1()+addX);
                    line.setControlY1(line.getControlY1()+addY);
                    line.setControlX2(line.getControlX2()+addX);
                    line.setControlY2(line.getControlY2()+addY);
                    control1Dot.setCenterX(line.getControlX1());
                    control1Dot.setCenterY(line.getControlY1());
                    control2Dot.setCenterX(line.getControlX2());
                    control2Dot.setCenterY(line.getControlY2());
                    line.setStartX(startX);
                    line.setStartY(startY);
                    line.setEndX(endX);
                    line.setEndY(endY);
                    startDot.setCenterX(startX);
                    startDot.setCenterY(startY);
                    endDot.setCenterX(endX);
                    endDot.setCenterY(endY);
                    Point2D point= BezjeovaKriva.findMiddle(line);
                    middleDot.setCenterX(point.getX());
                    middleDot.setCenterY(point.getY());
                });

                middleDot.setOnMouseReleased(event1 -> {
                    if (line.getBoundsInParent().getMinX()>1 && line.getBoundsInParent().getMaxX()<WIDTH-1 &&
                            line.getBoundsInParent().getMinY()>1 && line.getBoundsInParent().getMaxY()<heightForGame-6)  return;
                    double addX=lastEvent.get().getX()-middleDot.getCenterX();
                    double addY=lastEvent.get().getY()-middleDot.getCenterY();
                    double startX = line.getStartX()+addX;
                    double startY = line.getStartY()+addY;
                    double endX = line.getEndX()+addX;
                    double endY = line.getEndY()+addY;
                    line.setControlX1(line.getControlX1()+addX);
                    line.setControlY1(line.getControlY1()+addY);
                    line.setControlX2(line.getControlX2()+addX);
                    line.setControlY2(line.getControlY2()+addY);
                    control1Dot.setCenterX(line.getControlX1());
                    control1Dot.setCenterY(line.getControlY1());
                    control2Dot.setCenterX(line.getControlX2());
                    control2Dot.setCenterY(line.getControlY2());
                    line.setStartX(startX);
                    line.setStartY(startY);
                    line.setEndX(endX);
                    line.setEndY(endY);
                    startDot.setCenterX(startX);
                    startDot.setCenterY(startY);
                    endDot.setCenterX(endX);
                    endDot.setCenterY(endY);
                    Point2D point= BezjeovaKriva.findMiddle(line);
                    middleDot.setCenterX(point.getX());
                    middleDot.setCenterY(point.getY());
                });

                control1Dot.setOnMouseDragged(event1 -> {
                    control1Dot.setCenterX(event1.getX());
                    control1Dot.setCenterY(event1.getY());
                    line.setControlX1(event1.getX());
                    line.setControlY1(event1.getY());
                    Point2D point= BezjeovaKriva.findMiddle(line);
                    middleDot.setCenterX(point.getX());
                    middleDot.setCenterY(point.getY());
                });
                control2Dot.setOnMouseDragged(event1 -> {
                    control2Dot.setCenterX(event1.getX());
                    control2Dot.setCenterY(event1.getY());
                    line.setControlX2(event1.getX());
                    line.setControlY2(event1.getY());
                    Point2D point= BezjeovaKriva.findMiddle(line);
                    middleDot.setCenterX(point.getX());
                    middleDot.setCenterY(point.getY());
                });
            }
        });
    }

    private void addHandler(Ellipse ellipse){
        ellipse.setOnMouseClicked(event -> {
            if (STANJE== StanjeEditora.ANIMACIJA) return;
            if (STANJE== StanjeEditora.BOJENJE){
                if (event.isControlDown()) ellipse.setStroke((paleta1.getColor()==null)?paleta2.getColor():paleta1.getColor());
                else ellipse.setFill((paleta1.getColor()==null)?paleta2.getColor():paleta1.getColor());
                return;
            }
            this.getChildren().removeAll(pomeranjeOblika);
            pomeranjeOblika.clear();
            refresh();
            if (ellipse==selectedNode){
                selectedNode=null;
                deleteAnimationOption();
            }
            else{
                setAnimationOption();
                Circle center=helpCircle(ellipse.getCenterX(), ellipse.getCenterY(),(Color)ellipse.getFill());
                selectedNode=ellipse;
                resetChoosen();

                AtomicReference<Point2D> lastEvent= new AtomicReference<>(new Point2D(center.getCenterX(), center.getCenterY()));

                center.setOnMouseDragged(event1 -> {
                    if (ellipse.getBoundsInParent().getMinX()>1 && ellipse.getBoundsInParent().getMaxX()<WIDTH-1 &&
                            ellipse.getBoundsInParent().getMinY()>1 && ellipse.getBoundsInParent().getMaxY()<heightForGame-6) {
                        lastEvent.set(new Point2D(event1.getSceneX(), event1.getSceneY()));
                    }
                    ellipse.setCenterX(event1.getX());
                    ellipse.setCenterY(event1.getY());
                    center.setCenterX(event1.getX());
                    center.setCenterY(event1.getY());
                });

                center.setOnMouseReleased(event1 -> {
                    if (ellipse.getBoundsInParent().getMinX()>1 && ellipse.getBoundsInParent().getMaxX()<WIDTH-1 &&
                            ellipse.getBoundsInParent().getMinY()>1 && ellipse.getBoundsInParent().getMaxY()<heightForGame-6) return;
                    ellipse.setCenterX(lastEvent.get().getX());
                    ellipse.setCenterY(lastEvent.get().getY());
                    center.setCenterX(lastEvent.get().getX());
                    center.setCenterY(lastEvent.get().getY());
                });
            }
        });
    }

    private void addHandler(Path rectangle) {
        rectangle.setOnMouseClicked(event -> {

            if (STANJE== StanjeEditora.ANIMACIJA) return;
            if (STANJE== StanjeEditora.BOJENJE){
                if (event.isControlDown()) rectangle.setStroke((paleta1.getColor()==null)?paleta2.getColor():paleta1.getColor());
                else rectangle.setFill((paleta1.getColor()==null)?paleta2.getColor():paleta1.getColor());
                return;
            }
            this.getChildren().removeAll(pomeranjeOblika);
            pomeranjeOblika.clear();
            refresh();
            if (rectangle==selectedNode) {
                selectedNode=null;
                deleteAnimationOption();
            }
            else {
                setAnimationOption();
                Circle middleDot = helpCircle( 0,0, (Color)rectangle.getFill());
                selectedNode=rectangle;
                resetChoosen();
                MoveTo m=null;
                LineTo l=null;
                middleDot.setCenterX((rectangle.getBoundsInParent().getMaxX()+rectangle.getBoundsInParent().getMinX())/2);
                middleDot.setCenterY((rectangle.getBoundsInParent().getMaxY()+rectangle.getBoundsInParent().getMinY())/2);

                for (int i=1;i<5;i++){
                    if (i==1) m = (MoveTo) rectangle.getElements().get(0);
                    else l=(LineTo) rectangle.getElements().get(i-1);
                    AtomicReference<Point2D> lastEvent= new AtomicReference<>(new Point2D(middleDot.getCenterX(), middleDot.getCenterY()));
                    Circle c= helpCircle((i==1)?(m.getX()+rectangle.getLayoutX()):l.getX()+rectangle.getLayoutX(), (i==1)?(m.getY()+rectangle.getLayoutY()):l.getY()+rectangle.getLayoutY(),color);
                    c.setOnMouseDragged(event1 -> {
                        if (event1.getX()>1 && event1.getX()<WIDTH-1 &&
                                event1.getY()>1 && event1.getY()<=heightForGame-6) {
                            lastEvent.set(new Point2D(event1.getSceneX(), event1.getSceneY()));
                        }
                        c.setCenterX(event1.getX());
                        c.setCenterY(event1.getY());
                        double x =rectangle.getLayoutX();
                        double y= rectangle.getLayoutY();
                        rectangle.getElements().clear();
                        rectangle.getElements().addAll(
                                new MoveTo(pomeranjeOblika.get(1).getCenterX()-x,pomeranjeOblika.get(1).getCenterY()-y) ,
                                new LineTo(pomeranjeOblika.get(2).getCenterX()-x,pomeranjeOblika.get(2).getCenterY()-y) ,
                                new LineTo(pomeranjeOblika.get(3).getCenterX()-x,pomeranjeOblika.get(3).getCenterY()-y) ,
                                new LineTo(pomeranjeOblika.get(4).getCenterX()-x,pomeranjeOblika.get(4).getCenterY()-y) ,
                                new ClosePath()
                        );
                        middleDot.setCenterX((rectangle.getBoundsInParent().getMaxX()+rectangle.getBoundsInParent().getMinX())/2);
                        middleDot.setCenterY((rectangle.getBoundsInParent().getMaxY()+rectangle.getBoundsInParent().getMinY())/2);
                    });

                    c.setOnMouseReleased(event1 -> {
                        if (event1.getX()>1 && event1.getX()<WIDTH-1 &&
                                event1.getY()>1 && event1.getY()<=heightForGame-6) return;
                        c.setCenterX(lastEvent.get().getX());
                        c.setCenterY(lastEvent.get().getY());
                        double x =rectangle.getLayoutX();
                        double y= rectangle.getLayoutY();
                        rectangle.getElements().clear();
                        rectangle.getElements().addAll(
                                new MoveTo(pomeranjeOblika.get(1).getCenterX()-x,pomeranjeOblika.get(1).getCenterY()-y) ,
                                new LineTo(pomeranjeOblika.get(2).getCenterX()-x,pomeranjeOblika.get(2).getCenterY()-y) ,
                                new LineTo(pomeranjeOblika.get(3).getCenterX()-x,pomeranjeOblika.get(3).getCenterY()-y) ,
                                new LineTo(pomeranjeOblika.get(4).getCenterX()-x,pomeranjeOblika.get(4).getCenterY()-y) ,
                                new ClosePath()
                        );
                        middleDot.setCenterX((rectangle.getBoundsInParent().getMaxX()+rectangle.getBoundsInParent().getMinX())/2);
                        middleDot.setCenterY((rectangle.getBoundsInParent().getMaxY()+rectangle.getBoundsInParent().getMinY())/2);
                    });
                }

                AtomicReference<Point2D> lastEvent= new AtomicReference<>(new Point2D(middleDot.getCenterX(), middleDot.getCenterY()));

                middleDot.setOnMouseDragged(event1 -> {
                    if (rectangle.getBoundsInParent().getMinX()>1 && rectangle.getBoundsInParent().getMaxX()<WIDTH-1 &&
                            rectangle.getBoundsInParent().getMinY()>1 && rectangle.getBoundsInParent().getMaxY()<heightForGame-6) {
                        lastEvent.set(new Point2D(event1.getSceneX(), event1.getSceneY()));
                    }
                    double deltaX = event1.getSceneX() - middleDot.getCenterX();
                    double deltaY = event1.getSceneY() - middleDot.getCenterY();
                    rectangle.setLayoutX(rectangle.getLayoutX()+deltaX);
                    rectangle.setLayoutY(rectangle.getLayoutY()+deltaY);
                    middleDot.setCenterX(event1.getX());
                    middleDot.setCenterY(event1.getY());
                    for (int i=1; i<5; i++){
                        pomeranjeOblika.get(i).setCenterX(pomeranjeOblika.get(i).getCenterX()+deltaX);
                        pomeranjeOblika.get(i).setCenterY(pomeranjeOblika.get(i).getCenterY()+deltaY);
                    }
                });
                middleDot.addEventHandler(MouseEvent.MOUSE_RELEASED, event13 -> {
                    if (rectangle.getBoundsInParent().getMinX()>1 && rectangle.getBoundsInParent().getMaxX()<WIDTH-1 &&
                            rectangle.getBoundsInParent().getMinY()>1 && rectangle.getBoundsInParent().getMaxY()<heightForGame-6) return;

                    double deltaX = lastEvent.get().getX() - middleDot.getCenterX();
                    double deltaY = lastEvent.get().getY() - middleDot.getCenterY();
                    rectangle.setLayoutX(rectangle.getLayoutX()+deltaX);
                    rectangle.setLayoutY(rectangle.getLayoutY()+deltaY);
                    middleDot.setCenterX(lastEvent.get().getX());
                    middleDot.setCenterY(lastEvent.get().getY());
                    for (int i=1; i<5; i++){
                        pomeranjeOblika.get(i).setCenterX(pomeranjeOblika.get(i).getCenterX()+deltaX);
                        pomeranjeOblika.get(i).setCenterY(pomeranjeOblika.get(i).getCenterY()+deltaY);
                    }
                });

                resetChoosen();
            }
        });
    }

    private void refresh(){
        Group g=new Group();
        g.getChildren().addAll(this.getChildren());
        this.getChildren().clear();
        this.getChildren().addAll(g.getChildren());
    }

    public void undo(){
        if (listOfShapes.isEmpty() || STANJE== StanjeEditora.BOJENJE || STANJE== StanjeEditora.ANIMACIJA) return;
        this.getChildren().removeAll(pomeranjeOblika);
        pomeranjeOblika.clear();
        selectedNode=null;
        deleteAnimationOption();
        Node removeObject = listOfShapes.get(listOfShapes.size()-1);
        this.getChildren().remove(removeObject);
        refresh();
        listOfShapes.remove(removeObject);
        redo.add(0,removeObject);
    }

    public void redo(){
        if (listOfShapes.isEmpty() || STANJE== StanjeEditora.BOJENJE || STANJE== StanjeEditora.ANIMACIJA || redo.isEmpty()) return;
        System.out.println(redo.get(0).getId());
        if (Integer.valueOf(redo.get(0).getId())!=listOfShapes.size()){
            redo.clear();
            return;
        }
        listOfShapes.add(redo.get(0));
        this.getChildren().add(redo.remove(0));
        this.getChildren().removeAll(pomeranjeOblika);
        pomeranjeOblika.clear();
        selectedNode=null;
        deleteAnimationOption();
    }

    public void setAnimationOption(){
        if(!alati.getChildren().contains(animationButton))  alati.getChildren().set(alati.getChildren().size()-2, animationButton);
    }

    public void deleteAnimationOption(){
        if(!alati.getChildren().contains(targetButton))  alati.getChildren().set(alati.getChildren().size()-2,targetButton);
    }

    private void addDragHandlerPoly(){
        if (choosen==polyButton){
            if (!listOfShapes.isEmpty() && (listOfShapes.get(listOfShapes.size()-1) instanceof Polygon || listOfShapes.get(listOfShapes.size()-1) instanceof Polyline)){

                Node newPoly= listOfShapes.get(listOfShapes.size()-1);
                newPoly.setOnMouseClicked(event -> {
                            if (STANJE == StanjeEditora.ANIMACIJA) return;
                            if (STANJE == StanjeEditora.BOJENJE) {
                                if (newPoly instanceof Polyline)
                                    ((Polyline) newPoly).setStroke((paleta1.getColor() == null) ? paleta2.getColor() : paleta1.getColor());
                                else if (event.isControlDown()) ((Polygon) newPoly).setStroke((paleta1.getColor() == null) ? paleta2.getColor() : paleta1.getColor());
                                else ((Polygon) newPoly).setFill((paleta1.getColor() == null) ? paleta2.getColor() : paleta1.getColor());
                                return;
                            }
                            this.getChildren().removeAll(pomeranjeOblika);
                            pomeranjeOblika.clear();
                            refresh();

                            if (newPoly == selectedNode) {
                                selectedNode = null;
                                deleteAnimationOption();
                            } else {
                                setAnimationOption();
                                Circle dot = helpCircle((newPoly.getBoundsInParent().getMaxX() + newPoly.getBoundsInParent().getMinX()) / 2, (newPoly.getBoundsInParent().getMaxY() + newPoly.getBoundsInParent().getMinY()) / 2, (newPoly instanceof Polyline)? color:(Color)((Polygon)newPoly).getFill());
                                resetChoosen();
                                selectedNode = newPoly;
                                AtomicReference<Point2D> lastEvent= new AtomicReference<>(new Point2D(0, 0));

                                  dot.setOnMouseDragged(event13 -> {
                                    if (newPoly.getBoundsInParent().getMinX()>0 && newPoly.getBoundsInParent().getMaxX()<WIDTH &&
                                        newPoly.getBoundsInParent().getMinY()>0 && newPoly.getBoundsInParent().getMaxY()<heightForGame-5) {
                                        lastEvent.set(new Point2D(event13.getSceneX(), event13.getSceneY()));
                                    }
                                    double deltaX = event13.getSceneX() - dot.getCenterX();
                                    double deltaY = event13.getSceneY() - dot.getCenterY();
                                    newPoly.setLayoutX(newPoly.getLayoutX()+deltaX);
                                    newPoly.setLayoutY(newPoly.getLayoutY()+deltaY);
                                    dot.setCenterX(event13.getX());  dot.setCenterY(event13.getY());
                                });

                                dot.setOnMouseReleased(event13 -> {
                                    if (newPoly.getBoundsInParent().getMinX()>0 && newPoly.getBoundsInParent().getMaxX()<WIDTH &&
                                            newPoly.getBoundsInParent().getMinY()>0 && newPoly.getBoundsInParent().getMaxY()<heightForGame-5) return;
                                    double deltaX = lastEvent.get().getX() - dot.getCenterX();
                                    double deltaY = lastEvent.get().getY()  - dot.getCenterY();
                                    newPoly.setLayoutX(newPoly.getLayoutX()+deltaX);
                                    newPoly.setLayoutY(newPoly.getLayoutY()+deltaY);
                                    dot.setCenterX(lastEvent.get().getX());  dot.setCenterY(lastEvent.get().getY());
                                });

                            }
                        });
            }

        }
    }

    private Circle helpCircle(double x, double y, Color color){
        Circle c=new Circle(x, y,5);
        c.setFill((color == Color.RED) ? Color.BLUE : Color.RED);
        this.getChildren().add(c);
        pomeranjeOblika.add(c);
        return c;
    }

    private void resetChoosen(){
        addDragHandlerPoly();
        if (choosen!=null) choosen.restart();
        choosen=null;
    }
    }

