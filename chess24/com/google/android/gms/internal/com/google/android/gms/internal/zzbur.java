/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbun;
import com.google.android.gms.internal.zzbup;
import java.nio.charset.Charset;
import java.util.Arrays;

public final class zzbur {
    protected static final Charset ISO_8859_1;
    protected static final Charset UTF_8;
    public static final Object zzcsf;

    static {
        UTF_8 = Charset.forName("UTF-8");
        ISO_8859_1 = Charset.forName("ISO-8859-1");
        zzcsf = new Object();
    }

    public static boolean equals(float[] arrf, float[] arrf2) {
        if (arrf != null && arrf.length != 0) {
            return Arrays.equals(arrf, arrf2);
        }
        if (arrf2 != null && arrf2.length != 0) {
            return false;
        }
        return true;
    }

    public static boolean equals(int[] arrn, int[] arrn2) {
        if (arrn != null && arrn.length != 0) {
            return Arrays.equals(arrn, arrn2);
        }
        if (arrn2 != null && arrn2.length != 0) {
            return false;
        }
        return true;
    }

    public static boolean equals(long[] arrl, long[] arrl2) {
        if (arrl != null && arrl.length != 0) {
            return Arrays.equals(arrl, arrl2);
        }
        if (arrl2 != null && arrl2.length != 0) {
            return false;
        }
        return true;
    }

    public static boolean equals(Object[] arrobject, Object[] arrobject2) {
        int n;
        int n2 = arrobject == null ? 0 : arrobject.length;
        int n3 = arrobject2 == null ? 0 : arrobject2.length;
        int n4 = n = 0;
        do {
            int n5 = n4;
            if (n < n2) {
                n5 = n4;
                if (arrobject[n] == null) {
                    ++n;
                    continue;
                }
            }
            while (n5 < n3 && arrobject2[n5] == null) {
                ++n5;
            }
            n4 = n >= n2 ? 1 : 0;
            int n6 = n5 >= n3 ? 1 : 0;
            if (n4 != 0 && n6 != 0) {
                return true;
            }
            if (n4 != n6) {
                return false;
            }
            if (!arrobject[n].equals(arrobject2[n5])) {
                return false;
            }
            ++n;
            n4 = n5 + 1;
        } while (true);
    }

    public static int hashCode(float[] arrf) {
        if (arrf != null && arrf.length != 0) {
            return Arrays.hashCode(arrf);
        }
        return 0;
    }

    public static int hashCode(int[] arrn) {
        if (arrn != null && arrn.length != 0) {
            return Arrays.hashCode(arrn);
        }
        return 0;
    }

    public static int hashCode(long[] arrl) {
        if (arrl != null && arrl.length != 0) {
            return Arrays.hashCode(arrl);
        }
        return 0;
    }

    public static int hashCode(Object[] arrobject) {
        int n = arrobject == null ? 0 : arrobject.length;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            Object object = arrobject[i];
            int n3 = n2;
            if (object != null) {
                n3 = 31 * n2 + object.hashCode();
            }
            n2 = n3;
        }
        return n2;
    }

    public static void zza(zzbun zzbun2, zzbun zzbun3) {
        if (zzbun2.zzcrX != null) {
            zzbun3.zzcrX = (zzbup)zzbun2.zzcrX.clone();
        }
    }

    public static boolean zza(byte[][] arrby, byte[][] arrby2) {
        int n;
        int n2 = arrby == null ? 0 : arrby.length;
        int n3 = arrby2 == null ? 0 : arrby2.length;
        int n4 = n = 0;
        do {
            int n5 = n4;
            if (n < n2) {
                n5 = n4;
                if (arrby[n] == null) {
                    ++n;
                    continue;
                }
            }
            while (n5 < n3 && arrby2[n5] == null) {
                ++n5;
            }
            n4 = n >= n2 ? 1 : 0;
            int n6 = n5 >= n3 ? 1 : 0;
            if (n4 != 0 && n6 != 0) {
                return true;
            }
            if (n4 != n6) {
                return false;
            }
            if (!Arrays.equals(arrby[n], arrby2[n5])) {
                return false;
            }
            ++n;
            n4 = n5 + 1;
        } while (true);
    }

    public static int zzb(byte[][] arrby) {
        int n = arrby == null ? 0 : arrby.length;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            byte[] arrby2 = arrby[i];
            int n3 = n2;
            if (arrby2 != null) {
                n3 = 31 * n2 + Arrays.hashCode(arrby2);
            }
            n2 = n3;
        }
        return n2;
    }
}
