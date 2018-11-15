/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 */
package android.support.v7.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatDelegateImplV14;

class AppCompatDelegateImplV14.AutoNightModeManager
extends BroadcastReceiver {
    AppCompatDelegateImplV14.AutoNightModeManager() {
    }

    public void onReceive(Context context, Intent intent) {
        AutoNightModeManager.this.dispatchTimeChanged();
    }
}
