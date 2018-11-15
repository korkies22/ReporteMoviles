/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzf;
import com.google.android.gms.common.internal.zzac;
import java.util.HashMap;

public final class zzrn
extends zzf<zzrn> {
    private String zzacf;
    private String zzacg;
    private String zzach;
    private String zzaci;
    private boolean zzacj;
    private String zzack;
    private boolean zzacl;
    private double zzacm;

    public String getUserId() {
        return this.zzach;
    }

    public void setClientId(String string) {
        this.zzacg = string;
    }

    public void setSampleRate(double d) {
        boolean bl = d >= 0.0 && d <= 100.0;
        zzac.zzb(bl, (Object)"Sample rate must be between 0% and 100%");
        this.zzacm = d;
    }

    public void setUserId(String string) {
        this.zzach = string;
    }

    public String toString() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("hitType", this.zzacf);
        hashMap.put("clientId", this.zzacg);
        hashMap.put("userId", this.zzach);
        hashMap.put("androidAdId", this.zzaci);
        hashMap.put("AdTargetingEnabled", this.zzacj);
        hashMap.put("sessionControl", this.zzack);
        hashMap.put("nonInteraction", this.zzacl);
        hashMap.put("sampleRate", this.zzacm);
        return zzrn.zzj(hashMap);
    }

    public void zzR(boolean bl) {
        this.zzacj = bl;
    }

    public void zzS(boolean bl) {
        this.zzacl = bl;
    }

    public void zza(zzrn zzrn2) {
        if (!TextUtils.isEmpty((CharSequence)this.zzacf)) {
            zzrn2.zzbD(this.zzacf);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzacg)) {
            zzrn2.setClientId(this.zzacg);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzach)) {
            zzrn2.setUserId(this.zzach);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzaci)) {
            zzrn2.zzbE(this.zzaci);
        }
        if (this.zzacj) {
            zzrn2.zzR(true);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzack)) {
            zzrn2.zzbF(this.zzack);
        }
        if (this.zzacl) {
            zzrn2.zzS(this.zzacl);
        }
        if (this.zzacm != 0.0) {
            zzrn2.setSampleRate(this.zzacm);
        }
    }

    @Override
    public /* synthetic */ void zzb(zzf zzf2) {
        this.zza((zzrn)zzf2);
    }

    public void zzbD(String string) {
        this.zzacf = string;
    }

    public void zzbE(String string) {
        this.zzaci = string;
    }

    public void zzbF(String string) {
        this.zzack = string;
    }

    public String zzlX() {
        return this.zzacg;
    }

    public String zzmT() {
        return this.zzacf;
    }

    public String zzmU() {
        return this.zzaci;
    }

    public boolean zzmV() {
        return this.zzacj;
    }

    public String zzmW() {
        return this.zzack;
    }

    public boolean zzmX() {
        return this.zzacl;
    }

    public double zzmY() {
        return this.zzacm;
    }
}
