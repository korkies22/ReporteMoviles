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

public final class zzrj
extends zzf<zzrj> {
    public int zzLQ;
    public int zzLR;
    private String zzabW;
    public int zzabX;
    public int zzabY;
    public int zzabZ;

    public String getLanguage() {
        return this.zzabW;
    }

    public void setLanguage(String string) {
        this.zzabW = string;
    }

    public String toString() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("language", this.zzabW);
        hashMap.put("screenColors", this.zzabX);
        hashMap.put("screenWidth", this.zzLQ);
        hashMap.put("screenHeight", this.zzLR);
        hashMap.put("viewportWidth", this.zzabY);
        hashMap.put("viewportHeight", this.zzabZ);
        return zzrj.zzj(hashMap);
    }

    public void zza(zzrj zzrj2) {
        if (this.zzabX != 0) {
            zzrj2.zzay(this.zzabX);
        }
        if (this.zzLQ != 0) {
            zzrj2.zzaz(this.zzLQ);
        }
        if (this.zzLR != 0) {
            zzrj2.zzaA(this.zzLR);
        }
        if (this.zzabY != 0) {
            zzrj2.zzaB(this.zzabY);
        }
        if (this.zzabZ != 0) {
            zzrj2.zzaC(this.zzabZ);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzabW)) {
            zzrj2.setLanguage(this.zzabW);
        }
    }

    public void zzaA(int n) {
        this.zzLR = n;
    }

    public void zzaB(int n) {
        this.zzabY = n;
    }

    public void zzaC(int n) {
        this.zzabZ = n;
    }

    public void zzay(int n) {
        this.zzabX = n;
    }

    public void zzaz(int n) {
        this.zzLQ = n;
    }

    @Override
    public /* synthetic */ void zzb(zzf zzf2) {
        this.zza((zzrj)zzf2);
    }

    public int zzmJ() {
        return this.zzabX;
    }

    public int zzmK() {
        return this.zzLQ;
    }

    public int zzmL() {
        return this.zzLR;
    }

    public int zzmM() {
        return this.zzabY;
    }

    public int zzmN() {
        return this.zzabZ;
    }
}
