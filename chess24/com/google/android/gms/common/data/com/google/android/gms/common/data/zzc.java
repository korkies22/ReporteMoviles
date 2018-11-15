/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 *  android.net.Uri
 */
package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;

public abstract class zzc {
    protected int zzaCm;
    private int zzaCn;
    protected final DataHolder zzazI;

    public zzc(DataHolder dataHolder, int n) {
        this.zzazI = zzac.zzw(dataHolder);
        this.zzcA(n);
    }

    public boolean equals(Object object) {
        boolean bl;
        boolean bl2 = object instanceof zzc;
        boolean bl3 = bl = false;
        if (bl2) {
            object = (zzc)object;
            bl3 = bl;
            if (zzaa.equal(object.zzaCm, this.zzaCm)) {
                bl3 = bl;
                if (zzaa.equal(object.zzaCn, this.zzaCn)) {
                    bl3 = bl;
                    if (object.zzazI == this.zzazI) {
                        bl3 = true;
                    }
                }
            }
        }
        return bl3;
    }

    protected boolean getBoolean(String string) {
        return this.zzazI.zze(string, this.zzaCm, this.zzaCn);
    }

    protected byte[] getByteArray(String string) {
        return this.zzazI.zzg(string, this.zzaCm, this.zzaCn);
    }

    protected float getFloat(String string) {
        return this.zzazI.zzf(string, this.zzaCm, this.zzaCn);
    }

    protected int getInteger(String string) {
        return this.zzazI.zzc(string, this.zzaCm, this.zzaCn);
    }

    protected long getLong(String string) {
        return this.zzazI.zzb(string, this.zzaCm, this.zzaCn);
    }

    protected String getString(String string) {
        return this.zzazI.zzd(string, this.zzaCm, this.zzaCn);
    }

    public int hashCode() {
        return zzaa.hashCode(this.zzaCm, this.zzaCn, this.zzazI);
    }

    public boolean isDataValid() {
        return this.zzazI.isClosed() ^ true;
    }

    protected void zza(String string, CharArrayBuffer charArrayBuffer) {
        this.zzazI.zza(string, this.zzaCm, this.zzaCn, charArrayBuffer);
    }

    protected void zzcA(int n) {
        boolean bl = n >= 0 && n < this.zzazI.getCount();
        zzac.zzar(bl);
        this.zzaCm = n;
        this.zzaCn = this.zzazI.zzcC(this.zzaCm);
    }

    public boolean zzdj(String string) {
        return this.zzazI.zzdj(string);
    }

    protected Uri zzdk(String string) {
        return this.zzazI.zzh(string, this.zzaCm, this.zzaCn);
    }

    protected boolean zzdl(String string) {
        return this.zzazI.zzi(string, this.zzaCm, this.zzaCn);
    }

    protected int zzwB() {
        return this.zzaCm;
    }
}
