/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzapo;
import com.google.android.gms.internal.zzapp;

public final class zzapr {
    private static zzapr zzaWJ;
    private final zzapo zzaWK = new zzapo();
    private final zzapp zzaWL = new zzapp();

    static {
        zzapr.zza(new zzapr());
    }

    private zzapr() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static zzapr zzCP() {
        synchronized (zzapr.class) {
            return zzaWJ;
        }
    }

    public static zzapo zzCQ() {
        return zzapr.zzCP().zzaWK;
    }

    public static zzapp zzCR() {
        return zzapr.zzCP().zzaWL;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected static void zza(zzapr zzapr2) {
        synchronized (zzapr.class) {
            zzaWJ = zzapr2;
            return;
        }
    }
}
