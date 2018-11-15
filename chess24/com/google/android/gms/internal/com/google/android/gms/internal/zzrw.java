/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.internal.zzrr;
import com.google.android.gms.internal.zzrs;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzrv;
import com.google.android.gms.internal.zzrx;
import com.google.android.gms.internal.zzsb;
import com.google.android.gms.internal.zzse;
import com.google.android.gms.internal.zzsj;
import com.google.android.gms.internal.zzsm;
import com.google.android.gms.internal.zzsn;
import com.google.android.gms.internal.zzsq;
import com.google.android.gms.internal.zzsx;
import com.google.android.gms.internal.zzta;
import com.google.android.gms.internal.zzth;

public class zzrw {
    private static volatile zzrw zzacQ;
    private final Context mContext;
    private final Context zzacR;
    private final zzsj zzacS;
    private final zzsx zzacT;
    private final zzh zzacU;
    private final zzrs zzacV;
    private final zzsn zzacW;
    private final zzth zzacX;
    private final zzta zzacY;
    private final GoogleAnalytics zzacZ;
    private final zzse zzada;
    private final zzrr zzadb;
    private final zzsb zzadc;
    private final zzsm zzadd;
    private final zze zzuI;

    protected zzrw(zzrx object) {
        Object object2 = object.getApplicationContext();
        zzac.zzb(object2, (Object)"Application context can't be null");
        Object object3 = object.zznC();
        zzac.zzw(object3);
        this.mContext = object2;
        this.zzacR = object3;
        this.zzuI = object.zzh(this);
        this.zzacS = object.zzg(this);
        object3 = object.zzf(this);
        object3.initialize();
        this.zzacT = object3;
        object3 = this.zznr();
        Object object4 = zzrv.VERSION;
        Object object5 = new StringBuilder(134 + String.valueOf(object4).length());
        object5.append("Google Analytics ");
        object5.append((String)object4);
        object5.append(" is starting up. To enable debug logging on a device run:\n  adb shell setprop log.tag.GAv4 DEBUG\n  adb logcat -s GAv4");
        object3.zzbQ(object5.toString());
        object3 = object.zzq(this);
        object3.initialize();
        this.zzacY = object3;
        object3 = object.zze(this);
        object3.initialize();
        this.zzacX = object3;
        object3 = object.zzl(this);
        object4 = object.zzd(this);
        object5 = object.zzc(this);
        zzsb zzsb2 = object.zzb(this);
        zzsm zzsm2 = object.zza(this);
        object2 = object.zzX((Context)object2);
        object2.zza(this.zznB());
        this.zzacU = object2;
        object2 = object.zzi(this);
        object4.initialize();
        this.zzada = object4;
        object5.initialize();
        this.zzadb = object5;
        zzsb2.initialize();
        this.zzadc = zzsb2;
        zzsm2.initialize();
        this.zzadd = zzsm2;
        object = object.zzp(this);
        object.initialize();
        this.zzacW = object;
        object3.initialize();
        this.zzacV = object3;
        object2.initialize();
        this.zzacZ = object2;
        object3.start();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzrw zzW(Context object) {
        zzac.zzw(object);
        if (zzacQ != null) return zzacQ;
        synchronized (zzrw.class) {
            if (zzacQ != null) return zzacQ;
            zze zze2 = com.google.android.gms.common.util.zzh.zzyv();
            long l = zze2.elapsedRealtime();
            object = new zzrw(new zzrx((Context)object));
            zzacQ = object;
            GoogleAnalytics.zzlW();
            l = zze2.elapsedRealtime() - l;
            long l2 = zzsq.zzaeY.get();
            if (l <= l2) return zzacQ;
            object.zznr().zzc("Slow initialization (ms)", l, l2);
            return zzacQ;
        }
    }

    private void zza(zzru zzru2) {
        zzac.zzb(zzru2, (Object)"Analytics service not created/initialized");
        zzac.zzb(zzru2.isInitialized(), (Object)"Analytics service not initialized");
    }

    public Context getContext() {
        return this.mContext;
    }

    public zzrs zzlZ() {
        this.zza(this.zzacV);
        return this.zzacV;
    }

    public zzth zzma() {
        this.zza(this.zzacX);
        return this.zzacX;
    }

    public void zzmq() {
        zzh.zzmq();
    }

    protected Thread.UncaughtExceptionHandler zznB() {
        return new Thread.UncaughtExceptionHandler(){

            @Override
            public void uncaughtException(Thread object, Throwable throwable) {
                object = zzrw.this.zznD();
                if (object != null) {
                    object.zze("Job execution failed", throwable);
                }
            }
        };
    }

    public Context zznC() {
        return this.zzacR;
    }

    public zzsx zznD() {
        return this.zzacT;
    }

    public GoogleAnalytics zznE() {
        zzac.zzw(this.zzacZ);
        zzac.zzb(this.zzacZ.isInitialized(), (Object)"Analytics instance not initialized");
        return this.zzacZ;
    }

    public zzta zznF() {
        if (this.zzacY != null && this.zzacY.isInitialized()) {
            return this.zzacY;
        }
        return null;
    }

    public zzrr zznG() {
        this.zza(this.zzadb);
        return this.zzadb;
    }

    public zzse zznH() {
        this.zza(this.zzada);
        return this.zzada;
    }

    public zze zznq() {
        return this.zzuI;
    }

    public zzsx zznr() {
        this.zza(this.zzacT);
        return this.zzacT;
    }

    public zzsj zzns() {
        return this.zzacS;
    }

    public zzh zznt() {
        zzac.zzw(this.zzacU);
        return this.zzacU;
    }

    public zzsn zznu() {
        this.zza(this.zzacW);
        return this.zzacW;
    }

    public zzta zznv() {
        this.zza(this.zzacY);
        return this.zzacY;
    }

    public zzsb zzny() {
        this.zza(this.zzadc);
        return this.zzadc;
    }

    public zzsm zznz() {
        return this.zzadd;
    }

}
