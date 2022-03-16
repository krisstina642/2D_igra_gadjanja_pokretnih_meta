package EditorStreljane.Meni;

import EditorStreljane.tipTacke;
import EditorStreljane.Editor;
import Komponente.Dugme;
import Komponente.DugmeOdabir;
import Komponente.Meta;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import PomocneKlase.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class DodajMeteMeni{

    ArrayList<TezinaNivoa> listaMeta=new ArrayList<>();
    ArrayList<Node> listaPutanja=new ArrayList<>();
    ArrayList<TezinaNivoa> brzinaKretanja = new ArrayList<>();
    ArrayList<Circle> pomeranjeOblika=new ArrayList<>();
    ArrayList<EventHandler<MouseEvent>> handlers = new ArrayList<>();
    ArrayList<EventType<MouseEvent>> eventTypes = new ArrayList<>();

    TezinaNivoa newMeta;
    Node newPutanja;
    Group group;
    Editor editor;
    double buttonSize;

    double heightForGame;
    double width;
    DugmeOdabir doneButton;
    DugmeOdabir targetButton;
    HBox alati;
    Color color;

    private void addEventHandler(EventType<MouseEvent> type, EventHandler<MouseEvent> handler){
        eventTypes.add(type); handlers.add(handler); group.addEventHandler(type,handler);
    }

    private void removeEventHandler(EventHandler<MouseEvent> handler){
        int i=0;
        while (i < handlers.size()){
            if (handlers.get(i)==handler){
                group.removeEventHandler(eventTypes.get(i), handlers.get(i));
                handlers.remove(i);
                eventTypes.remove(i);
            }
            else i++;
        }
    }
    private void removeAllEventHandlers(){
        while (handlers.size()>0){
                group.removeEventHandler(eventTypes.get(0), handlers.get(0));
                handlers.remove(0);
                eventTypes.remove(0);
            }
    }

    DodajMeteMeni(Group group, Editor editor, double WIDTH, double HEIGHT, Color color, HBox alati, double buttonSize, DugmeOdabir exitButton){
        this.group=group;
        this.editor = editor;
        this.buttonSize=buttonSize;
        this.heightForGame=HEIGHT;
        this.width=WIDTH;
        this.alati=alati;
        this.color=color;

        Meta target =new Meta(TezinaNivoa.EASY, Boja.getBoja(), buttonSize/2, null);
        targetButton = new DugmeOdabir( target, Color.WHITE, color);
        Tooltip tooltipTarget= new Tooltip("Ubaci novu metu u igru");
        tooltipTarget.setShowDelay(Duration.millis(50));
        Tooltip.install(targetButton,tooltipTarget);

        Rectangle doneBackground = new Rectangle( 5,5,buttonSize*1.3, buttonSize);
        Image doneImg=new Image("Slike/done.png");
        doneBackground.setFill(new ImagePattern(doneImg));
        doneButton = new DugmeOdabir( doneBackground, Color.WHITE, color);
        Tooltip tooltipDone= new Tooltip("Zavrsi sa izmenama");
        tooltipDone.setShowDelay(Duration.millis(50));
        Tooltip.install(doneButton,tooltipDone);

        doneButton.setOnMousePressed(event -> {
            removeAllEventHandlers();

            for (int i=0; i< pomeranjeOblika.size(); i++) {
                group.getChildren().remove(pomeranjeOblika.get(i));
            }
            group.getChildren().remove(newPutanja);
            pomeranjeOblika.clear();
            alati.getChildren().remove(doneButton);
            alati.getChildren().add(targetButton);

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(editor.getStage());
            dialog.setResizable(false);

            Text tt=new Text(" Brzina Kretanja Mete ");
            tt.setFont(Font.font(15));
            Dugme buttonEasy = new Dugme( " Sporo ",100 );
            Dugme buttonMedium = new Dugme( " Srednje ",100 );
            Dugme buttonHard = new Dugme( " Brzo ",100 );
            VBox box=new VBox(10, tt, buttonEasy.getDugmeSaPostoljem(), buttonMedium.getDugmeSaPostoljem(), buttonHard.getDugmeSaPostoljem());
            box.setMinHeight(200);
            box.setAlignment(Pos.CENTER);
            box.setMinWidth(220);
            box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,new CornerRadii(2), null, new Insets(-5))));

            Scene dialogScene = new Scene(box);
            dialog.setScene(dialogScene);
            dialog.show();

            buttonEasy.setOnMouseClicked(event1 -> {  dialog.close(); brzinaKretanja.add(TezinaNivoa.EASY); dodajMetuUJSON(); });
            buttonMedium.setOnMouseClicked(event1 -> {  dialog.close(); brzinaKretanja.add(TezinaNivoa.MEDIUM); dodajMetuUJSON();});
            buttonHard.setOnMouseClicked(event1 -> {  dialog.close(); brzinaKretanja.add(TezinaNivoa.HARD); dodajMetuUJSON();});

        });

        alati.getChildren().addAll(exitButton, targetButton);
        exitButton.setOnMouseReleased(event -> {
            for (int i=0;i<handlers.size();i++){
                removeAllEventHandlers();
            }
            group.getChildren().removeAll();
            if (listaMeta.size()>0){
                Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(editor.getStage());
                dialog.setResizable(false);

                Text t1=new Text(" Dodali ste "+listaMeta.size()+((listaMeta.size()==1)?" metu. ":(listaMeta.size()<5)?" mete. ": " meta. "));
                t1.setFont(Font.font(15));
                Text t2=new Text(" Želite li da se sve mete pojave u istom(početnom) trenutku ");
                t2.setFont(Font.font(15));
                Text t3=new Text(" ili da se pojavljuju jedna za drugom u razmaku od 1 sekunde? ");
                t3.setFont(Font.font(15));

                Dugme same = new Dugme(" Sve u početnom trenutku ",150);
                Dugme auto = new Dugme(" Jedna za drugom ",150);

                VBox box=new VBox(10, t1, t2, t3, same.getDugmeSaPostoljem(), auto.getDugmeSaPostoljem());
                box.setMinSize(450,220);
                box.setAlignment(Pos.CENTER);
                box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,new CornerRadii(2), null, new Insets(-5))));

                Scene dialogScene = new Scene(box);
                dialog.setScene(dialogScene);
                dialog.show();
                dialog.setOnCloseRequest(event1 ->dodajDijalogZaMetke(listaMeta.size()));

                same.setOnMouseClicked(event1 -> {
                    editor.getJson().addString("METE","P");
                    dialog.close();
                    dodajDijalogZaMetke(listaMeta.size());
                });

                auto.setOnMouseClicked(event1 -> {
                    editor.getJson().addString("METE","A");
                    dialog.close();
                    dodajDijalogZaMetke(listaMeta.size());
                });
            }
        });


        targetButton.setOnMousePressed(event -> {

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(editor.getStage());
            dialog.setResizable(false);

            Text tt=new Text(" Težina Mete\n(3/5/7) prstena");
            tt.setFont(Font.font(15));
            Meta mEasy = new Meta(TezinaNivoa.EASY, Color.YELLOW,50, null);
            DugmeOdabir buttonEasy = new DugmeOdabir(mEasy, Color.WHITESMOKE, Color.YELLOW);
            Meta mMedium = new Meta(TezinaNivoa.MEDIUM, Color.ORANGE,50, null);
            DugmeOdabir buttonMedium = new DugmeOdabir(mMedium , Color.WHITESMOKE, Color.ORANGE);
            Meta mHard = new Meta(TezinaNivoa.HARD, Color.RED,50, null);
            DugmeOdabir buttonHard = new DugmeOdabir(mHard , Color.WHITESMOKE, Color.RED);
            HBox hBox =new HBox(20,buttonEasy.getDugmeSaPostoljem(),buttonMedium.getDugmeSaPostoljem(), buttonHard.getDugmeSaPostoljem());
            hBox.setAlignment(Pos.CENTER);
            hBox.setMinSize(200,150);
            VBox box=new VBox(5, tt, hBox);
            box.setMinSize(450,220);
            box.setAlignment(Pos.CENTER);
            box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,new CornerRadii(2), null, new Insets(-5))));

            Scene dialogScene = new Scene(box);
            dialog.setScene(dialogScene);
            dialog.show();

            buttonEasy.setOnMouseClicked(event1 -> { newMeta=TezinaNivoa.EASY; dialog.close(); ubaciPutanju(); });
            buttonMedium.setOnMouseClicked(event1 -> { newMeta=TezinaNivoa.MEDIUM; dialog.close(); ubaciPutanju();  });
            buttonHard.setOnMouseClicked(event1 -> { newMeta=TezinaNivoa.HARD; dialog.close(); ubaciPutanju(); });
        });
    }

    private void dodajDijalogZaMetke(int brojMeta){

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(editor.getStage());
        dialog.setResizable(false);

        Text t1=new Text(" Unesite broj metaka u opsegu ["+brojMeta+",15]");
        t1.setFont(Font.font(15));

        TextField tf= new TextField();
        tf.setAlignment(Pos.CENTER);
        tf.setMaxWidth(180);

        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        Dugme ok= new Dugme(" OK ");

        VBox box=new VBox(10, t1, tf, ok.getDugmeSaPostoljem());
        box.setMinSize(350,180);
        box.setAlignment(Pos.CENTER);
        box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,new CornerRadii(2), null, new Insets(-5))));

        Scene dialogScene = new Scene(box);
        dialog.setScene(dialogScene);
        dialog.show();

        ok.setOnMouseClicked(event -> {
            int num;
            if (tf.getText()==null || tf.getText().length()==0) num=10;
            else if (Integer.valueOf(tf.getText())>15) num=15;
            else if (Integer.valueOf(tf.getText())<brojMeta) num=brojMeta;
            else num=Integer.valueOf(tf.getText());
            editor.getJson().addInt("brMetaka", num);
            dialog.close();
        });
    }

    private void ubaciPutanju(){

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(editor.getStage());
        dialog.setResizable(false);

        Text tt=new Text(" Odaberi putanju ");
        tt.setFont(Font.font(15));

        Line line=new Line( 5, 5, buttonSize+5, buttonSize+5);
        DugmeOdabir lineButton = new DugmeOdabir( line, Color.WHITESMOKE, Color.CYAN );
        Tooltip tooltipLine= new Tooltip("Prava Linija");
        tooltipLine.setShowDelay(Duration.millis(50));
        Tooltip.install(lineButton,tooltipLine);
        lineButton.setOnMouseClicked(event ->{
            dialog.close(); nacrtajLiniju(false); alati.getChildren().remove(targetButton);
        });

        Rectangle rectangle = new Rectangle( 5,5,buttonSize*1.3, buttonSize);
        rectangle.setFill(null);
        rectangle.setStroke(Color.BLACK);
        DugmeOdabir recButton = new DugmeOdabir( rectangle, Color.WHITESMOKE, Color.CYAN );
        Tooltip tooltipRec= new Tooltip("Četvorougao");
        tooltipRec.setShowDelay(Duration.millis(50));
        Tooltip.install(recButton,tooltipRec);
        recButton.setOnMouseClicked(event ->{
            dialog.close(); nacrtajPravougaonik(); alati.getChildren().remove(targetButton);
        });

        Polyline polyline = new Polyline(5,5, buttonSize ,5,buttonSize/3*2, buttonSize/3, buttonSize, buttonSize,5,buttonSize, buttonSize/3, buttonSize/3*2);
        polyline.setFill(null);
        polyline.setStroke(Color.BLACK);
        DugmeOdabir polyButton= new DugmeOdabir( polyline, Color.WHITESMOKE, Color.CYAN );
        Tooltip tooltipPoly= new Tooltip("");
        tooltipPoly.setShowDelay(Duration.millis(50));
        Tooltip.install(polyButton,tooltipPoly);
        Tooltip tooltipPoly2= new Tooltip("Izlomljena linija");
        tooltipPoly2.setShowDelay(Duration.millis(50));
        Tooltip.install(polyButton,tooltipPoly2);
        polyButton.setOnMouseReleased(qevent ->{
            dialog.close(); nacrtajLiniju(true); alati.getChildren().remove(targetButton);
        });

        Ellipse ellipse = new Ellipse((buttonSize)/2*1.3,(buttonSize)/2);
        ellipse.setFill(null);
        ellipse.setStroke(Color.BLACK);
        DugmeOdabir ellButton = new DugmeOdabir( ellipse, Color.WHITESMOKE, Color.CYAN );
        Tooltip tooltipEll= new Tooltip("Elipsa");
        tooltipEll.setShowDelay(Duration.millis(50));
        Tooltip.install(ellButton,tooltipEll);
        ellButton.setOnMouseClicked(event ->{
            dialog.close(); nacrtajElipsu(); alati.getChildren().remove(targetButton);
        });

        CubicCurve cubicCurve = new CubicCurve( 5, 5, buttonSize/3*2 + 5, 5,buttonSize/3+5,buttonSize , buttonSize, buttonSize );
        cubicCurve.setFill(null);
        cubicCurve.setStroke(Color.BLACK);
        DugmeOdabir curButton = new DugmeOdabir( cubicCurve, Color.WHITESMOKE, Color.CYAN );
        Tooltip tooltipCur= new Tooltip("Kriva Linija");
        tooltipCur.setShowDelay(Duration.millis(50));
        Tooltip.install(curButton,tooltipCur);
        curButton.setOnMouseClicked(event ->{
            dialog.close(); nacrtajKrivuLiniju(); alati.getChildren().remove(targetButton);
        });

        HBox box1=new HBox(10,  lineButton.getDugmeSaPostoljem(), polyButton.getDugmeSaPostoljem(), recButton.getDugmeSaPostoljem());
        box1.setAlignment(Pos.CENTER);
        HBox box2=new HBox(10, ellButton.getDugmeSaPostoljem(), curButton.getDugmeSaPostoljem());
        box2.setAlignment(Pos.CENTER);

        VBox vBox=new VBox(10,tt,box1,box2);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinHeight(200);
        vBox.setMinWidth(250);
        Scene dialogScene = new Scene(vBox);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void nacrtajLiniju(boolean bool){
        if (!bool){
            final Line[] newLine = {null};
            EventHandler<MouseEvent> mousePresHandler, mouseDragHandler, mouseReleaseHandler;

            mousePresHandler = event ->{
                if (event.getX()<50 || event.getX()>=width-50 ||
                        event.getY()<=50 || event.getY()>=heightForGame-55) return;
                newLine[0]=(new Line(event.getX(), event.getY(), event.getX(), event.getY()));
                newLine[0].setStrokeWidth(3);
                newPutanja=newLine[0];
                group.getChildren().add(newLine[0]);
            };
            addEventHandler(MouseEvent.MOUSE_PRESSED, mousePresHandler);

            mouseDragHandler = event ->{
                if (newLine[0]==null) return;
                double  endNodeX = event.getX();
                double  endNodeY = event.getY();
                if (event.getY()>heightForGame-5) return;
                        newLine[0].setEndX(endNodeX);
                        newLine[0].setEndY(endNodeY);
            };
            addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDragHandler);

            mouseReleaseHandler = event ->{
                if (newLine[0]==null) return;
                if (!alati.getChildren().contains(doneButton)) {
                    alati.getChildren().add(doneButton);
                Circle startDot=helpCircle(newLine[0].getStartX(),newLine[0].getStartY(),  tipTacke.PRVA);
                Circle endDot=helpCircle(newLine[0].getEndX(),newLine[0].getEndY(), tipTacke.POSLEDNJA);
                Circle moveLineDot = helpCircle((newLine[0].getStartX()+newLine[0].getEndX())/2,(newLine[0].getStartY()+newLine[0].getEndY())/2, tipTacke.SREDISNJA);
                removeEventHandler(mousePresHandler);
                removeEventHandler(mouseDragHandler);
                    final Point2D[] lastEvent = {new Point2D(0, 0)};
                startDot.setOnMouseDragged(event1 -> {
                    if (startDot.getCenterX()>50 && startDot.getCenterX()<width-50 &&
                            startDot.getCenterY()>50 && startDot.getCenterY()<heightForGame-55) lastEvent[0] =new Point2D(event1.getX(),event1.getY());
                    newLine[0].setStartX(event1.getX());
                    newLine[0].setStartY(event1.getY());
                    startDot.setCenterX(event1.getX());
                    startDot.setCenterY(event1.getY());
                    moveLineDot.setCenterX((newLine[0].getStartX()+newLine[0].getEndX())/2);
                    moveLineDot.setCenterY((newLine[0].getStartY()+newLine[0].getEndY())/2);
                });
                    startDot.setOnMouseReleased(event1 -> {
                        if (startDot.getCenterX()>50 && startDot.getCenterX()<width-50 &&
                                startDot.getCenterY()>50 && startDot.getCenterY()<heightForGame-55) return;
                        newLine[0].setStartX(lastEvent[0].getX());
                        newLine[0].setStartY(lastEvent[0].getY());
                        startDot.setCenterX(lastEvent[0].getX());
                        startDot.setCenterY(lastEvent[0].getY());
                        moveLineDot.setCenterX((newLine[0].getStartX()+newLine[0].getEndX())/2);
                        moveLineDot.setCenterY((newLine[0].getStartY()+newLine[0].getEndY())/2);
                    });
                endDot.setOnMouseDragged(event1 -> {
                    if (endDot.getCenterX()>50 && endDot.getCenterX()<width-50 &&
                            endDot.getCenterY()>50 && endDot.getCenterY()<heightForGame-55) lastEvent[0]=new Point2D(event1.getX(),event1.getY());
                    newLine[0].setEndX(event1.getX());
                    newLine[0].setEndY(event1.getY());
                    endDot.setCenterX(event1.getX());
                    endDot.setCenterY(event1.getY());
                    moveLineDot.setCenterX((newLine[0].getStartX()+newLine[0].getEndX())/2);
                    moveLineDot.setCenterY((newLine[0].getStartY()+newLine[0].getEndY())/2);
                });
                    endDot.setOnMouseReleased(event1 -> {
                        if (endDot.getCenterX()>50 && endDot.getCenterX()<width-50 &&
                                endDot.getCenterY()>50 && endDot.getCenterY()<heightForGame-55) return;
                        newLine[0].setEndX(lastEvent[0].getX());
                        newLine[0].setEndY(lastEvent[0].getY());
                        endDot.setCenterX(lastEvent[0].getX());
                        endDot.setCenterY(lastEvent[0].getY());
                        moveLineDot.setCenterX((newLine[0].getStartX()+newLine[0].getEndX())/2);
                        moveLineDot.setCenterY((newLine[0].getStartY()+newLine[0].getEndY())/2);
                    });
                moveLineDot.setOnMouseDragged(event1 -> {
                    if (newLine[0].getBoundsInParent().getMinX()>50 && newLine[0].getBoundsInParent().getMaxX()<width-50 &&
                            newLine[0].getBoundsInParent().getMinY()>50 && newLine[0].getBoundsInParent().getMaxY()<heightForGame-55) lastEvent[0]=new Point2D(event1.getX(),event1.getY());
                    double addX=event1.getX()-moveLineDot.getCenterX();
                    double addY=event1.getY()-moveLineDot.getCenterY();
                    double startX = newLine[0].getStartX()+addX;
                    double startY = newLine[0].getStartY()+addY;
                    double endX = newLine[0].getEndX()+addX;
                    double endY = newLine[0].getEndY()+addY;
                    newLine[0].setStartX(startX);
                    newLine[0].setStartY(startY);
                    newLine[0].setEndX(endX);
                    newLine[0].setEndY(endY);
                    startDot.setCenterX(startX);
                    startDot.setCenterY(startY);
                    endDot.setCenterX(endX);
                    endDot.setCenterY(endY);
                    moveLineDot.setCenterX(event1.getX());
                    moveLineDot.setCenterY(event1.getY());
                });
                    moveLineDot.setOnMouseReleased(event1 -> {
                        if (newLine[0].getBoundsInParent().getMinX()>50 && newLine[0].getBoundsInParent().getMaxX()<width-50 &&
                                newLine[0].getBoundsInParent().getMinY()>50 && newLine[0].getBoundsInParent().getMaxY()<heightForGame-55) return;
                        double addX= lastEvent[0].getX()-moveLineDot.getCenterX();
                        double addY= lastEvent[0].getY()-moveLineDot.getCenterY();
                        double startX = newLine[0].getStartX()+addX;
                        double startY = newLine[0].getStartY()+addY;
                        double endX = newLine[0].getEndX()+addX;
                        double endY = newLine[0].getEndY()+addY;
                        newLine[0].setStartX(startX);
                        newLine[0].setStartY(startY);
                        newLine[0].setEndX(endX);
                        newLine[0].setEndY(endY);
                        startDot.setCenterX(startX);
                        startDot.setCenterY(startY);
                        endDot.setCenterX(endX);
                        endDot.setCenterY(endY);
                        moveLineDot.setCenterX(lastEvent[0].getX());
                        moveLineDot.setCenterY(lastEvent[0].getY());
                    });
                }
            };
            addEventHandler(MouseEvent.MOUSE_RELEASED,mouseReleaseHandler);
            return;
        }

        alati.getChildren().add(doneButton);
        EventHandler<MouseEvent> mouseClickHandler;
        final Polyline[] newPolyLine = new Polyline[1];
        final Circle[] last = new Circle[1];
        mouseClickHandler = event -> {
            final Circle[] lastMade = new Circle[1];
            if (event.getClickCount() > 1 && !event.isShiftDown()) {
                if (event.getX() < 50 || event.getX() >= width - 50 ||
                        event.getY() <= 50 || event.getY() >= heightForGame - 50) return;
                if (pomeranjeOblika.isEmpty()) {
                    lastMade[0] = last[0] = helpCircle(event.getSceneX(), event.getSceneY(), tipTacke.POSLEDNJA);
                    last[0].setId("" + 0);
                    newPolyLine[0] = new Polyline(event.getSceneX(), event.getSceneY());
                    group.getChildren().add(newPolyLine[0]);
                    this.newPutanja = newPolyLine[0];
                } else {
                    pomeranjeOblika.remove(last[0]);
                    lastMade[0] = helpCircle(last[0].getCenterX(), last[0].getCenterY(), (pomeranjeOblika.size() == 0) ? tipTacke.PRVA : tipTacke.SREDISNJA);
                    lastMade[0].setId("" + (pomeranjeOblika.size()-1));
                    last[0].setCenterX(event.getSceneX());
                    last[0].setCenterY(event.getSceneY());
                    last[0].setId("" + pomeranjeOblika.size());
                    pomeranjeOblika.add(last[0]);
                    newPolyLine[0].getPoints().addAll(event.getSceneX(), event.getSceneY());
                }
                AtomicReference<Point2D> lastEvent = new AtomicReference<>(new Point2D(0, 0));

                lastMade[0].setOnMouseDragged(event13 -> {
                    if (lastMade[0].getCenterX() > 50 && lastMade[0].getCenterX() < width - 50 &&
                            lastMade[0].getCenterY() > 50 && lastMade[0].getCenterY() < heightForGame - 55) {
                        lastEvent.set(new Point2D(event13.getSceneX(), event13.getSceneY()));
                    }
                    double deltaX = event13.getSceneX() - lastMade[0].getCenterX();
                    double deltaY = event13.getSceneY() - lastMade[0].getCenterY();
                    newPolyLine[0].getPoints().set(pomeranjeOblika.indexOf(lastMade[0]) * 2, newPolyLine[0].getPoints().get( pomeranjeOblika.indexOf(lastMade[0]) * 2 ) + deltaX);
                    newPolyLine[0].getPoints().set(pomeranjeOblika.indexOf(lastMade[0]) * 2 + 1, newPolyLine[0].getPoints().get(pomeranjeOblika.indexOf(lastMade[0]) * 2 + 1) + deltaY);
                    lastMade[0].setCenterX(event13.getX());
                    lastMade[0].setCenterY(event13.getY());
                });

                lastMade[0].setOnMouseReleased(event13 -> {
                    if (lastMade[0].getCenterX() > 50 && lastMade[0].getCenterX() < width - 50 &&
                            lastMade[0].getCenterY() > 50 && lastMade[0].getCenterY() < heightForGame - 55) return;
                    newPolyLine[0].getPoints().set(pomeranjeOblika.indexOf(lastMade[0]) * 2, lastEvent.get().getX());
                    newPolyLine[0].getPoints().set(pomeranjeOblika.indexOf(lastMade[0]) * 2 + 1, lastEvent.get().getY());
                    lastMade[0].setCenterX(lastEvent.get().getX());
                    lastMade[0].setCenterY(lastEvent.get().getY());
                });

                lastMade[0].setOnMousePressed(event1 -> {
                    if (event1.isShiftDown()){
                        group.getChildren().remove(lastMade[0]);
                        newPolyLine[0].getPoints().remove(pomeranjeOblika.indexOf(lastMade[0]) * 2 + 1 );
                        newPolyLine[0].getPoints().remove(pomeranjeOblika.indexOf(lastMade[0]) * 2 );
                        pomeranjeOblika.remove(lastMade[0]);
                        if (pomeranjeOblika.size()==0) refresh();
                    }
                });
            }
        };
        addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickHandler);
    }

    private void nacrtajKrivuLiniju() {

            final CubicCurve[] newLine = {null};
            EventHandler<MouseEvent> mousePresHandler, mouseDragHandler, mouseReleaseHandler = null;

             mouseDragHandler = event -> {
                 double endNodeX = event.getX();
                 double endNodeY = event.getY();
                if (event.getY() > heightForGame - 5) return;
                newLine[0].setEndX(endNodeX);
                newLine[0].setEndY(endNodeY);
                newLine[0].setControlX1(endNodeX);
                newLine[0].setControlY2(endNodeY);
             };
            addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDragHandler);

            mousePresHandler = event -> {
                if (event.getX() < 50 && event.getX() > width - 50 &&
                        event.getY() < 50 && event.getX() > heightForGame - 55) return;
                newLine[0] = (new CubicCurve(event.getX(), event.getY(), event.getX(), event.getY(), event.getX(), event.getY(), event.getX(), event.getY()));
                newLine[0].setStrokeWidth(3);
                newLine[0].setStroke(color==Color.BLACK?Color.BLUE:Color.RED);
                newLine[0].setFill(null);
                newPutanja = newLine[0];
                group.getChildren().add(newLine[0]);
            };
            addEventHandler(MouseEvent.MOUSE_PRESSED, mousePresHandler);

            mouseReleaseHandler = event -> {
                if (!alati.getChildren().contains(doneButton) && newLine[0]!=null) {
                    alati.getChildren().add(doneButton);
                    Circle startDot = helpCircle(newLine[0].getStartX(), newLine[0].getStartY(), tipTacke.PRVA);
                    Circle endDot = helpCircle(newLine[0].getEndX(), newLine[0].getEndY(), tipTacke.POSLEDNJA);
                    Circle c1 = helpCircle(newLine[0].getControlX1(), newLine[0].getControlY1(), tipTacke.KONTROLNA);
                    Circle c2= helpCircle(newLine[0].getControlX2(), newLine[0].getControlY2(), tipTacke.KONTROLNA);
                    Point2D middle= BezjeovaKriva.findMiddle(newLine[0]);
                    Circle moveLineDot = helpCircle(middle.getX(), middle.getY(), tipTacke.SREDISNJA);

                    removeEventHandler(mousePresHandler);
                    removeEventHandler(mouseDragHandler);

                    EventHandler<MouseEvent> updateMiddle = event1 -> {
                        Point2D middle2= BezjeovaKriva.findMiddle(newLine[0]);
                        moveLineDot.setCenterX(middle2.getX());
                        moveLineDot.setCenterY(middle2.getY());
                    };
                    c1.addEventHandler(MouseEvent.MOUSE_DRAGGED,updateMiddle);
                    c2.addEventHandler(MouseEvent.MOUSE_DRAGGED,updateMiddle);
                    endDot.addEventHandler(MouseEvent.MOUSE_DRAGGED,updateMiddle);
                    startDot.addEventHandler(MouseEvent.MOUSE_DRAGGED,updateMiddle);

                    c1.setOnMouseDragged(event1 -> {
                        newLine[0].setControlX1(event1.getX()); newLine[0].setControlY1(event1.getY());
                        c1.setCenterX(event1.getX()); c1.setCenterY(event1.getY());
                    });
                    c2.setOnMouseDragged(event1 -> {
                        newLine[0].setControlX2(event1.getX()); newLine[0].setControlY2(event1.getY());
                        c2.setCenterX(event1.getX()); c2.setCenterY(event1.getY());
                    });
                    startDot.setOnMouseDragged(event1 -> {
                        if (event1.getX() < 50 || event1.getX() >= width - 50 ||
                                event1.getY() <= 50 || event1.getY() >= heightForGame - 55) return;
                        newLine[0].setStartX(event1.getX());
                        newLine[0].setStartY(event1.getY());
                        startDot.setCenterX(event1.getX());
                        startDot.setCenterY(event1.getY());
                    });
                    endDot.setOnMouseDragged(event1 -> {
                        if (event1.getX() < 50 || event1.getX() >= width - 50 ||
                                event1.getY() <= 50 || event1.getY() >= heightForGame - 55) return;
                        newLine[0].setEndX(event1.getX());
                        newLine[0].setEndY(event1.getY());
                        endDot.setCenterX(event1.getX());
                        endDot.setCenterY(event1.getY());
                    });


                    moveLineDot.setOnMouseDragged(event1 -> {
                        if (newLine[0].getBoundsInParent().getMinX() < 0 || newLine[0].getBoundsInParent().getMaxX() >= width ||
                                newLine[0].getBoundsInParent().getMinY() <= 0 || newLine[0].getBoundsInParent().getMaxY() >= heightForGame)
                            return;
                        double addX = event1.getX() - moveLineDot.getCenterX();
                        double addY = event1.getY() - moveLineDot.getCenterY();

                        Point2D start=new Point2D(newLine[0].getStartX() + addX, newLine[0].getStartY() + addY);
                        Point2D end=new Point2D(newLine[0].getEndX() + addX, newLine[0].getEndY() + addY);
                        Point2D contr1=new Point2D(newLine[0].getControlX1() + addX, newLine[0].getControlY1() + addY);
                        Point2D contr2=new Point2D(newLine[0].getControlX2() + addX, newLine[0].getControlY2() + addY);

                        newLine[0].setStartX(start.getX()); newLine[0].setStartY(start.getY());
                        newLine[0].setControlX1(contr1.getX()); newLine[0].setControlY1(contr1.getY());
                        newLine[0].setControlX2(contr2.getX()); newLine[0].setControlY2(contr2.getY());
                        newLine[0].setEndX(end.getX()); newLine[0].setEndY(end.getY());
                        c1.setCenterX(contr1.getX()); c1.setCenterY(contr1.getY());
                        c2.setCenterX(contr2.getX()); c2.setCenterY(contr2.getY());
                        startDot.setCenterX(start.getX()); startDot.setCenterY(start.getY());
                        endDot.setCenterX(end.getX()); endDot.setCenterY(end.getY());
                        moveLineDot.setCenterX(event1.getX()); moveLineDot.setCenterY(event1.getY());

                    });
                }
            };
            addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleaseHandler);

    }

    private void nacrtajPravougaonik(){
        final Polygon[] newPoly = new Polygon[1];
        EventHandler<MouseEvent> mousePresHandler, mouseDragHandler, mouseReleaseHandler = null;

        mousePresHandler = event ->{
            newPoly[0]=new Polygon(event.getSceneX(),event.getSceneY(),event.getSceneX(),event.getSceneY(),event.getSceneX(),event.getSceneY(),event.getSceneX(),event.getSceneY());
            newPoly[0].setStrokeWidth(2);
            newPutanja=newPoly[0];
            group.getChildren().add(newPoly[0]);
            newPoly[0].setFill(null);
            newPoly[0].setStroke(color==Color.BLACK? Color.BLUE:Color.BLACK);
        };
        addEventHandler(MouseEvent.MOUSE_PRESSED, mousePresHandler);

        mouseDragHandler = event ->{
            if (event.getY()>heightForGame-50) return;
            newPoly[0].getPoints().set(2,event.getX());
            newPoly[0].getPoints().set(4,event.getX());
            newPoly[0].getPoints().set(5,event.getY());
            newPoly[0].getPoints().set(7,event.getY());
        };
        addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDragHandler);

        mouseReleaseHandler = event ->{
            if (!alati.getChildren().contains(doneButton)) {
                alati.getChildren().add(doneButton);
                Circle center=helpCircle((newPoly[0].getPoints().get(0)+newPoly[0].getPoints().get(2))/2,(newPoly[0].getPoints().get(3)+newPoly[0].getPoints().get(7))/2, tipTacke.SREDISNJA);
                pomeranjeOblika.remove(center);

                for (int i=0;i<4;i++){
                    Circle c=helpCircle(newPoly[0].getPoints().get(i*2),newPoly[0].getPoints().get(i*2+1),(i==0)? tipTacke.PRVA : (i==3)? tipTacke.POSLEDNJA : tipTacke.SREDISNJA);

                removeEventHandler(mousePresHandler);
                removeEventHandler(mouseDragHandler);

                    int finalI = i;
                    c.setOnMouseDragged(event1 -> {
                    if (event1.getX()<50 || event1.getX()>=width-50 ||
                            event1.getY()<=50 || event1.getY()>=heightForGame-55) return;
                    newPoly[0].getPoints().set(finalI *2,event1.getX());
                    newPoly[0].getPoints().set(finalI *2+1,event1.getY());
                    if (finalI%2==0){
                        newPoly[0].getPoints().set((finalI*2+6)%8, event1.getX());
                        newPoly[0].getPoints().set((finalI*2+3)%8, event1.getY());
                        Circle cc= pomeranjeOblika.get((finalI+3)%4); cc.setCenterX(event1.getX());
                        Circle cc2= pomeranjeOblika.get((finalI+1)%4); cc2.setCenterY(event1.getY());
                    }
                    else {

                        newPoly[0].getPoints().set((finalI*2+2)%8, event1.getX());
                        newPoly[0].getPoints().set((finalI*2+7)%8, event1.getY());
                        Circle cc= pomeranjeOblika.get((finalI+1)%4); cc.setCenterX(event1.getX());
                        Circle cc2= pomeranjeOblika.get((finalI+3)%4); cc2.setCenterY(event1.getY());
                    }
                    c.setCenterX(event1.getX());
                    c.setCenterY(event1.getY());
                    center.setCenterX((newPoly[0].getPoints().get(0)+newPoly[0].getPoints().get(2))/2);
                    center.setCenterY((newPoly[0].getPoints().get(3)+newPoly[0].getPoints().get(5))/2);

                });
                    center.addEventHandler(MouseEvent.MOUSE_DRAGGED,event2 -> {
                        Point2D add =new Point2D(event2.getX()-center.getCenterX(),event2.getY()-center.getCenterY());
                        c.setCenterX(c.getCenterX()+add.getX()); c.setCenterY(c.getCenterY()+add.getY());
                        newPoly[0].getPoints().set(finalI*2,c.getCenterX());
                        newPoly[0].getPoints().set(finalI*2+1,c.getCenterY());

                    });
                }
                pomeranjeOblika.add(center);
                center.addEventHandler(MouseEvent.MOUSE_DRAGGED,event1 -> {
                    center.setCenterX(event1.getX()); center.setCenterY(event1.getY());
                });
            }
        };
        addEventHandler(MouseEvent.MOUSE_RELEASED,mouseReleaseHandler);
    }

    private void nacrtajElipsu(){
        final Ellipse[] newEll = {null};
        final Point2D[] start = new Point2D[1];
        EventHandler<MouseEvent> mousePresHandler, mouseDragHandler, mouseReleaseHandler = null;

        mousePresHandler = event ->{
            newEll[0]=new Ellipse(0,0);
            newEll[0].setStrokeWidth(2);
            newPutanja=newEll[0];
            start[0] =new Point2D(event.getX(),event.getY());
            group.getChildren().add(newEll[0]);
            newEll[0].setFill(null);
            newEll[0].setStroke(color==Color.BLACK? Color.BLUE:Color.BLACK);
        };
        addEventHandler(MouseEvent.MOUSE_PRESSED, mousePresHandler);

        mouseDragHandler = event ->{
            if (event.getY()>heightForGame-50) return;
            Point2D radius = new Point2D(Math.abs(event.getX()-start[0].getX())/2, Math.abs(event.getY()-start[0].getY())/2);
            Point2D center = new Point2D((event.getX()+start[0].getX())/2, Math.abs(event.getY()+start[0].getY())/2);
            newEll[0].setRadiusX(radius.getX());
            newEll[0].setRadiusY(radius.getY());
            newEll[0].setCenterX(center.getX());
            newEll[0].setCenterY(center.getY());
        };
        addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDragHandler);

        mouseReleaseHandler = event ->{
            if (!alati.getChildren().contains(doneButton)) {
                alati.getChildren().add(doneButton);
                    Circle first=helpCircle(newEll[0].getCenterX(),newEll[0].getCenterY()-newEll[0].getRadiusY(), tipTacke.PRVA_ELIPSA);
                    Circle last=helpCircle(newEll[0].getCenterX()+newEll[0].getRadiusX(),newEll[0].getCenterY()+newEll[0].getRadiusY(), tipTacke.SREDISNJA);
                    Circle center=helpCircle(newEll[0].getCenterX(),newEll[0].getCenterY(), tipTacke.SREDISNJA);

                    removeEventHandler(mousePresHandler);
                    removeEventHandler(mouseDragHandler);

                    last.setOnMouseDragged(event1 -> {
                        last.setCenterX(event1.getX()); last.setCenterY(event1.getY());
                        newEll[0].setRadiusX(Math.abs(last.getCenterX()-start[0].getX())/2);
                        newEll[0].setRadiusY(Math.abs(last.getCenterY()-start[0].getY())/2);
                        newEll[0].setCenterX((last.getCenterX()+start[0].getX())/2);
                        newEll[0].setCenterY((last.getCenterY()+start[0].getY())/2);
                        center.setCenterX(newEll[0].getCenterX()); center.setCenterY(newEll[0].getCenterY());
                        first.setCenterX(newEll[0].getCenterX());
                        first.setCenterY(newEll[0].getCenterY()-newEll[0].getRadiusY());
                    });

                    center.setOnMouseDragged(event1 -> {
                        Point2D add = new Point2D(event1.getX()-center.getCenterX(),event1.getY()-center.getCenterY());
                        newEll[0].setCenterX(event1.getX()); newEll[0].setCenterY(event1.getY());
                        center.setCenterX(event1.getX()); center.setCenterY(event1.getY());
                        start[0]=new Point2D(start[0].getX()+add.getX(),start[0].getY()+add.getY());
                        last.setCenterY(last.getCenterY()+add.getY()); last.setCenterX(last.getCenterX()+add.getX());
                        first.setCenterX(newEll[0].getCenterX());
                        first.setCenterY(newEll[0].getCenterY()-newEll[0].getRadiusY());
                    });



            }
        };
        addEventHandler(MouseEvent.MOUSE_RELEASED,mouseReleaseHandler);



    }

    private Circle helpCircle(double x, double y, tipTacke type) {
        Circle c = new Circle(x, y,5);
        c.setFill((color == Color.RED) ? Color.BLUE : Color.RED);
        pomeranjeOblika.add(c);
        group.getChildren().add(c);

        switch(type){

            case PRVA:
                c.setRadius(50);
                Tooltip tooltipTarget= new Tooltip("Početna tacka kretanja");
                tooltipTarget.setShowDelay(Duration.millis(50));
                Tooltip.install(c,tooltipTarget);
                break;
            case POSLEDNJA:
                c.setStrokeWidth(5);
                c.setRadius(50);
                c.setStrokeType(StrokeType.INSIDE);
                c.setStroke(Color.BLACK);
                Tooltip tooltipTarget2= new Tooltip("Krajnja tačka kretanja");
                tooltipTarget2.setShowDelay(Duration.millis(50));
                Tooltip.install(c,tooltipTarget2);
                break;
            case KONTROLNA:
                Tooltip tooltipTarget3= new Tooltip("Kontrolana tačka - pomeri da oblikujes putanju");
                tooltipTarget3.setShowDelay(Duration.millis(50));
                Tooltip.install(c,tooltipTarget3);
                break;
            case PRVA_ELIPSA:
                c.setRadius(50);
                Tooltip tooltipTarget4= new Tooltip("Početna tacka kretanja - kretanje je u smeru kazaljke na časovniku\nnema efekta");
                tooltipTarget4.setShowDelay(Duration.millis(50));
                Tooltip.install(c,tooltipTarget4);
                break;
        }
        return c;
    }

    private void refresh(){
        Group g=new Group();
        g.getChildren().addAll(group.getChildren());
        group.getChildren().clear();
        group.getChildren().addAll(g.getChildren());
    }

    private void dodajMetuUJSON(){
        listaMeta.add(newMeta);
        listaPutanja.add(newPutanja);
        editor.getJson().addInt("brMeta", listaMeta.size());
        editor.getJson().addInt("me-"+(listaMeta.size()-1)+"-r", newMeta.vrednostTezineNivoa(newMeta));
        editor.getJson().addInt("me-"+(listaMeta.size()-1)+"-s", TezinaNivoa.vrednostTezineNivoa(brzinaKretanja.get(brzinaKretanja.size()-1)));
        editor.getJson().addString("me-"+(listaMeta.size()-1)+"-p", ObradaOblika.NodeToString(newPutanja));
    }


}
