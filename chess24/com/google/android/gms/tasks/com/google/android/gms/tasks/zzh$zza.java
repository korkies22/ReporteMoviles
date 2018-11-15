/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package com.google.android.gms.tasks;

import android.app.Activity;
import android.support.annotation.MainThread;
import com.google.android.gms.internal.zzaaw;
import com.google.android.gms.internal.zzaax;
import com.google.android.gms.tasks.zzf;
import com.google.android.gms.tasks.zzh;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

private static class zzh.zza
extends zzaaw {
    private final List<WeakReference<zzf<?>>> mListeners = new ArrayList();

    private zzh.zza(zzaax zzaax2) {
        super(zzaax2);
        this.zzaBs.zza("TaskOnStopCallback", this);
    }

    public static zzh.zza zzw(Activity object) {
        zzaax zzaax2 = zzh.zza.zzs(object);
        zzh.zza zza2 = zzaax2.zza("TaskOnStopCallback", zzh.zza.class);
        object = zza2;
        if (zza2 == null) {
            object = new zzh.zza(zzaax2);
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
