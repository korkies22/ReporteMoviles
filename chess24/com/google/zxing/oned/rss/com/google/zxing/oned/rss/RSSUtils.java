/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.oned.rss;

public final class RSSUtils {
    private RSSUtils() {
    }

    private static int combins(int n, int n2) {
        int n3;
        int n4;
        int n5 = n4 = n - n2;
        int n6 = n2;
        if (n4 > n2) {
            n6 = n4;
            n5 = n2;
        }
        n2 = 1;
        int n7 = 1;
        n4 = n;
        n = n7;
        do {
            n7 = n2;
            if (n4 <= n6) break;
            n2 = n3 = n2 * n4;
            n7 = n;
            if (n <= n5) {
                n2 = n3 / n;
                n7 = n + 1;
            }
            --n4;
            n = n7;
        } while (true);
        for (n3 = n; n3 <= n5; ++n3) {
            n7 /= n3;
        }
        return n7;
    }

    public static int getRSSvalue(int[] arrn, int n, boolean bl) {
        int n2;
        int n3;
        int[] arrn2 = arrn;
        int n4 = arrn2.length;
        int n5 = 0;
        for (n2 = 0; n2 < n4; ++n2) {
            n5 += arrn2[n2];
        }
        int n6 = arrn2.length;
        n2 = 0;
        int n7 = 0;
        int n8 = n5;
        n5 = n2;
        for (int i = 0; i < (n3 = n6 - 1); ++i) {
            int n9 = 1 << i;
            n5 |= n9;
            int n10 = 1;
            while (n10 < arrn[i]) {
                int n11 = n8 - n10;
                int n12 = n6 - i;
                int n13 = n12 - 2;
                n2 = n4 = RSSUtils.combins(n11 - 1, n13);
                if (bl) {
                    n2 = n4;
                    if (n5 == 0) {
                        int n14 = n12 - 1;
                        n2 = n4;
                        if (n11 - n14 >= n14) {
                            n2 = n4 - RSSUtils.combins(n11 - n12, n13);
                        }
                    }
                }
                if (n12 - 1 > 1) {
                    n4 = n11 - n13;
                    n13 = 0;
                    while (n4 > n) {
                        n13 += RSSUtils.combins(n11 - n4 - 1, n12 - 3);
                        --n4;
                    }
                    n4 = n2 - n13 * (n3 - i);
                } else {
                    n4 = n2;
                    if (n11 > n) {
                        n4 = n2 - 1;
                    }
                }
                n7 += n4;
                ++n10;
                n5 &= ~ n9;
            }
            n8 -= n10;
        }
        return n7;
    }
}
