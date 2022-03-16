package Komponente;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Stoperica {

        private AnimationTimer timer;
        private Text text ;
        private int seconds;
        private long counter;

        public Stoperica(){
            text = new Text("");
            text.setFont(Font.font("Arial", FontWeight.BOLD,20));
            text.setStroke(Color.BLACK);
            text.setStrokeWidth(0.5);
            text.setFill(Color.WHITE);

            counter = 0;
        }

        public void start() {
            if (seconds==0) text.setText("Vreme: 0 s");

            timer = new AnimationTimer() {
                public void handle(long now) {
                        if (counter == 60) {
                            seconds++;
                            text.setText("Vreme: "+ seconds + " s");
                            counter = 0;
                    } else {
                        counter ++;
                    }
                }
            };
            timer.start();
        }

    public void stop() {
        timer.stop();
    };
    public void reset() {
        if (timer!=null) timer.stop();
        counter = 0;
        seconds = 0;
        text.setText("");
    };

    public Text getText(){
        return text;
    }

    public int getSeconds() {
        return seconds;
    }
}


