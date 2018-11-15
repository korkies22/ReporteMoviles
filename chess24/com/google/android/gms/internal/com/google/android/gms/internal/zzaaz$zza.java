/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzaaz;

private final class zzaaz.zza
extends Handler {
    public zzaaz.zza(Looper looper) {
        super(looper);
    }

    public void handleMessage(Message message) {
        int n = message.what;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        zzac.zzas(bl);
        zzaaz.this.zzb((zzaaz.zzc)message.obj);
    }
}
