/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbul;
import java.io.IOException;

public final class zzbuw {
    public static final int[] zzcsi = new int[0];
    public static final long[] zzcsj = new long[0];
    public static final float[] zzcsk = new float[0];
    public static final double[] zzcsl = new double[0];
    public static final boolean[] zzcsm = new boolean[0];
    public static final String[] zzcsn = new String[0];
    public static final byte[][] zzcso = new byte[0][];
    public static final byte[] zzcsp = new byte[0];

    public static int zzK(int n, int n2) {
        return n << 3 | n2;
    }

    public static boolean zzb(zzbul zzbul2, int n) throws IOException {
        return zzbul2.zzqh(n);
    }

    public static final int zzc(zzbul zzbul2, int n) throws IOException {
        int n2 = zzbul2.getPosition();
        zzbul2.zzqh(n);
        int n3 = 1;
        while (zzbul2.zzacu() == n) {
            zzbul2.zzqh(n);
            ++n3;
        }
        zzbul2.zzql(n2);
        return n3;
    }

    static int zzqA(int n) {
        return n & 7;
    }

    public static int zzqB(int n) {
        return n >>> 3;
    }
}
