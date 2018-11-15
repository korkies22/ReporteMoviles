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

public final class zzrf
extends zzf<zzrf> {
    private String mName;
    private String zzFy;
    private String zzGu;
    private String zzabN;
    private String zzabO;
    private String zzabP;
    private String zzabQ;
    private String zzabR;
    private String zzabS;
    private String zzabT;

    public String getContent() {
        return this.zzFy;
    }

    public String getId() {
        return this.zzGu;
    }

    public String getName() {
        return this.mName;
    }

    public String getSource() {
        return this.zzabN;
    }

    public void setName(String string) {
        this.mName = string;
    }

    public String toString() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("name", this.mName);
        hashMap.put("source", this.zzabN);
        hashMap.put("medium", this.zzabO);
        hashMap.put("keyword", this.zzabP);
        hashMap.put("content", this.zzFy);
        hashMap.put("id", this.zzGu);
        hashMap.put("adNetworkId", this.zzabQ);
        hashMap.put("gclid", this.zzabR);
        hashMap.put("dclid", this.zzabS);
        hashMap.put("aclid", this.zzabT);
        return zzrf.zzj(hashMap);
    }

    public void zza(zzrf zzrf2) {
        if (!TextUtils.isEmpty((CharSequence)this.mName)) {
            zzrf2.setName(this.mName);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzabN)) {
            zzrf2.zzbq(this.zzabN);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzabO)) {
            zzrf2.zzbr(this.zzabO);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzabP)) {
            zzrf2.zzbs(this.zzabP);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzFy)) {
            zzrf2.zzbt(this.zzFy);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzGu)) {
            zzrf2.zzbu(this.zzGu);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzabQ)) {
            zzrf2.zzbv(this.zzabQ);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzabR)) {
            zzrf2.zzbw(this.zzabR);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzabS)) {
            zzrf2.zzbx(this.zzabS);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzabT)) {
            zzrf2.zzby(this.zzabT);
        }
    }

    @Override
    public /* synthetic */ void zzb(zzf zzf2) {
        this.zza((zzrf)zzf2);
    }

    public void zzbq(String string) {
        this.zzabN = string;
    }

    public void zzbr(String string) {
        this.zzabO = string;
    }

    public void zzbs(String string) {
        this.zzabP = string;
    }

    public void zzbt(String string) {
        this.zzFy = string;
    }

    public void zzbu(String string) {
        this.zzGu = string;
    }

    public void zzbv(String string) {
        this.zzabQ = string;
    }

    public void zzbw(String string) {
        this.zzabR = string;
    }

    public void zzbx(String string) {
        this.zzabS = string;
    }

    public void zzby(String string) {
        this.zzabT = string;
    }

    public String zzmA() {
        return this.zzabO;
    }

    public String zzmB() {
        return this.zzabP;
    }

    public String zzmC() {
        return this.zzabQ;
    }

    public String zzmD() {
        return this.zzabR;
    }

    public String zzmE() {
        return this.zzabS;
    }

    public String zzmF() {
        return this.zzabT;
    }
}
