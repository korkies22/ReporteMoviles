/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.PositionMap;

static class PositionMap.ContainerHelpers {
    static final boolean[] EMPTY_BOOLEANS = new boolean[0];
    static final int[] EMPTY_INTS = new int[0];
    static final long[] EMPTY_LONGS = new long[0];
    static final Object[] EMPTY_OBJECTS = new Object[0];

    PositionMap.ContainerHelpers() {
    }

    static int binarySearch(int[] arrn, int n, int n2) {
        --n;
        int n3 = 0;
        while (n3 <= n) {
            int n4 = n3 + n >>> 1;
            int n5 = arrn[n4];
            if (n5 < n2) {
                n3 = n4 + 1;
                continue;
            }
            if (n5 > n2) {
                n = n4 - 1;
                continue;
            }
            return n4;
        }
        return ~ n3;
    }
}
