/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 */
package com.crashlytics.android.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.concurrent.atomic.AtomicBoolean;

class DevicePowerStateListener {
    private static final IntentFilter FILTER_BATTERY_CHANGED = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    private static final IntentFilter FILTER_POWER_CONNECTED = new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED");
    private static final IntentFilter FILTER_POWER_DISCONNECTED = new IntentFilter("android.intent.action.ACTION_POWER_DISCONNECTED");
    private final Context context;
    private boolean isPowerConnected;
    private final BroadcastReceiver powerConnectedReceiver;
    private final BroadcastReceiver powerDisconnectedReceiver;
    private final AtomicBoolean receiversRegistered;

    public DevicePowerStateListener(Context context) {
        this.context = context;
        this.powerConnectedReceiver = new BroadcastReceiver(){

            public void onReceive(Context context, Intent intent) {
                DevicePowerStateListener.this.isPowerConnected = true;
            }
        };
        this.powerDisconnectedReceiver = new BroadcastReceiver(){

            public void onReceive(Context context, Intent intent) {
                DevicePowerStateListener.this.isPowerConnected = false;
            }
        };
        this.receiversRegistered = new AtomicBoolean(false);
    }

    public void dispose() {
        if (!this.receiversRegistered.getAndSet(false)) {
            return;
        }
        this.context.unregisterReceiver(this.powerConnectedReceiver);
        this.context.unregisterReceiver(this.powerDisconnectedReceiver);
    }

    public void initialize() {
        AtomicBoolean atomicBoolean = this.receiversRegistered;
        boolean bl = true;
        if (atomicBoolean.getAndSet(true)) {
            return;
        }
        atomicBoolean = this.context.registerReceiver(null, FILTER_BATTERY_CHANGED);
        int n = -1;
        if (atomicBoolean != null) {
            n = atomicBoolean.getIntExtra("status", -1);
        }
        boolean bl2 = bl;
        if (n != 2) {
            bl2 = n == 5 ? bl : false;
        }
        this.isPowerConnected = bl2;
        this.context.registerReceiver(this.powerConnectedReceiver, FILTER_POWER_CONNECTED);
        this.context.registerReceiver(this.powerDisconnectedReceiver, FILTER_POWER_DISCONNECTED);
    }

    public boolean isPowerConnected() {
        return this.isPowerConnected;
    }

}
