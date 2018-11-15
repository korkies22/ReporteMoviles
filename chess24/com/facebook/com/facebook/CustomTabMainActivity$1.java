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

class CustomTabMainActivity
extends BroadcastReceiver {
    CustomTabMainActivity() {
    }

    public void onReceive(Context context, Intent intent) {
        context = new Intent((Context)CustomTabMainActivity.this, com.facebook.CustomTabMainActivity.class);
        context.setAction(com.facebook.CustomTabMainActivity.REFRESH_ACTION);
        context.putExtra(com.facebook.CustomTabMainActivity.EXTRA_URL, intent.getStringExtra(com.facebook.CustomTabMainActivity.EXTRA_URL));
        context.addFlags(603979776);
        CustomTabMainActivity.this.startActivity((Intent)context);
    }
}
