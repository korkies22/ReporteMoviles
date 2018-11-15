/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzo;

private class zzo.zzb
extends Handler {
    private final ContainerHolder.ContainerAvailableListener zzbDf;

    public zzo.zzb(ContainerHolder.ContainerAvailableListener containerAvailableListener, Looper looper) {
        super(looper);
        this.zzbDf = containerAvailableListener;
    }

    public void handleMessage(Message message) {
        if (message.what != 1) {
            zzbo.e("Don't know how to handle this message.");
            return;
        }
        this.zzhb((String)message.obj);
    }

    public void zzha(String string) {
        this.sendMessage(this.obtainMessage(1, (Object)string));
    }

    protected void zzhb(String string) {
        this.zzbDf.onContainerAvailable(zzo.this, string);
    }
}
