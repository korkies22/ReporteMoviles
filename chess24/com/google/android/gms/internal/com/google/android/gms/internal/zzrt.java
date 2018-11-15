/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.internal.zzrr;
import com.google.android.gms.internal.zzrs;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzsb;
import com.google.android.gms.internal.zzse;
import com.google.android.gms.internal.zzsj;
import com.google.android.gms.internal.zzsm;
import com.google.android.gms.internal.zzsn;
import com.google.android.gms.internal.zzsq;
import com.google.android.gms.internal.zzsx;
import com.google.android.gms.internal.zzta;
import com.google.android.gms.internal.zzth;

public class zzrt {
    private final zzrw zzacN;

    protected zzrt(zzrw zzrw2) {
        zzac.zzw(zzrw2);
        this.zzacN = zzrw2;
    }

    private void zza(int n, String string, Object object, Object object2, Object object3) {
        Object object4 = this.zzacN != null ? this.zzacN.zznD() : null;
        if (object4 != null) {
            object4.zza(n, string, object, object2, object3);
            return;
        }
        object4 = zzsq.zzaek.get();
        if (Log.isLoggable((String)object4, (int)n)) {
            Log.println((int)n, (String)object4, (String)zzrt.zzc(string, object, object2, object3));
        }
    }

    protected static String zzc(String object, Object object2, Object object3, Object object4) {
        String string = object;
        if (object == null) {
            string = "";
        }
        String string2 = zzrt.zzk(object2);
        object3 = zzrt.zzk(object3);
        object4 = zzrt.zzk(object4);
        StringBuilder stringBuilder = new StringBuilder();
        object = "";
        if (!TextUtils.isEmpty((CharSequence)string)) {
            stringBuilder.append(string);
            object = ": ";
        }
        object2 = object;
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            stringBuilder.append((String)object);
            stringBuilder.append(string2);
            object2 = ", ";
        }
        object = object2;
        if (!TextUtils.isEmpty((CharSequence)object3)) {
            stringBuilder.append((String)object2);
            stringBuilder.append((String)object3);
            object = ", ";
        }
        if (!TextUtils.isEmpty((CharSequence)object4)) {
            stringBuilder.append((String)object);
            stringBuilder.append((String)object4);
        }
        return stringBuilder.toString();
    }

    private static String zzk(Object object) {
        if (object == null) {
            return "";
        }
        if (object instanceof String) {
            return (String)object;
        }
        if (object instanceof Boolean) {
            if (object == Boolean.TRUE) {
                return "true";
            }
            return "false";
        }
        if (object instanceof Throwable) {
            return ((Throwable)object).toString();
        }
        return object.toString();
    }

    protected Context getContext() {
        return this.zzacN.getContext();
    }

    public void zza(String string, Object object) {
        this.zza(2, string, object, null, null);
    }

    public void zza(String string, Object object, Object object2) {
        this.zza(2, string, object, object2, null);
    }

    public void zza(String string, Object object, Object object2, Object object3) {
        this.zza(3, string, object, object2, object3);
    }

    public void zzb(String string, Object object) {
        this.zza(3, string, object, null, null);
    }

    public void zzb(String string, Object object, Object object2) {
        this.zza(3, string, object, object2, null);
    }

    public void zzb(String string, Object object, Object object2, Object object3) {
        this.zza(5, string, object, object2, object3);
    }

    public void zzbO(String string) {
        this.zza(2, string, null, null, null);
    }

    public void zzbP(String string) {
        this.zza(3, string, null, null, null);
    }

    public void zzbQ(String string) {
        this.zza(4, string, null, null, null);
    }

    public void zzbR(String string) {
        this.zza(5, string, null, null, null);
    }

    public void zzbS(String string) {
        this.zza(6, string, null, null, null);
    }

    public void zzc(String string, Object object) {
        this.zza(4, string, object, null, null);
    }

    public void zzc(String string, Object object, Object object2) {
        this.zza(5, string, object, object2, null);
    }

    public void zzd(String string, Object object) {
        this.zza(5, string, object, null, null);
    }

    public void zzd(String string, Object object, Object object2) {
        this.zza(6, string, object, object2, null);
    }

    public void zze(String string, Object object) {
        this.zza(6, string, object, null, null);
    }

    public boolean zzkh() {
        return Log.isLoggable((String)zzsq.zzaek.get(), (int)2);
    }

    public GoogleAnalytics zzlT() {
        return this.zzacN.zznE();
    }

    protected zzrs zzlZ() {
        return this.zzacN.zzlZ();
    }

    protected zzth zzma() {
        return this.zzacN.zzma();
    }

    protected void zzmq() {
        this.zzacN.zzmq();
    }

    public zzrw zznp() {
        return this.zzacN;
    }

    protected zze zznq() {
        return this.zzacN.zznq();
    }

    protected zzsx zznr() {
        return this.zzacN.zznr();
    }

    protected zzsj zzns() {
        return this.zzacN.zzns();
    }

    protected zzh zznt() {
        return this.zzacN.zznt();
    }

    protected zzsn zznu() {
        return this.zzacN.zznu();
    }

    protected zzta zznv() {
        return this.zzacN.zznv();
    }

    protected zzse zznw() {
        return this.zzacN.zznH();
    }

    protected zzrr zznx() {
        return this.zzacN.zznG();
    }

    protected zzsb zzny() {
        return this.zzacN.zzny();
    }

    protected zzsm zznz() {
        return this.zzacN.zznz();
    }
}
