/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.zzapn;
import com.google.android.gms.internal.zzapr;
import java.util.ArrayList;
import java.util.Collection;

public class zzapo {
    private final Collection<zzapn> zzAJ = new ArrayList<zzapn>();
    private final Collection<zzapn.zzd> zzAK = new ArrayList<zzapn.zzd>();
    private final Collection<zzapn.zzd> zzAL = new ArrayList<zzapn.zzd>();

    public static void initialize(Context context) {
        zzapr.zzCR().initialize(context);
    }

    public void zza(zzapn zzapn2) {
        this.zzAJ.add(zzapn2);
    }
}
