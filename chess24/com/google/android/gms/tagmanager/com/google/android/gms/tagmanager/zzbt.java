/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Bundle
 */
package com.google.android.gms.tagmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import com.google.android.gms.tagmanager.zzdb;

class zzbt
extends BroadcastReceiver {
    static final String zzafu = "com.google.android.gms.tagmanager.zzbt";
    private final zzdb zzbEN;

    zzbt(zzdb zzdb2) {
        this.zzbEN = zzdb2;
    }

    public static void zzbK(Context context) {
        Intent intent = new Intent("com.google.analytics.RADIO_POWERED");
        intent.addCategory(context.getPackageName());
        intent.putExtra(zzafu, true);
        context.sendBroadcast(intent);
    }

    public void onReceive(Context object, Intent intent) {
        object = intent.getAction();
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(object)) {
            Bundle bundle = intent.getExtras();
            object = Boolean.FALSE;
            if (bundle != null) {
                object = intent.getExtras().getBoolean("noConnectivity");
            }
            this.zzbEN.zzaN(object.booleanValue() ^ true);
            return;
        }
        if ("com.google.analytics.RADIO_POWERED".equals(object) && !intent.hasExtra(zzafu)) {
            this.zzbEN.zznn();
        }
    }

    public void zzbJ(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver((BroadcastReceiver)this, intentFilter);
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.google.analytics.RADIO_POWERED");
        intentFilter.addCategory(context.getPackageName());
        context.registerReceiver((BroadcastReceiver)this, intentFilter);
    }
}
