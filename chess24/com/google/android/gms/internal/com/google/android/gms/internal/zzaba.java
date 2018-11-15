/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzaaz;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class zzaba {
    private final Set<zzaaz<?>> zzarH = Collections.newSetFromMap(new WeakHashMap());

    public static <L> zzaaz.zzb<L> zza(@NonNull L l, @NonNull String string) {
        zzac.zzb(l, (Object)"Listener must not be null");
        zzac.zzb(string, (Object)"Listener type must not be null");
        zzac.zzh(string, "Listener type must not be empty");
        return new zzaaz.zzb<L>(l, string);
    }

    public static <L> zzaaz<L> zzb(@NonNull L l, @NonNull Looper looper, @NonNull String string) {
        zzac.zzb(l, (Object)"Listener must not be null");
        zzac.zzb(looper, (Object)"Looper must not be null");
        zzac.zzb(string, (Object)"Listener type must not be null");
        return new zzaaz<L>(looper, l, string);
    }

    public void release() {
        Iterator<zzaaz<?>> iterator = this.zzarH.iterator();
        while (iterator.hasNext()) {
            iterator.next().clear();
        }
        this.zzarH.clear();
    }

    public <L> zzaaz<L> zza(@NonNull L object, @NonNull Looper looper, @NonNull String string) {
        object = zzaba.zzb(object, looper, string);
        this.zzarH.add((zzaaz<?>)object);
        return object;
    }

    public <L> zzaaz<L> zzb(@NonNull L l, Looper looper) {
        return this.zza(l, looper, "NO_TYPE");
    }
}
