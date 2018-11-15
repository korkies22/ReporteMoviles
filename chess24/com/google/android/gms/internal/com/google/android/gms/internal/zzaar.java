/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 */
package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public final class zzaar
extends BroadcastReceiver {
    protected Context mContext;
    private final zza zzaBm;

    public zzaar(zza zza2) {
        this.zzaBm = zza2;
    }

    public void onReceive(Context object, Intent intent) {
        object = intent.getData();
        object = object != null ? object.getSchemeSpecificPart() : null;
        if ("com.google.android.gms".equals(object)) {
            this.zzaBm.zzvb();
            this.unregister();
        }
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void unregister() {
        synchronized (this) {
            if (this.mContext != null) {
                this.mContext.unregisterReceiver((BroadcastReceiver)this);
            }
            this.mContext = null;
            return;
        }
    }

    public static abstract class zza {
        public abstract void zzvb();
    }

}
