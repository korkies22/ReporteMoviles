/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzsj;
import com.google.android.gms.internal.zzst;
import com.google.android.gms.internal.zzsx;
import com.google.android.gms.internal.zzsz;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

private class zzsz.zza {
    private ByteArrayOutputStream zzafA = new ByteArrayOutputStream();
    private int zzafz;

    public byte[] getPayload() {
        return this.zzafA.toByteArray();
    }

    public boolean zzj(zzst zzst2) {
        zzac.zzw(zzst2);
        if (this.zzafz + 1 > zzsz.this.zzns().zzoH()) {
            return false;
        }
        byte[] arrby = zzsz.this.zza(zzst2, false);
        if (arrby == null) {
            zzsz.this.zznr().zza(zzst2, "Error formatting hit");
            return true;
        }
        int n = (arrby = arrby.getBytes()).length;
        if (n > zzsz.this.zzns().zzoz()) {
            zzsz.this.zznr().zza(zzst2, "Hit size exceeds the maximum size limit");
            return true;
        }
        int n2 = n;
        if (this.zzafA.size() > 0) {
            n2 = n + 1;
        }
        if (this.zzafA.size() + n2 > zzsz.this.zzns().zzoB()) {
            return false;
        }
        try {
            if (this.zzafA.size() > 0) {
                this.zzafA.write(zzafy);
            }
            this.zzafA.write(arrby);
            ++this.zzafz;
            return true;
        }
        catch (IOException iOException) {
            zzsz.this.zze("Failed to write payload when batching hits", iOException);
            return true;
        }
    }

    public int zzpD() {
        return this.zzafz;
    }
}
