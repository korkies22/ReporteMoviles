/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 */
package com.facebook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class CustomTabActivity
extends BroadcastReceiver {
    CustomTabActivity() {
    }

    public void onReceive(Context context, Intent intent) {
        CustomTabActivity.this.finish();
    }
}
