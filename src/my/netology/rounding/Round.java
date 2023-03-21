package my.netology.rounding;

public class Round {
    //метод
    public static double roundingTo(double meaning, int discharge) {
        double m = Math.pow(10, discharge);
        return Math.round(meaning * m) / m;
    }
}
