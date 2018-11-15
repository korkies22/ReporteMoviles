/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzaaj;
import com.google.android.gms.internal.zzaal;
import com.google.android.gms.internal.zzaan;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

private class zzaaj.zzc
extends zzaaj.zzf {
    private final ArrayList<Api.zze> zzaAg;

    public zzaaj.zzc(ArrayList<Api.zze> arrayList) {
        super(zzaaj.this, null);
        this.zzaAg = arrayList;
    }

    @WorkerThread
    @Override
    public void zzvA() {
        zzaaj.zzd((zzaaj)zzaaj.this).zzazd.zzaAs = zzaaj.this.zzvH();
        Iterator<Api.zze> iterator = this.zzaAg.iterator();
        while (iterator.hasNext()) {
            iterator.next().zza(zzaaj.this.zzazW, zzaaj.zzd((zzaaj)zzaaj.this).zzazd.zzaAs);
        }
    }
}
