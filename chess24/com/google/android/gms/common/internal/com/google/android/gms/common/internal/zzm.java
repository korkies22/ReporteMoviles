/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzac;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzm
implements Handler.Callback {
    private final Handler mHandler;
    private volatile boolean zzaEA = false;
    private final AtomicInteger zzaEB = new AtomicInteger(0);
    private boolean zzaEC = false;
    private final zza zzaEw;
    private final ArrayList<GoogleApiClient.ConnectionCallbacks> zzaEx = new ArrayList();
    final ArrayList<GoogleApiClient.ConnectionCallbacks> zzaEy = new ArrayList();
    private final ArrayList<GoogleApiClient.OnConnectionFailedListener> zzaEz = new ArrayList();
    private final Object zzrN = new Object();

    public zzm(Looper looper, zza zza2) {
        this.zzaEw = zza2;
        this.mHandler = new Handler(looper, (Handler.Callback)this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean handleMessage(Message object) {
        if (object.what != 1) {
            int n = object.what;
            object = new StringBuilder(45);
            object.append("Don't know how to handle message: ");
            object.append(n);
            Log.wtf((String)"GmsClientEvents", (String)object.toString(), (Throwable)new Exception());
            return false;
        }
        GoogleApiClient.ConnectionCallbacks connectionCallbacks = (GoogleApiClient.ConnectionCallbacks)object.obj;
        object = this.zzrN;
        synchronized (object) {
            if (this.zzaEA && this.zzaEw.isConnected() && this.zzaEx.contains(connectionCallbacks)) {
                connectionCallbacks.onConnected(this.zzaEw.zzud());
            }
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isConnectionCallbacksRegistered(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        zzac.zzw(connectionCallbacks);
        Object object = this.zzrN;
        synchronized (object) {
            return this.zzaEx.contains(connectionCallbacks);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isConnectionFailedListenerRegistered(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        zzac.zzw(onConnectionFailedListener);
        Object object = this.zzrN;
        synchronized (object) {
            return this.zzaEz.contains(onConnectionFailedListener);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        zzac.zzw(connectionCallbacks);
        Object object = this.zzrN;
        // MONITORENTER : object
        if (this.zzaEx.contains(connectionCallbacks)) {
            String string = String.valueOf(connectionCallbacks);
            StringBuilder stringBuilder = new StringBuilder(62 + String.valueOf(string).length());
            stringBuilder.append("registerConnectionCallbacks(): listener ");
            stringBuilder.append(string);
            stringBuilder.append(" is already registered");
            Log.w((String)"GmsClientEvents", (String)stringBuilder.toString());
        } else {
            this.zzaEx.add(connectionCallbacks);
        }
        // MONITOREXIT : object
        if (!this.zzaEw.isConnected()) return;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, (Object)connectionCallbacks));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener object) {
        zzac.zzw(object);
        Object object2 = this.zzrN;
        synchronized (object2) {
            if (this.zzaEz.contains(object)) {
                object = String.valueOf(object);
                StringBuilder stringBuilder = new StringBuilder(67 + String.valueOf(object).length());
                stringBuilder.append("registerConnectionFailedListener(): listener ");
                stringBuilder.append((String)object);
                stringBuilder.append(" is already registered");
                Log.w((String)"GmsClientEvents", (String)stringBuilder.toString());
            } else {
                this.zzaEz.add((GoogleApiClient.OnConnectionFailedListener)object);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks object) {
        zzac.zzw(object);
        Object object2 = this.zzrN;
        synchronized (object2) {
            if (!this.zzaEx.remove(object)) {
                object = String.valueOf(object);
                StringBuilder stringBuilder = new StringBuilder(52 + String.valueOf(object).length());
                stringBuilder.append("unregisterConnectionCallbacks(): listener ");
                stringBuilder.append((String)object);
                stringBuilder.append(" not found");
                Log.w((String)"GmsClientEvents", (String)stringBuilder.toString());
            } else if (this.zzaEC) {
                this.zzaEy.add((GoogleApiClient.ConnectionCallbacks)object);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener object) {
        zzac.zzw(object);
        Object object2 = this.zzrN;
        synchronized (object2) {
            if (!this.zzaEz.remove(object)) {
                object = String.valueOf(object);
                StringBuilder stringBuilder = new StringBuilder(57 + String.valueOf(object).length());
                stringBuilder.append("unregisterConnectionFailedListener(): listener ");
                stringBuilder.append((String)object);
                stringBuilder.append(" not found");
                Log.w((String)"GmsClientEvents", (String)stringBuilder.toString());
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zzcP(int n) {
        boolean bl = Looper.myLooper() == this.mHandler.getLooper();
        zzac.zza(bl, (Object)"onUnintentionalDisconnection must only be called on the Handler thread");
        this.mHandler.removeMessages(1);
        Object object = this.zzrN;
        synchronized (object) {
            this.zzaEC = true;
            Object object2 = new ArrayList<GoogleApiClient.ConnectionCallbacks>(this.zzaEx);
            int n2 = this.zzaEB.get();
            object2 = object2.iterator();
            while (object2.hasNext()) {
                GoogleApiClient.ConnectionCallbacks connectionCallbacks = (GoogleApiClient.ConnectionCallbacks)object2.next();
                if (!this.zzaEA || this.zzaEB.get() != n2) break;
                if (!this.zzaEx.contains(connectionCallbacks)) continue;
                connectionCallbacks.onConnectionSuspended(n);
            }
            this.zzaEy.clear();
            this.zzaEC = false;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zzo(ConnectionResult connectionResult) {
        boolean bl = Looper.myLooper() == this.mHandler.getLooper();
        zzac.zza(bl, (Object)"onConnectionFailure must only be called on the Handler thread");
        this.mHandler.removeMessages(1);
        Object object = this.zzrN;
        synchronized (object) {
            Object object2 = new ArrayList<GoogleApiClient.OnConnectionFailedListener>(this.zzaEz);
            int n = this.zzaEB.get();
            object2 = object2.iterator();
            do {
                if (!object2.hasNext()) {
                    return;
                }
                GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = (GoogleApiClient.OnConnectionFailedListener)object2.next();
                if (!this.zzaEA || this.zzaEB.get() != n) break;
                if (!this.zzaEz.contains(onConnectionFailedListener)) continue;
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            } while (true);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zzq(Bundle bundle) {
        Object object = Looper.myLooper();
        Object object2 = this.mHandler.getLooper();
        boolean bl = true;
        boolean bl2 = object == object2;
        zzac.zza(bl2, (Object)"onConnectionSuccess must only be called on the Handler thread");
        object = this.zzrN;
        synchronized (object) {
            zzac.zzar(this.zzaEC ^ true);
            this.mHandler.removeMessages(1);
            this.zzaEC = true;
            bl2 = this.zzaEy.size() == 0 ? bl : false;
            zzac.zzar(bl2);
            object2 = new ArrayList<GoogleApiClient.ConnectionCallbacks>(this.zzaEx);
            int n = this.zzaEB.get();
            object2 = object2.iterator();
            while (object2.hasNext()) {
                GoogleApiClient.ConnectionCallbacks connectionCallbacks = (GoogleApiClient.ConnectionCallbacks)object2.next();
                if (!this.zzaEA || !this.zzaEw.isConnected() || this.zzaEB.get() != n) break;
                if (this.zzaEy.contains(connectionCallbacks)) continue;
                connectionCallbacks.onConnected(bundle);
            }
            this.zzaEy.clear();
            this.zzaEC = false;
            return;
        }
    }

    public void zzxq() {
        this.zzaEA = false;
        this.zzaEB.incrementAndGet();
    }

    public void zzxr() {
        this.zzaEA = true;
    }

    public static interface zza {
        public boolean isConnected();

        public Bundle zzud();
    }

}
