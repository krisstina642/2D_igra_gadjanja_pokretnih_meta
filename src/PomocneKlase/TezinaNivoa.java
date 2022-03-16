package PomocneKlase;

public enum TezinaNivoa {
    HARD, MEDIUM, EASY;

    public static int vrednostTezineNivoa(TezinaNivoa t){
      return (t==HARD)? 1:(t==MEDIUM)?2:3;
    };

    public static TezinaNivoa tezinaNivoa(int i){
      return (i==1)?HARD:(i==2)?MEDIUM:EASY;
    };
}
