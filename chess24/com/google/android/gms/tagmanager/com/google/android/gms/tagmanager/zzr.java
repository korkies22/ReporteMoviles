/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;

class zzr
extends zzam {
    private static final String ID = zzag.zzdl.toString();
    private final String zzaux;

    public zzr(String string) {
        super(ID, new String[0]);
        this.zzaux = string;
    }

    @Override
    public boolean zzOw() {
        return true;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> map) {
        if (this.zzaux == null) {
            return zzdm.zzQm();
        }
        return zzdm.zzR(this.zzaux);
    }
}
