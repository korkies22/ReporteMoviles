/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 *  android.net.Uri
 *  android.text.TextUtils
 */
package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zztf;
import java.util.HashMap;
import java.util.Map;

private class Tracker.zza
extends zzru
implements GoogleAnalytics.zza {
    private boolean zzabF;
    private int zzabG;
    private long zzabH;
    private boolean zzabI;
    private long zzabJ;

    protected Tracker.zza(zzrw zzrw2) {
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
