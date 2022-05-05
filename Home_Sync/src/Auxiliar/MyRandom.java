package Auxiliar;

import java.util.Random;

public class MyRandom {
    private static Random r = new Random();

    public static float random_f(float min, float max){
    return r.nextFloat(max - min) + min;
    }
    public static int random_i(int min, int max){
        return r.nextInt(max - min) + min;
    }
    public static double random_d(double min, double max){
        return r.nextDouble(max - min) + min;
    }
}
