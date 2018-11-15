/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.DeadObjectException
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zzc;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzal;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzaae;
import com.google.android.gms.internal.zzaap;
import com.google.android.gms.internal.zzaaz;
import com.google.android.gms.internal.zzabf;
import com.google.android.gms.internal.zzabj;
import com.google.android.gms.internal.zzzq;
import com.google.android.gms.internal.zzzs;
import com.google.android.gms.internal.zzzu;
import com.google.android.gms.internal.zzzz;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class zzaap.zza<O extends Api.ApiOptions>
implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener,
zzzz {
    private final Queue<zzzq> zzaAY = new LinkedList<zzzq>();
    private final Api.zzb zzaAZ;
    private boolean zzaAm;
    private final zzaad zzaBa;
    private final Set<zzzu> zzaBb = new HashSet<zzzu>();
    private final Map<zzaaz.zzb<?>, zzabf> zzaBc = new HashMap();
    private final int zzaBd;
    private final zzabj zzaBe;
    private ConnectionResult zzaBf = null;
    private final zzzs<O> zzaxH;
    private final Api.zze zzazq;

    /*
     * Enabled aggressive block sorting
     */
    @WorkerThread
    public zzaap.zza(zzc<O> zzc2) {
        void var3_4;
        this.zzazq = zzc2.buildApiClient(zzaap.this.mHandler.getLooper(), this);
        if (this.zzazq instanceof zzal) {
            Api.zzg zzg2 = ((zzal)this.zzazq).zzxG();
        } else {
            Api.zze zze2 = this.zzazq;
        }
        this.zzaAZ = var3_4;
        this.zzaxH = zzc2.getApiKey();
        this.zzaBa = new zzaad();
        this.zzaBd = zzc2.getInstanceId();
        if (!this.zzazq.zzqD()) {
            this.zzaBe = null;
            return;
        }
        this.zzaBe = zzc2.createSignInCoordinator(zzaap.this.mContext, zzaap.this.mHandler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @WorkerThread
    private void zzb(zzzq zzzq2) {
        zzzq2.zza(this.zzaBa, this.zzqD());
        try {
            zzzq2.zza(this);
            return;
        }
        catch (DeadObjectException deadObjectException) {}
        this.onConnectionSuspended(1);
        this.zzazq.disconnect();
    }

    @WorkerThread
    private void zzj(ConnectionResult connectionResult) {
        Iterator<zzzu> iterator = this.zzaBb.iterator();
        while (iterator.hasNext()) {
            iterator.next().zza(this.zzaxH, connectionResult);
        }
        this.zzaBb.clear();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @WorkerThread
    private void zzvZ() {
        this.zzwd();
        this.zzj(ConnectionResult.zzawX);
        this.zzwf();
        Iterator<zzabf> iterator = this.zzaBc.values().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            try {
                new TaskCompletionSource();
                continue;
            }
            catch (DeadObjectException deadObjectException) {}
            this.onConnectionSuspended(1);
            this.zzazq.disconnect();
            break;
            catch (RemoteException remoteException) {
            }
        }
        this.zzwb();
        this.zzwg();
    }

    @WorkerThread
    private void zzwa() {
        this.zzwd();
        this.zzaAm = true;
        this.zzaBa.zzvw();
        zzaap.this.mHandler.sendMessageDelayed(Message.obtain((Handler)zzaap.this.mHandler, (int)7, this.zzaxH), zzaap.this.zzaAo);
        zzaap.this.mHandler.sendMessageDelayed(Message.obtain((Handler)zzaap.this.mHandler, (int)9, this.zzaxH), zzaap.this.zzaAn);
        zzaap.this.zzaAS = -1;
    }

    @WorkerThread
    private void zzwb() {
        while (this.zzazq.isConnected() && !this.zzaAY.isEmpty()) {
            this.zzb(this.zzaAY.remove());
        }
    }

    @WorkerThread
    private void zzwf() {
        if (this.zzaAm) {
            zzaap.this.mHandler.removeMessages(9, this.zzaxH);
            zzaap.this.mHandler.removeMessages(7, this.zzaxH);
            this.zzaAm = false;
        }
    }

    private void zzwg() {
        zzaap.this.mHandler.removeMessages(10, this.zzaxH);
        zzaap.this.mHandler.sendMessageDelayed(zzaap.this.mHandler.obtainMessage(10, this.zzaxH), zzaap.this.zzaAQ);
    }

    @WorkerThread
    public void connect() {
        zzac.zza(zzaap.this.mHandler);
        if (!this.zzazq.isConnected()) {
            if (this.zzazq.isConnecting()) {
                return;
            }
            if (this.zzazq.zzuI() && zzaap.this.zzaAS != 0) {
                zzaap.this.zzaAS = zzaap.this.zzaxX.isGooglePlayServicesAvailable(zzaap.this.mContext);
                if (zzaap.this.zzaAS != 0) {
                    this.onConnectionFailed(new ConnectionResult(zzaap.this.zzaAS, null));
                    return;
                }
            }
            zzaap.zzb zzb2 = new zzaap.zzb(zzaap.this, this.zzazq, this.zzaxH);
            if (this.zzazq.zzqD()) {
                this.zzaBe.zza(zzb2);
            }
            this.zzazq.zza(zzb2);
        }
    }

    public int getInstanceId() {
        return this.zzaBd;
    }

    boolean isConnected() {
        return this.zzazq.isConnected();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (Looper.myLooper() == zzaap.this.mHandler.getLooper()) {
            this.zzvZ();
            return;
        }
        zzaap.this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                zza.this.zzvZ();
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @WorkerThread
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult object) {
        zzac.zza(zzaap.this.mHandler);
        if (this.zzaBe != null) {
            this.zzaBe.zzwr();
        }
        this.zzwd();
        zzaap.this.zzaAS = -1;
        this.zzj((ConnectionResult)object);
        if (object.getErrorCode() == 4) {
            this.zzC(zzaAP);
            return;
        }
        if (this.zzaAY.isEmpty()) {
            this.zzaBf = object;
            return;
        }
        Object object2 = zztU;
        // MONITORENTER : object2
        if (zzaap.this.zzaAV != null && zzaap.this.zzaAW.contains(this.zzaxH)) {
            zzaap.this.zzaAV.zzb((ConnectionResult)object, this.zzaBd);
            // MONITOREXIT : object2
            return;
        }
        // MONITOREXIT : object2
        if (zzaap.this.zzc((ConnectionResult)object, this.zzaBd)) return;
        if (object.getErrorCode() == 18) {
            this.zzaAm = true;
        }
        if (this.zzaAm) {
            zzaap.this.mHandler.sendMessageDelayed(Message.obtain((Handler)zzaap.this.mHandler, (int)7, this.zzaxH), zzaap.this.zzaAo);
            return;
        }
        object = String.valueOf(this.zzaxH.zzuV());
        object2 = new StringBuilder(38 + String.valueOf(object).length());
        object2.append("API: ");
        object2.append((String)object);
        object2.append(" is not available on this device.");
        this.zzC(new Status(17, object2.toString()));
    }

    @Override
    public void onConnectionSuspended(int n) {
        if (Looper.myLooper() == zzaap.this.mHandler.getLooper()) {
            this.zzwa();
            return;
        }
        zzaap.this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                zza.this.zzwa();
            }
        });
    }

    @WorkerThread
    public void resume() {
        zzac.zza(zzaap.this.mHandler);
        if (this.zzaAm) {
            this.connect();
        }
    }

    @WorkerThread
    public void signOut() {
        zzac.zza(zzaap.this.mHandler);
        this.zzC(zzaap.zzaAO);
        this.zzaBa.zzvv();
        Iterator<zzaaz.zzb<?>> iterator = this.zzaBc.keySet().iterator();
        while (iterator.hasNext()) {
            this.zza(new zzzq.zze(iterator.next(), new TaskCompletionSource<Void>()));
        }
        this.zzazq.disconnect();
    }

    @WorkerThread
    public void zzC(Status status) {
        zzac.zza(zzaap.this.mHandler);
        Iterator iterator = this.zzaAY.iterator();
        while (iterator.hasNext()) {
            ((zzzq)iterator.next()).zzy(status);
        }
        this.zzaAY.clear();
    }

    @Override
    public void zza(final ConnectionResult connectionResult, Api<?> api, int n) {
        if (Looper.myLooper() == zzaap.this.mHandler.getLooper()) {
            this.onConnectionFailed(connectionResult);
            return;
        }
        zzaap.this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                zza.this.onConnectionFailed(connectionResult);
            }
        });
    }

    @WorkerThread
    public void zza(zzzq zzzq2) {
        zzac.zza(zzaap.this.mHandler);
        if (this.zzazq.isConnected()) {
            this.zzb(zzzq2);
            this.zzwg();
            return;
        }
        this.zzaAY.add(zzzq2);
        if (this.zzaBf != null && this.zzaBf.hasResolution()) {
            this.onConnectionFailed(this.zzaBf);
            return;
        }
        this.connect();
    }

    @WorkerThread
    public void zzb(zzzu zzzu2) {
        zzac.zza(zzaap.this.mHandler);
        this.zzaBb.add(zzzu2);
    }

    @WorkerThread
    public void zzi(@NonNull ConnectionResult connectionResult) {
        zzac.zza(zzaap.this.mHandler);
        this.zzazq.disconnect();
        this.onConnectionFailed(connectionResult);
    }

    public boolean zzqD() {
        return this.zzazq.zzqD();
    }

    @WorkerThread
    public void zzvJ() {
        zzac.zza(zzaap.this.mHandler);
        if (this.zzaAm) {
            this.zzwf();
            Status status = zzaap.this.zzaxX.isGooglePlayServicesAvailable(zzaap.this.mContext) == 18 ? new Status(8, "Connection timed out while waiting for Google Play services update to complete.") : new Status(8, "API failed to connect while resuming due to an unknown error.");
            this.zzC(status);
            this.zzazq.disconnect();
        }
    }

    public Api.zze zzvr() {
        return this.zzazq;
    }

    public Map<zzaaz.zzb<?>, zzabf> zzwc() {
        return this.zzaBc;
    }

    @WorkerThread
    public void zzwd() {
        zzac.zza(zzaap.this.mHandler);
        this.zzaBf = null;
    }

    @WorkerThread
    public ConnectionResult zzwe() {
        zzac.zza(zzaap.this.mHandler);
        return this.zzaBf;
    }

    @WorkerThread
    public void zzwh() {
        zzac.zza(zzaap.this.mHandler);
        if (this.zzazq.isConnected() && this.zzaBc.size() == 0) {
            if (this.zzaBa.zzvu()) {
                this.zzwg();
                return;
            }
            this.zzazq.disconnect();
        }
    }

}
