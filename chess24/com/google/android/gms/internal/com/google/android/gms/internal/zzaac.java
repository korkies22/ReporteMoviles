/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.zzb;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.zzc;
import com.google.android.gms.internal.zzaab;
import com.google.android.gms.internal.zzaal;
import com.google.android.gms.internal.zzaap;
import com.google.android.gms.internal.zzaau;
import com.google.android.gms.internal.zzabi;
import com.google.android.gms.internal.zzabq;
import com.google.android.gms.internal.zzact;
import com.google.android.gms.internal.zzaxn;
import com.google.android.gms.internal.zzaxo;
import com.google.android.gms.internal.zzzs;
import com.google.android.gms.internal.zzzv;
import com.google.android.gms.internal.zzzx;
import com.google.android.gms.internal.zzzy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class zzaac
implements zzaau {
    private final zzaap zzaxK;
    private ConnectionResult zzazA;
    private final Lock zzazn;
    private final zzg zzazs;
    private final Map<Api.zzc<?>, com.google.android.gms.common.api.zzc<?>> zzazt = new HashMap();
    private final Map<Api<?>, Integer> zzazu;
    private final zzaal zzazv;
    private final zzc zzazw;
    private final Condition zzazx;
    private boolean zzazy;
    private Map<zzzs<?>, ConnectionResult> zzazz;
    private final Looper zzrx;

    public zzaac(Context context, Lock object, Looper looper, zzc hashMap, Map<Api.zzc<?>, Api.zze> object2, zzg zzg2, Map<Api<?>, Integer> object32, Api.zza<? extends zzaxn, zzaxo> zza2, ArrayList<zzzy> object4, zzaal object5) {
        Object object3;
        Api.zze zze2;
        this.zzazn = object;
        this.zzrx = looper;
        this.zzazx = object.newCondition();
        this.zzazw = hashMap;
        this.zzazv = zze2;
        this.zzazu = object32;
        this.zzazs = zzg2;
        object = new HashMap();
        for (Api api : object32.keySet()) {
            object.put(api.zzuH(), api);
        }
        hashMap = new HashMap();
        Iterator iterator = object3.iterator();
        while (iterator.hasNext()) {
            object3 = (zzzy)iterator.next();
            hashMap.put(object3.zzawb, (zzzy)object3);
        }
        for (Map.Entry entry : object2.entrySet()) {
            void var8_13;
            object3 = (Api)object.get(entry.getKey());
            zze2 = (Api.zze)entry.getValue();
            zzzy zzzy2 = (zzzy)hashMap.get(object3);
            this.zzazt.put((Api.zzc)entry.getKey(), new zzaab(context, object3, looper, zze2, zzzy2, zzg2, (Api.zza<? extends zzaxn, zzaxo>)((Api.zza<zzaxn, zzaxo>)((Api.zza<? extends zzaxn, zzaxo>)var8_13))));
        }
        this.zzaxK = zzaap.zzvS();
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
            this.zzazx.await();
        }
        if (this.isConnected()) {
            return ConnectionResult.zzawX;
        }
        if (this.zzazA == null) return new ConnectionResult(13, null);
        return this.zzazA;
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
            var1_1 = this.zzazx.awaitNanos(var1_1);
        }
        if (this.isConnected()) {
            return ConnectionResult.zzawX;
        }
        if (this.zzazA == null) return new ConnectionResult(13, null);
        return this.zzazA;
        catch (InterruptedException var3_3) {}
        Thread.currentThread().interrupt();
        return new ConnectionResult(15, null);
    }

    @Override
    public void connect() {
        block4 : {
            this.zzazn.lock();
            boolean bl = this.zzazy;
            if (!bl) break block4;
            this.zzazn.unlock();
            return;
        }
        try {
            this.zzazy = true;
            this.zzazz = null;
            this.zzazA = null;
            zza zza2 = new zza();
            zzact zzact2 = new zzact(this.zzrx);
            this.zzaxK.zza(this.zzazt.values()).addOnSuccessListener(zzact2, (OnSuccessListener<Void>)zza2).addOnFailureListener(zzact2, (OnFailureListener)zza2);
            return;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    @Override
    public void disconnect() {
        this.zzazn.lock();
        try {
            this.zzazy = false;
            this.zzazz = null;
            this.zzazA = null;
            this.zzazx.signalAll();
            return;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    @Override
    public void dump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
    }

    @Nullable
    @Override
    public ConnectionResult getConnectionResult(@NonNull Api<?> object) {
        this.zzazn.lock();
        try {
            if (((zzaab)this.zzazt.get(object.zzuH())).zzvr().isConnected()) {
                object = ConnectionResult.zzawX;
                return object;
            }
            if (this.zzazz != null) {
                object = this.zzazz.get(this.zzazt.get(object.zzuH()).getApiKey());
                return object;
            }
            return null;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    @Override
    public boolean isConnected() {
        this.zzazn.lock();
        try {
            ConnectionResult connectionResult;
            boolean bl = this.zzazz != null && (connectionResult = this.zzazA) == null;
            this.zzazn.unlock();
            return bl;
        }
        catch (Throwable throwable) {
            this.zzazn.unlock();
            throw throwable;
        }
    }

    @Override
    public boolean isConnecting() {
        this.zzazn.lock();
        try {
            boolean bl;
            bl = this.zzazz == null && (bl = this.zzazy);
            this.zzazn.unlock();
            return bl;
        }
        catch (Throwable throwable) {
            this.zzazn.unlock();
            throw throwable;
        }
    }

    @Override
    public <A extends Api.zzb, R extends Result, T extends zzzv.zza<R, A>> T zza(@NonNull T t) {
        this.zzazv.zzaAx.zzb((zzzx<? extends Result>)t);
        return this.zzazt.get(t.zzuH()).doRead(t);
    }

    @Override
    public boolean zza(zzabi zzabi2) {
        return false;
    }

    @Override
    public <A extends Api.zzb, T extends zzzv.zza<? extends Result, A>> T zzb(@NonNull T t) {
        this.zzazv.zzaAx.zzb((zzzx<? extends Result>)t);
        return this.zzazt.get(t.zzuH()).doWrite(t);
    }

    @Override
    public void zzuN() {
    }

    @Override
    public void zzvj() {
    }

    private class zza
    implements OnFailureListener,
    OnSuccessListener<Void> {
        private zza() {
        }

        @Nullable
        private ConnectionResult zzvs() {
            Iterator iterator = zzaac.this.zzazu.keySet().iterator();
            ConnectionResult connectionResult = null;
            int n = 0;
            while (iterator.hasNext()) {
                int n2;
                Api api = (Api)iterator.next();
                ConnectionResult connectionResult2 = (ConnectionResult)zzaac.this.zzazz.get(((com.google.android.gms.common.api.zzc)zzaac.this.zzazt.get(api.zzuH())).getApiKey());
                if (connectionResult2.isSuccess() || (n2 = ((Integer)zzaac.this.zzazu.get(api)).intValue()) == 2 || n2 == 1 && !connectionResult2.hasResolution() && !zzaac.this.zzazw.isUserResolvableError(connectionResult2.getErrorCode())) continue;
                n2 = api.zzuF().getPriority();
                if (connectionResult != null && n <= n2) continue;
                n = n2;
                connectionResult = connectionResult2;
            }
            return connectionResult;
        }

        private void zzvt() {
            if (zzaac.this.zzazs == null) {
                zzaac.zzd((zzaac)zzaac.this).zzaAs = Collections.emptySet();
                return;
            }
            HashSet<Scope> hashSet = new HashSet<Scope>(zzaac.this.zzazs.zzxe());
            Map<Api<?>, zzg.zza> map = zzaac.this.zzazs.zzxg();
            for (Api<?> api : map.keySet()) {
                ConnectionResult connectionResult = (ConnectionResult)zzaac.this.zzazz.get(((com.google.android.gms.common.api.zzc)zzaac.this.zzazt.get(api.zzuH())).getApiKey());
                if (connectionResult == null || !connectionResult.isSuccess()) continue;
                hashSet.addAll(map.get(api).zzajm);
            }
            zzaac.zzd((zzaac)zzaac.this).zzaAs = hashSet;
        }

        @Override
        public void onFailure(@NonNull Exception exception) {
            exception = (zzb)exception;
            zzaac.this.zzazn.lock();
            try {
                zzaac.this.zzazz = exception.zzuK();
                zzaac.this.zzazA = this.zzvs();
                if (zzaac.this.zzazA == null) {
                    this.zzvt();
                    zzaac.this.zzazv.zzo(null);
                } else {
                    zzaac.this.zzazy = false;
                    zzaac.this.zzazv.zzc(zzaac.this.zzazA);
                }
                zzaac.this.zzazx.signalAll();
                return;
            }
            finally {
                zzaac.this.zzazn.unlock();
            }
        }

        @Override
        public /* synthetic */ void onSuccess(Object object) {
            this.zza((Void)object);
        }

        public void zza(Void object) {
            zzaac.this.zzazn.lock();
            try {
                zzaac.this.zzazz = new ArrayMap(zzaac.this.zzazt.size());
                for (Api.zzc zzc2 : zzaac.this.zzazt.keySet()) {
                    zzaac.this.zzazz.put(((com.google.android.gms.common.api.zzc)zzaac.this.zzazt.get(zzc2)).getApiKey(), ConnectionResult.zzawX);
                }
                this.zzvt();
                zzaac.this.zzazv.zzo(null);
                zzaac.this.zzazx.signalAll();
                return;
            }
            finally {
                zzaac.this.zzazn.unlock();
            }
        }
    }

}
