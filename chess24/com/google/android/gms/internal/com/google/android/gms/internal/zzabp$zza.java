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
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzabh;
import com.google.android.gms.internal.zzabp;

private final class zzabp.zza
extends Handler {
    public zzabp.zza(Looper looper) {
        super(looper);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void handleMessage(Message var1_1) {
        switch (var1_1.what) {
            default: {
                var2_2 = var1_1.what;
                var1_1 = new StringBuilder(70);
                var1_1.append("TransformationResultHandler received unknown message type: ");
                var1_1.append(var2_2);
                Log.e((String)"TransformedResultImpl", (String)var1_1.toString());
                return;
            }
            case 1: {
                var3_3 = (RuntimeException)var1_1.obj;
                var1_1 = String.valueOf(var3_3.getMessage());
                var1_1 = var1_1.length() != 0 ? "Runtime exception on the transformation worker thread: ".concat((String)var1_1) : new String("Runtime exception on the transformation worker thread: ");
                Log.e((String)"TransformedResultImpl", (String)var1_1);
                throw var3_3;
            }
            case 0: 
        }
        var3_4 = (PendingResult)var1_1.obj;
        var1_1 = zzabp.zzf(zzabp.this);
        // MONITORENTER : var1_1
        if (var3_4 != null) ** GOTO lbl23
        zzabp.zza(zzabp.zzg(zzabp.this), new Status(13, "Transform returned null"));
        return;
lbl23: // 1 sources:
        if (var3_4 instanceof zzabh) {
            zzabp.zza(zzabp.zzg(zzabp.this), ((zzabh)var3_4).getStatus());
            return;
        }
        zzabp.zzg(zzabp.this).zza(var3_4);
        // MONITOREXIT : var1_1
        return;
    }
}
