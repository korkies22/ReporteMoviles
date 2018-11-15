/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.net.Uri
 *  android.net.Uri$Builder
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzaa;
import com.google.android.gms.tagmanager.zzat;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzdd;
import com.google.android.gms.tagmanager.zzdk;
import com.google.android.gms.tagmanager.zzdm;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class zzj
extends zzdk {
    private static final String ID = zzag.zzew.toString();
    private static final String URL = zzah.zzjZ.toString();
    private static final String zzbCM = zzah.zzfy.toString();
    private static final String zzbCN = zzah.zzjY.toString();
    static final String zzbCO;
    private static final Set<String> zzbCP;
    private final Context mContext;
    private final zza zzbCQ;

    static {
        String string = ID;
        StringBuilder stringBuilder = new StringBuilder(17 + String.valueOf(string).length());
        stringBuilder.append("gtm_");
        stringBuilder.append(string);
        stringBuilder.append("_unrepeatable");
        zzbCO = stringBuilder.toString();
        zzbCP = new HashSet<String>();
    }

    public zzj(Context context) {
        this(context, new zza(){

            @Override
            public zzat zzOx() {
                return zzaa.zzbB(Context.this);
            }
        });
    }

    zzj(Context context, zza zza2) {
        super(ID, URL);
        this.zzbCQ = zza2;
        this.mContext = context;
    }

    private boolean zzgR(String string) {
        synchronized (this) {
            block6 : {
                boolean bl = this.zzgT(string);
                if (!bl) break block6;
                return true;
            }
            if (this.zzgS(string)) {
                zzbCP.add(string);
                return true;
            }
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void zzaa(Map<String, zzaj.zza> object) {
        String string = object.get(zzbCN) != null ? zzdm.zze(object.get(zzbCN)) : null;
        if (string != null && this.zzgR(string)) {
            return;
        }
        Uri.Builder builder = Uri.parse((String)zzdm.zze(object.get(URL))).buildUpon();
        if ((object = object.get(zzbCM)) != null) {
            if (!((object = zzdm.zzj((zzaj.zza)object)) instanceof List)) {
                object = String.valueOf(builder.build().toString());
                object = object.length() != 0 ? "ArbitraryPixel: additional params not a list: not sending partial hit: ".concat((String)object) : new String("ArbitraryPixel: additional params not a list: not sending partial hit: ");
                zzbo.e((String)object);
                return;
            }
            object = ((List)object).iterator();
            while (object.hasNext()) {
                Object object2 = object.next();
                if (!(object2 instanceof Map)) {
                    object = String.valueOf(builder.build().toString());
                    object = object.length() != 0 ? "ArbitraryPixel: additional params contains non-map: not sending partial hit: ".concat((String)object) : new String("ArbitraryPixel: additional params contains non-map: not sending partial hit: ");
                    zzbo.e((String)object);
                    return;
                }
                for (Map.Entry entry : ((Map)object2).entrySet()) {
                    builder.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                }
            }
        }
        object = builder.build().toString();
        this.zzbCQ.zzOx().zzhi((String)object);
        object = String.valueOf(object);
        object = object.length() != 0 ? "ArbitraryPixel: url = ".concat((String)object) : new String("ArbitraryPixel: url = ");
        zzbo.v((String)object);
        if (string != null) {
            synchronized (zzj.class) {
                zzbCP.add(string);
                zzdd.zzc(this.mContext, zzbCO, string, "true");
                return;
            }
        }
    }

    boolean zzgS(String string) {
        return this.mContext.getSharedPreferences(zzbCO, 0).contains(string);
    }

    boolean zzgT(String string) {
        return zzbCP.contains(string);
    }

    public static interface zza {
        public zzat zzOx();
    }

}
