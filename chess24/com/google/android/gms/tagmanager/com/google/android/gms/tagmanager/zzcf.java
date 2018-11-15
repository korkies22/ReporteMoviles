/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.tagmanager;

import android.os.Build;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;

class zzcf
extends zzam {
    private static final String ID = zzag.zzdz.toString();

    public zzcf() {
        super(ID, new String[0]);
    }

    @Override
    public boolean zzOw() {
        return true;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> map) {
        return zzdm.zzR(Build.VERSION.RELEASE);
    }
}
