/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzbg;

static class zzbg {
    static final /* synthetic */ int[] zzbEy;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        zzbEy = new int[zzbg.zza.values().length];
        try {
            zzbg.zzbEy[zzbg.zza.zzbEA.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            zzbg.zzbEy[zzbg.zza.zzbEB.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            zzbg.zzbEy[zzbg.zza.zzbEz.ordinal()] = 3;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
