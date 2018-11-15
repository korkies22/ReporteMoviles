/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 */
package de.cisha.android.board.view.notation;

import android.graphics.Color;

class ColorHelper {
    private static final int MAX_COLOR = 255;

    ColorHelper() {
    }

    public static int addToneToColor(int n, int n2, int n3, int n4) {
        int n5 = Color.red((int)n) + n2;
        n2 = Color.green((int)n) + n3;
        n3 = Math.max(n = Color.blue((int)n) + n4, Math.max(n2, n5));
        if (n3 > 255) {
            float f = n5;
            float f2 = n3;
            n3 = (int)(f / f2 * 255.0f);
            n2 = (int)((float)n2 / f2 * 255.0f);
            n = (int)((float)n / f2 * 255.0f);
        } else {
            n3 = n5;
        }
        return Color.rgb((int)n3, (int)n2, (int)n);
    }

    public static int getColorForLayer(int n) {
        n = 255 - n % 8 * 20;
        return Color.rgb((int)n, (int)n, (int)n);
    }
}
