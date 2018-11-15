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

public final class zzrl
extends zzf<zzrl> {
    private String mCategory;
    private String zzaca;
    private String zzacb;
    private long zzacc;

    public String getAction() {
        return this.zzaca;
    }

    public String getCategory() {
        return this.mCategory;
    }

    public String getLabel() {
        return this.zzacb;
    }

    public long getValue() {
        return this.zzacc;
    }

    public String toString() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("category", this.mCategory);
        hashMap.put("action", this.zzaca);
        hashMap.put("label", this.zzacb);
        hashMap.put("value", this.zzacc);
        return zzrl.zzj(hashMap);
    }

    public void zza(zzrl zzrl2) {
        if (!TextUtils.isEmpty((CharSequence)this.mCategory)) {
            zzrl2.zzbA(this.mCategory);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzaca)) {
            zzrl2.zzbB(this.zzaca);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzacb)) {
            zzrl2.zzbC(this.zzacb);
        }
        if (this.zzacc != 0L) {
            zzrl2.zzq(this.zzacc);
        }
    }

    @Override
    public /* synthetic */ void zzb(zzf zzf2) {
        this.zza((zzrl)zzf2);
    }

    public void zzbA(String string) {
        this.mCategory = string;
    }

    public void zzbB(String string) {
        this.zzaca = string;
    }

    public void zzbC(String string) {
        this.zzacb = string;
    }

    public void zzq(long l) {
        this.zzacc = l;
    }
}
