/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzdh;
import java.util.Map;

class zzdg
extends zzdh {
    private static final String ID = zzag.zzem.toString();

    public zzdg() {
        super(ID);
    }

    @Override
    protected boolean zza(String string, String string2, Map<String, zzaj.zza> map) {
        return string.startsWith(string2);
    }
}
