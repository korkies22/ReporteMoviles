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
import com.google.android.gms.internal.zzaan;

final class zzaan.zzb
extends Handler {
    zzaan.zzb(Looper looper) {
        super(looper);
    }

    public void handleMessage(Message object) {
        switch (object.what) {
            default: {
                int n = object.what;
                object = new StringBuilder(31);
                object.append("Unknown message id: ");
                object.append(n);
                Log.w((String)"GACStateManager", (String)object.toString());
                return;
            }
            case 2: {
                throw (RuntimeException)object.obj;
            }
            case 1: 
        }
        ((zzaan.zza)object.obj).zzc(zzaan.this);
    }
}
