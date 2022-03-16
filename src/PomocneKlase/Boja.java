package PomocneKlase;


import javafx.scene.paint.Color;

public class Boja {

    static Color boje[]= {
            Color.RED,
            Color.YELLOW,
            Color.ORANGE,
            Color.CORAL,
            Color.AQUA,
            Color.PINK,
            Color.GOLD,
            Color.BLUE,
            Color.GAINSBORO,
            Color.WHITE,
            Color.GREEN,
            Color.AQUAMARINE,
            Color.ALICEBLUE,
            Color.BROWN,
            Color.YELLOWGREEN,
            Color.CHOCOLATE,

            Color.PEACHPUFF,
            Color.GREENYELLOW,
            Color.SILVER,
            Color.NAVAJOWHITE,
            Color.BEIGE,
            Color.OLIVE,
            Color.ORCHID,
            Color.AZURE,
            Color.FUCHSIA,
            Color.SIENNA,
            Color.FIREBRICK,
            Color.LEMONCHIFFON,
            Color.PEACHPUFF,
            Color.PERU,
            Color.PLUM,
            Color.PAPAYAWHIP,

            Color.MEDIUMPURPLE,
            Color.KHAKI


    };

    public static Color getBoja(){
        return boje[(int)(Math.random()*5)];
    }
    public static Color getBoja(int i){if (i>=0 && i<boje.length) return boje[i]; return boje[4]; }
    public static Color getBoja2(int i){if (i>=0 && i<boje.length) return boje[i+16]; return boje[4];}

}
