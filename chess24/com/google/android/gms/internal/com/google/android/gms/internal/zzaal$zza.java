/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.internal.zzaal;

final class zzaal.zza
extends Handler {
    zzaal.zza(Looper looper) {
        super(looper);
    }

    public void handleMessage(Message object) {
        switch (object.what) {
            default: {
                int n = object.what;
                object = new StringBuilder(31);
                object.append("Unknown message id: ");
                object.append(n);
                Log.w((String)"GoogleApiClientImpl", (String)object.toString());
                return;
            }
            case 2: {
                zzaal.this.resume();
                return;
            }
            case 1: 
        }
        zzaal.this.zzvJ();
    }
}
