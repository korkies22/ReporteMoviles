/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.ExceptionReporter;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.internal.zzre;
import com.google.android.gms.internal.zzrj;
import com.google.android.gms.internal.zzrr;
import com.google.android.gms.internal.zzrs;
import com.google.android.gms.internal.zzrt;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzrv;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzry;
import com.google.android.gms.internal.zzsb;
import com.google.android.gms.internal.zzse;
import com.google.android.gms.internal.zzsm;
import com.google.android.gms.internal.zzst;
import com.google.android.gms.internal.zzsv;
import com.google.android.gms.internal.zzsx;
import com.google.android.gms.internal.zztf;
import com.google.android.gms.internal.zztg;
import com.google.android.gms.internal.zzth;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Tracker
extends zzru {
    private final Map<String, String> zzFs = new HashMap<String, String>();
    private boolean zzabr;
    private final Map<String, String> zzabs = new HashMap<String, String>();
    private final zzsv zzabt;
    private final zza zzabu;
    private ExceptionReporter zzabv;
    private zztf zzabw;

    Tracker(zzrw zzrw2, String string, zzsv zzsv2) {
        super(zzrw2);
        if (string != null) {
            this.zzFs.put("&tid", string);
        }
        this.zzFs.put("useSecure", "1");
        this.zzFs.put("&a", Integer.toString(new Random().nextInt(Integer.MAX_VALUE) + 1));
        this.zzabt = zzsv2 == null ? new zzsv("tracking", this.zznq()) : zzsv2;
        this.zzabu = new zza(zzrw2);
    }

    private static boolean zza(Map.Entry<String, String> entry) {
        String string = entry.getKey();
        entry.getValue();
        if (string.startsWith("&") && string.length() >= 2) {
            return true;
        }
        return false;
    }

    private static String zzb(Map.Entry<String, String> entry) {
        if (!Tracker.zza(entry)) {
            return null;
        }
        return entry.getKey().substring(1);
    }

    private static void zzb(Map<String, String> object, Map<String, String> map) {
        zzac.zzw(map);
        if (object == null) {
            return;
        }
        for (Map.Entry<String, String> entry : object.entrySet()) {
            String string = Tracker.zzb(entry);
            if (string == null) continue;
            map.put(string, entry.getValue());
        }
    }

    private static void zzc(Map<String, String> object, Map<String, String> map) {
        zzac.zzw(map);
        if (object == null) {
            return;
        }
        for (Map.Entry<String, String> entry : object.entrySet()) {
            String string = Tracker.zzb(entry);
            if (string == null || map.containsKey(string)) continue;
            map.put(string, entry.getValue());
        }
    }

    private boolean zzms() {
        if (this.zzabv != null) {
            return true;
        }
        return false;
    }

    static String zzq(Activity object) {
        zzac.zzw(object);
        object = object.getIntent();
        if (object == null) {
            return null;
        }
        if (TextUtils.isEmpty((CharSequence)(object = object.getStringExtra("android.intent.extra.REFERRER_NAME")))) {
            return null;
        }
        return object;
    }

    public void enableAdvertisingIdCollection(boolean bl) {
        this.zzabr = bl;
    }

    public void enableAutoActivityTracking(boolean bl) {
        this.zzabu.enableAutoActivityTracking(bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void enableExceptionReporting(boolean bl) {
        synchronized (this) {
            Object object;
            if (this.zzms() == bl) {
                return;
            }
            if (bl) {
                object = this.getContext();
                this.zzabv = new ExceptionReporter(this, Thread.getDefaultUncaughtExceptionHandler(), (Context)object);
                Thread.setDefaultUncaughtExceptionHandler(this.zzabv);
                object = "Uncaught exceptions will be reported to Google Analytics";
            } else {
                Thread.setDefaultUncaughtExceptionHandler(this.zzabv.zzlU());
                object = "Uncaught exceptions will not be reported to Google Analytics";
            }
            this.zzbO((String)object);
            return;
        }
    }

    public String get(String string) {
        this.zznA();
        if (TextUtils.isEmpty((CharSequence)string)) {
            return null;
        }
        if (this.zzFs.containsKey(string)) {
            return this.zzFs.get(string);
        }
        if (string.equals("&ul")) {
            return zztg.zza(Locale.getDefault());
        }
        if (string.equals("&cid")) {
            return this.zznw().zzop();
        }
        if (string.equals("&sr")) {
            return this.zznz().zzpc();
        }
        if (string.equals("&aid")) {
            return this.zzny().zznX().zzjI();
        }
        if (string.equals("&an")) {
            return this.zzny().zznX().zzmx();
        }
        if (string.equals("&av")) {
            return this.zzny().zznX().zzmy();
        }
        if (string.equals("&aiid")) {
            return this.zzny().zznX().zzmz();
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void send(Map<String, String> object) {
        long l = this.zznq().currentTimeMillis();
        if (this.zzlT().getAppOptOut()) {
            this.zzbP("AppOptOut is set to true. Not sending Google Analytics hit");
            return;
        }
        boolean bl = this.zzlT().isDryRunEnabled();
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        Tracker.zzb(this.zzFs, hashMap);
        Tracker.zzb(object, hashMap);
        boolean bl2 = zztg.zzg(this.zzFs.get("useSecure"), true);
        Tracker.zzc(this.zzabs, hashMap);
        this.zzabs.clear();
        object = (String)hashMap.get("t");
        if (TextUtils.isEmpty((CharSequence)object)) {
            this.zznr().zzg(hashMap, "Missing hit type parameter");
            return;
        }
        String string = (String)hashMap.get("tid");
        if (TextUtils.isEmpty((CharSequence)string)) {
            this.zznr().zzg(hashMap, "Missing tracking id parameter");
            return;
        }
        final boolean bl3 = this.zzmt();
        synchronized (this) {
            if ("screenview".equalsIgnoreCase((String)object) || "pageview".equalsIgnoreCase((String)object) || "appview".equalsIgnoreCase((String)object) || TextUtils.isEmpty((CharSequence)object)) {
                int n;
                int n2 = n = Integer.parseInt(this.zzFs.get("&a")) + 1;
                if (n >= Integer.MAX_VALUE) {
                    n2 = 1;
                }
                this.zzFs.put("&a", Integer.toString(n2));
            }
        }
        this.zznt().zzg(new Runnable((String)object, l, bl, bl2, string){
            final /* synthetic */ long zzabA;
            final /* synthetic */ boolean zzabB;
            final /* synthetic */ boolean zzabC;
            final /* synthetic */ String zzabD;
            final /* synthetic */ String zzabz;
            {
                this.zzabz = string;
                this.zzabA = l;
                this.zzabB = bl2;
                this.zzabC = bl32;
                this.zzabD = string2;
            }

            @Override
            public void run() {
                long l;
                double d;
                if (Tracker.this.zzabu.zzmu()) {
                    hashMap.put("sc", "start");
                }
                zztg.zzd(hashMap, "cid", Tracker.this.zzlT().zzlX());
                Object object = (String)hashMap.get("sf");
                if (object != null && zztg.zza(d = zztg.zza((String)object, 100.0), (String)hashMap.get("cid"))) {
                    Tracker.this.zzb("Sampling enabled. Hit sampled out. sample rate", d);
                    return;
                }
                object = Tracker.this.zznx();
                if (bl3) {
                    zztg.zzb(hashMap, "ate", object.zzmV());
                    zztg.zzc(hashMap, "adid", object.zznf());
                } else {
                    hashMap.remove("ate");
                    hashMap.remove("adid");
                }
                object = Tracker.this.zzny().zznX();
                zztg.zzc(hashMap, "an", object.zzmx());
                zztg.zzc(hashMap, "av", object.zzmy());
                zztg.zzc(hashMap, "aid", object.zzjI());
                zztg.zzc(hashMap, "aiid", object.zzmz());
                hashMap.put("v", "1");
                hashMap.put("_v", zzrv.zzacP);
                zztg.zzc(hashMap, "ul", Tracker.this.zznz().zzpb().getLanguage());
                zztg.zzc(hashMap, "sr", Tracker.this.zznz().zzpc());
                boolean bl = this.zzabz.equals("transaction") || this.zzabz.equals("item");
                if (!bl && !Tracker.this.zzabt.zzpv()) {
                    Tracker.this.zznr().zzg(hashMap, "Too many hits sent too quickly, rate limiting invoked");
                    return;
                }
                long l2 = l = zztg.zzce((String)hashMap.get("ht"));
                if (l == 0L) {
                    l2 = this.zzabA;
                }
                if (this.zzabB) {
                    object = new zzst(Tracker.this, hashMap, l2, this.zzabC);
                    Tracker.this.zznr().zzc("Dry run enabled. Would have sent hit", object);
                    return;
                }
                object = (String)hashMap.get("cid");
                HashMap<String, String> hashMap2 = new HashMap<String, String>();
                zztg.zza(hashMap2, "uid", hashMap);
                zztg.zza(hashMap2, "an", hashMap);
                zztg.zza(hashMap2, "aid", hashMap);
                zztg.zza(hashMap2, "av", hashMap);
                zztg.zza(hashMap2, "aiid", hashMap);
                object = new zzry(0L, (String)object, this.zzabD, TextUtils.isEmpty((CharSequence)((CharSequence)hashMap.get("adid"))) ^ true, 0L, hashMap2);
                l = Tracker.this.zzlZ().zza((zzry)object);
                hashMap.put("_s", String.valueOf(l));
                object = new zzst(Tracker.this, hashMap, l2, this.zzabC);
                Tracker.this.zzlZ().zza((zzst)object);
            }
        });
    }

    public void set(String string, String string2) {
        zzac.zzb(string, (Object)"Key should be non-null");
        if (TextUtils.isEmpty((CharSequence)string)) {
            return;
        }
        this.zzFs.put(string, string2);
    }

    public void setAnonymizeIp(boolean bl) {
        this.set("&aip", zztg.zzW(bl));
    }

    public void setAppId(String string) {
        this.set("&aid", string);
    }

    public void setAppInstallerId(String string) {
        this.set("&aiid", string);
    }

    public void setAppName(String string) {
        this.set("&an", string);
    }

    public void setAppVersion(String string) {
        this.set("&av", string);
    }

    public void setCampaignParamsOnNextHit(Uri object) {
        if (object != null) {
            if (object.isOpaque()) {
                return;
            }
            if (TextUtils.isEmpty((CharSequence)(object = object.getQueryParameter("referrer")))) {
                return;
            }
            object = (object = String.valueOf(object)).length() != 0 ? "http://hostname/?".concat((String)object) : new String("http://hostname/?");
            String string = (object = Uri.parse((String)object)).getQueryParameter("utm_id");
            if (string != null) {
                this.zzabs.put("&ci", string);
            }
            if ((string = object.getQueryParameter("anid")) != null) {
                this.zzabs.put("&anid", string);
            }
            if ((string = object.getQueryParameter("utm_campaign")) != null) {
                this.zzabs.put("&cn", string);
            }
            if ((string = object.getQueryParameter("utm_content")) != null) {
                this.zzabs.put("&cc", string);
            }
            if ((string = object.getQueryParameter("utm_medium")) != null) {
                this.zzabs.put("&cm", string);
            }
            if ((string = object.getQueryParameter("utm_source")) != null) {
                this.zzabs.put("&cs", string);
            }
            if ((string = object.getQueryParameter("utm_term")) != null) {
                this.zzabs.put("&ck", string);
            }
            if ((string = object.getQueryParameter("dclid")) != null) {
                this.zzabs.put("&dclid", string);
            }
            if ((string = object.getQueryParameter("gclid")) != null) {
                this.zzabs.put("&gclid", string);
            }
            if ((object = object.getQueryParameter("aclid")) != null) {
                this.zzabs.put("&aclid", (String)object);
            }
        }
    }

    public void setClientId(String string) {
        this.set("&cid", string);
    }

    public void setEncoding(String string) {
        this.set("&de", string);
    }

    public void setHostname(String string) {
        this.set("&dh", string);
    }

    public void setLanguage(String string) {
        this.set("&ul", string);
    }

    public void setLocation(String string) {
        this.set("&dl", string);
    }

    public void setPage(String string) {
        this.set("&dp", string);
    }

    public void setReferrer(String string) {
        this.set("&dr", string);
    }

    public void setSampleRate(double d) {
        this.set("&sf", Double.toString(d));
    }

    public void setScreenColors(String string) {
        this.set("&sd", string);
    }

    public void setScreenName(String string) {
        this.set("&cd", string);
    }

    public void setScreenResolution(int n, int n2) {
        if (n < 0 && n2 < 0) {
            this.zzbR("Invalid width or height. The values should be non-negative.");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(23);
        stringBuilder.append(n);
        stringBuilder.append("x");
        stringBuilder.append(n2);
        this.set("&sr", stringBuilder.toString());
    }

    public void setSessionTimeout(long l) {
        this.zzabu.setSessionTimeout(l * 1000L);
    }

    public void setTitle(String string) {
        this.set("&dt", string);
    }

    public void setUseSecure(boolean bl) {
        this.set("useSecure", zztg.zzW(bl));
    }

    public void setViewportSize(String string) {
        this.set("&vp", string);
    }

    void zza(zztf object) {
        boolean bl;
        this.zzbO("Loading Tracker config values");
        this.zzabw = object;
        if (this.zzabw.zzpS()) {
            object = this.zzabw.getTrackingId();
            this.set("&tid", (String)object);
            this.zza("trackingId loaded", object);
        }
        if (this.zzabw.zzpT()) {
            object = Double.toString(this.zzabw.zzpU());
            this.set("&sf", (String)object);
            this.zza("Sample frequency loaded", object);
        }
        if (this.zzabw.zzpV()) {
            int n = this.zzabw.getSessionTimeout();
            this.setSessionTimeout(n);
            this.zza("Session timeout loaded", n);
        }
        if (this.zzabw.zzpW()) {
            bl = this.zzabw.zzpX();
            this.enableAutoActivityTracking(bl);
            this.zza("Auto activity tracking loaded", bl);
        }
        if (this.zzabw.zzpY()) {
            bl = this.zzabw.zzpZ();
            if (bl) {
                this.set("&aip", "1");
            }
            this.zza("Anonymize ip loaded", bl);
        }
        this.enableExceptionReporting(this.zzabw.zzqa());
    }

    @Override
    protected void zzmr() {
        this.zzabu.initialize();
        String string = this.zzma().zzmx();
        if (string != null) {
            this.set("&an", string);
        }
        if ((string = this.zzma().zzmy()) != null) {
            this.set("&av", string);
        }
    }

    boolean zzmt() {
        return this.zzabr;
    }

    private class zza
    extends zzru
    implements GoogleAnalytics.zza {
        private boolean zzabF;
        private int zzabG;
        private long zzabH;
        private boolean zzabI;
        private long zzabJ;

        protected zza(zzrw zzrw2) {
            super(zzrw2);
            this.zzabH = -1L;
        }

        private void zzmv() {
            if (this.zzabH < 0L && !this.zzabF) {
                this.zzlT().zzb(Tracker.this.zzabu);
                return;
            }
            this.zzlT().zza(Tracker.this.zzabu);
        }

        public void enableAutoActivityTracking(boolean bl) {
            this.zzabF = bl;
            this.zzmv();
        }

        public void setSessionTimeout(long l) {
            this.zzabH = l;
            this.zzmv();
        }

        @Override
        protected void zzmr() {
        }

        public boolean zzmu() {
            synchronized (this) {
                boolean bl = this.zzabI;
                this.zzabI = false;
                return bl;
            }
        }

        boolean zzmw() {
            if (this.zznq().elapsedRealtime() >= this.zzabJ + Math.max(1000L, this.zzabH)) {
                return true;
            }
            return false;
        }

        @Override
        public void zzo(Activity object) {
            if (this.zzabG == 0 && this.zzmw()) {
                this.zzabI = true;
            }
            ++this.zzabG;
            if (this.zzabF) {
                Object object2 = object.getIntent();
                if (object2 != null) {
                    Tracker.this.setCampaignParamsOnNextHit(object2.getData());
                }
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("&t", "screenview");
                Tracker tracker = Tracker.this;
                object2 = Tracker.this.zzabw != null ? Tracker.this.zzabw.zzr((Activity)object) : object.getClass().getCanonicalName();
                tracker.set("&cd", (String)object2);
                if (TextUtils.isEmpty((CharSequence)((CharSequence)hashMap.get("&dr"))) && !TextUtils.isEmpty((CharSequence)(object = Tracker.zzq(object)))) {
                    hashMap.put("&dr", (String)object);
                }
                Tracker.this.send(hashMap);
            }
        }

        @Override
        public void zzp(Activity activity) {
            --this.zzabG;
            this.zzabG = Math.max(0, this.zzabG);
            if (this.zzabG == 0) {
                this.zzabJ = this.zznq().elapsedRealtime();
            }
        }
    }

}
