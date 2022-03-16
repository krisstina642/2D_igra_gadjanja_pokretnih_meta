package EditorStreljane.Meni;

import EditorStreljane.Editor;
import Komponente.Dugme;
import Komponente.DugmeOdabir;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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

import java.util.ArrayList;

public class AnimirajOblikMeni {

    ArrayList<Transition> animacije;
    DugmeOdabir choosen = null;
    Node animationObject;
    SequentialTransition sequentialTransition;
    double startX;
    double startY;
    double lastX;
    double lastY;
    double lastOpacity;
    ArrayList<Node> listOfDots=new ArrayList<>();


    AnimirajOblikMeni(Group group, Editor editor, double HEIGHT, Color color, HBox alati, double buttonSize, DugmeOdabir exitButton){
        lastOpacity=1;
        alati.getChildren().addAll(exitButton);
        animationObject=group.getChildren().get(group.getChildren().size()-1);
        startX = lastX =( animationObject.getBoundsInParent().getMinX() + animationObject.getBoundsInParent().getMaxX())/2;
        startY = lastY = ( animationObject.getBoundsInParent().getMinY() + animationObject.getBoundsInParent().getMaxY())/2;
        Image[] imageList= new Image[]{
                new Image("Slike/fade.png"),
                new Image("Slike/rotate.png"),
                new Image("Slike/translation.png"),
                new Image("Slike/play.png")
        };

        Rectangle fadeRec = new Rectangle( 5,5,(buttonSize)*1.3, buttonSize);
        Rectangle rotateRec = new Rectangle( 5,5,(buttonSize)*1.3, buttonSize);
        Rectangle translateRec = new Rectangle( 5,5,(buttonSize)*1.3, buttonSize);
        Rectangle playRec = new Rectangle( 5,5,(buttonSize)*1.3, buttonSize);
        Rectangle[] recList=new Rectangle[]{ fadeRec, rotateRec, translateRec, playRec};

        for(int i=0; i<recList.length;i++){
            recList[i].setFill(new ImagePattern(imageList[i]));
        }

        DugmeOdabir fadeButton= new DugmeOdabir( fadeRec, Color.WHITE, color);
        DugmeOdabir rotateButton= new DugmeOdabir( rotateRec, Color.WHITE, color);
        DugmeOdabir translateButton= new DugmeOdabir( translateRec, Color.WHITE, color);
        DugmeOdabir playButton= new DugmeOdabir( playRec, Color.WHITE, color);
        DugmeOdabir[] buttonList=new DugmeOdabir[]{ fadeButton, rotateButton, translateButton, playButton};

        for(int i=0; i<buttonList.length;i++){
            if (i<recList.length/2) alati.getChildren().add(0,buttonList[i]);
            else alati.getChildren().add(alati.getChildren().size(),buttonList[i]);
        }
        Tooltip tooltipFade= new Tooltip("Efekat Prozirnosti koji traje 0.5 sekundi");
        tooltipFade.setShowDelay(Duration.millis(50));
        Tooltip.install(fadeButton,tooltipFade);

        Tooltip tooltipRot= new Tooltip("Rotacija dužne 0.5 sekundi");
        tooltipRot.setShowDelay(Duration.millis(50));
        Tooltip.install(rotateButton,tooltipRot);

        Tooltip tooltipPlay= new Tooltip("Pusti Animaciju ispočetka");
        tooltipPlay.setShowDelay(Duration.millis(50));
        Tooltip.install(playButton,tooltipPlay);

        Tooltip tooltipTrans= new Tooltip("Translacija objekta - nacrtaj putanju klikom na pozadinu");
        tooltipTrans.setShowDelay(Duration.millis(50));
        Tooltip.install(translateButton,tooltipTrans);


        EventHandler<MouseEvent> mousePressedTranslationHandler = event -> {
            if (event.getY()<HEIGHT){
                Circle circle = new Circle(event.getSceneX(), event.getSceneY(),5);
                circle.setFill( color==Color.RED? Color.BLUE : Color.RED);
                Path p= new Path(new MoveTo(lastX,lastY));
                group.getChildren().add(circle);
                listOfDots.add(circle);
                double lastX1 = event.getX()-lastX;
                double lastY1 = event.getY()-lastY;
                lastX=event.getX(); lastY=event.getY();
                double length=Math.sqrt(Math.pow(lastX1,2)+Math.pow(lastY1,2));
                p.getElements().add(new LineTo(lastX,lastY));
                group.getChildren().add(p);
                listOfDots.add(p);

                TranslateTransition translate=new TranslateTransition(Duration.millis(length * 2));
                translate.setByX(lastX1);
                translate.setByY(lastY1);

                animacije.add(translate);
            }
        };

        translateButton.setOnMouseClicked(event -> group.addEventHandler(MouseEvent.MOUSE_PRESSED,mousePressedTranslationHandler));

        rotateButton.setOnMouseClicked(event -> {

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(editor.getStage());

            TextField tf= new TextField();
            tf.setAlignment(Pos.CENTER);
            tf.setMaxWidth(180);
            Text tt=new Text("  Unesite Ugao Rotacije u stepenima ");
            tt.setFont(Font.font(15));
            Dugme buttonLeft = new Dugme( 25, 25 );
            buttonLeft.setPozadinaImage(new Image("Slike/rotateLeft.png"));
            Dugme buttonRight = new Dugme( 25, 25 );
            buttonRight.setPozadinaImage(new Image("Slike/rotate.png"));
            HBox buttons=new HBox(20,buttonLeft.getDugmeSaPostoljem(),buttonRight.getDugmeSaPostoljem());
            buttons.setAlignment(Pos.CENTER);

            VBox dialogVbox = new VBox(10, tt, tf, buttons);
            dialogVbox.setMinHeight(120);
            dialogVbox.setAlignment(Pos.CENTER);
            dialogVbox.setMinWidth(200);
            dialogVbox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,new CornerRadii(2), null, new Insets(-5))));

            tf.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    tf.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });

            Scene dialogScene = new Scene(dialogVbox);
            dialog.setScene(dialogScene);
            dialog.show();

            buttonLeft.setOnMouseClicked(event2 -> {
                if(tf.getText()==null || tf.getText().equals("")) return;
                double angle = -Integer.parseInt(tf.getText());
                RotateTransition rotTransition=new RotateTransition(Duration.seconds(0.5));
                rotTransition.setByAngle(angle);
                animacije.add(rotTransition);
                dialog.close();
                if (choosen!=null) choosen.restart();
                choosen=null;
                });

            buttonRight.setOnMouseClicked(event2 -> {
                if(tf.getText()==null || tf.getText().equals("")) return;
                double angle = Integer.parseInt(tf.getText());
                RotateTransition rotTransition=new RotateTransition(Duration.seconds(0.5));
                rotTransition.setByAngle(angle);
                animacije.add(rotTransition);
                dialog.close();
                if (choosen!=null) choosen.restart();
                choosen=null;
            });
            });


        fadeButton.setOnMouseClicked(event -> {

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(editor.getStage());

            TextField tf= new TextField();
            tf.setAlignment(Pos.CENTER);
            tf.setMaxWidth(180);
            Dugme button2 = new Dugme( "OK");
            Text tt=new Text("  Unesite Neprozirnost u intervalu od 50-100  ");
            tt.setFont(Font.font(15));
            VBox dialogVbox = new VBox(10, tt, tf, button2.getDugmeSaPostoljem());
            dialogVbox.setAlignment(Pos.CENTER);
            dialogVbox.setMinWidth(200);
            dialogVbox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,new CornerRadii(2), null, new Insets(-5))));

            tf.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    tf.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });

            Scene dialogScene = new Scene(dialogVbox);
            dialog.setScene(dialogScene);
            dialog.show();

            button2.setOnMouseClicked(event2 -> {
                if(tf.getText()==null || tf.getText().equals("")) return;
                double fade;
                if (Integer.parseInt(tf.getText())>100) fade = 1;
                else if (Integer.parseInt(tf.getText())<50) fade = 0.5;
                else fade = Integer.parseInt(tf.getText())*0.01;

                double f2=fade;
                fade=fade-lastOpacity;
                lastOpacity=f2;

                FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5));
                fadeTransition.setByValue(fade);
                animacije.add(fadeTransition);
                dialog.close();
                if (choosen!=null) choosen.restart();
                choosen=null;
            });
        });

        EventHandler<MouseEvent> exitHandler = new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {

                editor.getJson().addInt("bA" + animationObject.getId(), animacije.size());

                for (int i = 0; i < animacije.size(); i++) {
                    if (animacije.get(i) instanceof TranslateTransition) {
                        editor.getJson().addString("a-" + animationObject.getId() + "-" + i, "t");
                        editor.getJson().addDouble("x-" + animationObject.getId() + "-" + i, (((TranslateTransition) animacije.get(i)).getByX()));
                        editor.getJson().addDouble("y-" + animationObject.getId() + "-" + i, (((TranslateTransition) animacije.get(i)).getByY()));
                    } else if (animacije.get(i) instanceof RotateTransition) {
                        editor.getJson().addString("a-" + animationObject.getId() + "-" + i, "r");
                        editor.getJson().addDouble("an-" + animationObject.getId() + "-" + i, (((RotateTransition) animacije.get(i)).getByAngle()));
                    } else if (animacije.get(i) instanceof FadeTransition) {
                        editor.getJson().addString("a-" + animationObject.getId() + "-" + i, "f");
                        editor.getJson().addDouble("op-" + animationObject.getId() + "-" + i, (((FadeTransition) animacije.get(i)).getByValue() * 100));
                    }
                }
                editor.getJson().write();
                restartNode();
                for (Node listOfDot : listOfDots) group.getChildren().remove(listOfDot);
                group.removeEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedTranslationHandler);
                exitButton.removeEventHandler(MouseEvent.MOUSE_RELEASED, this);
            }
        };

        exitButton.addEventHandler(MouseEvent.MOUSE_RELEASED, exitHandler);

        playButton.addEventHandler(MouseEvent.MOUSE_PRESSED,event -> {
            restartNode();
            sequentialTransition=new SequentialTransition(animationObject);
            for (Transition transition : animacije) {
                sequentialTransition.getChildren().add(transition);
            }
            sequentialTransition.play();
        });
        animacije=new ArrayList<>();

    }

    private void restartNode(){
        if (sequentialTransition!=null) sequentialTransition.stop();
        animationObject.setTranslateX(0);
        animationObject.setTranslateY(0);
        animationObject.setOpacity(1);
        animationObject.setRotate(0);
    }




}
