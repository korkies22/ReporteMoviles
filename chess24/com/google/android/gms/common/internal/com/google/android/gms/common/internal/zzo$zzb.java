/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.IBinder
 */
package com.google.android.gms.common.internal;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.common.internal.zzo;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

private final class zzo.zzb {
    private int mState;
    private ComponentName zzaEJ;
    private final zza zzaEK;
    private final Set<ServiceConnection> zzaEL;
    private boolean zzaEM;
    private final zzo.zza zzaEN;
    private IBinder zzaEa;

    public zzo.zzb(zzo.zza zza2) {
        this.zzaEN = zza2;
        this.zzaEK = new zza();
        this.zzaEL = new HashSet<ServiceConnection>();
        this.mState = 2;
    }

    public IBinder getBinder() {
        return this.zzaEa;
    }

    public ComponentName getComponentName() {
        return this.zzaEJ;
    }

    public int getState() {
        return this.mState;
    }

    public boolean isBound() {
        return this.zzaEM;
    }

    public void zza(ServiceConnection serviceConnection, String string) {
        zzo.this.zzaEG.zza(zzo.this.zzvZ, serviceConnection, string, this.zzaEN.zzxs());
        this.zzaEL.add(serviceConnection);
    }

    public boolean zza(ServiceConnection serviceConnection) {
        return this.zzaEL.contains((Object)serviceConnection);
    }

    public void zzb(ServiceConnection serviceConnection, String string) {
        zzo.this.zzaEG.zzb(zzo.this.zzvZ, serviceConnection);
        this.zzaEL.remove((Object)serviceConnection);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @TargetApi(value=14)
    public void zzdr(String string) {
        this.mState = 3;
        this.zzaEM = zzo.this.zzaEG.zza(zzo.this.zzvZ, string, this.zzaEN.zzxs(), this.zzaEK, 129);
        if (this.zzaEM) return;
        this.mState = 2;
        try {
            zzo.this.zzaEG.zza(zzo.this.zzvZ, this.zzaEK);
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
    }

    public void zzds(String string) {
        zzo.this.zzaEG.zza(zzo.this.zzvZ, this.zzaEK);
        this.zzaEM = false;
        this.mState = 2;
    }

    public boolean zzxt() {
        return this.zzaEL.isEmpty();
    }

    public class zza
    implements ServiceConnection {
        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            HashMap hashMap = zzo.this.zzaEF;
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
            HashMap hashMap = zzo.this.zzaEF;
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

}
