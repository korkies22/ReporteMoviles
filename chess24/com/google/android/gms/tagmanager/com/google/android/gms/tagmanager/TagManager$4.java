/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzcj;

static class TagManager {
    static final /* synthetic */ int[] zzbGA;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        zzbGA = new int[zzcj.zza.values().length];
        try {
            TagManager.zzbGA[zzcj.zza.zzbFf.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            TagManager.zzbGA[zzcj.zza.zzbFg.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            TagManager.zzbGA[zzcj.zza.zzbFh.ordinal()] = 3;
            return;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            return;
        }
    }
}
