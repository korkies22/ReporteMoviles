/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package com.google.android.gms.tasks;

import android.app.Activity;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzaaw;
import com.google.android.gms.internal.zzaax;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.gms.tasks.zzb;
import com.google.android.gms.tasks.zzc;
import com.google.android.gms.tasks.zzd;
import com.google.android.gms.tasks.zze;
import com.google.android.gms.tasks.zzf;
import com.google.android.gms.tasks.zzg;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

final class zzh<TResult>
extends Task<TResult> {
    private final zzg<TResult> zzbLH = new zzg();
    private boolean zzbLI;
    private TResult zzbLJ;
    private Exception zzbLK;
    private final Object zzrN = new Object();

    zzh() {
    }

    private void zzSe() {
        zzac.zza(this.zzbLI, (Object)"Task is not yet complete");
    }

    private void zzSf() {
        zzac.zza(this.zzbLI ^ true, (Object)"Task is already complete");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzSg() {
        Object object = this.zzrN;
        synchronized (object) {
            if (!this.zzbLI) {
                return;
            }
        }
        this.zzbLH.zza(this);
    }

    @NonNull
    @Override
    public Task<TResult> addOnCompleteListener(@NonNull Activity activity, @NonNull OnCompleteListener<TResult> object) {
        object = new zzc<TResult>(TaskExecutors.MAIN_THREAD, (OnCompleteListener<TResult>)object);
        this.zzbLH.zza((zzf<TResult>)object);
        zza.zzw(activity).zzb(object);
        this.zzSg();
        return this;
    }

    @NonNull
    @Override
    public Task<TResult> addOnCompleteListener(@NonNull OnCompleteListener<TResult> onCompleteListener) {
        return this.addOnCompleteListener(TaskExecutors.MAIN_THREAD, onCompleteListener);
    }

    @NonNull
    @Override
    public Task<TResult> addOnCompleteListener(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zzbLH.zza(new zzc<TResult>(executor, onCompleteListener));
        this.zzSg();
        return this;
    }

    @NonNull
    @Override
    public Task<TResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener object) {
        object = new zzd(TaskExecutors.MAIN_THREAD, (OnFailureListener)object);
        this.zzbLH.zza((zzf<TResult>)object);
        zza.zzw(activity).zzb(object);
        this.zzSg();
        return this;
    }

    @NonNull
    @Override
    public Task<TResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return this.addOnFailureListener(TaskExecutors.MAIN_THREAD, onFailureListener);
    }

    @NonNull
    @Override
    public Task<TResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzbLH.zza(new zzd(executor, onFailureListener));
        this.zzSg();
        return this;
    }

    @NonNull
    @Override
    public Task<TResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super TResult> object) {
        object = new zze<TResult>(TaskExecutors.MAIN_THREAD, (OnSuccessListener<? super TResult>)object);
        this.zzbLH.zza((zzf<TResult>)object);
        zza.zzw(activity).zzb(object);
        this.zzSg();
        return this;
    }

    @NonNull
    @Override
    public Task<TResult> addOnSuccessListener(@NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        return this.addOnSuccessListener(TaskExecutors.MAIN_THREAD, onSuccessListener);
    }

    @NonNull
    @Override
    public Task<TResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzbLH.zza(new zze<TResult>(executor, onSuccessListener));
        this.zzSg();
        return this;
    }

    @NonNull
    @Override
    public <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Continuation<TResult, TContinuationResult> continuation) {
        return this.continueWith(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    @Override
    public <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation) {
        zzh<TResult> zzh2 = new zzh<TResult>();
        this.zzbLH.zza(new com.google.android.gms.tasks.zza<TResult, TContinuationResult>(executor, continuation, zzh2));
        this.zzSg();
        return zzh2;
    }

    @NonNull
    @Override
    public <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        return this.continueWithTask(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    @Override
    public <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        zzh<TResult> zzh2 = new zzh<TResult>();
        this.zzbLH.zza(new zzb<TResult, TContinuationResult>(executor, continuation, zzh2));
        this.zzSg();
        return zzh2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    @Override
    public Exception getException() {
        Object object = this.zzrN;
        synchronized (object) {
            return this.zzbLK;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public TResult getResult() {
        Object object = this.zzrN;
        synchronized (object) {
            this.zzSe();
            if (this.zzbLK != null) {
                throw new RuntimeExecutionException(this.zzbLK);
            }
            TResult TResult = this.zzbLJ;
            return TResult;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public <X extends Throwable> TResult getResult(@NonNull Class<X> class_) throws Throwable {
        Object object = this.zzrN;
        synchronized (object) {
            this.zzSe();
            if (class_.isInstance(this.zzbLK)) {
                throw (Throwable)class_.cast(this.zzbLK);
            }
            if (this.zzbLK != null) {
                throw new RuntimeExecutionException(this.zzbLK);
            }
            class_ = this.zzbLJ;
            return (TResult)class_;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isComplete() {
        Object object = this.zzrN;
        synchronized (object) {
            return this.zzbLI;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isSuccessful() {
        Object object = this.zzrN;
        synchronized (object) {
            if (!this.zzbLI) return false;
            if (this.zzbLK != null) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setException(@NonNull Exception exception) {
        zzac.zzb(exception, (Object)"Exception must not be null");
        Object object = this.zzrN;
        synchronized (object) {
            this.zzSf();
            this.zzbLI = true;
            this.zzbLK = exception;
        }
        this.zzbLH.zza(this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setResult(TResult TResult) {
        Object object = this.zzrN;
        synchronized (object) {
            this.zzSf();
            this.zzbLI = true;
            this.zzbLJ = TResult;
        }
        this.zzbLH.zza(this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean trySetException(@NonNull Exception exception) {
        zzac.zzb(exception, (Object)"Exception must not be null");
        Object object = this.zzrN;
        synchronized (object) {
            if (this.zzbLI) {
                return false;
            }
            this.zzbLI = true;
            this.zzbLK = exception;
        }
        this.zzbLH.zza(this);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean trySetResult(TResult TResult) {
        Object object = this.zzrN;
        synchronized (object) {
            if (this.zzbLI) {
                return false;
            }
            this.zzbLI = true;
            this.zzbLJ = TResult;
        }
        this.zzbLH.zza(this);
        return true;
    }

    private static class zza
    extends zzaaw {
        private final List<WeakReference<zzf<?>>> mListeners = new ArrayList();

        private zza(zzaax zzaax2) {
            super(zzaax2);
            this.zzaBs.zza("TaskOnStopCallback", this);
        }

        public static zza zzw(Activity object) {
            zzaax zzaax2 = zza.zzs(object);
            zza zza2 = zzaax2.zza("TaskOnStopCallback", zza.class);
            object = zza2;
            if (zza2 == null) {
                object = new zza(zzaax2);
            }
            return object;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @MainThread
        @Override
        public void onStop() {
            List<WeakReference<zzf<?>>> list = this.mListeners;
            synchronized (list) {
                Iterator<WeakReference<zzf<?>>> iterator = this.mListeners.iterator();
                do {
                    if (!iterator.hasNext()) {
                        this.mListeners.clear();
                        return;
                    }
                    zzf zzf2 = (zzf)iterator.next().get();
                    if (zzf2 == null) continue;
                    zzf2.cancel();
                } while (true);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public <T> void zzb(zzf<T> zzf2) {
            List<WeakReference<zzf<?>>> list = this.mListeners;
            synchronized (list) {
                this.mListeners.add(new WeakReference<zzf<T>>(zzf2));
                return;
            }
        }
    }

}
