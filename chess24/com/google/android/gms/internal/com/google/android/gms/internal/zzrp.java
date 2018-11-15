/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzf;
import java.util.HashMap;

public final class zzrp
extends zzf<zzrp> {
    public String zzaca;
    public String zzacu;
    public String zzacv;

    public String getAction() {
        return this.zzaca;
    }

    public String getTarget() {
        return this.zzacv;
    }

    public String toString() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("network", this.zzacu);
        hashMap.put("action", this.zzaca);
        hashMap.put("target", this.zzacv);
        return zzrp.zzj(hashMap);
    }

    public void zza(zzrp zzrp2) {
        if (!TextUtils.isEmpty((CharSequence)this.zzacu)) {
            zzrp2.zzbI(this.zzacu);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzaca)) {
            zzrp2.zzbB(this.zzaca);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzacv)) {
            zzrp2.zzbJ(this.zzacv);
        }
    }

    @Override
    public /* synthetic */ void zzb(zzf zzf2) {
        this.zza((zzrp)zzf2);
    }

    public void zzbB(String string) {
        this.zzaca = string;
    }

    public void zzbI(String string) {
        this.zzacu = string;
    }

    public void zzbJ(String string) {
        this.zzacv = string;
    }

    public String zznd() {
        return this.zzacu;
    }
}
