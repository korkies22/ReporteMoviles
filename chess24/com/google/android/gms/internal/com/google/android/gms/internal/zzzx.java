/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.util.Log
 *  android.util.Pair
 */
package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzs;
import com.google.android.gms.internal.zzabp;
import com.google.android.gms.internal.zzabq;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public abstract class zzzx<R extends Result>
extends PendingResult<R> {
    static final ThreadLocal<Boolean> zzayN = new ThreadLocal<Boolean>(){

        @Override
        protected /* synthetic */ Object initialValue() {
            return this.zzvg();
        }

        protected Boolean zzvg() {
            return false;
        }
    };
    private boolean zzJ;
    private final Object zzayO = new Object();
    protected final zza<R> zzayP;
    protected final WeakReference<GoogleApiClient> zzayQ;
    private final ArrayList<PendingResult.zza> zzayR = new ArrayList();
    private ResultCallback<? super R> zzayS;
    private final AtomicReference<zzabq.zzb> zzayT = new AtomicReference();
    private zzb zzayU;
    private volatile boolean zzayV;
    private boolean zzayW;
    private zzs zzayX;
    private volatile zzabp<R> zzayY;
    private boolean zzayZ = false;
    private R zzayd;
    private final CountDownLatch zzth = new CountDownLatch(1);

    @Deprecated
    zzzx() {
        this.zzayP = new zza(Looper.getMainLooper());
        this.zzayQ = new WeakReference<Object>(null);
    }

    @Deprecated
    protected zzzx(Looper looper) {
        this.zzayP = new zza(looper);
        this.zzayQ = new WeakReference<Object>(null);
    }

    protected zzzx(GoogleApiClient googleApiClient) {
        Looper looper = googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper();
        this.zzayP = new zza(looper);
        this.zzayQ = new WeakReference<GoogleApiClient>(googleApiClient);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private R get() {
        R r;
        Object object = this.zzayO;
        synchronized (object) {
            zzac.zza(this.zzayV ^ true, (Object)"Result has already been consumed.");
            zzac.zza(this.isReady(), (Object)"Result is not ready.");
            r = this.zzayd;
            this.zzayd = null;
            this.zzayS = null;
            this.zzayV = true;
        }
        this.zzvd();
        return r;
    }

    private void zzc(R object) {
        this.zzayd = object;
        this.zzayX = null;
        this.zzth.countDown();
        object = this.zzayd.getStatus();
        if (this.zzJ) {
            this.zzayS = null;
        } else if (this.zzayS == null) {
            if (this.zzayd instanceof Releasable) {
                this.zzayU = new zzb();
            }
        } else {
            this.zzayP.zzvh();
            this.zzayP.zza(this.zzayS, (R)this.get());
        }
        Iterator<PendingResult.zza> iterator = this.zzayR.iterator();
        while (iterator.hasNext()) {
            iterator.next().zzx((Status)object);
        }
        this.zzayR.clear();
    }

    public static void zzd(Result object) {
        if (object instanceof Releasable) {
            try {
                ((Releasable)object).release();
                return;
            }
            catch (RuntimeException runtimeException) {
                object = String.valueOf(object);
                StringBuilder stringBuilder = new StringBuilder(18 + String.valueOf(object).length());
                stringBuilder.append("Unable to release ");
                stringBuilder.append((String)object);
                Log.w((String)"BasePendingResult", (String)stringBuilder.toString(), (Throwable)runtimeException);
            }
        }
    }

    private void zzvd() {
        zzabq.zzb zzb2 = this.zzayT.getAndSet(null);
        if (zzb2 != null) {
            zzb2.zzc(this);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final R await() {
        block3 : {
            Looper looper = Looper.myLooper();
            Looper looper2 = Looper.getMainLooper();
            boolean bl = false;
            boolean bl2 = looper != looper2;
            zzac.zza(bl2, (Object)"await must not be called on the UI thread");
            zzac.zza(this.zzayV ^ true, (Object)"Result has already been consumed");
            bl2 = bl;
            if (this.zzayY == null) {
                bl2 = true;
            }
            zzac.zza(bl2, (Object)"Cannot await if then() has been called.");
            try {
                this.zzth.await();
                break block3;
            }
            catch (InterruptedException interruptedException) {}
            this.zzB(Status.zzayi);
        }
        zzac.zza(this.isReady(), (Object)"Result is not ready.");
        return this.get();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final R await(long l, TimeUnit timeUnit) {
        block4 : {
            boolean bl = false;
            boolean bl2 = l <= 0L || Looper.myLooper() != Looper.getMainLooper();
            zzac.zza(bl2, (Object)"await must not be called on the UI thread when time is greater than zero.");
            zzac.zza(this.zzayV ^ true, (Object)"Result has already been consumed.");
            bl2 = bl;
            if (this.zzayY == null) {
                bl2 = true;
            }
            zzac.zza(bl2, (Object)"Cannot await if then() has been called.");
            try {
                if (!this.zzth.await(l, timeUnit)) {
                    this.zzB(Status.zzayk);
                }
                break block4;
            }
            catch (InterruptedException interruptedException) {}
            this.zzB(Status.zzayi);
        }
        zzac.zza(this.isReady(), (Object)"Result is not ready.");
        return this.get();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void cancel() {
        Object object = this.zzayO;
        // MONITORENTER : object
        if (this.zzJ) {
            // MONITOREXIT : object
            return;
        }
        if (this.zzayV) {
            return;
        }
        zzs zzs2 = this.zzayX;
        if (zzs2 != null) {
            try {
                this.zzayX.cancel();
            }
            catch (RemoteException remoteException) {}
        }
        zzzx.zzd(this.zzayd);
        this.zzJ = true;
        this.zzc(this.zzc(Status.zzayl));
        // MONITOREXIT : object
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isCanceled() {
        Object object = this.zzayO;
        synchronized (object) {
            return this.zzJ;
        }
    }

    public final boolean isReady() {
        if (this.zzth.getCount() == 0L) {
            return true;
        }
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public final void setResultCallback(ResultCallback<? super R> var1_1) {
        block5 : {
            var4_3 = this.zzayO;
            // MONITORENTER : var4_3
            if (var1_1 != null) ** GOTO lbl8
            this.zzayS = null;
            // MONITOREXIT : var4_3
            return;
lbl8: // 1 sources:
            var3_4 = this.zzayV;
            var2_5 = true;
            zzac.zza(var3_4 ^ true, (Object)"Result has already been consumed.");
            if (this.zzayY == null) break block5;
            var2_5 = false;
        }
        zzac.zza(var2_5, (Object)"Cannot set callbacks if then() has been called.");
        if (this.isCanceled()) {
            // MONITOREXIT : var4_3
            return;
        }
        if (this.isReady()) {
            this.zzayP.zza(var1_1, (R)this.get());
            return;
        }
        this.zzayS = var1_1;
        // MONITOREXIT : var4_3
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public final void setResultCallback(ResultCallback<? super R> var1_1, long var2_3, TimeUnit var4_4) {
        block5 : {
            var7_5 = this.zzayO;
            // MONITORENTER : var7_5
            if (var1_1 != null) ** GOTO lbl8
            this.zzayS = null;
            // MONITOREXIT : var7_5
            return;
lbl8: // 1 sources:
            var6_6 = this.zzayV;
            var5_7 = true;
            zzac.zza(var6_6 ^ true, (Object)"Result has already been consumed.");
            if (this.zzayY == null) break block5;
            var5_7 = false;
        }
        zzac.zza(var5_7, (Object)"Cannot set callbacks if then() has been called.");
        if (this.isCanceled()) {
            // MONITOREXIT : var7_5
            return;
        }
        if (this.isReady()) {
            this.zzayP.zza(var1_1, (R)this.get());
            return;
        }
        this.zzayS = var1_1;
        this.zzayP.zza(this, var4_4.toMillis(var2_3));
        // MONITOREXIT : var7_5
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> object) {
        zzac.zza(this.zzayV ^ true, (Object)"Result has already been consumed.");
        Object object2 = this.zzayO;
        synchronized (object2) {
            zzabp<R> zzabp2 = this.zzayY;
            boolean bl = false;
            boolean bl2 = zzabp2 == null;
            zzac.zza(bl2, (Object)"Cannot call then() twice.");
            bl2 = bl;
            if (this.zzayS == null) {
                bl2 = true;
            }
            zzac.zza(bl2, (Object)"Cannot call then() if callbacks are set.");
            this.zzayZ = true;
            this.zzayY = new zzabp(this.zzayQ);
            object = this.zzayY.then(object);
            if (this.isReady()) {
                this.zzayP.zza(this.zzayY, this.get());
            } else {
                this.zzayS = this.zzayY;
            }
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzB(Status status) {
        Object object = this.zzayO;
        synchronized (object) {
            if (!this.isReady()) {
                this.zzb(this.zzc(status));
                this.zzayW = true;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void zza(PendingResult.zza zza2) {
        boolean bl = this.zzayV;
        boolean bl2 = true;
        zzac.zza(bl ^ true, (Object)"Result has already been consumed.");
        if (zza2 == null) {
            bl2 = false;
        }
        zzac.zzb(bl2, (Object)"Callback cannot be null.");
        Object object = this.zzayO;
        synchronized (object) {
            if (this.isReady()) {
                zza2.zzx(this.zzayd.getStatus());
            } else {
                this.zzayR.add(zza2);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final void zza(zzs zzs2) {
        Object object = this.zzayO;
        synchronized (object) {
            this.zzayX = zzs2;
            return;
        }
    }

    public void zza(zzabq.zzb zzb2) {
        this.zzayT.set(zzb2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzb(R r) {
        Object object = this.zzayO;
        synchronized (object) {
            if (!this.zzayW && !this.zzJ) {
                this.isReady();
                zzac.zza(this.isReady() ^ true, (Object)"Results have already been set");
                zzac.zza(this.zzayV ^ true, (Object)"Result has already been consumed");
                this.zzc(r);
                return;
            }
            zzzx.zzd(r);
            return;
        }
    }

    @NonNull
    protected abstract R zzc(Status var1);

    @Override
    public Integer zzuR() {
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean zzvc() {
        Object object = this.zzayO;
        synchronized (object) {
            if ((GoogleApiClient)this.zzayQ.get() != null) {
                if (this.zzayZ) return this.isCanceled();
            }
            this.cancel();
            return this.isCanceled();
        }
    }

    public void zzve() {
        this.setResultCallback(null);
    }

    public void zzvf() {
        boolean bl = this.zzayZ || zzayN.get().booleanValue();
        this.zzayZ = bl;
    }

    public static class zza<R extends Result>
    extends Handler {
        public zza() {
            this(Looper.getMainLooper());
        }

        public zza(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message object) {
            switch (object.what) {
                default: {
                    int n = object.what;
                    object = new StringBuilder(45);
                    object.append("Don't know how to handle message: ");
                    object.append(n);
                    Log.wtf((String)"BasePendingResult", (String)object.toString(), (Throwable)new Exception());
                    return;
                }
                case 2: {
                    ((zzzx)object.obj).zzB(Status.zzayk);
                    return;
                }
                case 1: 
            }
            object = (Pair)object.obj;
            this.zzb((ResultCallback)object.first, (Result)object.second);
        }

        public void zza(ResultCallback<? super R> resultCallback, R r) {
            this.sendMessage(this.obtainMessage(1, (Object)new Pair(resultCallback, r)));
        }

        public void zza(zzzx<R> zzzx2, long l) {
            this.sendMessageDelayed(this.obtainMessage(2, zzzx2), l);
        }

        protected void zzb(ResultCallback<? super R> resultCallback, R r) {
            try {
                resultCallback.onResult(r);
                return;
            }
            catch (RuntimeException runtimeException) {
                zzzx.zzd(r);
                throw runtimeException;
            }
        }

        public void zzvh() {
            this.removeMessages(2);
        }
    }

    private final class zzb {
        private zzb() {
        }

        protected void finalize() throws Throwable {
            zzzx.zzd(zzzx.this.zzayd);
            super.finalize();
        }
    }

}
