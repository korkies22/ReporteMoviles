/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.util.Log
 */
package com.google.android.gms.analytics;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzrs;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzse;
import com.google.android.gms.internal.zzsq;
import com.google.android.gms.internal.zzsv;
import com.google.android.gms.internal.zzsw;
import com.google.android.gms.internal.zzte;
import com.google.android.gms.internal.zztf;
import com.google.android.gms.internal.zzth;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class GoogleAnalytics
extends com.google.android.gms.analytics.zza {
    private static List<Runnable> zzaaF = new ArrayList<Runnable>();
    private Set<zza> zzaaG = new HashSet<zza>();
    private boolean zzaaH;
    private boolean zzaaI;
    private volatile boolean zzaaJ;
    private boolean zzaaK;
    private boolean zztW;

    public GoogleAnalytics(zzrw zzrw2) {
        super(zzrw2);
    }

    @RequiresPermission(allOf={"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public static GoogleAnalytics getInstance(Context context) {
        return zzrw.zzW(context).zznE();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void zzlW() {
        synchronized (GoogleAnalytics.class) {
            if (zzaaF != null) {
                Iterator<Runnable> iterator = zzaaF.iterator();
                while (iterator.hasNext()) {
                    iterator.next().run();
                }
                zzaaF = null;
            }
            return;
        }
    }

    private zzrs zzlZ() {
        return this.zzlM().zzlZ();
    }

    private zzth zzma() {
        return this.zzlM().zzma();
    }

    public void dispatchLocalHits() {
        this.zzlZ().zznk();
    }

    @TargetApi(value=14)
    public void enableAutoActivityReports(Application application) {
        if (Build.VERSION.SDK_INT >= 14 && !this.zzaaH) {
            application.registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)new zzb());
            this.zzaaH = true;
        }
    }

    public boolean getAppOptOut() {
        return this.zzaaJ;
    }

    @Deprecated
    public Logger getLogger() {
        return zzsw.getLogger();
    }

    public void initialize() {
        this.zzlV();
        this.zztW = true;
    }

    public boolean isDryRunEnabled() {
        return this.zzaaI;
    }

    public boolean isInitialized() {
        return this.zztW;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Tracker newTracker(int n) {
        synchronized (this) {
            zztf zztf2;
            Tracker tracker = new Tracker(this.zzlM(), null, null);
            if (n > 0 && (zztf2 = (zztf)new zzte(this.zzlM()).zzaG(n)) != null) {
                tracker.zza(zztf2);
            }
            tracker.initialize();
            return tracker;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Tracker newTracker(String object) {
        synchronized (this) {
            object = new Tracker(this.zzlM(), (String)object, null);
            object.initialize();
            return object;
        }
    }

    public void reportActivityStart(Activity activity) {
        if (!this.zzaaH) {
            this.zzm(activity);
        }
    }

    public void reportActivityStop(Activity activity) {
        if (!this.zzaaH) {
            this.zzn(activity);
        }
    }

    public void setAppOptOut(boolean bl) {
        this.zzaaJ = bl;
        if (this.zzaaJ) {
            this.zzlZ().zznj();
        }
    }

    public void setDryRun(boolean bl) {
        this.zzaaI = bl;
    }

    public void setLocalDispatchPeriod(int n) {
        this.zzlZ().setLocalDispatchPeriod(n);
    }

    @Deprecated
    public void setLogger(Logger object) {
        zzsw.setLogger((Logger)object);
        if (!this.zzaaK) {
            object = zzsq.zzaek.get();
            String string = zzsq.zzaek.get();
            StringBuilder stringBuilder = new StringBuilder(112 + String.valueOf(string).length());
            stringBuilder.append("GoogleAnalytics.setLogger() is deprecated. To enable debug logging, please run:\nadb shell setprop log.tag.");
            stringBuilder.append(string);
            stringBuilder.append(" DEBUG");
            Log.i((String)object, (String)stringBuilder.toString());
            this.zzaaK = true;
        }
    }

    void zza(zza zza2) {
        this.zzaaG.add(zza2);
        zza2 = this.zzlM().getContext();
        if (zza2 instanceof Application) {
            this.enableAutoActivityReports((Application)zza2);
        }
    }

    void zzb(zza zza2) {
        this.zzaaG.remove(zza2);
    }

    void zzlV() {
        zzth zzth2 = this.zzma();
        zzth2.zzpi();
        if (zzth2.zzpm()) {
            this.setDryRun(zzth2.zzpn());
        }
        zzth2.zzpi();
    }

    public String zzlX() {
        zzac.zzdo("getClientId can not be called from the main thread");
        return this.zzlM().zznH().zzop();
    }

    void zzlY() {
        this.zzlZ().zznl();
    }

    void zzm(Activity activity) {
        Iterator<zza> iterator = this.zzaaG.iterator();
        while (iterator.hasNext()) {
            iterator.next().zzo(activity);
        }
    }

    void zzn(Activity activity) {
        Iterator<zza> iterator = this.zzaaG.iterator();
        while (iterator.hasNext()) {
            iterator.next().zzp(activity);
        }
    }

    static interface zza {
        public void zzo(Activity var1);

        public void zzp(Activity var1);
    }

    @TargetApi(value=14)
    class zzb
    implements Application.ActivityLifecycleCallbacks {
        zzb() {
        }

        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public void onActivityDestroyed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityStarted(Activity activity) {
            GoogleAnalytics.this.zzm(activity);
        }

        public void onActivityStopped(Activity activity) {
            GoogleAnalytics.this.zzn(activity);
        }
    }

}
