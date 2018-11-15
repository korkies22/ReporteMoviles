/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.GoogleApiAvailability;

@SuppressLint(value={"HandlerLeak"})
private class GoogleApiAvailability.zza
extends Handler {
    private final Context zzvZ;

    public GoogleApiAvailability.zza(Context context) {
        GoogleApiAvailability.this = Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper();
        super((Looper)GoogleApiAvailability.this);
        this.zzvZ = context.getApplicationContext();
    }

    public void handleMessage(Message object) {
        if (object.what != 1) {
            int n = object.what;
            object = new StringBuilder(50);
            object.append("Don't know how to handle this message: ");
            object.append(n);
            Log.w((String)"GoogleApiAvailability", (String)object.toString());
            return;
        }
        int n = GoogleApiAvailability.this.isGooglePlayServicesAvailable(this.zzvZ);
        if (GoogleApiAvailability.this.isUserResolvableError(n)) {
            GoogleApiAvailability.this.showErrorNotification(this.zzvZ, n);
        }
    }
}
