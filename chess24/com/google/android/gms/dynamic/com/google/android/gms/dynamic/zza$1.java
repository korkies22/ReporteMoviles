/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.dynamic;

import android.os.Bundle;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.zza;
import com.google.android.gms.dynamic.zzf;
import java.util.Iterator;
import java.util.LinkedList;

class zza
implements zzf<T> {
    zza() {
    }

    @Override
    public void zza(T object) {
        zza.this.zzaQd = object;
        object = zza.this.zzaQf.iterator();
        while (object.hasNext()) {
            ((zza.zza)object.next()).zzb(zza.this.zzaQd);
        }
        zza.this.zzaQf.clear();
        zza.this.zzaQe = null;
    }
}
