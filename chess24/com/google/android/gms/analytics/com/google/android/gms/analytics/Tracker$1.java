/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.text.TextUtils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.internal.zzre;
import com.google.android.gms.internal.zzrj;
import com.google.android.gms.internal.zzrr;
import com.google.android.gms.internal.zzrs;
import com.google.android.gms.internal.zzrt;
import com.google.android.gms.internal.zzrv;
import com.google.android.gms.internal.zzry;
import com.google.android.gms.internal.zzsb;
import com.google.android.gms.internal.zzsm;
import com.google.android.gms.internal.zzst;
import com.google.android.gms.internal.zzsv;
import com.google.android.gms.internal.zzsx;
import com.google.android.gms.internal.zztg;
import java.util.HashMap;
import java.util.Map;

class Tracker
implements Runnable {
    final /* synthetic */ long zzabA;
    final /* synthetic */ boolean zzabB;
    final /* synthetic */ boolean zzabC;
    final /* synthetic */ String zzabD;
    final /* synthetic */ Map zzabx;
    final /* synthetic */ boolean zzaby;
    final /* synthetic */ String zzabz;

    Tracker(Map map, boolean bl, String string, long l, boolean bl2, boolean bl3, String string2) {
        this.zzabx = map;
        this.zzaby = bl;
        this.zzabz = string;
        this.zzabA = l;
        this.zzabB = bl2;
        this.zzabC = bl3;
        this.zzabD = string2;
    }

    @Override
    public void run() {
        double d;
        long l;
        if (Tracker.this.zzabu.zzmu()) {
            this.zzabx.put("sc", "start");
        }
        zztg.zzd(this.zzabx, "cid", Tracker.this.zzlT().zzlX());
        Object object = (String)this.zzabx.get("sf");
        if (object != null && zztg.zza(d = zztg.zza((String)object, 100.0), (String)this.zzabx.get("cid"))) {
            Tracker.this.zzb("Sampling enabled. Hit sampled out. sample rate", d);
            return;
        }
        object = Tracker.this.zznx();
        if (this.zzaby) {
            zztg.zzb(this.zzabx, "ate", object.zzmV());
            zztg.zzc(this.zzabx, "adid", object.zznf());
        } else {
            this.zzabx.remove("ate");
            this.zzabx.remove("adid");
        }
        object = Tracker.this.zzny().zznX();
        zztg.zzc(this.zzabx, "an", object.zzmx());
        zztg.zzc(this.zzabx, "av", object.zzmy());
        zztg.zzc(this.zzabx, "aid", object.zzjI());
        zztg.zzc(this.zzabx, "aiid", object.zzmz());
        this.zzabx.put("v", "1");
        this.zzabx.put("_v", zzrv.zzacP);
        zztg.zzc(this.zzabx, "ul", Tracker.this.zznz().zzpb().getLanguage());
        zztg.zzc(this.zzabx, "sr", Tracker.this.zznz().zzpc());
        boolean bl = this.zzabz.equals("transaction") || this.zzabz.equals("item");
        if (!bl && !Tracker.this.zzabt.zzpv()) {
            Tracker.this.zznr().zzg(this.zzabx, "Too many hits sent too quickly, rate limiting invoked");
            return;
        }
        long l2 = l = zztg.zzce((String)this.zzabx.get("ht"));
        if (l == 0L) {
            l2 = this.zzabA;
        }
        if (this.zzabB) {
            object = new zzst(Tracker.this, this.zzabx, l2, this.zzabC);
            Tracker.this.zznr().zzc("Dry run enabled. Would have sent hit", object);
            return;
        }
        object = (String)this.zzabx.get("cid");
        HashMap<String, String> hashMap = new HashMap<String, String>();
        zztg.zza(hashMap, "uid", this.zzabx);
        zztg.zza(hashMap, "an", this.zzabx);
        zztg.zza(hashMap, "aid", this.zzabx);
        zztg.zza(hashMap, "av", this.zzabx);
        zztg.zza(hashMap, "aiid", this.zzabx);
        object = new zzry(0L, (String)object, this.zzabD, TextUtils.isEmpty((CharSequence)((CharSequence)this.zzabx.get("adid"))) ^ true, 0L, hashMap);
        l = Tracker.this.zzlZ().zza((zzry)object);
        this.zzabx.put("_s", String.valueOf(l));
        object = new zzst(Tracker.this, this.zzabx, l2, this.zzabC);
        Tracker.this.zzlZ().zza((zzst)object);
    }
}
