/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.zzb;
import com.google.android.gms.analytics.zze;
import com.google.android.gms.analytics.zzf;
import com.google.android.gms.analytics.zzg;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzre;
import com.google.android.gms.internal.zzrj;
import com.google.android.gms.internal.zzrn;
import com.google.android.gms.internal.zzrr;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzsb;
import com.google.android.gms.internal.zzse;
import com.google.android.gms.internal.zzsm;
import java.util.List;
import java.util.ListIterator;

public class zza
extends zzg<zza> {
    private final zzrw zzaam;
    private boolean zzaan;

    public zza(zzrw zzrw2) {
        super(zzrw2.zznt(), zzrw2.zznq());
        this.zzaam = zzrw2;
    }

    public void enableAdvertisingIdCollection(boolean bl) {
        this.zzaan = bl;
    }

    @Override
    protected void zza(zze object) {
        if (TextUtils.isEmpty((CharSequence)(object = object.zzb(zzrn.class)).zzlX())) {
            object.setClientId(this.zzaam.zznH().zzop());
        }
        if (this.zzaan && TextUtils.isEmpty((CharSequence)object.zzmU())) {
            zzrr zzrr2 = this.zzaam.zznG();
            object.zzbE(zzrr2.zznf());
            object.zzR(zzrr2.zzmV());
        }
    }

    public void zzbn(String string) {
        zzac.zzdv(string);
        this.zzbo(string);
        this.zzmn().add(new zzb(this.zzaam, string));
    }

    public void zzbo(String string) {
        string = zzb.zzbp(string);
        ListIterator<zzi> listIterator = this.zzmn().listIterator();
        while (listIterator.hasNext()) {
            if (!string.equals((Object)listIterator.next().zzlQ())) continue;
            listIterator.remove();
        }
    }

    zzrw zzlM() {
        return this.zzaam;
    }

    @Override
    public zze zzlN() {
        zze zze2 = this.zzmm().zzmb();
        zze2.zza(this.zzaam.zzny().zznX());
        zze2.zza(this.zzaam.zznz().zzpb());
        this.zzd(zze2);
        return zze2;
    }
}
