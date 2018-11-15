/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzg;
import java.util.Collections;
import java.util.Set;

public static final class zzg.zza {
    public final boolean zzaEf;
    public final Set<Scope> zzajm;

    public zzg.zza(Set<Scope> set, boolean bl) {
        zzac.zzw(set);
        this.zzajm = Collections.unmodifiableSet(set);
        this.zzaEf = bl;
    }
}
