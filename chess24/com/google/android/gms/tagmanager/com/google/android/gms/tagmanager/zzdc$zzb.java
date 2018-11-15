/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.tagmanager.zzdc;

private class zzdc.zzb
implements zzdc.zza {
    private Handler handler;

    private zzdc.zzb() {
        this.handler = new Handler(zzdc.this.zzbGa.getMainLooper(), new Handler.Callback(){

            public boolean handleMessage(Message message) {
                if (1 == message.what && zzbFZ.equals(message.obj)) {
                    zzdc.this.dispatch();
                    if (!zzdc.this.isPowerSaveMode()) {
                        zzb.this.zzx(zzdc.this.zzbGd);
                    }
                }
                return true;
            }
        });
    }

    private Message obtainMessage() {
        return this.handler.obtainMessage(1, zzbFZ);
    }

    @Override
    public void cancel() {
        this.handler.removeMessages(1, zzbFZ);
    }

    @Override
    public void zzPY() {
        this.handler.removeMessages(1, zzbFZ);
        this.handler.sendMessage(this.obtainMessage());
    }

    @Override
    public void zzx(long l) {
        this.handler.removeMessages(1, zzbFZ);
        this.handler.sendMessageDelayed(this.obtainMessage(), l);
    }

}
