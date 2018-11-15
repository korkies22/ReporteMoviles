/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.analytics.zzf;
import com.google.android.gms.common.internal.zzac;
import java.util.HashMap;
import java.util.UUID;

public final class zzro
extends zzf<zzro> {
    private String zzacn;
    private int zzaco;
    private int zzacp;
    private String zzacq;
    private String zzacr;
    private boolean zzacs;
    private boolean zzact;

    public zzro() {
        this(false);
    }

    public zzro(boolean bl) {
        this(bl, zzro.zzmZ());
    }

    public zzro(boolean bl, int n) {
        zzac.zzcR(n);
        this.zzaco = n;
        this.zzact = bl;
    }

    static int zzmZ() {
        UUID uUID = UUID.randomUUID();
        int n = (int)(uUID.getLeastSignificantBits() & Integer.MAX_VALUE);
        if (n != 0) {
            return n;
        }
        n = (int)(uUID.getMostSignificantBits() & Integer.MAX_VALUE);
        if (n != 0) {
            return n;
        }
        Log.e((String)"GAv4", (String)"UUID.randomUUID() returned 0.");
        return Integer.MAX_VALUE;
    }

    public void setScreenName(String string) {
        this.zzacn = string;
    }

    public String toString() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("screenName", this.zzacn);
        hashMap.put("interstitial", this.zzacs);
        hashMap.put("automatic", this.zzact);
        hashMap.put("screenId", this.zzaco);
        hashMap.put("referrerScreenId", this.zzacp);
        hashMap.put("referrerScreenName", this.zzacq);
        hashMap.put("referrerUri", this.zzacr);
        return zzro.zzj(hashMap);
    }

    public void zzT(boolean bl) {
        this.zzact = bl;
    }

    public void zzU(boolean bl) {
        this.zzacs = bl;
    }

    public void zza(zzro zzro2) {
        if (!TextUtils.isEmpty((CharSequence)this.zzacn)) {
            zzro2.setScreenName(this.zzacn);
        }
        if (this.zzaco != 0) {
            zzro2.zzaD(this.zzaco);
        }
        if (this.zzacp != 0) {
            zzro2.zzaE(this.zzacp);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzacq)) {
            zzro2.zzbG(this.zzacq);
        }
        if (!TextUtils.isEmpty((CharSequence)this.zzacr)) {
            zzro2.zzbH(this.zzacr);
        }
        if (this.zzacs) {
            zzro2.zzU(this.zzacs);
        }
        if (this.zzact) {
            zzro2.zzT(this.zzact);
        }
    }

    public void zzaD(int n) {
        this.zzaco = n;
    }

    public void zzaE(int n) {
        this.zzacp = n;
    }

    @Override
    public /* synthetic */ void zzb(zzf zzf2) {
        this.zza((zzro)zzf2);
    }

    public void zzbG(String string) {
        this.zzacq = string;
    }

    public void zzbH(String string) {
        String string2 = string;
        if (TextUtils.isEmpty((CharSequence)string)) {
            string2 = null;
        }
        this.zzacr = string2;
    }

    public String zzna() {
        return this.zzacn;
    }

    public int zznb() {
        return this.zzaco;
    }

    public String zznc() {
        return this.zzacr;
    }
}
