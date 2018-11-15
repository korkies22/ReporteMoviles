/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.zzl;

public class zzal<T extends IInterface>
extends zzl<T> {
    private final Api.zzg<T> zzaFm;

    @Override
    protected String zzeu() {
        return this.zzaFm.zzeu();
    }

    @Override
    protected String zzev() {
        return this.zzaFm.zzev();
    }

    @Override
    protected T zzh(IBinder iBinder) {
        return this.zzaFm.zzh(iBinder);
    }

    public Api.zzg<T> zzxG() {
        return this.zzaFm;
    }
}
