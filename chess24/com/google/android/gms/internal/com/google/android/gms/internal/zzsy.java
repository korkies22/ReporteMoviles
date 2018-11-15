/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzrs;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzsx;

class zzsy
extends BroadcastReceiver {
    static final String zzafu = "com.google.android.gms.internal.zzsy";
    private final zzrw zzacN;
    private boolean zzafv;
    private boolean zzafw;

    zzsy(zzrw zzrw2) {
        zzac.zzw(zzrw2);
        this.zzacN = zzrw2;
    }

    private Context getContext() {
        return this.zzacN.getContext();
    }

    private zzrs zzlZ() {
        return this.zzacN.zzlZ();
    }

    private zzsx zznr() {
        return this.zzacN.zznr();
    }

    private void zzpy() {
        this.zznr();
        this.zzlZ();
    }

    public boolean isConnected() {
        if (!this.zzafv) {
            this.zzacN.zznr().zzbR("Connectivity unknown. Receiver not registered");
        }
        return this.zzafw;
    }

    public boolean isRegistered() {
        return this.zzafv;
    }

    public void onReceive(Context object, Intent intent) {
        this.zzpy();
        object = intent.getAction();
        this.zzacN.zznr().zza("NetworkBroadcastReceiver received action", object);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(object)) {
            boolean bl = this.zzpA();
            if (this.zzafw != bl) {
                this.zzafw = bl;
                this.zzlZ().zzV(bl);
            }
            return;
        }
        if ("com.google.analytics.RADIO_POWERED".equals(object)) {
            if (!intent.hasExtra(zzafu)) {
                this.zzlZ().zznn();
            }
            return;
        }
        this.zzacN.zznr().zzd("NetworkBroadcastReceiver received unknown action", object);
    }

    public void unregister() {
        if (!this.isRegistered()) {
            return;
        }
        this.zzacN.zznr().zzbO("Unregistering connectivity change receiver");
        this.zzafv = false;
        this.zzafw = false;
        Context context = this.getContext();
        try {
            context.unregisterReceiver((BroadcastReceiver)this);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            this.zznr().zze("Failed to unregister the network broadcast receiver", illegalArgumentException);
            return;
        }
    }

    protected boolean zzpA() {
        boolean bl;
        block3 : {
            ConnectivityManager connectivityManager = (ConnectivityManager)this.getContext().getSystemService("connectivity");
            boolean bl2 = false;
            try {
                connectivityManager = connectivityManager.getActiveNetworkInfo();
                bl = bl2;
                if (connectivityManager == null) break block3;
            }
            catch (SecurityException securityException) {
                return false;
            }
            boolean bl3 = connectivityManager.isConnected();
            bl = bl2;
            if (!bl3) break block3;
            bl = true;
        }
        return bl;
    }

    public void zzpx() {
        this.zzpy();
        if (this.zzafv) {
            return;
        }
        Context context = this.getContext();
        context.registerReceiver((BroadcastReceiver)this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        IntentFilter intentFilter = new IntentFilter("com.google.analytics.RADIO_POWERED");
        intentFilter.addCategory(context.getPackageName());
        context.registerReceiver((BroadcastReceiver)this, intentFilter);
        this.zzafw = this.zzpA();
        this.zzacN.zznr().zza("Registering connectivity change receiver. Network connected", this.zzafw);
        this.zzafv = true;
    }

    public void zzpz() {
        if (Build.VERSION.SDK_INT <= 10) {
            return;
        }
        Context context = this.getContext();
        Intent intent = new Intent("com.google.analytics.RADIO_POWERED");
        intent.addCategory(context.getPackageName());
        intent.putExtra(zzafu, true);
        context.sendOrderedBroadcast(intent, null);
    }
}
