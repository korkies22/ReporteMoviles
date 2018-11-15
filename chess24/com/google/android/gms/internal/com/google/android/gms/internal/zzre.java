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

public final class zzre
extends zzf<zzre> {
    private String zzVQ;
    private String zzabK;
    private String zzabL;
    private String zzabM;

    public void setAppId(String string) {
        this.zzVQ = string;
    }

    public void setAppInstallerId(String string) {
        this.zzabM = string;
    }

    public void setAppName(String string) {
        this.zzabK = string;
    }

    public void setAppVersion(String string) {
        this.zzabL = string;
    }

    public String toString() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("appName", this.zzabK);
        hashMap.put("appVersion", this.zzabL);
        hashMap.put("appId", this.zzVQ);
        hashMap.put("appInstallerId", this.zzabM);
        return zzre.zzj(hashMap);
    }

    public void zza(zzre zzre2) {
        if (!TextUtils.isEmpty((CharSequence)this.zzabK)) {
            zzre2.setAppName(this.zzabK);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzabL)) {
            zzre2.setAppVersion(this.zzabL);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzVQ)) {
            zzre2.setAppId(this.zzVQ);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzabM)) {
            zzre2.setAppInstallerId(this.zzabM);
        }
    }

    @Override
    public /* synthetic */ void zzb(zzf zzf2) {
        this.zza((zzre)zzf2);
    }

    public String zzjI() {
        return this.zzVQ;
    }

    public String zzmx() {
        return this.zzabK;
    }

    public String zzmy() {
        return this.zzabL;
    }

    public String zzmz() {
        return this.zzabM;
    }
}
