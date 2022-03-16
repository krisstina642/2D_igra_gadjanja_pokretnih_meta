package PomocneKlase;

import javafx.beans.value.ChangeListener;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.*;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;


public interface SkaliranjePozornice {

    public static void promeniKoren(Stage primaryStage, Parent root){
        Scene SCENE= primaryStage.getScene();
        root.getTransforms().addAll(SCENE.getRoot().getTransforms());
        SCENE.setRoot(root);
    }
    public static void podesiSkaliranje(Stage primaryStage){

        Scene SCENA=primaryStage.getScene();
        double sceneWIDTH=SCENA.getWidth();
        double sceneHEIGHT=SCENA.getHeight();
        double primaryStageX=primaryStage.getWidth();
        double primaryStageY=primaryStage.getHeight();
        double visakX=primaryStageX-sceneWIDTH;
        double visakY=primaryStageY-sceneHEIGHT;

        Scale stageScale=new Scale(1,1);
        Translate stageTranslate=new Translate(0,0);
        stageScale.setPivotX(0);
        stageScale.setPivotY(0);

        RadialGradient gradient1 = new RadialGradient(0, .1, 0.5, 0.5, 1, true,
                CycleMethod.NO_CYCLE, new Stop(0, Color.STEELBLUE),
                new Stop(1, Color.BLACK));

        SCENA.getRoot().getTransforms().setAll(stageTranslate, stageScale);
        SCENA.setFill(gradient1);

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {

            double newWidth = primaryStage.getWidth()-visakX;
            double newHEIGHT = newWidth * sceneHEIGHT/ sceneWIDTH;
            if (primaryStage.isFullScreen()){
                newHEIGHT=primaryStage.getHeight();
                newWidth = newHEIGHT * sceneWIDTH/ sceneHEIGHT;

                double newScaleY = newHEIGHT/(sceneHEIGHT);
                double newScaleX = newWidth/(sceneWIDTH);

                stageScale.setX(newScaleX);
                stageScale.setY(newScaleY);

                stageTranslate.setX((primaryStage.getWidth()-newScaleX*sceneWIDTH)/2);
                stageTranslate.setY((primaryStage.getHeight()-newScaleY*sceneHEIGHT)/2);
                SCENA.getRoot().getTransforms().setAll(stageTranslate, stageScale);
                return;

            }
            else if(newHEIGHT>(primaryStage.getHeight()-visakY)){
                newHEIGHT=primaryStage.getHeight()-visakY;
                newWidth = newHEIGHT * sceneWIDTH/ sceneHEIGHT;
            }

            double newScaleY = newHEIGHT/(primaryStageY - visakY);
            double newScaleX = newWidth/(primaryStageX - visakX);

            stageTranslate.setX((primaryStage.getWidth()-visakX-newScaleX*sceneWIDTH)/2);
            stageTranslate.setY((primaryStage.getHeight()-visakY-newScaleY*sceneHEIGHT)/2);

            stageScale.setX(newScaleX);
            stageScale.setY(newScaleY);

        };
        primaryStage.widthProperty().addListener(stageSizeListener);
        primaryStage.heightProperty().addListener(stageSizeListener);


    }
}
