/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Message
 */
package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Message;
import com.google.android.gms.tagmanager.zzdc;

class zzdc.zzb
implements Handler.Callback {
    zzdc.zzb() {
    }

    public boolean handleMessage(Message message) {
        if (1 == message.what && zzbFZ.equals(message.obj)) {
            zzb.this.zzbGm.dispatch();
            if (!zzb.this.zzbGm.isPowerSaveMode()) {
                zzb.this.zzx(zzb.this.zzbGm.zzbGd);
            }
        }
        return true;
    }
}
