package org.midnight.midnightFish.Utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

public class ProcUtils {

    private static Random random = new Random();

    public static boolean Proc(double chance) {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.FLOOR);

        double RandomDouble = Double.parseDouble(df.format(random.nextDouble(0, 100)));
//        System.out.printf(String.valueOf(chance) + " " + String.valueOf(RandomDouble) + "\n");
        return RandomDouble <= chance;
    }

}
