package org.midnight.midnightFish.Utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

public class ProcUtilities {

    public static boolean Proc(double chance) {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.FLOOR);

        chance = Double.parseDouble(df.format(chance));
        Random random = new Random();
        double RandomDouble = Double.parseDouble(df.format(random.nextDouble(0, 100)));
//        System.out.printf(String.valueOf(chance) + " " + String.valueOf(RandomDouble) + "\n");
        return RandomDouble <= chance;
    }

}
