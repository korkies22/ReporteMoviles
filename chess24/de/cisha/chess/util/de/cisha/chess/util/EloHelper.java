/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.chess.util;

import java.util.HashMap;
import java.util.Map;

public class EloHelper {
    private static Map<Integer, Integer> _diffForPercentage = new HashMap<Integer, Integer>();

    static {
        _diffForPercentage.put(100, 800);
        _diffForPercentage.put(99, 677);
        _diffForPercentage.put(98, 589);
        _diffForPercentage.put(97, 538);
        _diffForPercentage.put(96, 501);
        _diffForPercentage.put(95, 470);
        _diffForPercentage.put(94, 444);
        _diffForPercentage.put(93, 422);
        _diffForPercentage.put(92, 401);
        _diffForPercentage.put(91, 383);
        _diffForPercentage.put(90, 366);
        _diffForPercentage.put(89, 351);
        _diffForPercentage.put(88, 336);
        _diffForPercentage.put(87, 322);
        _diffForPercentage.put(86, 309);
        _diffForPercentage.put(85, 296);
        _diffForPercentage.put(84, 284);
        _diffForPercentage.put(83, 273);
        _diffForPercentage.put(82, 262);
        _diffForPercentage.put(81, 251);
        _diffForPercentage.put(80, 240);
        _diffForPercentage.put(79, 230);
        _diffForPercentage.put(78, 220);
        _diffForPercentage.put(77, 211);
        _diffForPercentage.put(76, 202);
        _diffForPercentage.put(75, 193);
        _diffForPercentage.put(74, 184);
        _diffForPercentage.put(73, 175);
        _diffForPercentage.put(72, 166);
        _diffForPercentage.put(71, 158);
        _diffForPercentage.put(70, 149);
        _diffForPercentage.put(69, 141);
        _diffForPercentage.put(68, 133);
        _diffForPercentage.put(67, 125);
        _diffForPercentage.put(66, 117);
        _diffForPercentage.put(65, 110);
        _diffForPercentage.put(64, 102);
        _diffForPercentage.put(63, 95);
        _diffForPercentage.put(62, 87);
        _diffForPercentage.put(61, 80);
        _diffForPercentage.put(60, 72);
        _diffForPercentage.put(59, 65);
        _diffForPercentage.put(58, 57);
        _diffForPercentage.put(57, 50);
        _diffForPercentage.put(56, 43);
        _diffForPercentage.put(55, 36);
        _diffForPercentage.put(54, 29);
        _diffForPercentage.put(53, 21);
        _diffForPercentage.put(52, 14);
        _diffForPercentage.put(51, 7);
        _diffForPercentage.put(50, 0);
    }

    public static float getExpectedPoints(int n, int n2) {
        return (float)(1.0 / (Math.pow(10.0, (double)Math.max(-400, Math.min(400, n2 - n)) / 400.0) + 1.0));
    }

    public static float getPerformance(int n, float f, float f2) {
        if (n == 0) {
            return 0.0f;
        }
        float f3 = n;
        return (f2 * f3 + (f - f3 / 2.0f) * 2.0f * 400.0f) / f3;
    }

    public static float getPerformanceFIDE(int n, float f, float f2) {
        if (n > 0) {
            n = (n = Math.min(100, Math.max(0, Math.round(100.0f * f / (float)n)))) < 50 ? -1 * _diffForPercentage.get(100 - n) : _diffForPercentage.get(n);
            return f2 + (float)n;
        }
        return 0.0f;
    }
}
