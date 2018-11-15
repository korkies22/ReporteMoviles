/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.view.View
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzah;
import com.google.android.gms.common.internal.zzy;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzg;

public final class zzaj
extends zzg<zzy> {
    private static final zzaj zzaFl = new zzaj();

    private zzaj() {
        super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
    }

    public static View zzd(Context context, int n, int n2) throws zzg.zza {
        return zzaFl.zze(context, n, n2);
    }

    private View zze(Context context, int n, int n2) throws zzg.zza {
        try {
            zzah zzah2 = new zzah(n, n2, null);
            zzd zzd2 = zze.zzA(context);
            context = (View)zze.zzE(((zzy)this.zzaT(context)).zza(zzd2, zzah2));
            return context;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder(64);
            stringBuilder.append("Could not get button with size ");
            stringBuilder.append(n);
            stringBuilder.append(" and color ");
            stringBuilder.append(n2);
            throw new zzg.zza(stringBuilder.toString(), exception);
        }
    }

    public zzy zzby(IBinder iBinder) {
        return zzy.zza.zzbx(iBinder);
    }

    @Override
    public /* synthetic */ Object zzc(IBinder iBinder) {
        return this.zzby(iBinder);
    }
}
