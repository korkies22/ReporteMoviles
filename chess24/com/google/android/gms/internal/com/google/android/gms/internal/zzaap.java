/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.DeadObjectException
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zzc;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzal;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzaae;
import com.google.android.gms.internal.zzaaz;
import com.google.android.gms.internal.zzabd;
import com.google.android.gms.internal.zzabe;
import com.google.android.gms.internal.zzabf;
import com.google.android.gms.internal.zzabj;
import com.google.android.gms.internal.zzabk;
import com.google.android.gms.internal.zzabn;
import com.google.android.gms.internal.zzabr;
import com.google.android.gms.internal.zzzq;
import com.google.android.gms.internal.zzzs;
import com.google.android.gms.internal.zzzu;
import com.google.android.gms.internal.zzzv;
import com.google.android.gms.internal.zzzz;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class zzaap
implements Handler.Callback {
    public static final Status zzaAO = new Status(4, "Sign-out occurred while this API call was in progress.");
    private static final Status zzaAP = new Status(4, "The user must be signed in to make this API call.");
    private static zzaap zzaAR;
    private static final Object zztU;
    private final Context mContext;
    private final Handler mHandler;
    private long zzaAQ = 10000L;
    private int zzaAS = -1;
    private final AtomicInteger zzaAT = new AtomicInteger(1);
    private final AtomicInteger zzaAU = new AtomicInteger(0);
    private zzaae zzaAV = null;
    private final Set<zzzs<?>> zzaAW = new com.google.android.gms.common.util.zza();
    private final Set<zzzs<?>> zzaAX = new com.google.android.gms.common.util.zza();
    private long zzaAn = 120000L;
    private long zzaAo = 5000L;
    private final GoogleApiAvailability zzaxX;
    private final Map<zzzs<?>, zza<?>> zzazt = new ConcurrentHashMap(5, 0.75f, 1);

    static {
        zztU = new Object();
    }

    private zzaap(Context context, Looper looper, GoogleApiAvailability googleApiAvailability) {
        this.mContext = context;
        this.mHandler = new Handler(looper, (Handler.Callback)this);
        this.zzaxX = googleApiAvailability;
    }

    @WorkerThread
    private void zza(int n, ConnectionResult object) {
        zza<?> zza22;
        block2 : {
            for (zza<?> zza22 : this.zzazt.values()) {
                if (zza22.getInstanceId() != n) continue;
                break block2;
            }
            zza22 = null;
        }
        if (zza22 != null) {
            String string = String.valueOf(this.zzaxX.getErrorString(object.getErrorCode()));
            object = String.valueOf(object.getErrorMessage());
            StringBuilder stringBuilder = new StringBuilder(69 + String.valueOf(string).length() + String.valueOf(object).length());
            stringBuilder.append("Error resolution was canceled by the user, original error message: ");
            stringBuilder.append(string);
            stringBuilder.append(": ");
            stringBuilder.append((String)object);
            zza22.zzC(new Status(17, stringBuilder.toString()));
            return;
        }
        object = new StringBuilder(76);
        object.append("Could not find API instance ");
        object.append(n);
        object.append(" while trying to fail enqueued calls.");
        Log.wtf((String)"GoogleApiManager", (String)object.toString(), (Throwable)new Exception());
    }

    @WorkerThread
    private void zza(zzabd zzabd2) {
        zza<?> zza2;
        zza<?> zza3 = zza2 = this.zzazt.get(zzabd2.zzaBF.getApiKey());
        if (zza2 == null) {
            this.zzb(zzabd2.zzaBF);
            zza3 = this.zzazt.get(zzabd2.zzaBF.getApiKey());
        }
        if (zza3.zzqD() && this.zzaAU.get() != zzabd2.zzaBE) {
            zzabd2.zzaBD.zzy(zzaAO);
            zza3.signOut();
            return;
        }
        zza3.zza(zzabd2.zzaBD);
    }

    /*
     * Enabled aggressive block sorting
     */
    @WorkerThread
    private void zza(zzzu zzzu2) {
        Iterator<zzzs<?>> iterator = zzzu2.zzuY().iterator();
        do {
            zza<?> zza2;
            block8 : {
                zzzs<?> zzzs2;
                block7 : {
                    block6 : {
                        if (!iterator.hasNext()) {
                            return;
                        }
                        zzzs2 = iterator.next();
                        zza2 = this.zzazt.get(zzzs2);
                        if (zza2 == null) {
                            zzzu2.zza(zzzs2, new ConnectionResult(13));
                            return;
                        }
                        if (!zza2.isConnected()) break block6;
                        zza2 = ConnectionResult.zzawX;
                        break block7;
                    }
                    if (zza2.zzwe() == null) break block8;
                    zza2 = zza2.zzwe();
                }
                zzzu2.zza(zzzs2, (ConnectionResult)((Object)zza2));
                continue;
            }
            zza2.zzb(zzzu2);
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzaap zzax(Context object) {
        Object object2 = zztU;
        synchronized (object2) {
            if (zzaAR != null) return zzaAR;
            Looper looper = zzaap.zzvT();
            zzaAR = new zzaap(object.getApplicationContext(), looper, GoogleApiAvailability.getInstance());
            return zzaAR;
        }
    }

    @WorkerThread
    private void zzb(zzc<?> object) {
        zzzs<?> zzzs2 = object.getApiKey();
        if (!this.zzazt.containsKey(zzzs2)) {
            this.zzazt.put(zzzs2, new zza(object));
        }
        if ((object = this.zzazt.get(zzzs2)).zzqD()) {
            this.zzaAX.add(zzzs2);
        }
        object.connect();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzaap zzvS() {
        Object object = zztU;
        synchronized (object) {
            zzac.zzb(zzaAR, (Object)"Must guarantee manager is non-null before using getInstance");
            return zzaAR;
        }
    }

    private static Looper zzvT() {
        HandlerThread handlerThread = new HandlerThread("GoogleApiHandler", 9);
        handlerThread.start();
        return handlerThread.getLooper();
    }

    @WorkerThread
    private void zzvV() {
        for (zza<?> zza2 : this.zzazt.values()) {
            zza2.zzwd();
            zza2.connect();
        }
    }

    @WorkerThread
    private void zzvW() {
        for (zzzs<?> zzzs2 : this.zzaAX) {
            this.zzazt.remove(zzzs2).signOut();
        }
        this.zzaAX.clear();
    }

    @WorkerThread
    public boolean handleMessage(Message object) {
        switch (object.what) {
            default: {
                int n = object.what;
                object = new StringBuilder(31);
                object.append("Unknown message id: ");
                object.append(n);
                Log.w((String)"GoogleApiManager", (String)object.toString());
                return false;
            }
            case 10: {
                if (!this.zzazt.containsKey(object.obj)) break;
                this.zzazt.get(object.obj).zzwh();
                break;
            }
            case 9: {
                if (!this.zzazt.containsKey(object.obj)) break;
                this.zzazt.get(object.obj).zzvJ();
                break;
            }
            case 8: {
                this.zzvW();
                break;
            }
            case 7: {
                if (!this.zzazt.containsKey(object.obj)) break;
                this.zzazt.get(object.obj).resume();
                break;
            }
            case 5: {
                this.zzb((zzc)object.obj);
                break;
            }
            case 4: {
                this.zza(object.arg1, (ConnectionResult)object.obj);
                break;
            }
            case 3: 
            case 6: 
            case 11: {
                this.zza((zzabd)object.obj);
                break;
            }
            case 2: {
                this.zzvV();
                break;
            }
            case 1: {
                this.zza((zzzu)object.obj);
            }
        }
        return true;
    }

    public <O extends Api.ApiOptions> Task<Void> zza(@NonNull zzc<O> zzc2, @NonNull zzaaz.zzb<?> object) {
        TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<Void>();
        object = new zzzq.zze((zzaaz.zzb<?>)object, taskCompletionSource);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(11, (Object)new zzabd((zzzq)object, this.zzaAU.get(), zzc2)));
        return taskCompletionSource.getTask();
    }

    public <O extends Api.ApiOptions> Task<Void> zza(@NonNull zzc<O> zzc2, @NonNull zzabe<Api.zzb, ?> object, @NonNull zzabr<Api.zzb, ?> zzabr2) {
        TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<Void>();
        object = new zzzq.zzc(new zzabf((zzabe<Api.zzb, ?>)object, zzabr2), taskCompletionSource);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(6, (Object)new zzabd((zzzq)object, this.zzaAU.get(), zzc2)));
        return taskCompletionSource.getTask();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Task<Void> zza(Iterable<zzc<?>> iterator) {
        zzzu zzzu2 = new zzzu((Iterable<zzc<? extends Api.ApiOptions>>)((Object)iterator));
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            Object object = (zzc)iterator.next();
            if ((object = this.zzazt.get(object.getApiKey())) != null && object.isConnected()) continue;
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, (Object)zzzu2));
            do {
                return zzzu2.getTask();
                break;
            } while (true);
        }
        zzzu2.zzuZ();
        return zzzu2.getTask();
    }

    public void zza(ConnectionResult connectionResult, int n) {
        if (!this.zzc(connectionResult, n)) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(4, n, 0, (Object)connectionResult));
        }
    }

    public void zza(zzc<?> zzc2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(5, zzc2));
    }

    public <O extends Api.ApiOptions, TResult> void zza(zzc<O> zzc2, int n, zzabn<Api.zzb, TResult> object, TaskCompletionSource<TResult> taskCompletionSource, zzabk zzabk2) {
        object = new zzzq.zzd<TResult>(n, (zzabn<Api.zzb, TResult>)object, taskCompletionSource, zzabk2);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3, (Object)new zzabd((zzzq)object, this.zzaAU.get(), zzc2)));
    }

    public <O extends Api.ApiOptions> void zza(zzc<O> zzc2, int n, zzzv.zza<? extends Result, Api.zzb> object) {
        object = new zzzq.zzb<zzzv.zza<? extends Result, Api.zzb>>(n, (zzzv.zza<? extends Result, Api.zzb>)object);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3, (Object)new zzabd((zzzq)object, this.zzaAU.get(), zzc2)));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zza(@NonNull zzaae zzaae2) {
        Object object = zztU;
        synchronized (object) {
            if (this.zzaAV != zzaae2) {
                this.zzaAV = zzaae2;
                this.zzaAW.clear();
                this.zzaAW.addAll(zzaae2.zzvx());
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void zzb(@NonNull zzaae zzaae2) {
        Object object = zztU;
        synchronized (object) {
            if (this.zzaAV == zzaae2) {
                this.zzaAV = null;
                this.zzaAW.clear();
            }
            return;
        }
    }

    boolean zzc(ConnectionResult connectionResult, int n) {
        if (!connectionResult.hasResolution() && !this.zzaxX.isUserResolvableError(connectionResult.getErrorCode())) {
            return false;
        }
        this.zzaxX.zza(this.mContext, connectionResult, n);
        return true;
    }

    public void zzuW() {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2));
    }

    public int zzvU() {
        return this.zzaAT.getAndIncrement();
    }

    public class zza<O extends Api.ApiOptions>
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
        public zza(zzc<O> zzc2) {
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
                zzb zzb2 = new zzb(this.zzazq, this.zzaxH);
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

    private class zzb
    implements zzf.zzf,
    zzabj.zza {
        private boolean zzaBj = false;
        private Set<Scope> zzajm = null;
        private final zzzs<?> zzaxH;
        private zzr zzazW = null;
        private final Api.zze zzazq;

        public zzb(Api.zze zze2, zzzs<?> zzzs2) {
            this.zzazq = zze2;
            this.zzaxH = zzzs2;
        }

        @WorkerThread
        private void zzwi() {
            if (this.zzaBj && this.zzazW != null) {
                this.zzazq.zza(this.zzazW, this.zzajm);
            }
        }

        @WorkerThread
        @Override
        public void zzb(zzr zzr2, Set<Scope> set) {
            if (zzr2 != null && set != null) {
                this.zzazW = zzr2;
                this.zzajm = set;
                this.zzwi();
                return;
            }
            Log.wtf((String)"GoogleApiManager", (String)"Received null response from onSignInSuccess", (Throwable)new Exception());
            this.zzi(new ConnectionResult(4));
        }

        @Override
        public void zzg(final @NonNull ConnectionResult connectionResult) {
            zzaap.this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    if (connectionResult.isSuccess()) {
                        zzb.this.zzaBj = true;
                        if (zzb.this.zzazq.zzqD()) {
                            zzb.this.zzwi();
                            return;
                        }
                        zzb.this.zzazq.zza(null, Collections.<Scope>emptySet());
                        return;
                    }
                    ((zza)zzaap.this.zzazt.get(zzb.this.zzaxH)).onConnectionFailed(connectionResult);
                }
            });
        }

        @WorkerThread
        @Override
        public void zzi(ConnectionResult connectionResult) {
            ((zza)zzaap.this.zzazt.get(this.zzaxH)).zzi(connectionResult);
        }

    }

}
