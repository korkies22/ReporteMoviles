/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.zzc;
import com.google.android.gms.internal.zzaal;
import com.google.android.gms.internal.zzaan;
import com.google.android.gms.internal.zzaau;
import com.google.android.gms.internal.zzabi;
import com.google.android.gms.internal.zzaxn;
import com.google.android.gms.internal.zzaxo;
import com.google.android.gms.internal.zzzv;
import com.google.android.gms.internal.zzzy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

final class zzaaa
implements zzaau {
    private final Context mContext;
    private final zzaal zzazd;
    private final zzaan zzaze;
    private final zzaan zzazf;
    private final Map<Api.zzc<?>, zzaan> zzazg;
    private final Set<zzabi> zzazh = Collections.newSetFromMap(new WeakHashMap());
    private final Api.zze zzazi;
    private Bundle zzazj;
    private ConnectionResult zzazk = null;
    private ConnectionResult zzazl = null;
    private boolean zzazm = false;
    private final Lock zzazn;
    private int zzazo = 0;
    private final Looper zzrx;

    private zzaaa(Context object, zzaal iterator, Lock lock, Looper looper, zzc zzc2, Map<Api.zzc<?>, Api.zze> map, Map<Api.zzc<?>, Api.zze> map2, zzg zzg2, Api.zza<? extends zzaxn, zzaxo> zza2, Api.zze zze2, ArrayList<zzzy> arrayList, ArrayList<zzzy> arrayList2, Map<Api<?>, Integer> map3, Map<Api<?>, Integer> map4) {
        this.mContext = object;
        this.zzazd = iterator;
        this.zzazn = lock;
        this.zzrx = looper;
        this.zzazi = zze2;
        this.zzaze = new zzaan((Context)object, this.zzazd, lock, looper, zzc2, map2, null, map4, null, arrayList2, new zza());
        this.zzazf = new zzaan((Context)object, this.zzazd, lock, looper, zzc2, map, zzg2, map3, zza2, arrayList, new zzb());
        object = new ArrayMap();
        iterator = map2.keySet().iterator();
        while (iterator.hasNext()) {
            object.put((Api.zzc)iterator.next(), this.zzaze);
        }
        iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            object.put(iterator.next(), this.zzazf);
        }
        this.zzazg = Collections.unmodifiableMap(object);
    }

    public static zzaaa zza(Context context, zzaal zzaal2, Lock lock, Looper looper, zzc zzc2, Map<Api.zzc<?>, Api.zze> object, zzg zzg2, Map<Api<?>, Integer> object2, Api.zza<? extends zzaxn, zzaxo> zza2, ArrayList<zzzy> object3) {
        Object object4;
        Object object5;
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        Object object6 = object.entrySet().iterator();
        object = null;
        while (object6.hasNext()) {
            object4 = object6.next();
            object5 = (Api.zze)object4.getValue();
            if (object5.zzqS()) {
                object = object5;
            }
            if (object5.zzqD()) {
                arrayMap.put((Api.zzc)object4.getKey(), (Api.zze)object5);
                continue;
            }
            arrayMap2.put((Api.zzc)object4.getKey(), (Api.zze)object5);
        }
        zzac.zza(arrayMap.isEmpty() ^ true, (Object)"CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        object5 = new ArrayMap();
        object6 = new ArrayMap();
        for (Api object7 : object2.keySet()) {
            Api.zzc<?> zzc3 = object7.zzuH();
            if (arrayMap.containsKey(zzc3)) {
                object5.put(object7, (Integer)object2.get(object7));
                continue;
            }
            if (arrayMap2.containsKey(zzc3)) {
                object6.put(object7, (Integer)object2.get(object7));
                continue;
            }
            throw new IllegalStateException("Each API in the apiTypeMap must have a corresponding client in the clients map.");
        }
        object2 = new ArrayList<zzzy>();
        object4 = new ArrayList<zzzy>();
        object3 = object3.iterator();
        while (object3.hasNext()) {
            zzzy zzzy2 = (zzzy)object3.next();
            if (object5.containsKey(zzzy2.zzawb)) {
                object2.add(zzzy2);
                continue;
            }
            if (object6.containsKey(zzzy2.zzawb)) {
                object4.add(zzzy2);
                continue;
            }
            throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the apiTypeMap");
        }
        return new zzaaa(context, zzaal2, lock, looper, zzc2, arrayMap, arrayMap2, zzg2, zza2, (Api.zze)object, object2, (ArrayList<zzzy>)object4, (Map<Api<?>, Integer>)object5, (Map<Api<?>, Integer>)object6);
    }

    private void zza(ConnectionResult connectionResult) {
        switch (this.zzazo) {
            default: {
                Log.wtf((String)"CompositeGAC", (String)"Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", (Throwable)new Exception());
                break;
            }
            case 2: {
                this.zzazd.zzc(connectionResult);
            }
            case 1: {
                this.zzvo();
            }
        }
        this.zzazo = 0;
    }

    private void zzb(int n, boolean bl) {
        this.zzazd.zzc(n, bl);
        this.zzazl = null;
        this.zzazk = null;
    }

    private static boolean zzb(ConnectionResult connectionResult) {
        if (connectionResult != null && connectionResult.isSuccess()) {
            return true;
        }
        return false;
    }

    private boolean zzc(zzzv.zza<? extends Result, ? extends Api.zzb> object) {
        object = object.zzuH();
        zzac.zzb(this.zzazg.containsKey(object), (Object)"GoogleApiClient is not configured to use the API required for this call.");
        return this.zzazg.get(object).equals(this.zzazf);
    }

    private void zzn(Bundle bundle) {
        if (this.zzazj == null) {
            this.zzazj = bundle;
            return;
        }
        if (bundle != null) {
            this.zzazj.putAll(bundle);
        }
    }

    private void zzvl() {
        this.zzazl = null;
        this.zzazk = null;
        this.zzaze.connect();
        this.zzazf.connect();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void zzvm() {
        ConnectionResult connectionResult;
        if (zzaaa.zzb(this.zzazk)) {
            if (!zzaaa.zzb(this.zzazl) && !this.zzvp()) {
                if (this.zzazl == null) return;
                {
                    if (this.zzazo == 1) {
                        this.zzvo();
                        return;
                    }
                    this.zza(this.zzazl);
                    this.zzaze.disconnect();
                    return;
                }
            }
            this.zzvn();
            return;
        }
        if (this.zzazk != null && zzaaa.zzb(this.zzazl)) {
            this.zzazf.disconnect();
            connectionResult = this.zzazk;
        } else {
            if (this.zzazk == null || this.zzazl == null) return;
            connectionResult = this.zzazk;
            if (this.zzazf.zzaAJ < this.zzaze.zzaAJ) {
                connectionResult = this.zzazl;
            }
        }
        this.zza(connectionResult);
    }

    private void zzvn() {
        switch (this.zzazo) {
            default: {
                Log.wtf((String)"CompositeGAC", (String)"Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", (Throwable)((Object)new AssertionError()));
                break;
            }
            case 2: {
                this.zzazd.zzo(this.zzazj);
            }
            case 1: {
                this.zzvo();
            }
        }
        this.zzazo = 0;
    }

    private void zzvo() {
        Iterator<zzabi> iterator = this.zzazh.iterator();
        while (iterator.hasNext()) {
            iterator.next().zzqR();
        }
        this.zzazh.clear();
    }

    private boolean zzvp() {
        if (this.zzazl != null && this.zzazl.getErrorCode() == 4) {
            return true;
        }
        return false;
    }

    @Nullable
    private PendingIntent zzvq() {
        if (this.zzazi == null) {
            return null;
        }
        return PendingIntent.getActivity((Context)this.mContext, (int)this.zzazd.getSessionId(), (Intent)this.zzazi.zzqT(), (int)134217728);
    }

    @Override
    public ConnectionResult blockingConnect() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConnectionResult blockingConnect(long l, @NonNull TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void connect() {
        this.zzazo = 2;
        this.zzazm = false;
        this.zzvl();
    }

    @Override
    public void disconnect() {
        this.zzazl = null;
        this.zzazk = null;
        this.zzazo = 0;
        this.zzaze.disconnect();
        this.zzazf.disconnect();
        this.zzvo();
    }

    @Override
    public void dump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.append(string).append("authClient").println(":");
        this.zzazf.dump(String.valueOf(string).concat("  "), fileDescriptor, printWriter, arrstring);
        printWriter.append(string).append("anonClient").println(":");
        this.zzaze.dump(String.valueOf(string).concat("  "), fileDescriptor, printWriter, arrstring);
    }

    @Nullable
    @Override
    public ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        if (this.zzazg.get(api.zzuH()).equals(this.zzazf)) {
            if (this.zzvp()) {
                return new ConnectionResult(4, this.zzvq());
            }
            return this.zzazf.getConnectionResult(api);
        }
        return this.zzaze.getConnectionResult(api);
    }

    @Override
    public boolean isConnected() {
        boolean bl;
        block5 : {
            block4 : {
                boolean bl2;
                this.zzazn.lock();
                try {
                    bl = this.zzaze.isConnected();
                    bl2 = true;
                    if (!bl) break block4;
                    bl = bl2;
                }
                catch (Throwable throwable) {
                    this.zzazn.unlock();
                    throw throwable;
                }
                if (this.zzvk()) break block5;
                bl = bl2;
                if (this.zzvp()) break block5;
                int n = this.zzazo;
                if (n != 1) break block4;
                bl = bl2;
                break block5;
            }
            bl = false;
        }
        this.zzazn.unlock();
        return bl;
    }

    @Override
    public boolean isConnecting() {
        this.zzazn.lock();
        try {
            int n = this.zzazo;
            boolean bl = n == 2;
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
        if (this.zzc((zzzv.zza<? extends Result, ? extends Api.zzb>)t)) {
            if (this.zzvp()) {
                t.zzA(new Status(4, null, this.zzvq()));
                return t;
            }
            return this.zzazf.zza(t);
        }
        return this.zzaze.zza(t);
    }

    @Override
    public boolean zza(zzabi zzabi2) {
        this.zzazn.lock();
        try {
            if ((this.isConnecting() || this.isConnected()) && !this.zzvk()) {
                this.zzazh.add(zzabi2);
                if (this.zzazo == 0) {
                    this.zzazo = 1;
                }
                this.zzazl = null;
                this.zzazf.connect();
                return true;
            }
            return false;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    @Override
    public <A extends Api.zzb, T extends zzzv.zza<? extends Result, A>> T zzb(@NonNull T t) {
        if (this.zzc((zzzv.zza<? extends Result, ? extends Api.zzb>)t)) {
            if (this.zzvp()) {
                t.zzA(new Status(4, null, this.zzvq()));
                return t;
            }
            return this.zzazf.zzb(t);
        }
        return this.zzaze.zzb(t);
    }

    @Override
    public void zzuN() {
        this.zzazn.lock();
        try {
            boolean bl = this.isConnecting();
            this.zzazf.disconnect();
            this.zzazl = new ConnectionResult(4);
            if (bl) {
                new Handler(this.zzrx).post(new Runnable(){

                    @Override
                    public void run() {
                        zzaaa.this.zzazn.lock();
                        try {
                            zzaaa.this.zzvm();
                            return;
                        }
                        finally {
                            zzaaa.this.zzazn.unlock();
                        }
                    }
                });
            } else {
                this.zzvo();
            }
            return;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    @Override
    public void zzvj() {
        this.zzaze.zzvj();
        this.zzazf.zzvj();
    }

    public boolean zzvk() {
        return this.zzazf.isConnected();
    }

    private class zza
    implements zzaau.zza {
        private zza() {
        }

        @Override
        public void zzc(int n, boolean bl) {
            zzaaa.this.zzazn.lock();
            try {
                if (!zzaaa.this.zzazm && zzaaa.this.zzazl != null && zzaaa.this.zzazl.isSuccess()) {
                    zzaaa.this.zzazm = true;
                    zzaaa.this.zzazf.onConnectionSuspended(n);
                    return;
                }
                zzaaa.this.zzazm = false;
                zzaaa.this.zzb(n, bl);
                return;
            }
            finally {
                zzaaa.this.zzazn.unlock();
            }
        }

        @Override
        public void zzc(@NonNull ConnectionResult connectionResult) {
            zzaaa.this.zzazn.lock();
            try {
                zzaaa.this.zzazk = connectionResult;
                zzaaa.this.zzvm();
                return;
            }
            finally {
                zzaaa.this.zzazn.unlock();
            }
        }

        @Override
        public void zzo(@Nullable Bundle bundle) {
            zzaaa.this.zzazn.lock();
            try {
                zzaaa.this.zzn(bundle);
                zzaaa.this.zzazk = ConnectionResult.zzawX;
                zzaaa.this.zzvm();
                return;
            }
            finally {
                zzaaa.this.zzazn.unlock();
            }
        }
    }

    private class zzb
    implements zzaau.zza {
        private zzb() {
        }

        @Override
        public void zzc(int n, boolean bl) {
            zzaaa.this.zzazn.lock();
            try {
                if (zzaaa.this.zzazm) {
                    zzaaa.this.zzazm = false;
                    zzaaa.this.zzb(n, bl);
                    return;
                }
                zzaaa.this.zzazm = true;
                zzaaa.this.zzaze.onConnectionSuspended(n);
                return;
            }
            finally {
                zzaaa.this.zzazn.unlock();
            }
        }

        @Override
        public void zzc(@NonNull ConnectionResult connectionResult) {
            zzaaa.this.zzazn.lock();
            try {
                zzaaa.this.zzazl = connectionResult;
                zzaaa.this.zzvm();
                return;
            }
            finally {
                zzaaa.this.zzazn.unlock();
            }
        }

        @Override
        public void zzo(@Nullable Bundle bundle) {
            zzaaa.this.zzazn.lock();
            try {
                zzaaa.this.zzazl = ConnectionResult.zzawX;
                zzaaa.this.zzvm();
                return;
            }
            finally {
                zzaaa.this.zzazn.unlock();
            }
        }
    }

}
