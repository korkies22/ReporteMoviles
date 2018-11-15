/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

public final class zzsg
extends Enum<zzsg> {
    public static final /* enum */ zzsg zzadU = new zzsg();
    public static final /* enum */ zzsg zzadV = new zzsg();
    private static final /* synthetic */ zzsg[] zzadW;

    static {
        zzadW = new zzsg[]{zzadU, zzadV};
    }

    private zzsg() {
    }

    public static zzsg[] values() {
        return (zzsg[])zzadW.clone();
    }

    public static zzsg zzbY(String string) {
        if ("GZIP".equalsIgnoreCase(string)) {
            return zzadV;
        }
        return zzadU;
    }
}
