/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.gms.tasks.zzh;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class Tasks {
    private Tasks() {
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task) throws ExecutionException, InterruptedException {
        zzac.zzxx();
        zzac.zzb(task, (Object)"Task must not be null");
        if (task.isComplete()) {
            return Tasks.zzb(task);
        }
        zza zza2 = new zza();
        Tasks.zza(task, zza2);
        zza2.await();
        return Tasks.zzb(task);
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task, long l, @NonNull TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        zzac.zzxx();
        zzac.zzb(task, (Object)"Task must not be null");
        zzac.zzb(timeUnit, (Object)"TimeUnit must not be null");
        if (task.isComplete()) {
            return Tasks.zzb(task);
        }
        zza zza2 = new zza();
        Tasks.zza(task, zza2);
        if (!zza2.await(l, timeUnit)) {
            throw new TimeoutException("Timed out waiting for Task");
        }
        return Tasks.zzb(task);
    }

    public static <TResult> Task<TResult> call(@NonNull Callable<TResult> callable) {
        return Tasks.call(TaskExecutors.MAIN_THREAD, callable);
    }

    public static <TResult> Task<TResult> call(@NonNull Executor executor, final @NonNull Callable<TResult> callable) {
        zzac.zzb(executor, (Object)"Executor must not be null");
        zzac.zzb(callable, (Object)"Callback must not be null");
        zzh zzh2 = new zzh();
        executor.execute(new Runnable(){

            @Override
            public void run() {
                try {
                    zzh.this.setResult(callable.call());
                    return;
                }
                catch (Exception exception) {
                    zzh.this.setException(exception);
                    return;
                }
            }
        });
        return zzh2;
    }

    public static <TResult> Task<TResult> forException(@NonNull Exception exception) {
        zzh zzh2 = new zzh();
        zzh2.setException(exception);
        return zzh2;
    }

    public static <TResult> Task<TResult> forResult(TResult TResult) {
        zzh<TResult> zzh2 = new zzh<TResult>();
        zzh2.setResult(TResult);
        return zzh2;
    }

    public static Task<Void> whenAll(Collection<? extends Task<?>> object) {
        if (object.isEmpty()) {
            return Tasks.forResult(null);
        }
        Object object2 = object.iterator();
        while (object2.hasNext()) {
            if (object2.next() != null) continue;
            throw new NullPointerException("null tasks are not accepted");
        }
        object2 = new zzh();
        zzc zzc2 = new zzc(object.size(), (zzh<Void>)object2);
        object = object.iterator();
        while (object.hasNext()) {
            Tasks.zza((Task)object.next(), zzc2);
        }
        return object2;
    }

    public static /* varargs */ Task<Void> whenAll(Task<?> ... arrtask) {
        if (arrtask.length == 0) {
            return Tasks.forResult(null);
        }
        return Tasks.whenAll(Arrays.asList(arrtask));
    }

    private static void zza(Task<?> task, zzb zzb2) {
        task.addOnSuccessListener(TaskExecutors.zzbLG, zzb2);
        task.addOnFailureListener(TaskExecutors.zzbLG, (OnFailureListener)zzb2);
    }

    private static <TResult> TResult zzb(Task<TResult> task) throws ExecutionException {
        if (task.isSuccessful()) {
            return task.getResult();
        }
        throw new ExecutionException(task.getException());
    }

    private static final class zza
    implements zzb {
        private final CountDownLatch zzth = new CountDownLatch(1);

        private zza() {
        }

        public void await() throws InterruptedException {
            this.zzth.await();
        }

        public boolean await(long l, TimeUnit timeUnit) throws InterruptedException {
            return this.zzth.await(l, timeUnit);
        }

        @Override
        public void onFailure(@NonNull Exception exception) {
            this.zzth.countDown();
        }

        @Override
        public void onSuccess(Object object) {
            this.zzth.countDown();
        }
    }

    static interface zzb
    extends OnFailureListener,
    OnSuccessListener<Object> {
    }

    private static final class zzc
    implements zzb {
        private final zzh<Void> zzbLF;
        private Exception zzbLK;
        private final int zzbLM;
        private int zzbLN;
        private int zzbLO;
        private final Object zzrN = new Object();

        public zzc(int n, zzh<Void> zzh2) {
            this.zzbLM = n;
            this.zzbLF = zzh2;
        }

        private void zzSh() {
            if (this.zzbLN + this.zzbLO == this.zzbLM) {
                if (this.zzbLK == null) {
                    this.zzbLF.setResult(null);
                    return;
                }
                zzh<Void> zzh2 = this.zzbLF;
                int n = this.zzbLO;
                int n2 = this.zzbLM;
                StringBuilder stringBuilder = new StringBuilder(54);
                stringBuilder.append(n);
                stringBuilder.append(" out of ");
                stringBuilder.append(n2);
                stringBuilder.append(" underlying tasks failed");
                zzh2.setException(new ExecutionException(stringBuilder.toString(), this.zzbLK));
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onFailure(@NonNull Exception exception) {
            Object object = this.zzrN;
            synchronized (object) {
                ++this.zzbLO;
                this.zzbLK = exception;
                this.zzSh();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onSuccess(Object object) {
            object = this.zzrN;
            synchronized (object) {
                ++this.zzbLN;
                this.zzSh();
                return;
            }
        }
    }

}
