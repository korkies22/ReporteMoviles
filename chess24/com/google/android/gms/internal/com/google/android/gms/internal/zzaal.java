/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.auth.api.signin.internal.zzl;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzm;
import com.google.android.gms.common.zzc;
import com.google.android.gms.internal.zzaaa;
import com.google.android.gms.internal.zzaac;
import com.google.android.gms.internal.zzaan;
import com.google.android.gms.internal.zzaar;
import com.google.android.gms.internal.zzaau;
import com.google.android.gms.internal.zzaav;
import com.google.android.gms.internal.zzaaz;
import com.google.android.gms.internal.zzaba;
import com.google.android.gms.internal.zzabi;
import com.google.android.gms.internal.zzabl;
import com.google.android.gms.internal.zzabp;
import com.google.android.gms.internal.zzabq;
import com.google.android.gms.internal.zzabx;
import com.google.android.gms.internal.zzaby;
import com.google.android.gms.internal.zzacb;
import com.google.android.gms.internal.zzaxn;
import com.google.android.gms.internal.zzaxo;
import com.google.android.gms.internal.zzzt;
import com.google.android.gms.internal.zzzv;
import com.google.android.gms.internal.zzzx;
import com.google.android.gms.internal.zzzy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;

public final class zzaal
extends GoogleApiClient
implements zzaau.zza {
    private final Context mContext;
    private final zzm zzaAj;
    private zzaau zzaAk = null;
    final Queue<zzzv.zza<?, ?>> zzaAl = new LinkedList();
    private volatile boolean zzaAm;
    private long zzaAn = 120000L;
    private long zzaAo = 5000L;
    private final zza zzaAp;
    zzaar zzaAq;
    final Map<Api.zzc<?>, Api.zze> zzaAr;
    Set<Scope> zzaAs = new HashSet<Scope>();
    private final zzaba zzaAt = new zzaba();
    private final ArrayList<zzzy> zzaAu;
    private Integer zzaAv = null;
    Set<zzabp> zzaAw = null;
    final zzabq zzaAx;
    private final zzm.zza zzaAy = new zzm.zza(){

        @Override
        public boolean isConnected() {
            return zzaal.this.isConnected();
        }

        @Override
        public Bundle zzud() {
            return null;
        }
    };
    private final int zzaxV;
    private final GoogleApiAvailability zzaxX;
    final Api.zza<? extends zzaxn, zzaxo> zzaxY;
    private boolean zzayb;
    private final Lock zzazn;
    final zzg zzazs;
    final Map<Api<?>, Integer> zzazu;
    private final Looper zzrx;

    public zzaal(Context object, Lock object22, Looper looper, zzg zzg2, GoogleApiAvailability googleApiAvailability, Api.zza<? extends zzaxn, zzaxo> zza2, Map<Api<?>, Integer> map, List<GoogleApiClient.ConnectionCallbacks> list, List<GoogleApiClient.OnConnectionFailedListener> list2, Map<Api.zzc<?>, Api.zze> map2, int n, int n2, ArrayList<zzzy> arrayList, boolean bl) {
        void var4_8;
        void var9_13;
        void var10_14;
        void var8_12;
        void var3_7;
        void var11_15;
        void var7_11;
        void var6_10;
        void var13_17;
        void var5_9;
        void var14_18;
        this.mContext = object;
        this.zzazn = object22;
        this.zzayb = var14_18;
        this.zzaAj = new zzm((Looper)var3_7, this.zzaAy);
        this.zzrx = var3_7;
        this.zzaAp = new zza((Looper)var3_7);
        this.zzaxX = var5_9;
        this.zzaxV = var11_15;
        if (this.zzaxV >= 0) {
            void var12_16;
            this.zzaAv = (int)var12_16;
        }
        this.zzazu = var7_11;
        this.zzaAr = var10_14;
        this.zzaAu = var13_17;
        this.zzaAx = new zzabq(this.zzaAr);
        for (GoogleApiClient.ConnectionCallbacks connectionCallbacks : var8_12) {
            this.zzaAj.registerConnectionCallbacks(connectionCallbacks);
        }
        for (GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener : var9_13) {
            this.zzaAj.registerConnectionFailedListener(onConnectionFailedListener);
        }
        this.zzazs = var4_8;
        this.zzaxY = var6_10;
    }

    private void resume() {
        this.zzazn.lock();
        try {
            if (this.isResuming()) {
                this.zzvI();
            }
            return;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    public static int zza(Iterable<Api.zze> object, boolean bl) {
        object = object.iterator();
        boolean bl2 = false;
        boolean bl3 = false;
        while (object.hasNext()) {
            Api.zze zze2 = (Api.zze)object.next();
            boolean bl4 = bl2;
            if (zze2.zzqD()) {
                bl4 = true;
            }
            bl2 = bl4;
            if (!zze2.zzqS()) continue;
            bl3 = true;
            bl2 = bl4;
        }
        if (bl2) {
            if (bl3 && bl) {
                return 2;
            }
            return 1;
        }
        return 3;
    }

    private void zza(final GoogleApiClient googleApiClient, final zzabl zzabl2, final boolean bl) {
        zzabx.zzaFp.zzg(googleApiClient).setResultCallback(new ResultCallback<Status>(){

            @Override
            public /* synthetic */ void onResult(@NonNull Result result) {
                this.zzp((Status)result);
            }

            public void zzp(@NonNull Status status) {
                zzl.zzaa(zzaal.this.mContext).zzre();
                if (status.isSuccess() && zzaal.this.isConnected()) {
                    zzaal.this.reconnect();
                }
                zzabl2.zzb(status);
                if (bl) {
                    googleApiClient.disconnect();
                }
            }
        });
    }

    private void zzb(@NonNull zzaav zzaav2) {
        if (this.zzaxV >= 0) {
            zzzt.zza(zzaav2).zzcu(this.zzaxV);
            return;
        }
        throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
    }

    private void zzcx(int n) {
        if (this.zzaAv == null) {
            this.zzaAv = n;
        } else if (this.zzaAv != n) {
            String string = String.valueOf(zzaal.zzcy(n));
            String string2 = String.valueOf(zzaal.zzcy(this.zzaAv));
            StringBuilder stringBuilder = new StringBuilder(51 + String.valueOf(string).length() + String.valueOf(string2).length());
            stringBuilder.append("Cannot use sign-in mode: ");
            stringBuilder.append(string);
            stringBuilder.append(". Mode was already set to ");
            stringBuilder.append(string2);
            throw new IllegalStateException(stringBuilder.toString());
        }
        if (this.zzaAk != null) {
            return;
        }
        Iterator<Api.zze> iterator = this.zzaAr.values().iterator();
        boolean bl = false;
        n = 0;
        while (iterator.hasNext()) {
            Api.zze zze2 = iterator.next();
            boolean bl2 = bl;
            if (zze2.zzqD()) {
                bl2 = true;
            }
            bl = bl2;
            if (!zze2.zzqS()) continue;
            n = 1;
            bl = bl2;
        }
        switch (this.zzaAv) {
            default: {
                break;
            }
            case 2: {
                if (!bl) break;
                this.zzaAk = zzaaa.zza(this.mContext, this, this.zzazn, this.zzrx, this.zzaxX, this.zzaAr, this.zzazs, this.zzazu, this.zzaxY, this.zzaAu);
                return;
            }
            case 1: {
                if (!bl) {
                    throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
                }
                if (n == 0) break;
                throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            case 3: 
        }
        if (this.zzayb && n == 0) {
            this.zzaAk = new zzaac(this.mContext, this.zzazn, this.zzrx, this.zzaxX, this.zzaAr, this.zzazs, this.zzazu, this.zzaxY, this.zzaAu, this);
            return;
        }
        this.zzaAk = new zzaan(this.mContext, this, this.zzazn, this.zzrx, this.zzaxX, this.zzaAr, this.zzazs, this.zzazu, this.zzaxY, this.zzaAu, this);
    }

    static String zzcy(int n) {
        switch (n) {
            default: {
                return "UNKNOWN";
            }
            case 3: {
                return "SIGN_IN_MODE_NONE";
            }
            case 2: {
                return "SIGN_IN_MODE_OPTIONAL";
            }
            case 1: 
        }
        return "SIGN_IN_MODE_REQUIRED";
    }

    private void zzvI() {
        this.zzaAj.zzxr();
        this.zzaAk.connect();
    }

    private void zzvJ() {
        this.zzazn.lock();
        try {
            if (this.zzvL()) {
                this.zzvI();
            }
            return;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ConnectionResult blockingConnect() {
        Looper looper = Looper.myLooper();
        Looper looper2 = Looper.getMainLooper();
        boolean bl = true;
        boolean bl2 = looper != looper2;
        zzac.zza(bl2, (Object)"blockingConnect must not be called on the UI thread");
        this.zzazn.lock();
        try {
            if (this.zzaxV >= 0) {
                bl2 = this.zzaAv != null ? bl : false;
                zzac.zza(bl2, (Object)"Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.zzaAv == null) {
                this.zzaAv = zzaal.zza(this.zzaAr.values(), false);
            } else if (this.zzaAv == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            this.zzcx(this.zzaAv);
            this.zzaAj.zzxr();
            ConnectionResult connectionResult = this.zzaAk.blockingConnect();
            this.zzazn.unlock();
            return connectionResult;
        }
        catch (Throwable throwable) {
            this.zzazn.unlock();
            throw throwable;
        }
    }

    @Override
    public ConnectionResult blockingConnect(long l, @NonNull TimeUnit object) {
        boolean bl = Looper.myLooper() != Looper.getMainLooper();
        zzac.zza(bl, (Object)"blockingConnect must not be called on the UI thread");
        zzac.zzb(object, (Object)"TimeUnit must not be null");
        this.zzazn.lock();
        try {
            if (this.zzaAv == null) {
                this.zzaAv = zzaal.zza(this.zzaAr.values(), false);
            } else if (this.zzaAv == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            this.zzcx(this.zzaAv);
            this.zzaAj.zzxr();
            object = this.zzaAk.blockingConnect(l, (TimeUnit)((Object)object));
            return object;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    @Override
    public PendingResult<Status> clearDefaultAccountAndReconnect() {
        zzac.zza(this.isConnected(), (Object)"GoogleApiClient is not connected yet.");
        boolean bl = this.zzaAv != 2;
        zzac.zza(bl, (Object)"Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
        final zzabl zzabl2 = new zzabl(this);
        if (this.zzaAr.containsKey(zzabx.zzahc)) {
            this.zza(this, zzabl2, false);
            return zzabl2;
        }
        final AtomicReference<Object> atomicReference = new AtomicReference<Object>();
        Object object = new GoogleApiClient.ConnectionCallbacks(){

            @Override
            public void onConnected(Bundle bundle) {
                zzaal.this.zza((GoogleApiClient)atomicReference.get(), zzabl2, true);
            }

            @Override
            public void onConnectionSuspended(int n) {
            }
        };
        GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener(this){

            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                zzabl2.zzb(new Status(8));
            }
        };
        object = new GoogleApiClient.Builder(this.mContext).addApi(zzabx.API).addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks)object).addOnConnectionFailedListener(onConnectionFailedListener).setHandler(this.zzaAp).build();
        atomicReference.set(object);
        object.connect();
        return zzabl2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void connect() {
        this.zzazn.lock();
        try {
            int n = this.zzaxV;
            boolean bl = false;
            if (n >= 0) {
                if (this.zzaAv != null) {
                    bl = true;
                }
                zzac.zza(bl, (Object)"Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.zzaAv == null) {
                this.zzaAv = zzaal.zza(this.zzaAr.values(), false);
            } else if (this.zzaAv == 2) {
                throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            this.connect(this.zzaAv);
            return;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    @Override
    public void connect(int n) {
        boolean bl;
        this.zzazn.lock();
        boolean bl2 = bl = true;
        if (n != 3) {
            bl2 = bl;
            if (n != 1) {
                bl2 = n == 2 ? bl : false;
            }
        }
        try {
            StringBuilder stringBuilder = new StringBuilder(33);
            stringBuilder.append("Illegal sign-in mode: ");
            stringBuilder.append(n);
            zzac.zzb(bl2, (Object)stringBuilder.toString());
            this.zzcx(n);
            this.zzvI();
            return;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    @Override
    public void disconnect() {
        block6 : {
            this.zzazn.lock();
            this.zzaAx.release();
            if (this.zzaAk != null) {
                this.zzaAk.disconnect();
            }
            this.zzaAt.release();
            for (zzzv.zza zza2 : this.zzaAl) {
                zza2.zza(null);
                zza2.cancel();
            }
            this.zzaAl.clear();
            zzaau zzaau2 = this.zzaAk;
            if (zzaau2 != null) break block6;
            this.zzazn.unlock();
            return;
        }
        try {
            this.zzvL();
            this.zzaAj.zzxq();
            return;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    @Override
    public void dump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.append(string).append("mContext=").println((Object)this.mContext);
        printWriter.append(string).append("mResuming=").print(this.zzaAm);
        printWriter.append(" mWorkQueue.size()=").print(this.zzaAl.size());
        this.zzaAx.dump(printWriter);
        if (this.zzaAk != null) {
            this.zzaAk.dump(string, fileDescriptor, printWriter, arrstring);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @NonNull
    @Override
    public ConnectionResult getConnectionResult(@NonNull Api<?> object) {
        block9 : {
            ConnectionResult connectionResult;
            block10 : {
                this.zzazn.lock();
                if (!this.isConnected() && !this.isResuming()) {
                    throw new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
                }
                if (!this.zzaAr.containsKey(object.zzuH())) break block9;
                connectionResult = this.zzaAk.getConnectionResult((Api<?>)object);
                if (connectionResult != null) break block10;
                if (this.isResuming()) {
                    object = ConnectionResult.zzawX;
                    do {
                        return object;
                        break;
                    } while (true);
                }
                Log.w((String)"GoogleApiClientImpl", (String)this.zzvN());
                Log.wtf((String)"GoogleApiClientImpl", (String)String.valueOf(object.getName()).concat(" requested in getConnectionResult is not connected but is not present in the failed  connections map"), (Throwable)new Exception());
                object = new ConnectionResult(8, null);
                return object;
            }
            this.zzazn.unlock();
            return connectionResult;
        }
        try {
            throw new IllegalArgumentException(String.valueOf(object.getName()).concat(" was never registered with GoogleApiClient"));
        }
        finally {
            this.zzazn.unlock();
        }
    }

    @Override
    public Context getContext() {
        return this.mContext;
    }

    @Override
    public Looper getLooper() {
        return this.zzrx;
    }

    public int getSessionId() {
        return System.identityHashCode(this);
    }

    @Override
    public boolean hasConnectedApi(@NonNull Api<?> object) {
        boolean bl = this.isConnected();
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = this.zzaAr.get(object.zzuH());
        bl = bl2;
        if (object != null) {
            bl = bl2;
            if (object.isConnected()) {
                bl = true;
            }
        }
        return bl;
    }

    @Override
    public boolean isConnected() {
        if (this.zzaAk != null && this.zzaAk.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isConnecting() {
        if (this.zzaAk != null && this.zzaAk.isConnecting()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isConnectionCallbacksRegistered(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        return this.zzaAj.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    @Override
    public boolean isConnectionFailedListenerRegistered(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.zzaAj.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    boolean isResuming() {
        return this.zzaAm;
    }

    @Override
    public void reconnect() {
        this.disconnect();
        this.connect();
    }

    @Override
    public void registerConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zzaAj.registerConnectionCallbacks(connectionCallbacks);
    }

    @Override
    public void registerConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zzaAj.registerConnectionFailedListener(onConnectionFailedListener);
    }

    @Override
    public void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        this.zzb(new zzaav(fragmentActivity));
    }

    @Override
    public void unregisterConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zzaAj.unregisterConnectionCallbacks(connectionCallbacks);
    }

    @Override
    public void unregisterConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zzaAj.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    @NonNull
    @Override
    public <C extends Api.zze> C zza(@NonNull Api.zzc<C> object) {
        object = this.zzaAr.get(object);
        zzac.zzb(object, (Object)"Appropriate Api was not requested.");
        return (C)object;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public <A extends Api.zzb, R extends Result, T extends zzzv.zza<R, A>> T zza(@NonNull T t) {
        boolean bl = t.zzuH() != null;
        zzac.zzb(bl, (Object)"This task can not be enqueued (it's probably a Batch or malformed)");
        bl = this.zzaAr.containsKey(t.zzuH());
        String string = t.getApi() != null ? t.getApi().getName() : "the API";
        StringBuilder stringBuilder = new StringBuilder(65 + String.valueOf(string).length());
        stringBuilder.append("GoogleApiClient is not configured to use ");
        stringBuilder.append(string);
        stringBuilder.append(" required for this call.");
        zzac.zzb(bl, (Object)stringBuilder.toString());
        this.zzazn.lock();
        try {
            if (this.zzaAk == null) {
                this.zzaAl.add(t);
                do {
                    return t;
                    break;
                } while (true);
            }
            t = this.zzaAk.zza(t);
            return t;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    @Override
    public void zza(zzabp zzabp2) {
        this.zzazn.lock();
        try {
            if (this.zzaAw == null) {
                this.zzaAw = new HashSet<zzabp>();
            }
            this.zzaAw.add(zzabp2);
            return;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    @Override
    public boolean zza(@NonNull Api<?> api) {
        return this.zzaAr.containsKey(api.zzuH());
    }

    @Override
    public boolean zza(zzabi zzabi2) {
        if (this.zzaAk != null && this.zzaAk.zza(zzabi2)) {
            return true;
        }
        return false;
    }

    <C extends Api.zze> C zzb(Api.zzc<?> object) {
        object = this.zzaAr.get(object);
        zzac.zzb(object, (Object)"Appropriate Api was not requested.");
        return (C)object;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public <A extends Api.zzb, T extends zzzv.zza<? extends Result, A>> T zzb(@NonNull T t) {
        boolean bl = t.zzuH() != null;
        zzac.zzb(bl, (Object)"This task can not be executed (it's probably a Batch or malformed)");
        bl = this.zzaAr.containsKey(t.zzuH());
        Object object = t.getApi() != null ? t.getApi().getName() : "the API";
        StringBuilder stringBuilder = new StringBuilder(65 + String.valueOf(object).length());
        stringBuilder.append("GoogleApiClient is not configured to use ");
        stringBuilder.append((String)object);
        stringBuilder.append(" required for this call.");
        zzac.zzb(bl, (Object)stringBuilder.toString());
        this.zzazn.lock();
        try {
            if (this.zzaAk == null) {
                throw new IllegalStateException("GoogleApiClient is not connected yet.");
            }
            if (this.isResuming()) {
                this.zzaAl.add(t);
                do {
                    object = t;
                    if (this.zzaAl.isEmpty()) break;
                    object = this.zzaAl.remove();
                    this.zzaAx.zzb((zzzx<? extends Result>)object);
                    object.zzA(Status.zzayj);
                } while (true);
                do {
                    return (T)object;
                    break;
                } while (true);
            }
            object = this.zzaAk.zzb(t);
            return (T)object;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void zzb(zzabp object) {
        this.zzazn.lock();
        try {
            block7 : {
                Exception exception;
                block6 : {
                    block5 : {
                        if (this.zzaAw != null) break block5;
                        object = "Attempted to remove pending transform when no transforms are registered.";
                        exception = new Exception();
                        break block6;
                    }
                    if (this.zzaAw.remove(object)) break block7;
                    object = "Failed to remove pending transform - this may lead to memory leaks!";
                    exception = new Exception();
                }
                Log.wtf((String)"GoogleApiClientImpl", (String)object, (Throwable)exception);
                return;
            }
            if (this.zzvM()) return;
            this.zzaAk.zzvj();
            return;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    @Override
    public void zzc(int n, boolean bl) {
        if (n == 1 && !bl) {
            this.zzvK();
        }
        this.zzaAx.zzww();
        this.zzaAj.zzcP(n);
        this.zzaAj.zzxq();
        if (n == 2) {
            this.zzvI();
        }
    }

    @Override
    public void zzc(ConnectionResult connectionResult) {
        if (!this.zzaxX.zzd(this.mContext, connectionResult.getErrorCode())) {
            this.zzvL();
        }
        if (!this.isResuming()) {
            this.zzaAj.zzo(connectionResult);
            this.zzaAj.zzxq();
        }
    }

    @Override
    public void zzo(Bundle bundle) {
        while (!this.zzaAl.isEmpty()) {
            this.zzb(this.zzaAl.remove());
        }
        this.zzaAj.zzq(bundle);
    }

    @Override
    public <L> zzaaz<L> zzr(@NonNull L object) {
        this.zzazn.lock();
        try {
            object = this.zzaAt.zzb(object, this.zzrx);
            return object;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    @Override
    public void zzuN() {
        if (this.zzaAk != null) {
            this.zzaAk.zzuN();
        }
    }

    void zzvK() {
        if (this.isResuming()) {
            return;
        }
        this.zzaAm = true;
        if (this.zzaAq == null) {
            this.zzaAq = this.zzaxX.zza(this.mContext.getApplicationContext(), new zzb(this));
        }
        this.zzaAp.sendMessageDelayed(this.zzaAp.obtainMessage(1), this.zzaAn);
        this.zzaAp.sendMessageDelayed(this.zzaAp.obtainMessage(2), this.zzaAo);
    }

    boolean zzvL() {
        if (!this.isResuming()) {
            return false;
        }
        this.zzaAm = false;
        this.zzaAp.removeMessages(2);
        this.zzaAp.removeMessages(1);
        if (this.zzaAq != null) {
            this.zzaAq.unregister();
            this.zzaAq = null;
        }
        return true;
    }

    boolean zzvM() {
        block4 : {
            this.zzazn.lock();
            Set<zzabp> set = this.zzaAw;
            if (set != null) break block4;
            this.zzazn.unlock();
            return false;
        }
        try {
            boolean bl = this.zzaAw.isEmpty();
            return bl ^ true;
        }
        finally {
            this.zzazn.unlock();
        }
    }

    String zzvN() {
        StringWriter stringWriter = new StringWriter();
        this.dump("", null, new PrintWriter(stringWriter), null);
        return stringWriter.toString();
    }

    final class zza
    extends Handler {
        zza(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message object) {
            switch (object.what) {
                default: {
                    int n = object.what;
                    object = new StringBuilder(31);
                    object.append("Unknown message id: ");
                    object.append(n);
                    Log.w((String)"GoogleApiClientImpl", (String)object.toString());
                    return;
                }
                case 2: {
                    zzaal.this.resume();
                    return;
                }
                case 1: 
            }
            zzaal.this.zzvJ();
        }
    }

    static class zzb
    extends zzaar.zza {
        private WeakReference<zzaal> zzaAD;

        zzb(zzaal zzaal2) {
            this.zzaAD = new WeakReference<zzaal>(zzaal2);
        }

        @Override
        public void zzvb() {
            zzaal zzaal2 = (zzaal)this.zzaAD.get();
            if (zzaal2 == null) {
                return;
            }
            zzaal2.resume();
        }
    }

}
