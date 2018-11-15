/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.internal.zzb;
import com.google.android.gms.internal.zzc;
import com.google.android.gms.internal.zze;
import com.google.android.gms.internal.zzf;
import com.google.android.gms.internal.zzg;
import com.google.android.gms.internal.zzk;
import com.google.android.gms.internal.zzn;
import com.google.android.gms.internal.zzs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class zzl {
    private AtomicInteger zzW = new AtomicInteger();
    private final Map<String, Queue<zzk<?>>> zzX = new HashMap();
    private final Set<zzk<?>> zzY = new HashSet();
    private final PriorityBlockingQueue<zzk<?>> zzZ = new PriorityBlockingQueue();
    private final PriorityBlockingQueue<zzk<?>> zzaa = new PriorityBlockingQueue();
    private zzg[] zzab;
    private zzc zzac;
    private List<Object> zzad = new ArrayList<Object>();
    private final zzb zzi;
    private final zzn zzj;
    private final zzf zzx;

    public zzl(zzb zzb2, zzf zzf2) {
        this(zzb2, zzf2, 4);
    }

    public zzl(zzb zzb2, zzf zzf2, int n) {
        this(zzb2, zzf2, n, new zze(new Handler(Looper.getMainLooper())));
    }

    public zzl(zzb zzb2, zzf zzf2, int n, zzn zzn2) {
        this.zzi = zzb2;
        this.zzx = zzf2;
        this.zzab = new zzg[n];
        this.zzj = zzn2;
    }

    public int getSequenceNumber() {
        return this.zzW.incrementAndGet();
    }

    public void start() {
        this.stop();
        this.zzac = new zzc(this.zzZ, this.zzaa, this.zzi, this.zzj);
        this.zzac.start();
        for (int i = 0; i < this.zzab.length; ++i) {
            zzg zzg2;
            this.zzab[i] = zzg2 = new zzg(this.zzaa, this.zzx, this.zzi, this.zzj);
            zzg2.start();
        }
    }

    public void stop() {
        if (this.zzac != null) {
            this.zzac.quit();
        }
        for (int i = 0; i < this.zzab.length; ++i) {
            if (this.zzab[i] == null) continue;
            this.zzab[i].quit();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public <T> zzk<T> zze(zzk<T> zzk2) {
        zzk2.zza(this);
        Collection<zzk<?>> collection = this.zzY;
        synchronized (collection) {
            this.zzY.add(zzk2);
        }
        zzk2.zza(this.getSequenceNumber());
        zzk2.zzc("add-to-queue");
        if (!zzk2.zzn()) {
            this.zzaa.add(zzk2);
            return zzk2;
        }
        Map<String, Queue<zzk<?>>> map = this.zzX;
        synchronized (map) {
            String string = zzk2.zzg();
            if (this.zzX.containsKey(string)) {
                Queue<zzk<?>> queue = this.zzX.get(string);
                collection = queue;
                if (queue == null) {
                    collection = new LinkedList();
                }
                collection.add(zzk2);
                this.zzX.put(string, (Queue<zzk<?>>)collection);
                if (zzs.DEBUG) {
                    zzs.zza("Request for cacheKey=%s is in flight, putting on hold.", string);
                }
            } else {
                this.zzX.put(string, null);
                this.zzZ.add(zzk2);
            }
            return zzk2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    <T> void zzf(zzk<T> object) {
        Object object2 = this.zzY;
        // MONITORENTER : object2
        this.zzY.remove(object);
        // MONITOREXIT : object2
        object2 = this.zzad;
        // MONITORENTER : object2
        Object object3 = this.zzad.iterator();
        while (object3.hasNext()) {
            object3.next();
        }
        // MONITOREXIT : object2
        if (!object.zzn()) return;
        object2 = this.zzX;
        // MONITORENTER : object2
        object = object.zzg();
        object3 = this.zzX.remove(object);
        if (object3 != null) {
            if (zzs.DEBUG) {
                zzs.zza("Releasing %d waiting requests for cacheKey=%s.", object3.size(), object);
            }
            this.zzZ.addAll((Collection<zzk<?>>)object3);
        }
        // MONITOREXIT : object2
    }
}
