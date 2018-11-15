/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.util;

import com.google.android.gms.common.internal.zzaa;
import java.util.ArrayList;
import java.util.Arrays;

public final class zzb {
    public static <T> int zza(T[] arrT, T t) {
        int n = arrT != null ? arrT.length : 0;
        for (int i = 0; i < n; ++i) {
            if (!zzaa.equal(arrT[i], t)) continue;
            return i;
        }
        return -1;
    }

    public static void zza(StringBuilder stringBuilder, double[] arrd) {
        int n = arrd.length;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Double.toString(arrd[i]));
        }
    }

    public static void zza(StringBuilder stringBuilder, float[] arrf) {
        int n = arrf.length;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Float.toString(arrf[i]));
        }
    }

    public static void zza(StringBuilder stringBuilder, int[] arrn) {
        int n = arrn.length;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Integer.toString(arrn[i]));
        }
    }

    public static void zza(StringBuilder stringBuilder, long[] arrl) {
        int n = arrl.length;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Long.toString(arrl[i]));
        }
    }

    public static <T> void zza(StringBuilder stringBuilder, T[] arrT) {
        int n = arrT.length;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(arrT[i].toString());
        }
    }

    public static void zza(StringBuilder stringBuilder, String[] arrstring) {
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append("\"");
            stringBuilder.append(arrstring[i]);
            stringBuilder.append("\"");
        }
    }

    public static void zza(StringBuilder stringBuilder, boolean[] arrbl) {
        int n = arrbl.length;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(Boolean.toString(arrbl[i]));
        }
    }

    public static /* varargs */ byte[] zza(byte[] ... arrby) {
        int n;
        if (arrby.length == 0) {
            return new byte[0];
        }
        int n2 = n = 0;
        while (n < arrby.length) {
            n2 += arrby[n].length;
            ++n;
        }
        byte[] arrby2 = Arrays.copyOf(arrby[0], n2);
        byte[] arrby3 = arrby[0];
        n2 = arrby3.length;
        for (n = 1; n < arrby.length; ++n) {
            arrby3 = arrby[n];
            System.arraycopy(arrby3, 0, arrby2, n2, arrby3.length);
            n2 += arrby3.length;
        }
        return arrby2;
    }

    public static Integer[] zza(int[] arrn) {
        if (arrn == null) {
            return null;
        }
        int n = arrn.length;
        Integer[] arrinteger = new Integer[n];
        for (int i = 0; i < n; ++i) {
            arrinteger[i] = arrn[i];
        }
        return arrinteger;
    }

    public static <T> ArrayList<T> zzb(T[] arrT) {
        int n = arrT.length;
        ArrayList<T> arrayList = new ArrayList<T>(n);
        for (int i = 0; i < n; ++i) {
            arrayList.add(arrT[i]);
        }
        return arrayList;
    }

    public static <T> boolean zzb(T[] arrT, T t) {
        if (zzb.zza(arrT, t) >= 0) {
            return true;
        }
        return false;
    }

    public static <T> ArrayList<T> zzys() {
        return new ArrayList();
    }
}
