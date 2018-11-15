/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package android.support.v4.graphics;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

public final class PaintCompat {
    private static final String EM_STRING = "m";
    private static final String TOFU_STRING = "\udb3f\udffd";
    private static final ThreadLocal<Pair<Rect, Rect>> sRectThreadLocal = new ThreadLocal();

    private PaintCompat() {
    }

    public static boolean hasGlyph(@NonNull Paint paint, @NonNull String string) {
        if (Build.VERSION.SDK_INT >= 23) {
            return paint.hasGlyph(string);
        }
        int n = string.length();
        if (n == 1 && Character.isWhitespace(string.charAt(0))) {
            return true;
        }
        float f = paint.measureText(TOFU_STRING);
        float f2 = paint.measureText(EM_STRING);
        float f3 = paint.measureText(string);
        float f4 = 0.0f;
        if (f3 == 0.0f) {
            return false;
        }
        if (string.codePointCount(0, string.length()) > 1) {
            if (f3 > 2.0f * f2) {
                return false;
            }
            int n2 = 0;
            while (n2 < n) {
                int n3 = Character.charCount(string.codePointAt(n2)) + n2;
                f4 += paint.measureText(string, n2, n3);
                n2 = n3;
            }
            if (f3 >= f4) {
                return false;
            }
        }
        if (f3 != f) {
            return true;
        }
        Pair<Rect, Rect> pair = PaintCompat.obtainEmptyRects();
        paint.getTextBounds(TOFU_STRING, 0, TOFU_STRING.length(), (Rect)pair.first);
        paint.getTextBounds(string, 0, n, (Rect)pair.second);
        return ((Rect)pair.first).equals(pair.second) ^ true;
    }

    private static Pair<Rect, Rect> obtainEmptyRects() {
        Pair<Rect, Rect> pair = sRectThreadLocal.get();
        if (pair == null) {
            pair = new Pair<Rect, Rect>(new Rect(), new Rect());
            sRectThreadLocal.set(pair);
            return pair;
        }
        ((Rect)pair.first).setEmpty();
        ((Rect)pair.second).setEmpty();
        return pair;
    }
}
