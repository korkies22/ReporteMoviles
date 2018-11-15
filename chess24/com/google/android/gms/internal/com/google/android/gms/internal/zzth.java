/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.google.android.gms.internal.zzru;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzsr;
import com.google.android.gms.internal.zzss;

public class zzth
extends zzru {
    protected boolean zzaaI;
    protected String zzabK;
    protected String zzabL;
    protected int zzaeb;
    protected boolean zzafU;
    protected boolean zzafV;
    protected int zzafd;

    public zzth(zzrw zzrw2) {
        super(zzrw2);
    }

    private static int zzcj(String string) {
        if ("verbose".equals(string = string.toLowerCase())) {
            return 0;
        }
        if ("info".equals(string)) {
            return 1;
        }
        if ("warning".equals(string)) {
            return 2;
        }
        if ("error".equals(string)) {
            return 3;
        }
        return -1;
    }

    void zza(zzss zzss2) {
        int n;
        String string;
        this.zzbO("Loading global XML config values");
        if (zzss2.zzpg()) {
            this.zzabK = string = zzss2.zzmx();
            this.zzb("XML config - app name", string);
        }
        if (zzss2.zzph()) {
            this.zzabL = string = zzss2.zzmy();
            this.zzb("XML config - app version", string);
        }
        if (zzss2.zzpi() && (n = zzth.zzcj(zzss2.zzpj())) >= 0) {
            this.zzaeb = n;
            this.zza("XML config - log level", n);
        }
        if (zzss2.zzpk()) {
            this.zzafd = n = zzss2.zzpl();
            this.zzafU = true;
            this.zzb("XML config - dispatch period (sec)", n);
        }
        if (zzss2.zzpm()) {
            boolean bl;
            this.zzaaI = bl = zzss2.zzpn();
            this.zzafV = true;
            this.zzb("XML config - dry run", bl);
        }
    }

    @Override
    protected void zzmr() {
        this.zzqc();
    }

    public String zzmx() {
        this.zznA();
        return this.zzabK;
    }

    public String zzmy() {
        this.zznA();
        return this.zzabL;
    }

    public boolean zzpi() {
        this.zznA();
        return false;
    }

    public boolean zzpk() {
        this.zznA();
        return this.zzafU;
    }

    public boolean zzpm() {
        this.zznA();
        return this.zzafV;
    }

    public boolean zzpn() {
        this.zznA();
        return this.zzaaI;
    }

    public int zzqb() {
        this.zznA();
        return this.zzafd;
    }

    protected void zzqc() {
        int n;
        Object object = this.getContext();
        try {
            object = object.getPackageManager().getApplicationInfo(object.getPackageName(), 129);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            this.zzd("PackageManager doesn't know about the app package", (Object)nameNotFoundException);
            object = null;
        }
        if (object == null) {
            this.zzbR("Couldn't get ApplicationInfo to load global config");
            return;
        }
        object = object.metaData;
        if (object != null && (n = object.getInt("com.google.android.gms.analytics.globalConfigResource")) > 0 && (object = (zzss)new zzsr(this.zznp()).zzaG(n)) != null) {
            this.zza((zzss)object);
        }
    }
}
