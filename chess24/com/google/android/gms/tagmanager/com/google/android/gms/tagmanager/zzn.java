/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzam;
import java.util.Map;

class zzn
extends zzam {
    private static final String ID = zzag.zzdi.toString();
    private static final String VALUE = zzah.zzkh.toString();

    public zzn() {
        super(ID, VALUE);
    }

    public static String zzOy() {
        return ID;
    }

    public static String zzOz() {
        return VALUE;
    }

    @Override
    public boolean zzOw() {
        return true;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> map) {
        return map.get(VALUE);
    }
}
