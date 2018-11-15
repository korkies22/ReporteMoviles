/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

public final class zzsd
extends Enum<zzsd> {
    public static final /* enum */ zzsd zzadK = new zzsd();
    public static final /* enum */ zzsd zzadL = new zzsd();
    public static final /* enum */ zzsd zzadM = new zzsd();
    public static final /* enum */ zzsd zzadN = new zzsd();
    public static final /* enum */ zzsd zzadO = new zzsd();
    public static final /* enum */ zzsd zzadP = new zzsd();
    private static final /* synthetic */ zzsd[] zzadQ;

    static {
        zzadQ = new zzsd[]{zzadK, zzadL, zzadM, zzadN, zzadO, zzadP};
    }

    private zzsd() {
    }

    public static zzsd[] values() {
        return (zzsd[])zzadQ.clone();
    }

    public static zzsd zzbX(String string) {
        if ("BATCH_BY_SESSION".equalsIgnoreCase(string)) {
            return zzadL;
        }
        if ("BATCH_BY_TIME".equalsIgnoreCase(string)) {
            return zzadM;
        }
        if ("BATCH_BY_BRUTE_FORCE".equalsIgnoreCase(string)) {
            return zzadN;
        }
        if ("BATCH_BY_COUNT".equalsIgnoreCase(string)) {
            return zzadO;
        }
        if ("BATCH_BY_SIZE".equalsIgnoreCase(string)) {
            return zzadP;
        }
        return zzadK;
    }
}
