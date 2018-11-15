/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class zzci
extends zzam {
    private static final String zzbEd = zzah.zzfL.toString();
    private static final String zzbFa = zzah.zzfM.toString();

    public zzci(String string) {
        super(string, zzbEd, zzbFa);
    }

    @Override
    public boolean zzOw() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> map) {
        boolean bl;
        boolean bl2;
        Object object;
        block2 : {
            object = map.values().iterator();
            do {
                bl = object.hasNext();
                bl2 = false;
                if (!bl) break block2;
            } while (object.next() != zzdm.zzQm());
            bl = bl2;
            return zzdm.zzR(bl);
        }
        object = map.get(zzbEd);
        zzaj.zza zza2 = map.get(zzbFa);
        bl = bl2;
        if (object == null) return zzdm.zzR(bl);
        if (zza2 == null) {
            bl = bl2;
            return zzdm.zzR(bl);
        }
        bl = this.zza((zzaj.zza)object, zza2, map);
        return zzdm.zzR(bl);
    }

    protected abstract boolean zza(zzaj.zza var1, zzaj.zza var2, Map<String, zzaj.zza> var3);
}
