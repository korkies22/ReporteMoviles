// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.view.notation;

import android.graphics.Color;

class ColorHelper
{
    private static final int MAX_COLOR = 255;
    
    public static int addToneToColor(int n, int n2, int max, final int n3) {
        final int n4 = Color.red(n) + n2;
        n2 = Color.green(n) + max;
        n = Color.blue(n) + n3;
        max = Math.max(n, Math.max(n2, n4));
        if (max > 255) {
            final float n5 = n4;
            final float n6 = max;
            max = (int)(n5 / n6 * 255.0f);
            n2 = (int)(n2 / n6 * 255.0f);
            n = (int)(n / n6 * 255.0f);
        }
        else {
            max = n4;
        }
        return Color.rgb(max, n2, n);
    }
    
    public static int getColorForLayer(int n) {
        n = 255 - n % 8 * 20;
        return Color.rgb(n, n, n);
    }
}
