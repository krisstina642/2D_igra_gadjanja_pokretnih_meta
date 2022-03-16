package sample;

import EditorStreljane.EditorStreljane;
import Komponente.*;
import MainDodatak.Akcije;
import MainDodatak.AkcijaMedjunivoa;
import MainDodatak.AkcijaPauze;
import MainDodatak.AkcijaStartMenija;
import Meni.*;
import javafx.animation.AnimationTimer;
import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.*;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import PomocneKlase.*;

import java.awt.event.InputEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class Main extends Application implements Akcije {

    private static final double BULLETSIZE = 50;
    private static final double sceneWIDTH = 800;
    private static final double sceneHEIGHT = 600;
    private static final double HEIGHT = sceneHEIGHT - BULLETSIZE - 10;
    private static final double STATUSBARHEIGHT = sceneHEIGHT-HEIGHT;

    private static Nisan NISAN;
    private static Scene SCENA;
    private static int KORAK_TASTATURE=10;
    private static Group GRUPA = new Group();
    private static List<Meta> listaMeta = new ArrayList<>();
    private static List<Meta> pripremnaListaMeta = new ArrayList<>();
    private static List<KeyCode> pritisnutiTasteri = new ArrayList<>();
    private static List<Metak> listaMetaka = new ArrayList<>();
    private Stage primaryStage;

    private static Brojac TRENUTNI_NIVO = new Brojac(1, "Nivo: ");
    private static Brojac BODOVI_UKUPNO = new Brojac(0, "Bodovi: ");
    private static Stoperica STOPERICA = new Stoperica();
    private static Brojac BODOVI_Nivo;
    private static Brojac BROJ_META_NIVO;
    private static int BROJ_META_NIVO_NEPROMENJIVI;
    private static int BROJ_POGODAKA_NIVO;
    private static int BROJ_PONAVLJANJA_NIVOA;

    private static int UKUPAN_BROJ_POGODAKA;
    private static int UKUPAN_BROJ_META;

    private static StatusIgre statusIgre = StatusIgre.OPCIJE;
    private static Rectangle POZADINA;

    private static MediaPlayer MUZIKA_MENI;
    private static AudioClip MUZIKA_IGRA;
    private static AudioClip PAUSE_CLICK;
    private static Paint START_POZADINA;
    private static AudioClip MUZIKA_METAK;
    private static AudioClip MUZIKA_RELOAD;
    private static AudioClip CENTER_HIT;
    private static AudioClip TARGET_HIT;
    private static int MAX_BONUS;
    private static Timeline oneSecond;
    private JsonFajl JSON_RECORDS;
    private JsonFajl CONFIG;

    private StartMeni START_MENI;
    private static int UKUPAN_BROJ_NIVOA = 6;
    private static ArrayList<PoljeRekordi> recordList;

    static String[] hardkodovaniNivoi =new String[]{
         "{\"re-0-cS\":\"0x000000ff\",\"re-4-y1\":312.0,\"re-4-cS\":\"0x000000ff\",\"text-6-cF\":\"0x000000ff\",\"re-3-cF\":\"0xffff00ff\",\"brojFigura\":7,\"figura2\":\"rectangle\",\"re-3-x2\":453.6,\"re-4-y4\":312.0,\"figura3\":\"rectangle\",\"re-3-x3\":377.6,\"text-6-y\":389.6,\"figura0\":\"rectangle\",\"re-0-y1\":66.4,\"re-4-y2\":412.8,\"text-6-x\":96.53203124999999,\"figura1\":\"rectangle\",\"re-3-x1\":453.6,\"re-4-y3\":412.8,\"figura6\":\"text\",\"re-0-y3\":244.0,\"re-0-y2\":244.0,\"figura4\":\"rectangle\",\"re-3-x4\":377.6,\"figura5\":\"text\",\"re-0-y4\":66.4,\"text-5-s\":20.0,\"re-1-cS\":\"0x000000ff\",\"re-4-cF\":\"0xffff00ff\",\"text-5-cF\":\"0x000000ff\",\"re-0-cF\":\"0xffff00ff\",\"re-4-x3\":44.0,\"re-4-x4\":44.0,\"text-6-txt\":\"u podešavanjima\",\"re-1-y2\":168.0,\"re-4-x1\":316.0,\"re-1-y1\":110.4,\"re-4-x2\":316.0,\"re-0-x2\":70.4,\"re-1-y4\":110.4,\"re-0-x1\":70.4,\"re-1-y3\":168.0,\"re-0-x4\":168.8,\"re-0-x3\":168.8,\"re-2-cS\":\"0x000000ff\",\"text-5-txt\":\"Možes obrisati prepreke\",\"re-1-cF\":\"0xffff00ff\",\"re-2-y1\":216.0,\"poz\":\"?\",\"re-1-x1\":288.0,\"re-2-y3\":303.2,\"text-5-y\":354.4,\"BOJA\":\"0xffff00ff\",\"re-2-y2\":500.0,\"text-5-x\":69.5400390625,\"re-1-x3\":700.0,\"re-1-x2\":288.0,\"re-2-y4\":216.0,\"re-1-x4\":700.0,\"re-3-cS\":\"0x000000ff\",\"text-6-s\":20.0,\"bA0\":0,\"bA2\":0,\"bA1\":0,\"bA4\":0,\"bA3\":0,\"re-2-cF\":\"0xffff00ff\",\"re-3-y3\":488.8,\"re-3-y4\":239.2,\"re-2-x2\":661.6,\"re-3-y1\":239.2,\"re-2-x1\":522.4,\"re-3-y2\":488.8,\"re-2-x4\":659.2,\"re-2-x3\":659.2,\"brMeta\":4, \"me-0-r\":3,\"me-0-s\":3,\"me-0-p\":\"M 100 75 L 750 75\", \"me-1-r\":3,\"me-1-s\":2,\"me-1-p\":\"M 100 175 L 750 175\", \"me-2-r\":3,\"me-2-s\":3,\"me-2-p\":\"M 100 275 L 750 275\", \"me-3-r\":3,\"me-3-s\":2,\"me-3-p\":\"M 100 375 L 750 375\",\"METE\":\"P\",\"brMetaka\":7}",
         "{\"re-0-cS\":\"0xa52a2aff\",\"ellipse-1-cS\":\"0xa52a2aff\",\"a-1-0\":\"r\",\"a-2-0\":\"r\",\"a-3-0\":\"r\",\"a-4-0\":\"r\",\"ellipse-2-rX\":8.399999999999977,\"ellipse-2-rY\":117.19999999999999,\"brojFigura\":3,\"ellipse-1-cF\":\"0x00ffffff\",\"figura2\":\"ellipse\",\"figura3\":\"ellipse\",\"poz\":\"?\",\"figura0\":\"rectangle\",\"re-0-y1\":76.8,\"ellipse-2-cY\":125.99999999999999,\"BOJA\":\"0x008000ff\",\"figura1\":\"ellipse\",\"an-2-0\":36000.0,\"re-0-y3\":509.6,\"an-1-0\":36000.0,\"re-0-y2\":397.6,\"figura4\":\"ellipse\",\"an-4-0\":36000.0,\"an-3-0\":3600.0,\"figura5\":\"circle\",\"re-0-y4\":499.2,\"ellipse-2-cS\":\"0xa52a2aff\",\"ellipse-2-cX\":668.4,\"ellipse-1-rX\":110.80000000000001,\"ellipse-1-rY\":10.0,\"bA0\":0,\"bA2\":1,\"bA1\":1,\"bA4\":1,\"bA3\":1,\"bA5\":0,\"re-0-cF\":\"0x00ffffff\",\"ellipse-2-cF\":\"0x00ffffff\",\"ellipse-1-cY\":124.4,\"ellipse-1-cX\":665.2,\"re-0-x2\":32.8,\"re-0-x1\":32.0,\"re-0-x4\":122.4,\"re-0-x3\":539.2, \"brMeta\":3, \"me-0-r\":2,\"me-0-s\":3,\"me-0-p\":\"M 642.0 490.0 L 158.0 490.0 A 108.0 108.0 90.0 0 1 50.0 382.0 L 50.0 158.0 A 108.0 108.0 90.0 0 1 108.0 50.0 L 642.0 50.0 A 108.0 108.0 90.0 0 1 750.0 108.0 L 750.0 382.0 A 108.0 108.0 90.0 0 1 642.0 490.0 \",\"me-1-r\":1,\"me-1-s\":3,\"me-1-p\":\"M 100.0 100.0 C 300.0 -13.33333333333333 500.0 213.33333333333333 700.0 100.0 C 500.0 213.33333333333333 900.0 326.66666666666666 700.0 440.0 C 500.0 553.3333333333333 300.0 326.66666666666666 100.0 440.0 C -100.0 326.66666666666666 300.0 213.33333333333333 100.0 100.0 \",\"me-2-r\":3,\"me-2-s\":1,\"me-2-p\":\"M 250.0 250.0 H 550.0 V 290.0 H 250.0 V 250.0 Z\",\"METE\":\"P\",\"brMetaka\":7}",
         "{\"a-4-1\":\"t\",\"a-4-2\":\"t\",\"a-6-0\":\"r\",\"a-4-3\":\"t\",\"line-5-sX\":604.8,\"a-4-4\":\"t\",\"line-5-sY\":359.2,\"a-4-0\":\"t\",\"line-1-sX\":633.6,\"line-1-sY\":81.6,\"an-6-0\":4000.0,\"brojFigura\":7,\"ellipse-4-rY\":32.2886977129769,\"line-6-cS\":\"0x000000ff\",\"ellipse-4-rX\":32.2886977129769,\"line-2-cS\":\"0x000000ff\",\"figura2\":\"line\",\"line-0-eX\":633.6,\"figura3\":\"text\",\"figura0\":\"line\",\"figura1\":\"line\",\"line-0-eY\":81.6,\"a-4-5\":\"t\",\"figura6\":\"line\",\"figura4\":\"circle\",\"figura5\":\"line\",\"text-3-s\":20.0,\"text-3-y\":68.0,\"text-3-x\":126.62128524780275,\"line-0-sX\":175.2,\"line-0-sY\":84.8,\"x-4-0\":-22.399993896484375,\"x-4-1\":-70.4,\"x-4-2\":-26.400000000000006,\"x-4-3\":-58.39999999999998,\"line-5-cS\":\"0x000000ff\",\"x-4-4\":-11.200000000000017,\"y-4-5\":-305.59999999999997,\"x-4-5\":-56.0,\"line-1-cS\":\"0x000000ff\",\"y-4-3\":-272.0,\"y-4-4\":266.4,\"y-4-1\":-184.79999999999995,\"y-4-2\":174.39999999999998,\"y-4-0\":91.19998779296873,\"a-5-0\":\"r\",\"line-6-eY\":447.2,\"an-5-0\":4000.0,\"line-2-eX\":383.2,\"line-0-cS\":\"0x000000ff\",\"line-6-eX\":614.4,\"poz\":\"?\",\"line-2-eY\":436.8,\"BOJA\":\"0xdcdcdcff\",\"line-6-sX\":699.2,\"line-6-sY\":330.4,\"text-3-txt\":\"BRZOO!!\",\"ellipse-4-cS\":\"0x000000ff\",\"ellipse-4-cY\":407.20000000000005,\"ellipse-4-cX\":310.4,\"line-2-sY\":86.4,\"line-2-sX\":170.4,\"bA0\":0,\"ellipse-4-cF\":\"0xdcdcdcff\",\"line-5-eX\":713.6,\"line-5-eY\":424.0,\"bA2\":0,\"bA1\":0,\"bA4\":6,\"text-3-cF\":\"0x000000ff\",\"bA6\":1,\"bA5\":1,\"line-1-eX\":389.6,\"line-1-eY\":437.6,\"METE\":\"A\",\"brMetaka\":9,\"brMeta\":7,\"me-0-p\":\"M 200 100 L 600 100 L 400 400 z\",\"me-0-r\":2,\"me-0-s\":1,\"me-1-p\":\"M 200 100 L 600 100 L 400 400 z\",\"me-1-r\":2,\"me-1-s\":1,\"me-2-p\":\"M 600 100 L 200 100 L 400 400 z\",\"me-2-r\":2,\"me-2-s\":1,\"me-3-p\":\"M 200 100 L 600 100 L 400 400 z\",\"me-3-r\":2,\"me-3-s\":1,\"me-4-p\":\"M 200 100 L 600 100 L 400 400 z\",\"me-4-r\":2,\"me-4-s\":1,\"me-5-p\":\"M 600 100 L 200 100 L 400 400 z\",\"me-5-r\":2,\"me-5-s\":1,\"me-6-p\":\"M 200 100 L 600 100 L 400 400 z\",\"me-6-r\":2,\"me-6-s\":1\n}",
         "{\"a-0-5\":\"t\",\"a-2-3\":\"t\",\"a-2-4\":\"t\",\"a-2-5\":\"t\",\"ellipse-1-cS\":\"0x000000ff\",\"a-0-1\":\"t\",\"a-0-2\":\"t\",\"a-2-0\":\"t\",\"a-0-3\":\"t\",\"a-2-1\":\"t\",\"a-0-4\":\"t\",\"a-2-2\":\"t\",\"ellipse-0-rY\":50.47771785649584,\"a-0-0\":\"t\",\"ellipse-0-rX\":50.47771785649584,\"brojFigura\":3,\"ellipse-1-cF\":\"0xffc0cbff\",\"figura2\":\"circle\",\"figura0\":\"circle\",\"ellipse-2-cY\":267.6,\"figura1\":\"circle\",\"ellipse-2-cS\":\"0x000000ff\",\"ellipse-2-cX\":404.4,\"ellipse-1-rX\":0.0,\"ellipse-1-rY\":0.0,\"x-0-2\":1.5999999999999943,\"x-2-0\":-5.200009155273449,\"x-0-3\":660.8,\"x-2-1\":324.00000000000006,\"x-0-4\":-7.199999999999932,\"x-2-2\":-16.0,\"x-0-5\":-319.20000000000005,\"x-2-3\":-626.4000000000001,\"x-2-4\":-8.799999999999997,\"x-2-5\":332.0,\"ellipse-2-cF\":\"0xfffacdff\",\"y-2-5\":-200.8,\"y-0-5\":212.00000000000003,\"y-2-3\":-6.399999999999999,\"y-2-4\":411.2,\"y-0-3\":4.0,\"y-2-1\":-0.8000000000000114,\"y-0-4\":-414.4,\"y-2-2\":-403.2,\"y-0-1\":0.0,\"y-0-2\":395.2,\"y-2-0\":200.40000915527344,\"y-0-0\":-195.2,\"ellipse-2-rX\":52.218004557815114,\"ellipse-2-rY\":52.218004557815114,\"x-0-0\":6.399999999999977,\"x-0-1\":-342.4,\"ellipse-0-cX\":404.0,\"poz\":\"?\",\"BOJA\":\"0xffc0cbff\",\"ellipse-0-cY\":268.0,\"ellipse-0-cS\":\"0x000000ff\",\"bA0\":6,\"bA2\":6,\"bA1\":0,\"ellipse-0-cF\":\"0xffc0cbff\",\"ellipse-1-cY\":0.0,\"ellipse-1-cX\":0.0,\"METE\":\"A\",\"brMetaka\":15,\"brMeta\":10,\"me-0-p\":\"M 100.0 100.0 C 300.0 -13.33333333333333 500.0 213.33333333333333 700.0 100.0 C 500.0 213.33333333333333 900.0 326.66666666666666 700.0 440.0 C 500.0 553.3333333333333 300.0 326.66666666666666 100.0 440.0 C -100.0 326.66666666666666 300.0 213.33333333333333 100.0 100.0 \",\"me-0-r\":3,\"me-0-s\":3,\"me-1-p\":\"M 700.0 440.0 C 500.0 553.3333333333333 300.0 326.66666666666666 100.0 440.0 C -100.0 326.66666666666666 300.0 213.33333333333333 100.0 100.0 C 300.0 -13.33333333333333 500.0 213.33333333333333 700.0 100.0 C 500.0 213.33333333333333 900.0 326.66666666666666 700.0 440.0 \",\"me-1-r\":3,\"me-1-s\":3,\"me-2-p\":\"M 700.0 100.0 C 500.0 213.33333333333333 900.0 326.66666666666666 700.0 440.0 C 500.0 553.3333333333333 300.0 326.66666666666666 100.0 440.0 C -100.0 326.66666666666666 300.0 213.33333333333333 100.0 100.0 C 300.0 -13.33333333333333 500.0 213.33333333333333 700.0 100.0\",\"me-2-r\":3,\"me-2-s\":3,\"me-3-p\":\"M 100.0 440.0 C -100.0 326.66666666666666 300.0 213.33333333333333 100.0 100.0 C 300.0 -13.33333333333333 500.0 213.33333333333333 700.0 100.0 C 500.0 213.33333333333333 900.0 326.66666666666666 700.0 440.0 C 500.0 553.3333333333333 300.0 326.66666666666666 100.0 440.0 \",\"me-3-r\":3,\"me-3-s\":3,\"me-4-p\":\"M 100.0 100.0 C 300.0 -13.33333333333333 500.0 213.33333333333333 700.0 100.0 C 500.0 213.33333333333333 900.0 326.66666666666666 700.0 440.0 C 500.0 553.3333333333333 300.0 326.66666666666666 100.0 440.0 C -100.0 326.66666666666666 300.0 213.33333333333333 100.0 100.0 \",\"me-4-r\":3,\"me-4-s\":3,\"me-5-p\":\"M 700.0 440.0 C 500.0 553.3333333333333 300.0 326.66666666666666 100.0 440.0 C -100.0 326.66666666666666 300.0 213.33333333333333 100.0 100.0 C 300.0 -13.33333333333333 500.0 213.33333333333333 700.0 100.0 C 500.0 213.33333333333333 900.0 326.66666666666666 700.0 440.0 \",\"me-5-r\":3,\"me-5-s\":3,\"me-6-p\":\"M 700.0 100.0 C 500.0 213.33333333333333 900.0 326.66666666666666 700.0 440.0 C 500.0 553.3333333333333 300.0 326.66666666666666 100.0 440.0 C -100.0 326.66666666666666 300.0 213.33333333333333 100.0 100.0 C 300.0 -13.33333333333333 500.0 213.33333333333333 700.0 100.0\",\"me-6-r\":3,\"me-6-s\":3,\"me-7-p\":\"M 100.0 440.0 C -100.0 326.66666666666666 300.0 213.33333333333333 100.0 100.0 C 300.0 -13.33333333333333 500.0 213.33333333333333 700.0 100.0 C 500.0 213.33333333333333 900.0 326.66666666666666 700.0 440.0 C 500.0 553.3333333333333 300.0 326.66666666666666 100.0 440.0 \",\"me-7-r\":3,\"me-7-s\":3,\"me-8-p\":\"M 100.0 100.0 C 300.0 -13.33333333333333 500.0 213.33333333333333 700.0 100.0 C 500.0 213.33333333333333 900.0 326.66666666666666 700.0 440.0 C 500.0 553.3333333333333 300.0 326.66666666666666 100.0 440.0 C -100.0 326.66666666666666 300.0 213.33333333333333 100.0 100.0 \",\"me-8-r\":3,\"me-8-s\":3,\"me-9-p\":\"M 700.0 440.0 C 500.0 553.3333333333333 300.0 326.66666666666666 100.0 440.0 C -100.0 326.66666666666666 300.0 213.33333333333333 100.0 100.0 C 300.0 -13.33333333333333 500.0 213.33333333333333 700.0 100.0 C 500.0 213.33333333333333 900.0 326.66666666666666 700.0 440.0 \",\"me-9-r\":3,\"me-9-s\":3}",
         "{\"op-1-1\": 50.0,\"op-1-0\": -50.0,\"op-3-1\": 50.0,\"re-0-cS\": \"0xffffffff\",\"op-3-0\": -50.0,\"a-0-1\": \"f\",\"a-2-0\": \"f\",\"a-2-1\": \"f\",\"a-0-0\": \"f\",\"re-3-cF\": \"0x008000ff\",\"brojFigura\": 4,\"figura2\": \"rectangle\",\"re-3-x2\": 20.8,\"figura3\": \"rectangle\",\"re-3-x3\": 132.0,\"figura0\": \"rectangle\",\"re-0-y1\": 9.6,\"figura1\": \"rectangle\",\"re-3-x1\": 20.8,\"re-0-y3\": 104.8,\"re-0-y2\": 104.8,\"re-3-x4\": 132.0,\"re-0-y4\": 9.6,\"re-1-cS\": \"0xffffffff\",\"re-0-cF\": \"0x008000ff\",\"re-1-y2\": 420.0,\"re-1-y1\": 112.8,\"re-0-x2\": 142.4,\"re-1-y4\": 112.8,\"re-0-x1\": 142.4,\"re-1-y3\": 420.0,\"re-0-x4\": 677.6,\"re-0-x3\": 677.6,\"op-2-0\": -50.0,\"re-2-cS\": \"0xffffffff\",\"op-0-1\": 50.0,\"op-2-1\": 50.0,\"a-1-0\": \"f\",\"a-1-1\": \"f\",\"a-3-0\": \"f\",\"a-3-1\": \"f\",\"op-0-0\": -50.0,\"re-1-cF\": \"0x008000ff\",\"re-2-y1\": 426.4,\"poz\": \"?\",\"re-1-x1\": 660.0,\"re-2-y3\": 516.0,\"BOJA\": \"0x008000ff\",\"re-2-y2\": 516.0,\"re-1-x3\": 771.2,\"re-1-x2\": 660.0,\"re-2-y4\": 426.4,\"re-1-x4\": 771.2,\"re-3-cS\": \"0xffffffff\",\"bA0\": 2,\"bA2\": 2,\"bA1\": 2,\"bA3\": 2,\"re-2-cF\": \"0x008000ff\",\"re-3-y3\": 448.8,\"re-3-y4\": 86.4,\"re-2-x2\": 671.2,\"re-3-y1\": 86.4,\"re-2-x1\": 671.2,\"re-3-y2\": 448.8,\"re-2-x4\": 141.6,\"re-2-x3\": 141.6,\"brMeta\": 9,\"me-0-r\": 2,\"me-0-s\": 3,\"me-0-p\": \"M 750.0 70.0 L 50.0 70.0 L 50.0 170.0 L 750.0 170.0 L 750.0 270.0 L 50.0 270.0 L 50.0 370.0 L 750.0 370.0 L 750.0 470.0 L 50.0 470.0\",\"me-1-r\": 2,\"me-1-s\": 3,\"me-1-p\": \"M 50.0 50.0 L 50.0 490.0 L 150.0 490.0 L 150 50 L 250.0 50.0 L 250.0 490.0 L 350.0 490.0 L 350 50.0 L 450.0 50.0 L 450.0 490.0 L 550.0 490.0 L 550.0 50.0 L 650.0 50.0 L 650.0 490.0 L 750.0 490.0 L 750.0 50.0\",\"me-2-r\": 2,\"me-2-s\": 3,\"me-2-p\": \"M 50.0 470.0 L 750.0 470.0 L 750.0 370.0 L 50.0 370.0 L 50.0 270.0 L 750.0 270.0 L 750.0 170.0 L 50.0 170.0 L 50.0 70.0 L 750.0 70.0\",\"me-3-r\": 2,\"me-3-s\": 3,\"me-3-p\": \"M 750.0 490.0 L 750.0 50.0 L 650.0 50.0 L 650.0 490.0 L 550.0 490.0 L 550.0 50.0 L 450.0 50.0 L 450.0 490.0 L 350.0 490.0 L 350 50.0 L 250.0 50.0 L 250.0 490.0 L 150.0 490.0 L 150.0 50.0 L 50.0 50.0 L 50.0 490.0\",\"me-4-r\": 2,\"me-4-s\": 3,\"me-4-p\": \"M 750.0 70.0 L 50.0 70.0 L 50.0 170.0 L 750.0 170.0 L 750.0 270.0 L 50.0 270.0 L 50.0 370.0 L 750.0 370.0 L 750.0 470.0 L 50.0 470.0\",\"me-5-r\": 2,\"me-5-s\": 3,\"me-5-p\": \"M 50.0 50.0 L 50.0 490.0 L 150.0 490.0 L 150 50 L 250.0 50.0 L 250.0 490.0 L 350.0 490.0 L 350 50.0 L 450.0 50.0 L 450.0 490.0 L 550.0 490.0 L 550.0 50.0 L 650.0 50.0 L 650.0 490.0 L 750.0 490.0 L 750.0 50.0\",\"me-6-r\": 2,\"me-6-s\": 3,\"me-6-p\": \"M 50.0 470.0 L 750.0 470.0 L 750.0 370.0 L 50.0 370.0 L 50.0 270.0 L 750.0 270.0 L 750.0 170.0 L 50.0 170.0 L 50.0 70.0 L 750.0 70.0\",\"me-7-r\": 2,\"me-7-s\": 3,\"me-7-p\": \"M 750.0 490.0 L 750.0 50.0 L 650.0 50.0 L 650.0 490.0 L 550.0 490.0 L 550.0 50.0 L 450.0 50.0 L 450.0 490.0 L 350.0 490.0 L 350 50.0 L 250.0 50.0 L 250.0 490.0 L 150.0 490.0 L 150.0 50.0 L 50.0 50.0 L 50.0 490.0\",\"me-8-r\": 2,\"me-8-s\": 3,\"me-8-p\": \"M 750.0 70.0 L 50.0 70.0 L 50.0 170.0 L 750.0 170.0 L 750.0 270.0 L 50.0 270.0 L 50.0 370.0 L 750.0 370.0 L 750.0 470.0 L 50.0 470.0\",\"METE\": \"A\",\"brMetaka\": 13}",
         "{\"re-0-cS\": \"0x000000ff\",\"a-1-0\": \"r\",\"a-2-0\": \"r\",\"line-1-sX\": 517.6,\"line-1-sY\": 156.0,\"a-0-0\": \"r\",\"brojFigura\": 3,\"line-2-eX\": 284.8,\"line-2-cS\": \"0x000000ff\",\"figura2\": \"line\",\"poz\": \"?\",\"figura0\": \"rectangle\",\"an-0-0\": -5000.0,\"re-0-y1\": 200.0,\"line-2-eY\": 158.4,\"BOJA\": \"0x9acd32ff\",\"figura1\": \"line\",\"an-2-0\": -5000.0,\"re-0-y3\": 333.6,\"an-1-0\": -5000.0,\"re-0-y2\": 333.6,\"re-0-y4\": 200.0,\"line-2-sY\": 386.4,\"line-2-sX\": 512.8,\"bA0\": 1,\"bA2\": 1,\"bA1\": 1,\"re-0-cF\": \"0xff0000ff\",\"line-1-cS\": \"0x000000ff\",\"line-1-eX\": 287.2,\"line-1-eY\": 371.2,\"re-0-x2\": 327.2,\"re-0-x1\": 327.2,\"re-0-x4\": 469.6,\"re-0-x3\": 469.6,\"brMeta\": 8,\"me-0-r\": 3,\"me-0-s\": 2,\"me-0-p\": \"M 400.0 270.0 L 500.0 270.0 L 500.0 170.0 L 300.0 170.0 L 300.0 370.0 L 600.0 370.0 L 600.0 70.0 L 200.0 70.0 L 200 470.0 L 700.0 470.0 \",\"me-1-r\": 2,\"me-1-s\": 2,\"me-1-p\": \"M 400.0 270.0 L 400.0 370.0 L 500.0 370.0 L 500.0 170.0 L 300.0 170.0 L 300.0 470.0 L 600.0 470.0 L 600.0 70.0 L 70.0 70.0\",\"me-2-r\": 1,\"me-2-s\": 2,\"me-2-p\": \"M 400.0 270.0 L 300 270 L 300 370 L 500 370 L 500 170 L 200 170 L 200 470 L 600 470 L 600 70 L 100 70 L 100 470\",\"me-3-r\": 3,\"me-3-s\": 2,\"me-3-p\": \"M 400.0 270.0 L 400 170 L 300 170 L 300 370 L 500 370 L 500 70 L 200 70 L 200 470 L 700 470 L 700 70\",\"me-4-r\": 2,\"me-4-s\": 2,\"me-4-p\": \"M 400.0 270.0 L 500.0 270.0 L 500.0 170.0 L 300.0 170.0 L 300.0 370.0 L 600.0 370.0 L 600.0 70.0 L 200.0 70.0 L 200 470.0 L 700.0 470.0 \",\"me-5-r\": 1,\"me-5-s\": 2,\"me-5-p\": \"M 400.0 270.0 L 400.0 370.0 L 500.0 370.0 L 500.0 170.0 L 300.0 170.0 L 300.0 470.0 L 600.0 470.0 L 600.0 70.0 L 70.0 70.0\",\"me-6-r\": 3,\"me-6-s\": 2,\"me-6-p\": \"M 400.0 270.0 L 300 270 L 300 370 L 500 370 L 500 170 L 200 170 L 200 470 L 600 470 L 600 70 L 100 70 L 100 470\",\"me-7-r\": 2,\"me-7-s\": 2,\"me-7-p\": \"M 400.0 270.0 L 400 170 L 300 170 L 300 370 L 500 370 L 500 70 L 200 70 L 200 470 L 700 470 L 700 70\",\"METE\": \"A\",\"brMetaka\": 13}\n"
    };

    private void pomeriNisanTastatura(){

        if (pritisnutiTasteri.contains(KeyCode.LEFT) && pritisnutiTasteri.contains(KeyCode.RIGHT)
                && pritisnutiTasteri.contains(KeyCode.UP) && pritisnutiTasteri.contains(KeyCode.DOWN)) return;
        else if (pritisnutiTasteri.contains(KeyCode.LEFT) && pritisnutiTasteri.contains(KeyCode.RIGHT)
                && pritisnutiTasteri.contains(KeyCode.UP) ) NISAN.pomeriZa(0, -KORAK_TASTATURE);
        else if (pritisnutiTasteri.contains(KeyCode.LEFT) && pritisnutiTasteri.contains(KeyCode.RIGHT)
                && pritisnutiTasteri.contains(KeyCode.DOWN) ) NISAN.pomeriZa(0, KORAK_TASTATURE);
        else if (pritisnutiTasteri.contains(KeyCode.LEFT) && pritisnutiTasteri.contains(KeyCode.DOWN)
                && pritisnutiTasteri.contains(KeyCode.UP) ) NISAN.pomeriZa(-KORAK_TASTATURE, 0 );
        else if (pritisnutiTasteri.contains(KeyCode.DOWN) && pritisnutiTasteri.contains(KeyCode.RIGHT)
                && pritisnutiTasteri.contains(KeyCode.UP) ) NISAN.pomeriZa(KORAK_TASTATURE, 0);
        else if (pritisnutiTasteri.contains(KeyCode.DOWN) && pritisnutiTasteri.contains(KeyCode.RIGHT))
                                                             NISAN.pomeriZa(KORAK_TASTATURE, KORAK_TASTATURE);
        else if (pritisnutiTasteri.contains(KeyCode.UP) && pritisnutiTasteri.contains(KeyCode.RIGHT))
                                                            NISAN.pomeriZa(KORAK_TASTATURE, -KORAK_TASTATURE);
        else if (pritisnutiTasteri.contains(KeyCode.LEFT) && pritisnutiTasteri.contains(KeyCode.RIGHT))
                                                            return;
        else if (pritisnutiTasteri.contains(KeyCode.DOWN) && pritisnutiTasteri.contains(KeyCode.LEFT))
                                                            NISAN.pomeriZa(-KORAK_TASTATURE, KORAK_TASTATURE);
        else if (pritisnutiTasteri.contains(KeyCode.UP) && pritisnutiTasteri.contains(KeyCode.LEFT))
                                                            NISAN.pomeriZa(-KORAK_TASTATURE, -KORAK_TASTATURE);
        else if (pritisnutiTasteri.contains(KeyCode.UP)) NISAN.pomeriZa(0, -KORAK_TASTATURE);

        else if (pritisnutiTasteri.contains(KeyCode.DOWN)) NISAN.pomeriZa(0, KORAK_TASTATURE);
        else if (pritisnutiTasteri.contains(KeyCode.LEFT)) NISAN.pomeriZa(-KORAK_TASTATURE, 0);
        else  NISAN.pomeriZa(KORAK_TASTATURE, 0);

        NISAN.getTransforms().setAll(
                new Translate(NISAN.getLayoutX(), NISAN.getLayoutY())
        );

        try {
            java.awt.Robot robot = new java.awt.Robot();
            robot.mouseMove((int)(NISAN.getX()+SCENA.getWindow().getX()+SCENA.getX()), (int)(NISAN.getY()+SCENA.getWindow().getY()+SCENA.getY()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void resetIgre(){
        TRENUTNI_NIVO.setBroj(1);
        BODOVI_UKUPNO.setBroj(0);
        UKUPAN_BROJ_POGODAKA=0;
        UKUPAN_BROJ_META=0;
        STOPERICA.reset();
    }

    @Override
    public void azurirajPogodjeneMete(int bodovi, boolean center) {
        BODOVI_Nivo.add(bodovi);
        BROJ_META_NIVO.dec();
        BROJ_POGODAKA_NIVO++;
        if (center) CENTER_HIT.play();
        else TARGET_HIT.play();
        if (BROJ_META_NIVO.getBroj() == 0) {
            statusIgre = StatusIgre.USPESNO_UNISTENO;
        }
    }

    @Override
    public boolean getFullScreen() {
        return primaryStage.isFullScreen();
    }

    @Override
    public void setFullScreen() {
        if (primaryStage.isFullScreen()){
            CONFIG.addInt("Fullscrn", 0);
        }
        else CONFIG.addInt("Fullscrn", 1);
        primaryStage.setFullScreen(!primaryStage.isFullScreen());
    }

    private void azurirajBrojMeta(ArrayList<Meta> lista) {
        int br= lista.size();
        BROJ_META_NIVO.setBroj(br);
        BROJ_META_NIVO_NEPROMENJIVI=br;
        if (!(statusIgre == StatusIgre.PONOVLJEN_NIVO)) UKUPAN_BROJ_META += br;
        for (Meta meta : lista) {
            pripremnaListaMeta.add(meta);
            MAX_BONUS += meta.getMaxBonus();
        }
    }

    private void ukloniMetak() {

        if (listaMetaka.isEmpty()) return;

        MUZIKA_METAK.play();
        MUZIKA_RELOAD.play();

        Metak metak = listaMetaka.get(listaMetaka.size() - 1);
        listaMetaka.remove(listaMetaka.size() - 1);
        GRUPA.getChildren().remove(metak);

        if (listaMetaka.isEmpty() && statusIgre!=StatusIgre.USPESNO_UNISTENO) {
            zaustaviMete();
            statusIgre = StatusIgre.POTROSENA_MUNICIJA;
        }
    }

    private void pokretacTajmeraNivoa(){
        AnimationTimer pTN = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (statusIgre == StatusIgre.PONOVLJEN_NIVO || statusIgre == StatusIgre.IGRA) {
                    STOPERICA.start();
                    okidacKrajaNivoa();
                    this.stop();
                }
            }
        };
        MUZIKA_MENI.play();
        MUZIKA_IGRA.stop();
        pTN.start();
    }
    private void okidacKrajaNivoa(){
        AnimationTimer oKN = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (statusIgre==StatusIgre.USPESNO_UNISTENO){
                    ukloniMetak();
                }
                if (statusIgre != StatusIgre.IGRA && statusIgre != StatusIgre.PONOVLJEN_NIVO) {
                    STOPERICA.stop();
                   if (statusIgre!=StatusIgre.PAUZA) medjunivo();
                    pokretacTajmeraNivoa();
                    this.stop();
                }
            }
        };
        MUZIKA_MENI.stop();
        MUZIKA_IGRA.play();
        oKN.start();
    }

    private void Init() {
        // inicijalizacija zvucnih efekata

        PAUSE_CLICK = new AudioClip(this.getClass().getResource("sounds/meni.wav").toExternalForm());
        MUZIKA_MENI.play();
        MUZIKA_MENI.setCycleCount(-1);
        MUZIKA_IGRA = new AudioClip(this.getClass().getResource("sounds/gameplay.mp3").toExternalForm());
        MUZIKA_IGRA.setCycleCount(-1);
        MUZIKA_METAK = new AudioClip(this.getClass().getResource("sounds/eff2.mp3").toExternalForm());
        MUZIKA_RELOAD = new AudioClip(this.getClass().getResource("sounds/reload.mp3").toExternalForm());
        MUZIKA_RELOAD.setRate(1.5);
        TARGET_HIT = new AudioClip(this.getClass().getResource("sounds/targetHit.mp3").toExternalForm());
        TARGET_HIT.setRate(2.5);
        CENTER_HIT = new AudioClip(this.getClass().getResource("sounds/centerHit.wav").toExternalForm());

        pokretacTajmeraNivoa();

        SCENA.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (statusIgre == StatusIgre.IGRA || statusIgre == StatusIgre.PONOVLJEN_NIVO) ukloniMetak();
            else {
                 PAUSE_CLICK.play();
            }
        });

        SCENA.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
            if (pritisnutiTasteri.size()>0) return;
            NISAN.setKoordinate(event.getSceneX(), event.getSceneY());
        });

        SCENA.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> NISAN.setKoordinate(event.getSceneX(), event.getSceneY()));

        SCENA.addEventHandler(KeyEvent.KEY_RELEASED, event -> pritisnutiTasteri.remove(event.getCode()));

        SCENA.addEventHandler(KeyEvent.KEY_PRESSED, event -> {

            if (event.getCode().equals(KeyCode.SPACE)) {

                try {
                    java.awt.Robot robot = new java.awt.Robot();
                    robot.mouseMove((int)(NISAN.getX()+SCENA.getWindow().getX()+SCENA.getX()), (int)(NISAN.getY()+SCENA.getWindow().getY()+SCENA.getY()));
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (event.getCode().equals(KeyCode.ESCAPE)) {
               if (statusIgre == StatusIgre.IGRA || statusIgre == StatusIgre.PONOVLJEN_NIVO){
                   pauzirajMete();
                   GRUPA.getChildren().add(PauzaMeni.dodajMeni(this, sceneWIDTH, HEIGHT, statusIgre));
                   statusIgre = StatusIgre.PAUZA;
               }

            }
           if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.RIGHT)) {
                if (!pritisnutiTasteri.contains(event.getCode())) pritisnutiTasteri.add(event.getCode());
                pomeriNisanTastatura();
            }

        });

        recordList =new ArrayList<>();
        File dir = new File("config");
        if(!dir.exists()) new File("config").mkdirs();

        File f = new File("config/rekordi.json");
        if(!f.exists()) {
        JSON_RECORDS =new JsonFajl(Paths.get("config/rekordi.json").toString());
            for (int i=0; i<10; i++) {
                JSON_RECORDS.addInt("" + i, 1000 - i * 100);
                JSON_RECORDS.addString("" + i + "i", "Anonymous" + (i + 1));
                recordList.add(new PoljeRekordi(1000 - i * 100, "Anonymous" + (i + 1), 200));
                JSON_RECORDS.write();
            }
        }
       else{
            JSON_RECORDS =new JsonFajl(Paths.get("config/rekordi.json").toString());
            for (int i=0; i<10; i++){
              int d = JSON_RECORDS.readInt(""+i);
                String s = JSON_RECORDS.readString(""+i+"i");
            recordList.add(new PoljeRekordi( d, s, 200));
        }}

        File f2 = new File("config/config.json");
        if(!f2.exists()) {
            CONFIG = new JsonFajl(Paths.get("config/config.json").toString());
            CONFIG.addDouble("Music", getJacinaMuzike());
            CONFIG.addDouble("Sounds", getJacinaEfekata());
            CONFIG.addInt("Broj_nivoa", 6);
            CONFIG.addInt("Fullscrn", 0);
            CONFIG.addDouble("Width", -1.);
            CONFIG.addDouble("Height", -1.);
            CONFIG.addDouble("X", -1.);
            CONFIG.addDouble("Y", -1.);
            CONFIG.write();
        }
        else{
            CONFIG =new JsonFajl(Paths.get("config/config.json").toString());
            double  d = CONFIG.readDouble("Music");           setJacinaMuzike(d);
                    d = CONFIG.readDouble("Sounds");          setJacinaEfekata(d);
            }

        for (int i=0; i<UKUPAN_BROJ_NIVOA; i++){
            File fil = new File("config/Nivo"+(i+1)+".json");
            if(!fil.exists()) ucitajHardkodovaniNivo(i+1);
            }

        int full = CONFIG.readInt("Fullscrn");
        START_MENI = new StartMeni(this, sceneWIDTH, HEIGHT, recordList, full);
    }


    private void ucitajHardkodovaniNivo(int i){
        JsonFajl m = new JsonFajl(Paths.get("config/Nivo"+i+".json").toString());
        m.write();
        FileWriter myWriter;
        try {
            myWriter = new FileWriter("config/Nivo"+i+".json");
        myWriter.write(hardkodovaniNivoi[i-1]);
        myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void pokreniNizMeta(ArrayList<Path> putanja, ArrayList<TezinaNivoa> brzinaKretanja,int brojPonavljanja, int nivo){

        if (pripremnaListaMeta.isEmpty()) return;
        oneSecond = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (brojPonavljanja==BROJ_PONAVLJANJA_NIVOA && TRENUTNI_NIVO.getBroj()==nivo && !pripremnaListaMeta.isEmpty()) {
                System.out.println(pripremnaListaMeta.size()+"  " +putanja.size()+ "   "+ brzinaKretanja.size());
                Meta m = pripremnaListaMeta.remove(0);
                m.dodajUGrupuIListu(GRUPA, listaMeta);
                kretanjePoRandomPutanji(m, putanja.remove(0), brzinaKretanja.remove(0), false);
                pokreniNizMeta(putanja,brzinaKretanja,brojPonavljanja, nivo);
            }
        }));
        oneSecond.play();
    }

    private void dodajMetke(int broj){
            listaMetaka=new ArrayList<>();
                 Metak metak;
            for (int i=0;i<broj;i++){
                metak=new Metak(BULLETSIZE);
                metak.getTransforms().addAll(
                  new Translate(sceneWIDTH -BULLETSIZE/2 - i*(BULLETSIZE/2),sceneHEIGHT-5)
                );
                GRUPA.getChildren().add(metak);
                listaMetaka.add(metak);
            }
    }

    private void kretanjePoRandomPutanji(Meta m, Path p, TezinaNivoa tezina, boolean reverse){

        int tezinaVr= TezinaNivoa.vrednostTezineNivoa(tezina)*3;
       PathTransition pt=new PathTransition(Duration.seconds(tezinaVr),p,m);
        pt.setAutoReverse(reverse);
        if (reverse) {
            pt.setCycleCount(tezinaVr);
        } else {
            pt.setCycleCount(1);
        }
        pt.setOnFinished(event -> {
            listaMeta.remove(m);
            BROJ_META_NIVO.dec();
            m.nestaniIzGrupe(GRUPA);
            if (BROJ_META_NIVO.getBroj()==0) {
                statusIgre=StatusIgre.ISTEKLO_VREME;
            }
        });

        ScaleTransition stt= new ScaleTransition(Duration.seconds(tezinaVr/2),m);
        stt.setCycleCount(2);
        stt.setAutoReverse(true);
        stt.setToX(0.5);
        stt.setToY(0.5);

        ParallelTransition ptt =new ParallelTransition(m,pt,stt);
        m.setAnimacija(ptt);
        ptt.play();
    }

    private void startMeni(){
        POZADINA.setFill(START_POZADINA);
        POZADINA.setOpacity(1);
        GRUPA.getChildren().add(START_MENI);
        resetIgre();
    }

    private void setBackground(){

        POZADINA.setOpacity(1);
        JsonFajl nivo =new JsonFajl(Paths.get("config/Nivo"+(int)TRENUTNI_NIVO.getBroj()+".json").toString());
        ImagePattern pozadina=nivo.getBackgroundPatern();
        if (pozadina!=null) POZADINA.setFill(pozadina);
        else if (nivo.getBackgroundColor()!=null) POZADINA.setFill(nivo.getBackgroundColor());
        else {
            Paint p= new ImagePattern(new Image("Slike/meta"+((int)TRENUTNI_NIVO.getBroj()%3)+".jpg"),0,0,1,1,true);
            POZADINA.setFill(p);
        }

        BODOVI_Nivo=new Brojac(0,"Bodovi: ");
        Text BODOVI_Nivo_txt=BODOVI_Nivo.getText();
        BODOVI_Nivo_txt.setFont(Font.font(15));
        BODOVI_Nivo_txt.getTransforms().addAll(
                new Translate(200,HEIGHT+STATUSBARHEIGHT/5*3)
        );
        Meta backgroundMeta=new Meta(TezinaNivoa.EASY, Boja.getBoja(),STATUSBARHEIGHT/2-2.5, this);
        backgroundMeta.getTransforms().addAll(
                new Translate(STATUSBARHEIGHT/2,HEIGHT+STATUSBARHEIGHT/2)
        );

        BROJ_META_NIVO=new Brojac(0,"x");
        BROJ_META_NIVO.set_int();
        Text BROJ_META_txt=BROJ_META_NIVO.getText();
        BROJ_META_txt.setFont(Font.font(15));
        BROJ_META_txt.getTransforms().addAll(
                new Translate(STATUSBARHEIGHT+5,HEIGHT+STATUSBARHEIGHT/5*3)
        );
        GRUPA.getChildren().clear();
        ArrayList<Node> nodeList= nivo.getDistractions();
        for (Node node : nodeList) { GRUPA.getChildren().add(node); }
        ArrayList<SequentialTransition> transitions = nivo.getDistractionEffects(nodeList);
        for (SequentialTransition transition : transitions) if (transition != null) transition.playFromStart();
        GRUPA.getChildren().addAll( BROJ_META_txt,backgroundMeta, BODOVI_Nivo_txt, new Line(0,HEIGHT, sceneWIDTH,HEIGHT));
    }

    private void zaustaviMete(){

        listaMeta.forEach(m -> {
            m.stopAnimacija();
            m.nestaniIzGrupe(GRUPA);
        });
        pripremnaListaMeta.forEach(Meta::stopAnimacija);
        pripremnaListaMeta.clear();
        listaMeta.clear();

    }

    private void pauzirajMete(){
           if (oneSecond!=null) oneSecond.pause();
        listaMeta.forEach(Meta::pauseAnimacija);
    }

    private void nastaviAnimacijuMete(){
        if (oneSecond!=null) oneSecond.play();
        listaMeta.forEach(Meta::continueAnimacija);
    }

    private void medjunivo(){

        pripremnaListaMeta.clear();
        BODOVI_UKUPNO.add(BODOVI_Nivo.getBroj());
        UKUPAN_BROJ_POGODAKA+=BROJ_POGODAKA_NIVO;
        if ((int)TRENUTNI_NIVO.getBroj()==UKUPAN_BROJ_NIVOA){
            GRUPA.getChildren().add(Pobednik.otvoriPanel(this, sceneWIDTH, HEIGHT, (int)BODOVI_UKUPNO.getBroj(),STOPERICA.getSeconds(), recordList));
        }
        else GRUPA.getChildren().addAll(MedjunivoMeni.dodajMeni(this,sceneWIDTH,HEIGHT,POZADINA,BROJ_POGODAKA_NIVO,BROJ_META_NIVO_NEPROMENJIVI, (int)TRENUTNI_NIVO.getBroj()==UKUPAN_BROJ_NIVOA, UKUPAN_BROJ_POGODAKA, UKUPAN_BROJ_META, statusIgre, BODOVI_UKUPNO, BODOVI_Nivo.getBroj(), MAX_BONUS));
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("META");
        this.primaryStage=primaryStage;
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        Media  pick = new Media(this.getClass().getResource("sounds/menimusic.mp3").toExternalForm());
        MUZIKA_MENI = new MediaPlayer(pick);
        MediaView mediaView = new MediaView(MUZIKA_MENI);

        POZADINA = new Rectangle(sceneWIDTH,HEIGHT);
        START_POZADINA= new ImagePattern(new Image("Slike/metaStart.jpg"),0,0,1,1,true);

        NISAN =new Nisan(30, sceneWIDTH / 2, HEIGHT / 2);
        Text stoperica=STOPERICA.getText();
        stoperica.getTransforms().add(
                new Translate(10,20)
        );

        Group GLAVNIPANE=new Group();
        GLAVNIPANE.getChildren().addAll(new Rectangle(sceneWIDTH,sceneHEIGHT,Color.WHITE), POZADINA, GRUPA, stoperica, mediaView);
        SCENA=new Scene(GLAVNIPANE, sceneWIDTH, sceneHEIGHT);
        SCENA.setCursor(Cursor.CROSSHAIR);
        primaryStage.setScene(SCENA);

        Init();
        startMeni();
        primaryStage.show();

        SkaliranjePozornice.podesiSkaliranje(primaryStage);

        double height = CONFIG.readDouble("Height");
        double width;
        if (height>0) {
            primaryStage.setHeight(height);
            width = CONFIG.readDouble("Width");     primaryStage.setWidth(width);
          Double d = CONFIG.readDouble("X");         primaryStage.setX(d);
            d = CONFIG.readDouble("Y");         primaryStage.setY(d);
        }

        int fullscrn = CONFIG.readInt("Fullscrn");        if(fullscrn==1) setFullScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void odabirPauze(AkcijaPauze p, StatusIgre status) {
        switch(p){
            case KRAJ:
                Stage st= (Stage) SCENA.getWindow();
                st.close();
                break;
            case PONOVO:
                statusIgre = StatusIgre.PONOVLJEN_NIVO;
                BODOVI_UKUPNO.sub(BODOVI_Nivo.getBroj());
                UKUPAN_BROJ_POGODAKA-=BROJ_POGODAKA_NIVO;
                BROJ_POGODAKA_NIVO=0;
                BODOVI_Nivo.setBroj(0);
                setBackground();
                pokreniNivo((int)TRENUTNI_NIVO.getBroj());
                break;
            case NASTAVI:
                GRUPA.getChildren().remove(GRUPA.getChildren().size()-1);
                nastaviAnimacijuMete();
                statusIgre = status;
                break;
            case POCETNI_MENI:
                GRUPA.getChildren().clear();
                startMeni();
                break;
        }
    }

    @Override
    public void odabirPocetnogMenija(AkcijaStartMenija s) {
        switch(s){
            case KRAJ:
                Stage st = (Stage) SCENA.getWindow();
                st.close();
                break;
            case POCNI:
                statusIgre=StatusIgre.IGRA;
                setBackground();
                pokreniNivo(1);
                break;
            case REZULTATI:
                GRUPA.getChildren().remove(GRUPA.getChildren().size()-1);
                GRUPA.getChildren().addAll(MedjunivoMeni.dodajMeni(this,sceneWIDTH,HEIGHT,POZADINA,BROJ_POGODAKA_NIVO,BROJ_META_NIVO_NEPROMENJIVI, true, UKUPAN_BROJ_POGODAKA, UKUPAN_BROJ_META, statusIgre, BODOVI_UKUPNO, BODOVI_Nivo.getBroj(),MAX_BONUS));
                break;
            case EDITOR_STRELJANE:
                new EditorStreljane(sceneWIDTH, sceneHEIGHT, HEIGHT, primaryStage);
                break;
            case PODESAVANJA_BEZ_PREPREKA:
                for (int i=0; i<UKUPAN_BROJ_NIVOA;i++){
                    JsonFajl m=new JsonFajl(Paths.get("config/Nivo"+(i+1)+".json").toString());
                    int backup = m.readInt("brojFigura");
                    m.addInt("brojFigura", 0);
                    m.addInt("backup", backup);
                    m.write();
                }
                break;
            case PODESAVANJA_SA_PREPREKAMA:
                for (int i=0; i<UKUPAN_BROJ_NIVOA;i++){
                    JsonFajl m=new JsonFajl(Paths.get("config/Nivo"+(i+1)+".json").toString());
                    int backup = m.readInt("backup");
                    if (backup>0) m.addInt("brojFigura", backup);
                }
                break;
        }
    }

    @Override
    public void odabirMedjunivoa(AkcijaMedjunivoa p) {
        switch(p){
            case PONOVO:
                MAX_BONUS=0;
                statusIgre = StatusIgre.PONOVLJEN_NIVO;
                BODOVI_UKUPNO.sub(BODOVI_Nivo.getBroj());
                UKUPAN_BROJ_POGODAKA-=BROJ_POGODAKA_NIVO;
                BROJ_POGODAKA_NIVO=0;
                BODOVI_Nivo.setBroj(0);
                setBackground();
                pokreniNivo((int)TRENUTNI_NIVO.getBroj());
                break;
            case SLEDECI_NIVO:
                statusIgre=StatusIgre.IGRA;
                BROJ_POGODAKA_NIVO=0;
                MAX_BONUS=0;
                if ((int) TRENUTNI_NIVO.getBroj() == UKUPAN_BROJ_NIVOA) {
                    resetIgre();
                } else {
                    TRENUTNI_NIVO.inc();
                }
                BROJ_PONAVLJANJA_NIVOA=-1;
                setBackground();
                pokreniNivo((int)TRENUTNI_NIVO.getBroj());
                break;
        }
    }

    @Override
    public void setJacinaMuzike(double value) {
        MUZIKA_IGRA.setVolume(value);
        MUZIKA_MENI.setVolume(value);
    }

    @Override
    public void setJacinaEfekata(double value) {
        MUZIKA_METAK.setVolume(value);
        MUZIKA_RELOAD.setVolume(value);
        PAUSE_CLICK.setVolume(value);
        PAUSE_CLICK.play();
        TARGET_HIT.setVolume(value);
        CENTER_HIT.setVolume(value);
    }

    @Override
    public double getJacinaMuzike() {
        return MUZIKA_IGRA.getVolume();
    }

    @Override
    public double getJacinaEfekata() {
        return MUZIKA_METAK.getVolume();
    }

    @Override
    public void stop(){

        for (int i=0; i<recordList.size(); i++){
                JSON_RECORDS.addInt(""+i,recordList.get(i).getBodovi());
                JSON_RECORDS.addString(""+i+"i",recordList.get(i).getIme());
            }
            JSON_RECORDS.write();

            CONFIG.addDouble("Music", getJacinaMuzike());
            CONFIG.addDouble("Sounds", getJacinaEfekata());
            CONFIG.addDouble("Width", primaryStage.getWidth());
            CONFIG.addDouble("Height", primaryStage.getHeight());
            CONFIG.addDouble("X", primaryStage.getX());
            CONFIG.addDouble("Y", primaryStage.getY());
            CONFIG.write();
    }

    private void pokreniNivo(int num){

        pripremnaListaMeta.clear();
        JsonFajl JM =new JsonFajl(Paths.get("config/Nivo"+num+".json").toString());
        int brojMeta = JM.readInt("brMeta");
        String tip = JM.readString("METE");
        ArrayList<Meta> mete = new ArrayList<>();
        ArrayList<Path> paths = new ArrayList<>();
        ArrayList<TezinaNivoa> brzine = new ArrayList<>();
        for (int i = 0; i < brojMeta; i++) {
            TezinaNivoa prsteni = TezinaNivoa.tezinaNivoa(JM.readInt("me-" + i + "-r"));
            TezinaNivoa brzina = TezinaNivoa.tezinaNivoa(JM.readInt("me-" + i + "-s"));
            brzine.add(brzina);
            Path path = ObradaOblika.stringToPath(JM.readString("me-" + i + "-p"));
            paths.add(path);
            Meta m = new Meta(prsteni, Boja.getBoja(), 50, this);
            mete.add(m);
        }
        azurirajBrojMeta(mete);
        dodajMetke(JM.readInt("brMetaka"));

        if (tip.equals("A")){
                pokreniNizMeta(paths, brzine,BROJ_PONAVLJANJA_NIVOA, num);
        }
        else {
            for (int i = 0; i < brojMeta; i++) {
            kretanjePoRandomPutanji(mete.get(i), paths.get(i), brzine.get(i), true);
                mete.get(i).dodajUGrupuIListu(GRUPA, listaMeta);
            }
        }
    }

}
