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

public final class zzrm
extends zzf<zzrm> {
    public String zzacd;
    public boolean zzace;

    public String getDescription() {
        return this.zzacd;
    }

    public void setDescription(String string) {
        this.zzacd = string;
    }

    public String toString() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("description", this.zzacd);
        hashMap.put("fatal", this.zzace);
        return zzrm.zzj(hashMap);
    }

    public void zzQ(boolean bl) {
        this.zzace = bl;
    }

    public void zza(zzrm zzrm2) {
        if (!TextUtils.isEmpty((CharSequence)this.zzacd)) {
            zzrm2.setDescription(this.zzacd);
        }
        if (this.zzace) {
            zzrm2.zzQ(this.zzace);
        }
    }

    @Override
    public /* synthetic */ void zzb(zzf zzf2) {
        this.zza((zzrm)zzf2);
    }

    public boolean zzmS() {
        return this.zzace;
    }
}
