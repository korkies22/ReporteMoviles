/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzabg;
import com.google.android.gms.internal.zzabh;
import com.google.android.gms.internal.zzzx;
import java.lang.ref.WeakReference;
import java.util.concurrent.Future;

public class zzabp<R extends Result>
extends TransformedResult<R>
implements ResultCallback<R> {
    private ResultTransform<? super R, ? extends Result> zzaBM = null;
    private zzabp<? extends Result> zzaBN = null;
    private volatile ResultCallbacks<? super R> zzaBO = null;
    private PendingResult<R> zzaBP = null;
    private Status zzaBQ = null;
    private final zza zzaBR;
    private boolean zzaBS = false;
    private final Object zzayO = new Object();
    private final WeakReference<GoogleApiClient> zzayQ;

    public zzabp(WeakReference<GoogleApiClient> object) {
        zzac.zzb(object, (Object)"GoogleApiClient reference must not be null");
        this.zzayQ = object;
        object = (GoogleApiClient)this.zzayQ.get();
        object = object != null ? object.getLooper() : Looper.getMainLooper();
        this.zzaBR = new zza((Looper)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzD(Status status) {
        Object object = this.zzayO;
        synchronized (object) {
            this.zzaBQ = status;
            this.zzE(this.zzaBQ);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzE(Status status) {
        Object object = this.zzayO;
        synchronized (object) {
            if (this.zzaBM != null) {
                status = this.zzaBM.onFailure(status);
                zzac.zzb(status, (Object)"onFailure must not return null");
                zzabp.super.zzD(status);
            } else if (this.zzwv()) {
                this.zzaBO.onFailure(status);
            }
            return;
        }
    }

    static /* synthetic */ void zza(zzabp zzabp2, Result result) {
        zzabp2.zzd(result);
    }

    static /* synthetic */ void zza(zzabp zzabp2, Status status) {
        zzabp2.zzD(status);
    }

    static /* synthetic */ ResultTransform zzc(zzabp zzabp2) {
        return zzabp2.zzaBM;
    }

    static /* synthetic */ zza zzd(zzabp zzabp2) {
        return zzabp2.zzaBR;
    }

    private void zzd(Result object) {
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
                Log.w((String)"TransformedResultImpl", (String)stringBuilder.toString(), (Throwable)runtimeException);
            }
        }
    }

    static /* synthetic */ WeakReference zze(zzabp zzabp2) {
        return zzabp2.zzayQ;
    }

    static /* synthetic */ Object zzf(zzabp zzabp2) {
        return zzabp2.zzayO;
    }

    static /* synthetic */ zzabp zzg(zzabp zzabp2) {
        return zzabp2.zzaBN;
    }

    private void zzwt() {
        if (this.zzaBM == null && this.zzaBO == null) {
            return;
        }
        GoogleApiClient googleApiClient = (GoogleApiClient)this.zzayQ.get();
        if (!this.zzaBS && this.zzaBM != null && googleApiClient != null) {
            googleApiClient.zza(this);
            this.zzaBS = true;
        }
        if (this.zzaBQ != null) {
            this.zzE(this.zzaBQ);
            return;
        }
        if (this.zzaBP != null) {
            this.zzaBP.setResultCallback(this);
        }
    }

    private boolean zzwv() {
        GoogleApiClient googleApiClient = (GoogleApiClient)this.zzayQ.get();
        if (this.zzaBO != null && googleApiClient != null) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void andFinally(@NonNull ResultCallbacks<? super R> resultCallbacks) {
        Object object = this.zzayO;
        synchronized (object) {
            ResultCallbacks<? super R> resultCallbacks2 = this.zzaBO;
            boolean bl = false;
            boolean bl2 = resultCallbacks2 == null;
            zzac.zza(bl2, (Object)"Cannot call andFinally() twice.");
            bl2 = bl;
            if (this.zzaBM == null) {
                bl2 = true;
            }
            zzac.zza(bl2, (Object)"Cannot call then() and andFinally() on the same TransformedResult.");
            this.zzaBO = resultCallbacks;
            this.zzwt();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onResult(R r) {
        Object object = this.zzayO;
        synchronized (object) {
            if (r.getStatus().isSuccess()) {
                if (this.zzaBM != null) {
                    zzabg.zzvR().submit(new Runnable((Result)r){
                        final /* synthetic */ Result zzaBT;
                        {
                            this.zzaBT = result;
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
                            zzzx.zzayN.set(true);
                            object = zzabp.zzc(zzabp.this).onSuccess(this.zzaBT);
                            zzabp.zzd(zzabp.this).sendMessage(zzabp.zzd(zzabp.this).obtainMessage(0, object));
                            zzzx.zzayN.set(false);
                            zzabp.zza(zzabp.this, this.zzaBT);
                            object = (GoogleApiClient)zzabp.zze(zzabp.this).get();
                            if (object == null) return;
lbl9: // 2 sources:
                            do {
                                object.zzb(zzabp.this);
                                return;
                                break;
                            } while (true);
                            {
                                catch (Throwable throwable22) {
                                }
                                catch (RuntimeException runtimeException) {}
                                {
                                    zzabp.zzd(zzabp.this).sendMessage(zzabp.zzd(zzabp.this).obtainMessage(1, (Object)runtimeException));
                                    zzzx.zzayN.set(false);
                                }
                                zzabp.zza(zzabp.this, this.zzaBT);
                                object = (GoogleApiClient)zzabp.zze(zzabp.this).get();
                                if (object == null) return;
                                ** continue;
                            }
                            zzzx.zzayN.set(false);
                            zzabp.zza(zzabp.this, this.zzaBT);
                            googleApiClient = (GoogleApiClient)zzabp.zze(zzabp.this).get();
                            if (googleApiClient == null) throw throwable22;
                            googleApiClient.zzb(zzabp.this);
                            throw throwable22;
                        }
                    });
                } else if (this.zzwv()) {
                    this.zzaBO.onSuccess(r);
                }
            } else {
                this.zzD(r.getStatus());
                this.zzd((Result)r);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @NonNull
    @Override
    public <S extends Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> object) {
        Object object2 = this.zzayO;
        synchronized (object2) {
            ResultTransform<? super R, ? extends Result> resultTransform = this.zzaBM;
            boolean bl = false;
            boolean bl2 = resultTransform == null;
            zzac.zza(bl2, (Object)"Cannot call then() twice.");
            bl2 = bl;
            if (this.zzaBO == null) {
                bl2 = true;
            }
            zzac.zza(bl2, (Object)"Cannot call then() and andFinally() on the same TransformedResult.");
            this.zzaBM = object;
            this.zzaBN = object = new zzabp<R>(this.zzayQ);
            this.zzwt();
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zza(PendingResult<?> pendingResult) {
        Object object = this.zzayO;
        synchronized (object) {
            this.zzaBP = pendingResult;
            this.zzwt();
            return;
        }
    }

    void zzwu() {
        this.zzaBO = null;
    }

    private final class zza
    extends Handler {
        public zza(Looper looper) {
            super(looper);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public void handleMessage(Message var1_1) {
            switch (var1_1.what) {
                default: {
                    var2_2 = var1_1.what;
                    var1_1 = new StringBuilder(70);
                    var1_1.append("TransformationResultHandler received unknown message type: ");
                    var1_1.append(var2_2);
                    Log.e((String)"TransformedResultImpl", (String)var1_1.toString());
                    return;
                }
                case 1: {
                    var3_3 = (RuntimeException)var1_1.obj;
                    var1_1 = String.valueOf(var3_3.getMessage());
                    var1_1 = var1_1.length() != 0 ? "Runtime exception on the transformation worker thread: ".concat((String)var1_1) : new String("Runtime exception on the transformation worker thread: ");
                    Log.e((String)"TransformedResultImpl", (String)var1_1);
                    throw var3_3;
                }
                case 0: 
            }
            var3_4 = (PendingResult)var1_1.obj;
            var1_1 = zzabp.zzf(zzabp.this);
            // MONITORENTER : var1_1
            if (var3_4 != null) ** GOTO lbl23
            zzabp.zza(zzabp.zzg(zzabp.this), new Status(13, "Transform returned null"));
            return;
lbl23: // 1 sources:
            if (var3_4 instanceof zzabh) {
                zzabp.zza(zzabp.zzg(zzabp.this), ((zzabh)var3_4).getStatus());
                return;
            }
            zzabp.zzg(zzabp.this).zza(var3_4);
            // MONITOREXIT : var1_1
            return;
        }
    }

}
