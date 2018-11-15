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

public final class zzrq
extends zzf<zzrq> {
    public String mCategory;
    public String zzacb;
    public String zzacw;
    public long zzacx;

    public String getCategory() {
        return this.mCategory;
    }

    public String getLabel() {
        return this.zzacb;
    }

    public long getTimeInMillis() {
        return this.zzacx;
    }

    public void setTimeInMillis(long l) {
        this.zzacx = l;
    }

    public String toString() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("variableName", this.zzacw);
        hashMap.put("timeInMillis", this.zzacx);
        hashMap.put("category", this.mCategory);
        hashMap.put("label", this.zzacb);
        return zzrq.zzj(hashMap);
    }

    public void zza(zzrq zzrq2) {
        if (!TextUtils.isEmpty((CharSequence)this.zzacw)) {
            zzrq2.zzbK(this.zzacw);
        }
        if (this.zzacx != 0L) {
            zzrq2.setTimeInMillis(this.zzacx);
        }
        if (!TextUtils.isEmpty((CharSequence)this.mCategory)) {
            zzrq2.zzbA(this.mCategory);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzacb)) {
            zzrq2.zzbC(this.zzacb);
        }
    }

    @Override
    public /* synthetic */ void zzb(zzf zzf2) {
        this.zza((zzrq)zzf2);
    }

    public void zzbA(String string) {
        this.mCategory = string;
    }

    public void zzbC(String string) {
        this.zzacb = string;
    }

    public void zzbK(String string) {
        this.zzacw = string;
    }

    public String zzne() {
        return this.zzacw;
    }
}
