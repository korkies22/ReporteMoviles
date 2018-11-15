/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.app.PendingIntent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzf;
import java.util.concurrent.atomic.AtomicInteger;

final class zzf.zzd
extends Handler {
    public zzf.zzd(Looper looper) {
        super(looper);
    }

    private void zza(Message message) {
        ((zzf.zze)message.obj).unregister();
    }

    private boolean zzb(Message message) {
        boolean bl;
        int n = message.what;
        boolean bl2 = bl = true;
        if (n != 2) {
            bl2 = bl;
            if (message.what != 1) {
                if (message.what == 5) {
                    return true;
                }
                bl2 = false;
            }
        }
        return bl2;
    }

    public void handleMessage(Message object) {
        if (zzf.this.zzaDS.get() != object.arg1) {
            if (this.zzb((Message)object)) {
                this.zza((Message)object);
            }
            return;
        }
        if (!(object.what != 1 && object.what != 5 || zzf.this.isConnecting())) {
            this.zza((Message)object);
            return;
        }
        int n = object.what;
        PendingIntent pendingIntent = null;
        if (n == 3) {
            if (object.obj instanceof PendingIntent) {
                pendingIntent = (PendingIntent)object.obj;
            }
            object = new ConnectionResult(object.arg2, pendingIntent);
            zzf.this.zzaDJ.zzg((ConnectionResult)object);
            zzf.this.onConnectionFailed((ConnectionResult)object);
            return;
        }
        if (object.what == 4) {
            zzf.this.zza(4, (T)null);
            if (zzf.this.zzaDO != null) {
                zzf.this.zzaDO.onConnectionSuspended(object.arg2);
            }
            zzf.this.onConnectionSuspended(object.arg2);
            zzf.this.zza(4, 1, (T)null);
            return;
        }
        if (object.what == 2 && !zzf.this.isConnected()) {
            this.zza((Message)object);
            return;
        }
        if (this.zzb((Message)object)) {
            ((zzf.zze)object.obj).zzxa();
            return;
        }
        n = object.what;
        object = new StringBuilder(45);
        object.append("Don't know how to handle message: ");
        object.append(n);
        Log.wtf((String)"GmsClient", (String)object.toString(), (Throwable)new Exception());
    }
}
