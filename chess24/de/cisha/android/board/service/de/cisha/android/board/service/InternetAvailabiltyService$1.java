/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 */
package de.cisha.android.board.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class InternetAvailabiltyService
extends BroadcastReceiver {
    InternetAvailabiltyService() {
    }

    public void onReceive(Context context, Intent intent) {
        InternetAvailabiltyService.this.networkChanged(context);
    }
}
