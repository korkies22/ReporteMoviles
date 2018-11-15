/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.v7.util.DiffUtil;
import java.util.Comparator;

static final class DiffUtil
implements Comparator<DiffUtil.Snake> {
    DiffUtil() {
    }

    @Override
    public int compare(DiffUtil.Snake snake, DiffUtil.Snake snake2) {
        int n;
        int n2 = n = snake.x - snake2.x;
        if (n == 0) {
            n2 = snake.y - snake2.y;
        }
        return n2;
    }
}
