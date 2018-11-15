/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package com.google.android.gms.internal;

import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zza;
import com.google.android.gms.internal.zzaap;
import com.google.android.gms.internal.zzaaw;
import com.google.android.gms.internal.zzaax;
import com.google.android.gms.internal.zzzs;
import com.google.android.gms.internal.zzzw;

public class zzaae
extends zzzw {
    private zzaap zzaxK;
    private final zza<zzzs<?>> zzazH = new zza();

    private zzaae(zzaax zzaax2) {
        super(zzaax2);
        this.zzaBs.zza("ConnectionlessLifecycleHelper", this);
    }

    public static void zza(Activity object, zzaap zzaap2, zzzs<?> zzzs2) {
        zzaax zzaax2 = zzaae.zzs(object);
        zzaae zzaae2 = zzaax2.zza("ConnectionlessLifecycleHelper", zzaae.class);
        object = zzaae2;
        if (zzaae2 == null) {
            object = new zzaae(zzaax2);
        }
        object.zzaxK = zzaap2;
        zzaae.super.zza(zzzs2);
        zzaap2.zza((zzaae)object);
    }

    private void zza(zzzs<?> zzzs2) {
        zzac.zzb(zzzs2, (Object)"ApiKey cannot be null");
        this.zzazH.add(zzzs2);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!this.zzazH.isEmpty()) {
            this.zzaxK.zza(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        this.zzaxK.zzb(this);
    }

    @Override
    protected void zza(ConnectionResult connectionResult, int n) {
        this.zzaxK.zza(connectionResult, n);
    }

    @Override
    protected void zzuW() {
        this.zzaxK.zzuW();
    }

    zza<zzzs<?>> zzvx() {
        return this.zzazH;
    }
}
