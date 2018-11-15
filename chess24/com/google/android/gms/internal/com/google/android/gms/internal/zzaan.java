/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.zzc;
import com.google.android.gms.internal.zzaai;
import com.google.android.gms.internal.zzaaj;
import com.google.android.gms.internal.zzaak;
import com.google.android.gms.internal.zzaal;
import com.google.android.gms.internal.zzaam;
import com.google.android.gms.internal.zzaau;
import com.google.android.gms.internal.zzabi;
import com.google.android.gms.internal.zzaxn;
import com.google.android.gms.internal.zzaxo;
import com.google.android.gms.internal.zzzv;
import com.google.android.gms.internal.zzzy;
import com.google.android.gms.internal.zzzz;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class zzaan
implements zzaau,
zzzz {
    private final Context mContext;
    private final Condition zzaAE;
    private final zzb zzaAF;
    final Map<Api.zzc<?>, ConnectionResult> zzaAG = new HashMap();
    private volatile zzaam zzaAH;
    private ConnectionResult zzaAI = null;
    int zzaAJ;
    final zzaau.zza zzaAK;
    final Map<Api.zzc<?>, Api.zze> zzaAr;
    final Api.zza<? extends zzaxn, zzaxo> zzaxY;
    final zzaal zzazd;
    private final Lock zzazn;
    final zzg zzazs;
    final Map<Api<?>, Integer> zzazu;
    private final zzc zzazw;

    public zzaan(Context object, zzaal zzaal2, Lock lock, Looper looper, zzc zzc2, Map<Api.zzc<?>, Api.zze> map, zzg zzg2, Map<Api<?>, Integer> map2, Api.zza<? extends zzaxn, zzaxo> zza2, ArrayList<zzzy> arrayList, zzaau.zza zza3) {
        this.mContext = object;
        this.zzazn = lock;
        this.zzazw = zzc2;
        this.zzaAr = map;
        this.zzazs = zzg2;
        this.zzazu = map2;
        this.zzaxY = zza2;
        this.zzazd = zzaal2;
        this.zzaAK = zza3;
        object = arrayList.iterator();
        while (object.hasNext()) {
            ((zzzy)object.next()).zza(this);
        }
        this.zzaAF = new zzb(looper);
        this.zzaAE = lock.newCondition();
        this.zzaAH = new zzaak(this);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public ConnectionResult blockingConnect() {
        this.connect();
        while (this.isConnecting()) {
            this.zzaAE.await();
        }
        if (this.isConnected()) {
            return ConnectionResult.zzawX;
        }
        if (this.zzaAI == null) return new ConnectionResult(13, null);
        return this.zzaAI;
        catch (InterruptedException interruptedException) {}
        Thread.currentThread().interrupt();
        return new ConnectionResult(15, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public ConnectionResult blockingConnect(long var1_1, TimeUnit var3_2) {
        this.connect();
        var1_1 = var3_2.toNanos(var1_1);
        while (this.isConnecting()) {
            if (var1_1 > 0L) ** GOTO lbl8
            this.disconnect();
            return new ConnectionResult(14, null);
lbl8: // 1 sources:
            var1_1 = this.zzaAE.awaitNanos(var1_1);
        }
        if (this.isConnected()) {
            return ConnectionResult.zzawX;
        }
        if (this.zzaAI == null) return new ConnectionResult(13, null);
        return this.zzaAI;
        catch (InterruptedException var3_3) {}
        Thread.currentThread().interrupt();
        return new ConnectionResult(15, null);
    }

    @Override
    public void connect() {
        this.zzaAH.connect();
    }

    @Override
    public void disconnect() {
        if (this.zzaAH.disconnect()) {
            this.zzaAG.clear();
        }
    }

    @Override
    public void dump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        String string2 = String.valueOf(string).concat("  ");
        printWriter.append(string).append("mState=").println(this.zzaAH);
        for (Api<?> api : this.zzazu.keySet()) {
            printWriter.append(string).append(api.getName()).println(":");
            this.zzaAr.get(api.zzuH()).dump(string2, fileDescriptor, printWriter, arrstring);
        }
    }

    @Nullable
    @Override
    public ConnectionResult getConnectionResult(@NonNull Api<?> object) {
        if (this.zzaAr.containsKey(object = object.zzuH())) {
            if (this.zzaAr.get(object).isConnected()) {
                return ConnectionResult.zzawX;
            }
            if (this.zzaAG.containsKey(object)) {
                return this.zzaAG.get(object);
            }
        }
        return null;
    }

    @Override
    public boolean isConnected() {
        return this.zzaAH instanceof zzaai;
    }

    @Override
    public boolean isConnecting() {
        return this.zzaAH instanceof zzaaj;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        this.zzazn.lock();
        try {
            this.zzaAH.onConnected(bundle);
            return;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    @Override
    public void onConnectionSuspended(int n) {
        this.zzazn.lock();
        try {
            this.zzaAH.onConnectionSuspended(n);
            return;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    @Override
    public <A extends Api.zzb, R extends Result, T extends zzzv.zza<R, A>> T zza(@NonNull T t) {
        t.zzvf();
        return this.zzaAH.zza(t);
    }

    @Override
    public void zza(@NonNull ConnectionResult connectionResult, @NonNull Api<?> api, int n) {
        this.zzazn.lock();
        try {
            this.zzaAH.zza(connectionResult, api, n);
            return;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    void zza(zza zza2) {
        zza2 = this.zzaAF.obtainMessage(1, (Object)zza2);
        this.zzaAF.sendMessage((Message)zza2);
    }

    void zza(RuntimeException runtimeException) {
        runtimeException = this.zzaAF.obtainMessage(2, (Object)runtimeException);
        this.zzaAF.sendMessage((Message)runtimeException);
    }

    @Override
    public boolean zza(zzabi zzabi2) {
        return false;
    }

    @Override
    public <A extends Api.zzb, T extends zzzv.zza<? extends Result, A>> T zzb(@NonNull T t) {
        t.zzvf();
        return this.zzaAH.zzb(t);
    }

    void zzh(ConnectionResult connectionResult) {
        this.zzazn.lock();
        try {
            this.zzaAI = connectionResult;
            this.zzaAH = new zzaak(this);
            this.zzaAH.begin();
            this.zzaAE.signalAll();
            return;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    @Override
    public void zzuN() {
    }

    void zzvO() {
        this.zzazn.lock();
        try {
            this.zzaAH = new zzaaj(this, this.zzazs, this.zzazu, this.zzazw, this.zzaxY, this.zzazn, this.mContext);
            this.zzaAH.begin();
            this.zzaAE.signalAll();
            return;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    void zzvP() {
        this.zzazn.lock();
        try {
            this.zzazd.zzvL();
            this.zzaAH = new zzaai(this);
            this.zzaAH.begin();
            this.zzaAE.signalAll();
            return;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    void zzvQ() {
        Iterator<Api.zze> iterator = this.zzaAr.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().disconnect();
        }
    }

    @Override
    public void zzvj() {
        if (this.isConnected()) {
            ((zzaai)this.zzaAH).zzvz();
        }
    }

    static abstract class zza {
        private final zzaam zzaAL;

        protected zza(zzaam zzaam2) {
            this.zzaAL = zzaam2;
        }

        public final void zzc(zzaan zzaan2) {
            block4 : {
                zzaan2.zzazn.lock();
                zzaam zzaam2 = zzaan2.zzaAH;
                zzaam zzaam3 = this.zzaAL;
                if (zzaam2 == zzaam3) break block4;
                zzaan2.zzazn.unlock();
                return;
            }
            try {
                this.zzvA();
                return;
            }
            finally {
                zzaan2.zzazn.unlock();
            }
        }

        protected abstract void zzvA();
    }

    final class zzb
    extends Handler {
        zzb(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message object) {
            switch (object.what) {
                default: {
                    int n = object.what;
                    object = new StringBuilder(31);
                    object.append("Unknown message id: ");
                    object.append(n);
                    Log.w((String)"GACStateManager", (String)object.toString());
                    return;
                }
                case 2: {
                    throw (RuntimeException)object.obj;
                }
                case 1: 
            }
            ((zza)object.obj).zzc(zzaan.this);
        }
    }

}
