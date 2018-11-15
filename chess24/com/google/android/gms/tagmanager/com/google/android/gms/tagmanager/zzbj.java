/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class zzbj
extends zzam {
    private static final String ID = zzag.zzdx.toString();

    public zzbj() {
        super(ID, new String[0]);
    }

    @Override
    public boolean zzOw() {
        return false;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> object) {
        object = Locale.getDefault();
        if (object == null) {
            return zzdm.zzQm();
        }
        if ((object = object.getLanguage()) == null) {
            return zzdm.zzQm();
        }
        return zzdm.zzR(object.toLowerCase());
    }
}
