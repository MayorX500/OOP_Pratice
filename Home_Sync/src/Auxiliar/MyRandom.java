package Auxiliar;

import java.util.Random;

public class MyRandom {
    private static Random r = new Random();

    public static float random_f(float min, float max){
    return min + r.nextFloat() * (max - min);
    }
    public static int random_i(int min, int max){
        return min + r.nextInt() * (max - min);
    }
    public static double random_d(double min, double max){
        return min + r.nextDouble() * (max - min);
    }
}
