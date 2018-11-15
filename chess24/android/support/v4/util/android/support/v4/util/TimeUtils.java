/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.util;

import android.support.annotation.RestrictTo;
import java.io.PrintWriter;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public final class TimeUtils {
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static final int HUNDRED_DAY_FIELD_LEN = 19;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;
    private static char[] sFormatStr;
    private static final Object sFormatSync;

    static {
        sFormatSync = new Object();
        sFormatStr = new char[24];
    }

    private TimeUtils() {
    }

    private static int accumField(int n, int n2, boolean bl, int n3) {
        if (!(n > 99 || bl && n3 >= 3)) {
            if (!(n > 9 || bl && n3 >= 2)) {
                if (!bl && n <= 0) {
                    return 0;
                }
                return 1 + n2;
            }
            return 2 + n2;
        }
        return 3 + n2;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static void formatDuration(long l, long l2, PrintWriter printWriter) {
        if (l == 0L) {
            printWriter.print("--");
            return;
        }
        TimeUtils.formatDuration(l - l2, printWriter, 0);
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static void formatDuration(long l, PrintWriter printWriter) {
        TimeUtils.formatDuration(l, printWriter, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static void formatDuration(long l, PrintWriter printWriter, int n) {
        Object object = sFormatSync;
        synchronized (object) {
            n = TimeUtils.formatDurationLocked(l, n);
            printWriter.print(new String(sFormatStr, 0, n));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static void formatDuration(long l, StringBuilder stringBuilder) {
        Object object = sFormatSync;
        synchronized (object) {
            int n = TimeUtils.formatDurationLocked(l, 0);
            stringBuilder.append(sFormatStr, 0, n);
            return;
        }
    }

    private static int formatDurationLocked(long l, int n) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        boolean bl;
        if (sFormatStr.length < n) {
            sFormatStr = new char[n];
        }
        char[] arrc = sFormatStr;
        if (l == 0L) {
            while (n - 1 > 0) {
                arrc[0] = 32;
            }
            arrc[0] = 48;
            return 1;
        }
        if (l > 0L) {
            n4 = 43;
        } else {
            n4 = 45;
            l = - l;
        }
        int n9 = (int)(l % 1000L);
        int n10 = (int)Math.floor(l / 1000L);
        if (n10 > 86400) {
            n2 = n10 / 86400;
            n10 -= 86400 * n2;
        } else {
            n2 = 0;
        }
        if (n10 > 3600) {
            n6 = n10 / 3600;
            n10 -= n6 * 3600;
        } else {
            n6 = 0;
        }
        if (n10 > 60) {
            n3 = n10 / 60;
            n5 = n10 - n3 * 60;
        } else {
            n3 = 0;
            n5 = n10;
        }
        if (n != 0) {
            n10 = TimeUtils.accumField(n2, 1, false, 0);
            bl = n10 > 0;
            bl = (n10 += TimeUtils.accumField(n6, 1, bl, 2)) > 0;
            bl = (n10 += TimeUtils.accumField(n3, 1, bl, 2)) > 0;
            n8 = n10 + TimeUtils.accumField(n5, 1, bl, 2);
            n10 = n8 > 0 ? 3 : 0;
            n8 += TimeUtils.accumField(n9, 2, true, n10) + 1;
            n10 = 0;
            do {
                n7 = n10++;
                if (n8 < n) {
                    arrc[n10] = 32;
                    ++n8;
                    continue;
                }
                break;
            } while (true);
        } else {
            n7 = 0;
        }
        arrc[n7] = n4;
        n8 = n7 + 1;
        n = n != 0 ? 1 : 0;
        n2 = TimeUtils.printField(arrc, n2, 'd', n8, false, 0);
        bl = n2 != n8;
        n10 = n != 0 ? 2 : 0;
        n2 = TimeUtils.printField(arrc, n6, 'h', n2, bl, n10);
        bl = n2 != n8;
        n10 = n != 0 ? 2 : 0;
        n2 = TimeUtils.printField(arrc, n3, 'm', n2, bl, n10);
        bl = n2 != n8;
        n10 = n != 0 ? 2 : 0;
        n10 = TimeUtils.printField(arrc, n5, 's', n2, bl, n10);
        n = n != 0 && n10 != n8 ? 3 : 0;
        n = TimeUtils.printField(arrc, n9, 'm', n10, true, n);
        arrc[n] = 115;
        return n + 1;
    }

    private static int printField(char[] arrc, int n, char c, int n2, boolean bl, int n3) {
        int n4;
        block6 : {
            int n5;
            block8 : {
                block7 : {
                    block5 : {
                        if (bl) break block5;
                        n4 = n2;
                        if (n <= 0) break block6;
                    }
                    if (bl && n3 >= 3 || n > 99) {
                        n5 = n / 100;
                        arrc[n2] = (char)(n5 + 48);
                        n4 = n2 + 1;
                        n -= n5 * 100;
                    } else {
                        n4 = n2;
                    }
                    if (bl && n3 >= 2 || n > 9) break block7;
                    n5 = n4;
                    n3 = n;
                    if (n2 == n4) break block8;
                }
                n2 = n / 10;
                arrc[n4] = (char)(n2 + 48);
                n5 = n4 + 1;
                n3 = n - n2 * 10;
            }
            arrc[n5] = (char)(n3 + 48);
            n = n5 + 1;
            arrc[n] = c;
            n4 = n + 1;
        }
        return n4;
    }
}
