/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ServiceConnection
 *  android.os.IBinder
 */
package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.common.internal.zzo;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class zzo.zzb.zza
implements ServiceConnection {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        HashMap hashMap = zzb.this.zzaEO.zzaEF;
        synchronized (hashMap) {
            zzb.this.zzaEa = iBinder;
            zzb.this.zzaEJ = componentName;
            Iterator iterator = zzb.this.zzaEL.iterator();
            do {
                if (!iterator.hasNext()) {
                    zzb.this.mState = 1;
                    return;
                }
                ((ServiceConnection)iterator.next()).onServiceConnected(componentName, iBinder);
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onServiceDisconnected(ComponentName componentName) {
        HashMap hashMap = zzb.this.zzaEO.zzaEF;
        synchronized (hashMap) {
            zzb.this.zzaEa = null;
            zzb.this.zzaEJ = componentName;
            Iterator iterator = zzb.this.zzaEL.iterator();
            do {
                if (!iterator.hasNext()) {
                    zzb.this.mState = 2;
                    return;
                }
                ((ServiceConnection)iterator.next()).onServiceDisconnected(componentName);
            } while (true);
        }
    }
}
