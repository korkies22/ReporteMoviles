/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Looper
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzaf;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzaal;
import com.google.android.gms.internal.zzaam;
import com.google.android.gms.internal.zzaan;
import com.google.android.gms.internal.zzaao;
import com.google.android.gms.internal.zzaau;
import com.google.android.gms.internal.zzaxn;
import com.google.android.gms.internal.zzaxo;
import com.google.android.gms.internal.zzaxr;
import com.google.android.gms.internal.zzaxu;
import com.google.android.gms.internal.zzayb;
import com.google.android.gms.internal.zzzv;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;

public class zzaaj
implements zzaam {
    private final Context mContext;
    private final Api.zza<? extends zzaxn, zzaxo> zzaxY;
    private ConnectionResult zzazA;
    private final zzaan zzazK;
    private int zzazN;
    private int zzazO = 0;
    private int zzazP;
    private final Bundle zzazQ = new Bundle();
    private final Set<Api.zzc> zzazR = new HashSet<Api.zzc>();
    private zzaxn zzazS;
    private int zzazT;
    private boolean zzazU;
    private boolean zzazV;
    private zzr zzazW;
    private boolean zzazX;
    private boolean zzazY;
    private ArrayList<Future<?>> zzazZ = new ArrayList();
    private final Lock zzazn;
    private final zzg zzazs;
    private final Map<Api<?>, Integer> zzazu;
    private final com.google.android.gms.common.zzc zzazw;

    public zzaaj(zzaan zzaan2, zzg zzg2, Map<Api<?>, Integer> map, com.google.android.gms.common.zzc zzc2, Api.zza<? extends zzaxn, zzaxo> zza2, Lock lock, Context context) {
        this.zzazK = zzaan2;
        this.zzazs = zzg2;
        this.zzazu = map;
        this.zzazw = zzc2;
        this.zzaxY = zza2;
        this.zzazn = lock;
        this.mContext = context;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void zza(zzayb zza2) {
        if (!this.zzcv(0)) {
            return;
        }
        Object object = zza2.zzxA();
        if (object.isSuccess()) {
            object = zza2.zzOp();
            zza2 = object.zzxA();
            if (!zza2.isSuccess()) {
                object = String.valueOf(zza2);
                StringBuilder stringBuilder = new StringBuilder(48 + String.valueOf(object).length());
                stringBuilder.append("Sign-in succeeded with resolve account failure: ");
                stringBuilder.append((String)object);
                Log.wtf((String)"GoogleApiClientConnecting", (String)stringBuilder.toString(), (Throwable)new Exception());
                this.zzf((ConnectionResult)zza2);
                return;
            }
            this.zzazV = true;
            this.zzazW = object.zzxz();
            this.zzazX = object.zzxB();
            this.zzazY = object.zzxC();
        } else {
            if (!this.zze((ConnectionResult)object)) {
                this.zzf((ConnectionResult)object);
                return;
            }
            this.zzvF();
        }
        this.zzvC();
    }

    private boolean zza(int n, int n2, ConnectionResult connectionResult) {
        boolean bl = false;
        if (n2 == 1 && !this.zzd(connectionResult)) {
            return false;
        }
        if (this.zzazA == null || n < this.zzazN) {
            bl = true;
        }
        return bl;
    }

    private void zzaq(boolean bl) {
        if (this.zzazS != null) {
            if (this.zzazS.isConnected() && bl) {
                this.zzazS.zzOe();
            }
            this.zzazS.disconnect();
            this.zzazW = null;
        }
    }

    private void zzb(ConnectionResult connectionResult, Api<?> api, int n) {
        int n2;
        if (n != 2 && this.zza(n2 = api.zzuF().getPriority(), n, connectionResult)) {
            this.zzazA = connectionResult;
            this.zzazN = n2;
        }
        this.zzazK.zzaAG.put(api.zzuH(), connectionResult);
    }

    private boolean zzcv(int n) {
        if (this.zzazO != n) {
            Log.w((String)"GoogleApiClientConnecting", (String)this.zzazK.zzazd.zzvN());
            CharSequence charSequence = String.valueOf(this);
            CharSequence charSequence2 = new StringBuilder(23 + String.valueOf(charSequence).length());
            charSequence2.append("Unexpected callback in ");
            charSequence2.append((String)charSequence);
            Log.w((String)"GoogleApiClientConnecting", (String)charSequence2.toString());
            int n2 = this.zzazP;
            charSequence = new StringBuilder(33);
            charSequence.append("mRemainingConnections=");
            charSequence.append(n2);
            Log.w((String)"GoogleApiClientConnecting", (String)charSequence.toString());
            charSequence = String.valueOf(this.zzcw(this.zzazO));
            charSequence2 = String.valueOf(this.zzcw(n));
            StringBuilder stringBuilder = new StringBuilder(70 + String.valueOf(charSequence).length() + String.valueOf(charSequence2).length());
            stringBuilder.append("GoogleApiClient connecting is in step ");
            stringBuilder.append((String)charSequence);
            stringBuilder.append(" but received callback for step ");
            stringBuilder.append((String)charSequence2);
            Log.wtf((String)"GoogleApiClientConnecting", (String)stringBuilder.toString(), (Throwable)new Exception());
            this.zzf(new ConnectionResult(8, null));
            return false;
        }
        return true;
    }

    private String zzcw(int n) {
        switch (n) {
            default: {
                return "UNKNOWN";
            }
            case 1: {
                return "STEP_GETTING_REMOTE_SERVICE";
            }
            case 0: 
        }
        return "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
    }

    private boolean zzd(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            return true;
        }
        if (this.zzazw.zzcr(connectionResult.getErrorCode()) != null) {
            return true;
        }
        return false;
    }

    private boolean zze(ConnectionResult connectionResult) {
        int n = this.zzazT;
        boolean bl = true;
        if (n != 2) {
            if (this.zzazT == 1 && !connectionResult.hasResolution()) {
                return true;
            }
            bl = false;
        }
        return bl;
    }

    private void zzf(ConnectionResult connectionResult) {
        this.zzvG();
        this.zzaq(connectionResult.hasResolution() ^ true);
        this.zzazK.zzh(connectionResult);
        this.zzazK.zzaAK.zzc(connectionResult);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean zzvB() {
        ConnectionResult connectionResult;
        --this.zzazP;
        if (this.zzazP > 0) {
            return false;
        }
        if (this.zzazP < 0) {
            Log.w((String)"GoogleApiClientConnecting", (String)this.zzazK.zzazd.zzvN());
            Log.wtf((String)"GoogleApiClientConnecting", (String)"GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", (Throwable)new Exception());
            connectionResult = new ConnectionResult(8, null);
        } else {
            if (this.zzazA == null) {
                return true;
            }
            this.zzazK.zzaAJ = this.zzazN;
            connectionResult = this.zzazA;
        }
        this.zzf(connectionResult);
        return false;
    }

    private void zzvC() {
        if (this.zzazP != 0) {
            return;
        }
        if (!this.zzazU || this.zzazV) {
            this.zzvD();
        }
    }

    private void zzvD() {
        ArrayList<Api.zze> arrayList = new ArrayList<Api.zze>();
        this.zzazO = 1;
        this.zzazP = this.zzazK.zzaAr.size();
        for (Api.zzc<?> zzc2 : this.zzazK.zzaAr.keySet()) {
            if (this.zzazK.zzaAG.containsKey(zzc2)) {
                if (!this.zzvB()) continue;
                this.zzvE();
                continue;
            }
            arrayList.add(this.zzazK.zzaAr.get(zzc2));
        }
        if (!arrayList.isEmpty()) {
            this.zzazZ.add(zzaao.zzvR().submit(new zzc(arrayList)));
        }
    }

    private void zzvE() {
        this.zzazK.zzvP();
        zzaao.zzvR().execute(new Runnable(){

            @Override
            public void run() {
                zzaaj.this.zzazw.zzan(zzaaj.this.mContext);
            }
        });
        if (this.zzazS != null) {
            if (this.zzazX) {
                this.zzazS.zza(this.zzazW, this.zzazY);
            }
            this.zzaq(false);
        }
        for (Api.zzc<?> zzc2 : this.zzazK.zzaAG.keySet()) {
            this.zzazK.zzaAr.get(zzc2).disconnect();
        }
        Object object = this.zzazQ.isEmpty() ? null : this.zzazQ;
        this.zzazK.zzaAK.zzo((Bundle)object);
    }

    private void zzvF() {
        this.zzazU = false;
        this.zzazK.zzazd.zzaAs = Collections.emptySet();
        for (Api.zzc zzc2 : this.zzazR) {
            if (this.zzazK.zzaAG.containsKey(zzc2)) continue;
            this.zzazK.zzaAG.put(zzc2, new ConnectionResult(17, null));
        }
    }

    private void zzvG() {
        Iterator<Future<?>> iterator = this.zzazZ.iterator();
        while (iterator.hasNext()) {
            iterator.next().cancel(true);
        }
        this.zzazZ.clear();
    }

    private Set<Scope> zzvH() {
        if (this.zzazs == null) {
            return Collections.emptySet();
        }
        HashSet<Scope> hashSet = new HashSet<Scope>(this.zzazs.zzxe());
        Map<Api<?>, zzg.zza> map = this.zzazs.zzxg();
        for (Api<?> api : map.keySet()) {
            if (this.zzazK.zzaAG.containsKey(api.zzuH())) continue;
            hashSet.addAll(map.get(api).zzajm);
        }
        return hashSet;
    }

    @Override
    public void begin() {
        this.zzazK.zzaAG.clear();
        this.zzazU = false;
        this.zzazA = null;
        this.zzazO = 0;
        this.zzazT = 2;
        this.zzazV = false;
        this.zzazX = false;
        HashMap<Api.zze, zza> hashMap = new HashMap<Api.zze, zza>();
        Object object = this.zzazu.keySet().iterator();
        boolean bl = false;
        while (object.hasNext()) {
            Api<?> api = object.next();
            Api.zze zze2 = this.zzazK.zzaAr.get(api.zzuH());
            int n = this.zzazu.get(api);
            boolean bl2 = api.zzuF().getPriority() == 1;
            bl |= bl2;
            if (zze2.zzqD()) {
                this.zzazU = true;
                if (n < this.zzazT) {
                    this.zzazT = n;
                }
                if (n != 0) {
                    this.zzazR.add(api.zzuH());
                }
            }
            hashMap.put(zze2, new zza(this, api, n));
        }
        if (bl) {
            this.zzazU = false;
        }
        if (this.zzazU) {
            this.zzazs.zzc(this.zzazK.zzazd.getSessionId());
            object = new zze();
            this.zzazS = this.zzaxY.zza(this.mContext, this.zzazK.zzazd.getLooper(), this.zzazs, this.zzazs.zzxk(), (GoogleApiClient.ConnectionCallbacks)object, (GoogleApiClient.OnConnectionFailedListener)object);
        }
        this.zzazP = this.zzazK.zzaAr.size();
        this.zzazZ.add(zzaao.zzvR().submit(new zzb(hashMap)));
    }

    @Override
    public void connect() {
    }

    @Override
    public boolean disconnect() {
        this.zzvG();
        this.zzaq(true);
        this.zzazK.zzh(null);
        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (!this.zzcv(1)) {
            return;
        }
        if (bundle != null) {
            this.zzazQ.putAll(bundle);
        }
        if (this.zzvB()) {
            this.zzvE();
        }
    }

    @Override
    public void onConnectionSuspended(int n) {
        this.zzf(new ConnectionResult(8, null));
    }

    @Override
    public <A extends Api.zzb, R extends Result, T extends zzzv.zza<R, A>> T zza(T t) {
        this.zzazK.zzazd.zzaAl.add(t);
        return t;
    }

    @Override
    public void zza(ConnectionResult connectionResult, Api<?> api, int n) {
        if (!this.zzcv(1)) {
            return;
        }
        this.zzb(connectionResult, api, n);
        if (this.zzvB()) {
            this.zzvE();
        }
    }

    @Override
    public <A extends Api.zzb, T extends zzzv.zza<? extends Result, A>> T zzb(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }

    private static class zza
    implements zzf.zzf {
        private final WeakReference<zzaaj> zzaAb;
        private final Api<?> zzawb;
        private final int zzazb;

        public zza(zzaaj zzaaj2, Api<?> api, int n) {
            this.zzaAb = new WeakReference<zzaaj>(zzaaj2);
            this.zzawb = api;
            this.zzazb = n;
        }

        @Override
        public void zzg(@NonNull ConnectionResult connectionResult) {
            zzaaj zzaaj2;
            block7 : {
                zzaaj2 = (zzaaj)this.zzaAb.get();
                if (zzaaj2 == null) {
                    return;
                }
                boolean bl = Looper.myLooper() == zzaaj.zzd((zzaaj)zzaaj2).zzazd.getLooper();
                zzac.zza(bl, (Object)"onReportServiceBinding must be called on the GoogleApiClient handler thread");
                zzaaj2.zzazn.lock();
                bl = zzaaj2.zzcv(0);
                if (bl) break block7;
                zzaaj2.zzazn.unlock();
                return;
            }
            try {
                if (!connectionResult.isSuccess()) {
                    zzaaj2.zzb(connectionResult, this.zzawb, this.zzazb);
                }
                if (zzaaj2.zzvB()) {
                    zzaaj2.zzvC();
                }
                return;
            }
            finally {
                zzaaj2.zzazn.unlock();
            }
        }
    }

    private class zzb
    extends zzf {
        private final Map<Api.zze, zza> zzaAc;

        public zzb(Map<Api.zze, zza> map) {
            super();
            this.zzaAc = map;
        }

        @WorkerThread
        @Override
        public void zzvA() {
            boolean bl;
            boolean bl2;
            int n;
            boolean bl3;
            Iterator<Api.zze> iterator;
            block8 : {
                iterator = this.zzaAc.keySet().iterator();
                bl3 = true;
                n = 0;
                bl = true;
                bl2 = false;
                while (iterator.hasNext()) {
                    Api.zze zze2 = iterator.next();
                    if (zze2.zzuI()) {
                        if (this.zzaAc.get(zze2).zzazb == 0) {
                            bl2 = true;
                            break block8;
                        }
                        bl2 = true;
                        continue;
                    }
                    bl = false;
                }
                boolean bl4 = false;
                bl3 = bl2;
                bl2 = bl4;
            }
            if (bl3) {
                n = zzaaj.this.zzazw.isGooglePlayServicesAvailable(zzaaj.this.mContext);
            }
            if (n != 0 && (bl2 || bl)) {
                iterator = new ConnectionResult(n, null);
                zzaaj.this.zzazK.zza(new zzaan.zza(zzaaj.this, (ConnectionResult)((Object)iterator)){
                    final /* synthetic */ ConnectionResult zzaAd;
                    {
                        this.zzaAd = connectionResult;
                        super(zzaam2);
                    }

                    @Override
                    public void zzvA() {
                        zzaaj.this.zzf(this.zzaAd);
                    }
                });
                return;
            }
            if (zzaaj.this.zzazU) {
                zzaaj.this.zzazS.connect();
            }
            for (Api.zze zze2 : this.zzaAc.keySet()) {
                final zzf.zzf zzf2 = this.zzaAc.get(zze2);
                if (zze2.zzuI() && n != 0) {
                    zzaaj.this.zzazK.zza(new zzaan.zza(this, zzaaj.this){

                        @Override
                        public void zzvA() {
                            zzf2.zzg(new ConnectionResult(16, null));
                        }
                    });
                    continue;
                }
                zze2.zza(zzf2);
            }
        }

    }

    private class zzc
    extends zzf {
        private final ArrayList<Api.zze> zzaAg;

        public zzc(ArrayList<Api.zze> arrayList) {
            super();
            this.zzaAg = arrayList;
        }

        @WorkerThread
        @Override
        public void zzvA() {
            zzaaj.zzd((zzaaj)zzaaj.this).zzazd.zzaAs = zzaaj.this.zzvH();
            Iterator<Api.zze> iterator = this.zzaAg.iterator();
            while (iterator.hasNext()) {
                iterator.next().zza(zzaaj.this.zzazW, zzaaj.zzd((zzaaj)zzaaj.this).zzazd.zzaAs);
            }
        }
    }

    private static class zzd
    extends zzaxr {
        private final WeakReference<zzaaj> zzaAb;

        zzd(zzaaj zzaaj2) {
            this.zzaAb = new WeakReference<zzaaj>(zzaaj2);
        }

        @BinderThread
        @Override
        public void zzb(final zzayb zzayb2) {
            final zzaaj zzaaj2 = (zzaaj)this.zzaAb.get();
            if (zzaaj2 == null) {
                return;
            }
            zzaaj2.zzazK.zza(new zzaan.zza(this, zzaaj2){

                @Override
                public void zzvA() {
                    zzaaj2.zza(zzayb2);
                }
            });
        }

    }

    private class zze
    implements GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {
        private zze() {
        }

        @Override
        public void onConnected(Bundle bundle) {
            zzaaj.this.zzazS.zza(new zzd(zzaaj.this));
        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            zzaaj.this.zzazn.lock();
            try {
                if (zzaaj.this.zze(connectionResult)) {
                    zzaaj.this.zzvF();
                    zzaaj.this.zzvC();
                } else {
                    zzaaj.this.zzf(connectionResult);
                }
                return;
            }
            finally {
                zzaaj.this.zzazn.unlock();
            }
        }

        @Override
        public void onConnectionSuspended(int n) {
        }
    }

    private abstract class zzf
    implements Runnable {
        private zzf() {
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @WorkerThread
        @Override
        public void run() {
            block7 : {
                block6 : {
                    zzaaj.zzc(zzaaj.this).lock();
                    var1_1 = Thread.interrupted();
                    if (!var1_1) break block6;
                    zzaaj.zzc(zzaaj.this).unlock();
                    return;
                }
                this.zzvA();
lbl10: // 2 sources:
                do {
                    zzaaj.zzc(zzaaj.this).unlock();
                    return;
                    break;
                } while (true);
                {
                    catch (Throwable var2_2) {
                        break block7;
                    }
                    catch (RuntimeException var2_3) {}
                    {
                        zzaaj.zzd(zzaaj.this).zza(var2_3);
                        ** continue;
                    }
                }
            }
            zzaaj.zzc(zzaaj.this).unlock();
            throw var2_2;
        }

        @WorkerThread
        protected abstract void zzvA();
    }

}
