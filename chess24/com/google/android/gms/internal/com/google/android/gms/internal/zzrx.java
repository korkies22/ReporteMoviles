/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.internal.zzrr;
import com.google.android.gms.internal.zzrs;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzrz;
import com.google.android.gms.internal.zzsa;
import com.google.android.gms.internal.zzsb;
import com.google.android.gms.internal.zzsc;
import com.google.android.gms.internal.zzse;
import com.google.android.gms.internal.zzsj;
import com.google.android.gms.internal.zzsm;
import com.google.android.gms.internal.zzsn;
import com.google.android.gms.internal.zzsx;
import com.google.android.gms.internal.zzsy;
import com.google.android.gms.internal.zzsz;
import com.google.android.gms.internal.zzta;
import com.google.android.gms.internal.zzth;

public class zzrx {
    private final Context zzadf;
    private final Context zzvZ;

    public zzrx(Context context) {
        zzac.zzw(context);
        context = context.getApplicationContext();
        zzac.zzb(context, (Object)"Application context can't be null");
        this.zzvZ = context;
        this.zzadf = context;
    }

    public Context getApplicationContext() {
        return this.zzvZ;
    }

    protected com.google.android.gms.analytics.zzh zzX(Context context) {
        return com.google.android.gms.analytics.zzh.zzV(context);
    }

    protected zzsm zza(zzrw zzrw2) {
        return new zzsm(zzrw2);
    }

    protected zzsb zzb(zzrw zzrw2) {
        return new zzsb(zzrw2);
    }

    protected zzrr zzc(zzrw zzrw2) {
        return new zzrr(zzrw2);
    }

    protected zzse zzd(zzrw zzrw2) {
        return new zzse(zzrw2);
    }

    protected zzth zze(zzrw zzrw2) {
        return new zzth(zzrw2);
    }

    protected zzsx zzf(zzrw zzrw2) {
        return new zzsx(zzrw2);
    }

    protected zzsj zzg(zzrw zzrw2) {
        return new zzsj(zzrw2);
    }

    protected zze zzh(zzrw zzrw2) {
        return zzh.zzyv();
    }

    protected GoogleAnalytics zzi(zzrw zzrw2) {
        return new GoogleAnalytics(zzrw2);
    }

    zzsc zzj(zzrw zzrw2) {
        return new zzsc(zzrw2, this);
    }

    zzsy zzk(zzrw zzrw2) {
        return new zzsy(zzrw2);
    }

    protected zzrs zzl(zzrw zzrw2) {
        return new zzrs(zzrw2, this);
    }

    public zzsa zzm(zzrw zzrw2) {
        return new zzsa(zzrw2);
    }

    public zzsz zzn(zzrw zzrw2) {
        return new zzsz(zzrw2);
    }

    public Context zznC() {
        return this.zzadf;
    }

    public zzrz zzo(zzrw zzrw2) {
        return new zzrz(zzrw2);
    }

    public zzsn zzp(zzrw zzrw2) {
        return new zzsn(zzrw2);
    }

    public zzta zzq(zzrw zzrw2) {
        return new zzta(zzrw2);
    }
}
