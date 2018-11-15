/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.Process
 *  android.text.TextUtils
 *  android.util.DisplayMetrics
 *  android.util.Log
 */
package com.google.android.gms.analytics;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.google.android.gms.analytics.zzd;
import com.google.android.gms.analytics.zze;
import com.google.android.gms.analytics.zzg;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzre;
import com.google.android.gms.internal.zzrj;
import com.google.android.gms.internal.zztg;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzh {
    private static volatile zzh zzabg;
    private final Context mContext;
    private final List<Object> zzabh;
    private final zzd zzabi;
    private final zza zzabj;
    private volatile zzre zzabk;
    private Thread.UncaughtExceptionHandler zzabl;

    zzh(Context context) {
        context = context.getApplicationContext();
        zzac.zzw(context);
        this.mContext = context;
        this.zzabj = new zza();
        this.zzabh = new CopyOnWriteArrayList<Object>();
        this.zzabi = new zzd();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzh zzV(Context context) {
        zzac.zzw(context);
        if (zzabg != null) return zzabg;
        synchronized (zzh.class) {
            if (zzabg != null) return zzabg;
            zzabg = new zzh(context);
            return zzabg;
        }
    }

    private void zzb(zze zze2) {
        zzac.zzdo("deliver should be called from worker thread");
        zzac.zzb(zze2.zzmg(), (Object)"Measurement must be submitted");
        Object object = zze2.zzmd();
        if (object.isEmpty()) {
            return;
        }
        HashSet<Uri> hashSet = new HashSet<Uri>();
        object = object.iterator();
        while (object.hasNext()) {
            zzi zzi2 = (zzi)object.next();
            Uri uri = zzi2.zzlQ();
            if (hashSet.contains((Object)uri)) continue;
            hashSet.add(uri);
            zzi2.zzb(zze2);
        }
    }

    public static void zzmq() {
        if (!(Thread.currentThread() instanceof zzc)) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public void zza(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.zzabl = uncaughtExceptionHandler;
    }

    public <V> Future<V> zzc(Callable<V> object) {
        zzac.zzw(object);
        if (Thread.currentThread() instanceof zzc) {
            object = new FutureTask<V>((Callable<V>)object);
            object.run();
            return object;
        }
        return this.zzabj.submit(object);
    }

    void zze(final zze zze2) {
        if (zze2.zzmk()) {
            throw new IllegalStateException("Measurement prototype can't be submitted");
        }
        if (zze2.zzmg()) {
            throw new IllegalStateException("Measurement can only be submitted once");
        }
        zze2 = zze2.zzmb();
        zze2.zzmh();
        this.zzabj.execute(new Runnable(){

            @Override
            public void run() {
                zze2.zzmi().zza(zze2);
                Iterator iterator = zzh.this.zzabh.iterator();
                while (iterator.hasNext()) {
                    iterator.next();
                }
                zzh.this.zzb(zze2);
            }
        });
    }

    public void zzg(Runnable runnable) {
        zzac.zzw(runnable);
        this.zzabj.submit(runnable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public zzre zzmo() {
        if (this.zzabk == null) {
            synchronized (this) {
                if (this.zzabk == null) {
                    String string;
                    CharSequence charSequence;
                    zzre zzre2;
                    block8 : {
                        zzre2 = new zzre();
                        PackageManager packageManager = this.mContext.getPackageManager();
                        String string2 = this.mContext.getPackageName();
                        zzre2.setAppId(string2);
                        zzre2.setAppInstallerId(packageManager.getInstallerPackageName(string2));
                        CharSequence charSequence2 = null;
                        String string3 = string2;
                        try {
                            PackageInfo packageInfo = packageManager.getPackageInfo(this.mContext.getPackageName(), 0);
                            string = string2;
                            charSequence = charSequence2;
                            if (packageInfo != null) {
                                string3 = string2;
                                charSequence = packageManager.getApplicationLabel(packageInfo.applicationInfo);
                                string = string2;
                                string3 = string2;
                                if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                                    string3 = string2;
                                    string = charSequence.toString();
                                }
                                string3 = string;
                                charSequence = packageInfo.versionName;
                            }
                            break block8;
                        }
                        catch (PackageManager.NameNotFoundException nameNotFoundException) {}
                        string = String.valueOf(string3);
                        string = string.length() != 0 ? "Error retrieving package info: appName set to ".concat(string) : new String("Error retrieving package info: appName set to ");
                        Log.e((String)"GAv4", (String)string);
                        charSequence = charSequence2;
                        string = string3;
                    }
                    zzre2.setAppName(string);
                    zzre2.setAppVersion((String)charSequence);
                    this.zzabk = zzre2;
                }
            }
        }
        return this.zzabk;
    }

    public zzrj zzmp() {
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        zzrj zzrj2 = new zzrj();
        zzrj2.setLanguage(zztg.zza(Locale.getDefault()));
        zzrj2.zzaz(displayMetrics.widthPixels);
        zzrj2.zzaA(displayMetrics.heightPixels);
        return zzrj2;
    }

    private class zza
    extends ThreadPoolExecutor {
        public zza() {
            super(1, 1, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
            this.setThreadFactory(new zzb());
            this.allowCoreThreadTimeOut(true);
        }

        @Override
        protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
            return new FutureTask<T>(runnable, t){

                @Override
                protected void setException(Throwable throwable) {
                    Object object = zzh.this.zzabl;
                    if (object != null) {
                        object.uncaughtException(Thread.currentThread(), throwable);
                    } else if (Log.isLoggable((String)"GAv4", (int)6)) {
                        object = String.valueOf(throwable);
                        StringBuilder stringBuilder = new StringBuilder(37 + String.valueOf(object).length());
                        stringBuilder.append("MeasurementExecutor: job failed with ");
                        stringBuilder.append((String)object);
                        Log.e((String)"GAv4", (String)stringBuilder.toString());
                    }
                    super.setException(throwable);
                }
            };
        }

    }

    private static class zzb
    implements ThreadFactory {
        private static final AtomicInteger zzabp = new AtomicInteger();

        private zzb() {
        }

        @Override
        public Thread newThread(Runnable runnable) {
            int n = zzabp.incrementAndGet();
            StringBuilder stringBuilder = new StringBuilder(23);
            stringBuilder.append("measurement-");
            stringBuilder.append(n);
            return new zzc(runnable, stringBuilder.toString());
        }
    }

    private static class zzc
    extends Thread {
        zzc(Runnable runnable, String string) {
            super(runnable, string);
        }

        @Override
        public void run() {
            Process.setThreadPriority((int)10);
            super.run();
        }
    }

}
