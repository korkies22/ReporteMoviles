/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 */
package com.crashlytics.android.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class DevicePowerStateListener
extends BroadcastReceiver {
    DevicePowerStateListener() {
    }

    public void onReceive(Context context, Intent intent) {
        DevicePowerStateListener.this.isPowerConnected = true;
    }
}
