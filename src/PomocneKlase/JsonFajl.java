package PomocneKlase;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;

public class JsonFajl {
    private JSONObject objekat;
    private String putanja;


    public JsonFajl(String putanja){
        File f = new File(putanja);
        if(!f.exists()) {
            objekat= new JSONObject();
        }
        else {
            try (FileReader file = new FileReader(putanja)) {
                Scanner myReader = new Scanner(file);
                StringBuilder sb = new StringBuilder();
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    sb.append(data).append("\n");
                }
                String contents = sb.toString();
                JSONParser parser = new JSONParser();
                try {
                    objekat = (JSONObject) parser.parse(contents.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                myReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.putanja=putanja;
    }

    public void addString(String parametar, String vrednost){
        objekat.put(parametar, vrednost);
    }

    public void addInt(String parametar, int vrednost){
        objekat.put(parametar, vrednost);
    }

    public void addDouble(String parametar, double vrednost){
        objekat.put(parametar, vrednost);
    }

    public void addShapes(ArrayList<Node> listOfShapes){
        addInt("brojFigura", listOfShapes.size());
        for (int i=0; i<listOfShapes.size();i++) {

            if (listOfShapes.get(i) instanceof Line) {
                objekat.put("line-" + i + "-sX", ((Line) listOfShapes.get(i)).getStartX());
                objekat.put("line-" + i + "-sY", ((Line) listOfShapes.get(i)).getStartY());
                objekat.put("line-" + i + "-eX", ((Line) listOfShapes.get(i)).getEndX());
                objekat.put("line-" + i + "-eY", ((Line) listOfShapes.get(i)).getEndY());
                objekat.put("line-" + i + "-cS", ((Color)((Line) listOfShapes.get(i)).getStroke()).toString());

            } else if (listOfShapes.get(i) instanceof Ellipse) {
                objekat.put("ellipse-" + i + "-cX", ((Ellipse) listOfShapes.get(i)).getCenterX());
                objekat.put("ellipse-" + i + "-cY", ((Ellipse) listOfShapes.get(i)).getCenterY());
                objekat.put("ellipse-" + i + "-rX", ((Ellipse) listOfShapes.get(i)).getRadiusX());
                objekat.put("ellipse-" + i + "-rY", ((Ellipse) listOfShapes.get(i)).getRadiusY());
                objekat.put("ellipse-" + i + "-cF", ((Color)((Ellipse) listOfShapes.get(i)).getFill()).toString());
                objekat.put("ellipse-" + i + "-cS", ((Color)((Ellipse) listOfShapes.get(i)).getStroke()).toString());

            } else if (listOfShapes.get(i) instanceof Path) {
                objekat.put("re-" + i + "-x1", ((MoveTo)((Path) listOfShapes.get(i)).getElements().get(0)).getX() + listOfShapes.get(i).getLayoutX());
                objekat.put("re-" + i + "-y1", ((MoveTo)((Path) listOfShapes.get(i)).getElements().get(0)).getY() + listOfShapes.get(i).getLayoutY());
                objekat.put("re-" + i + "-x2", ((LineTo)((Path) listOfShapes.get(i)).getElements().get(1)).getX() + listOfShapes.get(i).getLayoutX());
                objekat.put("re-" + i + "-y2", ((LineTo)((Path) listOfShapes.get(i)).getElements().get(1)).getY() + listOfShapes.get(i).getLayoutY());
                objekat.put("re-" + i + "-x3", ((LineTo)((Path) listOfShapes.get(i)).getElements().get(2)).getX() + listOfShapes.get(i).getLayoutX());
                objekat.put("re-" + i + "-y3", ((LineTo)((Path) listOfShapes.get(i)).getElements().get(2)).getY() + listOfShapes.get(i).getLayoutY());
                objekat.put("re-" + i + "-x4", ((LineTo)((Path) listOfShapes.get(i)).getElements().get(3)).getX() + listOfShapes.get(i).getLayoutX());
                objekat.put("re-" + i + "-y4", ((LineTo)((Path) listOfShapes.get(i)).getElements().get(3)).getY() + listOfShapes.get(i).getLayoutY());
                objekat.put("re-" + i + "-cF", ((Color)((Path) listOfShapes.get(i)).getFill()).toString());
                objekat.put("re-" + i + "-cS", ((Color)((Path) listOfShapes.get(i)).getStroke()).toString());
            }
            else if (listOfShapes.get(i) instanceof Text){
                objekat.put("text-" + i + "-x", ((Text) listOfShapes.get(i)).getX());
                objekat.put("text-" + i + "-y", ((Text) listOfShapes.get(i)).getY());
                objekat.put("text-" + i + "-cF", ((Text) listOfShapes.get(i)).getFill().toString());
                objekat.put("text-" + i + "-s", ((Text) listOfShapes.get(i)).getFont().getSize());
                objekat.put("text-" + i + "-txt", ((Text) listOfShapes.get(i)).getText());
            }
            else if (listOfShapes.get(i) instanceof CubicCurve){
                objekat.put("curve-" + i + "-sX", ((CubicCurve) listOfShapes.get(i)).getStartX());
                objekat.put("curve-" + i + "-sY", ((CubicCurve) listOfShapes.get(i)).getStartY());
                objekat.put("curve-" + i + "-eX", ((CubicCurve) listOfShapes.get(i)).getEndX());
                objekat.put("curve-" + i + "-eY", ((CubicCurve) listOfShapes.get(i)).getEndY());
                objekat.put("curve-" + i + "-c1x", ((CubicCurve) listOfShapes.get(i)).getControlX1());
                objekat.put("curve-" + i + "-c1y", ((CubicCurve) listOfShapes.get(i)).getControlY1());
                objekat.put("curve-" + i + "-c2x", ((CubicCurve) listOfShapes.get(i)).getControlX2());
                objekat.put("curve-" + i + "-c2y", ((CubicCurve) listOfShapes.get(i)).getControlY2());
                objekat.put("curve-" + i + "-cS", ((Color)((CubicCurve) listOfShapes.get(i)).getStroke()).toString());
            }
            else if (listOfShapes.get(i) instanceof Polygon){
                int num=((Polygon)listOfShapes.get(i)).getPoints().size()/2;
                objekat.put("pg-" + i + "-num", num);
                for(int k=0;k<num;k++){
                    objekat.put("pg-" + i + "-x"+k, ((Polygon) listOfShapes.get(i)).getPoints().get(k*2) + listOfShapes.get(i).getLayoutX());
                    objekat.put("pg-" + i + "-y"+k, ((Polygon) listOfShapes.get(i)).getPoints().get(k*2+1) + listOfShapes.get(i).getLayoutY());
                    objekat.put("pg-" + i + "-cS", ((Color)((Polygon) listOfShapes.get(i)).getStroke()).toString());
                    objekat.put("pg-" + i + "-cF", ((Color)((Polygon) listOfShapes.get(i)).getFill()).toString());
                }
            }
            else if (listOfShapes.get(i) instanceof Polyline){
                int num=((Polyline)listOfShapes.get(i)).getPoints().size()/2;
                objekat.put("pl-" + i + "-num", num);
                for(int k=0;k<num;k++){
                    objekat.put("pl-" + i + "-x"+k, ((Polyline) listOfShapes.get(i)).getPoints().get(k*2) + listOfShapes.get(i).getLayoutX());
                    objekat.put("pl-" + i + "-y"+k, ((Polyline) listOfShapes.get(i)).getPoints().get(k*2+1) + listOfShapes.get(i).getLayoutY());
                    objekat.put("pl-" + i + "-cS", ((Color)((Polyline) listOfShapes.get(i)).getStroke()).toString());
                }

            }
        }
    }

    public ArrayList<Node> getDistractions(){
        FileReader reader = null;
        ArrayList<Node> list=new ArrayList<>();
        try {
            reader = new FileReader(putanja);
        JSONParser jsonParser = new JSONParser();
        this.objekat = (JSONObject) jsonParser.parse(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Object obj=this.objekat.get("brojFigura");
        if (obj==null) return list;
        int brojfigura = Integer.valueOf(obj.toString());

        for (int i=0; i<brojfigura; i++){
            String figura = this.objekat.get("figura"+i).toString();
            if (figura.equals("line")){
                double sX=Double.valueOf(this.objekat.get("line-"+i+"-sX").toString());
                double sY=Double.valueOf(this.objekat.get("line-"+i+"-sY").toString());
                double eX=Double.valueOf(this.objekat.get("line-"+i+"-eX").toString());
                double eY=Double.valueOf(this.objekat.get("line-"+i+"-eY").toString());
                String c = (this.objekat.get("line-"+i+"-cS").toString());
                Color cS= Color.web(c);
                Line l=new Line(sX,sY,eX,eY);
                l.setStrokeWidth(2);
                l.setStroke(cS);
                list.add(l);
            }
            else if (figura.equals("rectangle")){
                Path path=new Path();
                double x1=  Double.valueOf(this.objekat.get("re-" + i + "-x1").toString());
                double y1=  Double.valueOf(this.objekat.get("re-" + i + "-y1").toString());

                path.getElements().add(new MoveTo(x1,y1));
                for (int k=0; k<3 ; k++){
                    double x=  Double.valueOf(this.objekat.get("re-" + i + "-x"+(k+2)).toString());
                    double y=  Double.valueOf(this.objekat.get("re-" + i + "-y"+(k+2)).toString());
                    path.getElements().add(new LineTo(x,y));
                }
                path.getElements().add(new ClosePath());

                String S=  (this.objekat.get("re-" + i + "-cS").toString());
                String F=  (this.objekat.get("re-" + i + "-cF").toString());
                Color cS= Color.web(S);
                Color cF= Color.web(F);
                path.setStrokeWidth(2);

                path.setFill(cF);
                path.setStroke(cS);
                list.add(path);
            }
            else if (figura.equals("curve")){
                double sX=Double.valueOf(this.objekat.get("curve-"+i+"-sX").toString());
                double sY=Double.valueOf(this.objekat.get("curve-"+i+"-sY").toString());
                double c1x=Double.valueOf(this.objekat.get("curve-"+i+"-c1x").toString());
                double c1y=Double.valueOf(this.objekat.get("curve-"+i+"-c1y").toString());
                double c2x=Double.valueOf(this.objekat.get("curve-"+i+"-c2x").toString());
                double c2y=Double.valueOf(this.objekat.get("curve-"+i+"-c2y").toString());
                double eX=Double.valueOf(this.objekat.get("curve-"+i+"-eX").toString());
                double eY=Double.valueOf(this.objekat.get("curve-"+i+"-eY").toString());
                String c = (this.objekat.get("curve-"+i+"-cS").toString());
                Color cS= Color.web(c);
                CubicCurve l=new CubicCurve( sX, sY, c1x, c1y, c2x, c2y, eX, eY);
                l.setStroke(cS);
                l.setFill(null);
                l.setStrokeWidth(2);
                list.add(l);
            }
            else if (figura.equals("text")){
                double x=Double.valueOf(this.objekat.get("text-" + i + "-x").toString());
                double y=Double.valueOf(this.objekat.get("text-" + i + "-y").toString());
                double s=Double.valueOf(this.objekat.get("text-" + i + "-s").toString());
                String txt=this.objekat.get("text-" + i + "-txt").toString();
                String c = (this.objekat.get("text-"+i+"-cF").toString());
                Color cS= Color.web(c);
                Text text=new Text(x,y,txt);
                text.setFill(cS);
                text.setFont(Font.font(20));
                list.add(text);
            }
            else if (figura.equals("ellipse") || figura.equals("circle")){
                double cX= Double.valueOf(this.objekat.get("ellipse-" + i + "-cX").toString());
                double cY= Double.valueOf(this.objekat.get("ellipse-" + i + "-cY").toString());
                double rX= Double.valueOf(this.objekat.get("ellipse-" + i + "-rX").toString());
                double rY= Double.valueOf(this.objekat.get("ellipse-" + i + "-rY").toString());
                String c1 = (this.objekat.get("ellipse-"+i+"-cS").toString());
                Color cS= Color.web(c1);
                String c2 = (this.objekat.get("ellipse-"+i+"-cF").toString());
                Color cF= Color.web(c2);
                Ellipse ellipse=new Ellipse(cX,cY,rX,rY);
                ellipse.setStroke(cS);
                ellipse.setFill(cF);
                ellipse.setStrokeWidth(2);
                list.add(ellipse);
            }
            else if (figura.equals("pg")){
                int num = Integer.valueOf(this.objekat.get("pg-" + i + "-num").toString());
                Polygon polygon=new Polygon();
                for (int j=0;j<num;j++){
                    polygon.getPoints().add(Double.valueOf(this.objekat.get("pg-" + i + "-x"+j).toString()));
                    polygon.getPoints().add(Double.valueOf(this.objekat.get("pg-" + i + "-y"+j).toString()));
                }
                String c1 = (this.objekat.get("pg-"+i+"-cS").toString());
                Color cS= Color.web(c1);
                String c2 = (this.objekat.get("pg-"+i+"-cF").toString());
                Color cF= Color.web(c2);
                polygon.setStroke(cS);
                polygon.setStrokeWidth(2);
                polygon.setFill(cF);
                list.add(polygon);
            }
            else if (figura.equals("pl")){
                int num = Integer.valueOf(this.objekat.get("pl-" + i + "-num").toString());
                Polyline polygon=new Polyline();
                for (int j=0;j<num;j++){
                    polygon.getPoints().add(Double.valueOf(this.objekat.get("pl-" + i + "-x"+j).toString()));
                    polygon.getPoints().add(Double.valueOf(this.objekat.get("pl-" + i + "-y"+j).toString()));
                }
                String c1 = (this.objekat.get("pl-"+i+"-cS").toString());
                Color cS= Color.web(c1);
                polygon.setStrokeWidth(2);
                polygon.setStroke(cS);
                list.add(polygon);
            }
        }
        return list;
    }

    public ArrayList<SequentialTransition> getDistractionEffects(ArrayList<Node> nodeList){
        ArrayList<SequentialTransition> transitionList=new ArrayList<>();
        for(int i=0;i<nodeList.size(); i++){
                if (nodeList.get(i) instanceof Text) transitionList.add(new SequentialTransition());
                else {
                  int num = Integer.valueOf(this.objekat.get("bA"+i).toString());
                  if (num==0){ transitionList.add(new SequentialTransition()); continue;}
                  SequentialTransition sequentialTransition=new SequentialTransition(nodeList.get(i));
                  for (int j=0;j<num; j++){
                      String type=this.objekat.get("a-"+i+"-"+j).toString();
                      if (type.equals("t")){
                          double x = Double.valueOf(this.objekat.get("x-"+i+"-"+j).toString());
                          double y = Double.valueOf(this.objekat.get("y-"+i+"-"+j).toString());
                          double length=Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
                          TranslateTransition tt=new TranslateTransition(Duration.millis(length * 2));
                        tt.setByY(y);
                        tt.setByX(x);
                        sequentialTransition.getChildren().add(tt);
                      }
                      else if (type.equals("r")){
                          double an = Double.valueOf(this.objekat.get("an-"+i+"-"+j).toString());
                          RotateTransition rt=new RotateTransition(Duration.seconds(2));
                          rt.setByAngle(an);
                          sequentialTransition.getChildren().add(rt);
                      }
                      else if (type.equals("f")){
                          double op = Double.valueOf(this.objekat.get("op-"+i+"-"+j).toString());
                          FadeTransition ft=new FadeTransition(Duration.seconds(2));
                          ft.setByValue(op/100);
                          sequentialTransition.getChildren().add(ft);
                      }
                  }
                    sequentialTransition.setCycleCount(-1);
                    transitionList.add(sequentialTransition);
                }
        }
        return transitionList;
    }

    public int readInt(String objekat){

        FileReader reader = null;
        try {
            reader = new FileReader(putanja);
        JSONParser jsonParser = new JSONParser();
        this.objekat = (JSONObject) jsonParser.parse(reader);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return -1;
        } catch (ParseException parseException) {
            parseException.printStackTrace();
            return -1;
        }
        Object Jo=this.objekat.get(objekat);
        return (Jo==null)?-1: Integer.valueOf(Jo.toString());
    }

    public Double readDouble(String objekat){

        FileReader reader = null;
        try {
            reader = new FileReader(putanja);
            JSONParser jsonParser = new JSONParser();
            this.objekat = (JSONObject) jsonParser.parse(reader);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1.;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return -1.;
        } catch (ParseException parseException) {
            parseException.printStackTrace();
            return -1.;
        }
        Object Jo=this.objekat.get(objekat);
        return (Jo==null)?-1.: Double.valueOf(Jo.toString());
    }

    public String readString(String objekat){

        FileReader reader = null;
        try {
            reader = new FileReader(putanja);
            JSONParser jsonParser = new JSONParser();
            this.objekat = (JSONObject) jsonParser.parse(reader);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;

        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;

        } catch (ParseException parseException) {
            parseException.printStackTrace();
            return null;
        }
        Object Jo=this.objekat.get(objekat);
        return (Jo==null)?null: Jo.toString();
    }

    public ImagePattern getBackgroundPatern(){
        String string=readString("poz");
        if (string==null || string.equals("?")) return null;
        File f=new File(string);
        ImagePattern pozadina= null;
        try {
            pozadina = new ImagePattern(new Image(f.toURI().toURL().toString()),0,0,1,1,true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return pozadina;
    }

    public Color getBackgroundColor(){
        String string=readString("BOJA");
        if (string==null || string.equals("?") ) return null;
        Color c=Color.web(string);
        return c;
    }


public void write(){

    try(FileWriter file = new FileWriter(putanja)){
        file.write(objekat.toJSONString());
        file.flush();
    }catch(IOException e){
        e.printStackTrace();
    }
}

public void copyFrom(java.nio.file.Path path){
        JsonFajl jm2= new JsonFajl(path.toString());
        this.objekat= jm2.getObjekat();
        write();
}

    public void copyFrom(JsonFajl jm){
        this.objekat= jm.getObjekat();
        write();
    }

public JSONObject getObjekat(){
        return objekat;
}

    public String getPutanja(){
        return putanja;
    }




}
