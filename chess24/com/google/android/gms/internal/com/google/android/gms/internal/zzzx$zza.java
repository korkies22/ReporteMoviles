/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 *  android.util.Pair
 */
package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzzx;

public static class zzzx.zza<R extends Result>
extends Handler {
    public zzzx.zza() {
        this(Looper.getMainLooper());
    }

    public zzzx.zza(Looper looper) {
        super(looper);
    }

    public void handleMessage(Message object) {
        switch (object.what) {
            default: {
                int n = object.what;
                object = new StringBuilder(45);
                object.append("Don't know how to handle message: ");
                object.append(n);
                Log.wtf((String)"BasePendingResult", (String)object.toString(), (Throwable)new Exception());
                return;
            }
            case 2: {
                ((zzzx)object.obj).zzB(Status.zzayk);
                return;
            }
            case 1: 
        }
        object = (Pair)object.obj;
        this.zzb((ResultCallback)object.first, (Result)object.second);
    }

    public void zza(ResultCallback<? super R> resultCallback, R r) {
        this.sendMessage(this.obtainMessage(1, (Object)new Pair(resultCallback, r)));
    }

    public void zza(zzzx<R> zzzx2, long l) {
        this.sendMessageDelayed(this.obtainMessage(2, zzzx2), l);
    }

    protected void zzb(ResultCallback<? super R> resultCallback, R r) {
        try {
            resultCallback.onResult(r);
            return;
        }
        catch (RuntimeException runtimeException) {
            zzzx.zzd(r);
            throw runtimeException;
        }
    }

    public void zzvh() {
        this.removeMessages(2);
    }
}
