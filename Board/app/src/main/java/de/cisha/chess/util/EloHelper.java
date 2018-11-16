// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.chess.util;

import java.util.HashMap;
import java.util.Map;

public class EloHelper
{
    private static Map<Integer, Integer> _diffForPercentage;
    
    static {
        (EloHelper._diffForPercentage = new HashMap<Integer, Integer>()).put(100, 800);
        EloHelper._diffForPercentage.put(99, 677);
        EloHelper._diffForPercentage.put(98, 589);
        EloHelper._diffForPercentage.put(97, 538);
        EloHelper._diffForPercentage.put(96, 501);
        EloHelper._diffForPercentage.put(95, 470);
        EloHelper._diffForPercentage.put(94, 444);
        EloHelper._diffForPercentage.put(93, 422);
        EloHelper._diffForPercentage.put(92, 401);
        EloHelper._diffForPercentage.put(91, 383);
        EloHelper._diffForPercentage.put(90, 366);
        EloHelper._diffForPercentage.put(89, 351);
        EloHelper._diffForPercentage.put(88, 336);
        EloHelper._diffForPercentage.put(87, 322);
        EloHelper._diffForPercentage.put(86, 309);
        EloHelper._diffForPercentage.put(85, 296);
        EloHelper._diffForPercentage.put(84, 284);
        EloHelper._diffForPercentage.put(83, 273);
        EloHelper._diffForPercentage.put(82, 262);
        EloHelper._diffForPercentage.put(81, 251);
        EloHelper._diffForPercentage.put(80, 240);
        EloHelper._diffForPercentage.put(79, 230);
        EloHelper._diffForPercentage.put(78, 220);
        EloHelper._diffForPercentage.put(77, 211);
        EloHelper._diffForPercentage.put(76, 202);
        EloHelper._diffForPercentage.put(75, 193);
        EloHelper._diffForPercentage.put(74, 184);
        EloHelper._diffForPercentage.put(73, 175);
        EloHelper._diffForPercentage.put(72, 166);
        EloHelper._diffForPercentage.put(71, 158);
        EloHelper._diffForPercentage.put(70, 149);
        EloHelper._diffForPercentage.put(69, 141);
        EloHelper._diffForPercentage.put(68, 133);
        EloHelper._diffForPercentage.put(67, 125);
        EloHelper._diffForPercentage.put(66, 117);
        EloHelper._diffForPercentage.put(65, 110);
        EloHelper._diffForPercentage.put(64, 102);
        EloHelper._diffForPercentage.put(63, 95);
        EloHelper._diffForPercentage.put(62, 87);
        EloHelper._diffForPercentage.put(61, 80);
        EloHelper._diffForPercentage.put(60, 72);
        EloHelper._diffForPercentage.put(59, 65);
        EloHelper._diffForPercentage.put(58, 57);
        EloHelper._diffForPercentage.put(57, 50);
        EloHelper._diffForPercentage.put(56, 43);
        EloHelper._diffForPercentage.put(55, 36);
        EloHelper._diffForPercentage.put(54, 29);
        EloHelper._diffForPercentage.put(53, 21);
        EloHelper._diffForPercentage.put(52, 14);
        EloHelper._diffForPercentage.put(51, 7);
        EloHelper._diffForPercentage.put(50, 0);
    }
    
    public static float getExpectedPoints(final int n, final int n2) {
        return (float)(1.0 / (Math.pow(10.0, Math.max(-400, Math.min(400, n2 - n)) / 400.0) + 1.0));
    }
    
    public static float getPerformance(final int n, final float n2, final float n3) {
        if (n == 0) {
            return 0.0f;
        }
        final float n4 = n;
        return (n3 * n4 + (n2 - n4 / 2.0f) * 2.0f * 400.0f) / n4;
    }
    
    public static float getPerformanceFIDE(int n, final float n2, final float n3) {
        if (n > 0) {
            n = Math.min(100, Math.max(0, Math.round(100.0f * n2 / n)));
            if (n < 50) {
                n = -1 * EloHelper._diffForPercentage.get(100 - n);
            }
            else {
                n = EloHelper._diffForPercentage.get(n);
            }
            return n3 + n;
        }
        return 0.0f;
    }
}
